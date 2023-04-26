<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>


    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>设置配送员</title>
    <link rel="stylesheet" type="text/css" href="bs/css/bootstrap.css">
    <script type="text/javascript" src="bs/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="bs/validform/style.css">
    <script type="text/javascript" src="bs/validform/Validform_v5.3.2_min.js"></script>
    <style type="text/css">
        body{
            margin:0;
            padding:0;
            background:#eee;
        }

        .container .row{
            line-height: 30px;
            htight:30px;
        }

    </style>
</head>
<script  type="text/javascript">
    $(function(){
        var form=$("#flowAddForm").Validform({
            tiptype:2,//validform初始化
        });

        form.addRule([

            {
                ele:"#charger",
                datatype:"*",
                nullmsg:"请选择配送员！",
                errormsg:"请选择配送员！"
            },


        ]);

    });

</script>
<body>
<h2 class="text-center">订单详情</h2>
<div class="container">
    <form id="flowAddForm" class="form-horizontal" action="jsp/admin/OrderManageServlet?action=updateCharger" method="post">
        <input type="hidden" name="id" value="${order.orderId}">

        <div class="form-group">
            <label for="orderNum" class="col-sm-2 col-sm-offset-2 control-label">订单号</label>
            <div class="col-sm-4">
                <input type="text" readonly name="orderNum" id="orderNum" class="form-control" value="${order.orderNum}">
            </div>
            <div class="col-sm-4 Validform_checktip"></div>
        </div>

        <div class="form-group">
            <label for="charger" class="col-sm-2 col-sm-offset-2 control-label">配送员</label>
            <div class="col-sm-4">
                <select name="charger" id="charger" class="form-control">
                    <option value="">==请选择配送员==</option>
                    <c:if test="${!empty charger}">
                        <c:forEach items="${charger}" var="i">
                            <c:choose>
                                <c:when test="${i.id eq order.chargerid}">
                                    <option value="${i.id}" selected>${i.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${i.id}">${i.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <div class="col-sm-4 Validform_checktip"></div>
        </div>

        <div class="form-group">
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-4 control-label">
                    <input class="btn btn-success btn-block" type="submit" value="提交">
                </label>
                <label class="col-sm-2 control-label">
                    <input class="btn btn-warning btn-block" type="reset" value="重置">
                </label>

            </div>

        </div>

    </form>


</div>
</body>
</html>