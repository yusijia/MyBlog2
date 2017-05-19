<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/about_icon.png"/>
		关于博主
	</div>
	<!-- 博主信息在Application中静态化了，但这里还要设置一下标题啥的所以，要从后台跳转并取得相关数据 -->
	<div style="padding: 30px">
		${blogger.profile }
	</div>
</div>