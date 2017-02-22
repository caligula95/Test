package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test2 {

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
	public Map<Character, ArrayList<String>> putWordsInMap(List<String> words) {
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
	private boolean isCompoundWord(String word, Map<Character, ArrayList<String>> words, int counter) {
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
				else if (word.startsWith(string) && !word.equals(string)) {
					if (isCompoundWord(word.substring(string.length()), words, counter++)==true){
						return true;
					}	
				}
				else if (string.equals(word) && counter>0) {
					return true;
				}
			}
		return false;
	}

	/**
	 * Finds the longest word in the list, return and removes it
	 * @param words
	 * @return the longest word
	 */
	private String findTheLongestWord(List<String> words) {
		String longestString = null;
		int index = 0;
		int largestString = words.get(0).length();
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).length() > largestString) {
				largestString = words.get(i).length();
				index = i;
			}
		}
		longestString = words.get(index);
		words.remove(index);
		return longestString;
	}
	
	/**
	 * Finds the longest compound word
	 * @param dictionary
	 * @return word if found compound word, null if not
	 */
	public String getTheWord(Map<Character, ArrayList<String>> words, List<String> dictionary) {
		boolean complex = false;
		String word = null;
		while (complex == false && dictionary.size() > 0) {
			word = findTheLongestWord(dictionary);
			complex = isCompoundWord(word, words, 0);
		}
		return word;
	}
	
	
	public static void main(String[] args) {
		Test2 test = new Test2();
		List<String> words = test.readFile(new File("words.txt"));
		Map<Character, ArrayList<String>> mappedWords = test.putWordsInMap(words);
		System.out.println(test.getTheWord(mappedWords, words));
		System.out.println(test.getTheWord(mappedWords, words));
	}
}
