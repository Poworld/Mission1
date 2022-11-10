package seoulApi;

import seoulApi.SaveDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SeoulWifiApi {
	private static HttpURLConnection conn;
	private  static URL url;
	private static SaveDB db = new SaveDB();
	
	public static String openApiUrl= "http://openapi.seoul.go.kr:8088";
	public static String apiKey = "576c6a57686c656536395447694277";
	public static String dataType = "json";
	public static String serviceName = "TbPublicWifiInfo";
	
	public static int dbUpdate()  {
		int cnt = 1;
		int totalCnt = 0;
		int tempo = 1000;
	
		StringBuilder sb = null;
		String endIdx = "1000";
		
		while(true) {
			
			try {
				StringBuilder urlString = new StringBuilder(openApiUrl);
				
				urlString.append("/" + URLEncoder.encode(apiKey, "UTF-8"));
				urlString.append("/" + URLEncoder.encode(dataType, "UTF-8"));
				urlString.append("/" + URLEncoder.encode(serviceName, "UTF-8"));
				urlString.append("/" + URLEncoder.encode(String.valueOf(cnt), "UTF-8"));
				urlString.append("/" + URLEncoder.encode(endIdx, "UTF-8"));
				url = new URL(urlString.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json; charset=UTF-8");
				conn.setDoInput(true);
				System.out.println("Response code: " + conn.getResponseCode());
				
				BufferedReader rd = null;
				
				
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					System.err.print("에러가 발생했습니다: " + conn.getResponseCode());
					break;
				}
				
				sb = new StringBuilder();
				String line;
				while((line = rd.readLine()) != null) {
					sb.append(line);
				}
				
				rd.close();
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Gson gson = new GsonBuilder().setLenient().create() ;
			
			JsonObject objData =  gson.fromJson(sb.toString().trim(), JsonObject.class);
			JsonObject jsonInfo = (JsonObject) objData.get(serviceName);
			totalCnt = Integer.valueOf(jsonInfo.get("list_total_count").toString());
			
			JsonArray jsonArr = jsonInfo.get("row").getAsJsonArray();
			String[] sqls = jsonArrToSql(jsonArr);
			
			for(String sql : sqls) {
				int result = db.insertDB(sql);
				
				if(result == -1) {
					return -1;
				}
			}
			
			cnt += tempo;
			
			if(cnt+999 > totalCnt) {
				endIdx = String.valueOf(totalCnt);
			}else {
				endIdx = String.valueOf(cnt + 999);
			}
			
			if(cnt >= totalCnt) {
				break;
			}
		}
		return totalCnt;
	}
	
	private static String[] jsonArrToSql(JsonArray arr) {
		ArrayList<String> list = new ArrayList<>();
		for(JsonElement element : arr) {
			Gson gson = new Gson();
			Map<String, String> map = gson.fromJson(element.getAsJsonObject(), Map.class);
			
			String keyStr = "";
			String valueStr = "";
			
			for(String key : map.keySet()) {
				keyStr += ("\"" + key + "\"" + ",");
				valueStr += ("\"" + map.get(key) + "\"" + ",");
			}
			
			keyStr = keyStr.substring(0, keyStr.length() - 1);
			valueStr = valueStr.substring(0, valueStr.length() - 1);
			
			String sql = "INSERT OR IGNORE INTO SEOUL_WIFI (" + keyStr + ") VALUES (" + valueStr + ");";
			
			list.add(sql);
		}
		
		return list.toArray(new String[list.size()]);
		
	}
	
}