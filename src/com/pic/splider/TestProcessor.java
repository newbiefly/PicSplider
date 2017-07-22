package com.pic.splider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.cookie.Cookie;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.jsoup.helper.StringUtil;

import com.pic.splider.Pipeline.PicDataStoragePipeline;
import com.pic.splider.model.PicModel;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class TestProcessor implements PageProcessor {
	

	public static final String URL_JIEPAI_PIC_DOMAIN = "http://pic.zhifuok.com/qq32593992";

	private Site site = Site
			.me()
			.setDomain("www.jiepaiss.com")
			.setSleepTime(200)
			.setUserAgent("Mozilla/5.0 (Macintosh; IntelMac OS X 10_7_2)  Chrome/26.0.1410.65");

	@Override
	public void process(Page page) {

		String resultString = page.getHtml().xpath("//table[@class='cgtl']//tr[2]/td/text()").toString();
		System.out.println(resultString);
		
		

		
	}
	
	

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		TestProcessor blogProcessor = new TestProcessor();
	
		Spider.create(blogProcessor)
				.addUrl("http://www.jiepaiss.com/thread-11315-1-1.html").run();
	}

}
