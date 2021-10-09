<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.removeAttribute("loginUser");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>登录界面</title>
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/color.css">
    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/computer/login.js"></script>
</head>

<body>
	<div id="win" class="easyui-window" title="登录窗口" modal="true" closable="true" collapsible="false" minimizable="false" maximizable="false" style="width:340px;height:220px;">
		<form id="loginForm" style="padding:10px 20px 10px 40px;" action="computer/usermgnt.jsp" method="post">
			<table style="width:100%">
				<tr>
					<td style="width:25%;height:35px">
						<label>名&nbsp;&nbsp;称: </label>
					</td>
					<td style="width:75%;">
						<input class="easyui-textbox" id="userName" name="userName" style="width:100%" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td style="width:25%;height:35px">
						<label>密&nbsp;&nbsp;码: </label>
					</td>
					<td style="width:75%;">
						<input class="easyui-passwordbox" id="password" name="password" iconWidth="28" style="width:100%" missingMessage="该输入项为必输项" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td  colspan=2>
						<div id="errorTip" style="padding:5px;text-align:center;color:red;margin-bottom:20px;" />
					</td>
				</tr>
			</table>		
			<div style="padding:5px;text-align:center;">
				<a href="javascript:login()" class="easyui-linkbutton" icon="icon-ok" id="btnOK">确 认</a>
				<a href="javascript:cancel()" class="easyui-linkbutton" icon="icon-cancel" id="btnCancel">取 消</a>
			</div>	
		</form>  

	</div>
</body>

</html>
