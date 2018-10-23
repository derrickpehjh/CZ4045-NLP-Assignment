package part1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Json {

	static String fileLocation = "dataset\\CellPhoneReview.json";
	static String formattedfileLocation = "dataset\\formattedCellPhoneReivew.json";
	static String formattedfileLocation2 = "dataset\\formattedCellPhoneReivew2.json";
	public static void formatJSON() throws IOException {

		FileInputStream fstream = new FileInputStream(fileLocation);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		BufferedWriter bw = new BufferedWriter(
				new FileWriter(new File("dataset\\formattedCellPhoneReivew.json"), true));
		String next, line = br.readLine();
		for (boolean first = true, last = (line == null); !last; first = false, line = next) {
			last = ((next = br.readLine()) == null);

			if (first) {
				bw.write("[" + line);
				bw.newLine();
			} else if (last) {
				bw.write(line + "]");
			} else {
				bw.write(line + ",");
				bw.newLine();
			}
		}

		br.close();
		bw.close();
		System.out.println("Format complete");
	}

	public static Iterator<?> readJSON() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(formattedfileLocation);
		JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
		Iterator<?> i = jsonArray.iterator();
		return i;
	}
	public static Iterator<?> readJSON2() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(formattedfileLocation2);
		JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
		Iterator<?> i = jsonArray.iterator();
		return i;
	}
	public static void main(String[] args) throws IOException {
		// formatJSON();
	}

}
