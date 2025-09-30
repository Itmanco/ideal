<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, controller.*, model.*" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reserve Completion</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
</head>
<body>


<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="userLogin.jsp"/>
</c:if>

<div class="container">
	<h4>${ sessionScope.userInfo.usrName }様、ご予約が完了いたしました。</h4>
	<h4>${reserve.rsvYy }年${reserve.rsvMm }月${reserve.rsvDd }日  ${reserve.rsvHh }時${reserve.rsvMi }分</h4>
	<h4>${ reserve.courseName }コース ${ reserve.person }名様</h4>
	<h4>ご予約番号は${ reserve.rsvId }番です。</h4>
	<h4>ご来店の際、受付にお申し付けください。</h4>

    <div class="footer-link">
	    <p><a href="ReserveListSvl">予約一覧に戻る</a></p>
	</div>
</div>
</body>
</html>