<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="seoulApi.UserDB"%>
<%@ page import="seoulApi.User" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="./style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<p>
		<a href="./index.jsp">홈</a> | <a href="./history.jsp">위치 히스토리 목록</a> | <a href="./dbUpdate.jsp">Open API 와이파이 정보 가져오기</a>
	</p>
	<table>
		<thead class="table-head">
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody class="table-body">
			<%
				UserDB db = new UserDB();
				ArrayList<User> userList = new ArrayList<>();
				String sql = "SELECT * FROM USER_HISTORY;";
				
				userList = db.readDB(sql);
				
				for(User user: userList) {
					out.print("<tr>");
					out.print("<th>" + user.getID() + "</th>");
					out.print("<th>" + user.getLAT() + "</th>");
					out.print("<th>" + user.getLNT() + "</th>");
					out.print("<th>" + user.getREG_DTTM() + "</th>");
					out.print("<th><button>삭제</button></th>");
					out.print("</tr>");
				}
				
			%>
		</tbody>
	</table>
</body>
</html>