package com.shinhan.maahproject.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CertificationService {

	@Autowired
	MemberRepository mRepo;
	
	@Value("${imp_key_cert}")
	private String imp_key;

	@Value("${imp_secret_cert}")
	private String imp_secret;
	
	public String cert(String imp_uid, String memberId) {
		
		String impKey = "본인키";
		String impSecret = "본인키";
		String strUrl = "https://api.iamport.kr/users/getToken"; // 토큰 요청 보낼 주소
		String access_token = " ";
		String name = "";

		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//Post 요청
			conn.setRequestMethod("POST");
			conn.setDoOutput(true); // outputStreamm으로 post 데이터를 넘김
			
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			
			//파라미터 세팅
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			JSONObject requestObj = new JSONObject();
			requestObj.put("imp_key", imp_key);
			requestObj.put("imp_secret", imp_secret);
			
			bw.write(requestObj.toString());
			bw.flush();
			bw.close();
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				
				// 토큰 값 빼기
				String response = sb.toString();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(sb.toString());
				access_token = (String) ((JSONObject) jsonObj.get("response")).get("access_token");

				//본인인증한 사람 정보 빼오기
				String getPaymentUrl = "https://api.iamport.kr/certifications/" + imp_uid;
				HttpURLConnection getConn = (HttpURLConnection) new URL(getPaymentUrl).openConnection();
				getConn.setRequestMethod("GET");
				getConn.setRequestProperty("Content-Type", "application/json");
				getConn.setRequestProperty("Authorization", "Bearer " + access_token);
				
				int getResponseCode = getConn.getResponseCode();
				
				if (getResponseCode == 200) { // 성공
					BufferedReader getBr = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
					StringBuilder getResponseSb = new StringBuilder();
					String getLine;
					while ((getLine = getBr.readLine()) != null) {
						getResponseSb.append(getLine).append("\n");
					}
					getBr.close();

					String getResponse = getResponseSb.toString();
					System.out.println("GET 응답 결과: " + getResponse);

					JSONParser parser1 = new JSONParser();
					JSONObject phoneJson1 = (JSONObject) parser1.parse(getResponse);

					name = (String) ((JSONObject) phoneJson1.get("response")).get("name");

				} else {
					System.out.println("GET 에러 응답 메시지: " + getConn.getResponseMessage());
				}
			}	else {
				System.out.println("error");
			}
			
		} catch(IOException | ParseException e) {
			e.printStackTrace();
		} 
		
		//본인인증 후 가져온 이름과 user의 이름이 같으면 가상카드번호 발급
		for(MemberVO mvo : mRepo.findByMemberId(memberId)) {
			if(name == mvo.getMemberName()) {
				log.info(name);
			}
		}
		
		//본인인증한 사람의 이름 반환
		return name;
	}
}
