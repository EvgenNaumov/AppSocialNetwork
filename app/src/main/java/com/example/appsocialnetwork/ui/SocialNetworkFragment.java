package com.example.appsocialnetwork.ui;

import android.app.Activity;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsocialnetwork.CardActivity;
import com.example.appsocialnetwork.R;
import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.data.CardsSource;
import com.example.appsocialnetwork.data.CardsSourceImpl;

public class SocialNetworkFragment extends Fragment {
    private CardsSource data;
    private RecyclerView recyclerView;
    private SocialNetworkAdapter adapter;
    onStartIntentListener startIntentListener;
    private static final int MY_DEFAULT_DURATION = 1000;

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    public interface onStartIntentListener{
        void startIntentEvent(String nameData, CardData cardData);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__list, container, false);

        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        data = new CardsSourceImpl(getResources()).init();
        initRecycleView();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                data.addCardData(new CardData("?????????????????? " + data.size(),
                        "???????????????? ",
                        R.drawable.ic_launcher_newcard,
                        false,"?????????? ??????????????" ));
                adapter.notifyItemInserted(data.size()-1);
                recyclerView.smoothScrollToPosition(data.size()-1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycleView() {
        // ?????? ?????????????????? ???????????? ?????? ?????????????????? ???????????????????????????????????? ??????????????
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // ?????????????????? ??????????????
        adapter = new SocialNetworkAdapter(data,this);
        recyclerView.setAdapter(adapter);

        // ?????????????? ?????????????????????? ????????????????
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        adapter.setItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),String.format("?????????????? - %d", position), Toast.LENGTH_SHORT).show();
                final CardData currentCardData = data.getCardData(position);
                startIntentListener.startIntentEvent("CardActivity",  currentCardData);

            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()){
            case R.id.action_update:
                data.updateCardData(position, new CardData("???????? " + position, data.getCardData(position).getDescription(), data.getCardData(position).getPicture(),false,""));
                adapter.notifyItemChanged(position);
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
