package com.pic.splider.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pic.splider.model.PicModel;
import com.pic.splider.model.PicVipInfo;


public class DBServices {
	public static Logger log = LoggerFactory.getLogger(DBServices.class);
	public static int addPic(PicModel pic) {
		System.err.println("addPic");
		int result = 0;
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		sql = "insert into pic_jiepai(id,title,type,firstUrl,time,viewNumber,amout,videoTime,likeAmount,commentAmout, isFree,domain_type) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		connection = new DBConnection().getConnection();
		try {
			prest = connection.prepareStatement(sql);
			prest.setString(1, pic.id);
			prest.setString(2, pic.title);
			prest.setInt(3, pic.type);
			prest.setString(4, pic.firstUrl);
			prest.setLong(5, pic.time);
			prest.setString(6, pic.viewNumber);
			prest.setInt(7, pic.amout);
			prest.setString(8, pic.videoTime);
			prest.setInt(9, pic.likeAmount);
			prest.setInt(10, pic.commentAmout);
			prest.setInt(11, pic.isFree);
			prest.setString(12, pic.domain_type);
			result = prest.executeUpdate();
			prest.close();
			connection.close();
		} catch (Exception e) {
			System.err.println("addPic:" + e.getMessage());
		}
		return result;
	}
	
	
	public static int addPicVipInfo(PicVipInfo info) {
		System.err.println("addPicVipInfo");
		int result = 0;
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		sql = "insert into pic_vip_info(id,urls,baiduUrl,pwd,domain_type) values(?,?,?,?,?)";
		connection = new DBConnection().getConnection();
		try {
			prest = connection.prepareStatement(sql);
			prest.setString(1, info.id);
			prest.setString(2, info.urls);
			prest.setString(3, info.baiduUrl);
			prest.setString(4, info.pwd);
			prest.setString(5, info.domain_type);
			result = prest.executeUpdate();
			prest.close();
			connection.close();
		} catch (Exception e) {
			System.err.println("addPicVipInfo:"+ e.getMessage());
		}
		return result;
		// prest.close();
		// connection.close();
	}
	
	
	
	public static PicModel getPicByUrl(String url) {
		PicModel picModel = null;
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		ResultSet rs = null;
		sql = "select * from pic_jiepai  where firstUrl = ?";
		connection = new DBConnection().getConnection();
		try {
			prest = connection.prepareStatement(sql);

			prest.setString(1, url);
			rs = prest.executeQuery();
			while (rs.next()) {
				// openid,nickname,sex,city,province,headimgurl,subscribe_time,issend,checkCount
				picModel = new PicModel();
				return picModel;
			}
			prest.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("getUsers:" + e.getMessage());
		}
		return picModel;
	}

	/*public static void updateUser(User user) {
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		sql = "update film_users set checkCount = ?, checkResult = ? where openid = ?";
		connection = new DBConnection().getConnection();
		try {
			prest = connection.prepareStatement(sql);
			prest.setInt(1, user.getCheckCount());
			prest.setInt(2, user.getCheckResult());
			prest.setString(3, user.getOpenid());
			prest.executeUpdate();
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("updateUser:" + e.getMessage());
			return;
		}
	}

	public static int addResult(Result result) throws Exception {
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		sql = "insert into cnyf_comare_error_result(result,url) values(?,?)";
		connection = new DBConnection().getConnection();
		prest = connection.prepareStatement(sql);
		prest.setString(1, result.getResult());
		prest.setString(2, result.getUrl());
		return prest.executeUpdate();
		// prest.close();
		// connection.close();
	}

	public static int deleteUsers(String openid) throws Exception {
		System.out.println("deleteUsers");
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		sql = "delete from  film_users where openid=?";
		connection = new DBConnection().getConnection();
		prest = connection.prepareStatement(sql);
		prest.setString(1, openid);
		return prest.executeUpdate();
		// prest.close();
		// connection.close();
	}

	public static User getUsers(String openid) {
		User user = null;
		PreparedStatement prest;
		String sql = null;
		Connection connection;
		ResultSet rs = null;
		sql = "select * from film_users  where openid = ?";
		connection = new DBConnection().getConnection();
		try {
			prest = connection.prepareStatement(sql);

			prest.setString(1, openid);
			rs = prest.executeQuery();
			while (rs.next()) {
				// openid,nickname,sex,city,province,headimgurl,subscribe_time,issend,checkCount
				user = new User();
				user.setOpenid(rs.getString("openid"));
				user.setNickname(rs.getString("nickname"));
				user.setSex(rs.getInt("sex"));
				user.setCity(rs.getString("city"));
				user.setProvince(rs.getString("province"));
				user.setHeadimgurl(rs.getString("headimgurl"));
				user.setSubscribe_time(rs.getLong("subscribe_time"));
				user.setIssend(rs.getInt("issend"));
				user.setCheckCount(rs.getInt("checkCount"));
				user.setCheckResult(rs.getInt("checkResult"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("getUsers:" + e.getMessage());
		}
		// prest.close();
		// connection.close();
		return user;
	}

	public static List<Content> getContentsByType(String type) throws Exception {
		System.out.println("getVideoContents");
		PreparedStatement prest;
		List<Content> contents = new ArrayList<Content>();

		String sql = null;
		Connection connection;
		ResultSet rs = null;
		sql = "select * from cnyf_content where type=? order by time DESC";
		connection = new DBConnection().getConnection();
		prest = connection.prepareStatement(sql);
		prest.setString(1, type);
		rs = prest.executeQuery();
		rs.last(); // 结果集指针知道最后一行数据
		int length = rs.getRow();
		rs.beforeFirst();
		while (rs.next()) {
			String title = rs.getString("title");
			String url = rs.getString("url");
			String time = rs.getString("time");
			String pwd = rs.getString("pwd");
			Content content = new Content(url, time, pwd);
			content.setIndex(length + "");
			length--;
			contents.add(content);
		}
		// rs.close();
		// prest.close();
		// connection.close();
		return contents;
	}

	public static List<Content> getContentsBySearch(String query)
			throws Exception {
		System.out.println("getContentsBySearch");
		PreparedStatement prest;
		List<Content> contents = new ArrayList<Content>();

		String sql = null;
		Connection connection;
		ResultSet rs = null;
		sql = "select * from  cnyf_content where title = '" + query + "'";
		connection = new DBConnection().getConnection();
		prest = connection.prepareStatement(sql);
		rs = prest.executeQuery();
		while (rs.next()) {
			String title = rs.getString("title");
			String url = rs.getString("url");
			String pwd = rs.getString("pwd");
			Content content = new Content(title, url, pwd);
			contents.add(content);
		}
		// rs.close();
		// prest.close();
		// connection.close();
		return contents;
	}
*/
}
