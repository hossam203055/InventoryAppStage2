package com.hsm.inventoryapp.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hsm.inventoryapp.EditActivity;
import com.hsm.inventoryapp.MainActivity;
import com.hsm.inventoryapp.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<StoreContract.Product> {
    ArrayList<StoreContract.Product> productArrayList;
    Context context;
    TextView productNameTextView;
    TextView priceTextView;
    TextView quantityTextView;
    Button saleButton;
    Button showDetailsButton;
    Button callSupplierButton;
    public ProductAdapter(@NonNull Context context, ArrayList<StoreContract.Product> productArrayList) {
        super(context, R.layout.item, productArrayList);
        this.context = context;
        this.productArrayList = productArrayList;
    }
    @Override
    public int getCount() {
        return productArrayList.size();
    }
    @Nullable
    @Override
    public StoreContract.Product getItem(int position) {
        return productArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        productNameTextView = view.findViewById(R.id.productNameTexViewItemValue);
        priceTextView = view.findViewById(R.id.priceTextViewItemValue);
        quantityTextView = view.findViewById(R.id.quantityTextViewItemValue);
        saleButton = view.findViewById(R.id.salaButtonItem);
        showDetailsButton = view.findViewById(R.id.showDetailsButtonItem);
        callSupplierButton = view.findViewById(R.id.callSupplierButtonItem);
        productNameTextView.setText(productArrayList.get(position).getProductName());
        priceTextView.setText(productArrayList.get(position).getPrice() + "");
        quantityTextView.setText(productArrayList.get(position).getQuantity() + "");
        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                Bundle sendProductDataBundle = new Bundle();
                sendProductDataBundle.putString("productName", productArrayList.get(position).getProductName());
                sendProductDataBundle.putInt("price", productArrayList.get(position).getPrice());
                sendProductDataBundle.putInt("quantity", productArrayList.get(position).getQuantity());
                sendProductDataBundle.putString("supplierName", productArrayList.get(position).getSupplierName());
                sendProductDataBundle.putString("supplierPhoneNumber", productArrayList.get(position).getSupplierPhoneNumber());
                sendProductDataBundle.putInt("productIndex",position);
                intent.putExtras(sendProductDataBundle);
                getContext().startActivity(intent);
                Toast.makeText(getContext(), "Go to Intent from " + position, Toast.LENGTH_SHORT).show();
            }
        });
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = productArrayList.get(position).getQuantity();
                if (!productArrayList.get(position).isSold()){
                    productArrayList.get(position).setQuantity(--quantity);
                    StoreContract.Product product = MainActivity.productArrayList.get(position);
                    product.setQuantity(Integer.parseInt(quantityTextView.getText().toString()));
                    StoreContract.ProductHelper productHelperForUpdate = new StoreContract.ProductHelper(getContext());
                    productHelperForUpdate.update(product);
                    MainActivity.productArrayList = productHelperForUpdate.getAllProducts();
                }
                if (quantity < 0) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.soldProductMessage) + position, Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
        callSupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callSupplierIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + productArrayList.get(position).getSupplierPhoneNumber()));
                getContext().startActivity(callSupplierIntent);
            }
        });

        return view;
    }
}
