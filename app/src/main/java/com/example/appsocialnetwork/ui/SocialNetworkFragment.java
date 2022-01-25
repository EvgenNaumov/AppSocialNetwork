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
import com.example.appsocialnetwork.FragmentEditCard;
import com.example.appsocialnetwork.MainActivity;
import com.example.appsocialnetwork.Navigation;
import com.example.appsocialnetwork.R;
import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.data.CardsSource;
import com.example.appsocialnetwork.data.CardsSourceImpl;
import com.example.appsocialnetwork.data.CardsSourceResponse;
import com.example.appsocialnetwork.observe.Observer;
import com.example.appsocialnetwork.observe.Publisher;

import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class SocialNetworkFragment extends Fragment {
    private CardsSource data;
    private RecyclerView recyclerView;
    private SocialNetworkAdapter adapter;
    onStartIntentListener startIntentListener;
    private static final int MY_DEFAULT_DURATION = 1000;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;

    public static SocialNetworkFragment newInstance() {
        return new SocialNetworkFragment();
    }

    public interface onStartIntentListener {
        void startIntentEvent(String nameData, CardData cardData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        initRecycleView();
        data = new CardsSourceImpl(getResources()).Init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();

        try {
            startIntentListener = (onStartIntentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onStartIntentListener");
        }
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add:
//                data.addCardData(new CardData("Заголовок " + data.size(),
//                        "Описание ",
//                        R.drawable.ic_launcher_newcard,
//                        false, "Новая заметка"));
//                adapter.notifyItemInserted(data.size() - 1);
//                recyclerView.smoothScrollToPosition(data.size() - 1);
//                navigation.addFragment(CardFragment.newInstance(), true);
//                publisher.subscibe(new Observer() {
//                    @Override
//                    public void updateCardData(CardData cardData) {
//                        data.addCardData(cardData);
//                        adapter.notifyItemInserted(data.size() - 1);
//                        // это сигнал, чтобы вызванный метод onCreateView
//                        // перепрыгнул на конец списка
//                        moveToLastPosition = true;
//                    }
//                });
//                return true;
//            case R.id.action_clear:
//                data.clearCardData();
//                adapter.notifyDataSetChanged();
//                return true;
//        }
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private boolean onItemSelected(int itemId) {
        switch (itemId){
            case R.id.action_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscibe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на конец списка
                        moveToLastPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition)
                ), true);
                publisher.subscibe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }

    private void initRecycleView() {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        adapter = new SocialNetworkAdapter(this);
        adapter.setDataSource(data);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(MY_DEFAULT_DURATION);
//        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        //загруженная библиотека с гита 'jp.wasabeef:recyclerview-animators:4.0.2'
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(data.size() - 1);
            moveToLastPosition = false;
        }

        adapter.setItemClickListener(new SocialNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
                final CardData currentCardData = data.getCardData(position);
//                startIntentListener.startIntentEvent("CardActivity", currentCardData);
                navigation.addFragment(FragmentEditCard.newInstance(currentCardData),true);
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
//        final int position = adapter.getMenuPosition();
//        switch (item.getItemId()) {
//            case R.id.action_update:
//                data.updateCardData(position, new CardData("Кадр " + position,
//                        data.getCardData(position).getDescription(),
//                        data.getCardData(position).getPicture(),
//                        data.getCardData(position).isLike(),
//                        data.getCardData(position).getEditText(),
//                        data.getCardData(position).getDate()
//                ));
//                adapter.notifyItemChanged(position);
//
//                navigation.addFragment(CardFragment.newInstance(data.getCardData(position)),true);
//                publisher.subscibe(new Observer() {
//                    @Override
//                    public void updateCardData(CardData cardData) {
//                        data.updateCardData(position,cardData);
//                        adapter.notifyItemChanged(position);
//                    }
//                });
//                return true;
//            case R.id.action_delete:
//                data.deleteCardData(position);
//                adapter.notifyItemRemoved(position);
//                return true;
//        }
        return super.onContextItemSelected(item) || onItemSelected(item.getItemId());
    }
}
