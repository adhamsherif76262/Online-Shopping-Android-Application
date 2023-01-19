package com.example.online_shopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoiceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoiceSearchFragment extends Fragment {

    EditText text;
    Cursor matchedProduct;
    int voiceCode=1;
    ListView mylist;
    MyDatabase DB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VoiceSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoiceSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoiceSearchFragment newInstance(String param1, String param2) {
        VoiceSearchFragment fragment = new VoiceSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == voiceCode && resultCode == getActivity().RESULT_OK){
            ArrayList <String> text2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            text.setText(text2.get(0));

        ArrayAdapter<String> ProductsAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        mylist.setAdapter(ProductsAdapter);

        Cursor matchedProducts = DB.getProductByName(text2.get(0));

        if(matchedProducts !=null){

            while (! matchedProducts.isAfterLast()){
                ProductsAdapter.add(matchedProducts.getString(1));
                matchedProducts.moveToNext();
            }
        }
        else {
            Toast.makeText(getActivity(), "No matched products",Toast.LENGTH_LONG).show();
        }

        }
        super.onActivityResult(requestCode, resultCode, data);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_voice_search,container,false);

        DB= new MyDatabase(getActivity());
        mylist=(ListView) rootview.findViewById(R.id.products_vc_list);
        text = (EditText) rootview.findViewById(R.id.text_vc);
        ImageButton voicebtn = (ImageButton) rootview.findViewById(R.id.voice_record);


        voicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(intent, voiceCode);
            }
        });


        // Inflate the layout for this fragment
        return rootview;
    }
}