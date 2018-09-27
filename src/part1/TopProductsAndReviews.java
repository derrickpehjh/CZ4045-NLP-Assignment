package part1;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import part1.Json;
import part1.HashMapSort;

public class TopProductsAndReviews {

	private static void countReviewerorProductID(String idType) throws IOException, ParseException {

		Map<String, Integer> idList = new HashMap<String, Integer>();
		int counter = 0;
		Iterator<?> i = Json.readJSON();
		
		if(idType == "reviewerID")
		System.out.println("Running Review Counter");
		else if(idType == "asin")
			System.out.println("Running Product Counter");
		else
			System.out.println("Running " +  idType +  " Counter");
		
		while (i.hasNext()) {

			JSONObject obj = (JSONObject) i.next();
			String id = (String) obj.get(idType);
			if (!idList.containsKey(id)) {
				counter++;
				idList.put(id, 1);
			} else {
				int count = idList.get(id);
				count++;
				idList.put(id, count);
			}
		}
		System.out.println("\n" +"Number of unique " + idType + ": " + counter );
		HashMapSort.sortDescending(idList,"TopProductsAndReviews");
	}

	

	public static void main(String[] args) throws IOException, ParseException {
		/*
		* reviewerID - reviewerID
		* asin - ProductID
		*/
		countReviewerorProductID("reviewerID");
		countReviewerorProductID("asin");

	}
}
