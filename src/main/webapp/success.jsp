<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <script type="text/javascript">

        function submitData(){
            $.post("${pageContext.request.contextPath }/user/testToken2.do",
                {
                    token : "${token}"// 记得异步访问的时候在这里将客户端的token参数提交
                },function(result){
                    if(result.errorMessage){
                        alert(result.errorMessage);
                    }
                },"json");
        }
    </script>
</head>
<body>
欢迎：${currentUser.userName }

<h1>注意：测试下面两个表单提交时，一次登入只能测试其中一个都有相同的隐藏域token</h1>

<p>测试普通表单重复提交：</p>
<form action="${pageContext.request.contextPath }/user/testToken.do" method="post">
	
	<input type="text" name="userText" value="userText"/>
	
	<input type="submit" value="login"/><font color="red">${errorMsg }</font>
	<input type="hidden" name="token" value="${token}" />
</form>

<p>测试异步表单重复提交：</p>
<form id="form2" method="post" >

    <input type="text" name="userText" value="userText"/>
    <input type="hidden" id="token" name="token" value="${token}" />

    <input id="button2" type="button" value="提交" onclick="submitData()"/>

</form>

</body>
</html>