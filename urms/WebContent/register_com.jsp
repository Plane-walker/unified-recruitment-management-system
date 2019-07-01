<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page contentType="text/html; charset=UTF-8"  %>
<!DOCTYPE html>
<html>
<head>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>注册-统一招聘管理系统</title>
<%session.removeAttribute("logwarn");%>
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
<% response.setContentType("text/html;charset=utf-8"); 
request.setCharacterEncoding("utf-8"); %>
<div class="container">
<div class="row row-centered">
<div class="well col-md-6 col-centered">
<div class='card mb-4 shadow'>
<div class='card-header'>
<h2 class="text-primary text-center"><a href="main">统一招聘管理系统</a>|注册</h2>
</div>
<div class='card-body'>
<form action="Regserv" method="post">
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*用户名：</label>
            <input class="form-control col-md-6" type="text" name="userID" value="${userID}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*昵称：</label>
            <input type="text" class="form-control col-md-6" name="username" value="${username}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*密码：</label>
            <input type="password" class="form-control col-md-6" name="password" value="${password}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*重复密码：</label>
            <input type="password" class="form-control col-md-6" name="repassword" value="${repassword}" autocomplete="off">
        </div>
        <div class="form-group form-inline">
            <label class="control-label col-md-4">*国家或地区：</label>
            <select class="form-control col-md-6" name="country" >
            <option value="China">中国</option>
            <option value="USA">美国</option>
            <option value="UK">英国</option>
            <option value="Russia">俄罗斯</option>
            <option value="France">法国</option>
            </select>
        </div>
        <div class="form-group form-inline">
            <div class="col-md-4"><input class="btn btn-primary" type="submit" value="注册"></div>
            <div class="col-md-4"><a href="login_com">登录</a></div>
            <div class="col-md-4"><font color="red"><%if((String)session.getAttribute("regwarn")!=null)out.print((String)session.getAttribute("regwarn"));%></font></div>
        </div>
</form>
</div>
</div>
</div>
</div>
</div>
</body>