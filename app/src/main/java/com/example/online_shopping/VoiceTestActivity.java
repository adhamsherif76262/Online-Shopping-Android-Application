package com.example.online_shopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VoiceTestActivity extends AppCompatActivity {

    TextView textView;

    Cursor matchedProduct;
    ListView mylist;
    MyDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_test);

        textView = findViewById(R.id.textView);
    }

    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK) {
            textView.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));

            DB= new MyDatabase(this);
            mylist=(ListView) findViewById(R.id.products_vc_list);

            ArrayAdapter<String> ProductsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            mylist.setAdapter(ProductsAdapter);

            Cursor matchedProducts = DB.getProductByName(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));

            if (matchedProducts != null) {

                while (!matchedProducts.isAfterLast()) {
                    ProductsAdapter.add(matchedProducts.getString(1));
                    matchedProducts.moveToNext();
                }
            } else {
                Toast.makeText(this, "No matched products", Toast.LENGTH_LONG).show();
            }
        }

    }
}