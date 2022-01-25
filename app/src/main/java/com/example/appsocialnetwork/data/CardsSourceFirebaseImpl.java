package com.example.appsocialnetwork.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements  CardsSource{

    public static final String CARD_COLLECTION = "card";
    public static final String TAG = "CardsSourceFirebaseImpl";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARD_COLLECTION);
    private List<CardData> cardsData = new ArrayList<>();

    @Override
    public CardsSource Init(CardsSourceResponse cardsSourceResponse) {
        collection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()
                            ) {
                                Log.d(TAG, document.getId() + "=" + task.getResult());
                                Map<String,Object> doc = document.getData();
                                CardData cardData = CardDataMapping.toCardData(document.getId(), doc);
                                cardsData.add(cardData);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    };

                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        collection.document(cardsData.get(position).getId())
                .set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addCardData(final CardData cardData) {
        collection.add(CardDataMapping.toDocument(cardData)).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        cardData.setId(documentReference.getId());
                    }
                });
    }

    @Override
    public void clearCardData() {
        for (CardData cardData:cardsData
             ) {
            collection.document(cardData.getId()).delete();
        }
        cardsData.clear();
    }
}
