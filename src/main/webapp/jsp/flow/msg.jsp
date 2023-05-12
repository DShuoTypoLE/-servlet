<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>留言</title>
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <script type="text/javascript" src="bs/js/bootstrap.js"></script>
    <script type="text/javascript" src="js/flow/landing.js"></script>
    <link href="css/flow/head_footer.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .wrapper{
            min-height:500px;
        }
        /* 分页样式 */
        .wrapper .pager{
            border-top:1px #eee solid;
            padding-top:15px;
        }
        .wrapper .pager .page-div{
            display: inline-block;
            width:110px;
        }
        .wrapper .homePage,.wrapper .prePage,.wrapper .page-go,.wrapper .nextPage,.lastPage{
            border-radius:15px;
            color:#d7006d;
        }


        .wrapper #page-input{
            display:inline-block;
            width:60px;
            border-radius: 10px;
        }
        .wrapper .flowImg{
            width:50px;
        }
        .wrapper .funbtn{
            margin:10px 0;
        }
        .wrapper .funbtn a{
            margin-right:10px;
        }


    </style>

</head>
<body>
<div class="container-fullid">
    <%@include file="header.jsp" %>
    <div class="wrapper">
        <div class="main container">
            <div id="tab_login" >
                <form  onsubmit="return submits()" action=""  method="post" class="form-horizontal">

                    <div class="form-group">
                        <label for="msg" class="col-md-3 control-label">留言：</label>
                        <div class="col-md-7">
                            <textarea name="msg" style="height: 200px" id="msg" type="text" class="form-control"  placeholder="请输入您的留言"></textarea>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label col-md-offset-4">
                            <input class="btn btn-sm" style="background:#d3b145;color:#fff" type="submit" value="提交">
                        </label>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@include file="footer.jsp" %>
</div>


<script type="text/javascript">
function submits(){
    if($("#msg").val()==''){
        alert('请输入留言内容')
        return
    }
    //发送留言
    $.ajax({
        url:"MsgServlet?action=add",
        async:true,
        data:{
            contain:$("#msg").val()
        },
        type:"POST",
        success:function(data){
            alert("留言成功");
            //这里跳转一下首页,不然感觉好奇怪...
            window.location.href = "jsp/flow/index.jsp";
        }
    })
    return false
}
</script>
</body>
</html>