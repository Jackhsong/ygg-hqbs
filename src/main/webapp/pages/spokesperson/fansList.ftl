<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的粉丝</title>
	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
	<link rel="apple-touch-icon" href="custom_icon.png">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<meta property="wb:webmaster" content="1d1b018b9190b96c" />
	<meta property="qc:admins" content="342774075752116375" />
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script>
	<#include "../common/otherShare.ftl">
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/my.css">
</head>
<body>
	<div class="main">
	
		 <div class="search">
           	  <img src="${rc.contextPath}/scripts/images/sousuo.png">
           	  <div style="border:1px solid;height:0.84rem;"><input type="text" placeholder="输入粉丝ID" id="inp"></input></div>
           	  <div class="searchBtn" id="btn">搜索</div>
         </div>

         <div class="nav">  
         <ul>
         	 	<!-- <li class="change"><div class="num">全部粉丝<p>${count}</p></div></li> -->
         	 	<li class="change"><div class="num">我的粉丝（${oneCount}）</div></li>
         	 	<li><div class="num">粉丝圈（${twoThCount}）</div></li>

         	 </ul> 
         	  <div class="clean"></div>
         </div>

         <div class="list">
         	<ul class="listul">
         	  
         	</ul>
         	
            <div class="empty">
                <img src="${rc.contextPath}/scripts/images/fans.png">
                 <p class="emptyT">你还没有<span></span>粉丝呢</p>
            </div>
           
              
         	<div class="more">
         	
         		    <div class="morebtn" id="morebtn">
         		                     <span>查看更多</span>
         		       <img src="${rc.contextPath}/scripts/images/down.png">
         		    </div>
         	</div> 
         	
         	
         	  
         	<script>
         	   $(function(){
         		
         	    
         		
         		  $(".empty").hide();
         		 
         		  var _id = $.trim($("#inp").val());
         		  var _index=$("li.change").index();
         		 
         		  listAjax(null,"1",1,"append");
         		 
         		  
         		 $(".nav ul li").click(function(){
         			 
         			$(".listul").html("");
         			$(".empty").hide();
         			$(".morebtn").hide();
         			$(this).addClass('change').siblings().removeClass('change');
         			
         		
         			
          		    var _index=$("li.change").index();
          		   
          		   if(_index==0){
          			 listAjax(null,"1",1,"html");
          		   }else{
          			 listAjax(null,"2,3",1,"html");
          		   }
          		    
          		 });
         		 
         		 
         	    
         	     
         	     $("#btn").click(function(){
         	    	 var _index=$("li.change").index();
          			 $(".morebtn").hide();
         	    	 var _id = $.trim($("#inp").val());
         	    	 if(_id==""){
         	    		return false;
         	    	 }
         	    	 $(".nav").hide();
         	         if(_index == 0){
         	    		 listAjax(_id,"1",1,"html");
         	    	 }else{
         	    		 listAjax(_id,"2,3",1,"html");
         	    	 } 
         	    	 
         	     });
         	     
         	     
         	     
         	   });
         	   
         	   
         	 
         	   
         	   function moreData(){
         			var _liSize=$(".listul li").size();
         			 var _index=$("li.change").index();
         			var _page=1;
         	   		 if(_liSize<10){
         				$(".morebtn span").text("没有更多数据").attr("disabled",true);
         				$(".empty").hide();
         			
         			}else{
         			
         			    _page=_liSize/10;
         				var _index=$("li.change").index();
             	    	_page++;
             	    	if(_index==0){
             	    		listAjax(null,"1",_page,"append");
             	    	}else{
             	    		listAjax(null,"2,3",_page,"append");
             	    	}
             	    	
             	    	
         			}  
         			
         	   }
         	  
         	  
         	  $("#morebtn").click(function(){ 
           	    	var _disabled = $(this).attr("disabled");
           	    	if(!_disabled){
           	    		moreData();
           	    	}
           	    	
           	  });
         	  
         	   
         	   function listAjax(_nameOrId,_level,_page,_operate){
         	    	var _data = {
         	    		"level":_level,
         	    		"page":_page,
         	    		"nameOrId":_nameOrId
         	    	};
         	    	 $.ajax({
       	    		  url : "${rc.contextPath}/spokesPerson/getFansListByConditions",
       	    		  data:_data,
       	    		  type : "post",
       	    		  dataType : "json",
       	    		  success:function(dataX){
       	    			  var _status = dataX.status;
       	    			  var data1 = dataX.fansList;
       	    			    //第一页小于10条，不显示按钮
	                      if (_page == 1) {
						    if (data1.length < 10) {
						        $(".morebtn").hide();
						    }

						  }

							    if (data1.length > 0) {

							        var _li = "";
							        for (var i = 0; i < data1.length; i++) {
							            _li += "<li><div class='two'><div class='left'>" + " <img src='" + data1[i].fansImage + "'>" + "</div> <div class='left1'><p class='nick'>" + data1[i].fansNickname + "</p>" + "<p class='id'>ID:" + data1[i].fansAccountId + " <span>" + data1[i].createTime.substring(0, 19) + "关注</span></p></div></div>" + "<div class='clean'></div></li>";

							        }
							        if (_operate == "append") {
							            $(".listul").append(_li);

							        } else {
							            $(".listul").html(_li);

							        }
							        if (data1.length > 9) {
							            $(".morebtn span").text("查看更多").removeAttr("disabled");
							            $(".morebtn").show();
							        } else {

							            $(".morebtn span").text("没有更多数据").attr("disabled", true);
							            $(".main .morebtn img").hide();
							        }
							    } else {
							        var _liSize = $(".listul li").size();
							        if (_liSize == 0) {
							            $(".empty").show();
							           
							            
							            $(".morebtn").hide();

							        } else {
							            $(".empty").hide();
							            $(".morebtn").hide();
							        }
							       }
	              		  
	       	    			if(_operate == "html"){
      	    					 $(".listul").html( _li);
      	    					 var _liSize = $(".listul li").size();
      	    					 if( _liSize == 0){
      	    						 $(".empty").show();
      	    						 $(".morebtn").hide();
      	    					 }
      	    					 
      	    				 }
	       	    			  
	       	    			  
       	    		 }
       	    	  }); 
         	    	
         	    }
         	  
         	</script>
         </div>


	</div>
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
