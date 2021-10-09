 
<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<script type="text/javascript" src="../js/computer/menu.js"></script>
	</head>
	<body>
	    <!-- 菜单栏 -->
		<div data-options="region:'north',border:false" style="height: 30px; padding: 0px 0px 2px 0px;background:blue; ">
			<div class="easyui-panel" data-options="fit:true" style="background:#FCFCFC;">
				<a href="#" id="btnUsermgnt" class="easyui-linkbutton" data-options="plain:true">用户管理</a>
				<a href="#" id="btnFundmgnt" class="easyui-linkbutton" data-options="plain:true">基金管理</a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#mm'">系统管理</a>
			</div>
			<!--系统管理下拉菜单  -->
			<div id="mm" style="width:150px;">
					<div data-options="" id="mmBackup" >数据备份</div>
			    	<div data-options="" id="mmRestore">数据恢复</div>
			    	<div class="menu-sep"></div>
			    	<div data-options="" id="mmExit">退出系统</div>
			</div>
		</div>
		
		<div id="winRestore" class="easyui-window" title="恢复数据" data-options="modal:true,iconCls:'icon-save',closed:'true'" style="width:350px;height:200px;padding:20px;">
			<form id="uploadForm" name="uploadForm" method="POST"  enctype="MULTIPART/FORM-DATA"> 
				<div >
					<input id="filename" name="filename" class="easyui-filebox" style="width:100%" data-options="prompt:'请选择一个文件...'">
				</div>
				
				<div style="width:100%;text-align:center;padding:5px 0 0;margin-top:40px;">
					<a href="#" id="btnUpload" class="easyui-linkbutton" data-options="required:true" style="width:80px">上传文件</a>
					<a href="#" id="btnCloseRestore" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" >关闭</a>
				</div>
			</form>
		</div>
	</body>
</html>	















