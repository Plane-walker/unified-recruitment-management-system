<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="style.css?v=1.4" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js" ></script>

<meta charset="UTF-8">
<title>招聘中心-统一招聘管理系统</title>

</head>
<body>
<script type="text/javascript">
var com_ID="<%out.print((String)session.getAttribute("ID"));%>";
var com_name="";
var pos_name="";
var page=1;
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
		   if(dates.length<10)
			   last=true;
		   else
			   last=false;
		   if(dates.length==0)
			   html+="<p class='text-warn'>无招聘信息</p>";
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
		   html+="<button class='btn btn-primary'>处理</button>";
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
	   },
	   error:function() {
	       }
	  });
	  };
	  function createpos(){
		  var html="";
		  html+="<div class='card mb-4 shadow col-md-4 offset-4'>"+
		  "<div class='card-header'>"+
		  "<h2 class='text-primary text-center'>发布职位</h2></div>"+
		  "<div class='card-body'>"+
			  "<form action='' id='publishinfo'>"+
			  "<div class='form-group form-inline'>"+
	            "<label class='control-label col-md-5'>*职位名：</label>"+
	            "<input class='form-control col-md-6' type='text' name='posname' value='${posname}' autocomplete='off'>"+
	        "</div>"+
	        "<div class='form-group form-inline'>"+
            "<label class='control-label col-md-5'>*月薪：</label>"+
            "<input class='form-control col-md-6' type='text' name='salary' value='${salary}' autocomplete='off'>"+
        	"</div>"+
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>*工作地点：</label>"+
        	"<input class='form-control col-md-6' type='text' name='city' value='${city}' autocomplete='off'>"+
    		"</div>"+
    		"<div class='form-group form-inline'>"+
    		"<label class='control-label col-md-5'>*人数：</label>"+
    		"<input class='form-control col-md-6' type='text' name='number' value='${number}' autocomplete='off'>"+
			"</div>"+
			"<div class='form-group form-inline'>"+
            "<label class='ontrol-label col-md-5'>*类别：</label>"+
            "<select class='form-control col-md-6' name='pos_type' >"+
            "<option value='IT'>IT</option>"+
            "<option value='金融'>金融</option>"+
            "<option value='建筑'>建筑</option>"+
            "<option value='服务'>服务</option>"+
            "<option value='其他'>其他</option>"+
            "</select>"+
        "</div>"+
    		"<div class='form-group form-inline'>"+
    		"<label class='control-label col-md-5'>学历要求：</label>"+
    		"<input class='form-control col-md-6' type='text' name='academic' value='${academic}' autocomplete='off'>"+
			"</div>"+
			"<div class='form-group form-inline'>"+
    		"<label class='control-label col-md-5'>详细信息：</label>"+
    		"<textarea class='form-control col-md-6' name='information' value='${information}'></textarea>"+
			"</div></form>"+
			"<div class='form-group form-inline'>"+
            "<div class='col-md-4'><button id='publish' class='btn btn-primary' onclick='return publishpos()'>发布</button></div>"+
            "<div class='col-md-8' id='publishwarn'></div>"+
        "</div>";
		  $("#poscard").html(html);
	  }
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
});
(function($){
    $.fn.serializeJson=function(){
      var serializeObj={};
      var array=this.serializeArray();
      var str=this.serialize();
      $(array).each(function(){
        if(serializeObj[this.name]){
          if($.isArray(serializeObj[this.name])){
            serializeObj[this.name].push(this.value);
          }else{
            serializeObj[this.name]=[serializeObj[this.name],this.value];
          }
        }else{
          serializeObj[this.name]=this.value;
        }
      });
      return serializeObj;
    };
  })(jQuery);
  function publishpos(){
         $.ajax({    
            type:'post', 
            dataType: "json",
            url:'Publishserv',    
            data:$("#publishinfo").serializeJson(),  
            success:function(dates){
            	var html="";
 			   if(dates.info==""){
 				   html+="<p class='text-warn'>发布成功</p>";
 				  $("#poscard").html(html);
 			   }
 				   else
 					  $("#publishwarn").html("<font color='red'>"+dates.info+"</font>");
 		   },
 		   error:function() {
 		       } 
        });    
    }
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
                <a href="#"><i class="fa fa-fw fa-user-circle"></i> 公司信息</a>
            </li>
            <li>
                <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-fw fa-gears"></i> 招聘管理 </a>
                <ul class="collapse list-unstyled" id="pageSubmenu">
                    <li><a href="#" onclick="return refresh()"><i class="fa fa-fw fa-bug"></i> 申请审核</a></li>
				<li><a href="#" onclick="return createpos()"><i class="fa fa-fw fa-bank"></i> 发布职位</a></li>
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
</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#poscard').toggleClass('fixborder');
    });
});
</script>
</body>
</html>