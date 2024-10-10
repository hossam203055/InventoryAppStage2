package com.hsm.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hsm.inventoryapp.data.ProductAdapter;
import com.hsm.inventoryapp.data.StoreContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<StoreContract.Product> productArrayList;
    ListView productListView;
    ProductAdapter productAdapter;
    TextView messageTextView;
    int insertRequuestCode = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertItemsFromCode();
        showResults();
        productArrayList = new StoreContract.ProductHelper(this).getAllProducts();
        productAdapter = new ProductAdapter(this,productArrayList);
        productListView = findViewById(R.id.productListView);
        productListView.setAdapter(productAdapter);

        if (productArrayList.isEmpty()){
            messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(getResources().getString(R.string.messageText));
        }
    }
    public void showResults(){
        productArrayList = new StoreContract.ProductHelper(this).getAllProducts();
        for (int i = 0; i<productArrayList.size();i++)
            Log.i("getItem",productArrayList.get(i).toString());
    }
    public void insertItemsFromCode(){
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro A",50,7,"n Supplier","+201223567898"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro B",70,6,"X Supplier","+201053927878"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro C",49,1,"X Supplier","+201053927878"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro D",26,5,"n Supplier","+201223567898"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro E",85,2,"X Supplier","+201053927878"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro F",37,3,"n Supplier","+201223567898"));
        new StoreContract.ProductHelper(this).insert(new StoreContract.Product("pro G",60,2,"n Supplier","+201223567898"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        productAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertMenu:
                Intent insertIntent = new Intent(MainActivity.this,InsertActivity.class);
                startActivityForResult(insertIntent,insertRequuestCode);
        }
        return super.onOptionsItemSelected(item);
    }
}
