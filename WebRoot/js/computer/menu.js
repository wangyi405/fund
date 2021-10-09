//初始化菜单事件
$(function(){	
	$("#btnUsermgnt").click(usermgnt);
	$("#btnFundmgnt").click(fundmgnt);
	$("#mmBackup").click(backup);
	$("#mmRestore").click(restore);
	$("#mmExit").click(exit);
	$('#filename').filebox({buttonText:"请选择文件",buttonAlign:'left'});
	$('#btnUpload').click(upload);
	$('#btnCloseRestore').click(closeRestore);
});

function isLogin(){
	var loginFlag=false;
	var url= path+"/services/computer/islogin"; 
	$.ajax({  
        url: path+"/services/computer/islogin",  
        type:"post",
        data:{},
        async: false,  
        success: function (data) {  
        	if(data=="true"){
        		loginFlag=true;
        	}else{
        		loginFlag=false;
        	}
        },  
        error: function (returndata) {  
        	loginFlag=false;
        }  
	});
	return loginFlag;
}

function upload(){
	 var res=$("#filename").filebox('getValue');
	 if(""==res){
		 $.messager.alert('消息','请选择一个备份文件！','warning'); 
		 return ;
	 }
	 var formData = new FormData($("#uploadForm" )[0]);  
     $.ajax({  
          url: path+"/servlet/upload" ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (returndata) {  
        	  $('#winRestore').window('close');
        	  $.messager.alert('成功','数据恢复成功！','success'); 
          },  
          error: function (returndata) {  
        	  $.messager.alert('错误','数据恢复失败！','error');
          }  
     });  
}

function closeRestore(){
	$('#winRestore').window('close');
}

//数据库备份
function backup(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	var url=path+"/services/computer/backup";
	 window.open(url,"_blank");
}

//数据库恢复
function restore(){
	if(!isLogin()){
		$.messager.alert('错误','请重新登录后，再试！','error');
		return;
	}
	$('#winRestore').window('open');
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