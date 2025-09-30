<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controller.*, model.*" %>
<%@ include file="incFile.jsp" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Delete Menu</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");	
%>
<jsp:useBean id="oneMenu" class="model.Menu" scope="request"/>
<%
	int typeId = oneMenu.getTypeId();
%>
<div class="container">
	<form action="MenuOperationSvl" method="post">
	<h2>メニューの削除</h2>
    <div class="comments">        
        ${msg}
    </div>
	<div class="form-group">
        <label for="menuName">メニュー名</label>
        <div class="display-value">${ oneMenu.menuName }</div>
    </div>
   	<div class="form-group">
        <label for="price">価格</label>
        <div class="display-value">${ oneMenu.price }</div>
    </div>
    <div class="form-group">
        <label for="orderFlg">オーダー可</label>
        <div class="display-value">${ oneMenu.orderFlg } &nbsp;&nbsp; (1==>可,0==>不可)</div>
    </div>
    <div class="form-group">
        <label for="detail">分類</label>
         <div class="display-value">${ oneMenu.typeName }</div>
    </div>  
    <div class="form-group">
        <label for="detail">コメント</label>
        <textarea id="detail" name="detail" rows="3" readonly><%= fmtNull(oneMenu.getDetail()) %></textarea>
    </div>   
    <input type="hidden" name="mode" value="<%=MenuOperationSvl.DELETE %>" />
	<input type="hidden" name="menuId" value='${ oneMenu.menuId }' />
	<input type="hidden" name="typeId" value='${ oneMenu.typeId }' />
	<div class="form-actions">
    	<button type="submit">メニューを削除</button>
    </div>
	</form>
	    <div class="footer-link">
	    <a href="MenuMaintenanceSvl?typeId=<%=typeId %>">一覧表示画面へ戻る</a>
	</div>
</div>   
</body>
</html>