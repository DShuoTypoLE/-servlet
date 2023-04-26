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
	<title>鲜花详情页</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
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
<body>
	<h2 class="text-center">鲜花详情</h2>
	<div class="container">
		<div class="row">
			<div class="col-md-2 text-right">鲜花id</div>
			<div class="col-md-10">${flowInfo.flowId}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">鲜花名称</div>
			<div class="col-md-10">${flowInfo.flowName}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">鲜花分类</div>
			<div class="col-md-10">${flowInfo.catalog.catalogName}</div>
		</div>
		
		<div class="row">
			<div class="col-md-2 text-right">鲜花价格</div>
			<div class="col-md-10">￥${flowInfo.price}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">上架日期</div>
			<div class="col-md-10">${flowInfo.addTime}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">关键字</div>
			<div class="col-md-10">${flowInfo.keywords}</div>
		</div>
		<div class="row">
			<div class="col-md-2 text-right">鲜花简介</div>
			<div class="col-md-10">${flowInfo.description}</div>
		</div>
		<div>
			<img class="col-md-6 col-md-offset-2" alt="" src="${flowInfo.upLoadImg.imgSrc}">
		</div>
		
	</div>
</body>
</html>