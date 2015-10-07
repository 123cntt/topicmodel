package com.longuh;

import java.awt.List;
import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAnchorElement;

public class HtmlUnitTester {
	public void testHomePage() throws Exception {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38, "10.61.213.140", 3128);
		
		final URL url = new URL("http://my.vnexpress.net/users/feed/1002613261");
		
		// Get the first page
		final HtmlPage page1 = (HtmlPage) webClient.getPage(url);
		System.out.println("anchor");
		
		// HtmlAnchor anchor1 = page1.getAnchorByHref("javascript:void(0)");
		// anchor1.click();
		// System.out.println(page1.getDocumentURI());
	}

	public static void main(String[] args) throws Exception {
		HtmlUnitTester htmlUnitTester = new HtmlUnitTester();
		htmlUnitTester.testHomePage();
	}
}
