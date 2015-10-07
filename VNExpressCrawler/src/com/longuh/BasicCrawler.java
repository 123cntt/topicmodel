package com.longuh;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author Yasser Ganjisaffar [lastname at gmail dot com]
 */
public class BasicCrawler extends WebCrawler {
	private Logger logger = LoggerFactory.getLogger(BasicCrawler.class);

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private String[] myCrawlDomains;

	@Override
	public void onStart() {
		myCrawlDomains = (String[]) myController.getCustomData();
	}

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) {
			return false;
		}

		for (String crawlDomain : myCrawlDomains) {
			if (href.startsWith(crawlDomain)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		int parentDocid = page.getWebURL().getParentDocid();

		logger.debug("Docid: {}", docid);
		logger.info("URL: {}", url);
		logger.debug("Docid of parent page: {}", parentDocid);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			
			//jsoup preprocess raw html file
			Document doc = Jsoup.parse(html, "UTF-8");
			Element titleElement = doc.select("div.title_news").first();
			Element timeElement = doc.select("div.block_timer.left.txt_666").first();
			Element contentElement = doc.select("div.fck_detail.width_common").first();
			
			StringBuilder text = new StringBuilder();
			text.append("Url="+url+"\n");
			text.append("Title="+titleElement.text()+"\n");
			text.append("Time="+timeElement.text()+"\n");
			text.append("Content="+contentElement.text()+"\n");
			//end add
						
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			String fileName = "./"+myController.getConfig().getCrawlStorageFolder()+"/doc-"+docid+".txt";
			File file = new File(fileName);
			
			try {
				Files.write(text.toString(), file, Charsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug("Text length: {}", contentElement.text().length());
			logger.debug("Html length: {}", html.length());
			logger.debug("Number of outgoing links: {}", links.size());
		}

		logger.debug("=============");
	}
}