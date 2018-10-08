package part1;

import org.json.simple.JSONObject;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TokenizeAndStemming {
	// self defined stopwords. Can consider using ready made corpus online
	private static String[] stopwords = { ".", "the", "I", "and", "it", "to", "a", "is", "'", "on", "of", "for", "my",
			"thi", "that", "in", "with", ",", "this", "you", "have", "The", "but", "-", "not", "t", "s", "was", "It",
			"as", "one", "so", ")", "are", "(", "be", "like", "or", "can", "very", "your", "This", "!", "at", "from",
			"just", "when", "if", "has", "will", "would", "they", "get", "an", "had", "me", "2", "3", "4", "than", ":",
			";", "5", "/", "about", "&", "use", "out", "all", "well", "up", "no", "only", "\"", "really", "off",
			"other", "which", "i", "more", "them", "because", "does", "much", "do", "also", "little", "these", "#",
			"there", "any", "ve", "back", "don", "work", "works", "m", "1", "some", "what", "...", "too", "its", "us",
			"need", "make" };

	private static Set<String> addWordsToStopList() {
		Set<String> set = new HashSet<>();
		for (String item : stopwords) {
			set.add(item);
		}
		return set;
	}

	private static void countTokens(String idType, Boolean stem) throws Exception {
		Map<String, Integer> tokenList = new HashMap<String, Integer>();
		Map<Integer, Integer> idList = new HashMap<Integer, Integer>();
		int tokenCounter = 0, length = 0;
		ArrayList<String> tokens = new ArrayList<String>();
		Iterator<?> i = Json.readJSON();

		System.out.println("Running Token Counter");
		while (i.hasNext()) {

			JSONObject obj = (JSONObject) i.next();
			String reviewText = (String) obj.get(idType);
			if (stem == false) {
				length = tokenizeCount(reviewText);
				tokens = allUniqueToken(reviewText);
			} else {
				length = tokenizeCountWithStem(reviewText);
				tokens = allUniqueStemmedToken(reviewText);
			}

			if (!idList.containsKey(length)) {
				idList.put(length, 1);
			} else {
				int count = idList.get(length);
				count++;
				idList.put(length, count);
			}

			for (int j = 0; j < tokens.size(); j++)
				if (!tokenList.containsKey(tokens.get(j))) {
					tokenList.put(tokens.get(j), 1);
					tokenCounter++;
				} else {
					int count = tokenList.get(tokens.get(j));
					count++;
					tokenList.put(tokens.get(j), count);
				}
		}
		System.out.println("x = number of tokens in a sentence" + '\n' + "y = number of sentences with that x tokens"
				+ '\n' + "Number of unique token count: " + idList.size());
		HashMapSort.sortDescending(idList, "Tokenization");

		System.out.println();

		System.out.println("Number of Unique Tokens: " + tokenCounter);
		HashMapSort.sortDescending(tokenList, "WordToken");
	}

	private static int tokenizeCount(String sentence) throws Exception {

		// Instantiating SimpleTokenizer class
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

		// Tokenizing the given sentence
		String tokens[] = simpleTokenizer.tokenize(sentence);
		ArrayList<String> tokensList = new ArrayList<String>(Arrays.asList(tokens));
		// tokensList.removeAll(addWordsToStopList());
		return tokensList.size();
	}

	private static ArrayList<String> allUniqueToken(String sentence) throws Exception {

		// Instantiating SimpleTokenizer class
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

		// Tokenizing the given sentence
		String tokens[] = simpleTokenizer.tokenize(sentence);
		ArrayList<String> tokensList = new ArrayList<String>(Arrays.asList(tokens));
		tokensList.removeAll(addWordsToStopList());
		return tokensList;
	}

	private static int tokenizeCountWithStem(String sentence) throws Exception {

		// Instantiating SimpleTokenizer class
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

		// Tokenizing the given sentence
		String tokens[] = simpleTokenizer.tokenize(sentence);
		ArrayList<String> tokensList = new ArrayList<String>(Arrays.asList(tokens));
		// tokensList.removeAll(addWordsToStopList());
		PorterStemmer stemmer = new PorterStemmer();

		for (int i = 0; i < tokensList.size(); i++) {
			tokensList.set(i, stemmer.stem(tokensList.get(i)));
		}

		return tokensList.size();
	}

	private static ArrayList<String> allUniqueStemmedToken(String sentence) throws Exception {

		// Instantiating SimpleTokenizer class
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

		// Tokenizing the given sentence
		String tokens[] = simpleTokenizer.tokenize(sentence);
		ArrayList<String> tokensList = new ArrayList<String>(Arrays.asList(tokens));
		tokensList.removeAll(addWordsToStopList());
		PorterStemmer stemmer = new PorterStemmer();

		for (int i = 0; i < tokensList.size(); i++) {
			tokensList.set(i, stemmer.stem(tokensList.get(i)));
		}

		return tokensList;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 2nd parameter : true = stemming , false = no stemming
		 */
		 countTokens("reviewText",false);
//		countTokens("reviewText", true);
	}
}
