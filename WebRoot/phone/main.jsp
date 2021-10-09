<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	if(session.getAttribute("loginUser")==null){
	    response.sendRedirect(path+"/phone/login.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">  
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>欢迎使用基金管理平台</title>  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/metro/easyui.css">  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/mobile.css">  
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">  

    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>  
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script> 
    <script type="text/javascript" src="../js/easyui/jquery.easyui.mobile.js"></script>     
    <script type="text/javascript" src="../js/easyui/highcharts.js"></script>    
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/phone/main.js"></script>
    
    <script>
      var path="<%=path%>";
	</script>
	<style>
		.user-label { text-align: right; padding-right: 5px;  }
		.user-summary {text-align: left; padding-left: 50px; font-weight:bold; color:blue;}
	</style>
</head>
	<body>
		<div class="easyui-navpanel" style="position:relative;padding:20px">
			<div class="easyui-accordion" fit="true" border="false">
				<div title="用户收益汇总">
					<ul class="m-list">
					    <li><span class="user-label">用&nbsp;&nbsp;户：</span><span class="user-summary" id="_userName"></span></li>
						<li><span class="user-label">总资产：</span><span class="user-summary" id="totalMoney"></span></li>
						<li><span class="user-label">总收益：</span><span class="user-summary" id="totalAdd"></span></li>
						<li><span class="user-label">收益率：</span><span class="user-summary" id="totalYieldRate"></span></li>
					</ul>
				</div>
				<div title="基金收益率曲线" style="padding:0px">
					<div id="line-graph" style='width:100%; height:70%;' ></div>
	
				</div>
				<div title="用户投资明细" style="padding:0px">
					<div id="invest-detail" style='width:100%;height:100%;' >
						<table id="invest-grid" class="easyui-datagrid" fit="true"  striped="true" 
						       data-options="singleSelect:false,collapsible:false,url:'<%=path%>/services/computer/showmoneydetail',method:'get'">
							<thead>
								<tr>
									<th data-options="field:'investDate',resizable:true" width="40%">日期</th>  
									<th data-options="field:'investAmount',resizable:true,sortable:false" width="40%">金额</th>
									<th data-options="field:'investDirection',resizable:true,sortable:false,formatter:formatBuyOrSale" width="20%">申赎</th>
								</tr>
							</thead>
					    </table>
					</div>
				</div>
				<div title="修改用户资料" style="padding:0px">
					<form id="userForm" method="post">
						<ul class="m-list">
						    <li><span class="user-label">新密码：</span>
						        <input type="hidden" id="userId"> 
						    	<input class="easyui-passwordbox" id="password" name="password" iconWidth="28" style="width:80%">					    	
						    </li>
						    <li><span class="user-label">姓&nbsp;&nbsp;名：</span>
						    	<input class="easyui-textbox" id="userName" name="userName" style="width:80%" data-options="disabled:true">
						    </li>
							<li><span class="user-label">身份证号</span>
								<input class="easyui-textbox" id="idCardNo" name="idCardNo" style="width:80%" data-options="disabled:true">
							</li>
							<li><span class="user-label">联系电话</span>
								<input class="easyui-textbox" id="phone" name="phone" style="width:80%" data-options="required:true">
							</li>
							<li><span class="user-label">邮&nbsp;&nbsp;箱：</span>
								<input class="easyui-textbox" id="email" name="email" style="width:80%">
							</li>
						</ul>
						<div style="text-align:center;" stype="width:100%;">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveUser()" style="width:80px">保&nbsp;存</a>
						</div>
					</form>	
				</div>
			</div>
		</div>
	</body>
</html>