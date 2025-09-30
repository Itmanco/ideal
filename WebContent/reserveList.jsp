<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, model.*, controller.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="incFile.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/utils.js"></script> 
<title>User Reservation List</title>
</head>
<body>
<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="${pageContext.request.contextPath}/userLogin.jsp"/>
</c:if>


<div class="form-container">
	<h1>${ sessionScope.userInfo.usrName }様、ご予約一覧</h1>
	<div class="comments">${ msg }</div>
	<table>
	    <thead>
	      <tr>
	        <th>NO</th>
	        <th>ご予約日時</th>
	        <th>人数</th>
	        <th>コース名</th>
	        <th>テーブル名</th>
	        <th>登録日時</th>
	        <th></th>
	        <th></th>
	      </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="rsv" items="${reserveList}">
	    		<tr>
				<td>${rsv.rsvId }</td>
		        <td>${rsv.rsvYy}年${rsv.rsvMm}月${rsv.rsvDd}日 ${rsv.rsvHh}時${rsv.rsvMi}分</td>
		        <td>${rsv.person }</td>
		        <td>${rsv.courseName }</td>
		        <td>${rsv.tableName }</td>
		        <td>${rsv.appYy}年${rsv.appMm}月${rsv.appDd}日 ${rsv.appHh}時${rsv.appMi}分</td>
		        <td>
					<form action='<%=request.getContextPath() + "/reserve/ReserveUpdateSvl"%>' method="post">
						<input type="hidden" name="rsvId" value="${rsv.rsvId}"/>
						
						<c:choose>
						    <c:when test="${rsv.passed}">
						        <input type="submit" value="変更" disabled="disabled" class="disabled-button"/>
						    </c:when>
						    <c:otherwise>
						        <input type="submit" value="変更"/>
						    </c:otherwise>
						</c:choose>
					</form>
		        </td>
		        <td>
					<form action='<%=request.getContextPath() + "/reserve/ReserveDeleteSvl"%>' method="post">
						<input type="hidden" name="rsvId" value="${rsv.rsvId}"/>
						<input type="submit" value="取消"/>
					</form>
		        </td>
		        <tr>
	    	</c:forEach>
	    </tbody>    
	</table>
	<div class="actions">
		<form action='<%= request.getContextPath() + "/reserve/ReserveInsertSvl" %>' method="post">
    		<button type="submit">新規ご予約</button>
    	</form>
    </div>    
	<div class="footer-link">
		<a href="<%=request.getContextPath()%>/userIndex.jsp">処理メニュー戻る</a>
	</div>
</div>
</body>
</html>