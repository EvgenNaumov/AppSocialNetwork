package com.example.appsocialnetwork.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsocialnetwork.CardActivity;
import com.example.appsocialnetwork.R;
import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.data.CardsSource;
import com.example.appsocialnetwork.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {
    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    public interface onStartIntentListener{
        public void startIntentEvent(String nameData);
    }

    onStartIntentListener startIntentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
//        String[] data = getResources().getStringArray(R.array.titles);
        CardsSource data = new CardsSourceImpl(getResources()).init();
        initRecycleView(recyclerView,data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            startIntentListener = (onStartIntentListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement onStartIntentListener");
        }
    }

    private void initRecycleView(RecyclerView recyclerView, CardsSource data) {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        SocialNetworkAdapter adapter = new SocialNetworkAdapter(data);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
                startIntentListener.startIntentEvent("CardActivity");

            }
        });
    }
}
