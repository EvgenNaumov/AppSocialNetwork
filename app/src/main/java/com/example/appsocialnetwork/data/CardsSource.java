package com.example.appsocialnetwork.data;

public interface CardsSource {
    CardsSource Init(CardsSourceResponse cardsSourceResponse);
    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();
}
