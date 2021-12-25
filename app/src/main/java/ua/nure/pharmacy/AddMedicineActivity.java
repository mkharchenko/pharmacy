package ua.nure.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ua.nure.pharmacy.controllers.AddMedicineController;
import ua.nure.pharmacy.dto.Medicine;
import ua.nure.pharmacy.dto.MedicineLeftover;

public class AddMedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        controller_ = new AddMedicineController(this);

        title_input_ = (EditText) findViewById(R.id.title_input);
        price_input_ = (EditText) findViewById(R.id.price_input);
        leftover_input_ = (EditText) findViewById(R.id.leftover_input);

        add_button_ = findViewById(R.id.add_button);
        add_button_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AddMedicine()) {
                    Toast.makeText(getApplicationContext(), "Не все поля формы заполнены верно!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Новый медикамент успешно добавлен.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean AddMedicine() {
        int leftover;
        double price;
        String title;
        title = title_input_.getText().toString();
        if (title.isEmpty())
            return false;

        try {
            price = Double.valueOf(price_input_.getText().toString());
            leftover = Integer.valueOf(leftover_input_.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        controller_.AddMedicineToJson(title, price, leftover);

        title_input_.setText("");
        price_input_.setText("");
        leftover_input_.setText("");

        return true;
    }

    private EditText title_input_;
    private EditText price_input_;
    private EditText leftover_input_;

    private Button add_button_;

    private AddMedicineController controller_;
}