<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*, controller.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
<title>Admin Index</title>
</head>
<body>
<%-- Check if the user is logged in. If not, redirect them to the login page. --%>
<c:if test="${empty sessionScope.adminInfo}">
    <c:redirect url="${pageContext.request.contextPath}/AdminLogin.jsp"/>
</c:if>
<div class="container">
	<h1>■Restaurante IDEALLE■</h1>
	<h2>${ sessionScope.adminInfo.admName } 様、いらしゃいませ。</h2>
	<div class="comments">${msg}</div> 
	
	<form name="frm1" action="<%= request.getContextPath() %>/menu/MenuMaintenanceSvl" method="post">
		<div onclick="document.frm1.submit();">
			<p>●メニューメンテナンス</p>
		</div>
	</form>	
	
	<form name="frm2" action="<%= request.getContextPath() %>/admin/AdminLogoffSvl" method="post">
		<div onclick="document.frm2.submit();">
			<p>●ログオフ</p>
		</div>
	</form>
</div>
</body>
</html>