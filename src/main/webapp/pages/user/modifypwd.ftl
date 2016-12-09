<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection" />
		<title>左岸城堡</title>
        <link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
		<link rel="apple-touch-icon" href="custom_icon.png">	
        <#include "../common/userHeader.ftl">	
	</head>
	<body>
		<div class="page">
			<div class="tit">
				修改密码
				<a href="javascript:history.back();" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>				
			</div>
			<p class="loerror"></p>
			<div class="protips"></div>
			<form id="loginform" name="loginform"   method="post">
			<ul class="reg_frame">
				<li class="old_reg"><img src="${rc.contextPath}/pages/images/oldpwd.png"><input id="oldpwd" type="password" value="" name="oldpwd" maxlength="16" /><span>请输入原始密码</span></li>				
				<li class="pwd_reg"><img src="${rc.contextPath}/pages/images/pwd.png"><input id="password1" type="password" value="" name="pwd" maxlength="16" /><a href="javascript:void(0)" id="showpwd1">显示密码</a><span>6-16位字母和数字的新密码</span></li>
			</ul>
			<a href="javascript:void(0)" id="modifypwd"><p class="login" >修改密码</p></a>	
			<!-- <a href="${rc.contextPath}/user/toresetpwd" class="modresetpwd">忘记密码</a> -->
			</form>
		</div>	
		
		<script>
		   $(function(){
			  　$("#modifypwd").click(function(){
                    
					  /* document.loginform.submit(); */
					  var oldpwd = $('#oldpwd');
					  var pwd = $('#password1');
					  var errormsg = "";
					  if(oldpwd ==undefined || oldpwd.val()=='' ||oldpwd.val() =='请输入原始密码' )
						  {
						    //$('.loerror').text('请填写原始密码!');
							//$('.loerror').show();
							errormsg="* 请填写原始密码" ;
						    showTipMsg(errormsg);
						    return ;
						  }
					  if(pwd ==undefined || pwd.val()=='' || pwd.val() == '6-16位字母和数字的新密码' )
					  {
					   // $('.loerror').text('请填写新密码!');
						// $('.loerror').show();
						errormsg="* 请填写新密码" ;
						showTipMsg(errormsg);
					    return ;
					  }
                      var reqdata="oldpwd="+oldpwd.val()+"&pwd="+pwd.val();
					  $.ajax({
                          url:'${rc.contextPath}/user/modifypwd',
                          type:'POST',
							 data:reqdata,
							 dataType:'json',
				              success:function(data){
				                var status = data.status ;
				                if(status =='0')
				                	{
				                	  var errorMsg = "" ;
				                	  var errorCode = data.errorCode ;
				                	  if(errorCode == 0)
						                  errorMsg = "服务器未知错误";
						               else if(errorCode == 1)
						                  errorMsg = "手机号未注册";
						               else if(errorCode == 2) 
						                  errorMsg ="旧密码不正确";
						              if(errorMsg !="")
						                  showTipMsg(errorMsg);    
				                	 // alert('修改密码失败,请先登录!');
				                	//   window.location.href="${rc.contextPath}/user/tologin";
				                	 //  return ;
				                	}
				                else if(status == '1')
				                	{
				                	    // alert('修改密码成功!');
				                	    showTipMsg('修改密码成功!');
				                	    window.location.href="${rc.contextPath}/mycenter/show";
				                	   return ;
				                	}
				                
				              },
				              error:function(){
				              }
                        });
			 });
			   
		   })
		</script>	
	</body>
</html>