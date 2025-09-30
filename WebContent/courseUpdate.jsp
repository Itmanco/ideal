<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controller.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Course</title>
<script type="text/javascript">
function dataCheck(obj){
	var msg = "";
	if(obj.courseName.value.length < 1){
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
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
</head>
<body>
<c:if test="${empty sessionScope.adminInfo}">
    <c:redirect url="${pageContext.request.contextPath}/AdminLogin.jsp"/>
</c:if>
<%
	int typeId;
	try{
		typeId = Integer.parseInt(request.getParameter("typeId"));
	} catch(NumberFormatException e){
		typeId = 100;
	}
%>
<div class="container">
	<form action="CourseOperationSvl" method="post" onSubmit="return dataCheck(this);">
	<h2>コースの更新</h2>
    <div class="comments" id="error-message">        
        ${msg}
    </div>
   	<div class="form-group">
        <label for="courseName">コース名※</label>
        <input type="text" id="courseName" name="courseName" value="${oneCourse.get(0).courseName }"/>
    </div>
   	<div class="form-group">
        <label for="price">価格※</label>
        <input type="text" id="price" name="price" value="${oneCourse.get(0).price }"/>
    </div>
    <div class="form-group">
        <label for="orderFlg">オーダー可※</label>
        <div style="flex-grow: 1; display: flex; align-items: center; padding: 5px;">
			<input type="radio" name="orderFlg" id="orderFlg" value="1" <c:if test="${oneCourse[0].orderFlg == 1}">checked</c:if>/> 可
			<input type="radio" name="orderFlg" id="orderFlg" value="0" <c:if test="${oneCourse[0].orderFlg == 0}">checked</c:if>/> 不可			
    	</div>
    </div>
    
			
    <div class="form-group">
        <label for="detail">コメント</label>
        <textarea id="detail" name="detail" cols="30" rows="4"> ${oneCourse.get(0).detail }</textarea>
    </div> 

	 <c:forEach var="tml" items="${typeMuneList}">	 
        <c:if test="${not empty tml}">
        	<div class="form-group">
        	<c:choose>
			    <c:when test="${tml.get(0).typeId == 200}">
			    	<label for="appetizerId">${tml.get(0).typeName}</label>
			        <select id="appetizerId" name="appetizerId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                    	<%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
			    <c:when test="${tml.get(0).typeId == 210}">
			    	<label for="soupId">${tml.get(0).typeName}</label>
			        <select id="soupId" name="soupId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                        <%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
			    <c:when test="${tml.get(0).typeId == 220}">
			    	<label for="pastaId">${tml.get(0).typeName}</label>
			        <select id="pastaId" name="pastaId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                        <%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
			    <c:when test="${tml.get(0).typeId == 300}">
			    	<label for="meatId">${tml.get(0).typeName}</label>
			        <select id="meatId" name="meatId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                        <%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
   			    <c:when test="${tml.get(0).typeId == 310}">
			    	<label for="fishId">${tml.get(0).typeName}</label>
			        <select id="fishId" name="fishId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                        <%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
			    <c:when test="${tml.get(0).typeId == 400}">
			    	<label for="dessertId">${tml.get(0).typeName}</label>
			        <select id="dessertId" name="dessertId">
	                    <option value="0">選択してください</option>
	                    <c:forEach var="menuItem" items="${tml}">
	                        <%-- Use a variable to track if this item is selected --%>
            				<c:set var="isSelected" value="false" />            
				            <%-- Check if the menu item exists in the 'oneCourse' list --%>
				            <c:forEach var="courseItem" items="${oneCourse}">
				                <c:if test="${menuItem.menuId == courseItem.menuId}">
				                    <c:set var="isSelected" value="true" />
				                </c:if>
				            </c:forEach>
	                        <option value="${menuItem.menuId}" <c:if test="${isSelected}">selected</c:if>>${menuItem.menuName}</option>
	                    </c:forEach>
	                </select>
			    </c:when>
			</c:choose>
            </div>		    
        </c:if>
	</c:forEach> 	
	<input type="hidden" name="typeId" value="100" />
	<input type="hidden" name="courseId" value="${oneCourse.get(0).courseId }" />	
    <input type="hidden" name="mode" value="<%=CourseOperationSvl.UPDATE %>" />
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