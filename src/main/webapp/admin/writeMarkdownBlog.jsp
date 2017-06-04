<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>写博客页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/editormd/css/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/editormd/css/editormd.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function submitData(){
		var title=$("#title").val();
		var blogTypeId=$("#blogTypeId").combobox("getValue");
		// 数据库里存Markdown原文,附加一个"markdown:"用于修改的时候识别出是Markdown文档还是普通文档
		var content = Editormd.getMarkdown();
		// 无html标签的博客内容，注意这个没有存到数据库里,用于给Lucene建blogIndex
		var contentNoTag = Editormd.getMarkdown();
		var summary = Editormd.getMarkdown().substr(0,155);
		var keywords=$("#keywords").val();

		if(title==null || title==''){
			alert("请输入标题！");
		}else if(blogTypeId==null || blogTypeId==''){
			alert("请选择博客类别！");
		}else if(content==null || content==''){
			alert("请填写内容！");
		}else{
			// 请求后台的地址要加do后缀，配置文件里配置的
			$.post("${pageContext.request.contextPath}/admin/blog/save.do",
				{
                    'id':'${blog.id}',
					'title' : title,
					'blogType.id' : blogTypeId,
					'content' : "markdown:" + content,
					'contentNoTag' : contentNoTag,
					'summary' : summary,
					'keywords' : keywords
				},
				function(result){
					if(result.success){
                        if(${blog.id==null} || ${blog.id==''}){
                            alert("博客发布成功！");
						}else {
                            alert("博客修改成功！");
                        }
						resultValue();
					}else{
						alert("博客发布失败！");
					}
				},"json");
		}// 向后台传json格式的数据

	}

	// 重置数据
	function resultValue(){
		$("#title").val("");
		$("#blogTypeId").combobox("setValue","");
		Editormd.setMarkdown("");
        $("#keywords").val("");
	}
</script>
</head>
<body style="background: #f6f6f6;">
	<br>
	<label>博客标题：</label>
	<input type="text" id="title" name="title" style="width: 400px"/>

	&nbsp;&nbsp;<label>所属类别：</label>
	<select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="blogType.id" editable="false" panelHeight="auto">
		<option value="">请选择博客类别...</option>
		<c:forEach var="blogType" items="${blogTypeCountList }">
			<option value="${blogType.id }">${blogType.typeName }</option>
		</c:forEach>
	</select>

	&nbsp;<label>关键字:</label>
	<input type="text" id="keywords" name="keywords" placeholder="多个关键字中间用空格隔开" style="width: 200px"/>
	<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">发布博客</a>

	<div id="editormd">
		<textarea id="editormd-content" style="display:none;"></textarea>
	</div>

	<!-- 前面已经引入了这个库 -->
	<%--<script src="${pageContext.request.contextPath }/static/editormd/js/jquery.min.js"></script>--%>
	<script src="${pageContext.request.contextPath }/static/editormd/js/editormd.js"></script>
	<script type="text/javascript">
        var Editormd;
        $(function() {
            Editormd = editormd("editormd", {
                width: "100%",
                height: 640,
                markdown : "",
                path : '${pageContext.request.contextPath }/static/editormd/lib/',
                theme : "dark",
                previewTheme : "dark",
                editorTheme : "pastel-on-dark",
                emoji : true,
                saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
                tex : true,                   // 开启科学公式TeX语言支持，默认关闭
                //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为 true
                //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为 true
                //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为 true
                //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为 0.1
                //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为 #fff
				/*imageUpload : true,
				 imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
				 imageUploadURL : "./php/upload.php?test=dfdf",*/

				/*
				 上传的后台只需要返回一个 JSON 数据，结构如下：
				 {
				 success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
				 message : "提示的信息，上传成功或上传失败及错误信息等。",
				 url     : "图片地址"        // 上传成功时才返回
				 }
				 */
                onload : function() {
                    $("#title").val("${blog.title}");
                    $("#keywords").val("${blog.keywords}");
                    $("#blogTypeId").combobox("setValue","${blog.blogType.id}");
                    var markdownContent = "${blog.content}";
                    markdownContent = markdownContent.substr(9);// 将开头的"markdown:"去掉
                    Editormd.setMarkdown(markdownContent);
                }
            });
        });



	</script>
</body>
</html>