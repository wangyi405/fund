//初始化数据
$(function(){
	$.ajax({
		type:"POST",
		url:path+"/services/computer/findFundInfo",
		data:{}
	});	
	

});

Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function rateChange(){
	//今日收益=(今日账面-今日申购 ) / (上日账面 - 今日赎回)
	var preVal=$("#preBookValue").val() * 1;
	var todayVal=$("#todayBookValue").val() * 1;
	if(preVal>0 && todayVal){
		var buyTotalValue=$("#buyTotalValue").val() * 1;
		var saleTotalValue=$("#saleTotalValue").val() * 1;	
		var yieldRate=(todayVal - buyTotalValue) / (preVal - saleTotalValue);
		$("#yieldRate").numberbox("setText",yieldRate);
	}else{
		$("#yieldRate").numberbox("setText",1);
	} 
}


//载入用户管理页面
function usermgnt(){
	window.location=path+"/computer/usermgnt.jsp";
}

//载入基金管理页面
function fundmgnt(){
	window.location=path+"/computer/fundmgnt.jsp";
}

function exit(){
   window.location=path;
}

function openWinFundInfo(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$.ajax({
		type:"POST",
		url:path+"/services/computer/findFundInfo",
		data:{},
		success:function(data){
			$('#formfundInfo').form('reset');
			if(data){
				var fundInfo=eval("("+data+")");
				fillFundInfo(fundInfo);
				$('#operation').val("update");
			}else{
				$('#operation').val("create");
			}
		},
		error:function(){
			$.messager.alert('错误','后台服务出错，请稍后再试！','error');
		}
	});
	$('#winEditFundInfo').window('open');
}

function fillFundInfo(fundInfo){
	$('#fundId').val(fundInfo.fundId);
	$('#fundName').textbox('setValue',fundInfo.fundName);
	$('#abbrName').textbox('setValue',fundInfo.abbrName);
	$('#manager').textbox('setValue',fundInfo.manager);
	$('#recommend').textbox('setValue',fundInfo.recommend);
}

function saveFundInfo(){
	var res=$("#formfundInfo").form('enableValidation').form('validate');
	var formData=$("#formfundInfo").serialize();
	if(res){
	   //校验通过
		$.ajax({
			type:"POST",
			url:path+"/services/computer/saveFundInfo",
			data:$("#formfundInfo").serialize(),
			success:function(msg){
				$('#winEditFundInfo').window('close');
				$.messager.show({
					title:'提示',
					msg:'用户数据保存成功！',
					timeout:1500,
					showType:'slide'
				});
			},
			error:function(){
				//$.messager.alert('Error','后台服务出错，请稍后再试！','error');
				window.location=path;
			}
		});

	}else{
		return ;
	}
}

//==========================================================================================================================================
function openWinYieldRate(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	findIncomeInfoByAccountDay();
	
	$.ajax({
		type:"POST",
		url:path+"/services/computer/findMaxDate",
		success:function(data){
			var result=eval("("+data+")");
			var maxDate=result.maxDate;
			$('#accountDay').datebox().datebox('calendar').calendar({
				validator: function(date){
					var now = new Date();
					var d1=toDate(maxDate);
					var d2 = now;
					return d1<=date && date<=d2;
				}
			});
		},
		error:function(){
			$.messager.alert('错误','后台服务出错，请稍后再试！','error');
		}
	});
	
	$('#winYieldRate').window('open');
}


function onSelectAccountDay(){
	findIncomeInfoByAccountDay();	
}

function findIncomeInfoByAccountDay(){
	var param={};
	param.accountDay=$("#accountDay").datebox('getValue');
	param.fundId=$('#_fundId').val();
	$.ajax({
		type:"POST",
		url:path+"/services/computer/findIncomeInfoByAccountDay",
		data:param,
		success:function(data){
			var fundIncome=eval("("+data+")");
			if(fundIncome.accountDay=="" || fundIncome.accountDay==null ){
				fundIncome.accountDay=param.accountDay;
			}
			fillYieldRateForm(fundIncome);
			
		},
		error:function(){
			$.messager.alert('错误','后台服务出错，请稍后再试！','error');
		}
	});
}

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}

function fillYieldRateForm(fundIncome){
	$('#_fundId').val(fundIncome.fundId);
	$('#yieldRate').numberbox('setValue',fundIncome.yieldRate);
	$('#accountDay').datebox('setValue',fundIncome.accountDay);
	$('#buyTotalValue').numberbox('setValue',fundIncome.buyTotalValue);
	$('#saleTotalValue').numberbox('setValue',fundIncome.saleTotalValue);	
	if(fundIncome.preBookValue>0){
		$('#preBookValue').numberbox('setValue',fundIncome.preBookValue);
		$('#preBookValue').numberbox('disable');
	}else{
		$('#preBookValue').numberbox('setValue',fundIncome.preBookValue);
		$('#preBookValue').numberbox('enable');
	}
	$('#todayBookValue').numberbox('setValue',fundIncome.todayBookValue);
	$('#yieldRate').numberbox('setValue',fundIncome.yieldRate);
	$('#yieldRate').numberbox('disable');
	
	
	if(fundIncome.yieldRate){
		$('#_operation').val("update");
	}else{
		$('#_operation').val("create");
	}
	
}

function toDate(str){
	str = str.replace(/-/g,"/");
	var date = new Date(str );
	return date;
}

function saveYieldRate(){
	var accountDay=$("#accountDay").datebox('getValue');
	var today=new Date();
	var sToday = today.format("yyyy-MM-dd");
	if(sToday==accountDay){//当日结算
       accountTime=Date.parse(sToday+" 15:00:00");
       if(today.getTime()<accountTime){
    	   $.messager.alert('提醒','请在收盘后录入当日收益率！','warn');
    	   return;
       }
	}
	
	var res=$("#formYieldRate").form('enableValidation').form('validate');
	if(res){
		 //校验通过
		var param=$("#formYieldRate").serialize()+"&yieldRate="+$("#yieldRate").numberbox("getText");
		$.ajax({
			type:"POST",
			url:path+"/services/computer/saveYieldRate",
			data:param,
			success:function(msg){
				$('#winYieldRate').window('close');
				$.messager.show({
					title:'提示',
					msg:'用户数据保存成功！',
					timeout:1500,
					showType:'slide'
				});
			},
			error:function(){
				//$.messager.alert('Error','后台服务出错，请尝试重新登录后再试！','error');
				window.location=path;
			}
		});
	}
}

function onLoadSuccess(data) {  
   drawGraph(data.rows);
}

function btnSearchOnClick(){
	 var beginDate=$('#beginDate').textbox('getText');
	 var endDate=$('#endDate').textbox('getText');
	 $('#yieldrate-grid').datagrid('load',{
		 beginDate: beginDate,
		 endDate: endDate
	 });
}

function btnWeekOnClick(){
	var beginDate=new Date();
	var endDate=new Date();
	beginDate.setDate(beginDate.getDate() -7)
	$("#beginDate").datebox('setValue',myformatter(beginDate));
	$("#endDate").datebox('setValue',myformatter(endDate));
	btnSearchOnClick();
}

function btnMonthOnClick(){
	var beginDate=new Date();
	var endDate=new Date();
	beginDate.setMonth(beginDate.getMonth() -1)
	$("#beginDate").datebox('setValue',myformatter(beginDate));
	$("#endDate").datebox('setValue',myformatter(endDate));
	btnSearchOnClick();
}

function btnThreeMonthOnClick(){
	var beginDate=new Date();
	var endDate=new Date();
	beginDate.setMonth(beginDate.getMonth()-3)
	$("#beginDate").datebox('setValue',myformatter(beginDate));
	$("#endDate").datebox('setValue',myformatter(endDate));
	btnSearchOnClick();
}

function btnSixMonthOnClick(){
	var beginDate=new Date();
	var endDate=new Date();
	beginDate.setMonth(beginDate.getMonth()-7)
	$("#beginDate").datebox('setValue',myformatter(beginDate));
	$("#endDate").datebox('setValue',myformatter(endDate));
	btnSearchOnClick();
}

function drawGraph(rowData){
	var data=[];
	var xticks=[];
	//解析数据
	for(var i=0;i<rowData.length;i++){
		data.push(rowData[i].accumulate);
		xticks.push(rowData[i].accountDay);
	}
	
	var tickInterval=1;
	if(xticks.length>15){
		tickInterval = Math.ceil(xticks.length/15);
	}
	
	var title={
		text:''	
	};
	var xAxis={
		tickInterval:tickInterval,	
		categories:xticks,	
		labels:{
			rotation:-15
		}
	};

    var yAxis={
    	title:{
    		text:''
    	},
    	plotLines:[{
    		value:0,
    		width:1,
    		color:'#808080'
    	}]
    };
    
    var tooltip={
    		formatter:function(){return  "日期：" +  this.x +" <br/>  累积收益率:"+this.y.toFixed(4);}
    };
    
    var legend={enabled :false};
    
    var series=[{
    	name:'',
    	data:data
    }];
	
    var json={};
    json.title=null;
    json.xAxis=xAxis;
    json.yAxis=yAxis;
    json.series=series;
    json.legend=legend;
    json.tooltip=tooltip;
   
    $('#line-graph').highcharts(json);
}



  