package com.example.implycafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProductActivity extends AppCompatActivity {
    private TextInputEditText productName, productDescription, productValue;
    private ImageView productImage;
    private String imageUrl;

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
        productImage = findViewById(R.id.imagemDoProduto);
        Button productSaveButton = findViewById(R.id.saveProductButton);

        Intent intent = getIntent();
        Product productToEdit = (Product) intent.getSerializableExtra("productToEdit");

        productImage.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
            LayoutInflater inflater = LayoutInflater.from(ProductActivity.this);
            View dialogView = inflater.inflate(R.layout.loadimage_layout, null);


            ImageView imagePopup = dialogView.findViewById(R.id.imageViewPopup);

            TextInputEditText urlinput = dialogView.findViewById(R.id.urlLink);
            if(productToEdit != null) {
                imageUrl = productToEdit.getImageUrl();
                if(imageUrl != null && !imageUrl.isEmpty()){
                    Glide.with(ProductActivity.this)
                            .load(imageUrl)
                            .into(imagePopup);
                    urlinput.setText(productToEdit.getImageUrl());
                }

            }



            Button loadImageButton = dialogView.findViewById(R.id.loadImageButton);


            loadImageButton.setOnClickListener(v1 -> {
                imageUrl = urlinput.getText().toString();
                Glide.with(ProductActivity.this)
                        .load(imageUrl)
                        .error(R.drawable.error_200dp_ea3323_fill0_wght400_grad0_opsz48)

                        .into(productImage);

                Glide.with(ProductActivity.this)
                        .load(imageUrl)
                        .error(R.drawable.error_200dp_ea3323_fill0_wght400_grad0_opsz48)

                        .into(imagePopup);
            });
            builder.setView(dialogView);

            builder.setPositiveButton("CONFIRMAR", (dialog,which) -> {
            });

            builder.setNegativeButton("CANCELAR", (dialog,which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

        });

        if(productToEdit != null) {
            productName.setText(productToEdit.getName());
            productDescription.setText(productToEdit.getDescription());
            productValue.setText(productToEdit.getPrice());
            imageUrl = productToEdit.getImageUrl();

            if(imageUrl != null && !imageUrl.isEmpty()){
                Glide.with(ProductActivity.this)
                        .load(imageUrl)
                        .error(R.drawable.error_200dp_ea3323_fill0_wght400_grad0_opsz48)
                        .into(productImage);
            }
        }

        productSaveButton.setOnClickListener(v -> {
            String name = productName.getText().toString();
            String description = productDescription.getText().toString();
            String value = String.valueOf(productValue.getText());

            if(productToEdit != null) {
                productToEdit.setName(name);
                productToEdit.setDescription(description);
                productToEdit.setPrice(value);
                productToEdit.setImageUrl(imageUrl);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedProduct", productToEdit);
                resultIntent.putExtra("position", intent.getIntExtra("position", -1));
                setResult(RESULT_OK, resultIntent);
            } else {
                Product newProduct = new Product(name, description, value, imageUrl);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newProduct", newProduct);
                setResult(RESULT_OK, resultIntent);
            }
            finish();
        });
    }
}