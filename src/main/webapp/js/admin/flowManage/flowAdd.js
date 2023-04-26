$(function(){
	var form=$("#flowAddForm").Validform({
		tiptype:2,//validform初始化
	});
	
	form.addRule([
		{
			ele:"#flowName",
		    datatype:"*2-20",
		    ajaxurl:"jsp/admin/FlowManageServlet?action=find",
		    nullmsg:"请输入鲜花名称！",
		    errormsg:"鲜花名称至少2个字符,最多20个字符！"
		},
		{ 
			ele:"#catalog",
			datatype:"*",
			nullmsg:"请选择鲜花分类！",
			errormsg:"请选择鲜花分类！"
		},
		{
			ele:"#price",
			datatype:"/^[0-9]{1,}([.][0-9]{1,2})?$/",
			mullmsg:"价格不能为空！",
			errormsg:"价格只能为数字"
		},
		
		{
			ele:"#flowImg",
		    datatype:"*",
		    nullmsg:"请上传鲜花图片！",
		    errormsg:"请上传图书图片！"
		}
	
	]);
	
});

