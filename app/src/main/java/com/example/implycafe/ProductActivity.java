package com.example.implycafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class ProductActivity extends AppCompatActivity {
    private TextInputEditText productName, productDescription, productValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productValue = findViewById(R.id.productValue);
        Button productSaveButton = findViewById(R.id.saveProductButton);

        productSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                String description = productDescription.getText().toString();
                String value = String.valueOf(productValue.getText());

                Product newProduct = new Product(name,description,value);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newProduct", newProduct);
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}