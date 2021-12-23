package ua.nure.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ua.nure.pharmacy.controllers.SalesController;
import ua.nure.pharmacy.dto.MedicineLeftover;

public class SaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        controller_ = new SalesController(this);

        medicines_leftover_adapter_ =
                new ArrayAdapter<MedicineLeftover>(this, android.R.layout.simple_list_item_1, controller_.GetMedicineLeftover());

        medicines_leftover_ = findViewById(R.id.medicines_leftover);
        medicines_leftover_.setAdapter(medicines_leftover_adapter_);
        medicines_leftover_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected_medicine_ = medicines_leftover_adapter_.getItem(i);
            }
        });

        amount_text_ = (EditText) findViewById(R.id.amount_input);

        sale_button_ = findViewById(R.id.sale_button);
        sale_button_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaleMedicine();
            }
        });
    }

    private void SaleMedicine() {
        if (selected_medicine_ == null) {
            Toast.makeText(getApplicationContext(), "Вы не выбрали медикамент из списка!", Toast.LENGTH_SHORT).show();
            return;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(amount_text_.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Вы не ввели количество!", Toast.LENGTH_SHORT).show();
            return;
        }
        controller_.AddSale(selected_medicine_, amount);
        medicines_leftover_adapter_.notifyDataSetChanged();

        selected_medicine_ = null;
        amount_text_.setText("");
    }

    private ListView medicines_leftover_;
    private ArrayAdapter<MedicineLeftover> medicines_leftover_adapter_;

    private EditText amount_text_;
    private Button sale_button_;

    private MedicineLeftover selected_medicine_;

    private SalesController controller_;
}