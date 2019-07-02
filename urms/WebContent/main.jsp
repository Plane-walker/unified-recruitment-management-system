<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>统一招聘管理系统</title>
<%session.removeAttribute("logwarn");session.removeAttribute("regwarn");%>
</head>
<body>
<div class='card mb-4 shadow-sm col-md-4 offset-4'>
<div class='card-header'>
<h2 class="text-primary text-center">统一招聘管理系统</h2>
</div>
<div class='card-body'>
<div class='card-deck mb-3 text-center'>
<div class='card mb-4 shadow'>
<div class='card-header'>
<h2>应聘者</h2>
</div>
<div class='card-body'>
<a href="login_app">>>登录入口</a>
</div>
</div>
<div class='card mb-4 shadow'>
<div class='card-header'>
<h2>公司</h2>
</div>
<div class='card-body'>
<a href="login_com">>>登录入口</a>
</div>
</div>
</div>
</div>
</div>
</body>
</html>