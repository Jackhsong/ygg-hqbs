<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>提现页面</title>
	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
	<link rel="apple-touch-icon" href="custom_icon.png">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script>
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/reward.css">
	<#include "../common/otherShare.ftl">
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/withdrawRecord.css">
</head>
<body>
     <div class="main">
        
          <div class="tx2">
                 <div class="content">
			 	 	 <h1>提现金额(元)</h1>
			 	 	 <input type="tel" placeholder="请输入提现金额" id="inp"></input>
			 	 	 <p class="ytx">可提现金额:<span id="withdrawCash">${(withdrawCash?replace('"',""))}</span>元</p>
			 	 </div>
		
		
		         
		         
		
			    
			 	 	<button class="unBtn"  id="sbtn" disabled="disabled" >申请提现</button>
			 	 	
			 	
        </div>
         
         <div class="tx1">
                 <div class="reward">
		     	 	 <img src="${rc.contextPath}/scripts/images/reward.jpg">
		     	 </div>
		        
		         
		        
		     	 <p>可提现金额(元)</p>
		     	 <h1>${(withdrawCash?replace('"',""))}</h1>
		     	
		   
						<div class="btn white" id="txBtn">提现</div>
		     	   	
		     	 
		     	 <a href="${rc.contextPath}/withdrawals/getWithdrawalsLogs"><div class="btn white" style="background:#fff;color:#000;">提现历史</div></a>
		     	 
		     	 
		     	  <a href="#">
		     	 	<div class="gz">
			     	 	
			     	 	 <div class="gzt">
			     	 	   <div style="float: left;margin-top: 0.05rem;margin-right: 0.2rem;">
			     	 	     <img src="${rc.contextPath}/scripts/images/icon.jpg">
			     	 	   </div>
			     	 	         活动规则<span>></span
			     	 	 </div>
			     	 </div>
		     	 </a>
     	 
       
         
         </div>
     	 

         
      
         
        
     	
     	 
     	 
     </div>
</div>
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
<script type="text/javascript">
 $(function(){
	
	 $("#inp").focus(function(){
	    
		  $(this).css("color","#000");
		
		  $("#inp").bind("input propertychange",function(){
			   var val = $.trim($(this).val());
			   var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			   var withdrawCash = ${(withdrawCash?replace('"',""))};//可提现的金额
			   if(val.length==0){
				   //$(".ytx").html("<span style='color:red;font-weight:normal;'>请输入大于50的金额</span>");
				   $(".ytx").html("可提现金额:<span id='withdrawCash'>${(withdrawCash?replace('"',""))}</span>元");
				   $("#sbtn").removeClass("btn").addClass("unBtn");
				   $("#sbtn").attr("disabled","diabled");
			   }else if(val>withdrawCash){
				   $(".ytx").html("<span style='color:red;font-weight:normal;'>输入金额超过可提现金额</span>");
				   $("#sbtn").removeClass("btn").addClass("unBtn");
				   $("#sbtn").attr("disabled","diabled");
			   }else if(val<50){
				   $(".ytx").html("<span style='color:red;font-weight:normal;'>提现最低50元起</span>");
				   $("#sbtn").removeClass("btn").addClass("unBtn");
				   $("#sbtn").attr("disabled","diabled");
			   }else if(!reg.test(val)){
				   $(".ytx").html("<span style='color:red;font-weight:normal;'>请输入正确金额</span>");
				   $("#sbtn").removeClass("btn").addClass("unBtn");
				   $("#sbtn").attr("disabled","diabled");
			   }else if(reg.test(val)){
				   $("#sbtn").removeClass("unBtn").addClass("btn"); 
				   $("#sbtn").removeAttr("disabled");
				   $(".ytx").html("可提现金额:<span id='withdrawCash'>${(withdrawCash?replace('"',""))}</span>元");  
				  
			   }  
		  });
		   
		  $("#sbtn").click(function(){
			  var val = $.trim($("#inp").val());
			    $.ajax({
			    	url:'${rc.contextPath}/withdrawals/getWithdrawals',
			    	type:"post",
			    	data:{cashNum:val},
			    	success:function(data){
			    		  if(data.status == 1){
	                        	location.href="${rc.contextPath}/withdrawals/getWithdrawalsLogs";
	                        	document.getElementById("inp").value="";
	                        }
			    	}
			    }); 
		   });
		   
	 });
	 
	 //提现规则内容隐藏
	  $(".gz").hide();
	 
	   $(".tx2").hide();
	   
	   $("#txBtn").click(function(){
		   $(".tx1").hide();
		   $(".tx2").show();
	   });
	  
	   
 });
</script>