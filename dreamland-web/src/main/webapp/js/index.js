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
    return date.getFullYear()+'-'+data.getMonth()+'-'+date.getDate()+" "+h+':'+m+':'+s;

    
}