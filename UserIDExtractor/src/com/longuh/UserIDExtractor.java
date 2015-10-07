package com.longuh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class UserIDExtractor {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				new File("data/UserIDList.txt")));
		TreeSet<String> userSet = new TreeSet<String>();
		String nextLine = bufferedReader.readLine();
		StringBuilder tmp = new StringBuilder();
		while (nextLine != null) {			
			if (nextLine.contains("URL")) {
				String newUserId = nextLine;
				if(nextLine.contains("?"))
					newUserId = nextLine.substring(0, nextLine.indexOf("?"));
				 
				userSet.add(newUserId);
			}
			nextLine = bufferedReader.readLine();			
		}
	
		for (String string : userSet) {
			tmp.append(string);
			tmp.append("\n");
		}
		
		Files.write(tmp.toString(), new File("data/UserIDList.out"),Charsets.UTF_8);
		System.out.println(tmp);
	}
}
