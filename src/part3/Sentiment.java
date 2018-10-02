package part3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			String sentence = (String) obj.get("summary");
			String tokens[] = tokenizer.tokenize(sentence); // Tokenize the sentence
			String tags[] = posTagger.tag(tokens); // Tagger tagging the tokens

			for (int j = 0; j < tokens.length; j++) {
				if (tags[j].equals("JJ"))
//					 System.out.print(tokens[j] + " (" + tags[j] + ") ");
					if ((double) obj.get("overall") > 2.5) {
						if (!idposList.containsKey(tokens[j])) {
							idposList.put(tokens[j], 1);
						} else {
							int count = idposList.get(tokens[j]);
							count++;
							idposList.put(tokens[j], count);
						}
					} else {
						if (!idnegList.containsKey(tokens[j])) {
							idnegList.put(tokens[j], 1);
						} else {
							int count = idnegList.get(tokens[j]);
							count++;
							idnegList.put(tokens[j], count);
						}
					}
			}
			System.out.println("Positive: " + idposList.size() + "  Negative: " + idnegList.size());
		}
		System.out.println("Positive List: " );
		HashMapSort.sortDescending(idposList,"sentiment");
		System.out.println('\n' + "Negative List: " );
		HashMapSort.sortDescending(idnegList,"sentiment");

	}

	public static void main(String[] args) throws IOException, ParseException {
		extractSentiment();
	}
}
