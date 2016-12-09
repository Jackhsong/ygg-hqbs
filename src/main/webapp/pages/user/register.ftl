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
		<link rel="apple-touch-icon" href="${rc.contextPath}/pages/custom_icon.png">	
		<#include "../common/userHeader.ftl">
	</head>
	<body>
		<div class="page">
			<div class="tit">
				注册
				<a href="${rc.contextPath}/mycenter/show" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>				
			</div>
			 <#if errorCode?exists>
			      <p class="loerror" style="display:block;">${(errorCode)?if_exists}</p>
			<#else>
			  <p class="loerror"></p>
			</#if>
			<div class="protips"></div>
			<form id="form1" name="form1" action="${rc.contextPath}/user/register" method="post">
			<ul class="reg_frame">
			    <#if accountid?exists>
				<li class="account_reg"><img src="${rc.contextPath}/pages/images/userimg.png"><input type="tel" value="${(accountid)?default("")}" name="accountid"   id="mobile" /></li>
			   <#else> 
			     <li class="account_reg"><img src="${rc.contextPath}/pages/images/userimg.png"><input type="tel" value="请输入11位手机号" name="accountid"   id="mobile" maxlength="11"/></li>
			   </#if>
				<li class="verify"><img src="${rc.contextPath}/pages/images/verifycode.png"><input type="text" value="请输入图片验证码" name="codeverify"  id="verifyCode"  maxlength="4"/><a class="verify_b" href="javascript:;" onclick="changeVerifyCode();"><img id="verifyCodeImage" alt="验证码"  src="${rc.contextPath}/verify/getCode" style="width: 80px;height:30px;padding: 0"/></a>
				<li class="verify"><img src="${rc.contextPath}/pages/images/msg.png"><input type="tel" value="请输入短信验证码" name="smsverify"  id="vfy"  maxlength="4"/><a class="verify_a" href="javascript:void(0)" >获取验证码</a>
				<a href="javascript:void(0)" class="retry off">再次发送</a>
				</li>
				<li class="pwd_reg"><img src="${rc.contextPath}/pages/images/pwd.png"><input type="password"  name="password" id="password" maxlength="16"/><a href="javascript:void(0)" id="showpwd">显示密码</a><span>6-16位字母和数字的密码</span></li>
			</ul>
			<a href="javascript:void(0)" id="userregister"><p class="login">立即注册</p></a>	
			<input type="hidden" value="1" name="type" id="type"/> 
			<input type="hidden" id="ordertype"  name="ordertype"  value="${(ordertype)!("1")}" /> <!--定单确认时需要的参数-->
			</form>
			<!-- <a href="javascript:void(0)" class="retry off"   >再次发送</a> -->
		</div>
		
		<script type="text/javascript">
		$(function(){
		      var flag = false ;
			　$("#userregister").click(function(){
			   var regflag = register() ;
			    if(regflag){
			           if(!flag){
					        flag = true ;
					        document.form1.action="${rc.contextPath}/user/register" ;
					        document.form1.submit(); 
					        return true ;
					    }else
					       alert('正在注册');
			     }
			    else{
			         return false ;
			    }
			 });
			 
		})
		
			
	</script>
	 
	<input type="hidden" id="contextPath" name="contextPath" value="${rc.contextPath}" />
	<input type="hidden" id="sendmsgtype" name="sendmsgtype" value="1" />
	</body>
</html>