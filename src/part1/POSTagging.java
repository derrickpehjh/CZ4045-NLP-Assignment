package part1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import opennlp.tools.postag.*;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import org.json.simple.JSONObject;

public class POSTagging {

	private static String posModelLocation = "C:\\Users\\Derrick\\OneDrive - Nanyang Technological University\\NTU\\Modules\\Y3S1\\CZ4045 Natural Langauge Processing\\Project\\NLP Assignment\\libraries\\en-pos-maxent.bin";

	private static void posTagging() throws Exception {
		Random rand = new Random();
		Map<String, Integer> idList = new HashMap<String, Integer>();
		Iterator<?> i = Json.readJSON();
		int n = rand.nextInt(100); ////////// start from here
		System.out.println("Running POS Tagger");

		while (i.hasNext()) {

			JSONObject obj = (JSONObject) i.next();
			String id = (String) obj.get("reviewText");
			posModel(id);
		}
		System.out.println("POS Tagging completed");
	}

	private static void posModel(String sentence) throws IOException {
		// Loading Parts of speech-maxent model
		InputStream inputStream = new FileInputStream(posModelLocation);
		POSModel model = new POSModel(inputStream);
		POSTaggerME tagger = new POSTaggerME(model);
		// Tokenizing the sentence using WhitespaceTokenizer class
		WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
		String[] tokens = whitespaceTokenizer.tokenize(sentence);

		// Generating tags
		String[] tags = tagger.tag(tokens);

		// Instantiating the POSSample class
		POSSample sample = new POSSample(tokens, tags);
		System.out.println(sample.toString());
	}

	public static void main(String[] args) throws Exception {
		posTagging();
	}

}
