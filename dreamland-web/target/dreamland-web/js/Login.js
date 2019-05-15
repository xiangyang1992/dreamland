

//检查用户名
function checkUserName() {
    $('#back_data').text("");
    $('#back_active').text("");
    $('#back_unzhuce').text("");
    var userName = $('#username').val();
    userName = userName.replace(/^\s+|\s+$/g,"");
    if (userName == null || userName == "") {
        $('#normal_span').text("请输入用户名和密码!").css("color","red");
        return false;
    }else {
        $('#normal_span').text("").css("color","green");
        return true;
    }
    
}

//密码框回车事件
$('#code').bind('keypress',function (event) {
    if (event.keyCode == 13) {
        normal_login();
    }

});

//密码框回车事件
$('#password').bind('keypress',function (event) {
    if (event.keyCode == 13) {
        normal_login();
    }
});

function normal_login() {
    if (checkUserName() && checkCode() && checkPassword()) {
        $('#normal_form').submit();
    }
}