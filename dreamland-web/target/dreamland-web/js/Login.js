//校验用户名
function checkUserName() {
    $("#back_data").text("");
    $("#back_active").text("");
    var username = $("#username").val();
    username = username.replace(/^\s+|\s+$/g,"");
    if(username == ""){
        $("#normal_span").text("请输入用户名或密码！").css("color","red");
        return false;
    }else{
        $("#normal_span").text("").css("color","green");
        return true;
    }
}
//校验密码
function checkPassword() {
    $("#back_data").text("");
    $("#back_active").text("");
    var password = $("#password").val();
    password = password.replace(/^\s+|\s+$/g, "");
    if (password == "") {
        $("#normal_span").text("请输入密码！").css("color", "red");
        return false;
    }
    if (password.length < 6) {
        $("#normal_span").text("密码长度少于6位，请重新输入！").css("color", "red");
        return false;
    }

    $("#normal_span").text("").css("color", "green");
    return true;
}
//更换验证码
function changeCaptcha() {
    $("#captchaImg").attr('src', '${ctx}/captchaServlet?t=' + (new Date().getTime()));
}
//验证码校验
var flag_c = false;
function checkCode() {
    $("#back_data").text("");
    $("#back_active").text("");
    var code = $("#code").val();
    code = code.replace(/^\s+|\s+$/g,"");
    if(code == ""){
        $("#code_span").text("请输入验证码！").css("color","red");
        flag_c = false;
    }else{
        $.ajax({
            type: 'post',
            url: '/checkCode',
            data: {"code": code},
            dataType: 'json',
            success: function (data) {
                var val = data['message'];
                if (val == "success") {
                    $("#code_span").text("√").css("color","green");
                    $("#reg_span").text("");
                    flag_c = true;
                }else {
                    $("#code_span").text("验证码错误！").css("color","red");
                    flag_c = false;
                }
            }
        });

    }
    return flag_c;
}
//密码框回车事件
$("#password").bind('keypress',function(event){


    if(event.keyCode == 13)

    {
        normal_login();
    }

});

//验证码框回车事件
$("#code").bind('keypress',function(event){


    if(event.keyCode == 13)

    {
        normal_login();
    }

});

function normal_login() {
    if(checkUserName() && checkPassword() && checkCode()) {
        $("#normal_form").submit();
    }
}
