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
		<link rel="apple-touch-icon" href=${rc.contextPath}/pages/"custom_icon.png">	
		<#include "../common/userHeader.ftl">		
	</head>
	<body>
		<div class="page">
			<div class="tit">
				忘记密码
				<a href="${rc.contextPath}/mycenter/show" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>				
			</div>
			<#if errorCode?exists>
			      <!--<p class="loerror" style="display:block;">${(errorCode)?if_exists}</p> -->
			<#else>
			  <p class="loerror"></p>
			</#if>
			<div class="protips"></div>
			<form id="form1" name="form1"  method="post">
				<ul class="reg_frame">
				    <#if accountid?exists>
					   <li class="account_reg"><img src="${rc.contextPath}/pages/images/userimg.png"><input type="tel" value="${(accountid)?default("")}" name="accountid"  input id="mobile" /></li>
				    <#else> 
				       <li class="account_reg"><img src="${rc.contextPath}/pages/images/userimg.png"><input type="tel" value="请输入11位手机号" name="accountid"  input id="mobile" maxlength="11"/></li>
				    </#if>
					<li class="verify"><img src="${rc.contextPath}/pages/images/verifycode.png"><input type="text" value="请输入图片验证码" name="codeverify"  id="verifyCode"  maxlength="4"/><a class="verify_b" href="javascript:;" onclick="changeVerifyCode();"><img id="verifyCodeImage" alt="验证码"  src="${rc.contextPath}/verify/getCode" style="width: 80px;height:30px;padding: 0"/></a>
					<li class="verify"><img src="${rc.contextPath}/pages/images/msg.png"><input type="tel" value="请输入短信验证码" name="smsverify" id="vfy" maxlength="4"/><a class="verify_a" href="javascript:void(0)">获取验证码</a>
					<a href="javascript:void(0)" class="retry off">再次发送</a>
					</li>
					<li class="pwd_reg"><img src="${rc.contextPath}/pages/images/pwd.png"><input type="password"  name="password" id="password1" maxlength="16"/><a href="javascript:void(0)" id="showpwd1">显示密码</a><span>6-16位字母和数字的密码</span></li>
				</ul>
				<a href="javascript:void(0)" id="userresetpwd"><p class="login">修改密码</p></a>	
			   <input type="hidden" value="2" name="type" />
			</form>
			<!-- <a href="javascript:void(0)" class="retry"   >再次发送</a> -->
		</div>	
		
		<script type="text/javascript">
		　　　var  flag = false ;
			$(function(){
			   <#if errorCode?exists>
		        var errormsg = "${(errorCode)?default("")}" ;
		        showTipMsg(errormsg);
			  </#if>
			
				　$("#userresetpwd").click(function(){
				    var resetflag = resetpwd();
				    if(resetflag){
				          if(!flag){
						          flag = true ;
						          document.form1.action="${rc.contextPath}/user/resetpwd" ;
						     	  document.form1.submit(); 
					              return true ;
						     }else{
						          alert('正在操作');
						     }
				    }else{
				      return false ;
				    }
				 });
				 
				 /* 验证码 */
				 $("#getsmscode").click(function(){
				    
						sendsms("${rc.contextPath}/user/sendsms","accountid="+$("#mobile").val()+"&type=2");
				 });
				 
				 /*  验证码  */
				 $("#getsmscoderetry").click(function(){
				    sendsms("${rc.contextPath}/user/sendsms","accountid="+$("#mobile").val()+"&type=2");
				    
				 });
				 
			})
	  </script>
		
		
		 <input type="hidden" id="contextPath" name="contextPath" value="${rc.contextPath}" />
		 <input type="hidden" id="sendmsgtype" name="sendmsgtype" value="2" />
	</body>
</html>