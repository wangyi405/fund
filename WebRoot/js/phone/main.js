$(function(){
	queryUserInfo();
	//查询一周内曲线
	queryYieldRate("week");	
});

function queryInvestDetail(){
	$.ajax({
		type:"GET",
		url:path+"/services/phone/findUserSummary",
		success:function(data){
			var userSummary=eval("("+data+")");
			$("#_userName").text(userSummary.userName);
			$("#totalMoney").text(userSummary.totalMoney.toFixed(2));
			$("#totalAdd").text((userSummary.totalMoney - userSummary.totalInvest).toFixed(2));
			if(userSummary.totalInvest!=0){
				$("#totalYieldRate").text(((userSummary.totalMoney - userSummary.totalInvest)/userSummary.totalInvest*100).toFixed(2)+"%");
			}
			$("#userName").textbox("setValue",userSummary.userName);
			$("#idCardNo").textbox("setValue",userSummary.idCardNo);
			$("#phone").textbox("setValue",userSummary.phone);
			$("#email").textbox("setValue",userSummary.email);	
			$("#userId").val(userSummary.userId);	
		},
		error:function(){
			$.messager.alert('Error','后台服务出错，请稍后再试！','error');
		}
	});
}

function queryTotalMoneyByUser(){
	$.ajax({
		type:"GET",
		url:path+"/services/phone/findTotalMoneyByUser",
		success:function(data){
			var userSummary=eval("("+data+")");
			$("#_userName").text(userSummary.userName);
			$("#totalMoney").text(userSummary.totalMoney.toFixed(2));
			$("#totalAdd").text((userSummary.totalMoney - userSummary.totalInvest).toFixed(2));
			if(userSummary.totalInvest!=0){
				$("#totalYieldRate").text(((userSummary.totalMoney - userSummary.totalInvest)/userSummary.totalInvest*100).toFixed(2)+"%");
			}
			$("#userName").textbox("setValue",userSummary.userName);
			$("#idCardNo").textbox("setValue",userSummary.idCardNo);
			$("#phone").textbox("setValue",userSummary.phone);
			$("#email").textbox("setValue",userSummary.email);	
			$("#userId").val(userSummary.userId);	
		}
	});	
}

function formatBuyOrSale(val,row){
	if(val==1){
		return '<span style="color:red;">申购</span>';
	}else{
		return '<span>赎回</span>';
	}
}


function queryUserInfo(){
	$.ajax({
		type:"GET",
		url:path+"/services/phone/findUserSummary",
		success:function(data){
			var userSummary=eval("("+data+")");
			$("#_userName").text(userSummary.userName);
			$("#totalMoney").text(userSummary.totalMoney.toFixed(2));
			$("#totalAdd").text((userSummary.totalMoney - userSummary.totalInvest).toFixed(2));
			if(userSummary.totalInvest!=0){
				$("#totalYieldRate").text(((userSummary.totalMoney - userSummary.totalInvest)/userSummary.totalInvest*100).toFixed(2)+"%");
			}
			$("#userName").textbox("setValue",userSummary.userName);
			$("#idCardNo").textbox("setValue",userSummary.idCardNo);
			$("#phone").textbox("setValue",userSummary.phone);
			$("#email").textbox("setValue",userSummary.email);	
			$("#userId").val(userSummary.userId);	
		},
		error:function(){
			$.messager.alert('Error','后台服务出错，请稍后再试！','error');
		}
	});
	
}


function queryYieldRate(period){
	var beginDate=new Date();
	var endDate=new Date();
	if(!period){
		period="week";
	}
	
    if("week"=="period"){
    	beginDate.setDate(beginDate.getDate()-15);
    }else if("month"){
    	beginDate.setMonth(beginDate.getMonth()-1);
    }else if("threemonth"){
    	beginDate.setMonth(beginDate.getMonth()-3);
    }
	

	$.ajax({
		type:"GET",
		url:path+"/services/phone/findFundInfo",
		success:function(data){
			//初始化数据
			$.ajax({
				type:"GET",
				url:path+"/services/computer/findFundIncome",
				data:{ beginDate: formatDate(beginDate),
					   endDate: formatDate(endDate)
				     },
				success:function(data){
					var result=eval("("+data+")");
					rowData=result.rows;
					drawGraph(rowData);
				}
			});
		}
	});
}


function drawGraph(rowData){
	debugger;
	var data=[];
	var xticks=[];
	//解析数据
	for(var i=0;i<rowData.length;i++){
		data.push(rowData[i].accumulate);
		xticks.push(rowData[i].accountDay.substring(5));
	}
	
	var tickInterval=1;
	if(xticks.length>8){
		tickInterval = Math.ceil(xticks.length/8);
	}
	
	var title={
		text:''	
	};
	var xAxis={
		tickInterval:tickInterval,	
		categories:xticks,	
		labels:{
			rotation:-30
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

function formatDate(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}



function saveUser(){
	var res=$("#userForm").form('enableValidation').form('validate');
	if(res){
	   //校验通过
		$.ajax({
			type:"POST",
			url:path+"/services/phone/saveUser",
			data:$("#userForm").serialize(),
			success:function(msg){
				$('#editUser').window('close');
				$.messager.show({
					title:'提示',
					msg:'用户数据保存成功！',
					timeout:1500,
					showType:'slide'
				});
				$('#user-grid').datagrid('load',{});
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