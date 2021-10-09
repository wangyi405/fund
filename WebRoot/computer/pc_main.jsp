<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	if(session.getAttribute("loginUser")==null){
	    response.sendRedirect(path+"/login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/color.css">
    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/computer/pc_main.js"></script>
    <script type="text/javascript" src="../js/computer/usermgnt.js"></script>
    
    <script>
      var path="<%=path%>";
	</script>
</head>
<body class="easyui-layout" data-options="border:true" style="margin:3px 1px 1px 1px;">
	<div data-options="region:'north',border:false" style="height: 30px; padding: 0px 0px 1px 0px; ">
		<div class="easyui-panel" data-options="fit:true">
			<a href="javascript:usermgnt()" class="easyui-linkbutton" data-options="plain:true">用户管理</a>
			<a href="javascript:fundmgnt()" class="easyui-linkbutton" data-options="plain:true">基金管理</a>
			<a href="javascript:exit()" class="easyui-linkbutton" data-options="plain:true">退出系统</a>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div region="center" style="padding:5px;" border="false">  
	 	<table id="user-grid" class="easyui-datagrid" fit="true"  striped="true" data-options="singleSelect:false,collapsible:false,url:'../js/computer/search_user.json',method:'get'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'userId',resizable:true,sortable:false" width="0%">用户ID</th>
					<th data-options="field:'userName',resizable:true,sortable:false" width="10%">名称</th>
					<th data-options="field:'idCardNo',resizable:true,sortable:false" width="15%">身份证</th>
					<th data-options="field:'phone',resizable:true,sortable:false" width="10%">联系电话</th>
					<th data-options="field:'email',resizable:true" width="15%"">邮箱</th>
					<th data-options="field:'userTypeName',resizable:true,sortable:false" width="10%">用户类型</th>
					<th data-options="field:'createdByName',resizable:true,sortable:false" width="10%">创建人</th>
					<th data-options="field:'createTime',resizable:true,sortable:false" width="10%">创建时间</th>
					<th data-options="field:'lastUpdatedByName',resizable:true,sortable:false" width="10%">最近修改人</th>
					<th data-options="field:'lastUpdateTime',resizable:true,sortable:false" width="10%">修改时间</th>
				</tr>
			</thead>
	    </table>
	</div>
			 
		</div>
	</div>
</body>