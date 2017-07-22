package com.pic.splider.Pipeline;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.pic.splider.db.DBServices;
import com.pic.splider.model.PicModel;
import com.pic.splider.model.PicVipInfo;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class PicDataStoragePipeline implements Pipeline {
	public static int amount;
	public static int repeat;

	@Override
	public void process(ResultItems resultItems, Task arg1) {
		PicModel pic = resultItems.get("pic");
		if (pic != null) {
			pic.id = UUID.randomUUID().toString();
			pic.time = new Date().getTime();
			if (pic.type == 0) {//免费体验 当普通图片处理 {
				pic.isFree = 0;
			} else {
				pic.isFree = 1;
			}
			
			
			if (pic.type == 4) {//超长集合归并到街拍图片
				pic.type = 3;
			}
			
			if (pic.type == 1) { //街拍视频归并到街拍图片
				pic.type = 2;
			}
			pic.domain_type = "100001";
			PicVipInfo info = new PicVipInfo();
			info.id = pic.id;
			info.urls = pic.urls;
			info.domain_type = "100001";
			
			/*if (DBServices.getPicByUrl(pic.firstUrl) != null) {
				repeat ++;
				System.err.print("---------------一repeat----------------:"+repeat + "\n");
				
				return ;
			}*/
			amount++;
			System.err.print("---------------一amount----------------:"+amount + "\n");
			DBServices.addPic(pic);
			DBServices.addPicVipInfo(info);
		}
		 System.out.println("get page resultItems: " + resultItems.getRequest().getUrl());
		 System.out.println(pic.type + ":"+ pic.title + ":"+pic.urls);
		 System.out.println(pic.viewNumber + ":" + pic.videoTime);
	}

}
