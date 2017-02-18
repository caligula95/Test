package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test2 {

	/**
	 * Reads words from file and puts them in ArrayList
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
	 * Checks if the word is compound from the other words in list
	 * @param word
	 * @param dictionary
	 * @return true if word is compound, false if not
	 */
	private boolean checkValidatedWords(String word, List<String> dictionary) {

		for (String ss : dictionary) {
			if (word.contains(ss)) {
				word = word.replaceAll(ss, "");
			}
		}
		if (word.length() == 0)
			return true;
		return false;
	}

	/**
	 * Finds the longest compound word
	 * @param dictionary
	 * @return word if found compound word, null if not
	 */
	public String getTheWord(List<String> dictionary) {
		boolean complex = false;
		String word = null;
		while (complex == false && dictionary.size() > 0) {
			word = findTheLongestWord(dictionary);
			complex = checkValidatedWords(word, dictionary);
		}
		return word;
	}

	/**
	 * Counts amount of complex words in the list
	 * @param dictionary
	 * @return
	 */
	public int countComplexWords(List<String> dictionary) {
		int count = 0;
		for (String word : dictionary) {
			if (checkValidatedWords(word, dictionary) == true)
				count++;
		}
		return count;
	}
}
