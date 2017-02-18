package com.test.main;

import java.io.File;
import java.util.List;

import com.test.Test2;

public class Main {

	public static void main(String[] args) {
		Test2 test = new Test2();
		List<String> words = test.readFile(new File("words.txt"));
		System.out.println(test.getTheWord(words));
		System.out.println(test.getTheWord(words));
		System.out.println(test.countComplexWords(words));
		
	}
}
