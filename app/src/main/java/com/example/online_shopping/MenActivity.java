package com.example.online_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_shopping.Model.CartModel;
import com.example.online_shopping.Model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men);
        FloatingActionButton fab = findViewById(R.id.fab);

        int count=0;

        final MyDatabase CartDB= new MyDatabase(getApplicationContext());


//        ProductModel myprod = new ProductModel(27,1,"ProductName3",275);
//
//        CartDB.insertProduct(myprod);
//        MyDatabase CartDB= new MyDatabase(getApplicationContext())
//        if (cursor.getCount() == 3)
//            Toast.makeText(this, "products exist", Toast.LENGTH_SHORT).show();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ProductModel myprod = new ProductModel(26,1,"ProductName4",300);
//                CartDB.insertProduct(myprod);

                Intent intent = new Intent(getApplicationContext(), submitActivity.class);
                startActivity(intent);                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });


        final TextView newText1 = (TextView) findViewById(R.id.text_cart_1);
        final TextView newText2 = (TextView) findViewById(R.id.text_cart_2);
        final TextView newText3 = (TextView) findViewById(R.id.text_cart_3);
        final TextView newText4 = (TextView) findViewById(R.id.text_cart_4);


        final TextView price1 = (TextView) findViewById(R.id.price1);
        final TextView price2 = (TextView) findViewById(R.id.price2);
        final TextView price3 = (TextView) findViewById(R.id.price3);
        final TextView price4 = (TextView) findViewById(R.id.price4);

        final Cursor cursor = CartDB.getMenProducts();


        while(!cursor.isAfterLast()){
            if (count == 0){
                newText1.setText(cursor.getString(0));
                price1.setText(cursor.getString(2) + " EGP");
            }
            else if (count == 1){
                newText2.setText(cursor.getString(0));
                price2.setText(cursor.getString(2) + " EGP");
            }
            else if(count == 2){
                newText3.setText(cursor.getString(0));
                price3.setText(cursor.getString(2) + " EGP");
            }
            else if(count == 3){
                newText4.setText(cursor.getString(0));
                price4.setText(cursor.getString(2) + " EGP");
            }
            count++;
            cursor.moveToNext();
        }



//        ImageView jackets = (ImageView) findViewById(R.id.jacket_id);

        newText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor Prod_price = CartDB.getProductPrice(newText1.getText().toString());
//                Toast.makeText(getApplicationContext(),Prod_price.getString(0),Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences2;
                sharedPreferences2=getSharedPreferences("remember file",MODE_PRIVATE);
                String name = sharedPreferences2.getString("username","");

                //zizo edited here
//                Toast.makeText(MenActivity.this, newText1.getText().toString(), Toast.LENGTH_SHORT).show();
                CartDB.AddToCartZizo(newText1.getText().toString(),name);
//                CartDB.insertIntoCart(newText1.getText().toString(),name,Prod_price.getString(0));

//                CartDB.insertCartItem(newText1.getText().toString());
                Toast.makeText(getApplicationContext(),"zizo1",Toast.LENGTH_LONG).show();
            }
        });

        newText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CartDB.insertCartItem(newText2.getText().toString());
                Cursor Prod_price = CartDB.getProductPrice(newText2.getText().toString());

                SharedPreferences sharedPreferences2;
                sharedPreferences2=getSharedPreferences("remember file",MODE_PRIVATE);
                String name = sharedPreferences2.getString("username","");

                CartDB.AddToCartZizo(newText2.getText().toString(),name);

//                CartDB.insertIntoCart(newText2.getText().toString(),name,Prod_price.getString(0));

                Toast.makeText(getApplicationContext(),"zizo2",Toast.LENGTH_LONG).show();
            }
        });
//
        newText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CartDB.insertCartItem(newText3.getText().toString());
                Cursor Prod_price = CartDB.getProductPrice(newText3.getText().toString());

                SharedPreferences sharedPreferences2;
                sharedPreferences2=getSharedPreferences("remember file",MODE_PRIVATE);
                String name = sharedPreferences2.getString("username","");
                CartDB.AddToCartZizo(newText3.getText().toString(),name);

//                CartDB.insertIntoCart(newText3.getText().toString(),name,Prod_price.getString(0));
                Toast.makeText(getApplicationContext(),"zizo3",Toast.LENGTH_LONG).show();
            }
        });

        newText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CartDB.insertCartItem(newText3.getText().toString());
                Cursor Prod_price = CartDB.getProductPrice(newText4.getText().toString());

                SharedPreferences sharedPreferences2;
                sharedPreferences2=getSharedPreferences("remember file",MODE_PRIVATE);
                String name = sharedPreferences2.getString("username","");
                CartDB.AddToCartZizo(newText4.getText().toString(),name);

//                CartDB.insertIntoCart(newText4.getText().toString(),name,Prod_price.getString(0));
                Toast.makeText(getApplicationContext(),"zizo",Toast.LENGTH_LONG).show();
            }
        });






    }
}