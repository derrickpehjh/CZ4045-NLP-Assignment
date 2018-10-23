package part3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import part1.HashMapSort;
import part1.Json;

public class Sentiment {

	private static void extractSentiment() throws IOException, ParseException {
		InputStream tokenModelIn = null;
		InputStream posModelIn = null;

		tokenModelIn = new FileInputStream("libraries\\en-token.bin");
		TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
		Tokenizer tokenizer = new TokenizerME(tokenModel);

		posModelIn = new FileInputStream("libraries\\en-pos-maxent.bin");
		POSModel posModel = new POSModel(posModelIn);
		POSTaggerME posTagger = new POSTaggerME(posModel);

		Map<String, Integer> idposList = new HashMap<String, Integer>();
		Map<String, Integer> idnegList = new HashMap<String, Integer>();
		Iterator<?> it = Json.readJSON();

		System.out.println("Running sentiment analysis - Retreiving top 20 polarity of words");
		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			String sentence = (String) obj.get("summary");
			String tokens[] = tokenizer.tokenize(sentence); // Tokenize the sentence

			String tags[] = posTagger.tag(tokens); // Tagger tagging the tokens
			ArrayList<String> tokensList = new ArrayList<String>(Arrays.asList(tokens));
			tokensList.replaceAll(String::toLowerCase);

			// using score range to retrieve positive (4-5) and negative (0-3) sentiments
			for (int j = 0; j < tokensList.size(); j++) {
				if (tags[j].equals("JJ")|| tags[j].equals("JJR") || tags[j].equals("JJS")) {

					if ((double) obj.get("overall") > 3) {
						if (!idposList.containsKey(tokensList.get(j))) {
							idposList.put(tokensList.get(j), 1);
						} else {
							int count = idposList.get(tokensList.get(j));
							count++;
							idposList.put(tokensList.get(j), count);
						}

					} else {
						if (!idnegList.containsKey(tokensList.get(j))) {
							idnegList.put(tokensList.get(j), 1);
						} else {
							int count = idnegList.get(tokensList.get(j));
							count++;
							idnegList.put(tokensList.get(j), count);
						}
					}
				}
			}
			// System.out.println("Positive: " + idposList.size() + " Negative: " +
			// idnegList.size());
		}

		// removing duplicate keys in both list to prevent ambiguity
		ArrayList<String> common = new ArrayList<String>();
		for (String a : idposList.keySet()) {
			if (idnegList.containsKey(a)) {
				common.add(a);
			}
		}
		for (int i = 0; i < common.size(); i++) {
			if (idnegList.containsKey(common.get(i)))
				idnegList.remove(common.get(i));
			if (idposList.containsKey(common.get(i)))
				idposList.remove(common.get(i));
		}

		System.out.println("Positive List: ");
		HashMapSort.sortDescending(idposList, "sentiment");
		System.out.println('\n' + "Negative List: ");
		HashMapSort.sortDescending(idnegList, "sentiment");

	}

	public static void main(String[] args) throws IOException, ParseException {
		/*
		 * extracted sentiment from summary instead of reviewText as reviewText contains a lot of noise
		 * removed words that appear in both positive and negative list to reduce ambiguity 
		 */
		extractSentiment();
	}
}
