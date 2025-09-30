<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, controller.*, model.*" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Completion</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
</head>
<body>


<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="userLogin.jsp"/>
</c:if>

<div class="container">
	<h1>お客様情報の登録が完了いたしました。</h1>
	<div class="comments"></div>
	<h3>${ sessionScope.userInfo.usrName }様のお客様ID [ ${ sessionScope.userInfo.usrId }] でございます。</h3>
	<h3>ログインの際に必要となりますので</h3>
	<h3>大切に保管してください。</h3>

    <div class="footer-link">
    <a href="<%= request.getContextPath() %>/userIndex.jsp">戻る</a>
	</div>
</div>
</body>
</html>