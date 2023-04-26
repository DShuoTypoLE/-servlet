function addToCart(flowId){
	$.ajax({
		url:"CartServlet?action=add",
		dataType:"json",
		async:true,
		data:{"flowId":flowId},
		type:"POST",
		success:function(data){
			$("#cart .num").html(data);
		}
			
	})
}



