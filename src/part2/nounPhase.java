package part2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import part1.HashMapSort;
import part1.Json;
import part1.SentenceSegmentation;
import part1.TopProductsAndReviews;

public class nounPhase {
	static String openNLPBinLocation = "libraries\\en-parser-chunking.bin";
	static Map<String, Integer> nounPhrases = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases1 = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases2 = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases3 = new HashMap<String, Integer>();
	static List<String> top10List=new ArrayList<String>();
	static List<String> chosen3=new ArrayList<String>();
	static String JSONFile = "dataset\\nounPhaseSummarizer1.json";
	static String JSONFile2 = "dataset\\nounPhaseSummarizer.json";
	static int counter=0;
	static Map<String, Integer> JSONnounPhrases = new HashMap<String, Integer>();
	static boolean enable;
	
	public static void main(String[] args) throws Exception {
		top10List=TopProductsAndReviews.retrieveReviewerorProductList("asin");
		choose3();
//		nounPhraseSummarizer();
		RetrieveJson();
		RemoveDuplicates();
		print();		
	}
	public static void nounPhraseSummarizer(int startIndex,int endIndex) throws Exception, ParseException//Noun Phrase for specific index
	{
		int count=0,tracker=startIndex;
		Iterator<?> i = Json.readJSON();
		while (i.hasNext()) {
			count++;
			System.out.println("Loop "+count);
			JSONObject obj = (JSONObject) i.next();
			String productID=(String) obj.get("asin");
			String review = (String) obj.get("reviewText");
			if(count>startIndex && count<=endIndex)
			{
				String[] sentences=SentenceSegmentation.Getsentences(review);
				for(String sentence:sentences)
				{
					if(chosen3.contains(productID))
					{
						int index=chosen3.indexOf(productID);
						index++;
						extracter(sentence,index);
					}
					else
					{
						extracter(sentence);
					}	
				}
				if(count%500==0||!i.hasNext())
				{
					writeToJson(tracker,count);
					tracker=count;
				}
			}
			if(count==endIndex)break;
		}
		System.out.println("Number of Review: "+count);
		System.out.println("\n" +"Number of unique Phrases: " + counter );			
	}
	public static void nounPhraseSummarizer() throws Exception, ParseException//General Noun Phrase
	{
		int count=0,tracker=0;
		Iterator<?> i = Json.readJSON();
		while (i.hasNext()) {
			count++;
			System.out.println("Loop "+count);
			JSONObject obj = (JSONObject) i.next();
			String productID=(String) obj.get("asin");
			String review = (String) obj.get("reviewText");			
			String[] sentences=SentenceSegmentation.Getsentences(review);
				for(String sentence:sentences)
				{
					if(chosen3.contains(productID))
					{
						int index=chosen3.indexOf(productID);
						index++;
						extracter(sentence,index);
					}
					else
					{
						extracter(sentence);
					}	
				}
				if(count%500==0||!i.hasNext())
				{
					writeToJson(tracker,count);
					tracker=count;
				}
		}
		System.out.println("Number of Review: "+count);
		System.out.println("\n" +"Number of unique Phrases: " + counter );			
	}
	public static void nounPhraseSummarizer(String id) throws Exception, ParseException//Noun Phrase for specific Product
	{
		int count=0,tracker=0;
		Iterator<?> i = Json.readJSON();
		while (i.hasNext()) {
			count++;
			System.out.println("Loop "+count);
			JSONObject obj = (JSONObject) i.next();
			String productID=(String) obj.get("asin");
			String review = (String) obj.get("reviewText");			
			if(productID==id)
				{
				String[] sentences=SentenceSegmentation.Getsentences(review);
					for(String sentence:sentences)
					{
							int index=chosen3.indexOf(productID);
							index++;
							extracter(sentence,index);
					}
				}
			if(count%500==0||!i.hasNext())
			{
					writeToJson(tracker,count);
					tracker=count;
			}
		}
		System.out.println("Number of Review: "+count);
		System.out.println("\n" +"Number of unique Phrases: " + counter );			
	}

	public static void extracter(String sentence1)
	{
		InputStream modelInParse = null;
		try {
			//load chunking model
			//from http://opennlp.sourceforge.net/models-1.5/
			modelInParse = new FileInputStream(openNLPBinLocation); 
			ParserModel model = new ParserModel(modelInParse);
			//create parse tree
			Parser parser = ParserFactory.create(model);
			Parse topParses[] = ParserTool.parseLine(sentence1, parser, 1);
			//call subroutine to extract noun phrases
			for (Parse p : topParses)
				getNounPhrases(p);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelInParse != null) {
		    try {
		    	modelInParse.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
	}
	public static void extracter(String sentence1,int num)
	{
		InputStream modelInParse = null;
		try {
			//load chunking model
			//from http://opennlp.sourceforge.net/models-1.5/
			modelInParse = new FileInputStream(openNLPBinLocation); 
			ParserModel model = new ParserModel(modelInParse);
			//create parse tree
			Parser parser = ParserFactory.create(model);
			Parse topParses[] = ParserTool.parseLine(sentence1, parser, 1);
			//call subroutine to extract noun phrases
			for (Parse p : topParses)
			{
				getNounPhrases2(p,num);
				getNounPhrases(p);
			}	
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelInParse != null) {
		    try {
		    	modelInParse.close();
		    }
		    catch (IOException e) {
		    }}}}
	
	//recursively loop through tree, extracting noun phrases
		public static void getNounPhrases(Parse p) {
		    if (p.getType().equals("NP")) { //NP=noun phrase
		    	String np=p.getCoveredText().toLowerCase(); 
		    	if(!isSingleWord(np))
		    	 {
			        if(nounPhrases.containsKey(np))
			        {	
			        	int value=nounPhrases.get(np);
			        	value++;
			        	nounPhrases.put(np, value);
			        }
			        else{
			        	nounPhrases.put(np, 1);
			        }
			    	counter++;
		    	 }
		    }
		    for (Parse child : p.getChildren())
		         getNounPhrases(child);
		}
		
		public static void getNounPhrases2(Parse p,int num) {
		    if (p.getType().equals("NP")) { //NP=noun phrase
		    	String np=p.getCoveredText().toLowerCase();
			     if(!isSingleWord(np))
			     {
		    		if(num==1)
			        {
			        	if(summaryPhrases1.containsKey(np))
				        {
				        	int value=summaryPhrases1.get(np);
				        	value++;
				        	summaryPhrases1.put(np, value);
				        }
				        else{
				        	summaryPhrases1.put(np, 1);
				        }
			        }
			        else if(num==2)
			        {
			        	if(summaryPhrases2.containsKey(np))
				        {
				        	int value=summaryPhrases2.get(np);
				        	value++;
				        	summaryPhrases2.put(np, value);
				        }
				        else{
				        	summaryPhrases2.put(np, 1);
				        }
			        }
			        else if(num==3)
			        {
			        	if(summaryPhrases3.containsKey(np))
				        {
				        	int value=summaryPhrases3.get(np);
				        	value++;
				        	summaryPhrases3.put(np, value);
				        }
				        else{
				        	summaryPhrases3.put(np, 1);
				        }
			        }
		    		counter++;
			     }
		    	
		    }
		    for (Parse child : p.getChildren())
		         getNounPhrases2(child,num);
		}

	public static void choose3 ()
	{
		Random rand = new Random();
		Boolean done=false;
		List<Integer> exclude=new ArrayList <Integer>();
		int count=0;
		System.out.println("Chosen 3:");
		while (!done)
		{
			int num =rand.nextInt(9);
			if(top10List.get(num)!=""&&!exclude.contains(num))
			{
				exclude.add(num);
				count++;
				chosen3.add(top10List.get(num));
				System.out.println(top10List.get(num));
				top10List.remove(num);
				if(count==3)done=true;
			}
		}
	}
	public static Boolean isSingleWord(String sentence)
	{
		String[] tempArray = sentence.trim().split(" ");
		if(tempArray.length>1)
		{
			return false;
		}
		return true;
	}
	public static void RemoveDuplicates()
	{
		List<String>removalList = new ArrayList<String>();
		for(String temp:summaryPhrases1.keySet())
		{
			if(temp.contains("."))
			{
				removalList.add(temp);
			}
		}
		for(String remove:removalList)
		{
			summaryPhrases1.remove(remove);
		}
		removalList.clear();
		for(String temp:summaryPhrases2.keySet())
		{
			if(temp.contains("."))
			{
				removalList.add(temp);
			}
		}
		for(String remove:removalList)
		{
			summaryPhrases2.remove(remove);
		}
		removalList.clear();
		for(String temp:summaryPhrases3.keySet())
		{
			if(temp.contains("."))
			{
				removalList.add(temp);
			}
		}
		for(String remove:removalList)
		{
			summaryPhrases3.remove(remove);
		}
		removalList.clear();
		for(String temp:JSONnounPhrases.keySet())
		{
			if(temp.contains("."))
			{
				removalList.add(temp);
			}
		}
		for(String remove:removalList)
		{
			JSONnounPhrases.remove(remove);
		}
		removalList.clear();
		
	}
	public static void print()
	{
		System.out.println("\n===================================");
		System.out.println("Overall Summary Phrase Top 20 Contents: ");
		HashMapSort.sortDescending(JSONnounPhrases,"nounPhase");
		System.out.println("\n===================================");
		if(!summaryPhrases1.isEmpty())
		{
			System.out.println("Summary Phrase 1 Top 20 Contents for product "+chosen3.get(0));
			HashMapSort.sortDescending(summaryPhrases1,"nounPhase1");
			System.out.println("\n===================================");
		}
		if(!summaryPhrases2.isEmpty())
		{
			System.out.println("Summary Phrase 2 Top 20 Contents for product "+chosen3.get(1));
			HashMapSort.sortDescending(summaryPhrases2,"nounPhase2");
			System.out.println("\n===================================");
		}
		if(!summaryPhrases3.isEmpty())
		{
			System.out.println("Summary Phrase 3 Top 20 Contents for product "+chosen3.get(2));
			HashMapSort.sortDescending(summaryPhrases3,"nounPhase3");
			System.out.println("\n===================================");
		}
	}
	@SuppressWarnings("unchecked")
	public static void writeToJson(int start,int end) throws IOException
	{
		JSONObject obj = new JSONObject();
		obj.put("Start", start);
		obj.put("End", end);
		JSONArray array = new JSONArray();
		for(String nounPhrase:nounPhrases.keySet())
		{
			JSONObject obj2 = new JSONObject();
			obj2.put("Noun Phrase", nounPhrase);
			obj2.put("Count", nounPhrases.get(nounPhrase));
			array.add(obj2);
		}
		nounPhrases.clear();
		obj.put("Noun Phrase", array);
		try (BufferedWriter file = new BufferedWriter(
				new FileWriter(new File(JSONFile), true));) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			file.write(",");	
		}
	}

	public static void RetrieveJson() throws IOException, ParseException
	{
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(JSONFile2);
		JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
		Iterator<?> i = jsonArray.iterator();
		List<Long> startList = new ArrayList<Long>();
		List<Long> endList = new ArrayList<Long>();
		while(i.hasNext())
		{
			JSONObject obj = (JSONObject) i.next();
			long start=(long) obj.get("Start");
			long end = (long) obj.get("End");
			if(!startList.contains(start)&&!endList.contains(end))
			{
				startList.add(start);
				endList.add( end);
			}
			else continue;
			System.out.println("Start: "+start+"  End: "+end);
			JSONArray array2= (JSONArray) obj.get("Noun Phrase");
			for (int x=0;x<array2.size();x++)
			{
				JSONObject temp=(JSONObject) array2.get(x);
				String phrase=(String) temp.get("Noun Phrase");
				long count= (long) temp.get("Count");
				if(JSONnounPhrases.containsKey(phrase))
				{	
					int value=JSONnounPhrases.get(phrase);
					value=(int) (value+count);
					JSONnounPhrases.put(phrase, value);
				}
				else
				{
					JSONnounPhrases.put(phrase,(int) count);
				}
			}
		}
	}
}
