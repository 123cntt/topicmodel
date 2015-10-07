package com.longuh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class CorpusFileContentMerger {

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
		HashSet<String> urlSet = new HashSet<String>();
		for (File childDir : dataDir.listFiles()) {
			if (childDir.isDirectory()) {
				for (File childChildDir : childDir.listFiles()) {
					if (childChildDir.isDirectory()) {
						for (File file : childChildDir.listFiles()) {
							if (file.getName().endsWith(".txt")) {
								
								Properties p = new Properties();
								InputStreamReader in = new InputStreamReader(
										new FileInputStream(file), "UTF-8");
								p.load(in);
								String url = p.getProperty("Url");
								if (urlSet.contains(url))
									continue;
								urlSet.add(url);
								String text = p.getProperty("Content");
								if(text==null){
									continue;
								}
								
//								text = text.replaceAll("[\\d\\/[<>()\'\'\"]", " ").toLowerCase();
								text = text.replaceAll("[_.;,:\\<>()\'\'\"]", " ").toLowerCase();
								text = text.replaceAll("[\\?!#%&\\*“”]"," ");
								text = text.replaceAll(" [\\+-] "," ");
//								text = text.replaceAll("[;,-_]", " ");
//								// remove stop word
//								BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("vnstopword.txt")));			
//								String x = bufferedReader.readLine();
//								do {
//									text = text.replaceAll(" "+x+" ", " ");
//									x = bufferedReader.readLine();
//								}
//								while(x!=null);
//								// end
								// System.out.println(text);
								// System.out.println("---->");
//								tmp.append(url + "\n");
								
								if(text.length()<100)
									continue;
								
								count++;
								tmp.append(text + "\n\n");
								in.close();
							}
						}
					}
				}
			}
		}
		Files.write(count + "\n" + tmp, data, Charsets.UTF_8);
		System.out.println(count);
	}
}