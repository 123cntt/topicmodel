package com.longuh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class StopWordRemover {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		if (args.length != 1) {
			System.out.println("Needed parameter: ");
			System.out.println("\t remove stop word from a file");
			return;
		}
		
		File file = new File(args[0]);
		List<String> list = Files.readLines(file, Charsets.UTF_8);
		StringBuilder tmp = new StringBuilder();
		for (String text : list) {
			// remove stop word
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					new File("vnstopword.txt")));
			String x = bufferedReader.readLine();
			do {
				text = text.replaceAll(" " + x + " ", " ");
				x = bufferedReader.readLine();
			} while (x != null);
			// end
			
			tmp.append(text + "\n");
		}
	
		Files.write(tmp.toString(), new File(args[0]+".swr"),Charsets.UTF_8);
	}
}
