package com.example.appsocialnetwork.observe;

import com.example.appsocialnetwork.data.CardData;

import java.util.ArrayList;
import java.util.List;

public class Publisher{
    private List<Observer> observers;
    public Publisher(){
        observers = new ArrayList<>();
    }
    public void subscibe(Observer observer){
        observers.add(observer);
    }
    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }
    public void notifySingle(CardData cardData){
        for (Observer observer: observers
             ) {
           observer.updateCardData(cardData);
           unsubscribe(observer);
        }
    }
}
