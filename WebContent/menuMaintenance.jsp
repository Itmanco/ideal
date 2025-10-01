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
<style>
h1{text-align:center;background:#007B66;color:#ECFFF3; width: 100%;} /* Added width to h1 to stretch */
table{width:100%; text-align:center; margin:auto;} /* Set table to 100% width and centered */
caption{font-size:18pt;color:#009967;}
tr#data0{background:#E3FFE3;}
tr#data1{background:#A4FFDB;}
th{background:#007B66;color:#ECFFF3;}
td{text-align:left;vertical-align:top;white-space:nowrap;}
td#outer{padding:10px;background:#FAFFE3;}
#code{width:40px;text-align:center;}
#menu{width:250px;}
#price{width:60px;text-align:right;padding-right:5px;}
#comm{width:300px;}
#ord{width:100px;text-align:center;}
#btn{width:100px;}	
div#type1{width:120px;border:solid gray 1px;
background:#E3FFE3;color:#007B53;padding:2px;cursor:pointer;margin-top:5px;}
div#type2{width:120px;border:solid gray 1px;
background:#007B53;color:#E3FFE3;padding:2px;cursor:pointer;margin-top:5px;}
.comments{
	padding-top:6px;
	padding-bottom:4px;
	text-align:center;
	color:blue;
	font-size:14pt;
	font-weight: 700;
}
.right-cell {
    display: flex;
    flex-direction: column;
    justify-content: center; /* This centers content vertically */
    align-items: flex-end; /* Align content to the right */
    padding-left: 10px; /* Add some padding to the left */
}
.form-container {
    width: 95%;
    padding: 20px;
    border: 4px double black;
    background-color: white;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 20px auto; /* Added to center the container */
}

/* Updated style for the button's parent div to control spacing */
.footer-link {
    margin-top: 50px;       /* Adds space above the link */
    text-align: center; 
}

.footer-link a {
    text-decoration: none;
    color: black;
    border-bottom: 1px solid black;
    padding-bottom: 2px;
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
			<td id="outer">
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
					<caption> &lt;&lt;&lt;
					<% try{ %>
					<%= ((Menu)menu.get(0)).getTypeName() %>
					<% }catch(Exception e){ %>
					メニューがありません。
					<% } %>
					&gt;&gt;&gt;
					</caption>
					<tr>
						<th id="code">ID</th>
						<th id="menu">メニュー</th>
						<th id="price">値段</th>
						<th id="comm">コメント</th>
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
					<tr>
						<form action='<%= request.getContextPath() + "/menu/MenuInsertSvl" %>' method="post">
						<th colspan="7">
							<input type="hidden" name="typeId" value="<%= typeId %>"/>							
							<input type="submit" value='<%= typeId == 100 ? "新しいコースの追加" : "新しいメニューの追加" %>'>
						</th>						
						</form>
					</tr>
					<tr>
                        <td colspan="7">
                            <div class="footer-link">
                                <a href="${pageContext.request.contextPath}/adminIndex.jsp">処理選択に戻る</a>
                            </div>
                        </td>
                    </tr>					
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
