package part1;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONObject;

public class SentenceSegmentation {

	static String openNLPBinLocation = "C:\\Users\\Derrick\\OneDrive - Nanyang Technological University\\NTU\\Modules\\Y3S1\\CZ4045 Natural Langauge Processing\\Project\\NLP Assignment\\libraries\\en-sent.bin";

	private static void countSentences(String idType) throws Exception {
		
		Random rand = new Random();
		int small = 0, big = 0, counter = 0, length = 0;
		
		Map <Integer,Integer> idList= new HashMap <Integer,Integer>();
		Iterator<?> i = Json.readJSON();
		System.out.println("Running Sentence Counter");
		while (i.hasNext()) {

			JSONObject obj = (JSONObject) i.next();
			String reviewText = (String) obj.get(idType);
			length = SentenceSegmentation.sentenceCount(reviewText);
			if ((length > 10 || length <= 10) && rand.nextInt(10) >= 8) {
				if (length == 263) {
					SentenceSegmentation.sentenceCountSampling(reviewText);
				}
				if (length > 10 && big < 3) {
					big++;
					SentenceSegmentation.sentenceCountSampling(reviewText);
				} else if (length <= 10 && small < 2) {
					small++;
					SentenceSegmentation.sentenceCountSampling(reviewText);
				}
			}
			if (!idList.containsKey(length)) {
				counter++;
				idList.put(length, 1);
			} else {
				int count = idList.get(length);
				count++;
				idList.put(length, count);
			}

		}
		System.out.println("Number of Unique Sentence Count: " + counter);
		HashMapSort.sortDescending(idList,"SentenceSegmentation");
	}
	

	private static void sentenceCountSampling(String sentence) throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("dataset\\sentenceCountSample.json"), true));
		bw.newLine();
		bw.newLine();
		// Loading sentence detector model
		InputStream inputStream = new FileInputStream(openNLPBinLocation);
		SentenceModel model = new SentenceModel(inputStream);

		// Instantiating the SentenceDetectorME class
		SentenceDetectorME detector = new SentenceDetectorME(model);
		bw.write("Actual Review: " + sentence);
		bw.newLine();
		// Detecting the sentence
		String sentences[] = detector.sentDetect(sentence);
		bw.write("Number Of sentence: " + sentences.length);
		bw.newLine();
		bw.close();
	}

	private static int sentenceCount(String sentence) throws Exception {
		InputStream inputStream = new FileInputStream(openNLPBinLocation);
		SentenceModel model = new SentenceModel(inputStream);

		// Instantiating the SentenceDetectorME class
		SentenceDetectorME detector = new SentenceDetectorME(model);

		// Detecting the sentence
		String sentences[] = detector.sentDetect(sentence);
		return sentences.length;
	}

	public static void main(String[] args) throws Exception {
		countSentences("reviewText");
	}
}
