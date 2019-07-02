function changeToActive(id,category,pageNum) {
    var ulist_id = "";
    if (typeof (id)=="object") {
        ulist_id = id.id;
    }else{
        ulist_id = id;
    }
    $("ul").remove("#release-dreamland-ul");
    $("ul").remove("#release-dreamland-fy");
    $(".list-group-item").attr("class", "list-group-item");
    $("#" + ulist_id).attr("class", "list-group-item active");
    $.ajax({
        type: 'post',
        url: '/findByCategory',
        data: {"category": category, "pageNum": pageNum},
        dataType:'json',
        success:function (data) {
            var pageCate = data['pageCate'];
            if (pageCate == "fail") {
                window.location.href = '/login.jsp';
            }else {
                var uclist =pageCate.result;
                var startHtml = "<ul id='release-dreamland-ul' style='font-size: 12px'>";
                var endHtml = "</ul>";
                if (uclist != null) {
                    $(uclist).each(function () {
                        var contHtml = "<li class='dreamland-fix'><a>"+this.title+"</a><span class='bar-read'>评论("+this.commentNum+")</span>" +
                            "<span class='bar-commend'>"+this.upvote+"人阅度</span><hr/></li>";
                        startHtml = startHtml+contHtml;
                    });
                    var okHtml = startHtml + endHtml;
                    //分页
                    var stPageHtml = "<ul id='release-dreamland-fy' class='pager pager-loose'>";
                    if (pageCate.pageNum<=1) {
                        stPageHtml = startHtml+"<li class='pervious'><a href='javasrcipt:void(0);'>« 上一页</a></li> ";
                    }else if (pageCate.pageNum>1) {
                        var num = parseInt(pageCate.pageNum)-1;
                        stPageHtml = stPageHtml +"<li class='pervious'><a onclick='changeToActive("+ulist_id+",\""+category+"\","+num+")'>« 上一页</a>"
                    }
                    var foHtml = "";
                    for(var i =1;i<pageCate.pages;i++) {
                        if (pageCate.pageNum==i) {
                            foHtml = foHtml + "<li class='active'><a href='javascript:void(0);'>" + i + "</a>";
                        }else {
                            foHtml = foHtml+ "<li ><a onclick='changeToActive("+ulist_id+",\""+category+"\","+i+")'>"+i+"</a></li>";
                        }
                    }
                    var teHtml = "";
                    if (pageCate.pageNum>=pageCate.pages) {
                        teHtml = "<li><a href='javascript:void(0)'>下一页 »</a></li>";
                    }else if (pageCate.pageNum<pageCate.pages) {
                        var num = parseInt(pageCate.pageNum)+1;
                        teHtml = "<li><a onclick='changeToActive("+ulist_id+",\""+category+"\","+num+")'>下一页 »</a>"
                    }
                    var endPageHtml = "</ul>";
                    var pageOkHtml = stPageHtml+foHtml+teHtml+endPageHtml;
                }
                $("#release-dreamland").append(okHtml);
                $("#release-dreamland-div").append(pageOkHtml);
            }
        }
    });
    
}


function turnPage(pageNum) {
    $("ul").remove("#update-dreamland-ul");
    $("ul").remove("#update-dreamland-fy");
    $.ajax({
        type:'post',
        url:'/findByCategory',
        data:{"pageNum":pageNum},
        dataType:'json',
        success:function (data) {
            var pageCate = data["pageCate"];
            if (pageCate == "fail") {
                window.location.href = "/login.jsp";
            }else {
                var uclist = pageCate.result;
                var startHtml = "<ul id='update-dreamland-ul' style='font-size: 12px'>";
                var endHtml = "</ul>";
                if (uclist!=null) {
                    $(uclist).each(function () {
                        var contHmtl = "<li class='dreamland-fix'><a>"+this.title+"</a><span class='bar-delete'>删除</span>" +
                            "<span class='bar-update'>修改</span><hr/></li>";
                        startHtml = startHtml +contHmtl;
                    });
                    var okHmtl = startHtml + endHtml;

                    //分页
                    var stPageHtml = "<ul class='pager pager-loose' id='update-dreamland-fy'>";
                    if (pageCate.pageNum<=1) {
                        stPageHtml = stPageHtml + "<li class='pervious'><a href='javascript:void(0)'>« 上一页</a></li>";
                    }else if (pageCate.pageNum >1) {
                        var num = parseInt(pageCate.pageNum) - 1;
                        stPageHtml = stPageHtml +"<li class='pervious'><a onclick='turnPage("+num+")'></a>« 上一页</li>";
                    }

                    var foHtml = "";
                    for (i=1;i<pageCate.pageNum;i++) {
                        if (pageCate.pageNum==i) {
                            foHtml = foHtml + "<li class='active'><a href='javascript:void(0)'>" + i + "</a></li>";
                        }else {
                            foHtml = foHtml + "<li><a onclick='turnPage(" + i + ")'>" + i + "</a></li>";
                        }
                    }
                    var teHtml = "";
                    if (pageCate.pageNum>=pageCate.pages) {
                        teHtml = "<li><a href='javascript:void(0)'>下一页 »</a></li>";
                    }else if (pageCate.pageNum<pageCate.pages) {
                        var num = parseInt(pageCate.pageNum) + 1;
                        teHtml = "<li><a onclick='turnPage("+num+")'>下一页 »</a></li>";
                    }
                    var endPageHtml = "</ul>";
                    var pageOkHtml = stPageHtml + foHtml + teHtml + endPageHtml;
                }
                $("#update-dreamland").append(okHmtl);
                $("#update-dreamland-fy").append(pageOkHtml);
            }
        }

    });
    
}
//管理梦点击事件
function manage_dreamland() {
    document.getElementById("fa-dreamland").style.backgroundColor = "#F0F0F0";
    document.getElementById("fa-span").style.color = "black";

    document.getElementById("personal-div").style.backgroundColor = "#F0F0F0";
    document.getElementById("personal-span").style.color = "black";

    document.getElementById("manage-dreamland").style.backgroundColor = "#B22222";
    document.getElementById("manage-span").style.color = "white";

    document.getElementById("release-dreamland").style.display = "none";
    document.getElementById("personal-dreamland").style.display = "none";
    document.getElementById("update-dreamland").style.display = "";
}
//发布梦点击事件
function release_dreamland() {
    document.getElementById("fa-dreamland").style.backgroundColor = "#B22222";
    document.getElementById("fa-span").style.color = "white";

    document.getElementById("manage-dreamland").style.backgroundColor = "#F0F0F0";
    document.getElementById("manage-span").style.color = "black";

    document.getElementById("personal-div").style.backgroundColor = "#F0F0F0";
    document.getElementById("personal-span").style.color = "black";

    document.getElementById("release-dreamland").style.display = "";
    document.getElementById("personal-dreamland").style.display = "none";
    document.getElementById("update-dreamland").style.display = "none";

}
//私密梦点击事件
function personal_dreamland() {
    document.getElementById("fa-dreamland").style.backgroundColor = "#F0F0F0";
    document.getElementById("fa-span").style.color = "black";

    document.getElementById("personal-div").style.backgroundColor = "#B22222";
    document.getElementById("personal-span").style.color = "white";

    document.getElementById("manage-dreamland").style.backgroundColor = "#F0F0F0";
    document.getElementById("manage-span").style.color = "black";

    document.getElementById("release-dreamland").style.display = "none";
    document.getElementById("personal-dreamland").style.display = "";
    document.getElementById("update-dreamland").style.display = "none";


}

//私密博客分页点击事件
function personTurnPage(pageNum) {
    $("ul").remove("#personal-dreamland-ul");
    $("ul").remove("#personal-dreamland-fy");
    $.ajax({
        type: 'post',
        url: '/findPersonal',
        data: {"pageNum": pageNum},
        dataType: 'json',
        success: function (data) {
            var pageCate = data["page2"];
            if(pageCate=="fail"){
                window.location.href = "/login.jsp";
            }else{
                var ucList = pageCate.result;
                var startHtml = "<ul style='font-size: 12px' id='personal-dreamland-ul'>";
                var endHtml = "</ul>";
                if(ucList!=null) {
                    $(ucList).each(function () {
                        var contHtml = "<li class='dreamland-fix'> <a>"+this.title+"</a> <span class='bar-delete'>删除</span>"
                            +"<span class='bar-update'>修改</span><hr/></li>";
                        startHtml = startHtml + contHtml;
                    });
                    var okHtml = startHtml + endHtml;

                    //分页
                    var stPageHtml = "<ul class='pager pager-loose' id='personal-dreamland-fy'>";
                    if(pageCate.pageNum<=1){
                        stPageHtml = stPageHtml + " <li class='previous'><a href='javascript:void(0);'>« 上一页</a></li>";
                    }else if(pageCate.pageNum>1){
                        var num = parseInt(pageCate.pageNum) -1;
                        stPageHtml = stPageHtml + "<li class='previous'><a onclick='personTurnPage("+num+")'>« 上一页</a></li>";
                    }

                    var foHtml = "";
                    for(var i = 1 ;i<= pageCate.pages;i++){
                        if(pageCate.pageNum==i){
                            foHtml = foHtml+ " <li class='active'><a href='javascript:void(0);'>"+i+"</a></li>";
                        }else{
                            foHtml = foHtml+ "<li ><a onclick='personTurnPage("+i+")'>"+i+"</a></li>";
                        }
                    }

                    var teHtml = "";
                    if(pageCate.pageNum>=pageCate.pages){
                        teHtml = " <li><a href='javascript:void(0);'>下一页 »</a></li>";
                    }else if(pageCate.pageNum<pageCate.pages){
                        var num = parseInt(pageCate.pageNum) + 1;
                        teHtml = "<li><a onclick='personTurnPage("+num+")'>下一页 »</a></li>";
                    }
                    var endPageHtml = "</ul>";

                    var pageOkHtml = stPageHtml + foHtml + teHtml +endPageHtml;
                }

                $("#personal-dreamland").append(okHtml);
                $("#personal-dreamland-div").append(pageOkHtml);
            }
        }
    });
}

//页面加载完成函数
$(function () {
    var tomVal = document.getElementById("dreamland-see").style.marginTop;
    var hgt = parseInt(num) * 40 + parseInt(tomVal.split("px")[0]);
    document.getElementById("dreamland-see").style.marginTop = hgt + "px";


    if (val =="manage") {
        manage_dreamland();
    }
});