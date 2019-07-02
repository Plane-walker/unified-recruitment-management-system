<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="style.css?v=1.6" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js" ></script>

<meta charset="UTF-8">
<title>招聘中心-统一招聘管理系统</title>

</head>
<body>
<script type="text/javascript">
var com_ID="";
var com_name="";
var pos_name="";
var page=1;
var last=false;
function psw(){
	var html="";
	if(!((last&&page==1)||com_ID!=""||com_name!=""||pos_name!="")){
	if(page>1)
		html+="<button class='control-label col-md-2 btn btn-info' onclick='switchp()'>上一页</button>";
	html+="<label class='control-label col-md-1'>第</label>";
    html+="<input type='text' class='form-control col-md-1' id='paget' name='paget' value='${paget}' autocomplete='off'>";
    html+="<button class='control-label col-md-2 btn btn-info' onclick='switchl()'>下一页</button>";
    html+="<button class='control-label col-md-2 btn btn-info' onclick='jump()'>跳页</button>";
    $("#pageswitch").html(html);
    $("#paget").val(page);
    }
}
function switchl(){
	if(!last){
	page++;
	refresh();
	psw();
	}
}
function switchp(){
	page--;
	refresh();
	psw();
}
function jump(){
	page=$("#paget").val();
	refresh();
	psw();
}
function refresh(){
	  var url = "Refreshserv";
	  var data = {"com_ID":com_ID,"com_name":com_name,"pos_name":pos_name,"size":"9","page":page};
	  $.ajax({
	   type :"post",
	   dataType: "json",
	   url : url,
	   data : data,
	   timeout:1000,
	   success:function(dates){
		   var html="";
		   if(dates.length<9)
			   last=true;
		   else
			   last=false;
		   if(dates.length==0)
			   html+="<p class='text-warn'>找不到招聘信息</p>";
			   else{
		   for(var i=0;i<dates.length;i++){
			   if(i%3==0)
			   html+="<div class='card-deck mb-3 text-center'>";
		   html+="<div class='card mb-4 shadow'><div class='card-header'>";
		   html+="<h4 class='my-0 font-weight-normal'>"+dates[i].com_name+"</div>";
		   html+="<div class='card-body'>";
		   html+="<h4 class='card-title'>"+dates[i].name+"</h4>";
		   html+="<p class='card-title'>月薪："+dates[i].salary+"</p>";
		   html+="<p class='card-title'>工作地点："+dates[i].city+"</p>";
		   html+="<p class='card-title'>学历要求："+dates[i].academic+"</p>";
		   html+="<p class='card-title'>需求人数："+dates[i].number+"</p>";
		   html+="<p class='card-title'>类型："+dates[i].type+"</p>";
		   html+="<p class='card-title d-none'>详情："+dates[i].information+"</p>";
		   html+="<button class='btn btn-info details'>详情</button>";
		   html+="<button class='btn btn-primary'>应聘</button>";
		   html+="</div></div>";
			   if(i%3==2)
			   html+="</div>";
	   }
		   if(dates.length%3!=0){
		   for(var i=0;i<3-dates.length%3;i++){
			   html+="<div class='card mb-4 emptycard'>";
			   html+="<div class='card-body'>";
			   html+="</div></div>";
		   }
		   html+="</div>";
		   }
			   }
		   $("#poscard").html(html);
		   psw();
	   },
	   error:function() {
	       }
	  });
	  };
function exit(){
	var url = "Exitserv";
	var data = {};
	 $.ajax({
		   type :"post",
		   dataType: "json",
		   url : url,
		   data : data,
		   timeout:1000,
		   success:function(dates){
			   window.location.reload();
		   },
		   error:function() {
		       }
		  });
};
$(function(){
	 refresh();
})
</script>
<div class="wrapper">
    <!-- Sidebar -->
    <nav id="sidebar" class="fixed-top">
        <div class="sidebar-header p-3 mb-5 emptycard">
        </div>

        <ul class="list-unstyled components shadow p-3 mb-5 bg-white rounded">
        <li><h4><a> <img src=<%out.print((String)session.getAttribute("avator"));%> class="rounded-circle" width="50" height="50"/> 
				   <%out.print((String)session.getAttribute("name"));%>
				</a></h4></li>
            <li class="active">
                <a href="#"><i class="fa fa-fw fa-user-circle"></i> 个人信息</a>
            </li>
            <li>
                <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-fw fa-plus"></i> 招聘市场 </a>
                <ul class="collapse list-unstyled" id="pageSubmenu">
                <li><a href="#" onclick="return refresh()"><i class="fa fa-fw fa-bug"></i> 综合</a></li>
                    <li><a href="#"><i class="fa fa-fw fa-bug"></i> IT</a></li>
				<li><a href="#"><i class="fa fa-fw fa-bank"></i> 金融</a></li>
				<li><a href="#"><i class="fa fa-fw fa-gavel"></i> 建筑</a></li>
				<li><a href="#"><i class="fa fa-fw fa-cutlery"></i> 服务</a></li>
				<li><a href="#"><i class="fa fa-fw fa-circle-o"></i> 其他</a></li>
                </ul>
            </li>
            <li>
                <a href="#"><i class="fa fa-fw fa-bell"></i> 消息</a>
            </li>
            <li>
                <a id="exit" href="javascript:void(0);" onclick="return exit()"><i class="fa fa-fw fa-power-off"></i> 退出登录</a>

            </li>
        </ul>
    </nav>
<div id="content">
    <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light p-3 mb-5 bg-white rounded shadow-sm">
        <div class="container-fluid">

            <button type="button" id="sidebarCollapse" class="btn btn-info">
                <i class="fa fa-fw fa-bars"></i>
                <span></span>
            </button>

        </div>
    </nav>
    <div id="poscard" class="fixborder movement"></div>
    <div id="pageswitch" class="fixborder movement form-group form-inline col-md-8 offset-3"></div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#poscard').toggleClass('fixborder');
        $('#pageswitch').toggleClass('fixborder');
    });
    $("#poscard").on('click','.details',function () {
    	var aimcard=$(this).parent().parent();
    	aimcard.addClass("offset-4");
    	aimcard.addClass("col-md-4");
    	aimcard.children().children(".d-none").removeClass("d-none");
    	$("#poscard").html(aimcard);
    	$(this).html("返回");
    	$(this).removeClass("details");
    	$(this).attr("onclick","refresh()");
    	$('html , body').animate({scrollTop: 0},0);
    	
    });
});
</script>
</body>
</html>