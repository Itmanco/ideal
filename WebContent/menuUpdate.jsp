<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controller.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Menu</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
<style>
.form-actions {
    display: flex;           
    justify-content: flex-end; 
    align-items: center;
    background-color: #A4FFDB;
    gap: 10px;               
    padding: 5px;  
}
</style>
<script type="text/javascript">
function dataCheck(obj){
	var msg = "";
	if(obj.menuName.value.length < 1){
		msg = "メニュー名を入力してください。\n";
	}
	if(!obj.price.value.match(/^[0-9]+$/g)){
		msg += "価格を数値で入力してください。\n";
	}
	var i;
	for(i = 0; i < obj.orderFlg.length; i++){
		if(obj.orderFlg[i].checked) break;
	}
	if( i >= obj.orderFlg.length){
		msg += "オーダーの可否をチェックしてください。\n";
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
<%
	int typeId;
	try{
		typeId = Integer.parseInt(request.getParameter("typeId"));
	} catch(NumberFormatException e){
		typeId = 100;
	}
%>
<c:if test="${empty sessionScope.adminInfo}">
    <c:redirect url="${pageContext.request.contextPath}/AdminLogin.jsp"/>
</c:if>

<div class="container">
	<form action="MenuOperationSvl" method="post" onSubmit="return dataCheck(this);">
	<h2>新しいメニューの更新</h2>
    <div id="error-message" class="comments">        
        ${msg}
    </div>
   	<div class="form-group">
        <label for="menuName">メニュー名</label>
        <input type="text" id="menuName" name="menuName" value="${ oneMenu.menuName }"/>
    </div>
   	<div class="form-group">
        <label for="price">価格</label>
        <input type="text" id="price" name="price" value="${ oneMenu.price }"/>
    </div>
    <div class="form-group">
        <label for="orderFlg">オーダー可</label>
        <div style="flex-grow: 1; display: flex; align-items: center; padding: 5px;">
	        <input type="radio" name="orderFlg" id="orderFlg_yes" value="1" <c:if test="${oneMenu.orderFlg == 1}">checked</c:if>/>可
	        <input type="radio" name="orderFlg" id="orderFlg_no" value="0" <c:if test="${oneMenu.orderFlg == 0}">checked</c:if>/>不可
    	</div>
    </div>
	<div class="form-group">
		<label for="detail">分類</label>
		<select id="typeId" name="typeId">
        <c:forEach var="menuType" items="${mType}">
            <option value="${menuType.typeId}" <c:if test="${menuType.typeId == oneMenu.typeId}">selected</c:if>>${menuType.typeName}</option>
        </c:forEach>
    </select>
	</div>
    <div class="form-group">
        <label for="detail">コメント</label>
        <textarea id="detail" name="detail" cols="30" rows="4">${oneMenu.detail}</textarea>
    </div>
    <input type="hidden" name="mode" value="<%=MenuOperationSvl.UPDATE %>" />
    <input type="hidden" name="menuId" value="${ oneMenu.menuId }" />
	<div class="form-actions">
    	<span>※は必須入力です。</span><button type="submit">コースを追加</button>
    </div>
</form>
<div class="footer-link">
    <p><a href="MenuMaintenanceSvl?typeId=<%=typeId %>">一覧表示画面へ戻る</a></p>
</div>
</div> 
</body>
</html>
