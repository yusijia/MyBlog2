<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客类别管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	var url;
	
	function openBlogTypeAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加博客类别信息");
		url="${pageContext.request.contextPath}/admin/blogType/save.do";
	}
	
	function openBlogTypeModifyDialog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一个要修改的博客类别！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改博客类别信息");
		// form方法通过id的匹配将row行对象里的数据加载到#fm表单里
		$("#fm").form("load",row);
		url="${pageContext.request.contextPath}/admin/blogType/save.do?id="+row.id;
	}
	
	function saveBlogType(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");// 验证表单里的数据
			},
			success:function(result){
				// 将后台传来的json字符串转为json对象
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败！");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#typeName").val("");
		$("#orderNo").val("");
	}
	
	function closeBlogTypeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function deleteBlogType(){
		// 返回选中的所有行对象
		var selectedRows=$("#dg").datagrid("getSelections");
		// 返回所有行对象
		var Rows = $("#dg").datagrid("getRows");
		// 存因为类别下有文章而删除失败的博客类别名
		var blogTypeNmae=[];
		if(selectedRows.length == 0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		// 存选中的所有行对象的id(BlogType的Id)
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/admin/blogType/delete.do",
					{
						ids:ids
					},function(result){
					if(result.success){// 返回成功
						if(result.exist){// 有的博客类型下有文章不能删除，没有文章的可以删除
							// $.messager.alert(result.blogTypeId);
							for(var i = 0; i < result.blogTypeId.length; i++){
								for(var j = 0; j < Rows.length; j++){
									if(Rows[j].id == result.blogTypeId[i]){
										blogTypeNmae.push(Rows[j].typeName);
									}
								}
							}
							$.messager.alert("系统提示", '[' + blogTypeNmae + ']' + result.exist);
						}else{
							$.messager.alert("系统提示","数据已成功删除！");							
						}
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","数据删除失败！");
					}
				},"json");
			}
		});
	}
	
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客类别管理" class="easyui-datagrid" 
  fitColumns="true" pagination="true" rownumbers="true"
  url="${pageContext.request.contextPath}/admin/blogType/list.do" fit="true" toolbar="#tb">
  <thead>
  	<tr>
  		<th field="cb" checkbox="true" align="center"></th>
  		<th field="id" width="20" align="center">编号</th>
  		<th field="typeName" width="100" align="center">博客类型名称</th>
  		<th field="orderNo" width="100" align="center">排序序号</th>
  	</tr>
  </thead>
</table>

<div id="tb">
	<div>
		<a href="javascript:openBlogTypeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openBlogTypeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteBlogType()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 500px;height: 180px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
			<tr>
				<td>博客类别名称：</td>
				<td>
					<input type="text" id="typeName" name="typeName" class="easyui-validatebox" required="true"/>
				</td>
			</tr>
			<tr>
				<td>博客类别排序：</td>
				<td>
					<input type="text" id="orderNo" name="orderNo" class="easyui-numberbox" required="true" style="width: 60px"/>&nbsp;(类别根据排序序号从小到大排序)
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveBlogType()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeBlogTypeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>