<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.*, controller.*" %>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete User</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
<style>
.form-actions {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 1px;
}
</style>
</head>
<body>

<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="/userLogin.jsp"/>
</c:if>

<div class="container">
<form action="<%= request.getContextPath() %>/user/UserOperationSvl" method="post">
	<h2>お客様脱会手続き</h2>
    <div class="comments">        
        ${msg}
    </div>
    <div class="form-group required">
        <label>お客様ID</label>
        <div class="display-value">${user.usrId}</div>
    </div>
    <div class="form-group required">
        <label>お名前</label>
        <div class="display-value">${user.usrName}</div>
    </div>
    <div class="form-group">
        <label>住所</label>
        <div class="display-value">${user.address}</div>
    </div>
    <div class="form-group">
        <label>電話番号</label>
        <div class="display-value">${user.phone}</div>       
    </div>
    <div class="form-group">
        <label>e-mail</label>
        <div class="display-value">${user.mail}</div>
    </div>
    <input type="hidden" name="mode" value="<%=UserOperationSvl.DELETE %>" />	
    <input type="hidden" name="usrId" value="${user.usrId}" />
    <div class="form-actions">
        <span>※確認し、脱会ボタンをクリック下ください。</span>
        <button type="submit">変脱会</button>
    </div>
    </form>
	<div class="footer-link">
    	<a href="<%= request.getContextPath() %>/userIndex.jsp">処理メニュー戻る</a>
	</div>
 </div>   

</body>
</html>
