$.ajax({
	url:"ShopIndex",
	dataType:"json",
	async:true,
	data:{},
	type:"POST",
	success:function(data){
		datalist(data);
	}
		
})

function datalist(data){

	//推荐鲜花
	if(data.recFlows!=null){
		$.each(data.recFlows,function(i,n){
			var tag="<li class='col-md-3'>" +
				"<div class='list'>" +
			"<a href='flowdetail?flowId="+n.flowId+"'><img class='img-responsive' style='width: 100%;height: 200px;'  src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='flowdetail?flowId="+n.flowId+"'>"+n.flowName+"</a></h2>"+
			"<p><i>￥"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.flowId+")' " +
				"data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			
			$("#recFlows ul").append(tag);
		})
	}
	
	//新增加的
	if(data.newFlows!=null){
		$.each(data.newFlows,function(i,n){
			var tag="<li class='col-md-3'><div class='list'>" +
			"<a href='flowdetail?flowId="+n.flowId+"'><img class='img-responsive' style='width: 100%;height: 200px;'  src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='flowdetail?flowId="+n.flowId+"'>"+n.flowName+"</a></h2>"+
			"<p><i>￥"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.flowId+")' " +
				"data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			
			$("#newFlows ul").append(tag);
			
		})
	}
	
	
}
