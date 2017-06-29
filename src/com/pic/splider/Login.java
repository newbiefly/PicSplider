package com.pic.splider;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Login {
	
	
	 
	 
	 public static void main(String[] args) {
		 post();
	}
	 
	 
	 
	  public static String post() {  
		  HttpClient httpclient=new DefaultHttpClient();
	        String resStr = "";  
	        String url = "http://www.jiepaiss.com/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=L284L";
	        // 创建httppost  
	        HttpPost httppost = new HttpPost(url);  
	      //设置参数  
            List<NameValuePair> params = new ArrayList<NameValuePair>(); 
            params.add(new BasicNameValuePair("cookietime","2592000"));  
            params.add(new BasicNameValuePair("formhash","007834df"));  
            params.add(new BasicNameValuePair("password","www.baidu.com"));  
            params.add(new BasicNameValuePair("questionid","0"));  
            params.add(new BasicNameValuePair("sechash","SM6RlmZ0"));  
            params.add(new BasicNameValuePair("username","baiduvip"));  
            params.add(new BasicNameValuePair("referer","http://www.jiepaiss.com/"));  
            
	        UrlEncodedFormEntity uefEntity;  
	        try {  
	            uefEntity = new UrlEncodedFormEntity(params, "utf-8");  
	            httppost.setEntity(uefEntity);  
	            HttpResponse response;  
	            response = httpclient.execute(httppost);  
	            HttpEntity entity = response.getEntity();  
	            if (entity != null) {  	
	                resStr = EntityUtils.toString(entity,"utf-8");  
	                //System.out.println(resStr.toString());  
	            }  
	            Header[] h =response.getAllHeaders();  
	            for (Header header : h) {  
	                System.out.println(header.toString());  
	            }  
	            //读取cookie并保存文件  
	            List<Cookie> cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();    
	            if (cookies.isEmpty()) {    
	                System.out.println("None");    
	            } else {    
	                for (int i = 0; i < cookies.size(); i++) {  
	                    System.out.println("- " + cookies.get(i).getName() + ":" + cookies.get(i).getValue());  
	                }    
	            }  
	            
	            HttpGet httpget = new HttpGet("http://www.jiepaiss.com/thread-10354-1-1.html");  
	            HttpResponse response2 = httpclient.execute(httpget);          
		        System.out.println("StatusCode -> " + response2.getStatusLine().getStatusCode());  
		          
		        HttpEntity entity2 = response2.getEntity();          
		        String jsonStr = EntityUtils.toString(entity2);//, "utf-8");  
		        System.out.println(jsonStr);  
	            
	            
	            
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            // 关闭连接,释放资源  
	           // httpclient.getConnectionManager().shutdown();  
	        }  
	        return resStr;  
	    }  
	  
	  
	  
	  public static void requestGet() throws Exception {  
	        CloseableHttpClient httpclient = HttpClientBuilder.create().build();  
	          
	        HttpGet httpget = new HttpGet("http://www.jiepaiss.com/thread-10354-1-1.html");  
	  
	        //配置请求的超时设置  
	        RequestConfig requestConfig = RequestConfig.custom()    
	                .setConnectionRequestTimeout(50) 
	                .setConnectTimeout(50)    
	                .setSocketTimeout(50).build();    
	        httpget.setConfig(requestConfig);   
	          
	        CloseableHttpResponse response = httpclient.execute(httpget);          
	        System.out.println("StatusCode -> " + response.getStatusLine().getStatusCode());  
	          
	        HttpEntity entity = response.getEntity();          
	        String jsonStr = EntityUtils.toString(entity);//, "utf-8");  
	        System.out.println(jsonStr);  
	          
	        httpget.releaseConnection();  
	}  
	
	
	
	
	 

}
