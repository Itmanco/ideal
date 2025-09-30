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
function confirmDelete(obj) {
    var confirmed = confirm("本当にこのコースを削除しますか？");
    if(confirmed) {
        return true;
    } else {
        return false;
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
    int typeID;
    try{
        typeID = Integer.parseInt(request.getParameter("typeID"));
    } catch(NumberFormatException e){
        typeID = 100;
    }
%>
<div class="container">
    <form action="CourseOperationSvl" method="post" onsubmit="return confirmDelete(this);">
        <h2>コースの削除</h2>
        <div class="comments">
            ${msg}
        </div>
        
        <c:if test="${not empty oneCourse}">
            <div class="form-group">
                <label for="courseName">メニュー名</label>
                <div class="display-value">${oneCourse.get(0).courseName}</div>
            </div>
            <div class="form-group">
                <label for="price">価格</label>
                <div class="display-value">&yen; ${oneCourse.get(0).price}</div>
            </div>
            <div class="form-group">
                <label for="orderFlg">オーダー可</label>
                <div class="display-value">
                    <c:if test="${oneCourse.get(0).orderFlg == 1}">可</c:if>
                    <c:if test="${oneCourse.get(0).orderFlg == 0}">不可</c:if>
                </div>
            </div>
            <div class="form-group">
		        <label for="detail">コメント</label>
		        <textarea id="detail" name="detail" cols="30" rows="4"> ${oneCourse.get(0).detail }</textarea>
		    </div>
		    
		    <c:if test="${oneCourse.get(0).typeName != null}">
		    	<c:forEach var="menu" items="${oneCourse}">
		    		<div class="form-group">
				        <label>${menu.typeName }</label>
				        <div class="display-value">${menu.menuName}</div>
				    </div>
		    	</c:forEach>
		    </c:if>
        
            <%-- Hidden fields to pass data for deletion --%>
            <input type="hidden" name="typeId" value="100" />
            <input type="hidden" name="courseId" value="${oneCourse.get(0).courseId }" />
            <input type="hidden" name="mode" value="<%=CourseOperationSvl.DELETE %>" />
            
            <div class="form-actions">
                <button type="submit">コースを削除</button>
            </div>
        </c:if>
        <c:if test="${empty oneCourse}">
            <p style="text-align:center; color:red;">指定されたコース情報が見つかりませんでした。</p>
        </c:if>
    </form>
    <div class="footer-link">
        <p><a href="MenuMaintenanceSvl?typeID=<%=typeID %>">一覧表示画面へ戻る</a></p>
    </div>
</div>
</body>
</html>