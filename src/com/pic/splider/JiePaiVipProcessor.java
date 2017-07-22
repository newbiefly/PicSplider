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

public class JiePaiVipProcessor implements PageProcessor {
	
	public static final int TYPE_FREE = 0; //免费体验
	public static final int TYPE_VIDEO = 1; //街拍视频 + 超长集合
	public static final int TYPE_PIC = 2; //街拍图片
	public static final int TYPE_LIFE = 3; //丝袜生活 + 私密自拍
	public static final int TYPE_SELF = 5; //
	public static final String URL_JIEPAI_HOME = "http://www.jiepaiss.com/";
	public static final String URL_JIEPAI_LIST = "http://www\\.jiepaiss\\.com/forum-\\d+\\.html";
	public static final String URL_JIEPAI_DETAIL = "http://www\\.jiepaiss.\\com/thread-\\w+\\.html";

	public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";
	public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";
	

	public static final String URL_JIEPAI_PIC_DOMAIN = "http://pic.zhifuok.com/qq32593992";

	private Site site = Site
			.me()
			.setDomain("www.jiepaiss.com")
			.setSleepTime(200)
			.setRetryTimes(5)
			.setUserAgent("Mozilla/5.0 (Macintosh; IntelMac OS X 10_7_2)  Chrome/26.0.1410.65");

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
				} else {
					request.putExtra("type", -1);
				}
				page.addTargetRequest(request);
			}
		} else if (page.getUrl().toString().indexOf("forum-") > 0) { // 列表页
			System.out.println("列表页:"+page.getRequest().getExtra("type"));
			if (page.getRequest().getExtra("type") == null || Integer.parseInt(page.getRequest().getExtra("type").toString()) != 3) {
				return;
			}
			
			if (page.getRequest().getExtra("type").toString().equals(0)) {//免费体验 当普通图片处理
				doListPageContent(page);
			}else if(page.getRequest().getExtra("type").toString().equals("1")) {//街拍视频
				
			}else if(page.getRequest().getExtra("type").toString().equals("2")) {//街拍图片
				System.out.println("街拍图片");
				doListPageContent(page);
				doListPageCount(page);
			}else if(page.getRequest().getExtra("type").toString().equals("3")) {//丝袜生活
				System.out.println("丝袜生活");
				doListPageContent(page);
				doListPageCount(page);
			}
			

		} else if (page.getUrl().toString().indexOf("thread-") > 0) { // 详情页
			if (page.getRequest().getExtra("type") == null || Integer.parseInt(page.getRequest().getExtra("type").toString()) != 3) {
				return;
			}
			PicModel picModel = new PicModel();
			if (page.getRequest().getExtra("type").toString().equals(0)) {//免费体验 当普通图片处理
				picModel.title = page.getHtml().xpath("//h1[@class='ts']/a/text()").toString();
				picModel.urls = page.getHtml().xpath("//div[@class='t_fsz']/table/tbody/tr/td/img/@file").all().toString().replace("[", "").replace("]", "");
				page.putField("title",picModel.title);
			    page.putField("urls",picModel.urls);
			}else if(page.getRequest().getExtra("type").toString().equals("1")) {//街拍视频
				
			}else if(page.getRequest().getExtra("type").toString().equals("2")) {//街拍图片
				picModel.title = page.getHtml().xpath("//h1[@class='ts']/a/text()").toString();
				picModel.urls = page.getHtml().xpath("//div[@class='t_fsz']/table/tbody/tr/td/img/@file").all().toString().replace("[", "").replace("]", "").replace(URL_JIEPAI_PIC_DOMAIN, "");
				page.putField("title",picModel.title);
			    page.putField("urls",picModel.urls);
			}else if(page.getRequest().getExtra("type").toString().equals("3")) {//丝袜生活照
				picModel.title = page.getHtml().xpath("//h1[@class='ts']/a/text()").toString();
				picModel.urls = page.getHtml().xpath("//div[@class='t_fsz']/table/tbody/tr/td/img/@file").all().toString().replace("[", "").replace("]", "").replace(URL_JIEPAI_PIC_DOMAIN, "");
				page.putField("title",picModel.title);
			    page.putField("urls",picModel.urls);
			}
			picModel.type = Integer.parseInt(page.getRequest().getExtra("type").toString());
			String[] urls = picModel.urls.split(",");
			picModel.amout = urls.length;
			picModel.firstUrl = urls[0];
			page.putField("pic", picModel);
		
		    
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
	
	
	private void doListPageContent(Page page) {
		List<String> catagList = page.getHtml()
				.xpath("//ul[@class=\"ml mlt mtw cl\"]").links().all();
		if (catagList == null || catagList.isEmpty()) {
			return;
		}
		for (int i = 0; i < catagList.size(); i++) {
			String url = catagList.get(i);
			Request request = new Request(url);
			request.putExtra("type", page.getRequest().getExtra("type"));
			page.addTargetRequest(request);
		}
	}
	
	private void doListPageCount(Page page) {//分页链接
		System.err.println("doListPageCount");
		List<String> catagList = page.getHtml()
				.xpath("//div[@class=\"pg\"]").links().all();
		System.err.println("doListPageCount:"+ catagList.toString());
		if (catagList == null || catagList.isEmpty()) {
			return;
		}
		for (int i = 0; i < catagList.size(); i++) {
			String url = catagList.get(i);
			Request request = new Request(url);
			request.putExtra("type", page.getRequest().getExtra("type"));
			page.addTargetRequest(request);
		}
	}
	
	
	public void setCookier(String name,String value) {
		if (site == null) {
			return;
		}
		site.addCookie(name, value);
	}
	

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		JiePaiVipProcessor blogProcessor = new JiePaiVipProcessor();
		
		ArrayList<Cookie> cookies = Login.post();
		
		if (!cookies.isEmpty()) {
			for (Cookie cookie : cookies) {
				blogProcessor.setCookier(cookie.getName(), cookie.getValue());
				
			}
		}
		Spider.create(blogProcessor)
				.addUrl("http://www.jiepaiss.com/")
				.addPipeline(new PicDataStoragePipeline()).run();
	}

}
