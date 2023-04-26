<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<base href="${basePath}">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>惜惜花店</title>
	<link rel="stylesheet" href="bs/css/bootstrap.css">
	<script type="text/javascript" src="bs/js/jquery.min.js"></script>
	<script type="text/javascript" src="bs/js/bootstrap.js"></script> 
	<script type="text/javascript" src="js/flow/landing.js"></script>
	<link href="css/flow/head_footer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/flow/addcart.js"></script>
	<style type="text/css">
		.wrapper .pro_info{
			border-bottom: 1px #ccc solid;
			line-height: 34px;
			margin-top:20px;
		}
		.wrapper .pro_info tr td:first-child{
			font-weight: bold;
			
		}
		.wrapper .pro_info i{
			color:#333333;
			font-size:22px;
		}
		.wrapper .buy_pro{
			margin-top:20px;
			
		}
		.wrapper .pro_desc{
			margin:10px;
		}
		
		.wrapper .pro_desc h3{
			border-bottom: 1px #ccc solid;
			padding:10px;
		}
		.wrapper .pro_desc div{
			text-indent: 2em;
			line-height: 2em;
		}
		
	</style>
</head>
<body>

	<div class="container-fullid">
		<%@include file="header.jsp" %>
		<div class="wrapper">
			<!-- main start -->
			<div class="main container">
				<div class="sub-nav">
					<ol class="breadcrumb">
  						<li><a href="jsp/flow/index.jsp">主页</a></li>
						<li><a href="#">${flowInfo.catalog.catalogName}</a></li>
						<li class="active">${flowInfo.flowName}</li>
					</ol>
				</div>
				<div class="row">
					<div class="col-md-5">
						<img class="img-responsive" src="${flowInfo.upLoadImg.imgSrc}" />
					</div>
					<div class="col-md-7">
						<table class="pro_info">
								<tr>
									<td colspan="3"><h2>${flowInfo.flowName}</h2></td>
								</tr>
								<tr>
									<td>价格：</td>
									<td><i>￥${flowInfo.price}</i></td>
								</tr>
								<tr>
									<td>鲜花编号：</td>
									<td>${flowInfo.flowId}</td>
								</tr>
								<tr>
									<td>鲜花分类：</td>
									<td>${flowInfo.catalog.catalogName}</td>
								</tr>
								
								
								<tr>
									<td>上架日期：</td>
									<td>${flowInfo.addTime}</td>
								</tr>
								<tr>
									<td>服务：</td>
									<td>由惜惜花店发货,并提供售后服务,预计七天内送达，我们会全力加快为您服务！</td>
								</tr>
							</table>
						<p class="buy_pro">
								<button type="button" class="btn" style="background:#333333;color:#fff;" onclick="addToCart(${flowInfo.flowId})" data-toggle="modal" data-target=".bs-example-modal-sm">
								<img class="cart-icon" src="images/flow/btn-cart-icon.png" alt="">
								加入购物车</button>
						</p>
						<p>温馨提示：支持7天无理由退货</p>
					</div>
					
				</div>
				<div class="row pro_desc">
					<h3>鲜花简介</h3>
					<div>${flowInfo.description}</div>
				</div>
				
			</div>
			
			
		</div>
								
		<%@include file="footer.jsp" %>
	</div>
<!--弹窗盒子start -->
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  	<div class="modal-dialog modal-sm">
    	<div class="modal-content" style="margin-top:300px;">
    		<div class="modal-body" style="color:green;font-size:20px;">
    		  	<img class="icon" src="images/flow/success-icon.png" alt="">
			  	 已成功加入购物车
			</div>
    			
      		<div class="modal-footer">
      			<a href="javascript:void(0)" type="button" class="btn  btn-md btn-default" data-dismiss="modal">返回继续购物</a>
		        <a href="jsp/flow/cart.jsp" type="button" class="btn btn-md btn-success">立即去结算</a>
		    </div>
    	</div>
  	</div>
</div>
<!--弹窗盒子end -->
</body>
</html>