<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class="data_list">
		<c:forEach var="blog" items="${blogList }">
			<div class="datas post">
				<ul>
					<%--<c:forEach var="blog" items="${blogList }">--%>
						<h2 class="post-title"><a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">${blog.title }</a></h2>
						<li style="margin-bottom: 30px">
							<span class="post-meta"><a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html"><fmt:formatDate value="${blog.createTime }" type="date" pattern="yyyy年MM月dd日"/> | </a></span>
							<span class="post-meta"><a href="${pageContext.request.contextPath}/index.html?typeId=${blog.blogType.id}" >&nbsp;${blog.blogType.typeName }</a></span></br>
							<div class="post-content">${blog.summary }...</div><!-- 摘要 -->

							<span class="img">
								<c:forEach var="image" items="${blog.imageList }">
									<a href="/blog/articles/${blog.id }.html">${image }</a>
									&nbsp;&nbsp;
								</c:forEach>
							</span>
							<span class="info" style="color: #40759b;">发表于 <fmt:formatDate value="${blog.createTime }" type="date" pattern="yyyy-MM-dd HH:mm"/> 阅读(${blog.countOfClick }) 评论(${blog.countOfReply }) </span></br>
						</li>
						<p class="readmore"><a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">阅读更多>></a></p>
						<!--<hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />-->
				</ul>
			</div>
		</c:forEach>
  	</div>
  	
  	<!-- 分页代码 -->
  	<div>
		<nav>
		  <ul class="pagination pagination-sm">
		  	${pageCode }
		  </ul>
		</nav>
	</div>