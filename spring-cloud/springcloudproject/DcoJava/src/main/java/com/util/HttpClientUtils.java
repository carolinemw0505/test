package com.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.common.GlobalVariables;

public class HttpClientUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(HttpClientUtils.class);
	
	public static String sendPost(String url,String param,Boolean isAgent){
			String result="";
			try{
				URL readUrl=new URL(url);
				String host=GlobalVariables.proxyHost;
				int port=GlobalVariables.proxyPort;
				URLConnection conn=null;
				if(isAgent && GlobalVariables.proxyopenflag==1){
					Proxy proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress(host,port));
					conn=readUrl.openConnection(proxy);
				}else{
					conn=readUrl.openConnection();
				}
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
				
				//发送POST请求必须设置这两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				
				
				try(PrintWriter out=new PrintWriter(conn.getOutputStream())){
					out.print(param);
					out.flush();
				}
				try(BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"))){
					String line;
					while((line=in.readLine())!=null){
						result+="\n"+line;
					}
				};
				
			}catch(Exception e){
				//System.out.println("发送POST请求出现异常!"+e);
				logger.error("发送POST请求出现异常:"+e.getMessage());
				e.printStackTrace();
			}
			return result;
	}
	
	public static String sendGet(String url,String param,boolean auth){
		String result="";
		String getUrl=url;
		if(!StringUtils.isEmpty(param.trim()) && !auth){
			getUrl=url.replace("http://api.csdn.net", "https://api.csdn.net")+"?"+param;
		}
		try{
			URL readUrl=new URL(getUrl);
			URLConnection conn=readUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MISE 6.0;Windows NT 5.1;SV1)");
			
			if(auth){
				conn.setRequestProperty("Authorization", "Bearer "+param);
			}
			
			try(BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"))){
				String line;
				while((line=in.readLine())!=null){
					result+="\n"+line;
				}
			}
			logger.info("result:"+result);
		}catch(Exception e){
			logger.error("发送GET请求出现异常:"+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String send(String urlParam,String param) {
		HttpURLConnection connection = null;
		try {
			final URL url = new URL(urlParam);
			// Create connection
			if(GlobalVariables.proxyopenflag==1){
				String host=GlobalVariables.proxyHost;
				int port=GlobalVariables.proxyPort;
				@SuppressWarnings("static-access")
				Proxy proxy=new Proxy(Proxy.Type.DIRECT.HTTP,new InetSocketAddress(host,port));
				connection = (HttpURLConnection) url.openConnection(proxy);
			}else{
				connection=(HttpURLConnection)url.openConnection();
			}
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(5000);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			final String payload=param;

			// Send request
			final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(payload);
			wr.flush();
			wr.close();

			// Get Response
			final InputStream is = connection.getInputStream();
			final BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}

			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
	

	
	public static Map<String,String> param2Map(String param){
		Map<String,String> result=new HashMap<String,String>();
		String[] arrs=param.split("&");
		String key="";
		for(int i=0;i<arrs.length;i++){
			String[] arrsTwoLayer=arrs[i].split("=");
			key=arrsTwoLayer[0].replace("\n", "");
			if(arrsTwoLayer.length<2){
				result.put(key,"");
			}else{
				result.put(key, arrsTwoLayer[1]);
			}
		}
		return result;
		
	}
}

