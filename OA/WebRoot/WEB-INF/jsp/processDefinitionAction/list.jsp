<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>审批流程列表</title>
    <%@include file="/WEB-INF/jsp/public/commons.jspf" %>
    <script type="text/javascript">
	     function showProcessImage( pdId ){
	        pdId=encodeURI(pdId);//需要手动编码否者浏览器不会帮我们通过这种方式传递URL进行编码，导致id=new String (id.getBytes("iso-8859-1"),"utf-8");也会是乱码
	        pdId=encodeURI(pdId);//再次编码，将“中”对应的“%E4%B8%AD”-> “%25E4%25B8%25AD”,在服务器端解码即可id=URLDecoder.decode(id,"utf-8");能够实现不依赖于服务器端的配置
            var url = "processDefinition_showProcessImage.action?id="+pdId; // + processDefId;
           // window.open(url);//打开新的窗口,用IE弹出窗口时带有工具栏
            window.showModalDialog(url,null,"dialogHeight:600px;dialogWidth:450px;resizable:yes");//显示更加简洁
            
        }
    </script> 
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="style/images/title_arrow.gif"/> 审批流程管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align=center valign=middle id=TableTitle>
            	<td width="200px">流程名称</td>
				<td width="80px">最新版本</td>
                <td width="300px">说明</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="processDefList">
            <s:iterator value="#processDefinitions">
			<tr class="TableDetail1 template">
					<td>${name}&nbsp;</td>
					<td align="CENTER">${version}&nbsp;</td>
					<td>${description}&nbsp;</td>
					<td><s:a onClick="return delConfirm()" action="processDefinition_delete">
					    <s:param name="key" >${key}</s:param>
					           删除</s:a>
						<a href="javascript: showProcessImage('${id}')">查看流程图</a>
					</td>
			</tr>
			</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <table border="0" cellspacing="0" cellpadding="10" align="left">
                <tr>
			        <td><div class="FuncBtn">
                            <div class=FuncBtnHead></div>
                            <div class=FuncBtnMemo>
                               <s:a action="processDefinition_addUI">部署流程定义文档</s:a>
                            </div>
                            <div class=FuncBtnTail></div>
                        </div>
                    </td>
                </tr>
			</table>
        </div>
    </div>
</div>

<div class="Description">
	说明：<br />
	1，列表显示的是所有流程定义（不同名称）的最新版本。<br />
	2，删除流程定义时，此名称的所有版本的流程定义都会被删除。<br />
</div>

</body>
</html>
