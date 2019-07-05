
//点击菜单切换页面事件
function account_set() {
    document.getElementById("base").style.backgroundColor = "#D1D1D1";
    document.getElementById("account").style.backgroundColor = "white";
    document.getElementById("binding").style.backgroundColor = "#D1D1D1";

    document.getElementById("set_title").innerHTML = "账号设置";

    document.getElementById("account_content").style.display = "";
    document.getElementById("base_content").style.display = "none";
    document.getElementById("binding_content").style.display = "none";

}
function base_set() {
    document.getElementById("base").style.backgroundColor = "white";
    document.getElementById("account").style.backgroundColor = "#D1D1D1";
    document.getElementById("binding").style.backgroundColor = "#D1D1D1";

    document.getElementById("set_title").innerHTML = "基本设置";

    document.getElementById("account_content").style.display = "none";
    document.getElementById("base_content").style.display = "";
    document.getElementById("binding_content").style.display = "none";

}

function binding_set() {
    document.getElementById("base").style.backgroundColor = "#D1D1D1";
    document.getElementById("account").style.backgroundColor = "#D1D1D1";
    document.getElementById("binding").style.backgroundColor = "white";

    document.getElementById("set_title").innerHTML = "绑定设置";

    document.getElementById("account_content").style.display = "none";
    document.getElementById("base_content").style.display = "none";
    document.getElementById("binding_content").style.display = "";

}

//点击图片事件
function selectImg() {
    $("#change-img").click();
}

function changeImg() {
    var formData = new FormData($("#upload-form")[0]);
    $.ajax({
        url:'/fileUpload',
        type:'post',
        data:formData,
        async:false,
        cache:false,
        contentType:false,
        processData:false,
        success:function (data) {
            var msg = data["error"];
            if (msg == 0) {
                var url = data["url"];
                $("#img-change").src = url;
                saveImg(url);
            }
        }
    });
}

//保存个人头像
function saveImg(url) {
    $.ajax({
        type:'post',
        url:'/saveImg',
        data:{"url":url},
        dataType:'json',
        success:function (data) {
            alert(data["msg"]);
        }
    });

}
//日期格式化
function FormatDate(strTime) {
    var date = new Date(strTime);
    var month = date.getMonth()+1;
    var h = date.getHours();
    var m = date.getMinutes();
    if (m<10) m = '0'+m;
    var s = date.getSeconds();
    if (s<10) s = '0'+s;
    return date.getFullYear()+'-'+month+'-'+date.getDate()+" "+h+':'+m+':'+s;
}

$(".form-date").datetimepicker(
    {
        language:  "zh-CN",
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: "yyyy-mm-dd"
    });

//保存修改个人信息
function saveUserInfo() {
    $("#userInfo_form").submit();
}

function changeColor() {
    // document.getElementById("password_span").style.color = "purple";
    $("#password_span").css("color", "purple");
}

function backColor() {
    $("#password_span").css("color", "grey");
    // document.getElementById("password_span").style.color = "grey";
}