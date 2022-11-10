<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="seoulApi.SaveDB"%>
<%@ page import="seoulApi.SeoulWifi"%>
<%@ page import="seoulApi.UserDB" %>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="./location.js" defer></script>
<script type="text/javascript" src="./wifiInfo.js" defer></script>

</head>

<body>
	<h1>와이파이 정보 구하기</h1>
	<p>
		<a href="./index.jsp">홈</a> | <a href="./history.jsp">위치 히스토리 목록</a> |
		<a href="./dbUpdate.jsp">Open API 와이파이 정보 가져오기</a>
	</p>
	<form>
		LAT: <input type="text" id="LAT" name="lat" required>, LNT: <input
			type="text" id="LNT" name="lnt" required>
		<button type="button" class="locationBtn">내 위치 가져오기</button>
		<button type="submit" value="주소" class="wifiInfoBtn">근처
			WIFI정보 보기</button>
	</form>
	<table>
		<thead class="table-head">
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스 구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody class="table-body">
			<tr class="baseTable">
				<th colspan="17">위치 정보를 입력한 후에 조회를 주세요.</th>
			</tr>
			<%
			try {
				double lat = Double.parseDouble(request.getParameter("lat"));
				double lnt = Double.parseDouble(request.getParameter("lnt"));
			%>
			<script>
				let baseTable = document.querySelector(".baseTable");
				let jspLat = document.querySelector("#LAT");
				let jspLnt = document.querySelector("#LNT");

				jspLat.value = <%=lat%>;
				jspLnt.value = <%=lnt%>;

				baseTable.remove();
			</script>
			<%
			SaveDB db = new SaveDB();
			UserDB userDB = new UserDB();
			ArrayList<SeoulWifi> dbList = new ArrayList<>();

			String sql = String.format("SELECT (ABS(%f - sw.LAT) + ABS(%f - sw.LNT)) * 100 as distance, * ", lat, lnt);
			sql += "FROM SEOUL_WIFI as sw ";
			sql += "where distance < 1 ";
			sql += String.format("ORDER BY ABS(%f - sw.LAT) + ABS(%f - sw.LNT) ASC ", lat, lnt);
			sql += "LIMIT 20;";

			dbList = db.readDB(sql);

			for (SeoulWifi sw : dbList) {
				out.print("<tr>");
				out.print("<th>" + String.format("%.5f", sw.getDISTANCE()) + "</th>");
				out.print("<th>" + sw.getMGR_NO() + "</th>");
				out.print("<th>" + sw.getWRDOFC() + "</th>");
				out.print("<th>" + sw.getMAIN_NM() + "</th>");
				out.print("<th>" + sw.getADRES1() + "</th>");
				out.print("<th>" + sw.getADRES2() + "</th>");
				out.print("<th>" + sw.getINSTL_FLOOR() + "</th>");
				out.print("<th>" + sw.getINSTL_TY() + "</th>");
				out.print("<th>" + sw.getINSTL_MBY() + "</th>");
				out.print("<th>" + sw.getSVC_SE() + "</th>");
				out.print("<th>" + sw.getCMCWR() + "</th>");
				out.print("<th>" + sw.getCNSTC_YEAR() + "</th>");
				out.print("<th>" + sw.getINOUT_DOOR() + "</th>");
				out.print("<th>" + sw.getREMARS3() + "</th>");
				out.print("<th>" + sw.getLAT() + "</th>");
				out.print("<th>" + sw.getLNT() + "</th>");
				out.print("<th>" + sw.getWORK_DTTM() + "</th>");
				out.print("</tr>");
				System.out.print(sw.getMAIN_NM());
			}
			
			sql = userDB.insertSql(lat, lnt);
			userDB.insertDB(sql);

			} catch (Exception e) {
			e.printStackTrace();
			}
			%>

		</tbody>
	</table>
</body>
</html>