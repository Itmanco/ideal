<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, model.*, controller.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="incFile.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu Maintenance</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/stylesheet.css" />
<style>
tr#data0{background:#E3FFE3;}
tr#data1{background:#A4FFDB;}
td#outer{padding:10px;background:#FAFFE3; margin-left: 5px;}
#code{width:40px;text-align:center;}
#menu{width:250px;text-align:left;}
#comm{width:300px;text-align:left;}
#ord{width:70px;text-align:center;}
div#type1{width:120px;border:solid gray 1px;
background:#f5f5dc;color:rgb(70, 70, 70);padding:2px;cursor:pointer;margin-top:5px;font-weight: 700;}
div#type2{width:120px;border:solid gray 1px;
background:rgb(70, 70, 70);color:#E3FFE3;padding:2px;cursor:pointer;margin-top:5px;font-weight: bold;}
.right-cell {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;
    padding-left: 10px;
}

</style>
</head>
<body>
<c:if test="${empty sessionScope.adminInfo}">
    <c:redirect url="${pageContext.request.contextPath}/AdminLogin.jsp"/>
</c:if>
<div class="form-container">
<h1>===メニューマスターメンテナンス===</h1>
<div class="comments">${ msg }</div> 
<% 
	String[] order = {"不可","可"};
	NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("ja","JP"));
	int typeId;
	String style = "id = 'type1'";

	try{
		typeId = Integer.parseInt(request.getParameter("typeId"));
	} catch(NumberFormatException e){
		typeId = 100;
	}		
%>
<jsp:useBean id="mType" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="menu" class="java.util.ArrayList" scope="request"/>

	<table>
		<tr>
			<td id="outer" style="width: 150px;">
			<%
			for(int i = 0; i < mType.size(); i++){
				MenuType mt = (MenuType)mType.get(i);
				if(typeId == mt.getTypeId()){
					style = " id = 'type2'";
				}else {
					style = " id = 'type1'";
				}			
			%>
			<form name="frm<%=i %>" action="MenuMaintenanceSvl" method="post">				
				<input type="hidden" name="typeId" value="<%= mt.getTypeId() %>"/>
				<div <%=style %> onclick="document.frm<%= i %>.submit();">
					<%= mt.getTypeName()%>
				</div>
			</form>
			<% } %>
			</td>
			<td id="outer" class="right-cell">
				<table>
					<span> &lt;&lt;&lt;
					<% try{ %>
					<%= ((Menu)menu.get(0)).getTypeName() %>
					<% }catch(Exception e){ %>
					メニューがありません。
					<% } %>
					&gt;&gt;&gt;
					</span>
					<tr>
						<th id="code">ID</th>
						<th>メニュー</th>
						<th id="price">値段</th>
						<th>コメント</th>
						<th id="ord">オーダー可</th>
						<th id="btn" colspan="2"></th>
					</tr>	
					
					<%					
					for(int i = 0; i < menu.size(); i++){
						Menu m = (Menu)menu.get(i);					
					%>
					<tr id="data<%= i % 2 %>">
						<td id="code"><%=m.getMenuId()%></td>
						<td id="menu"><%=m.getMenuName()%></td>
						<td id="price"><%=cf.format(m.getPrice())%></td>
						<td id="comm"><%=fmtNull(m.getDetail())%></td>
						<td id="ord"><%=order[m.getOrderFlg()]%></td>						
						<td style="width:50px">
							<form action='<%=request.getContextPath() + "/menu/MenuUpdateSvl"%>' method="post">

							<input type="hidden" name="menuId" value="<%=m.getMenuId()%>"/>
							<input type="hidden" name="typeId" value="<%=typeId%>"/>	
							<input type="submit" value="使新"/>
							</form>
						</td>
						<td style="width:50px">
							<form action='<%=request.getContextPath() + "/menu/MenuDeleteSvl"%>' method="post">

							<input type="hidden" name="menuId" value="<%=m.getMenuId()%>"/>
							<input type="hidden" name="typeId" value="<%=typeId%>"/>	
							<input type="submit" value="削除"/>
							</form>
						</td>	
					</tr>
					
					<% } %>
				</table>
				<div class="actions">
					<form action='<%= request.getContextPath() + "/menu/MenuInsertSvl" %>' method="post">
			    		<input type="hidden" name="typeId" value="<%= typeId %>"/>							
						<input type="submit" value='<%= typeId == 100 ? "新しいコースの追加" : "新しいメニューの追加" %>'>
			    	</form>
			    </div>
			</td>
		</tr>
	</table>
	<div class="footer-link">
		<a href="${pageContext.request.contextPath}/adminIndex.jsp">処理選択に戻る</a>
	</div>
</div>
</body>
</html>
