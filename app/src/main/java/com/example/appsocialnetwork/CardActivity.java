package com.example.appsocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        textView1 = findViewById(R.id.textView2);
        initView();
    }

    private void initView() {
        Bundle arguments = getIntent().getExtras();
        if (arguments!=null){
            String textview1 = arguments.getString("textview1");
            textView1.setText(textview1);
        }
    }


}