package ua.nure.pharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sales_button_ = findViewById(R.id.goto_sales_button);
        sales_button_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSalesPage();
            }
        });

        add_medicine_button_ = findViewById(R.id.goto_add_button);
        add_medicine_button_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddMedicinePage();
            }
        });
    }
    private void OpenSalesPage() {
        Intent intent = new Intent(this, SaleActivity.class);
        startActivity(intent);
    }
    private void OpenAddMedicinePage() {
        Intent intent = new Intent(this, AddMedicineActivity.class);
        startActivity(intent);
    }

    private Button sales_button_;
    private Button add_medicine_button_;
}