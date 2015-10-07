package com.longuh;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestJSoup {
	public static void main(String[] args) throws IOException {
		File html = new File("./data/Sohoa.html");
		Document doc = Jsoup.parse(html, "UTF-8");
		Element element = doc.select("div.block_col_480").first();
		String text = element.text();
		System.out.println("---all---");
		System.out.println(text);
		
		System.out.println("---title---");
		Element titleElement = doc.select("div.title_news").first();
		System.out.println("Title:"+titleElement.text());
		
		System.out.println("---shortContent---");
		Element shortContentElement = doc.select("div.short_intro.txt_666").first();
		System.out.println("Tom tat:"+shortContentElement.text());
		
		System.out.println("---Content---");
		Element contentElement = doc.select("#left_calculator > div.fck_detail.width_common").first();
		System.out.println("Noidung:"+contentElement.text());
		
		System.out.println("---Time---");
		Element timeElement = doc.select("div.block_col_480 > div.block_timer_share > div.block_timer.left.txt_666").first();
		System.out.println("Time:"+timeElement.text());		
		
	}
}
