$(function(){
	debugger;
	$.ajax({
		type:"POST",
		url:path+"/services/computer/findFundInfo",
		success:function(data){
			var fundInfo=eval("("+data+")");
			$("#fundAbbrName").text(fundInfo.abbrName+"-");
		},
		error:function(){
			$("#fundAbbrName").text("基金");
		}
	});
});


function login(){
	var res=$("#formLogin").form('enableValidation').form('validate');
	if(res==false) return;
	var param={};
	param.userName=$("#userName").val();
	param.password=$("#password").val();
	var url="../services/phone/login";
	$.post(url,param,function(data,status){
		var result=eval("("+data+")");
		if(result.login=="success"){
			window.location = "../phone/main.jsp";
		}else{
			$("#errorTip").text("姓名或密码错误，请重新输入！");
		}	
	});
}


