package com.longuh;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Test {
	public static void main(String[] args) throws IOException {
		List<String> list = Files.readLines(new File("test.txt"), Charsets.UTF_8);
		for (String string : list) {
			System.out.println(string.replaceAll("[;,\\d\\/<>()\'\'\"]", ""));
		}
		
	}
}
