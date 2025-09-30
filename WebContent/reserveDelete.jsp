<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controller.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Reserve</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
<style>
.form-group {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 10px; 
}
</style>
</head>
<body>
<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="${pageContext.request.contextPath}/userLogin.jsp"/>
</c:if>
<div class="container">	
	<h1>${ sessionScope.userInfo.usrName }様、ご予約取消</h1>
    <div class="comments" id="error-message">        
        ${msg}
    </div> 
    <form action="ReserveOperationSvl" method="post">
    <div class="form-group">
    	<label>予約番号</label>
        <div class="display-value">${reserve.rsvId}</div>   	
    </div>
   	<div class="form-group">
    	<label>日付</label>
        <div class="display-value">${reserve.rsvYy}年${reserve.rsvMm}月${reserve.rsvDd}日</div>   
    </div>
   	<div class="form-group">
    	<label>時刻</label>
        <div class="display-value">${reserve.rsvHh}時${reserve.rsvMi}分</div>   
    </div>
    <div class="form-group">
    	<label>人数</label>
        <div class="display-value">${reserve.person }名</div>     	
    </div>
    <div class="form-group">
    	<label>コース</label>
        <div class="display-value">${reserve.courseName }</div> 	
    </div>
   	
    <input type="hidden" name="mode" value="<%=ReserveOperationSvl.DELETE %>" />
    <input type="hidden" name="rsvId" value="${ reserve.rsvId }" />
	

	<div class="form-actions">
    	<button type="submit">取消</button>
    </div>
	</form>
	    <div class="footer-link">
	    <p><a href="ReserveListSvl">予約一覧に戻る</a></p>
	</div>
</div> 
</body>
</html>