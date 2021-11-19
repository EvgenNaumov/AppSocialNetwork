package com.example.appsocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appsocialnetwork.card.InitView;
import com.example.appsocialnetwork.data.CardData;
import com.google.android.material.textfield.TextInputEditText;

public class CardActivity extends AppCompatActivity implements InitView {

    private TextView textTitle ;
    private TextView textDescrip;
    private TextInputEditText textEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        textTitle = findViewById(R.id.textView_title);
        textDescrip = findViewById(R.id.textView_description);
        textEdit = findViewById(R.id.editText);
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
            textEdit.setText(textEdit.getEditableText());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}