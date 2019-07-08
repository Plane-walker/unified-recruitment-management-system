<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>登录-统一招聘管理系统</title>
<%session.removeAttribute("regwarn");%>
</head>
<body>
<div class='card mb-4 shadow col-md-4 offset-4'>
<div class='card-header'>
<h2 class="text-primary text-center"><a href="main">统一招聘管理系统</a>|登录</h2>
</div>
<div class='card-body'>
<form role="form" action="Logserv" method="post">
        <div class="form-group form-inline">
            <label class="control-label col-md-3">用户名：</label>
            <input type="text" class="form-control col-md-6" name="userID" value="${userID}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-3">密码：</label>
            <input type="password" class="form-control col-md-6" name="password" value="${password}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <div class="col-md-4"><input class="btn btn-primary" type="submit" value="登录"></div>
            <div class="col-md-2"><a href="register_app">注册</a></div>
            <div class="col-md-6"><font color="red"><%if((String)session.getAttribute("logwarn")!=null)out.print((String)session.getAttribute("logwarn"));%></font></div>
            </div>
            
</form>
</div>
</div>
</body>