<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<HTML>
<HEAD>
    <title>用户登陆</title>
    <%@include file="/WEB-INF/jsp/public/commons.jspf" %> 
	<LINK HREF="style/blue/login.css" type=text/css rel=stylesheet />
	<script type="text/javascript">
        //再被嵌套时就刷新上级窗口
        if(window.parent!=window){
           window.parent.location.reload(true);
        }
	
	</script>
</HEAD>

<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody >
 <s:form action="user_login">
    <DIV ID="CenterAreaBg">
        <DIV ID="CenterArea">
            <DIV ID="LogoImg"><IMG BORDER="0" SRC="style/blue/images/logo.png" /></DIV>
            <DIV ID="LoginInfo">
                <TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
                    <tr>
                         <td colspan="3">
                             <font color="red"><s:fielderror/></font>
                         </td>
                    </tr>
                    <TR>
                        <TD width=45 CLASS="Subject"><IMG BORDER="0" SRC="style/blue/images/login/userId.gif" /></TD>
                        <TD><s:textfield size="20"  cssClass="TextField"  name="loginName" /></TD>
                        <TD ROWSPAN="2" STYLE="padding-left:10px;"><INPUT TYPE="image" SRC="style/blue/images/login/userLogin_button.gif"/></TD>
                    </TR>
                    <TR>
                        <TD CLASS="Subject"><IMG BORDER="0" SRC="style/blue/images/login/password.gif" /></TD>
                        <TD><s:password size="20"  cssClass="TextField"  showPassword="false"  name="password" /></TD>
                    </TR>
                </TABLE>
            </DIV>
            <DIV ID="CopyRight"><A HREF="javascript:void(0)">&copy; 2010 版权所有 itcast</A></DIV>
        </DIV>
    </DIV>
</s:form>
</BODY>

</HTML>

