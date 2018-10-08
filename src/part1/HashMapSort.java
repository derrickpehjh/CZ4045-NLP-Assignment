package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HashMapSort {

	@SuppressWarnings("unchecked")
	public static HashMap<Integer, String> sortByValues(HashMap<?, ?> map) {
		List<Object> list = new LinkedList<Object>(map.entrySet());
		Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("rawtypes")
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Collections.reverse(list);
		@SuppressWarnings("rawtypes")
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator<Object> it = list.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	public static void sortDescending(Map<?, ?> idList, String idType) {

		if (idType.equals("SentenceSegmentation")) {
			Map<Integer, String> map = sortByValues((HashMap<?, ?>) idList);
			System.out.println("After Sorting:");
			Set<?> set2 = map.entrySet();
			Iterator<?> iterator2 = set2.iterator();
			while (iterator2.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry me2 = (Map.Entry) iterator2.next();
				System.out.print(me2.getKey() + ": ");
				System.out.println(me2.getValue());
			}
		} else if (idType.equals("WordToken")) {
			Map<Integer, String> map = sortByValues((HashMap<?, ?>) idList);
			System.out.println("After Sorting:");
			Set<Entry<Integer, String>> set = map.entrySet();
			Iterator<Entry<Integer, String>> iterator = set.iterator();
			int x = 0;
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry me = (Map.Entry) iterator.next();
				x++;
				if (x <= 20) {
					System.out.print(me.getKey() + ": ");
					System.out.println(me.getValue());
				}
			}
		} else if (idType.equals("sentiment")) {
			Map<Integer, String> map = sortByValues((HashMap<?, ?>) idList);
			System.out.println("After Sorting:");
			Set<Entry<Integer, String>> set = map.entrySet();
			Iterator<Entry<Integer, String>> iterator = set.iterator();
			int x = 0;
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry me = (Map.Entry) iterator.next();
				x++;
				if (x <= 20) {
					System.out.print(me.getKey() + ": ");
					System.out.println(me.getValue());
				}
			}
		} else {
			Map<Integer, String> map = sortByValues((HashMap<?, ?>) idList);
			System.out.println("After Sorting:");
			Set<Entry<Integer, String>> set = map.entrySet();
			Iterator<Entry<Integer, String>> iterator = set.iterator();
			int x = 0;
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry me = (Map.Entry) iterator.next();
				x++;
				if (x <= 10) {
					System.out.print(me.getKey() + ": ");
					System.out.println(me.getValue());
				}
			}

		}
	}
	
	public static ArrayList<String> retrieveDescending(Map<?, ?> idList) {
		Map<Integer, String> map = sortByValues((HashMap<?, ?>) idList);
		ArrayList<String> savedList = new ArrayList<String>();
		System.out.println("After Sorting:");
		Set<Entry<Integer, String>> set = map.entrySet();
		Iterator<Entry<Integer, String>> iterator = set.iterator();
		int x = 0;
		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry) iterator.next();
			x++;
			if (x <= 10) {
				savedList.add(me.getKey().toString());
			}
		}
		return savedList;
	}
	
}
