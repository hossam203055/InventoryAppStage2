package com.hsm.inventoryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

public class EditActivity extends AppCompatActivity {
    EditText productNameTextView;
    EditText priceTextView;
    TextView quantityTextView;
    EditText supplierNameTextView;
    EditText supplierPhoneNumberTextView;
    Button increaseQuantityButton;
    Button decreaseQuantityButton;
    Button callSupplierButton;
    int productIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        productNameTextView = findViewById(R.id.productNameEditTextItemDetailValue);
        priceTextView = findViewById(R.id.priceEditTextItemDetailValue);
        quantityTextView = findViewById(R.id.quantityTextViewItemDetailValue);
        supplierNameTextView = findViewById(R.id.supplierNameEditTextItemDetailValue);
        supplierPhoneNumberTextView = findViewById(R.id.supplierPhoneNumberEditTextItemDetailValue);
        increaseQuantityButton = findViewById(R.id.increaseQuantityButton);
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButton);
        callSupplierButton = findViewById(R.id.callSupplierButtonItemDetail);
        callSupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callSupplierIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + supplierPhoneNumberTextView.getText().toString()));
                startActivity(callSupplierIntent);
            }
        });
        Bundle recieveProductDataBundle = getIntent().getExtras();
        productNameTextView.setText(recieveProductDataBundle.getString("productName"));
        priceTextView.setText(recieveProductDataBundle.getInt("price") + "");
        quantityTextView.setText(recieveProductDataBundle.getInt("quantity") + "");
        supplierNameTextView.setText(recieveProductDataBundle.getString("supplierName"));
        supplierPhoneNumberTextView.setText(recieveProductDataBundle.getString("supplierPhoneNumber"));
        productIndex = recieveProductDataBundle.getInt("productIndex");
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
                    StoreContract.Product product = MainActivity.productArrayList.get(productIndex);
                    product.set_Id(MainActivity.productArrayList.get(productIndex).get_Id());
                    product.setProductName(productNameTextView.getText().toString());
                    product.setPrice(Integer.parseInt(priceTextView.getText().toString()));
                    product.setQuantity(Integer.parseInt(quantityTextView.getText().toString()));
                    product.setSupplierName(supplierNameTextView.getText().toString());
                    product.setSupplierPhoneNumber(supplierPhoneNumberTextView.getText().toString());
                    StoreContract.ProductHelper productHelperForUpdate = new StoreContract.ProductHelper(this);
                    productHelperForUpdate.update(product);
                    MainActivity.productArrayList = productHelperForUpdate.getAllProducts();
                    finish();
                    return true;
                }
            case R.id.deleteEditMenu:
                final StoreContract.ProductHelper productHelper = new StoreContract.ProductHelper(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.confirmDeleteMessage));
                builder.setPositiveButton(getResources().getString(R.string.yesAnswer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productHelper.delete(MainActivity.productArrayList.get(productIndex).get_Id());
                        MainActivity.productArrayList = productHelper.getAllProducts();
                        finish();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.noAnswer), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        if (dialogInterface != null) {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
