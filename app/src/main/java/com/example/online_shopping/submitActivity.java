package com.example.online_shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class submitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        final MyDatabase newdb = new MyDatabase(getApplicationContext());

        final ListView CartList = (ListView) findViewById(R.id.products_cart);
        final ArrayAdapter <String> myadabpter = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        CartList.setAdapter(myadabpter);

        final ListView DeleteList = (ListView) findViewById(R.id.Deletebtn);
        final ArrayAdapter <String> deleteadpt = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        DeleteList.setAdapter(deleteadpt);

        final ListView PlusList = (ListView) findViewById(R.id.Plus_btn);
        final ArrayAdapter <String> PlusAdapt = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        PlusList.setAdapter(PlusAdapt);

        final ListView MinusList = (ListView) findViewById(R.id.Minus_btn);
        final ArrayAdapter <String> MinusAdapt = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        MinusList.setAdapter(MinusAdapt);

        Button btn = (Button) findViewById(R.id.button2);
        final ListView CartListprice = (ListView) findViewById(R.id.products_cart2);
        final ArrayAdapter <String> myadabpterprice = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        CartListprice.setAdapter(myadabpterprice);

        final ListView CartListqty = (ListView) findViewById(R.id.products_cart3);
        final ArrayAdapter <String> myadabpterqty = new ArrayAdapter<String> (getApplicationContext(),
                android.R.layout.simple_list_item_1);
        CartListqty.setAdapter(myadabpterqty);

        final TextView qty_control = (TextView) findViewById(R.id.Total_Price);





        int qty1 = 1;
        int qty2 = 1;
        int qty3 = 1;
        int qty4 = 1;

//        qty_control.setText("1");



        DeleteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String prod_name = String.valueOf(CartList.getItemAtPosition(i));
                String Porname = newdb.getProductIDByName(prod_name);
                newdb.DeleteOrderItem(Integer.parseInt(Porname));

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        PlusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String prod_name = String.valueOf(CartList.getItemAtPosition(i));
                String Porname = newdb.getProductIDByName(prod_name);
                newdb.IncreamentProductCart(Integer.parseInt(Porname));

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        MinusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String prod_name = String.valueOf(CartList.getItemAtPosition(i));
                String Porname = newdb.getProductIDByName(prod_name);
                newdb.DecreamentProductCart(Integer.parseInt(Porname));

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


//        CartListqty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                String x = String.valueOf(i);
//                  String prod_name = String.valueOf(CartList.getItemAtPosition(i));
//                  String Porname = newdb.getProductIDByName(prod_name);
//                  newdb.IncreamentProductCart(Integer.parseInt(Porname));
//
//
////
////                String Price = String.valueOf(CartListprice.getItemAtPosition(i));
////
////
////
////
////                qty_control.setText("asd");
//
//
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
//
//
//
////                Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();
////                Toast.makeText(submitActivity.this, String.valueOf(CartList.getItemAtPosition(i)), Toast.LENGTH_SHORT).show();
//            }
//        });


//        CartListqty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = CartListqty.getItemAtPosition(position).toString();
////                String newitem = item.replaceAll("[^0-9]","");
//                qty_control.setText(item);
//                myadabpterqty.remove(CartListqty.getItemAtPosition(position).toString());
////                myadabpterqty.add(qty_control.getText().toString());
////                Toast.makeText(getApplicationContext(),qty_control.getText().toString(),Toast.LENGTH_LONG).show();
////                qty1[0]++;
//
////                Toast.makeText(getApplicationContext(),Integer.parseInt(qty_control.toString()),Toast.LENGTH_LONG).show();
////                qty1[0] = Integer.parseInt(qty_control.toString());
//
//            }
//        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Order Done Successfully", Toast.LENGTH_SHORT).show();
                newdb.DeleteAllCartItems();
                Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences("remember file",MODE_PRIVATE);
        String name = sharedPreferences.getString("username","");
        Boolean logged = sharedPreferences.getBoolean("login",false);

//        int qty1=1,qty2 = 1, qty3=1;

//        Cursor cursor = newdb.getCart(name);
        Cursor cursor = newdb.GetCartOfZizo(name);

        if(cursor.getCount() == 0){
//            myadabpter.add(name);
            myadabpter.add("No Items Found");
        }
        else
        {
//          myadabpter.add("Current user is:    "+ name + "\n Cart user is:   " +cursor.getString(0));
            String HEADER = "Name" ;

            myadabpter.add("Name");
//            myadabpter.add("\n");
            myadabpterprice.add("Price");

            myadabpterqty.add("QTY");

            PlusAdapt.add("");
            MinusAdapt.add("");
            deleteadpt.add("");


//            String nameprod = cursor.getString(0).toString();

//          if(cursor.getString(0).equals(name)){
                while (!cursor.isAfterLast()) {
//                    for (int i =0;i<CartList.getCount();i++){
//                        if(cursor.getString(1).equals(Cart))
//                    }
                    String id = newdb.getProductbyId(cursor.getInt(0));

                    String price = newdb.getSingleOrderPice(id,cursor.getInt(1));
//                    Toast.makeText(this, price, Toast.LENGTH_SHORT).show();

                    myadabpter.add(id.toString());
                    myadabpterprice.add(price);
                    myadabpterqty.add(cursor.getString(1));




                    PlusAdapt.add("+");
                    MinusAdapt.add("-");
                    deleteadpt.add("R");


                    cursor.moveToNext();
                }
//            }
//            else
//                Toast.makeText(getApplicationContext(),"no data to show",Toast.LENGTH_LONG).show();
        }



        Integer Total = 0;
        for (int j =1 ; j <CartListprice.getAdapter().getCount();j++){

           Total += Integer.parseInt(String.valueOf(CartListprice.getItemAtPosition(j)));
//           Toast.makeText(getApplicationContext(), String.valueOf(CartListprice.getItemAtPosition(1)), Toast.LENGTH_SHORT).show();

        }


//        String Price = String.valueOf(CartListprice.getAdapter().getCount());
        qty_control.setText(String.valueOf(Total));


    }




}