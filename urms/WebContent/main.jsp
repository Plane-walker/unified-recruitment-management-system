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
<style>
            /*web background*/
            .container{
                display:table;
                height:100%;
            }

            .row{
                display: table-cell;
                vertical-align: middle;
            }
            /* centered columns styles */
            .row-centered {
                text-align:center;
            }
            .col-centered {
                display:inline-block;
                float:none;
                text-align:left;
                margin-right:-4px;
            }
        </style>
</head>
<body>
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<div class='card mb-4 shadow-sm'>
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
</div>
</div>
</div>
</body>
</html>