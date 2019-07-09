<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<link href="style.css?v=2.7" rel="stylesheet">
<script type="text/javascript" src="jquery-3.4.1.min.js" ></script>
<script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="bootstrap-fileinput/js/fileinput.min.js" ></script>
<script type="text/javascript" src="bootstrap-fileinput/js/locales/zh.js" ></script>

<meta charset="UTF-8">
<title>招聘中心-统一招聘管理系统</title>

</head>
<body>
<script type="text/javascript">
var com_ID="";
var com_name="";
var pos_name="";
var type="";
var page=1;
var last=false;
var usefile=false;
function psw(){
	var html="";
	if(!(last&&page==1)){
	if(page>1){
		html+="<button class='control-label col-md-2 btn btn-outline-info' onclick='switchh()'>首页</button>";
		html+="<button class='control-label col-md-2 btn btn-outline-info' onclick='switchp()'>上一页</button>";
	}
	if(page>2)
	html+="<a href='javascript:void(0);' onclick='return switchpp()' class='text-primary col-md-1' id='firstp'>1</a>";
	if(page>1)
	html+="<a href='javascript:void(0);' onclick='return switchp()' class='text-primary col-md-1' id='secondp'>2</a>";
	html+="<a class='col-md-1' id='thirdp'>1</a>";
    html+="<button class='control-label col-md-2 btn btn-outline-info' onclick='switchl()'>下一页</button>";
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
		   html+="<h4 class='card-title companyid' style='display:none'>"+dates[i].com_ID+"</h4>";
		   html+="<h4 class='card-title positionname'>"+dates[i].name+"</h4>";
		   html+="<p class='card-title'>月薪："+dates[i].salary+"</p>";
		   html+="<p class='card-title'>工作地点："+dates[i].city+"</p>";
		   html+="<p class='card-title'>学历要求："+dates[i].academic+"</p>";
		   html+="<p class='card-title'>需求人数："+dates[i].number+"</p>";
		   html+="<p class='card-title'>类型："+dates[i].type+"</p>";
		   html+="<p class='card-title d-none'>详情："+dates[i].information+"</p>";
		   html+="<input type='file' class='file-loading d-none' id='filepath' onchange='changeInput()'><br class='d-none'>";
		   html+="<button class='btn btn-outline-info details col-md-3'>详情</button>";
		   html+="<button class='btn btn-outline-warning offset-6 col-md-3 d-none sendrequest' onclick='openinput()'>应聘</button>";
		   html+="<br><p class='card-title d-none'>评论：</p>";
		   html+="<div class='card-title d-none commit'></div>";
		   html+="<textarea class='commitinfo d-none'  value=''></textarea><br><button class='btn btn-outline-primary sendcommit col-md-4 d-none'>发表评论</button>";
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
		   usefile=false;
	   },
	   error:function() {
	       }
	  });
	  };
	  function allrefresh(){
		  type="";
		  refresh();
	  }
	  function typerefresh(typelabel){
		  type=$(typelabel).children("label").html();
		  refresh();
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
		  "<h2 class='text-primary text-center'>个人信息</h2></div>"+
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
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>电话：</label>"+
        	"<input class='form-control col-md-6' type='text' name='phone' value='"+dates.phone+"' autocomplete='off'>"+
    		"</div>"+
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>邮箱：</label>"+
        	"<input class='form-control col-md-6' type='text' name='email' value='"+dates.email+"' autocomplete='off'>"+
    		"</div>"+
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>公司：</label>"+
        	"<input class='form-control col-md-6' readonly type='text' name='com_ID' value='"+dates.com_ID+"' autocomplete='off'>"+
    		"</div>"+
        	"<div class='form-group form-inline'>"+
        	"<label class='control-label col-md-5'>职位：</label>"+
        	"<input class='form-control col-md-6' readonly type='text' name='pos_name' value='"+dates.pos_name+"' autocomplete='off'>"+
    		"</div>"+
			"</form>"+
			"<div class='form-group form-inline'>"+
            "<div class='col-md-6'><button id='setinfo' class='btn btn-outline-primary' onclick='return updateinfo()'>保存修改</button></div>"+
            "<div class='col-md-8' id='updatewarn'></div>"+
        "</div>";
		  $("#poscard").html(html);
		  $("#pageswitch").html("");
		   },
		   error:function() {
		       }
		  });
	  };
	  function belltower(){
		  var url = "Bellserv";
		  var data = {ID:"<%out.print((String)session.getAttribute("ID"));%>",type:"<%out.print((String)session.getAttribute("type"));%>"};
		  $.ajax({
		   type :"post",
		   dataType: "json",
		   url : url,
		   data : data,
		   timeout:1000,
		   success:function(dates){
			   var html="";
			   if(dates.length==0)
				   html+="<p class='text-warning'>无系统消息</p>";
    		   for(var i=0;i<dates.length;i++){
    			   html+="<div class='card mb-4 shadow col-md-4 offset-4'>";
    			   html+="<div class='card-body text-center'>";
    			   html+="<h4 class='card-title'><img src='"+dates[i].avator+"' class='rounded-circle' width='50' height='50'/><label class='applicantid'>"+dates[i].com_name+"</label></h4>";
    				html+="<p class='card-title'>"+dates[i].information+"</p>";
    			   html+="</div></div>"; 
    		   }
		  $("#poscard").html(html);
		  $("#pageswitch").html("");
		   },
		   error:function() {
		       }
		  });
	  };
	  function search(){
		  com_name="";
		  pos_name="";
		  if($("#searchtype").val()=="com")
			  com_name=$("#searchcon").val();
		  else
			  pos_name=$("#searchcon").val();
		  refresh();
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
                <a href="#" onclick="return infocard()"><i class="fa fa-fw fa-user-circle "></i> 个人信息</a>
            </li>
            <li>
                <a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-fw fa-plus"></i> 招聘市场 </a>
                <ul class="collapse list-unstyled" id="pageSubmenu">
                <li><a href="#" onclick="return allrefresh()"><i class="fa fa-fw fa-globe"></i> 综合</a></li>
                    <li><a href="#" onclick="return typerefresh(this)"><i class="fa fa-fw fa-bug"></i> <label>IT</label></a></li>
				<li><a href="#" onclick="return typerefresh(this)"><i class="fa fa-fw fa-balance-scale"></i> <label>金融</label></a></li>
				<li><a href="#" onclick="return typerefresh(this)"><i class="fa fa-fw fa-building"></i> <label>建筑</label></a></li>
				<li><a href="#" onclick="return typerefresh(this)"><i class="fa fa-fw fa-cutlery"></i> <label>服务</label></a></li>
				<li><a href="#" onclick="return typerefresh(this)"><i class="fa fa-fw fa-circle-o"></i> <label>其他</label></a></li>
                </ul>
            </li>
            <li>
                <a href="#" onclick="return belltower()"><i class="fa fa-fw fa-bell"></i> 消息</a>
            </li>
            <li>
                <a id="exit" href="javascript:void(0);" onclick="return exit()"><i class="fa fa-fw fa-power-off"></i> 退出登录</a>

            </li>
        </ul>
    </nav>
<div id="content">
    <nav class="navbar fixed-top rounded shadow-sm topbar">
        <div class="container-fluid">

            <button type="button" id="sidebarCollapse" class="btn btn-outline-info">
                <i class="fa fa-fw fa-bars"></i>
                <span></span>
            </button>
			<div class="form-group form-inline">
            <select class="form-control" id="searchtype" >
            <option value="pos">岗位名</option>
            <option value="com">公司名</option>
            </select>
				<input class="form-control" type="text" ID="searchcon" value="" autocomplete="off" placeholder="">
				<button class="btn btn-outline-info"  onclick="return search()"><i class='fa fa-fw fa-search'></i></button>
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
    	$("#filepath").fileinput({
            language: 'zh', 
            uploadUrl: 'Uploadserv', 
            showUpload: false,
            showCaption: false,
            enctype: 'multipart/form-data',
            browseClass: "btn btn-outline-primary chobutton",           
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            dropZoneTitle: "上传相关材料<br>多个文件请打包",
            maxFileCount: 1,
        })
        .on("filebatchselected", function(event, files) {
		usefile=true;
    	})
    	.on("filecleared",function(event, data, msg){
		usefile=false;
    	});
    	url="Commitserv";
    	data={"com_ID":aimcard.children().children(".companyid").html(),"pos_name":aimcard.children().children(".positionname").html()};
    	$.ajax({
 		   type :"post",
 		   dataType: "json",
 		   url : url,
 		   data : data,
 		   timeout:1000,
 		   success:function(dates){
 			  var html="";
 			   if(dates.length==0)
 				   html+="<p class='text-warning'>无任何评论</p>";
 				   else{
 			   for(var i=0;i<dates.length;i++)
 				   html+="<p class='card-title text-left'><img src='"+dates[i].avator+"' class='rounded-circle' width='30' height='30'/>"+dates[i].app_name+"："+dates[i].commit+"</p>";
 			   }
 			   $(".commit").html(html);
 		   },
 		   error:function() {
 		       }
 		  });
    });
    $("#poscard").on('click','.sendcommit',function () {
		 var aimcard=$(this).parent().parent();
		   var url = "Sendcommitserv";
			  var statusdata = {"com_ID":aimcard.children().children(".companyid").html(),"pos_name":aimcard.children().children(".positionname").html(),"commit":$(".commitinfo").val()};
			  $.ajax({
			   type :"post",
			   dataType: "json",
			   url : url,
			   data : statusdata,
			   timeout:1000,
			   success:function(dates){
				 var html="";
				   html+="<p class='text-info'>评论成功</p>";
				$("#poscard").html(html);
			   },
			   error:function() {
			       }
	 });
		  });
    $("#poscard").on('click','.sendrequest',function () {
    	var aimcard=$(this).parent().parent();
    	var url = "Applyserv";
    	var data = {"com_ID":aimcard.children().children(".companyid").html(),"pos_name":aimcard.children().children(".positionname").html(),"filepath":""};
    	 $.ajax({
    		   type :"post",
    		   dataType: "json",
    		   url : url,
    		   data : data,
    		   timeout:1000,
    		   success:function(dates){
    			   if(dates.info==""){
    				   if(usefile){
    				   $("#filepath").fileinput("upload");
    				   $("#filepath").on('fileuploaded', function (event, data,previewId, index) {
    					   var html="";
	    				   html+="<p class='text-info'>申请成功，等待审核</p>";
	    				  $("#poscard").html(html);
	    				  $('html , body').animate({scrollTop: 0},0);
    				    });
    				   }
    				   else{
    					   var html="";
	    				   html+="<p class='text-info'>申请成功，等待审核</p>";
	    				  $("#poscard").html(html);
	    				  $('html , body').animate({scrollTop: 0},0);
    				   }
    			   }
    			   else{
    				   var html="";
				   html+="<p class='text-warning'>"+dates.info+"</p>";
				  $("#poscard").html(html);
				  $('html , body').animate({scrollTop: 0},0);
    			   }
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