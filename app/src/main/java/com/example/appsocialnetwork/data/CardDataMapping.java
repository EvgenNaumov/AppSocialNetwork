package com.example.appsocialnetwork.data;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {
	public static class Fields{
		public final static String PICTURE = "picture";
		public final static String DATE = "date";
		public final static String TITLE = "title";
		public final static String DESCRIPTION = "description";
		public final static String LIKE = "like";
		public final static String EDITTEXT = "edittext";
	}

	public static CardData toCardData(String id,Map<String,Object> doc){
		long indexPic = (long) doc.get(Fields.PICTURE);
		Timestamp timestamp = (Timestamp) doc.get(Fields.DATE);
		CardData cardData = new CardData(
				(String) doc.get(Fields.TITLE),
				(String) doc.get(Fields.DESCRIPTION),
				PictureIndexConverter.getPictureByIndex((int) indexPic),
				(boolean) doc.get(Fields.LIKE),
				(String) doc.get(Fields.EDITTEXT),
				timestamp.toDate());


		cardData.setId(id);
		return cardData;
	}
	public static Object toDocument(@NonNull CardData cardData){
		Map<String, Object> result = new HashMap<>();
		result.put(Fields.TITLE, cardData.getTitle());
		result.put(Fields.DESCRIPTION,cardData.getDescription());
		result.put(Fields.PICTURE,PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
		result.put(Fields.LIKE,cardData.isLike());
		result.put(Fields.EDITTEXT,cardData.getEditText());
		result.put(Fields.DATE,cardData.getDate());
		return result;
	}
}
