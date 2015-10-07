package com.longuh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Properties;

import jgibblda.Inferencer;
import jgibblda.LDA;
import jgibblda.LDACmdOption;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class UserArticlesCrawler {

	public static void main(String[] args) throws Exception {

		String userAgent = "Mozilla/36.0";
		// System.setProperty("http.proxyHost", "10.61.213.140");
		// System.setProperty("http.proxyPort", "3128");
		File dir = new File(args[0]);

		for (File file : dir.listFiles()) {
			Properties prop = new Properties();
			InputStream in = new FileInputStream(file);
			prop.load(in);
			StringBuilder tmp = new StringBuilder();

			String articlesList = prop.getProperty("articles");
			String[] articles = articlesList.split(",");
			int count = 0;

			for (String article : articles) {
				try {
					Element element = Jsoup.connect(article).timeout(300000)
							.userAgent(userAgent).get()
							.select("div.fck_detail.width_common").first();
					if (element == null) {

						element = Jsoup.connect(article).timeout(300000)
								.userAgent(userAgent).get()
								.select("#block-1437487200 > div.text_live")
								.first();
						if (element == null) {
							element = Jsoup.connect(article).timeout(300000)
									.userAgent(userAgent).get()
									.select("#article_content").first();
							if (element == null) {
								System.out.println(article);
								continue;
							}
						}

					}

					count++;
					String content = element.text();
					Files.write("1\n" + content, new File("usermodel2"
							+ File.separator + file.getName() + count),
							Charsets.UTF_8);
					tmp.append(content + "\n\n");
				} catch (SocketTimeoutException e) {
					System.out.println("--------------->"+article);
					continue;
				}
			}
			Files.write(count + "\n" + tmp.toString(), new File("usermodel"
					+ File.separator + file.getName()), Charsets.UTF_8);
		}

	}
}
