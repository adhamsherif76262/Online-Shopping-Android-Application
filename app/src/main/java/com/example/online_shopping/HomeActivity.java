package com.example.online_shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.online_shopping.Model.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensubmitActivity();
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);







        ImageButton MenBtn = (ImageButton) findViewById(R.id.MenBtn);
        MenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenActivity();
            }
        });

        ImageButton WOMENBTN = (ImageButton) findViewById(R.id.WomenBtn);
        WOMENBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWomenActivity();
            }
        });

        MenuItem Logout = (MenuItem) findViewById(R.id.logout_btn);




//        Logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
////                Intent intent = new Intent(this, signupActivity.class);
////                startActivity(intent);
////                Boolean login;
////                SharedPreferences sharedPreferences;
////                sharedPreferences=getSharedPreferences("remember file",MODE_PRIVATE);
////                login= sharedPreferences.getBoolean("login",false);
////                if(login){
////                    Intent intent=new Intent(HomeActivity.this,signupActivity.class);
////                    startActivity(intent);
////                    finish();
////                }
//                Toast.makeText(getApplicationContext(), "Please Check username and password", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout_btn:
                Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences;
                SharedPreferences.Editor editor;
                sharedPreferences=getSharedPreferences("remember file",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putBoolean("login",false);
                editor.apply();
                Intent intent = new Intent(this, loginActivity.class);
                startActivity(intent);
//                MyDatabase cartdb = new MyDatabase(getApplicationContext());
//                cartdb.DeleteCartItems();
//                cartdb.DeleteCart();
                return true;


            case R.id.search_prd:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);

            case R.id.CreateProducts:

                  MyDatabase DB = new MyDatabase(getApplicationContext());
                  DB.DeleteProducts();
                  ProductModel pr1 = new ProductModel(10,1,"men product one",120);
                ProductModel pr2 = new ProductModel(10,1,"men product two",130);
                ProductModel pr3 = new ProductModel(10,1,"men product three",140);
                ProductModel pr4 = new ProductModel(10,1,"men product four",150);

                ProductModel wpr1 = new ProductModel(10,2,"women product one",120);
                ProductModel wpr2 = new ProductModel(10,2,"women product two",130);
                ProductModel wpr3 = new ProductModel(10,2,"women product three",140);
                ProductModel wpr4 = new ProductModel(10,2,"women product four",150);
                DB.insertProduct(pr1);
                DB.insertProduct(pr2);
                DB.insertProduct(pr3);
                DB.insertProduct(pr4);

                DB.insertProduct(wpr1);
                DB.insertProduct(wpr2);
                DB.insertProduct(wpr3);
                DB.insertProduct(wpr4);

            case R.id.Barcode:
                Intent intent3 = new Intent(this, BarcodeScannerActivity.class);
                startActivity(intent3);
            case R.id.action_settings:
//                  MyDatabase DB = new MyDatabase(getApplicationContext());
//                  ProductModel pr1 = new ProductModel(10,1,"product",120);
//                ProductModel pr2 = new ProductModel(10,1,"myproduct",130);
//                ProductModel pr3 = new ProductModel(10,1,"product1",140);
//                ProductModel pr4 = new ProductModel(10,1,"product2",150);
//                DB.insertProduct(pr1);
//                DB.insertProduct(pr2);
//                DB.insertProduct(pr3);
//                DB.insertProduct(pr4);
//                SharedPreferences sharedPreferences2;
//                sharedPreferences2=getSharedPreferences("remember file",MODE_PRIVATE);
//                String name = sharedPreferences2.getString("username","");
//                  MyDatabase DB = new MyDatabase(getApplicationContext());
//                  DB.newdb();
                Intent intent4 = new Intent(this, VoiceTestActivity.class);
                startActivity(intent4);
//                Toast.makeText(getApplicationContext(), "Products Created", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void opensubmitActivity() {
        Intent intent = new Intent(this, submitActivity.class);
        startActivity(intent);
    }
    public void openMenActivity() {
        Intent intent = new Intent(this, MenActivity.class);
        startActivity(intent);
    }

    public void openWomenActivity() {
        Intent intent = new Intent(this, WomenActivity.class);
        startActivity(intent);
    }
}