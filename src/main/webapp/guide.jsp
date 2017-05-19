<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- springmvc会识别.html的请求, 这样达到伪静态的效果，方便优化 -->
<% response.sendRedirect("index.html"); %>