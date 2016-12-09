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
				登录
				<a href="${rc.contextPath}/mycenter/show" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>
				<a href="${rc.contextPath}/user/toregister/${(ordertype)!("1")}" class="reg tit_ra">快速注册</a>
			</div>
			<#if errorCode?exists>
			     <!-- <p class="loerror" style="display:block;">${(errorCode)?if_exists}</p> -->
			<#else>
			  <p class="loerror"></p>
			</#if>
			<div class="protips"></div>
			<form id="loginform" name="loginform" action="${rc.contextPath}/user/login" method="post">
			<ul class="reg_frame">
			   <#if name?exists>
				<li class="account"><img src="${rc.contextPath}/pages/images/userimg.png"/><input  type="tel" value="${(name)?default("")}" name="name"  maxlength="11" id="mobile" /></li>
			   <#else> 
			     <li class="account"><img src="${rc.contextPath}/pages/images/userimg.png"/><input type="tel" value="请输入11位手机号" name="name"  maxlength="11" id="mobile" /></li>
			   </#if>
				<li class="pwd"><img src="${rc.contextPath}/pages/images/pwd.png"/><input type="password"  name="password"  maxlength="16" id="password"/><span>6-16位字母和数字的密码</span></li>
			</ul>
			<a href="javascript:void(0)" id="userlogin"><p class="login" >立即登录</p></a>
			 <input type="hidden" id="ordertype"  name="ordertype"  value="${(ordertype)!("1")}" /> <!--定单确认时需要的参数-->
			</form>	
			<span class="fpwd fr"><a href="${rc.contextPath}/user/toresetpwd">忘记密码？</a></span>
			<div class="grid">
				<div class="grid_tab">使用合作账号登录</div>
			</div>
			<div class="cooperation">
				<dl>
					<dt></dt>
					<dd></dd>
				</dl>
				<!-- <dl>
					 <a href="${rc.contextPath}/user/toqqlogin/${(ordertype)!("1")}"><img src="${rc.contextPath}/pages/images/Connect_logo_3.png"></a>
				 </dl>
				 <dl>
					 <a href="${rc.contextPath}/user/toweibologin/${(ordertype)!("1")}"><img src="${rc.contextPath}/pages/images/loginbtn_03.png"></a>
				</dl>-->
				
				<dl>
					<a href="${rc.contextPath}/user/toqqlogin/${(ordertype)!("1")}"><dt><img src="${rc.contextPath}/pages/images/qq1.png"></dt>
					<dd>QQ</dd></a>
				</dl> 
				<dl>
					<a href="${rc.contextPath}/user/toweibologin/${(ordertype)!("1")}"><dt><img src="${rc.contextPath}/pages/images/sina.png"></dt>
					<dd>微博</dd></a>
				</dl>
				<#if isWeiXinBrower ?? && isWeiXinBrower=='0'>
					<dl>
						<a href="${rc.contextPath}/user/toalipaylogin/${(ordertype)!("1")}"><dt><img src="${rc.contextPath}/pages/images/alipaylogin.png"></dt>
						<dd>支付宝</dd></a>
					</dl>
				</#if>
				<dl>
					<dt></dt>
					<dd></dd>
				</dl>
			</div>
		</div>
		
	　<script type="text/javascript">
	　　　var flag = false ;
		$(function(){
		   <#if errorCode?exists>
		       var errormsg = "${(errorCode)?default("")}" ;
		       showTipMsg(errormsg);
			</#if>
			　$("#userlogin").click(function(){
			       var errormsg = "";
			        var mobile=$('#mobile').val();
					var pwd=$('#password').val();
					if( mobile == '' || mobile == '请输入11位手机号'){
						//$('.loerror').text('* 请输入手机号');
						//$('.loerror').show();
						errormsg="* 请输入11位手机号码" ;
						showTipMsg(errormsg);
					}else if( pwd == '' || pwd == '6-16位字母和数字的密码'){
						//$('.loerror').text('* 请输入密码');
						//$('.loerror').show();
						errormsg="* 请输入密码" ;
						showTipMsg(errormsg);
					}else{
						var reg=/(^1[3-9]\d{9}$)/;
						if(!reg.test(mobile)){
							//$('.loerror').text('请输入正确的手机号码');
							//$('.loerror').show();
							errormsg="请输入正确的手机号码" ;
						    showTipMsg(errormsg);
							return false;
						}
						//表单提交submit
						 
						document.loginform.submit();
						return true ;
						/*        
						if(!flag){
						        flag = true ;
						        document.loginform.submit();
						        return true ;
						    }else
						      alert('正在登录!');
						*/
					}
			 });
		})
	</script>	
	</body>
</html>