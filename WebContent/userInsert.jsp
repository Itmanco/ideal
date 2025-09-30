<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.*, controller.*" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert User</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
<style>
.form-actions {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 1px;
}

</style>
<script type="text/javascript">
function dataCheck(obj){

	// Simple regex for email validation
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	
	if(obj.usrName.value.length < 1){
		msg = "ユーザー名を入力してください。\n";
	} else if(obj.password.value.length < 1){
		msg = "パスワードを入力してください。\n";
	}else if(!obj.phone.value.match(/^[0-9]+$/g)){
		msg += "電話番号を数値で入力してください。\n";
	}else if(!obj.mail.value.match(emailRegex)){
        msg += "有効なメールアドレスを入力してください。\n"; 
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
<div class="container">
<form action="<%= request.getContextPath() %>/user/UserOperationSvl" method="post" onSubmit="return dataCheck(this);">
	<h1>お客様情報変更</h1>
    <div id="error-message" class="comments">        
        ${msg}
    </div>
    <div class="form-group required">
        <label for="name">お名前</label>
        <input type="text" id="usrName" name="usrName" value="${ user.usrName }" required >
    </div>
    <div class="form-group">
        <label for="address">住所</label>
        <input type="text" id="address" name="address" value="${ user.address }">
    </div>
    <div class="form-group">
        <label for="phone">電話番号</label>
        <input type="text" id="phone" name="phone" value="${ user.phone }">
    </div>
    <div class="form-group">
        <label for="email">e-mail</label>
        <input type="email" id="mail" name="mail" value="${ user.mail }">
    </div>
    <div class="form-group required">
        <label for="password">パスワード※</label>
        <input type="password" id="password" name="password" required>
    </div>
    <input type="hidden" name="mode" value="<%=UserOperationSvl.INSERT %>" />	
    <div class="form-actions">
        <span>※は必須入力です。</span>
        <button type="submit">変更</button>
    </div>
    </form>
    <div class="footer-link">
    <a href="<%= request.getContextPath() %>/userIndex.jsp">処理メニュー戻る</a>
	</div>
 </div>   

</body>
</html>
