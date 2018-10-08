package part1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class PosTagging {

	private static void extractSentences() throws IOException, ParseException {
		Map<Integer, String> idList = new HashMap<Integer, String>();
		Iterator<?> it = Json.readJSON();
		int count = 0;

		Random rand = new Random(); // can set fixed seed if needed
		int[] randomNumbers = new int[5];

		for (int i = 0; i < 5; i++) {
			// int n = rand.nextInt(50); // for the sample dataset
			int n = rand.nextInt(190918); // for full dataset
			randomNumbers[i] = n;
			// System.out.println(randomNumbers[i]);
		}

		while (it.hasNext()) {
			JSONObject obj = (JSONObject) it.next();
			String sentence = (String) obj.get("reviewText");

			idList.put(count, sentence);
			count++;
		}

		List<String> sentences = new ArrayList<String>();
		Iterator<Map.Entry<Integer, String>> entries = idList.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Integer, String> entry = entries.next();

			for (int i = 0; i < 5; i++) {
				if (entry.getKey() == randomNumbers[i]) {
					sentences.add(entry.getValue());
				}
			}
		}

		// To check for which reviewText got chosen
		// for (int i = 0; i < sentences.size(); i++) {
		// System.out.println(sentences.get(i));
		// }

		// always start with a model, a model is learned from training data
		InputStream inputStream = new FileInputStream("libraries\\en-sent.bin");
		SentenceModel model = new SentenceModel(inputStream);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String[] sent = new String[5];

		for (int i = 0; i < sentences.size(); i++) {
			String paragraph = sentences.get(i);
			String s[] = sdetector.sentDetect(paragraph);
			sent[i] = s[0]; // only keeping the first sentence from each randomly chosen reviewText.
		}

		inputStream.close();

		// printing out the randomly chosen 5 sentences for POS tagging
		System.out.println("");
		for (int i = 0; i < sent.length; i++) {
			System.out.println(sent[i]);
		}
		System.out.println("");

		InputStream tokenModelIn = null;
		InputStream posModelIn = null;

		try {
			// Setting up tokenizer
			tokenModelIn = new FileInputStream("libraries\\en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
			Tokenizer tokenizer = new TokenizerME(tokenModel);

			// Setting up Parts-Of-Speech Tagging
			// reading parts-of-speech model to a stream
			posModelIn = new FileInputStream("libraries\\en-pos-maxent.bin");
			// loading the parts-of-speech model from stream
			POSModel posModel = new POSModel(posModelIn);
			// initializing the parts-of-speech tagger with model
			POSTaggerME posTagger = new POSTaggerME(posModel);

			for (int i = 0; i < sent.length; i++) {
				String tokens[] = tokenizer.tokenize(sent[i]); // Tokenize the sentence
				String tags[] = posTagger.tag(tokens); // Tagger tagging the tokens

				for (int j = 0; j < tokens.length; j++) {
					System.out.print(tokens[j] + " (" + tags[j] + ") ");
				}
				System.out.println("");
			}

		} catch (IOException e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		} finally {
			if (tokenModelIn != null) {
				try {
					tokenModelIn.close();
				} catch (IOException e) {
				}
			}
			if (posModelIn != null) {
				try {
					posModelIn.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		extractSentences();
	}
}
