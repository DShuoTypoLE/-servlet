<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
    <base href="${basePath}">
    <meta charset="UTF-8">
    <title>用户留言管理界面</title>
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <script type="text/javascript" src="bs/js/bootstrap.js"></script>
    <style type="text/css">
        body{
            margin:0;
            padding:0;
            background:#eee;
        }
        .content{
            padding:20px 40px 0 40px;
        }
        .page-div{
            display: inline-block;
            width:110px;
        }
        .homePage,.prePage,.page-go,.nextPage,.lastPage{
            border-radius:15px;
            color:#337ab7;
        }
        .pager{
            padding:0 20px;
        }
        #page-input{
            display:inline-block;
            width:60px;
            border-radius: 10px;
        }
        .flowImg{
            width:50px;
        }
        .funbtn{
            margin:10px 0;
        }
        .funbtn a{
            margin-right:10px;
        }
        /* .table #desc{
            display:inline-block;
            width:300px;
            word-break:keep-all;
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        } */
    </style>

</head>
<body>
<c:if test="${!empty msgMessage}">
    <h3 class="text-center">${msgMessage}</h3>
</c:if>
<h2 class="text-center">留言信息管理</h2>
<div class="container content">
    <table class="table table-striped table-hover">
        <tr class="success">
            <th>留言编号</th>
            <th>留言内容</th>
            <th>留言人</th>
            <th>留言时间</th>
            <th>操作</th>
        </tr>
        <c:choose>
            <c:when test="${!empty msgList}">
                <c:forEach items="${msgList}" var="i" varStatus="n">
                    <tr>
                        <td>${i.id}</td>
                        <td>${i.contain}</td>
                        <td>${i.inputperson}</td>
                        <td>${i.inputtime}</td>
                        <td class="noClick">
                            <a class="btn btn-danger btn-xs" href="jsp/admin/MsgServlet?action=delete&id=${i.id}" onclick="javascript:return confirm('确定要删除吗？');">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="8"><h4 class="text-center">当前无更多留言信息</h4></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>

    <ul class="pager">
        <li><button class="homePage btn btn-default btn-sm">首页</button></li>
        <li><button class="prePage btn btn-sm btn-default">上一页</button></li>
        <li>总共 ${pageBean.pageCount} 页 | 当前 ${pageBean.curPage} 页</li>
        <li>
            跳转到
            <div class="input-group form-group page-div">
                <input id="page-input" class="form-control input-sm" type="text" name="page"/>
                <span>
					<button  class="page-go btn btn-sm btn-default">GO</button>
				</span>
            </div>
        </li>
        <li><button class="nextPage btn btn-sm btn-default">下一页</button></li>
        <li><button class="lastPage btn btn-sm btn-default">末页</button></li>
    </ul>
</div>
<script type="text/javascript">
    //按钮禁用限制
    if("${pageBean.curPage}"==1){
        $(".homePage").attr("disabled","disabled");
        $(".prePage").attr("disabled","disabled");
    }
    if("${pageBean.curPage}"=="${pageBean.pageCount}"){
        $(".page-go").attr("disabled","disabled");
        $(".nextPage").attr("disabled","disabled");
        $(".lastPage").attr("disabled","disabled");
    }
    //按钮事件
    $(".homePage").click(function(){
        window.location="${bsePath}jsp/admin/MsgServlet?action=list&page=1";
    })
    $(".prePage").click(function(){
        window.location="${basePath}jsp/admin/MsgServlet?action=list&page=${pageBean.prePage}";
    })
    $(".nextPage").click(function(){

        window.location="${basePath}jsp/admin/MsgServlet?action=list&page=${pageBean.nextPage}";
    })
    $(".lastPage").click(function(){

        window.location="${basePath}jsp/admin/MsgServlet?action=list&page=${pageBean.pageCount}";
    })
    //控制页面跳转范围
    $(".page-go").click(function(){
        var page=$("#page-input").val();
        var pageCount=${pageBean.pageCount};
        if(isNaN(page)||page.length<=0){
            $("#page-input").focus();
            alert("请输入数字页码");
            return;
        }
        if(page > pageCount || page < 1 ){
            alert("输入的页码超出范围！");
            $("#page-input").focus();
        }else{
            window.location="${basePath}jsp/admin/MsgServlet?action=list&page="+page;
        }
    })

    
</script>
</body>
</html>