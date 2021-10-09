<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	if(session.getAttribute("loginUser")==null){
	    response.sendRedirect(path+"/computer/login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
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
    <!-- 载入菜单页面  -->
	<jsp:include page="menu.jsp"></jsp:include>
	<!-- 工作区 -->
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
		    <!-- 工具栏 及 搜索框-->
	        <div region="north" style="margin:10px 10px;height=auto;" border="false">   
				<div>
				    <a href="#" id="btnAddUser" class="easyui-linkbutton" iconCls="icon-add" style="width:80px" onclick="createUser()">开&nbsp;户</a>
			    	<a href="#" id="btnDeleteUser" class="easyui-linkbutton" iconCls="icon-remove" style="width:80px" onclick="deleteUser();">销&nbsp;户</a>
			    	<span>&nbsp;&nbsp;</span> <span>用户名称:</span>
			    	<span><input class="easyui-textbox" type="text" id="c_userName" style="width:120px"></input></span>
			    	<span>身份证:</span>
			    	<span><input class="easyui-textbox" type="text" id="c_idCardNo" style="width:160px"></input></span>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnSearchOnClick();">查&nbsp;询</a>
		    	</div>
			</div>
			
			<!--用户列表 -->
			<div region="center" style="padding:5px;" border="false">  
			 	<table id="user-grid" class="easyui-datagrid" fit="true"  striped="true" data-options="singleSelect:false,collapsible:false,url:'<%=path%>/services/computer/searchuser',method:'get'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'userId',resizable:true,hidden:true" width="0%">用户ID</th>
							<th data-options="field:'userName',resizable:true,sortable:false" width="15%">姓名</th>
							<th data-options="field:'idCardNo',resizable:true,sortable:false" width="15%">身份证</th>
							<th data-options="field:'phone',resizable:true,sortable:false" width="15%">联系电话</th>
							<th data-options="field:'email',resizable:true" width="15%"">邮箱</th>
							<th data-options="field:'userType',resizable:true,sortable:false,formatter:formatUserType" width="10%">用户类型</th>
							<th data-options="field:'totalMoney',resizable:true,sortable:false" width="10%">资金总额</th>
							<th data-options="field:'operation',resizable:true,sortable:false,formatter:formatOperation" width="20%">操作</th>
						</tr>
					</thead>
			    </table>
			</div>
		</div>
	</div>
	
	<!-- 用户编辑窗口 -->
	<div id="editUser" class="easyui-window" title="用户编辑" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:420px;height:420px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;">
				<form id="userForm" method="post">
					<table style="width:100%">
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>姓&nbsp;&nbsp;名: </label>
							</td>
							<td style="width:75%;">
								<input id="operation" name="operation" type="hidden">
							    <input id="userId" name="userId" type="hidden">
								<input class="easyui-textbox" id="userName" name="userName" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>密&nbsp;&nbsp;码: </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-passwordbox" id="password" name="password" iconWidth="28" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>身份证号:  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-textbox" id="idCardNo" name="idCardNo" iconWidth="28" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>用户类型:  </label>
							</td>
							<td style="width:75%;">
								<select class="easyui-combobox" id="userType" name="userType"  data-options="required:true"   style="width:100%;">
									<option value="0">系统管理员</option>
									<option value="1" selected>投资用户</option>
								</select>
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>联系电话:  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-textbox" id="phone" name="phone"  style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>邮&nbsp;&nbsp;箱:  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-textbox" id="email" name="email"  style="width:100%" >
							</td>
						<tr>			
						<tr class="buyOrSale-info">
							<td style="width:25%;height:35px">
								<label>金&nbsp;&nbsp;额:  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-numberbox" id="investAmount" name="investAmount" precision="2" value="0" style="width:50%" >
								<input type="radio" class="buy" name="buyOrSales" value="1" checked="checked" style="width:10%" disabled="false">申购
					            <input type="radio" class="sale" name="buyOrSales" value="-1" style="width:10%" disabled="false" >赎回
							</td>
						<tr>				
					</table>
					
				</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 10px;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveUser()" style="width:80px">保&nbsp;存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#editUser').window('close')" style="width:80px">取&nbsp;消</a>
			</div>
		</div>
	</div>
	
	<!-- 资金明细 -->
	<div id="moneyDetail" class="easyui-window" title="资金明细" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:60%;height:80%;padding:1px;">
		<div class="easyui-layout" data-options="fit:true">
			<div region="center" style="padding:1px;" border="false">  
			 	<table id="invest-grid" class="easyui-datagrid" fit="true"  striped="true" data-options="rownumbers:true,singleSelect:false,collapsible:false,url:'<%=path%>/services/computer/showmoneydetail',method:'get'">
					<thead>
						<tr>
							<th data-options="field:'userName',resizable:true" width="15%">用户</th>  
							<th data-options="field:'investDate',resizable:true" width="20%">日期</th>  
							<th data-options="field:'investAmount',resizable:true,sortable:false" width="15%">金额</th>
							<th data-options="field:'investDirection',resizable:true,sortable:false,formatter:formatBuyOrSale" width="15%">申赎</th>
							<th data-options="field:'createdByName',resizable:true,sortable:false" width="15%">经办人</th>  
							<th data-options="field:'createTime',resizable:true" width="20%">办理时间</th>
						</tr>
					</thead>
			    </table>
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:$('#moneyDetail').window('close');" style="width:80px">Ok</a>
			</div>
		</div>

	</div>
</body>