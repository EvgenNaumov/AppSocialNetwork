package com.example.appsocialnetwork.ui;

import android.icu.text.UFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsocialnetwork.R;
import com.example.appsocialnetwork.data.CardData;
import com.example.appsocialnetwork.data.CardsSource;

import java.text.SimpleDateFormat;

import javax.sql.DataSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {
    private final static String TAG = "SocialNetworkAdapter";
    private CardsSource dataSource;
    private OnItemClickListener itemClickListener;
    private  Fragment fragment;
    private  int menuPosition;

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public SocialNetworkAdapter(Fragment fragment) {
        //Фрагмент передаётся для вызова метода registerForContextMenu(). Повесим контекстное меню на
        //весь макет CardView.
        this.fragment = fragment;
    }

    public void setDataSource(CardsSource dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SocialNetworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull SocialNetworkAdapter.ViewHolder holder, int position) {
// Получить элемент из источника данных (БД, интернет...)
// Вынести на экран, используя ViewHolder
        //holder.getTextView().setText(CardsSource dataSource);
        holder.setData(dataSource.getCardData(position));
        Log.d(TAG,"onBindViewHolder");
    }

    public int getMenuPosition(){
        return menuPosition;
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder{
        //private TextView textView;
        //поля макета
        private TextView title;
        private TextView description;
        private AppCompatImageView image;
        private CheckBox like;
        private TextView date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
//            textView = (TextView) itemView;
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.ImageView);
            like = itemView.findViewById(R.id.like);
            date = itemView.findViewById(R.id.date);

            registerContextMenu(itemView);
            
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener!=null){

                        itemClickListener.onItemClick(view,getAdapterPosition());
                    }
                }
            });

            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                   itemView.showContextMenu(10,10);
                   return true;
                }
            });
        }

        //вешаем контекстное меню
        private void registerContextMenu(View itemView) {
            if (fragment!=null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(CardData cardData){
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            like.setChecked(cardData.isLike());
            image.setImageResource(cardData.getPicture());
            date.setText(new SimpleDateFormat("dd-MM-yy").format(cardData.getDate()));
        }
    }



}
