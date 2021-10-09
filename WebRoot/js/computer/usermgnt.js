var allowBuyOrSale=true;

$(function(){
	$.ajax({
		type:"GET",
		url:path+"/services/computer/findAllowBuyOrSale",
		async: false,
		success:function(data){
			allowBuyOrSale=data;
		},
		error:function(){
			window.location=path;
		}
	});
});


function btnSearchOnClick(){
	 var userName=$('#c_userName').textbox('getText');
	 var idCardNo=$('#c_idCardNo').textbox('getText');
	 $('#user-grid').datagrid('load',{
		 userName: userName,
		 idCardNo: idCardNo
	 });
}

//格式化用户类型
function formatUserType(val,row){
	if (val == 0){
		return '<span style="color:red;">系统管理员</span>';
	} else {
		return '投资用户';
	}
}

function formatOperation(val,row){
	var userInfo=row;
	if(userInfo.userType==0){
	     return	"<span>"+
				     "<a href='javascript:deleteUser("+userInfo.userId+",\""+userInfo.userName+"\")'>销户</a> | " +
			         "<a href='javascript:changeUser("+userInfo.userId+")'>修改</a> " +
			     "</span>";
	}else{
		 if(allowBuyOrSale==="true"){
		     return	"<span>"+
			             "<a href='javascript:deleteUser("+userInfo.userId+",\""+userInfo.userName+"\")'>销户</a> | " +
				         "<a href='javascript:changeUser("+userInfo.userId+")'>修改</a> | " +
				         "<a href='javascript:buyOrSale("+userInfo.userId+",true)'>申购</a> | " +
				         "<a href='javascript:buyOrSale("+userInfo.userId+",false)'>赎回</a> | " +   
				         "<a href='javascript:showMoneyDetail("+userInfo.userId+",\""+userInfo.userName+"\")'>资金明细</a> " +
	         		"</span>";
		 }else{
		     return	"<span>"+
			             "<a href='javascript:deleteUser("+userInfo.userId+",\""+userInfo.userName+"\")'>销户</a> | " +
				         "<a href='javascript:changeUser("+userInfo.userId+")'>修改</a> | " +
				         "<a href='javascript:showMoneyDetail("+userInfo.userId+",\""+userInfo.userName+"\")'>资金明细</a> " +
			         "</span>";
		 }

	}
}

function formatBuyOrSale(val,row){
	if(val==1){
		return '<span style="color:red;">申购</span>';
	}else{
		return '<span>赎回</span>';
	}
}

function formatInvestDate(val,row){
	return "<span>" + dateFormat(val,"yyyy-MM-dd HH:mm:ss")+"</span>";
}

function createUser(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$('.buyOrSale-info').hide();
	$('tr.base-info input').removeAttr("disabled");	
	$('#userId').val(null);
	$('#editUser').window('open');
	$('#userForm').form('reset');
	$('#operation').val("create");
}

function changeUser(userId){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$.ajax({
		type:"POST",
		url:path+"/services/computer/searchUserById",
		data:{"userId":userId},
		success:function(data){
			$('#userForm').form('reset');
			var userInfo=eval("("+data+")");
			fillUserForm(userInfo);
			$('#operation').val("update");
		},
		error:function(){
			//$.messager.alert('错误','后台服务出错，请稍后再试！','error');
			window.location=path;
		}
	});
	$('#editUser').window('open');
	$('.buyOrSale-info').hide();
	$('tr.base-info input').removeAttr("disabled");	
}

function deleteUser(userId,userName){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$.messager.confirm('删除确认', '你确认删除【'+userName+'】用户吗?', function(r){
		if (r){
			var userIds = [];
			if(userId){
				userIds.push(userId);
			}else{
				var rows = $('#user-grid').datagrid('getSelections');
				for(var i=0; i<rows.length; i++){
					var row = rows[i];
					userIds.push(row.userId);
				}
				if(userIds.length<=0) return;
			}
			
			$.ajax({
				type:"POST",
				url:path+"/services/computer/deleteUser",
				data:{"userIds":userIds.join()},
				success:function(data){
					$('#editUser').window('close');
					$.messager.show({
						title:'Success',
						msg:'用户删除成功！',
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
		}
	});
}

function buyOrSale(userId,isBuy){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$.ajax({
		type:"POST",
		url:path+"/services/computer/searchUserById",
		data:{"userId":userId},
		success:function(data){
			$('#userForm').form('reset');
			var userInfo=eval("("+data+")");
			fillUserForm(userInfo);
			if(isBuy){
				$("input[name='buyOrSales']")[0].checked=true;
			}else{
				$("input[name='buyOrSales']")[1].checked=true
			}
			$('#operation').val("buyOrSale");
		},
		error:function(){
			$.messager.alert('错误','后台服务出错，请稍后再试！','error');
		}
	});
	$('#editUser').window('open');
	$('tr.base-info input').attr("disabled","disabled");	
	$('.buyOrSale-info').show();
}

function saveUser(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	var operation=$("#operation").val();
	if("buyOrSale"==operation){
		//申赎操作
		var params={};
		params.userId=$("#userId").val();
		params.investAmount=$("#investAmount").val();
		params.investDirection=$('input[name="buyOrSales"]:checked').val();
		$.ajax({
			type:"POST",
			url:path+"/services/computer/buyOrSales",
			data:params,
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
		var res=$("#userForm").form('enableValidation').form('validate');
		if(res){
		   //校验通过
			$.ajax({
				type:"POST",
				url:path+"/services/computer/saveUser",
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

}

function fillUserForm(userInfo){
	$('#userId').val(userInfo.userId);
	$('#userName').textbox('setValue',userInfo.userName);
	$('#password').passwordbox('setValue',userInfo.password);
	$('#idCardNo').textbox('setValue',userInfo.idCardNo);
	$('#userType').combobox('setValue',userInfo.userType);
	$('#phone').textbox('setValue',userInfo.phone);
	$('#email').textbox('setValue',userInfo.email);
	
}


function showMoneyDetail(userId,userName){
	 $('#invest-grid').datagrid('load',{
		 userId: userId
	 });
	$('#moneyDetail').window('open');
	$('#moneyDetail').window('setTitle',"资金明细【"+userName+"】");
}


