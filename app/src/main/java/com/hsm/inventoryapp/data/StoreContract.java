package com.hsm.inventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class StoreContract {
    public StoreContract() {
    }

    private static final class ProductAttributesTable implements BaseColumns {
        public static final String tableName = "Product";
        public static final String _Id = BaseColumns._ID;
        public static final String productName = "Product_Name";
        public static final String price = "Price";
        public static final String quantity = "Quantity";
        public static final String supplierName = "Supplier_Name";
        public static final String supplierPhoneNumber = "Supplier_Phone_Number";
    }

    public static class Product {
        private int _Id;
        private String productName;
        private int price;
        private int quantity;
        private String supplierName;
        private String supplierPhoneNumber;

        public Product(int _Id, String productName, int price, int quantity, String supplierName, String supplierPhoneNumber) {
            this._Id = _Id;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.supplierName = supplierName;
            this.supplierPhoneNumber = supplierPhoneNumber;
        }

        public Product(String productName, int price, int quantity, String supplierName, String supplierPhoneNumber) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.supplierName = supplierName;
            this.supplierPhoneNumber = supplierPhoneNumber;
        }

        public int get_Id() {
            return _Id;
        }

        public String getProductName() {
            return productName;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public String getSupplierPhoneNumber() {
            return supplierPhoneNumber;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public boolean isSold() {
            return quantity == 0 ? true : false;
        }

        public void set_Id(int _Id) {
            this._Id = _Id;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public void setSupplierPhoneNumber(String supplierPhoneNumber) {
            this.supplierPhoneNumber = supplierPhoneNumber;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "_Id=" + _Id +
                    ", Product Name='" + productName + '\'' +
                    ", Price=" + price +
                    ", Quantity=" + quantity +
                    ", Supplier Name='" + supplierName + '\'' +
                    ", Supplier Phone Number='" + supplierPhoneNumber + '\'' +
                    '}';
        }
    }

    public static class ProductHelper extends SQLiteOpenHelper {
        public static final int databaseVersion = 1;
        public static final String databaseName = "MyDatabase";
        public static final String textType = " TEXT";
        public static final String integerType = " INTEGER";
        public static final String primaryKeyConstraint = " PRIMARY KEY";
        public static final String notNullConstraint = " NOT NULL";
        public static final char commaSyntax = ',';
        public static final String createDatabaseStatement = "CREATE TABLE " + StoreContract.ProductAttributesTable.tableName + " (" + StoreContract.ProductAttributesTable._Id + integerType + primaryKeyConstraint + commaSyntax + StoreContract.ProductAttributesTable.productName + textType + notNullConstraint + commaSyntax + StoreContract.ProductAttributesTable.price + integerType + notNullConstraint + commaSyntax + StoreContract.ProductAttributesTable.quantity + integerType + notNullConstraint + commaSyntax + StoreContract.ProductAttributesTable.supplierName + textType + notNullConstraint + commaSyntax + StoreContract.ProductAttributesTable.supplierPhoneNumber + textType + notNullConstraint + ");";

        public ProductHelper(Context context) {
            super(context, databaseName, null, databaseVersion);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createDatabaseStatement);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        public void insert(Product product) {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(StoreContract.ProductAttributesTable.productName, product.getProductName());
            contentValues.put(StoreContract.ProductAttributesTable.price, product.getPrice());
            contentValues.put(StoreContract.ProductAttributesTable.quantity, product.getQuantity());
            contentValues.put(StoreContract.ProductAttributesTable.supplierName, product.getSupplierName());
            contentValues.put(StoreContract.ProductAttributesTable.supplierPhoneNumber, product.getSupplierPhoneNumber());
            db.insert(StoreContract.ProductAttributesTable.tableName, null, contentValues);
        }

        public void update(Product product) {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(StoreContract.ProductAttributesTable.productName, product.getProductName());
            contentValues.put(StoreContract.ProductAttributesTable.price, product.getPrice());
            contentValues.put(StoreContract.ProductAttributesTable.quantity, product.getQuantity());
            contentValues.put(StoreContract.ProductAttributesTable.supplierName, product.getSupplierName());
            contentValues.put(StoreContract.ProductAttributesTable.supplierPhoneNumber, product.getSupplierPhoneNumber());
            db.update(StoreContract.ProductAttributesTable.tableName, contentValues,ProductAttributesTable._Id+" = ?", new String[]{product.get_Id() + ""});
        }
        public void delete(int id){
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(ProductAttributesTable.tableName,ProductAttributesTable._Id+" = ?",new String[]{id+""});
        }
        public ArrayList<Product> getAllProducts() {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<Product> productArrayList = new ArrayList<>();
            String selectAll = "SELECT * FROM " + StoreContract.ProductAttributesTable.tableName;
            Cursor cursor = db.rawQuery(selectAll, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int _Id = cursor.getInt(0);
                String productName = cursor.getString(1);
                int price = cursor.getInt(2);
                int quantity = cursor.getInt(3);
                String supplierName = cursor.getString(4);
                String supplierPhoneNumber = cursor.getString(5);
                Product product = new Product(_Id, productName, price, quantity, supplierName, supplierPhoneNumber);
                productArrayList.add(product);
            }
            cursor.close();
            return productArrayList;
        }
    }
}
