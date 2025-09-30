<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
h1{text-align:center;}
h3{
	margin: 2px 0 5px 0;
}
p{
	color:black;
	font-size:10pt;
	margin: 2px 0 2px 30px; /* Top, Right, Bottom, Left */
}
p.detail{
	color:black;
	font-size:8pt;
	margin: 2px 0 2px 40px; /* Top, Right, Bottom, Left */
}
.content{
	width: 40%;
	margin-left: auto;
	margin-right: auto;	
}
.item-type{
	margin: 25px 0 0 0;
}
.item-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 20px 0 0 0;
    height: 10px;
}
.back{
	padding: 20px 0 0 0;
	display: block; /* Make the anchor a block-level element */
    width: 100px; /* Optional: Give it a width */
    margin: 0 auto; /* Center it horizontally */
}
.content-name {
}

.item-price {
}
.menu-list {
    list-style-type: none; 
    padding-left: 0;     
}
.menu-indent {
    margin-left: 20px; 
}
</style>
<title>Menu</title>
</head>
<body>
	<h1>メニュー紹介</h1>
	<div id="error-message" class="comments">        
        ${msg}
    </div>
	<div class="content">
	<c:forEach var="mt" items="${mType}">
	<div class="item-type">
		<h3>■${mt.typeName }</h3>
		<c:choose>
			<c:when test="${mt.typeId == 100}">
				<c:set var="currentCourseId" value="-1" />
				<c:forEach var="c" items="${courses}">
					<c:if test="${c.courseId != currentCourseId}">
						<c:if test="${currentCourseId != -1}">
		                    </ul>
		                </c:if>
		                
		               <div class="item-row">
		                   <h4 class="content-name">${c.courseName}</h4>
		                   <h4 class="align-items">
		                        &yen; ${c.price}
		                   </h4>	                   
		               </div>
		               <h5 class="menu-indent">${c.detail}</h5>
		               <ul class="menu-list menu-indent">
		           </c:if>
	               <%-- Always display the menu item --%>
               	   <li>${c.menuName}</li>
               	   <c:set var="currentCourseId" value="${c.courseId}" />
	       		</c:forEach> 
	       		<c:if test="${!courses.isEmpty()}">
                	</ul>
            	</c:if>
			</c:when>
			<c:otherwise>				
				<c:forEach var="m" items="${menus}">				
	            <c:if test="${m.typeId == mt.typeId}">
	                <div class="item-row">
	                    <p class="content-name">${m.menuName}</p>	                    
	                    <h4 class="align-items">
	                        &yen; ${m.price}
	                    </h4>	                    
	                </div>
	                <p class="detail">${m.detail}</p>
	            </c:if>          
	        	</c:forEach>
	        	<hr>
			</c:otherwise>
		</c:choose>
	</div>
	</c:forEach>	
	</div>
	<a class="back" href="#" onclick="history.back();">戻る</a>
</body>
</html>