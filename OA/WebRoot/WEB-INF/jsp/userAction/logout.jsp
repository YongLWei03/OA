<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>

<head>

<title>您已退出Itcast OA系统</title>
	    <%@include file="/WEB-INF/jsp/public/commons.jspf" %> 
</head>

<body>
	<table border=0 cellspacing=0 cellpadding=0 width=100% height=100%>
		<tr>
			<td align=center>
				<div id=Logout>
					<div id=AwokeMsg><img id=LogoutImg src="style/blue/images/logout/logout.gif" border=0 /><img id=LogoutTitle src="style/blue/images/logout/logout1.gif" border=0 /></div>
					<div id=LogoutOperate>
                    <img src="style/blue/images/logout/logout2.gif" border=0 /> <a href="user_loginUI.action">重新进入系统</a>
                    <img src="style/blue/images/logout/logout3.gif" border=0 /> <a href="javascript: window.close();">关闭当前窗口</a>
                    </div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
