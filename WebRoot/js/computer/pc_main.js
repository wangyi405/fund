$(function(){
	//usermgnt();
});


//载入用户管理页面
function usermgnt(){
	$('#workspace').panel('refresh', 'usermgnt.jsp');
}

//载入基金管理页面
function fundmgnt(){
   $('#workspace').panel('refresh', 'fundmgnt.jsp');
}

function exit(){
   window.location=path;
}