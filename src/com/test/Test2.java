package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test2 {

	private Set<String> compoundWords = new TreeSet<String>();
	/**
	 * Reads words from file and puts them in ArrayList
	 * 
	 * @param file
	 * @return List of words
	 */
	public List<String> readFile(File file) {
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
			String line;
			while ((line = reader.readLine()) != null) {
				words.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("IOException");
		}
		return words;
	}

	/**
	 * Puts words in map where key is word first character and value arraylist of words
	 * @param words
	 * @return map
	 */
	private Map<Character, ArrayList<String>> putWordsInMap(List<String> words) {
		Map<Character, ArrayList<String>> mappedWords = new TreeMap<>();
		for (String string : words) {
			if (!mappedWords.containsKey(string.charAt(0))) {
				mappedWords.put(string.charAt(0), new ArrayList<>());
			}
			mappedWords.get(string.charAt(0)).add(string);
		}
		return mappedWords;
	}

	/**
	 * Finds if word is compound and adds it to the set 
	 * @param word
	 * @param original
	 * @param words
	 * @param counter
	 */
	private void isCompoundWord(String word, String original, Map<Character, ArrayList<String>> words, int counter) {
		if (word.length() == 0) {
			//adds compound word to the set of words
			compoundWords.add(original);
		} else {
			char firstLetter = word.charAt(0);
			List<String> dictionary = null;
			if (words.containsKey(firstLetter)) {
				dictionary = words.get(firstLetter);
			} else {
				System.out.println("Word don't found");
			}
			for (String string : dictionary) {
				if (string.equals(word) && counter == 0)
					continue;
				if (word.startsWith(string)) {
					int t = counter + 1;
					isCompoundWord(word.substring(string.length()), original, words, t);
				}

			}

		}

	}

	/**
	 * Finds the longest word in set
	 * @param words
	 * @return
	 */
	public String findMax(Set<String> words) {
		String max = "";
		for (Iterator<String> it = words.iterator(); it.hasNext();) {
			String f = it.next();
			if (f.length() > max.length())
				max = f;

		}
		return max;
	}

	public static void main(String[] args) {
		Test2 test = new Test2();
		List<String> words = test.readFile(new File("words.txt"));
		Map<Character, ArrayList<String>> mappedWords = test.putWordsInMap(words);
		for (String string : words) {
			test.isCompoundWord(string, string, mappedWords, 0);
		}
		System.out.println(test.compoundWords.size());
		System.out.println(test.findMax(test.compoundWords));
		test.compoundWords.remove("ethylenediaminetetraacetates");
		System.out.println(test.findMax(test.compoundWords));
	}
}
