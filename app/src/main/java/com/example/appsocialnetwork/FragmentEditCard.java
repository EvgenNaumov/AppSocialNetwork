package com.example.appsocialnetwork;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditCard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText title;
    private TextInputEditText description;
    private TextInputEditText edittext;
    private TextInputEditText textDateCard;
    private CardData cardData;
    private DatePicker datePicker;
    private AppCompatTextView textdate;

    private Publisher publisher;

    private static final String ARG_CARD_Data = "Param_CARD_DATA";


    public FragmentEditCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment_AddCard.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditCard newInstance(CardData cardData) {
        FragmentEditCard fragment = new FragmentEditCard();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_Data,cardData);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_Data);
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcard, container, false);
        initView(view);
        fillView();
        // Inflate the layout for this fragment
        return view;

    }

    private void fillView() {
        if (cardData!=null){

            if (cardData.getTitle()!=null){
                title.setText(cardData.getTitle().toString());
            }
            if (cardData.getDescription()!=null) {
                description.setText(cardData.getDescription().toString());
            }
//            if (cardData.getEditText()!=null){
//                edittext.setText(cardData.getEditText());
//            }
            if (cardData.getDate()!=null) {
                textDateCard.setText(cardData.getDate().toString());
            }
            else {
                String currentTime  = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                textDateCard.setText(currentTime.toString());

            }
            }

        }


    private void initView(View view) {
       title = view.findViewById(R.id.inputTitle);
       description = view.findViewById(R.id.inputDesription);
       edittext = view.findViewById(R.id.inputEditText);
       datePicker = view.findViewById(R.id.inputDate);
        textDateCard = view.findViewById(R.id.text_date_card);
    }

        // Получение даты из DatePicker
        private Date getDateFromDatePicker() {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.YEAR,this.datePicker.getYear());
            cal.set(Calendar.MONTH, this.datePicker.getMonth());
            cal.set(Calendar.DAY_OF_MONTH,this.datePicker.getDayOfMonth());

            return cal.getTime();
        }

        @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}