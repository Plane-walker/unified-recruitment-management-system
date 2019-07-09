<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<link href="font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="style.css?v=1.2" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script type="text/javascript" src="bootstrap-fileinput/js/fileinput.min.js" ></script>
<script type="text/javascript" src="bootstrap-fileinput/js/locales/zh.js" ></script>
<script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js" ></script>

<meta charset="UTF-8">
<title>招聘中心-统一招聘管理系统</title>

</head>
<body>
<script type="text/javascript">
var com_ID="<%out.print((String)session.getAttribute("ID"));%>";
var app_ID="";
var com_name="";
var pos_name="";
var  type="";
var page=1;
var last=false;
function psw(){
	var html="";
	if(!(last&&page==1)){
	if(page>1){
		html+="<button class='control-label col-md-2 btn btn-info' onclick='switchh()'>首页</button>";
		html+="<button class='control-label col-md-2 btn btn-info' onclick='switchp()'>上一页</button>";
	}
	if(page>2)
	html+="<a href='javascript:void(0);' onclick='return switchpp()' class='text-primary col-md-1 text-center' id='firstp'>1</a>";
	if(page>1)
	html+="<a href='javascript:void(0);' onclick='return switchp()' class='text-primary col-md-1 text-center' id='secondp'>2</a>";
	html+="<a class='col-md-1' id='thirdp'>1</a>";
    html+="<button class='control-label col-md-2 btn btn-info' onclick='switchl()'>下一页</button>";
    $("#pageswitch").html(html);
    $("#thirdp").html(page);
    if(page>1)
    $("#secondp").html(page-1);
    if(page>2)
    $("#firstp").html(page-2);
    }
	else
		$("#pageswitch").html("");
}
function switchh(){
	page=1;
	refresh();
	psw();
	$('html , body').animate({scrollTop: 0},0);
}
function switchl(){
	if(!last){
	page++;
	refresh();
	psw();
	$('html , body').animate({scrollTop: 0},0);
	}
}
function switchp(){
	page--;
	refresh();
	psw();
	$('html , body').animate({scrollTop: 0},0);
}
function switchpp(){
	page-=2;
	refresh();
	psw();
	$('html , body').animate({scrollTop: 0},0);
}
function jump(){
	page=$("#paget").val();
	refresh();
	psw();
	$('html , body').animate({scrollTop: 0},0);
}
function refresh(){
	  var url = "Refreshserv";
	  var data = {"com_ID":com_ID,"com_name":com_name,"pos_name":pos_name,"size":"9","page":page,"type":type};
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
			   html+="<p class='text-warning'>无招聘信息</p>";
			   else{
		   for(var i=0;i<dates.length;i++){
			   if(i%3==0)
			   html+="<div class='card-deck mb-3 text-center'>";
		   html+="<div class='card mb-4 shadow text-center'><div class='card-header'>";
		   html+="<h4 class='my-0 font-weight-normal'><img src='"+dates[i].avator+"' class='rounded-circle' width='50' height='50'/>"+dates[i].com_name+"</h4></div>";
		   html+="<div class='card-body'>";
		   html+="<h4 class='card-title positionname'>"+dates[i].name+"</h4>";
		   html+="<p class='card-title'>月薪："+dates[i].salary+"</p>";
		   html+="<p class='card-title'>工作地点："+dates[i].city+"</p>";
		   html+="<p class='card-title'>学历要求："+dates[i].academic+"</p>";
		   html+="<p class='card-title'>需求人数：<label class='neednumber'>"+dates[i].number+"</label></p>";
		   html+="<p class='card-title'>类型："+dates[i].type+"</p>";
		   html+="<p class='card-title d-none'>详情："+dates[i].information+"</p>";
		   html+="<div class='d-none appinfo'></div>";
		   html+="<button class='btn btn-primary details'>处理</button>";
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
	  function employee(){
		  var url="Employeeserv";
		  var data={"ID":app_ID,"size":"9","page":page};
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
					   html+="<p class='text-warning'>无员工信息</p>";
					   else{
				   for(var i=0;i<dates.length;i++){
					   if(i%3==0)
						   html+="<div class='card-deck mb-3 text-center'>";
					   html+="<div class='card mb-4 shadow text-center'><div class='card-header'>";
					   html+="<h4 class='my-0 font-weight-normal'><img src='"+dates[i].avator+"' class='rounded-circle' width='50' height='50'/><label class='applicantID'>"+dates[i].ID+"</label></h4></div>";
					   html+="<div class='card-body'>";

					   html+="<p class='card-title'>昵称："+dates[i].name+"</p>";
					   html+="<p class='card-title'>性别："+dates[i].gender+"</p>";
					   html+="<p class='card-title'>国籍："+dates[i].country+"</p>";
					   html+="<p class='card-title'>联系电话："+dates[i].phone+"</p>";
					   html+="<p class='card-title'>邮箱："+dates[i].email+"</p>";
					   html+="<p class='card-title'>职位："+dates[i].pos_name+"</p>";
					   html+="<button class='btn btn-primary disemploy'>解聘</button>";
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
	  function search(){
		  com_name="";
		  pos_name="";
		  if($("#searchtype").val()=="app"){
			  app_ID=$("#searchcon").val();
			  employee();
		  }
		  else{
			  pos_name=$("#searchcon").val();
		  refresh();
		  }
	  }
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
		  $("#pageswitch").html("");
	  }
	  function infocard(){
		  var url = "Accinfoserv";
		  var data = {ID:"<%out.print((String)session.getAttribute("ID"));%>",type:"<%out.print((String)session.getAttribute("type"));%>"};
		  $.ajax({
		   type :"post",
		   dataType: "json",
		   url : url,
		   data : data,
		   timeout:1000,
		   success:function(dates){
		  var html="";
		  html+="<div class='card mb-4 shadow col-md-4 offset-4'>"+
		  "<div class='card-header'>"+
		  "<h2 class='text-primary text-center'>公司信息</h2></div>"+
		  "<div class='card-body'>"+
		  "<div class='form-group form-inline'>"+
          "<label class='control-label col-md-5'>头像：</label><input type='file' class='file-loading d-none' id='filepath' ><br class='d-none'>"+
          "<img src='"+dates.avator+"' class='rounded-circle' width='50' height='50'/><a class='updateavator' href='javascript:void(0);'>重新上传</a>"+
      "</div>"+
			  "<form action='' id='updateinformation'>"+
			  "<div class='form-group form-inline'>"+
	            "<label class='control-label col-md-5'>用户名：</label>"+
	            "<input class='form-control col-md-6' readonly type='text' name='ID' value='"+dates.ID+"' autocomplete='off'>"+
	        "</div>"+
	        "<div class='form-group form-inline'>"+
            "<label class='control-label col-md-5'>昵称：</label>"+
            "<input class='form-control col-md-6' type='text' name='name' value='"+dates.name+"' autocomplete='off'>"+
        	"</div>"+
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>国家：</label>"+
        	"<input class='form-control col-md-6' readonly type='text' name='country' value='"+dates.country+"' autocomplete='off'>"+
    		"</div>"+
			"</form>"+
			"<div class='form-group form-inline'>"+
            "<div class='col-md-6'><button id='setinfo' class='btn btn-primary' onclick='return updateinfo()'>保存修改</button></div>"+
            "<div class='col-md-8' id='updatewarn'></div>"+
        "</div>";
		  $("#poscard").html(html);
		  $("#pageswitch").html("");
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
 				   html+="<p class='text-info'>发布成功</p>";
 				  $("#poscard").html(html);
 				 $('html , body').animate({scrollTop: 0},0);
 			   }
 				   else
 					  $("#publishwarn").html("<font color='red'>"+dates.info+"</font>");
 		   },
 		   error:function() {
 		       } 
        });    
    }
  function updateinfo(){
      $.ajax({    
         type:'post', 
         dataType: "json",
         url:'Updateinfoserv',    
         data:$("#updateinformation").serializeJson(),  
         success:function(dates){
         	var html="";
			   if(dates.info==""){
				   window.location.reload();
			   }
				   else
					  $("#updatewarn").html("<font color='red'>"+dates.info+"</font>");
		   },
		   error:function() {
		       } 
     });    
 }
  function download(){
	  
	  var url = $("#downloadpath").html();
	  windows.open(url)   
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
                <a href="#" onclick="return infocard()"><i class="fa fa-fw fa-user-circle"></i> 公司信息</a>
            </li>
            <li>
                <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-fw fa-gears"></i> 招聘管理 </a>
                <ul class="collapse list-unstyled" id="pageSubmenu">
                    <li><a href="#" onclick="return refresh()"><i class="fa fa-fw fa-folder-open"></i> 申请审核</a></li>
				<li><a href="#" onclick="return createpos()"><i class="fa fa-fw fa-upload"></i> 发布职位</a></li>
                </ul>
            </li>
            <li>
                <a href="#" onclick="return employee()"><i class="fa fa-fw fa-wrench"></i> 员工管理</a>
            </li>
            <li>
                <a id="exit" href="javascript:void(0);" onclick="return exit()"><i class="fa fa-fw fa-power-off"></i> 退出登录</a>

            </li>
        </ul>
    </nav>
<div id="content">
    <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light p-3 mb-5 topbar bg-white rounded shadow-sm">
        <div class="container-fluid">

            <button type="button" id="sidebarCollapse" class="btn btn-info">
                <i class="fa fa-fw fa-bars"></i>
                <span></span>
            </button>
			<div class="form-group form-inline">
            <select class="form-control" id="searchtype" >
            <option value="pos">岗位名</option>
            <option value="com">公司名</option>
            </select>
				<input class="form-control" type="text" ID="searchcon" value="" autocomplete="off" placeholder="">
				<button class="btn btn-info"  onclick="return search()"><i class='fa fa-fw fa-search'></i></button>
			</div>
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
    	$("#pageswitch").html("");
    	$('html , body').animate({scrollTop: 0},0);
    	var url = "Applyinfoserv";
    	  var data = {"pos_name":aimcard.children().children(".positionname").html()};
    	  $.ajax({
    	   type :"post",
    	   dataType: "json",
    	   url : url,
    	   data : data,
    	   timeout:1000,
    	   success:function(dates){
    		   var html="";
    		   for(var i=0;i<dates.length;i++){
    			   if(dates[i].status!="refuse"){
    			   html+="<div class='card mb-4 shadow text-center'>";
    			   html+="<div class='card-body'>";
    			   html+="<h4 class='card-title'><img src='"+dates[i].avator+"' class='rounded-circle' width='50' height='50'/><label class='applicantid'>"+dates[i].ID+"</label></h4>";
    			   html+="<p class='card-title'>昵称："+dates[i].name+"</p>";
    			   html+="<p class='card-title'>性别："+dates[i].gender+"</p>";
    			   html+="<p class='card-title'>国籍："+dates[i].country+"</p>";
    			   html+="<p class='card-title'>联系电话："+dates[i].phone+"</p>";
    			   html+="<p class='card-title'>邮箱："+dates[i].email+"</p>";
    			   if(dates[i].filepath!=""){
    				   html+="<form action='Downloadserv' method='post'>";
    				   html+="<input type='text' class='d-none' name='filepath' value='"+dates[i].filepath+"'>";
    			   html+="<input class='btn btn-link' type='submit' value='下载材料'></form><br>";
    			   }
    			   else
    				   html+="<p class='card-title'>未提供材料</p>";
    				   if(dates[i].status!="meeting"){
    			   html+="<button class='btn btn-info meeting'>安排面试</button><div class='d-none meetingarr'><br>"+
    	    		"<textarea class='meetinginfo'  value=''></textarea><br><button class='btn btn-primary sendmeeting'>发送</button></div><br><br>";
    				   }
    				   else{
    					   html+="<button class='btn btn-info disabled'>已安排面试</button><br><p class='card-title'>详细信息："+dates[i].information+"</p><br><br>";
    				   }
    			   html+="<button class='btn btn-warning finaldec col-md-3'>拒绝</button>";
    			   html+="<button class='btn btn-warning finaldec col-md-3 offset-6'>接受</button>";
    			   html+="</div></div>";
    			   }
    			   
    		   }
    		   aimcard.children().children(".appinfo").html(html);
    		   $("#poscard").on('click','.finaldec',function () {
    			   var thiscard=$(this).parent().parent();
    			   var app_ID=$(this).parent().children().children("label").html();
    			   var dec=$(this).html();
    			   var url = "Statusserv";
    				  var statusdata = {"pos_name":data.pos_name,"new_status":dec,"app_ID":app_ID};
    				  $.ajax({
    				   type :"post",
    				   dataType: "json",
    				   url : url,
    				   data : statusdata,
    				   timeout:1000,
    				   success:function(dates){
    					   thiscard.html("");
    					   thiscard.addClass("d-none");
    					   if(statusdata.new_status=="接受"){
    						   var num=Number(aimcard.parent().find(".neednumber").html());
    						   if(num>1)
    							   aimcard.parent().find(".neednumber").html(num-1);
    						   else
    							   refresh();
    					   }
    				   },
    				   error:function() {
    				       }
    			  });
    		   });
    		   $("#poscard").on('click','.meeting',function () {
    			   $(".meetingarr").toggleClass("d-none");
    		   });
    		   $("#poscard").on('click','.sendmeeting',function () {
    			   var url = "Statusserv";
    				  var statusdata = {"pos_name":$(".positionname").html(),"new_status":"meeting","app_ID":$(".applicantid").html(),information:$(".meetinginfo").val()};
    				  $.ajax({
    				   type :"post",
    				   dataType: "json",
    				   url : url,
    				   data : statusdata,
    				   timeout:1000,
    				   success:function(dates){
    					   $(".meeting").html("已安排面试");
    					   var html="<br><p class='card-title'>详细信息："+$(".meetinginfo").val()+"</p>";
    					   $(".meetingarr").html(html);
    					   $(".meeting").addClass("disabled");
    					   $(".meeting").removeClass("meeting");
    				   },
    				   error:function() {
    				       }
    			  });
    		   });
    	   },
    	   error:function() {
    	       }
    	  });
    });
    $("#poscard").on('click','.disemploy',function () {
    	var aimcard=$(this).parent().parent();
    	var url = "Disemployserv";
    	  var data = {"app_ID":aimcard.children().children().children(".applicantID").html()};
    	  $.ajax({
    	   type :"post",
    	   dataType: "json",
    	   url : url,
    	   data : data,
    	   timeout:1000,
    	   success:function(dates){
    		   employee();
    	   },
    	   error:function() {
    	       }
    	  });
    });
    $("#poscard").on('click','.updateavator',function () {
    	$("#filepath").fileinput({
            language: 'zh', 
            uploadUrl: 'Uploadavatorserv', 
            allowedFileExtensions: ['jpg', 'gif', 'png'],
            showUpload: false,
            showCaption: false,
            showPreview: false,
            showRemove: false,
            showUpload: false,
            showCancel: false,
            showClose: false,
            showUploadedThumbs: false,
            browseOnZoneClick: false,
            enctype: 'multipart/form-data',
            browseClass: "d-none",           
            maxFileCount: 1,
        })
        .on("filebatchselected", function(event, files) {
        	$("#filepath").fileinput("upload");
        	$("#filepath").on('fileuploaded', function (event, data,previewId, index) {
        		window.location.reload();
    		    });
    	})
    	$("#filepath").click();
    });
});

</script>
</body>
</html>