package cn.joy.android.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;

import cn.joy.android.demo.TestApplication;
import cn.joy.android.demo.TestDialogActivity;

public class HttpUtil
{
	/**
	 * post发送数据到服务器：参数，不包含文件
	 * 成功返回response，
	 * 异常返回null
	 */
	public static HttpResponse postDataOfPost(String url,Map<String,Object> datas) throws Exception
	{
		HttpClient httpClient = createHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		Set<Entry<String,Object>> temps = datas.entrySet();
		for(Entry<String,Object> temp : temps)
		{
			String key = temp.getKey();
			Object value = temp.getValue();
			if(value==null)	value = "";
			if(key!=null)
				params.add(new BasicNameValuePair(key, value.toString()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		response = httpClient.execute(httpPost);
		
		if(response.getStatusLine().getStatusCode()==200)
		{
			String resultStr=EntityUtils.toString(response.getEntity());
			if(resultStr!=null&&resultStr.startsWith("logout,")){
				/*Message msg=new Message();
				msg.obj=resultStr.split(",")[1];
				msg.arg1=100;
				QYESApplication.getLogoutHandler().sendMessage(msg);
				response.setStatusCode(404);*/
			}
			
			return response;
		}
		return null;
	}
	
	public static HttpResponse postDataOfPostWithRetry(String url,Map<String,Object> datas) throws Exception
	{
		DefaultHttpClient httpClient = (DefaultHttpClient)createHttpClient();
		httpClient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount,
					HttpContext context) {
				 if (executionCount >= 3) {  
			            return false;  
			        }  
			        if (exception instanceof NoHttpResponseException) {  
			            return true;  
			        } else if (exception instanceof ClientProtocolException) {  
			            return true;  
			        }  
			        return false;  
			}
		});
		
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		Set<Entry<String,Object>> temps = datas.entrySet();
		for(Entry<String,Object> temp : temps){
			if(temp.getValue()!=null){
				params.add(new BasicNameValuePair(temp.getKey(),temp.getValue().toString()));
			}else{
				params.add(new BasicNameValuePair(temp.getKey(),""));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		response = httpClient.execute(httpPost);
		
		if(response.getStatusLine().getStatusCode()==200)
		{
			
			String resultStr=EntityUtils.toString(response.getEntity());
			if(resultStr!=null&&resultStr.startsWith("logout,")){
				
			}
			
			return response;
		}
		return null;
	}
	
	public static HttpResponse postMultipartEntityOfPost(String url, MultipartEntity multipartEntity) throws Exception{
		HttpClient httpClient = createHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(multipartEntity);
		HttpContext httpContext = new BasicHttpContext();
		
		return httpClient.execute(httpPost, httpContext);
	}
	
	public static HttpResponse postMultipartEntityOfPost(String url, Map<String, Object> params, MultipartEntity multipartEntity) throws Exception{
		if(params!=null){
			String encode = HTTP.UTF_8;;
			for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
				Entry<String, Object> stringEntry = it.next();
				Object val = stringEntry.getValue();
				if(val==null)	val = "";
				StringBody str = new StringBody(val.toString(), Charset.forName(encode));
				multipartEntity.addPart(stringEntry.getKey(), str);
			}
		}
		
		HttpClient httpClient = createHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(multipartEntity);
		HttpContext httpContext = new BasicHttpContext();
		
		return httpClient.execute(httpPost, httpContext);
	}
	/**
	 * get发送数据
	 */
	public static HttpResponse postDataOfGet(String url) throws Exception
	{
		HttpClient httpClient = createHttpClient();
		//https://app.actiz.com/actiz/user.do?action=getUserInfoByLoginId&loginname=zhang@qyes.com
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		if(response.getStatusLine().getStatusCode()==200)
		{
			String resultStr=EntityUtils.toString(response.getEntity());
			if(resultStr!=null&&resultStr.startsWith("logout,")){
				/*Message msg=new Message();
				msg.obj=resultStr.split(",")[1];
				msg.arg1=100;
				QYESApplication.getLogoutHandler().sendMessage(msg);
				response.setStatusCode(404);*/
			}
			
			return response;
		}
		return null;
	}
	/**
	 * Get请求Https
	 */
	public static String getDataByHttpsOfGet(String url)
	{
		String result = null;
		try
		{
			SSLContext sc= SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
		
		
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
			HttpsURLConnection conn= (HttpsURLConnection)new URL(url).openConnection();
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.connect();
			
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024*10];
			is.read(buffer);
			if(buffer!=null)
			{
				result=new String(buffer);
				
				if(result!=null&&result.startsWith("logout,")){
					/*Message msg=new Message();
					msg.obj=result.split(",")[1];
					msg.arg1=100;
					QYESApplication.getLogoutHandler().sendMessage(msg);*/
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 设置HttpClient，解决https和http差异
	 */
	public static HttpClient createHttpClient()
	{
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		
		/*TrustManager easyTrustManager = new X509TrustManager() {
			public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[0]; 
			}
		};*/
		//SSLContext sslcontext = SSLContext.getInstance("TLS");
		//sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
		SSLSocketFactory sf = SSLSocketFactory.getSocketFactory();
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		
		schReg.register(new Scheme("https", sf, 443));
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		return new DefaultHttpClient(conMgr, params); 
    }
	public static String encodeParam(String url)
	{
		try
		{
			return URLEncoder.encode(URLEncoder.encode(url));
		}
		catch(Exception e)
		{
			return url;
		}
	}
	
	public static void testRequest(){
		Context ctx = TestApplication.getInstance().getApplicationContext();
		Intent intent = new Intent(ctx, TestDialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}
}