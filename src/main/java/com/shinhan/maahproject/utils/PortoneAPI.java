package com.shinhan.maahproject.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/*
 * 포트원 API 호출
 * API가이드 - https://developers.portone.io/api/rest-v1?v=v1
 * 1. access_token 발급 API
 * 2. 예금주 조회 API
 * */
@Slf4j
@Configuration
public class PortoneAPI {
	
    private String imp_key = "0633384166078527";
    
    private String imp_secret = "6WkmonXcr4MBQZs8Gdkv6n2BuJLHmswAfdGi2DlEwNCpf8NKHEx97S3GMlGWvuHMQUoXB5a97f2eRk4l";
    
    private String access_token;

    /* 
	 * 2.예금주 조회 API
	 * 은행별 지정코드는 API가이드 필수 참고 해야함
	 * */
	public String getAcountHolderNM(String bank_code, String bank_name) throws UnsupportedEncodingException {
		
		String bankHolder = "";
		String strUrl = "https://api.iamport.kr/vbanks/holder";
		String queryStr = String.format("?bank_code=%s&bank_num=%s",URLEncoder.encode(bank_code, "UTF-8"),URLEncoder.encode(bank_name, "UTF-8")); 
		
		try {
			
			URL url = new URL(strUrl+queryStr);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + getAccess_token());
			
			int resposeCode = conn.getResponseCode();
			
			log.info("============= resposeCode :"+resposeCode+" ==========");
			
			if(resposeCode == 200) 
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				
				while((line=br.readLine())!= null)
				{
					sb.append(line);
					
				}
				br.close();
				
				JSONObject jsonData = new JSONObject(sb.toString());			
				JSONObject response = jsonData.getJSONObject("response");				
				bankHolder = response.getString("bank_holder");
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return bankHolder; 
	}
	
	/* 
	 * 1. access_token 발급 API
	 * 발급시 30분 동안 유효함
	 * */
	public String getAccess_token() {
		
		String strUrl = "https://api.iamport.kr/users/getToken";
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			
			JSONObject requestData = new JSONObject();
			
			requestData.put("imp_key", this.imp_key);
			requestData.put("imp_secret", this.imp_secret);
			
			bw.write(requestData.toString());
			bw.flush();
			bw.close();
			
			int resposeCode = conn.getResponseCode();
			
			log.info("============= resposeCode :"+resposeCode+" ==========");
			
			if(resposeCode == 200) 
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				
				while((line=br.readLine())!= null)
				{
					sb.append(line);
					
				}
				br.close();
			
				
				JSONObject jsonData = new JSONObject(sb.toString());
				JSONObject response = jsonData.getJSONObject("response");
				
				this.access_token = response.getString("access_token");
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.access_token;
	}
	
}
