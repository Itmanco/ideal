<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="controller.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
</head>
<body>
<div class="container">
    <h1>■Restaurante IDEALLE■</h1>
    <div class="comments">${ msg }</div> 

	<p class="submit-trigger">
	    <a href="<%= request.getContextPath() %>/menu/ShowMenuSvl">●メニュー紹介</a>
	</p>
	
	<p class="submit-trigger"><a href="<%= request.getContextPath() %>/userLogin.jsp">●ログイン</a></p>
	
	<p><a href="<%= request.getContextPath() %>/userInsert.jsp">●新規お客様登録</a></p>
	<p class="last-item-spacing"><a href="<%= request.getContextPath() %>/adminLogin.jsp">●管理者ログイン</a></p>
</div>
</body>
</html>