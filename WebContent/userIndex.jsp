<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*, controller.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Index</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">

</head>
<body>
<%-- Check if the user is logged in. If not, redirect them to the login page. --%>
<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="userLogin.jsp"/>
</c:if>
	<div class="container">
	<h1>■Restaurante IDEALLE■</h1>
	<h2>${ sessionScope.userInfo.usrName } 様、いらしゃいませ。</h2>
	<div class="comments">${msg}</div> 
	
	<form name="frm1" action="<%= request.getContextPath() %>/menu/showMenu" method="get">
		<div onclick="document.frm1.submit();">
			<p>●メニュー紹介</p>
		</div>
	</form>
	
	
	<form name="frm2" action="<%= request.getContextPath() %>/reserve/ReserveListSvl" method="post">
		<div onclick="document.frm2.submit();">
			<p>●ご予約</p>
		</div>	
	</form>
	<form name="frm3" action="<%= request.getContextPath() %>/user/UserUpdateSvl" method="post">
		<div onclick="document.frm3.submit();">
			<p>●お客様情報変更</p>
		</div>	
	</form>
	
	<form name="frm4" action="<%= request.getContextPath() %>/user/UserDeleteSvl" method="post">
		<div onclick="document.frm4.submit();">
			<p>●お客様脱会手続き</p>
		</div>	
	</form>
	
	<form name="frm5" action="<%= request.getContextPath() %>/user/UserLogoffSvl" method="post">
		<div onclick="document.frm5.submit();">
			<p>●ログオフ</p>
		</div>
	</form>	
</div>

</body>
</html>