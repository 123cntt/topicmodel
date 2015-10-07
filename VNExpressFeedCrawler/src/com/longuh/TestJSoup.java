package com.longuh;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJSoup {
	public static void main(String[] args) throws IOException {
		File html = new File("./data/1002613143.html");
		Document doc = Jsoup.parse(html, "UTF-8");
		Elements elements = doc.select("div.content_comment");
		for (Element element : elements) {			
			System.out.println(element.getElementsByTag("a").attr("href"));
			System.out.println(element.getElementsByTag("p").text());
			System.out.println(element.getElementsByClass("time").text());
		}
		
	}
}
