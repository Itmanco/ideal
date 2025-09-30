<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controller.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Reserve</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/utils.js"></script>
<script>  
    window.onload = function() {
        const reservedYearInput = document.getElementById("reservedYear");
        if (reservedYearInput) {
            const reservedYear = parseInt(reservedYearInput.value);
            document.getElementById("year").value = reservedYear;
        }
        updateMonths();
    };
</script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
<style>
.form-group {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 10px; 
}
.form-group select {
    background-color: transparent; 
    flex-grow: 0;
    width: 70px;
}
.form-group select[name="courseId"] {
    width: auto;
    flex-grow: 1; 
}
</style>
</head>
<body>
<c:if test="${empty sessionScope.userInfo}">
    <c:redirect url="${pageContext.request.contextPath}/userLogin.jsp"/>
</c:if>
<div class="container">	
	<h1>${ sessionScope.userInfo.usrName }様、ご予約変更</h1>
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
        <select id="year" name="rsvYy" onchange="updateMonths()">
		    <c:forEach var="year" items="${years}">
		        <option value="${year}"
		            <c:choose>
		                <c:when test="${not empty reserve and reserve.rsvYy == year}">
		                    selected
		                </c:when>
		                <c:when test="${empty reserve and year == currentYear}">
		                    selected
		                </c:when>
		            </c:choose>
		        >${year}</option>
		    </c:forEach>        	
		</select>年
    	<select id="month" name="rsvMm" onchange="updateDays()"></select>月
        <select id="day" name="rsvDd"></select>日
    </div>
   	<div class="form-group">
    	<label>時刻</label>
        <select name="rsvHh">
        	<c:forEach var="hour" items="${hours}">
        		<option value="${hour}" <c:if test="${not empty reserve and reserve.rsvHh == hour}">selected</c:if>>${hour}</option>
        	</c:forEach>        	
    	</select>時
    	<select name="rsvMi">
        	<c:forEach var="minute" items="${minutes}">
        		<option value="${minute}" <c:if test="${not empty reserve and reserve.rsvMi == minute}">selected</c:if>>${minute}</option>
        	</c:forEach>        	
    	</select>分
    </div>
    <div class="form-group">
    	<label>人数</label>
        <select name="person">
        	<c:forEach var="person" items="${people}">
        		<option value="${person}" <c:if test="${not empty reserve and reserve.person == person}">selected</c:if>>${person}</option>
        	</c:forEach>        	
    	</select>名    	
    </div>
    <div class="form-group">
    	<label>コース</label>
        <select name="courseId">
        	<c:forEach var="course" items="${courseList}">
        		<option value="${course.courseId}" <c:if test="${not empty reserve and reserve.courseId == course.courseId}">selected</c:if>>${course.courseName}</option>
        	</c:forEach>        	
    	</select>    	
    </div>
   	
    <input type="hidden" name="mode" value="<%=ReserveOperationSvl.UPDATE %>" />
    <input type="hidden" name="usrId" value="${ sessionScope.userInfo.usrId }" />
    <input type="hidden" name="rsvId" value="${ reserve.rsvId }" />
	
	<input type="hidden" id="currentYear" value="${currentYear}" />
    <input type="hidden" id="currentMonth" value="${currentMonth}" />
    <input type="hidden" id="currentDay" value="${currentDay}" />
    <c:if test="${not empty reserve}">
        <input type="hidden" id="reservedYear" value="${reserve.rsvYy}" />
        <input type="hidden" id="reservedMonth" value="${reserve.rsvMm}" />
        <input type="hidden" id="reservedDay" value="${reserve.rsvDd}" />
    </c:if>

	<div class="form-actions">
    	<button type="submit">変更</button>
    </div>
	</form>
	    <div class="footer-link">
	    <p><a href="ReserveListSvl">予約一覧に戻る</a></p>
	</div>
</div> 
</body>
</html>