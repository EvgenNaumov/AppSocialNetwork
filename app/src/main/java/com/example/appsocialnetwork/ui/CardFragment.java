package com.example.appsocialnetwork.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appsocialnetwork.MainActivity;
import com.example.appsocialnetwork.R;
import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {
    private static final String ARG_CARD_DATA = "Param_CardData";

    private CardData cardData; // Данные по карточке
    private Publisher publisher; //Паблишер, с его помощью обмениваемся данными

    private TextInputEditText title;
    private TextInputEditText description;
    private  TextInputEditText editText;
    private DatePicker datePicker;

    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA,cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDesription);
        datePicker = view.findViewById(R.id.inputDate);
        editText = view.findViewById(R.id.inputEditText);
    }
    // Здесь соберём данные из views


    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    // Здесь передадим данные в паблишер


    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(cardData);
    }

    private CardData collectCardData() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        String editText = this.editText.getText()!=null? this.editText.getText().toString():"";

        Date date = getDateFromDatePicker();
        int picture;
        boolean like;
        if (cardData != null){
            picture = cardData.getPicture();
            like = cardData.isLike();
        } else {
            picture = R.drawable.nature1;
            like = false;
        }

        return new CardData(title,description,picture,like,editText,date);
    }
    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH,this.datePicker.getDayOfMonth());

        return cal.getTime();
    }

    private void populateView() {
        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        editText.setText(cardData.getEditText());
        iniDatePicker(cardData.getDate());
    }

    private void iniDatePicker(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.datePicker.init(cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
            null);
    }


}
