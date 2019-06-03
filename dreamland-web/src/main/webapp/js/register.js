//更换验证码
function changeCaptcha() {
    $("#captchaImg").attr('src', '${ctx}/captchaServlet?t=' + (new Date().getTime()));
}

//根据内容增加而增加高度
function increaseHeight() {

    var hgt = $("#regist-left").height();
    $("#regist-left").height(hgt+30);
    $("#regist-right").height(hgt+30);

}
//根据内容减少而减少高度
function reduceHeight() {
    var hgt = $("#regist-left").height();
    $("#regist-left").height(hgt-30);
    $("#regist-right").height(hgt-30);
}

//校验手机号
var v = 0;
var flag2 = false
function checkPhone(){
    var phone = $("#phone").val();
    phone = phone.replace(/^\s+|\s+$/g,"");
    if(phone == ""){
        $("#phone_span").text("请输入手机号码！");
        $("#phone_ok").text("");
        var hgt = $("#regist-left").height();

        if(v==0){
            $("#regist-left").height(hgt+30);
            $("#regist-right").height(hgt+30);
            v++;
        }

        flag2 =  false;
    }
    if(!(/^1[3|4|5|8|7][0-9]\d{8}$/.test(phone))){
        $("#phone_span").text("手机号码非法，请重新输入！");
        $("#phone_ok").text("");
        var hgt = $("#regist-left").height();
        if(v==0){
            $("#regist-left").height(hgt+30);
            $("#regist-right").height(hgt+30);
            v++;
        }
        flag2 = false;
    }else{
        $.ajax({
            type:'post',
            url:'/checkPhone',
            data: {"phone":phone},
            dataType:'json',
            success:function(data){
                var val = data['message'];
                if(val=="success"){
                    $("#phone_span").text("");
                    $("#reg_span").text("");
                    $("#phone_ok").text("√").css("color","green");

                    var content = $("#phone_ok").text();
                    if(content=="√" ){
                        var hgt = $("#regist-left").height();
                        if(v==1){
                            $("#regist-left").height(hgt-30);
                            $("#regist-right").height(hgt-30);
                        }
                        v=0;
                    }
                    flag2 =  true;

                }else{

                    $("#phone_span").text("该手机号已经注册！");
                    $("#phone_ok").text("");
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
var flag = false;
var i = 0;
var c = 0;
function CheckIntensity(pwd) {
    var val = $("#password_span").text();
    if(c==0 && (val==null || val =="")){
        increaseHeight();
        c++;
    }
    //去两边空格
    if(pwd!=null) {
        pwd = pwd.replace(/^\s+|\s+$/g, "");
    }
    var len = 0;
    if(pwd != "" && pwd!=null){
        len = pwd.length;
    }
    if( len < 6){
        $("#password_span").text("密码长度少于6位，请重新输入！").css("color","red");
    }else {
        if(/^[0-9]*$/.test(pwd) || /^[A-Za-z]+$/.test(pwd)){

            $("#password_span").text("密码强度弱！").css("color","red");
        }else if(/^[A-Za-z0-9]+$ /.test(pwd) || pwd.length <= 10){

            $("#password_span").text("密码强度中！").css("color","#FF6100");
        }else{

            $("#password_span").text("密码强度强！").css("color","green");

        }
        flag = true;


    }
    return flag;
}

//密码框离焦事件
var cp = 0;
function checkPassword() {
    var password = $("#password").val();
    password = password.replace(/^\s+|\s+$/g,"");
    if(password == ""){
        $("#password_span").text("请输入密码！").css("color","red");
        if(cp==0){
            increaseHeight();
            cp++;
        }
        return false;
    }
    if(password.length < 6){
        $("#password_span").text("密码长度少于6位，请重新输入！").css("color","red");
        if(cp==0){
            cp++;
            increaseHeight();
        }
        return false;
    }


    if(flag){
        $("#password_span").text("");
        $("#reg_span").text("");

        if(cp==1){
            reduceHeight();
            cp=0;
        }
        return true;
    }
}




//邮箱校验
var e = 0;
var flag_e =false;
function checkEmail() {
    var email = $("#email").val();
    email = email.replace(/^\s+|\s+$/g,"");
    if(email == "" || email==null){
        if(e==0){
            increaseHeight();
            e++;
        }
        $("#email_span").text("请输入邮箱账号！");
        $("#email_ok").text("");
        flag_e = false;
    }
    if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/.test(email))){
        $("#email_span").text("邮箱账号不存在，请重新输入！");
        $("#email_ok").text("");
        if(e==0){
            increaseHeight();
            e++;
        }
        flag_e = false;
    }else{
        //验证邮箱是否已经注册
        $.ajax({
            type:'post',
            url:'/checkEmail',
            data: {"email":email},
            dataType:'json',
            success:function(data) {
                var val = data['message'];
                if(val=="success"){
                    $("#email_span").text("");
                    $("#reg_span").text("");
                    $("#email_ok").text("√").css("color","green");

                    var content = $("#email_ok").text();
                    if(content=="√" ){
                        var hgt = $("#regist-left").height();
                        if(v==1){
                            $("#regist-left").height(hgt-30);
                            $("#regist-right").height(hgt-30);
                        }
                        v=0;
                    }
                    flag_e = true;

                }else{

                    $("#email_span").text("该Email已经注册！");
                    $("#email_ok").text("");
                    var hgt = $("#regist-left").height();
                    if(v==0){
                        $("#regist-left").height(hgt+30);
                        $("#regist-right").height(hgt+30);
                        v++;
                    }
                    flag_e =  false;
                }
            }
        });

    }
    return flag_e;
}

//昵称校验
function checkNickName() {
    var nickName = $("#nickName").val();
    nickName = nickName.replace(/^\s+|\s+$/g,"");
    if(nickName == ""){
        $("#nickName_span").text("请输入昵称！");
        return false;
    }else{
        $("#nickName_span").text("");
        $("#reg_span").text("");
        return true;
    }
};

//验证码校验
var flag_c = false;
function checkCode() {
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

//勾选用户协议校验
function checkProtocol() {
    if($('#protocol').prop("checked"))
    {
        $("#reg_span").text("");
        return true;
    }
    else{
        return false;
    }

};

//提交注册信息
$("#to_register").click (function(){
    if(!checkProtocol()){
        $("#protocol_span").text("请勾选\"阅读并接受梦境网用户协议\"！").css("color","red");
    }else{
        $("#protocol_span").text("");
    }

    if(checkPhone()  &&  checkPassword()&& checkEmail() && checkNickName()&& checkCode() && checkProtocol()){
        $("#registerForm").submit();
    }else {
        $("#reg_span").text("请将信息填写完整！").css("color","red");
    }

});