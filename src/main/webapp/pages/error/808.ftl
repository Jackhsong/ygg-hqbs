<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>左岸城堡</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
   <meta content="yes" name="apple-mobile-web-app-capable"/>
   <meta content="yes" name="apple-touch-fullscreen"/>
   <meta content="telephone=no,email=no" name="format-detection"> 
   <link rel="stylesheet" type="text/css" href="${rc.contextPath}/pages/css/base.css">
   <link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
   <link rel="apple-touch-icon" href="custom_icon.png">
   <script type="text/javascript" src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script> 
   <style type="text/css">
  .main{width:100%;text-align: center;max-width:640px;margin:0 auto;position:relative;padding-bottom:1px;}
  img{width:9.3rem;display:block;margin:4.3rem auto 1.55rem;}
  .text{margin-bottom:2.9rem;text-align:center; word-spacing:.3rem; letter-spacing:.3rem;color:#33383b;}
  .text p{font-size:1.5rem;margin:0 auto .4rem;}
  .preId{margin:2rem auto;font-weight:bold;font-size:1.5rem;}
  .inputInsert{margin:0 1.5rem;height:4.5rem;border:.1rem solid #7a7a7a;-webkit-border-radius:.1rem;border-radius:.1rem;}
  .inputInsert .insert{margin:0.4rem 0 0 .5rem;width:66%;height:3.9rem;line-height:3.5rem;color:#979ba1; font-size: 1.9rem; border: none;margin-left:1.5rem;font-family:"microsoft yahei";}
  .btn{width:25%;height:4.5rem;background:#000;color:#fff;border-left-style:none;border-right-style:none;border-top-style:none;border-bottom-style:none;font-size:1.7rem;line-height:4.5rem;-webkit-border-radius:.1rem;border-radius:.1rem;}
  .login{background:url(${rc.contextPath}/pages/images/errorBg.png) no-repeat;background-size:contain;background-position:50% 50%;margin:2.8rem 1.9rem;text-align:center;color:#9ea2a7;font-size:1.4rem;}
  .loginBtn{background:#000;margin:0 1.5rem 12.1rem;color:#fff;height:4.7rem;line-height:4.7rem;font-size:1.7rem;-webkit-border-radius:.1rem;border-radius:.1rem;}
  .disabled{ background: #ccc; } 
   </style>
</head>
<body>
  <script>
             $.ajax({
                   url : "${rc.contextPath}/error/createNotTAccount",
                   type : "post",
                   dataType : "json",
                   beforeSend:function(){
                     $("#login").addClass("disabled");
                   },
                   success:function(dataX){
                          if(dataX.status==0){ 
                             alert(dataX.msg);
                             $("#login").removeClass("disabled");
                          }else if(dataX.status==1){
                            window.location.href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo";
                          }
                   },
                   error:function(){
                       alert("网络异常，请稍后再试");
                       $("#login").removeClass("disabled");
                   }
            });  
  </script>
	<!-- <div class="content" style="width:100%;text-align:center;max-width:640px;margin:0 auto;position:relative;padding-bottom:1px;">
		 <img src="${rc.contextPath}/pages/images/logo.png">
	     <div class="text">
	          <p>搜罗全球1000个品牌</p>
	          <p>精选1万款美食</p>
	          <p>100%正品, 七天退换</p>
	          <p>带你吃遍全球</p>
	     </div>

     	 <p class="preId">请填写您的推荐人ID</p>
    
	     <div class="inputInsert clearfix">
	           <input type="tel" id="txt" class="insert left t_l" placeholder="推荐人ID"></input>
	           <div class="btn right" id="btn">确定</div>
	     </div>
  
        <div class="login">您还可以</div>
        <div id="login" class="loginBtn">直接进入商城</div>
     
	</div>
	-->
   <script type="text/javascript">


   $(function(){
       $("#btn").click(function(){
           var _this = $(this);
           if(_this.hasClass('disabled')){
             return false;
           }else{
             var txt = $.trim($("#txt").val());
             var _data = {"accountId":txt};
             $.ajax({
                   url : "${rc.contextPath}/error/createAccount",
                   data:_data,
                   type : "post",
                   dataType : "json",
                   beforeSend:function(){
                     $("#btn").addClass("disabled");
                   },
                   success:function(dataX){
                          if(dataX.status==0){
                             alert(dataX.msg);
                             $("#btn").removeClass("disabled");
                          }else if(dataX.status==1){
                            window.location.href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo";
                          }
                   },
                   error:function(){
                       alert("网络异常，请稍后再试");
                       $("#btn").removeClass("disabled");
                   }
            });
           }
             
       });

       $("#login").click(function(){
           var _this = $(this);
           if(_this.hasClass('disabled')){
             return false;
           }else{
             $.ajax({
                   url : "${rc.contextPath}/error/createNotTAccount",
                   type : "post",
                   dataType : "json",
                   beforeSend:function(){
                     $("#login").addClass("disabled");
                   },
                   success:function(dataX){
                          if(dataX.status==0){ 
                             alert(dataX.msg);
                             $("#login").removeClass("disabled");
                          }else if(dataX.status==1){
                            window.location.href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo";
                          }
                   },
                   error:function(){
                       alert("网络异常，请稍后再试");
                       $("#login").removeClass("disabled");
                   }
            });
           }
             
       });
       

    });
   </script>
</body>
</html>