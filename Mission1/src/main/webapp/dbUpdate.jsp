<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="seoulApi.SeoulWifiApi" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<style>
		p {
			font-size: 32px;
			font-weight: 900;
			display: flex;
			justify-content: center;	
		}
		
		a {
			width: 100%;
			display: flex;
			justify-content: center;
		}
	</style>
</head>
<body>
	<%
	int result = SeoulWifiApi.dbUpdate();
	if(result == -1) {
		out.print("<p>에러가 발생했습니다.</p>");
	}else {
		out.print("<p>" + result + "개의 WIFI 정보를 정상적으로 저장하였습니다.</p>");
	}
	%>
	<a href="./index.jsp">홈 으로 가기</a>
</body>
</html>