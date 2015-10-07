package com.longuh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileContentMerger {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		if (args.length != 1) {
			System.out.println("Needed parameter: ");
			System.out.println("\t crawled file folder for concat");
			return;
		}
		
		File dataDir = new File(args[0]);
		File data = new File(args[0] + ".dat");
		StringBuilder tmp = new StringBuilder();
		int count = 0;
		for (File childDir : dataDir.listFiles()) {
			if (childDir.isDirectory()) {
				for (File file : childDir.listFiles()) {
					if (file.getName().endsWith(".txt")) {
						count++;
						Properties p = new Properties();
						InputStreamReader in = new InputStreamReader(
								new FileInputStream(file), "UTF-8");
						p.load(in);
						String text = p.getProperty("Content");
						text=text.replaceAll("[<>()\'\'\"]", "");
						// System.out.println(text);
						// System.out.println("---->");						
						tmp.append(text + "\n\n");
						in.close();
					}
				}
			}
		}
		Files.append(count+"\n"+tmp, data, Charsets.UTF_8);
	}
}