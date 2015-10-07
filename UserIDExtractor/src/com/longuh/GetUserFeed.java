package com.longuh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetUserFeed {
	public static void main(String[] args) throws IOException {
		int start = 0;
		int num = 100;
		GetUserFeed getUrl = new GetUserFeed();
		String result="";
		do{
			result = getUrl.getBatchUrl(start, num);
			System.out.println("-----Batch"+start/100+"-----");
			start+=100;
		}
		while(!result.equals(""));
	}
	public String getBatchUrl(int start, int num) throws UnsupportedEncodingException, IOException{
		String google = "http://www.google.com/search?start="+start+"&num="+num+"&q=";
		String search = "site:http://my.vnexpress.net/users/feed/";
		String charset = "UTF-8";
		String userAgent = "Mozilla/36.0"; // Change
		String tmp="";				
		System.setProperty("http.proxyHost", "10.61.213.140");
		System.setProperty("http.proxyPort", "3128");
		HashSet<String> userSet = new HashSet<String>();
		Elements links = Jsoup
				.connect(google + URLEncoder.encode(search, charset)).timeout(3000)
				.userAgent(userAgent).get().select("li.g>h3>a");

		for (Element link : links) {
			String title = link.text();
			String url = link.absUrl("href"); // Google returns URLs in format
												// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
			url = URLDecoder.decode(
					url.substring(url.indexOf('=') + 1, url.indexOf('&')),
					"UTF-8");

			if (!url.startsWith("http")) {
				continue; // Ads/news/etc.
			}

			String newUserId = url.substring(0, url.indexOf("?"));
			if(userSet.contains(newUserId))
				continue;
			
			System.out.println("Title: " + title);
			System.out.println("URL: " + newUserId);
			
			tmp += title +"\n"+url+"\n";
		}
		return tmp;
	}
}
