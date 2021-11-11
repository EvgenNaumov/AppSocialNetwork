package com.example.appsocialnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.data.CardsSource;
import com.example.appsocialnetwork.data.CardsSourceImpl;
import com.example.appsocialnetwork.ui.SocialNetworkAdapter;
import com.example.appsocialnetwork.ui.SocialNetworkFragment;

public class MainActivity extends AppCompatActivity implements SocialNetworkFragment.onStartIntentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        addFragment(SocialNetworkFragment.newInstance());
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void startIntentEvent(String cardData, CardData currCardData) {
        Intent intent = new Intent(this, new CardActivity().getClass());
//        intent.putExtra("title", view.findViewById(R.id.title).toString());
//        intent.putExtra("description", view.findViewById(R.id.description).toString());
        intent.putExtra("cardData",currCardData);
        startActivity(intent);
    }
}