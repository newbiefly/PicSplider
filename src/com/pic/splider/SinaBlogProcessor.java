package com.pic.splider;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class SinaBlogProcessor implements PageProcessor {
	public static final String URL_JIEPAI_HOME = "http://www.jiepaiss.com/";
	public static final String URL_JIEPAI_LIST = "http://www\\.jiepaiss\\.com/forum-\\d+\\.html";
	public static final String URL_JIEPAI_DETAIL = "http://www\\.jiepaiss.\\com/thread-\\w+\\.html";

	public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";
	public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

	private Site site = Site
			.me()
			.setDomain("www.jiepaiss.com")
			.setSleepTime(100)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; IntelMac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {

		if (URL_JIEPAI_HOME.equals(page.getUrl().toString())) { // 首页
			List<String> catagList = page.getHtml()
					.xpath("//div[@class=\"vocation-mark\"]/a/@href").all();
			System.out.println(catagList.toString());
			if (catagList == null || catagList.isEmpty()) {
				return;
			}
			for (int i = 0; i < catagList.size(); i++) {
				String url = catagList.get(i);
				Request request = new Request(URL_JIEPAI_HOME + url);
				if (("forum-62-1.html").equals(url)) {
					request.putExtra("type", 0);
				} else if (("forum-126-1.html").equals(url)) {
					request.putExtra("type", 1);
				} else if (("forum-2-1.html").equals(url)) {
					request.putExtra("type", 2);

				} else if (("forum-67-1.html").equals(url)) {
					request.putExtra("type", 3);

				} else if (("forum-91-1.html").equals(url)) {
					request.putExtra("type", 4);
				} else if (("forum-37-1.html").equals(url)) {
					request.putExtra("type", 5);

				} else if (("forum-45-1.html").equals(url)) {
					request.putExtra("type", 6);
				}
				page.addTargetRequest(request);
			}
		} else if (page.getUrl().toString().indexOf("forum-") > 0) { // 列表页
			if (page.getRequest().getExtra("type") == null || Integer.parseInt(page.getRequest().getExtra("type").toString()) != 0) {
				return;
			}
			List<String> catagList = page.getHtml()
					.xpath("//ul[@class=\"ml mlt mtw cl\"]").links().all();
			System.out.println(catagList.toString());
			if (catagList == null || catagList.isEmpty()) {
				return;
			}
			for (int i = 0; i < catagList.size(); i++) {
				String url = catagList.get(i);
				Request request = new Request(url);
				request.putExtra("type", page.getRequest().getExtra("type"));
				page.addTargetRequest(request);
			}

		} else if (page.getUrl().toString().indexOf("thread-") > 0) { // 详情页
			if (page.getRequest().getExtra("type") == null || Integer.parseInt(page.getRequest().getExtra("type").toString()) != 0) {
				return;
			}
			
		    page.putField("title",page.getHtml().xpath("//h1[@class='ts']/a/text()"));
		    page.putField("urls",page.getHtml().xpath("//div[@class='t_fsz']/table/tbody/tr/td/img/@file").all().toString());
		} else {
			System.err.println("no no no ");
		}

		// //列表页
		// if (page.getUrl().regex(URL_LIST).match()) {
		// page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
		// page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
		// //文章页
		// } else {
		// page.putField("title",
		// page.getHtml().xpath("//div[@class='articalTitle']/h2"));
		// //page.putField("content",
		// page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
		// //page.putField("date",
		// //page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
		// }
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new SinaBlogProcessor())
				.addUrl("http://www.jiepaiss.com/").run();
	}

}
