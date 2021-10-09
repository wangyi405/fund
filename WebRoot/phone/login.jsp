<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	session.removeAttribute("loginUser");
	
%>
<!DOCTYPE html>
<html>
<head>
    <script>
      var path="<%=path%>";
	</script>
    <meta charset="UTF-8">  
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>欢迎使用基金管理平台</title>  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/metro/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">  
    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>  
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../js/easyui/jquery.easyui.mobile.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script> 
    <script type="text/javascript" src="../js/phone/login.js"></script>
    <style>
    	form,div,input  {font-size:18px}
    </style>
</head>
<body>
	<div class="easyui-navpanel" style="position:relative;padding:20px">
		<header>
			<div class="m-toolbar">
				<div class="m-title"><span id="fundAbbrName"></span>管理平台</div>
			</div>
		</header>
		<div style="margin:20px auto;width:100px;height:100px;border-radius:100px;overflow:hidden">
			<img src="../images/logo_1.jpg" style="margin:0;width:100%;height:100%;">
		</div>
		<form id="formLogin" >
			<div style="margin-bottom:24px">
				<input id="userName" name="userName"  class="easyui-textbox" label="姓&nbsp;名: &nbsp;" data-options="required:true" prompt="用户姓名" style="height:36px;width:100%">
			</div>
			<div style="margin-bottom:24px">
				<input id="password" name="password" class="easyui-passwordbox" missingMessage="该输入项为必输项" data-options="required:true"  label="密&nbsp;码: &nbsp;" prompt="密码" style="height:36px;width:100%">
			</div>
			
			<div id="errorTip" style="margin-bottom:24px;text-align:center;color:red;">
			</div>
			
			
			<div style="text-align:center;margin-top:30px">
				<a href="javascript:login();" class="easyui-linkbutton" style="width:100%;height:50px">
					<span style="font-size:16px">登&nbsp;录</span>
				</a>
			</div>
		</form>
	</div>
</body>

</html>