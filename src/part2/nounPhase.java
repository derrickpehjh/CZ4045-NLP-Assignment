package part2;

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

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import part1.HashMapSort;
import part1.Json;
import part1.TopProductsAndReviews;

public class nounPhase {

	static String sentence = "Who is the author of The Call of the Wild?";
	static String sentence2="Good case, solid build. Protects phone all around with good access to buttons. Battery charges with full battery lasts me a full day. I usually leave my house around 7am and return at 10pm. I'm glad that it lasts from start to end. 5/5";
	static String sentence3="Just what I needed. I needed a phone case for myself and my two sons, but I also needed new replacement batteries. Now this isn't the case, since I got both in one. Awesome thanks A+";
	static String openNLPBinLocation = "libraries\\en-parser-chunking.bin";
	static Map<String, Integer> nounPhrases = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases1 = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases2 = new HashMap<String, Integer>();
	static Map<String, Integer> summaryPhrases3 = new HashMap<String, Integer>();
	static List<String> top10List=new ArrayList<String>();
	static List<String> chosen3=new ArrayList<String>();
	static int counter=0;
	
	public static void main(String[] args) throws Exception {
		top10List=TopProductsAndReviews.retrieveReviewerorProductList("asin");//get top 10 products on review
		choose3();//Randomly selected
//		selected3();//Pre-selected used for testing 
//		nounPhraseSummarizer();//For overall Search
		nounPhraseSummarizer(100,300);//Search Particular sections
		RemoveDuplicates();
		print();		
	}
	public static void nounPhraseSummarizer(int startIndex,int endIndex) throws Exception, ParseException
	{
		int count=0;
		Iterator<?> i = Json.readJSON();
		while (i.hasNext()) {
			count++;
			if(count>startIndex && count<=endIndex)
			{
				System.out.println("Loop "+count);
				JSONObject obj = (JSONObject) i.next();
				String productID=(String) obj.get("asin");
				String review = (String) obj.get("reviewText");
				if(chosen3.contains(productID))
				{
						int index=chosen3.indexOf(productID);
						index++;
						extracter(review,index);
				}
				else
				{
					extracter(review);
				}
			}
			if(count==endIndex)break;
//			if(count==200)break;//should remove for full run
		}
		System.out.println("Number of Review: "+count);
		System.out.println("\n" +"Number of unique Phrases: " + counter );			
	}
	public static void nounPhraseSummarizer() throws Exception, ParseException
	{
		int count=0;
		Iterator<?> i = Json.readJSON();
		while (i.hasNext()) {
			count++;
			
				System.out.println("Loop "+count);
				JSONObject obj = (JSONObject) i.next();
				String productID=(String) obj.get("asin");
				String review = (String) obj.get("reviewText");
				if(chosen3.contains(productID))
				{
						int index=chosen3.indexOf(productID);
						index++;
						extracter(review,index);
				}
				else
				{
					extracter(review);
				}
			if(count==200)break;//should remove for full run
		}
		System.out.println("Number of Review: "+count);
		System.out.println("\n" +"Number of unique Phrases: " + counter );			
	}
	public static void extracter(String sentence1)
	{
		InputStream modelInParse = null;
		try {
			//load chunking model
			modelInParse = new FileInputStream(openNLPBinLocation); //from http://opennlp.sourceforge.net/models-1.5/
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
			modelInParse = new FileInputStream(openNLPBinLocation); //from http://opennlp.sourceforge.net/models-1.5/
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
		    }
		  }
		}
	}
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
	public static void selected3()
	{
		chosen3.add("6073894996");
		chosen3.add("8288853439");
		chosen3.add("8288878881");
	}
	public static void choose3 ()
	{
		Random rand = new Random();
		Boolean done=false;
		List<Integer> exclude=new ArrayList <Integer>();
		int count=0;
		while (!done)
		{
			int num =rand.nextInt(9);
			if(top10List.get(num)!=""&&!exclude.contains(num))
			{
				exclude.add(num);
				count++;
				chosen3.add(top10List.get(num));
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
		
	}
	public static void print()
	{
		System.out.println("\n===================================");
		System.out.println("Overall Summary Phrase Top 10 Contents: ");
		HashMapSort.retrieveDescending(nounPhrases);
		System.out.println("\n===================================");
		System.out.println("Summary Phrase 1 Top 10 Contents for product "+chosen3.get(0));
		HashMapSort.retrieveDescending(summaryPhrases1);
		System.out.println("\n===================================");
		System.out.println("Summary Phrase 2 Top 10 Contents for product "+chosen3.get(1));
		HashMapSort.retrieveDescending(summaryPhrases2);
		System.out.println("\n===================================");
		System.out.println("Summary Phrase 3 Top 10 Contents for product "+chosen3.get(2));
		HashMapSort.retrieveDescending(summaryPhrases3);
	}
}
