package com.example.appsocialnetwork.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardData implements Parcelable {
    private String title; // заголовок
    private String description; // описание
    private int picture; // изображение
    private boolean like; // флажок
    private String editText; //редактируемый текст
    private Date date;

    public CardData(String title, String description, int picture, boolean like, String editText,Date date) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
        this.editText = editText;
        this.date = date;

    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        like = in.readByte() != 0;
        editText = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeString(editText);
        dest.writeLong(date.getTime());
    }

    public Date getDate() {
        return date;
    }
}
