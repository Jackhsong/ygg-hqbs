<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>提现记录</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<meta property="wb:webmaster" content="1d1b018b9190b96c" />
	<meta property="qc:admins" content="342774075752116375" />
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script>
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/withdrawList.css">
	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
	<link rel="apple-touch-icon" href="custom_icon.png">
	<#include "../common/otherShare.ftl">
</head>
<body>
	 <div class="main">
	     
	 	  <ul class="listul">
	 	  
	 	  </ul>
	 	  
	 	   <div class="empty">
                <img src="${rc.contextPath}/scripts/images/order.png">
                <p class="emptyT">还没有提现记录</p>
           </div>
            
	 	  <div class="more">
         		    <div class="morebtn" id="morebtn">
         		                      查看更多
         		       <img src="${rc.contextPath}/scripts/images/down.png">
         		    </div>
          </div>
	 </div>
	 <script>
	     $(function(){
           

	    	$(".empty").hide();
	    	listAjax(1);
	    	$("#morebtn").click(function(){
	    		var _disabled = $(this).attr("disabled");
     	    	if(!_disabled){
     	    		moreData();
     	    		$(".more").show();
     	    		//$(".morebtn").text("没有更多数据").attr("disabled",true);
     	    	}
     	    	
	    	});
	    	
	    
	    	
	     });
	     
	     
	     function moreData(){
  			var _liSize=$(".listul li").size();
  			var _page=1;
  			if(_liSize<10){
  				$(".morebtn").text("没有更多数据").attr("disabled",true);
  			}else{
  				 _page=_liSize/10;
  				var _index=$("li.change").index();
      	    	_page++;
      	    	listAjax(_page);
  			}   
  	   }
	     
	     function listAjax(_page){
		    	 $.ajax({
		    		  url : "${rc.contextPath}/withdrawals/getListByPage",
		    		  data:{"page":_page},
		    		  type : "post",
		    		  dataType : "json",
		    		  success:function(data){
			    			var data1 = data.logList;
			    			
			    			if(data1.length < 10){
		    		        	$(".morebtn").text("没有更多数据").attr("disabled",true);
		    		        }

			    			if(data1.length > 0){
			    				var _li="";
			    				for(var i=0;i<data1.length;i++){
			    					_li+= "<li><div class='list'><div class='one'>"+
			    		 	  			 	  "<span class='left'>编号:"+data1[i].id+"</span>"+
			    		 	  			 	  "<span class='right'>"+data1[i].createTime+"</span></div>"+
		                                      "<div class='two'><span class='left'>金额:<span>"+data1[i].withdrawals+"</span></span>"+
			                                  "<span class='right status' value='"+data1[i].status+"'></span></div></div></li>";
			    				}
			    				$(".more").show();
			    				$(".listul").append(_li);
			    				  var _status = $(".listul").find(".status");
			    				  for(var j = 0 ; j <= _status.length ; j++){
			    					  var _statusVal = _status.eq(j).attr("value");
			    					  if(_statusVal == 1){
			    						  _status.eq(j).html("提现中"); 
			    					  }else if(_statusVal == 2){
			    						  _status.eq(j).html("提现成功").addClass("success"); 
			    					  }else if(_statusVal == 3){
			    						  _status.eq(j).html("提现失败"); 
			    					  }
			    				  }  
			    				 
			    				  
			    			}else if(data1.length ==0){
			    				 $(".empty").show();
			    				 $(".more").hide();
			    				
			    			   }
		    		        }
		    		 
	   	            });
		    	
	     }
	     
	  
	   
	   
	   
	</script>
</body>
</html>