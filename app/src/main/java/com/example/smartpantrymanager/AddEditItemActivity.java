package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEditItemActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Pantry Item");
        }

        etName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etItemQuantity);
        etUnit = findViewById(R.id.etItemUnit);
        etExpiry = findViewById(R.id.etItemExpiry);
        btnSave = findViewById(R.id.btnSaveItem);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(qtyStr) || TextUtils.isEmpty(unit)) {
            Toast.makeText(this, "Please fill in Name, Quantity, and Unit", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(qtyStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(expiry)) {
            expiry = "No expiry date";
        }

        dbHelper.addPantryItem(name, quantity, unit, expiry);
        Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}