<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>粉丝订单</title>
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
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/getFansOrderByNumOrId.css">
</head>
<body>
	 <div class="main">
	 	   <div class="head">
	 	   	   <div class="left">
	 	   	       <p class="top">累计奖励(元)</p>
	 	   	   	   <p class="bottom">${(totalReward?replace('"',""))}</p>
	 	   	   </div>
	 	   	   <div class="right">
	 	   	   	   <p class="top">粉丝销量(元)</p>
	 	   	   	   <p class="bottom">${(fansOrderPrice?replace('"',""))}</p>
	 	   	   </div>
	 	   </div>
           
           <div class="search">
           	   <div class="left"><img src="${rc.contextPath}/scripts/images/sousuo.png"></div>
           	   <div class="left1"><input type="text" placeholder="输入订单号、粉丝ID"></input></div>
           	   <div class="right" id="btn">搜索</div>
           </div>

           <div class="list">
           	   <ul id="listul">
           	    
           	       
           	   	 
           	   </ul>
           	   
           	    <div class="empty">
	                 <img src="${rc.contextPath}/scripts/images/order.png">
	                <p class="emptyT">粉丝还没有腐败过</p>
	            </div>
	            
           	   <div class="more">
         		    <div class="morebtn" id="morebtn">
         		                     <span>查看更多</span>
         		     <img src="${rc.contextPath}/scripts/images/down.png">
         		    </div>
         	  </div>
         	  
           </div>
	 </div>
	 <script>
	    $(function(){
	        
	         
	    	 listAjax(1);
	    	
	    	  $(".more").show();
		    	 
	    	 $("#morebtn").click(function(){
	    		
	    		   var _disabled = $(this).attr("disabled"); 
	    			
         	    	if(!_disabled){
         	    		moreData();
         	    	} 
         	    	
	    		 
		    	});
	    	
	    	
	    	  $("#btn").click(function(){
	 	    	 
		    	  var num = $("input").val();
		    	  if(num==""){
		    	  	return false;
		    	  }
		    	  $.ajax({
		    		  url : "${rc.contextPath}/spokesPerson/getFansOrderByNumOrId",
		    		  data:{"num":num},
		    		  type : "post",
		    		  dataType : "json",
		    		  success:function(dataX){
		    			  var data1 = dataX.fansOrderList;
		    			  if(data1.length > 0){
		    			  	$(".empty").hide();
		    				  var _li = "" ;
		    				  for(var i = 0 ; i < data1.length ; i++){
		    					 _li += "<a href='${rc.contextPath}/spokesPerson/getFansOrderDetail?id="+data1[i].id+"'><li><div class='one'><div class='left'>订单编号:"+data1[i].number+""+
		    					 "</div><div class='right'>"+data1[i].createTime.substring(0, 19)+"</div></div>"+
		              	   	   	   "<div class='two'><div class='left'><img src="+data1[i].fansImage+">"+
		              	   	   	   	"</div><div class='left1'><h3>"+data1[i].fansNickname+"</h3>"+
		              	   	   	   	 "<p>ID:"+data1[i].fansAccountId+"</p>"+
		              	   	   	   	   "</div><div class='right'><p class='status' value="+data1[i].status+"></p>"+
		              	   	   	   	   "<h3>￥"+data1[i].withdrawCash+"</h3></div></div></li></a>";
		              	   	                 	   	   
		    				  }
		    				  $("#listul").html(_li);
		    				  var _status = $("#listul").find(".status");
		    				  for(var j = 0 ; j <= _status.length ; j++){
		    					  var _statusVal = _status.eq(j).attr("value");
		    					  if(_statusVal == 1){
		    						  _status.eq(j).html("已付款"); 
		    					  }else if(_statusVal == 2){
		    						  _status.eq(j).html("已发货");
		    					  }else if(_statusVal == 3){
		    						  _status.eq(j).html("已收货"); 
		    					  }else if(_statusVal == 4){
		    						  _status.eq(j).html("可提现").css("color","#02b200"); 
		    					  }
		    				  }
		    			  }else if(data1.length == 0){
		    				  $("#listul").html("");
		    				  $(".more").hide();
		    				  $(".empty").show();
		    				  $(".search").hide();
		    				  
		    			  }
		    		  }
		    	  }); 
		    	  
		    	  
		    	  var _liSize=$(".listul li").size();
			    	
			    	if(_liSize == 0){
			    	   $("#listul").html("");
			    		$(".empty").show();
			    		$(".more").hide();
			    		$(".search").hide();
			    	}
		      });
	    	  
	    	  
	    });
	    
	    function listAjax(_page){
	    	$.ajax({
	    		 url : "${rc.contextPath}/spokesPerson/getFansOrderListByCondition",
  	    		 data:{"page":_page},
  	    		 type : "post",
  	    		 dataType : "json",
  	    		 success:function(dataX){
  	    			     console.log(dataX);
  	    		         var data1 = dataX.fansOrderList;
  	    		         //第一页小于10条，不显示按钮
  	    		         if(_page==1){
 	    			    	if(data1.length<10){
 	    			    		$(".more").hide();
 	    			    	}
	    			     } 
  	    		         
  	    		         if(data1.length > 0)
		    			  {
  	    			
		    				  var _li = "" ;
		    				  for(var i = 0 ; i < data1.length ; i++){
		    					 _li += "<a href='${rc.contextPath}/spokesPerson/getFansOrderDetail?id="+ data1[i].id+"'><li><div class='one'><div class='left'>订单编号:"+data1[i].number+"</div>"+
		    					       "<div class='right'>"+data1[i].createTime.substring(0, 19)+"</div></div><div class='two'>"+
		           	   	   	   	    "<div class='left'><img src='"+data1[i].fansImage+"'>"+
		           	   	   	   	    "</div><div class='left1'><h3>"+data1[i].fansNickname+"</h3>"+
		           	   	   	   	    "<p>ID:"+data1[i].fansAccountId+"</p></div><div class='right'><p class='status' value='"+data1[i].status+"'></p>"+
		           	   	   	   	     "<h3>￥"+data1[i].withdrawCash+"</h3></div></div></li></a>";        	   	   
		    				  }
		    				  $("#listul").append(_li);
		    				  var _status =$("#listul li").find(".status");
		    				  for(var j = 0 ; j <= _status.length ; j++){
		    					  var _statusVal = _status.eq(j).attr("value");
		    					  if(_statusVal == 1){
		    						  _status.eq(j).html("已付款"); 
		    					  }else if(_statusVal == 2){
		    						  _status.eq(j).html("已发货");
		    					  }else if(_statusVal == 3){
		    						  _status.eq(j).html("已收货"); 
		    					  }else if(_statusVal == 4){
		    						  _status.eq(j).html("可提现"); 
		    						  _status.eq(j).parent().find("h3").css("color","#02b200");
		    					  }
		    				  }  
		    				
		    		   }else if(data1.length==0){
		    			      $("#listul").html("");
		    				  $(".more").hide();
		    				  $(".empty").show();
		    				  $(".search").hide();
		    		   }
  	    		     
  	    		 }
	    	});
	    }
	    
	    function moreData(){
	     
  			var _liSize=$("#listul li").size();
  			var _page=1;
  			if(_liSize<10){
  				
  				$(".morebtn span").text("没有更多数据").attr("disabled",true);
  				$(".main .morebtn img").hide();
  			}else{
  				 _page=_liSize/10;
  				var _index=$("li.change").index();
      	    	_page++;
      	    	listAjax(_page);
  			}  
  	   }
	    
	    
	    
	 </script>
 	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>