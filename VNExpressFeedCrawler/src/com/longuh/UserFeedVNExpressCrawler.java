package com.longuh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.TreeSet;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class UserFeedVNExpressCrawler {
	private static Logger logger = LoggerFactory
			.getLogger(UserFeedVNExpressCrawler.class);

	public CrawlController createAnCrawlerController(String crawlStorageFolder,
			String label, String seedUrl) throws Exception {
		CrawlConfig config1 = new CrawlConfig();
		config1.setCrawlStorageFolder(crawlStorageFolder + File.separator
				+ label);
		config1.setPolitenessDelay(1000);
		config1.setMaxPagesToFetch(50);
		PageFetcher pageFetcher1 = new PageFetcher(config1);

		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher1);

		CrawlController controller1 = new CrawlController(config1,
				pageFetcher1, robotstxtServer);
		String[] customData = new String[] { seedUrl };
		controller1.setCustomData(customData);
		controller1.addSeed(seedUrl);

		return controller1;
	}

	public static void main(String[] args) throws Exception {
		// if (args.length != 1) {
		// logger.info("Needed config file dir, each file must have 4 parameters: ");
		// logger.info("\t 1. crawlStorageFolder (it will contain intermediate crawl data)");
		// logger.info("\t 2. numberOfThread for paralel processing");
		// logger.info("\t 3. label");
		// logger.info("\t 4. seedUrl");
		// return;
		// }

		UserFeedVNExpressCrawler crawler = new UserFeedVNExpressCrawler();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				new File("./config/UserIDList.out")));
		
		
		String charset = "UTF-8";
		String userAgent = "Mozilla/36.0";
		System.setProperty("http.proxyHost", "10.61.213.140");
		System.setProperty("http.proxyPort", "3128");
		
		String nextLine = bufferedReader.readLine();		
		int count = 0;			
		
		while (nextLine != null) {
			StringBuilder articleList = new StringBuilder();
			StringBuilder articleTestList = new StringBuilder();
			StringBuilder commentList = new StringBuilder();
			String url = nextLine.substring(nextLine.indexOf("http"),
					nextLine.length());
			String userID = nextLine.substring(nextLine.indexOf("feed")+5,nextLine.length());
			Elements links = Jsoup.connect(url+"?action=article").timeout(30000)
					.userAgent(userAgent).get()
					.select("div.content_comment");
			int trainSize = (int) Math.ceil(links.size()/3);
			for (int i =0; i< links.size();i++) {
				Element element = links.get(i);
				articleList.append(element.getElementsByTag("a").attr("href")+",");				
				commentList.append(element.getElementsByTag("p").text()+",");
				if(i>trainSize)
					articleTestList.append(element.getElementsByTag("a").attr("href")+",");
				count++;
			}
			nextLine = bufferedReader.readLine();			
			if(count>3)
				Files.write("numOfComment="+count+"\ntestArticles="+articleTestList+"\narticles="+articleList.toString()+"\ncomments="+commentList.toString(), new File("feed"+File.separator+userID+".txt"),Charsets.UTF_8);
			articleList = new StringBuilder();
			commentList = new StringBuilder();		
			count =0;
		}		
	}
}
