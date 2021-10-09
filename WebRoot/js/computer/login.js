$(function(){
   $("#userName").textbox('textbox').bind("keydown",
	function(e){
	   if(e.keyCode==13){
		   $('#password').passwordbox().next('span').find('input').focus();
	   }
	 });
   
   $("#password").passwordbox('passwordbox').bind("keydown",
	function(e){
	   if(e.keyCode==13){
		   $("#btnOK").focus();
	   }
	 }); 
});


function login(){
	var res=$("#loginForm").form('enableValidation').form('validate');
	if(res==false) return;
	var param={};
	param.userName=$("#userName").val();
	param.password=$("#password").val();
	var url="../services/computer/login";
	$.post(url,param,function(data,status){
		var result=eval("("+data+")");
		if(result.login=="success"){
			window.location.href = "../computer/usermgnt.jsp";
		}else{
			$("#errorTip").text("用户名或密码错误，请重新输入！");
		}	
	});
}

function cancel(){
	$("#userName").val("");
	$("#password").val("");
	$("#errorTip").text("");
}
