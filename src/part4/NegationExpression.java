package part4;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import part1.Json;

public class NegationExpression {

	private static void findNegationEx() throws IOException, ParseException {

		Iterator<?> i = Json.readJSON();
		int sentCount = 0;

		while (i.hasNext()) {
			ArrayList<String> negList = new ArrayList<String>();
			sentCount++;

			JSONObject obj = (JSONObject) i.next();
			String str = (String) obj.get("reviewText");

			// put regex here
			String pattern = "(\\w*not\\b)|(\\w*n't\\b)|(\\bnon-?\\w+)|(\\bnever\\b)|(\\bno\\b)|(\\bnothing\\b)|(\\bnobody\\b)|(\\bnowhere\\b)|(\\bnope\\b)";

			Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);

			if (m.find()) {
				m.reset();
				System.out.println("Match in Sentence " + sentCount + ": " + (str.substring(0, str.length() / 1)));
				System.out.print("Negation Words List for Sentence " + sentCount + ": ");
				while (m.find()) {
					if (!negList.contains(m.group().toLowerCase()))
						negList.add(m.group(0).toLowerCase());
				}
				for (String word : negList)
					System.out.print(word + " ");
				System.out.println('\n');
			}
		}

	}

	public static void main(String[] args) throws IOException, ParseException {
		findNegationEx();

	}

}
