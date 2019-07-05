var f1 = false;
var f2 = false;
var f3 = false;
var oldpsd = "";
var newPsd = "";
var rePsd = "";


//提交修改密码
function surePost() {
    if (f1 && f2 && f3) {
        $("#update_password").submit();
    }else {
        $("#old_span").text("请重新输入密码！").css("color","red");
    }
}

//旧密码框离焦事件
function oldPassword() {
    oldpsd = $("#old_password").val();
    if (oldpsd == null || oldpsd.trim()=="") {
        $("#old_span").html("请输入旧密码！");
        f1 = false;
    }else if (oldpsd.length<6) {
        $("#old_span").html("密码长度少于6位，请重新输入！")
        f1 = false;
    }else {
        $("#old_span").html("");
        f1 = true;
    }
}

//新密码框离焦事件
function newPassword() {
    newPsd = $("#password").val();
    if (newPsd == null || newPsd.trim() == "") {
        $("#old_span").html("请输入新密码!");
        f2 = false;
    }else if (newPsd.length<6) {
        $("#old_span").html("密码长度少于6位，请重新输入！");
        f2 = false;
    }else if (newPsd == oldpsd) {
        $("#old_span").html("新旧密码一致,请重新输入！");
        f2 = false;
    }else {
        $("#old_span").html("");
        f2 = true;
    }

}

//新密码确认离焦事件
function rePassword() {
    rePsd = $("#repassword").val();
    if (rePsd!=newPsd) {
        $("#old_span").html("两次密码输入不一致,请确认！");
        f3 = false;
    }else {
        $("#old_span").html("");
        f3 = true;
    }
}