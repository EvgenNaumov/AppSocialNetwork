package com.example.appsocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appsocialnetwork.card.InitView;
import com.example.appsocialnetwork.data.CardData;

public class CardActivity extends AppCompatActivity implements InitView {

    private TextView textTitle ;
    private TextView textDescrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        textTitle = findViewById(R.id.textView2);
        textDescrip = findViewById(R.id.textView4);
        InitView();
    }

    @Override
    public void InitView() {
        Bundle arguments = getIntent().getExtras();
        CardData currData = getIntent().getParcelableExtra("cardData");
        if (arguments!=null){
//            String textview1 = arguments.getString("textview1");
            textTitle.setText(currData.getTitle().toString());
            textDescrip.setText(currData.getDescription().toString());
        }
    }



}