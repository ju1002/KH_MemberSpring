<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>하이하이</h1>
	
	<!-- main.jsp가 나오게 하기 위해서 -->
	<%-- <jsp:forwarad page="" /> --%>
	<jsp:forward page="WEB-INF/views/main.jsp" />
</body>
</html>