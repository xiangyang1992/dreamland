<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人空间</title>
    <link href="${ctx}/css/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>

    <link href="${ctx}/css/zui/css/zui.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/zui/css/zui-theme.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/css/zui/js/zui.min.js"></script>
    <script src="${ctx}/css/zui/lib/kindeditor/kindeditor.min.js"></script>
    <link href="${ctx}/css/My_css/my.css?<%=UUID.randomUUID().toString()%>" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <!-- 导航头部 -->
        <div class="navbar-header">
            <!-- 移动设备上的导航切换按钮 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse-example">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- 品牌名称或logo -->
            <a class="navbar-brand" href="javascript:void(0);">个人空间</a>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li class="active"><a href="your/nice/url">我的博客</a></li>
                <li><a href="${ctx}/index_list">首页</a></li>

                <!-- 导航中的下拉菜单 -->
                <li class="dropdown">
                    <a href="your/nice/url" class="dropdown-toggle" data-toggle="dropdown">设置 <b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="your/nice/url">任务</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav">
                <li><a href="your/nice/url">消息</a></li>
            </ul>

            <ul class="nav navbar-nav">
                <li><a href="${ctx}/writedream?id=${user.id}">写博客</a></li>
            </ul>
            <ul class="nav navbar-nav" style="margin-left: 680px">
                <li><a href="${ctx}/list?id=${user.id}">${user.nickName}

                </a></li>
            </ul>
            <img src="images/q.png" width="30" style="margin-top: 4px"/>
        </div>
    </div>

</nav>
    <!-- 左侧菜单栏-->
<div class="author-card follow-box">
    <div class="designer-card card-media">

        <input type="hidden" name="creator" value="13149346">
        <div class="avatar-container-80 center">
            <a href="#" title="${user.nickName}" class="avatar" target="_blank">
                <img src="${user.imgUrl}" width="80" height="80" alt="">
            </a>
        </div>
        <br/>
        <div class="author-info">
            <p class="author-info-title">
                <a href="#" title="${user.nickName}"
                   class="title-content" target="_blank">${user.nickName}
                </a>
            </p>
            <div class="position-info">
                <span>上海&nbsp;|&nbsp;UI设计师</span>
            </div>
            <div class="btn-area">
                <div class="js-project-focus-btn" style="margin-left: 20px">

                    <input type="button" title="添加关注" class="btn-default-main btn-current attention notfollow" value="关注"
                           z-st="follow">

                </div>
                <div style="margin-left: 30px;float: left">
                <input type="button" title="发私信" class="btn-default-secondary btn-current private-letter" value="私信"
                       onclick="" z-st="privateMsg">
                </div>
            </div>

            <div>
                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 42px">
                    <span style="color: white;font-size: 12px">等级</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">1</span>
                </div>


                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 20px">
                    <span style="color: white;font-size: 12px">博客博</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">2</span>
                </div>

                <div style="width: 35px;height: 18px;background-color: #4cae4c;float: left;line-height: 15px;margin-top: 30px;margin-left: 20px">
                    <span style="color: white;font-size: 12px">下载</span>
                </div>
                <div style="width: 18px;height: 18px;background-color: #2b542c;float: left;line-height: 15px;margin-top: 30px;">
                    <span style="color: white;font-size: 12px">2</span>
                </div>
            </div>

            <div style="float: left;margin-top: 40px; margin-left: 130px;">
                <a href="${ctx}/profile?id=${user.id}"><i class="icon icon-edit"></i><span style="margin-left: 10px">修改个人资料</span></a>
            </div>
        </div>
    </div>

    <div class="dreamland-diff">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">博客分类</font>
        </div>
        <div class="list-group">
            <a onclick="changeToActive('category_x',null,null)" class="list-group-item active" id="category_x">全部(${page.total})</a>
            <c:forEach items="${categorys}" var="catetory" varStatus="sta">
                <a onclick="changeToActive('category_${sta.index}','${catetory.category}',null)" class="list-group-item" id="category_${sta.index}">${catetory.category}(${catetory.num})</a>
            </c:forEach>
        </div>
    </div>

    <div class="dreamland-see">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">关注(8人)</font>
        </div>
        <br/>
        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                   白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="${ctx}/images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>

    </div>

    <!--被关注-->
    <div class="dreamland-bysee">
        <div class="customer" style="height: 40px;background-color:#262626;line-height: 40px ">
            <font color="white" size="2.8" face="黑体" style="margin-top: 10px;margin-left: 10px">被关注(8人)</font>
        </div>
        <br/>
        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>


        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg">
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="" >
                <h2 class="author-h2">
                    白龙马
                </h2>
            </a>

        </div>

        <div class="author clearfix" style="margin-left: 10px">
            <div>
                <a href="/users/31123326/" target="_blank" rel="nofollow" style="height: 50px" onclick="">
                     <img src="images/icon_m.jpg" >
                </a>
            </div>
            <a href="/users/31123326/" target="_blank" onclick="">
                <h2 class="author-h2">
                    小黑
                </h2>
            </a>

        </div>

    </div>

</div>

    <!-- 中间内容展示 -->
    <div class="dreamland-content" style="background-color: white">
        <div class="content-bar">
            <div id="fa-dreamland" style="background-color: #B22222;width: 120px;text-align: center;height: 40px;line-height: 40px;float: left" onclick="release_dreamland();">

                 <span id="fa-span" style="color: white">发布的博客</span>

            </div>

            <div id="manage-dreamland" style="background-color: #F0F0F0;width: 120px;text-align: center;height: 40px;line-height: 40px;float: left;margin-left: 20px" onclick="manage_dreamland();">

                <span id="manage-span" style="color: black">管理博客</span>

            </div>

            <div id="personal-div" style="background-color: #F0F0F0;width: 120px;text-align: center;height: 40px;line-height: 40px;float: left;margin-left: 20px" onclick="personal_dreamland();">

                <span id="personal-span"  style="color: black">私密博客</span>

            </div>

        </div>

        <div id="release-dreamland" style="height: 700px;margin-top: 50px;width: 100%">
            <ul style="font-size: 12px" id="release-dreamland-ul">
                <c:forEach var="cont" items="${page.result}" varStatus="i">
                <li class="dreamland-fix">
                    <a>${cont.title}</a>
                    <span class="bar-read">评论 (${cont.commentNum})</span>
                    <span class="bar-commend">${cont.upvote}人阅读</span>


                    <hr/>
                </li>
                </c:forEach>

            </ul>

            <div style="float: left;position: absolute;bottom: 1080px;margin-left: 20px">

                <ul class="pager pager-loose">
                    <c:if test="${page.pageNum <= 1}">
                        <li class="previous"><a href="javascript:void(0);">« 上一页</a></li>
                    </c:if>
                    <c:if test="${page.pageNum > 1}">
                        <li class="previous"><a href="${ctx}/list?pageNum=${page.pageNum-1}&&id=${user.id}">« 上一页</a></li>
                    </c:if>
                    <c:forEach begin="1" end="${page.pages}" var="pn">
                        <c:if test="${page.pageNum==pn}">
                            <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                        </c:if>
                        <c:if test="${page.pageNum!=pn}">
                            <li ><a href="${ctx}/list?pageNum=${pn}&&id=${user.id}">${pn}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${page.pageNum>=page.pages}">
                        <li><a href="javascript:void(0);">下一页 »</a></li>
                    </c:if>
                    <c:if test="${page.pageNum<page.pages}">
                        <li><a href="${ctx}/list?pageNum=${page.pageNum+1}&&id=${user.id}">下一页 »</a></li>
                    </c:if>

                </ul>
            </div>

        </div>
        <div id="update-dreamland" style="height: 700px;margin-top: 50px;width: 100%;display: none">
            <ul style="font-size: 12px" id="update-dreamland-ul">
                <c:forEach var="cont" items="${page.result}" varStatus="i">
                <li class="dreamland-fix">
                    <a>${cont.title}</a>
                    <span class="bar-delete">删除</span>
                    <span class="bar-update">修改</span>
                    <hr/>
                </li>
                </c:forEach>

            </ul>


            <div style="float: left;position: absolute;bottom: 1080px;margin-left: 20px" id="update-dream-div">
                <ul class="pager pager-loose" id="udpate-dreamland-fy">
                    <c:if test="${page.pageNum <= 1}">
                        <li class="previous"><a href="javascript:void(0);">« 上一页</a></li>
                    </c:if>
                    <c:if test="${page.pageNum > 1}">
                        <li class="previous"><a href="${ctx}/list?pageNum=${page.pageNum-1}&&id=${user.id}">« 上一页</a></li>
                    </c:if>
                    <c:forEach begin="1" end="${page.pages}" var="pn">
                        <c:if test="${page.pageNum==pn}">
                            <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                        </c:if>
                        <c:if test="${page.pageNum!=pn}">
                            <li ><a href="${ctx}/list?pageNum=${pn}&&id=${user.id}">${pn}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${page.pageNum>=page.pages}">
                        <li><a href="javascript:void(0);">下一页 »</a></li>
                    </c:if>
                    <c:if test="${page.pageNum<page.pages}">
                        <li><a href="${ctx}/list?pageNum=${page.pageNum+1}&&id=${user.id}">下一页 »</a></li>
                    </c:if>

                </ul>

            </div>
        </div>
        <div id="personal-dreamland" style="height: 700px;margin-top: 50px;width: 100%;display: none">
            <ul style="font-size: 12px" id="personal-dreamland-ul">
                <c:forEach var="cont" items="${page1.result}" varStatus="i">
                <li class="dreamland-fix">
                    <a>${cont.title}</a>
                    <span class="bar-delete">删除</span>
                    <span class="bar-update">修改</span>
                    <hr/>
                </li>
                </c:forEach>

            </ul>

            <div id="personal-dreamland-div" style="float: left;position: absolute;bottom: 1080px;margin-left: 20px">
            <ul class="pager pager-loose" id="personal-dreamland-fy">
                <c:if test="${page2.pageNum <= 1}">
                    <li class="previous"><a href="javascript:void(0);">« 上一页</a></li>
                </c:if>
                <c:if test="${page2.pageNum > 1}">
                    <li class="previous"><a href="${ctx}/list?pageNum=${page2.pageNum-1}&&id=${user.id}">« 上一页</a></li>
                </c:if>
                <c:forEach begin="1" end="${page.pages}" var="pn">
                    <c:if test="${page2.pageNum==pn}">
                        <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                    </c:if>
                    <c:if test="${page2.pageNum!=pn}">
                        <li ><a href="${ctx}/list?pageNum=${pn}&&id=${user.id}">${pn}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${page2.pageNum>=page2.pages}">
                    <li><a href="javascript:void(0);">下一页 »</a></li>
                </c:if>
                <c:if test="${page2.pageNum<page2.pages}">
                    <li><a href="${ctx}/list?pageNum=${page2.pageNum+1}&&id=${user.id}">下一页 »</a></li>
                </c:if>

            </ul>
            </div>
        </div>

        <div style="background-color: #EBEBEB;width:800px;height: 20px">

        </div>

        <div class="hot-dreamland" style="height: 1020px">
            <div style="text-align: center;margin-top: 20px">热博客推荐
                <span style="color:#B22222 ">hot</span>
            </div>

            <div style="height: 700px;margin-top: 30px;width: 100%">
                <ul style="font-size: 12px">
                    <c:forEach var="cont" items="${HotPage.result}" varStatus="i">
                    <li class="dreamland-fix">
                        <a>${cont.title}</a>
                        <span class="bar-read">评论 (${cont.commentNum} )</span>
                        <span class="bar-commend">${cont.upvote}人阅读</span>


                        <hr/>
                    </li>
                    </c:forEach>


                </ul>
            </div>

            <div style="float: left;position: absolute;bottom: 10px;margin-left: 20px">
            <ul class="pager pager-loose">
                <c:if test="${hotPage.pageNum <= 1}">
                    <li class="previous"><a href="javascript:void(0);">« 上一页</a></li>
                </c:if>
                <c:if test="${hotPage.pageNum > 1}">
                    <li class="previous"><a href="${ctx}/list?pageNum=${hotPage.pageNum-1}&&id=${user.id}">« 上一页</a></li>
                </c:if>
                <c:forEach begin="1" end="${hotPage.pages}" var="pn">
                    <c:if test="${hotPage.pageNum==pn}">
                        <li class="active"><a href="javascript:void(0);">${pn}</a></li>
                    </c:if>
                    <c:if test="${hotPage.pageNum!=pn}">
                        <li ><a href="${ctx}/list?pageNum=${pn}&&id=${user.id}">${pn}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${hotPage.pageNum>=hotPage.pages}">
                    <li><a href="javascript:void(0);">下一页 »</a></li>
                </c:if>
                <c:if test="${hotPage.pageNum<hotPage.pages}">
                    <li><a href="${ctx}/list?pageNum=${hotPage.pageNum+1}&&id=${user.id}">下一页 »</a></li>
                </c:if>

            </ul>
            </div>
        </div>

    </div>
<!--右侧-->

<div class="ibx-advice" onmouseover="changeBackColor();" onmouseout="back2color();">
    <a href="${ctx}/writedream?id=${user.id}"><span class="glyphicon glyphicon-edit" aria-hidden="true" style="color:#1b1b1b;font-size:30px;" title="写博客"></span></a>
</div>

<!--底部-->
<div class="foot" style="position: absolute;left: 0px;float: left;margin-top: 1920px;width: 100% ;height: 280px;background-color: #5e5e5e">

    <div style="margin-left: 400px;color: white">
        <div class="foot-nav clearfix">
            <div class="foot-nav-col">
                <h3>
                    关于
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            关于博客境网
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            加入我们
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            联系方式
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    帮助
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            在线反馈
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            用户协议
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="nofollow" style="color: white">
                            隐私政策
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    下载
                </h3>
                <ul style="color: white">
                    <li>
                        <a href=""
                           target="_blank" rel="external nofollow" style="color: white">
                            Android 客户端
                        </a>
                    </li>
                    <li>
                        <a href="" rel="external nofollow" style="color: white">
                            iPhone 客户端
                        </a>
                    </li>
                </ul>
            </div>
            <div class="foot-nav-col">
                <h3>
                    关注
                </h3>
                <ul style="color: white">
                    <li>
                        <a href="http://www.dreamland.wang" onMouseOut="hideImg()"  onmouseover="showImg()" style="color: white">
                            微信
                            <div id="wxImg" style="display:none;height:50px;back-ground:#f00;position:absolute;color: white">
                                <img src="images/dreamland.png"/><br/>
                                手机扫描二维码关注
                            </div>

                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="external nofollow" style="color: white">
                            新浪微博
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" rel="external nofollow" style="color: white">
                            QQ空间
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- rgba(60,63,65,0.31)-->
        <hr style="position: absolute;background-color: rgba(161,171,175,0.31);width: 100%;height: 1px;left: 0px;margin-top: 10px"/>

        <div class="foot-nav clearfix" style="position: absolute;left: 0px;margin-top: 20px;margin-left: 400px;text-align: center">
            <div class="foot-copyrights" style="margin-left: 100px">
                <p style="color: white;font-size: 12px">
                    互联网ICP备案：京ICP备xxxxxx号-1
                </p>
                <p>
                    <span style="color: white;font-size: 12px">违法和不良信息举报电话：010-xxxxxxx</span>
                    <span style="color: white">邮箱：xxx@dreamland.wang</span>
                </p>
                <p style="margin-top: 8px;color: white;font-size: 12px">&copy;www.dreamland.wang 博客境网版权所有</p>
            </div>
        </div>
    </div>
</div>


</body>
<script type="text/javascript" src="${ctx}/js/personal.js?ver=<%=UUID.randomUUID()%>"></script>
</html>