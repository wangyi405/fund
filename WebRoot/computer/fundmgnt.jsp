<%@ page language="java" import="java.util.*,com.acbcwy.fund.service.FundInfoService,com.acbcwy.fund.model.FundInfo" pageEncoding="utf-8"%>
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
    <title>基金管理</title>
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../js/easyui/themes/color.css">
    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/highcharts.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/computer/fundmgnt.js"></script>
    <script>
      var path="<%=path%>";
	</script>
</head>
<body class="easyui-layout" data-options="border:true" style="margin:3px 1px 1px 1px;">
 	<!-- 载入菜单页面  -->
	<jsp:include page="menu.jsp"></jsp:include>
	<div data-options="region:'center',border:true">
		<div class="easyui-layout" data-options="fit:true">
		     <div region="north" style="margin:10px 10px;height:auto;" border="false">
				<div>
				    <a href="#" id="btnAddUser" class="easyui-linkbutton" iconCls="icon-edit" style="width:80px" onclick="openWinFundInfo();">修改资料</a>
			    	<a href="#" id="btnDeleteUser" class="easyui-linkbutton" iconCls="icon-add" style="width:80px" onclick="openWinYieldRate();">录入收益</a>
			    	<span>&nbsp;&nbsp;</span> <span>开始日期:</span>
			    	<span><input class="easyui-datebox"" type="text" id="beginDate" style="width:120px" data-options="formatter:myformatter,parser:myparser"></input></span>
			    	<span>结束日期:</span>
			    	<span><input class="easyui-datebox"" type="text" id="endDate" style="width:160px" data-options="formatter:myformatter,parser:myparser"></input></span>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnSearchOnClick();">查&nbsp;询</a>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnWeekOnClick();">近一周</a>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnMonthOnClick();">近一月</a>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnThreeMonthOnClick();">近三月</a>
			    	<a href="#" id="btnSearch" class="easyui-linkbutton" iconCls="icon-search" style="width:80px" onclick="btnSixMonthOnClick();">近六月</a>
		    	</div>
			</div>	
			
			 <div region="center" style="margin:1px 10px 10px 10px;height:auto;" border="false">
			 	<div class="easyui-tabs" style="width:100%;height:100%;padding:10px;">
			 		<div title="净值走势图" data-options="iconCls:'icon-help'" style="padding:10px;">
						<div id="line-graph" style='width:100%; height:90%;' ></div>
					</div>
					<div title="收益率明细" style="padding:10px;width:100%;height:100%;">
						<table id="yieldrate-grid" class="easyui-datagrid" fit="true"  striped="true" data-options="rownumbers: true,onLoadSuccess:onLoadSuccess,url:'<%=path%>/services/computer/findFundIncome',method:'get'">
							<thead>
								<tr>
									<th data-options="field:'abbrName'"   width="10%" >基金名称</th>
									<th data-options="field:'accountDay'" width="10%">日期</th>
									<th data-options="field:'buyTotalValue'" width="10%">申购总金额</th>	
									<th data-options="field:'saleTotalValue'" width="10%">赎回总金额</th>	
									<th data-options="field:'todayBookValue'" width="10%">账面净值</th>																		
									<th data-options="field:'yieldRate'"  width="13%">当日收益率</th>
									<th data-options="field:'accumulate'" width="13%">累积收益率</th> 
									<th data-options="field:'lastUpdatedByName'" width="12%">最近修改人</th> 
									<th data-options="field:'lastUpdateTime'" width="12%">最近修改时间</th> 
								</tr>
							</thead>
						</table>	
					</div>

				</div>
			 
			</div>	
		</div>
	</div>
	
	<!-- 基金资料编辑窗口 -->
	<div id="winEditFundInfo" class="easyui-window" title="基金资料编辑" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:420px;height:365px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;">
				<form id="formfundInfo" method="post">
					<table style="width:100%">
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>基金名称:  </label>
							</td>
							<td style="width:75%;">
								<input id="operation" name="operation" type="hidden">
							    <input id="fundId" name="fundId" type="hidden">
								<input class="easyui-textbox" id="fundName" name="fundName" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>基金简称:  </label>
							</td>
							<td style="width:75%;">
								<input id="operation" name="operation" type="hidden">
							    <input id="fundId" name="fundId" type="hidden">
								<input class="easyui-textbox" id="abbrName" name="abbrName" style="width:100%" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>基金经理:  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-textbox" id="manager" name="manager" style="width:100%;" data-options="required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:25%;height:35px">
								<label>基金简介 :  </label>
							</td>
							<td style="width:75%;">
								<input class="easyui-textbox" id="recommend" name="recommend" data-options="multiline:true" style="width:100%;height:100px;" >
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 10px;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveFundInfo()" style="width:80px">保&nbsp;存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#winEditFundInfo').window('close')" style="width:80px">取&nbsp;消</a>
			</div>
		</div>
	</div>
	
	<!-- 录入收益编辑窗口 -->
	<div id="winYieldRate" class="easyui-window" title="基金收益率录入" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:300px;height:370px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false" style="padding:10px;">
				<form id="formYieldRate" method="post">
					<table style="width:100%">
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>日&nbsp;&nbsp;期:  </label>
							</td>
							<td style="width:60%;">
								<input id="_operation" name="_operation" type="hidden">
							    <input id="_fundId" name="_fundId" type="hidden">
								<input class="easyui-datebox" id="accountDay" name="accountDay" style="width:100%" data-options="formatter:myformatter,parser:myparser,onSelect:onSelectAccountDay,required:true">
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>上日账面:  </label>
							</td>
							<td style="width:60%;">
								<input class="easyui-numberbox" id="preBookValue" name="preBookValue" precision="2" style="width:100%"  data-options="required:true,onChange:rateChange">
							</td>
						</tr>	
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>申购金额:  </label>
							</td>
							<td style="width:60%;">
								<input class="easyui-numberbox" id="buyTotalValue" name="buyTotalValue" precision="2" style="width:100%"  data-options="disabled:true" >
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>赎回金额:  </label>
							</td>
							<td style="width:60%;">
								<input class="easyui-numberbox" id="saleTotalValue" name="saleTotalValue" precision="2" style="width:100%"  data-options="disabled:true" >
							</td>
						</tr>
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>今日账面:  </label>
							</td>
							<td style="width:60%;">
								<input class="easyui-numberbox" id="todayBookValue" name="todayBookValue" precision="2" style="width:100%"  data-options="required:true,onChange:rateChange" >
							</td>
						</tr>						
						<tr class="base-info">
							<td style="width:40%;height:35px">
								<label>日收益率:  </label>
							</td>
							<td style="width:60%;">
								<input class="easyui-numberbox" id="yieldRate" name="yieldRate" precision="8" value="1" style="width:100%"  data-options="required:true">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 10px;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveYieldRate()" style="width:80px">保&nbsp;存</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#winYieldRate').window('close')" style="width:80px">取&nbsp;消</a>
			</div>
		</div>
	</div>
</body>