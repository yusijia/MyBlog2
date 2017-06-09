<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta NAME="Author" CONTENT="余思嘉">
<meta name="keywords" content="MyBlog|yusijia" />
<meta name="description" content="yusijia的博客" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function checkInput(){
		var q=document.getElementById("q").value.trim();
		if(q==null || q=="") {
			alert("请输入您要查询的关键字！");
			return false;
		} else {
			// $("#search-form").submit();
			return true;
		}
	}
</script>
<title>${pageTitle }</title>
<style type="text/css">
	body{
		padding-top: 10px;
		padding-bottom: 40px;

		font-family: "Source Code Pro",Tahoma,"Helvetica Neue",Helvetica,"Hiragino Sans GB","Microsoft YaHei Light","Microsoft YaHei","Source Han Sans CN","WenQuanYi Micro Hei",Arial,sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		font-size: 14px;
		color: #333;
		width: 100%;
		height: 100%;
		background: #d8e2eb url(${pageContext.request.contextPath}/static/images/bg.jpg) no-repeat top center;
	}
	.widget {
		margin-bottom: 20px;
		padding: 10px 20px 20px 20px;
		border-radius: 4px;
		-webkit-box-shadow: 1px 2px 3px #adc2d7;
		box-shadow: 1px 2px 3px #adc2d7;
		border: 1px solid #adc2d7;
		background: #fff;
	}
	.post {
		padding: 25px;
		background: #fff;
		border-radius: 4px;
		-webkit-box-shadow: 1px 2px 3px #adc2d7;
		box-shadow: 1px 2px 3px #adc2d7;
		border: 1px solid #adc2d7;
		margin-bottom: 40px;
	}
	.post .post-title {
		margin: 0;
		color: #40759b;
		text-align: left;
		border-bottom: 1px solid #d8e2eb;
		padding-bottom: 10px;
		font-weight: bold;
		font-size: 22px;
		line-height: 1.1;
	}
	.post .post-meta {
		float: left;
		height: 16px;
		line-height: 16px;
		display: inline;
	}
	.post .post-content {
		clear: left;
		font-size: 15px;
		line-height: 1.77;
		color: #333;
		padding-top: 15px;
		text-align: justify;
		word-break: normal;
	}
	.content_container {
		padding-right: 40px;
		width: 75%;
	}
	.readmore a {
		font-size: 14px;
		color: #40759b;
		margin: -20px 0 0;
		padding: 5px 10px;
		float: right;
	}
	.blog-motto {
		font-size:120%;
		font-weight:normal;
	}
	.nav-menu {
		margin: 20px 0;
		padding: 0;
		position: absolute;
		right: 0;
		top: 0;
	}
	.search-form-input {
		box-sizing: border-box;
		height: 32px;
		padding: 7px 11px 7px 35px;
		line-height: 16px;
		width: 100%;
		border-radius: 15px;
		background: #fff;
		-webkit-box-shadow: 1px 2px 3px #adc2d7;
		box-shadow: 1px 2px 3px #adc2d7;
		border: 1px solid #adc2d7;
	}
	.header {
		text-align: left;
		position: relative;
		min-height: 300px;
	}
	.site-name {
		width:86%;
		font-family:"covered_by_your_graceregular";
		font-size:200%;
		line-height:1.5;
		-webkit-font-smoothing:antialiased;
		-moz-osx-font-smoothing:grayscale;
	}
	.logo {
		white-space: nowrap;
		color: rgb(255, 255, 255);
		text-shadow: rgb(17, 63, 110) 1px 3px 6px;
		font: bold 42px/1.2 宋体;
	}
</style>
</head>
<body>
<div class="container">
	<jsp:include page="/foreground/common/head.jsp" />
	
	<div class="row">
	  <div class="col-md-9">
	  	<jsp:include page="${mainPage }"></jsp:include>
	  </div>

	  <div class="col-md-3">
		  	<div class="data_list widget" style="padding-bottom: 20px;">
				<div class="datas">
					<form id="search-form" action="${pageContext.request.contextPath}/blog/q.html"
						  onkeydown="if(event.keyCode==13){return checkInput();}">
						<label>搜索：</label><input style="padding-right: 0px;" class="form-control"
							   placeholder="请输入要查询的关键字..." type="text"
							   id="q" name="q" value="${q }">
					</form>
				</div>
			</div>

	  		<div class="data_list widget">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/user_icon.png"/>
					博主信息
				</div>
				<div class="user_image">
					<a class="thumbnail" href="#"><img src="${pageContext.request.contextPath}/static/userImages/${blogger.imageName}"/></a>
				</div>
				<div class="nickName">${blogger.nickName}</div>
				<div class="userSign">(${blogger.sign})</div>
			</div>
	  	
	  		<div class="data_list widget" >
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/byType_icon.png"/>
					分类
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="blogTypeCount" items="${blogTypeCountList }">
							<li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${blogTypeCount.id}">${blogTypeCount.typeName }(${blogTypeCount.blogCount })</a></span></li>
						</c:forEach>
						
					</ul>
				</div>
			</div>
	  		
	  		
	  		
			<div class="data_list widget">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/byDate_icon.png"/>
					按日志日期
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="blogCount" items="${blogTypesAndCountsByDate }">
							<li><span><a href="${pageContext.request.contextPath}/index.html?releaseDateStr=${blogCount.releaseDateStr}">${blogCount.releaseDateStr }(${blogCount.blogCount })</a></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div class="data_list widget">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/link_icon.png"/>
					友情链接
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="link" items="${linkList }">
							<li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
	  		
	  </div>
	</div>

	<jsp:include page="/foreground/common/foot.jsp"></jsp:include>
	
</div>
</body>
</html>