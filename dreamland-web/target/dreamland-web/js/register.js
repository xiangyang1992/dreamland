var v = 0;
var flag2 = false;

//自动更换验证码
function changeCaptcha() {
    $("#captchaImg").attr('src','/captchaServlet?t='+(new Date().getTime()));
}

//校验手机号
function checkPhone() {
    var phone = $("#phone").val();
    phone = phone.replace(/^\s+|\s+$/g,"");
    if (phone === "") {
        $("#phone_span").text("请输入手机号码！");
        $("#phone_ok").text("");
        var hgt = $("#regist-left").height();
        if (v == 0) {
            $("#regist-left").height(hgt + 30);
            $("#regist-right").height(hgt + 30);
            v++;
        }
        flag2 = false;
    }
    if (!(/^1[3|4|5|8|7][0-9]\d{8}$/.test(phone))) {
        $("#phone_span").text("手机号码非法，请重新输入!");
        $("#phone_ok").text("");
        var hgt =$("#regist-left").height();
        if (v==0) {
            $("#regist-left").height(hgt+30);
            $("#regist-right").height(hgt+30);
            v++;
        }
        flag2 = false;
    }else {
        $.ajax({
            type:'post',
            url:'/checkPhone',
            data:{"phone":phone},
            dataType:'json',
            success:function (data) {
                var val = data['message'];
                if (val=="success") {
                    $("#phone_span").text("");
                    $("#reg_span").text("");
                    $("#phone_ok").text("√").css('color','green');

                    var content = $("#phone_ok").text();
                    if (content == "√") {
                        var hgt = $("#regist-right").height();
                        if (v == 1) {
                            $("#regist-right").height(hgt-30);
                            $("#regist-left").height(hgt-30);
                        }
                        v = 0;
                    }
                    flag2 = true;
                }else {
                    $("#phone_span").text("该手机号已经注册!");
                    $("#phone_ok").text("");
                    var hgt = $("#regist-right").height();
                    if (v == 0 ) {
                        $("#regist-left").height(hgt+30);
                        $("#regist-right").height(hgt+30);
                        v++;
                    }
                    flag2 = false;
                }
            }
        });
    }
    return flag2;
}

//验证邮箱
function checkEmail() {
    var email = $("#email").val();
    email = email.replace(/^\s+|\s+$/g,"");//去掉左右空白
    if (email == null || email =="") {
        if (v==0) {
            increaseHeight();
            v++;
        }
        $("#email_span").text("输入邮箱账号!");
        $("#email_ok").text("");
        flag2 = false;
    }
    if (!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/.test(email))) {
        $("#email_span").text("邮箱账号不合法，请重新输入!");
        $("#email_ok").text("");
        if (v == 0) {
            increaseHeight();
            v++;
        }
        flag2 = false;
    }else {
        //验证邮箱是否已经注册
        $.ajax({
            type:'post',
            url:'/checkEmail',
            data:{'email':email},
            dataType:'json',
            success:function (data) {
                var val = data['message'];
                if (val=="success") {
                    $("#email_span").text("");
                    $("#reg_span").text("");
                    $("#email_ok").text("√").css("color","green");

                    var content = $("#email_ok").text();
                    if (content == "√") {
                        var hgt = $("#regist-left").height();
                        if (v ==1) {
                            $("#regist-left").height(hgt+30);
                            $("#regist-right").height(hgt+30);
                        }
                        v = 0;
                    }
                    flag2 = true;
                }else {
                    $("#email_span").text("该邮箱账号已注册!");
                    $("#email_ok").text("");
                    var hgt = $("#regist-left").height();
                    if(v==0){
                        $("#regist-left").height(hgt+30);
                        $("#regist-right").height(hgt+30);
                        v++;
                    }
                    flag2 =  false;
                }
            }
        });
    }
    return flag2;
}
//校验密码强弱
/**
 * @return {boolean}
 */
function CheckIntensity(password) {
    var val = $('#password_span').text();
    if (v=0 && (val ==null || val =="")) {
        increaseHeight();
        v++;
    }
    if (password !=null) {
        password = password.replace(/^\s+|\s+$/g, "");
    }
    var len = 0;
    if (password != null && "" != password) {
        len = password.length;
    }
    if (len<6) {
        $('#password_span').text("密码长度少于6位，请重新输入!").css("color","red");
    }else {
        if (/^[0-9]*$/.test(password) || /^[A-Za-z]+$/.test(password)) {
            $('#password_span').text("密码强度弱!").css("color","red");
        }else if (/^[A-Za-z0-9]+$/.test(password) || password.length<=10) {
            $('#password_span').text("密码强度中!").css("color","#FF6100");
        }else {
            $('#password_span').text("密码强度强!").css("color","green");
        }
        flag2 = true;
    }
    return flag2;
}

//密码框离焦时间
function checkPassword() {
    var pwd = $('#password').val();
    pwd = pwd.replace(/^\s+|\s+$/g,"");
    if (pwd == "") {
        $('#password_span').text("请输入密码").css("color","red");
        if (v == 0) {
            increaseHeight();
            v++;
        }
        return false;
    }
    if (pwd.length < 6) {
        $('#password_span').text("密码长度少于6位，请重新输入!").css("color","red");
        if (v == 0) {
            v++;
            increaseHeight();
        }
        return false;
    }
    if (flag2) {
        $('#password_span').text("");
        $('#reg_span').text("");
        if (v == 1) {
            reduceHeight();
            v = 0;
        }
        return true;
    }

}

//校验昵称
function checkNickName() {
    var nickName = $('#nickName').val();
    nickName = nickName.replace(/^\s+|\s+$/g,"");
    if (nickName == null) {
        $('#nickName_span').text("请输入昵称!");
        return false;
    }else {
        $('#nickName_span').text("");
        $('#reg_span').text("");
        return true;
    }
    
}

//校验验证码
function checkCode() {
    var code = $('#code').val();
    code = code.replace(/^\s+|\s+$/g,"");
    if (code == null || code == "") {
        $('#code_span').text("请输入验证码!").css("color","red");
        flag2 = false;

    }else {
        $.ajax({
            type:'post',
            url:'/checkCode',
            data:{'code':code},
            dataType:'json',
            success:function (data) {
                var val = data['message'];
                if (val == 'success') {
                    $('#code_span').text('√').css("color","green");
                    $('#reg_span').text("");
                    flag2 = true;
                }else {
                    $('#code_span').text("验证码错误!").css("color","red");
                    flag2 = false;
                }
                
            }
        });
        return flag2;
    }

}

//勾选协议
function checkProtocol() {
    if ($('#protocol').prop('checked')) {
        $('#reg_span').text("");
        return true;
    }else {
        return false;
    }
}
//提交注册信息
$('#to_register').click(function () {
    if (!checkProtocol()) {
        $('#protocol_span').text("请勾选用户协议!").css("color","red");
    }else {
        $('#protocol_span').text("")
    }
    if (checkCode() && checkEmail() && checkNickName() && checkPassword() && checkPhone() && checkProtocol()) {
        $('#registerForm').submit();
    }else {
        $('#reg_span').text("请填写完整信息!").css("color","red");
    }
});


//根据内容增加div高度
function increaseHeight() {
    var hgt = $('#regist-left').height();
    $('#regist-left').height(hgt+30);
    $("#regist-right").height(hgt+30);
}
//根据内容减少div高度
function reduceHeight() {
    var hgt = $("#regist-left").height();
    $("#regist-left").height(hgt-30);
    $("#regist-right").height(hgt-30);
}