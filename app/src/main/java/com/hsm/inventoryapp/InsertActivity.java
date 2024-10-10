package com.hsm.inventoryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hsm.inventoryapp.data.ProductAdapter;
import com.hsm.inventoryapp.data.StoreContract;

public class InsertActivity extends AppCompatActivity {
    EditText productNameTextView;
    EditText priceTextView;
    TextView quantityTextView;
    EditText supplierNameTextView;
    EditText supplierPhoneNumberTextView;
    Button increaseQuantityButton;
    Button decreaseQuantityButton;
    int insertRequestCode = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        productNameTextView = findViewById(R.id.productNameEditTextInsertItemValue);
        priceTextView = findViewById(R.id.priceEditTextInsertItemValue);
        quantityTextView = findViewById(R.id.quantityTextViewInsertItemValue);
        supplierNameTextView = findViewById(R.id.supplierNameEditTextInsertItemValue);
        supplierPhoneNumberTextView = findViewById(R.id.supplierPhoneNumberEditTextInsertItemValue);
        increaseQuantityButton = findViewById(R.id.increaseQuantityButtonInsert);
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButtonInsert);
        increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityTextView.setText((Integer.parseInt(quantityTextView.getText().toString()) + 1) + "");
            }
        });
        decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantityTextView.getText().toString()) != 0) {
                    quantityTextView.setText((Integer.parseInt(quantityTextView.getText().toString()) - 1) + "");
                } else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.decreaseQuantityOver0Message), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveEditMenu:
                if (productNameTextView.getText().toString().equals("") || priceTextView.getText().toString().equals("") || supplierNameTextView.getText().toString().equals("") || supplierPhoneNumberTextView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.insertRequestMessage), Toast.LENGTH_SHORT).show();
                } else {
                    new StoreContract.ProductHelper(this).insert(new StoreContract.Product(productNameTextView.getText().toString(), Integer.parseInt(priceTextView.getText().toString()), Integer.parseInt(quantityTextView.getText().toString()), supplierNameTextView.getText().toString(), supplierPhoneNumberTextView.getText().toString()));
                    MainActivity.productArrayList = new StoreContract.ProductHelper(this).getAllProducts();
                    Intent returnToMainActivityIntent = new Intent(InsertActivity.this, MainActivity.class);
                    setResult(insertRequestCode, returnToMainActivityIntent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
