package com.longuh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ExplicitLabelVNExpressCrawler {
	private static Logger logger = LoggerFactory
			.getLogger(ExplicitLabelVNExpressCrawler.class);

	public CrawlController createAnCrawlerController(String crawlStorageFolder, String label, String seedUrl) throws Exception{
		CrawlConfig config1 = new CrawlConfig();
		config1.setCrawlStorageFolder(crawlStorageFolder+File.separator+label);
		config1.setPolitenessDelay(1000);
		config1.setMaxPagesToFetch(50);
		PageFetcher pageFetcher1 = new PageFetcher(config1);
		
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher1);

		CrawlController controller1 = new CrawlController(config1,
				pageFetcher1, robotstxtServer);
		String [] customData = new String[]{seedUrl};
		controller1.setCustomData(customData);
		controller1.addSeed(seedUrl);
		
		return controller1;
	}
	public static void main(String[] args) throws Exception {
//		if (args.length != 1) {
//			logger.info("Needed config file dir, each file must have 4 parameters: ");
//			logger.info("\t 1. crawlStorageFolder (it will contain intermediate crawl data)");
//			logger.info("\t 2. numberOfThread for paralel processing");
//			logger.info("\t 3. label");
//			logger.info("\t 4. seedUrl");
//			return;
//		}
				
		ExplicitLabelVNExpressCrawler crawler = new ExplicitLabelVNExpressCrawler();
		
		File fileDir = new File("./config/labelDir");
		Vector<CrawlController> crawlControllers = new Vector<CrawlController>();
		System.out.println(fileDir.getAbsolutePath());
		for (File file : fileDir.listFiles()) {
			Properties prop = new Properties();
			InputStream in = new FileInputStream(file);
			prop.load(in);
			
			String crawlStorageFolder = prop.getProperty("crawlStorageFolder");			
			String label = prop.getProperty("label");
			String seedUrl = prop.getProperty("seedUrl");
			in.close();
			
			CrawlController aCrawler = crawler.createAnCrawlerController(crawlStorageFolder, label, seedUrl);
			crawlControllers.add(aCrawler);
		}
		
		for (CrawlController crawlController : crawlControllers) {
			crawlController.startNonBlocking(BasicCrawler.class, 5);					    
			crawlController.waitUntilFinish();
			logger.info("Crawler is finished.");
		}
	}
}
