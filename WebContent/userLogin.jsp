<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.*, controller.*" %>
<%@ include file="incFile.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Login</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
<script type="text/javascript">
function dataCheck(obj){
	
	if(obj.usrId.value.length < 1){
		msg = "ユーザー名を入力してください。\n";
	}
	if(obj.password.value.length < 1){
		msg = "パスワードを入力してください。\n";
	}

	const errorMessageDiv = document.getElementById("error-message");
	if(msg.length > 0){
		errorMessageDiv.innerHTML = msg;
		return false;
	} else {
		errorMessageDiv.innerHTML = "";
		return true;
	}
}
</script>
</head>
<body>


<c:if test="${not empty sessionScope.loggedInUser}">
    <c:redirect url="userIndex.jsp"/>
</c:if>

<div class="container">
<form action="<%= request.getContextPath() %>/user/UserLoginSvl" method="post" onSubmit="return dataCheck(this);">
	<h1>お客様ログイン</h1>
    <div id="error-message" class="comments">        
        ${msg}
    </div>   
	<div class="form-group required">
        <label for="usrId">お客様ID</label>
        <input type="text" id="usrId" name="usrId" required >
    </div>
    <div class="form-group required">
        <label for="password">パスワード</label>
        <input type="password" id="password" name="password" required>       
    </div>
	<div class="form-actions">
        <button type="submit">登録</button>
    </div>
    <div class="footer-link">
    	<a href="<%= request.getContextPath() %>/home.jsp">ホームページ</a>
	</div>	
</form>
</div>
</body>
</html>