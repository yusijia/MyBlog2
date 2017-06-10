<!-- 百度富文本编辑器内置的代码高亮方案 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
    SyntaxHighlighter.all();
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="data_list post">
	<div class="data_list_title">
		<img src="/static/images/blog_show_icon.png"/>
		博客信息
	</div>
	<div class="blog_title"><h3><strong>${blog.title }</strong></h3></div>
	<div>
		<div class="blog_info">
			发布时间：『 <fmt:formatDate value="${blog.createTime }" type="date" pattern="yyyy-MM-dd HH:mm"/>』&nbsp;&nbsp;博客类别：${blog.blogType.typeName }&nbsp;&nbsp;阅读(${blog.countOfClick }) 评论(${blog.countOfReply })
		</div>
		<div class="blog_content">
			<div id="blog_content">${blog.content }</div>
		</div>
		
		<div class="blog_keyWord">
			<font><strong>关键字：</strong></font>
			<c:choose>
				<c:when test="${keywords==null }">
					&nbsp;&nbsp;无
				</c:when>
				<c:otherwise>
					<c:forEach var="keyword" items="${keywords }">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/blog/q.html?q=${keyword}" target="_blank">${keyword}</a>&nbsp;&nbsp;
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		
		<!-- 上一篇下一篇的链接 -->
		<div class="blog_lastAndNextPage">
			${pageCode }
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/static/showdown-1.7.1/dist/showdown.min.js"></script>
	<script type="text/javascript">
        $(document).ready(function(){
            var converter = new showdown.Converter();
            var content = $("#blog_content").text();
            if(content.substring(0, 9) == 'markdown:') {
                content = content.substr(9);
                content = converter.makeHtml(content);
                $("#blog_content").html(content);
            }
        });
	</script>
</div>