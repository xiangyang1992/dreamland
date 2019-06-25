function upvote_click(id,cont) {
    $.ajax({
        type:'post',
        url:'/upvote',
        dataType:'json',
        data:{"id":id,"uid":userId,"upvote":cont},
        success:function (data) {
            var up = data["data"];
            if (up == "success") {
                if (cont == -1) {
                    var down = document.getElementById("down_"+id);
                    var num = down.innerText;
                    var value = parseInt(num)+cont;
                    down.innerHTML = value;
                }else {
                    var up = document.getElementById("up_"+id);
                    var num = up.innerText;
                    var value = parseInt(num)+cont;
                    up.innerHTML = value;
                    document.getElementById(id).innerText = value;
                }

            }else if (up == "upvote") {
                alert("已点赞！")
            }else if (up == "downvote") {
                alert("已踩！")
            }else {
                window.open("/login.jsp");
            }
        }
    });
}
function showImg() {
    document.getElementById("wxImg").style.display = 'block';
}

function hideImg() {
    document.getElementById("wxImg").style.display = 'none';
}

function personal(uId) {
    this.location = "${ctx}/list?id=" + uId;
}

/**
 * @return {string}
 */
function FormatDate(strTime) {
    var date = new Date(strTime);
    var h = date.getHours();
    var m = date.getMinutes();
    if (m<10) m = '0'+m;
    var s = date.getSeconds();
    if (s<10) s = '0'+s;
    return date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+" "+h+':'+m+':'+s;
}


function reply(id,uid) {
    $("div").remove("#comment_reply_"+id+" .comment-show");
    $("div").remove("#comment_reply_"+id+" .comment-show-con");

    // $("div").remove("#comment_reply_"+id+" .comment-show");
    // $("div").remove("#comment_reply_"+id+" .comment-show-con");

    if (showdiv_display = document.getElementById('comment_reply_' + id).style.display == 'none') {//如果show是隐藏的

        document.getElementById('comment_reply_' + id).style.display = 'block';//show的display属性设置为block（显示）
    // var showdiv_display = document.getElementById("comment_reply_"+id).style.display;
    // if (showdiv_display =='none') {
    //     showdiv_display.style.display = 'block';
    // }
        $.ajax({
            type: 'post',
            url: '/reply',
            data: {"content_id": id},
            dataType: 'json',
            success: function (data) {
                var list = data["list"];
                var okHtml;
                if (list != null && list != "") {
                    $(list).each(function () {
                        var chtml = "<div class='comment-show'>" +
                            "<div class='comment-show-con clearfix'>" +
                            "<div class='comment-show-con-img pull-left'><img src='" + this.user.imgUrl + "' alt=''></div> " +
                            "<div class='comment-show-con-list pull-left clearfix'>" +
                            "<div class='pl-text clearfix'>" +
                            "<a class='comment-size-name'>" + this.user.nickName + ":</a>" +
                            "<span class='my-pl-con'> "+this.comContent+"</span>" +
                            "</div><div class='date-dz-right'<span class='date-dz-left pull-left comment-time'> " + FormatDate(this.commTime) + "</span>" +
                            "<div class='date-dz-right pull-right comment-pl-block'> " +
                            "<a onclick='deleteComment("+id+","+uid+","+this.id+",null)' id='comment_dl_"+this.id+"' style='cursor:pointer' class='removeBlock'>删除</a>" +
                            "<a style='cursor:pointer' onclick='comment_hf("+id+","+this.id+","+null+","+this.user.id+","+uid+")' id='comment_hf_"+this.id+"' class='date-dz-pl pl-hf hf-con-block pull-left'>回复</a>" +
                            "<span class='pull-left date-dz-line'>|</span>" +
                            "<a onclick='reply_up("+this.id+")' style='cursor:pointer' class='date-dz-z pull-left' id='change_color_"+this.id+"'><i class='date-dz-z-click-red'></i>赞 (<i class='z-num' id='comment_upvote_"+this.id+"'>"+this.upvote+"</i>)</a>" +
                            "</div> </div> <div class='hf-list-con' id='hf-list-con-"+this.id+"'>";
                        var ehtml = "</div></div></div></div>";
                        var parentComm_id = this.id;

                        okHtml = chtml;
                        if (this.children != null && this.children != "") {
                            var commentList = this.comList;
                            $(commentList).each(function () {
                                var oHtml = "<div class='all-pl-con'><div class='pl-text hfpl-text clearfix'>" +
                                    "<a class='comment-size-name'>" + this.user.nickName + "<a class='atName'>@" + this.byUser.nickName + " :</a> </a>" +
                                    "<span class='my-pl-con'>" + this.comContent + "</span>" +
                                    "</div><div class='date-dz'> <span class='date-dz-left pull-left comment-time'>" + FormatDate(this.commTime) + "</span>" +
                                    "<div class='date-dz-right pull-right comment-pl-block'>" +
                                    "<a style='cursor:pointer' onclick='deleteComment(" + id + "," + uid + "," + this.id + "," + parentComm_id + ")' id='comment_dl_" + this.id + "' class='removeBlock'>删除</a>" +
                                    "<a onclick='comment_hf(" + id + "," + this.id + "," + parentComm_id + "," + this.user.id + "," + uid + ")' id='comment_hf_" + this.id + "' style='cursor:pointer' class='date-dz-pl pl-hf hf-con-block pull-left'>回复</a> <span class='pull-left date-dz-line'>|</span>" +
                                    "<a onclick='reply_up(" + this.id + ")' id='change_color_" + this.id + "' style='cursor:pointer' class='date-dz-z pull-left'><i class='date-dz-z-click-red'></i>赞 (<i class='z-num' id='comment_upvote_" + this.id + "'>" + this.upvote + "</i>)</a>" +
                                    "</div></div> </div>";
                                okHtml = okHtml + oHtml;
                            });
                        }
                        okHtml = okHtml + ehtml;
                        $("#comment-show-" + id).append(okHtml);
                    })
                }
            }
        })
    }else {
        document.getElementById('comment_reply_'+id).style.display='none';//show的display属性设置为none（隐藏）
    }

}

//评论限制字数
function keyUP(t) {
    var len = $(t).val().length;
    if(len>140) {
        $(t).val($(t).val().substring(0,140));
    }

}

//点击评论事件
function _comment(cont_id,uid,cuid) {
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth();
    var date = myDate.getDate();
    var h = myDate.getHours();
    var m = myDate.getMinutes();
    if (m<10) m = '0'+m;
    var s = myDate.getSeconds();
    if (s<10) s = '0'+s;
    var now = year+'-'+month+'-'+date+" "+h+':'+m+':'+s;
    var oSize = $("#comment_input_" + cont_id).val();
    debugger;
    console.log(oSize);
    //动态创建评论模板
    if (oSize.replace(/(^\s*)|(\s*$)/g, "")!='') {
        $.ajax({
            type:'post',
            url:'/comment',
            data:{"cont_id":cont_id,"uid":uid,"oSize":oSize,"comment_time":now},
            dataType:'json',
            success:function (data) {
                var comm_data = data["data"];
                if(comm_data == "fail") {
                    window.location.href = "/login.jsp";
                }else {
                    var id = comm_data.id;
                    oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="${user.imgUrl}" alt="">' +
                        '</div> <div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a class="comment-size-name">${user.nickName} : </a> ' +
                        '<span class="my-pl-con"> '+ oSize +'</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'' +
                        '</span> <div class="date-dz-right pull-right comment-pl-block"><a style="cursor:pointer" onclick="deleteComment('+cont_id+','+cuid+','+id+','+null+')" ' +
                        'id="comment_dl_'+id+'" class="removeBlock">删除</a> <a style="cursor:pointer" onclick="comment_hf('+cont_id+','+id+','+null+','+comm_data.user.id+','+cuid+')" ' +
                        'id="comment_hf_'+id+'" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a onclick="reply_up('+id+')" ' +
                        'id="change_color_'+id+'" style="cursor:pointer" class="date-dz-z pull-left" ><i class="date-dz-z-click-red"></i>赞 (<i class="z-num" id="comment_upvote_'+id+'">0</i>)</a>' +
                        ' </div> </div><div class="hf-list-con"></div></div> </div>';
                    $("#comment_"+cont_id).parents('.reviewArea').siblings('.comment-show-first').prepend(oHtml);
                    $("#comment_"+cont_id).siblings('.flex-text-wrap').find('.comment-input').prop('value','').siblings('pre').find('span').text('');
                    $("#comment_input_" + cont_id).val('');
                    var num = document.getElementById("comment_num"+cont_id).innerHTML;
                    document.getElementById("comment_num_" + cont_id).innerHTML = (parseInt(num) + 1) + "";

                }
            }
        });
    }

}