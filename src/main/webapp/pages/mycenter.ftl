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
		<#include "./common/mycenterHeader.ftl">	
	</head>
	<body>
		<div class="page">
			<div class="tit">
				个人中心
				<a href="${rc.contextPath}/index" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>				
			</div>
			<div class="mycenter">
		 	<#if islogin?exists && islogin== 0 >
			     <img src="${rc.contextPath}/pages/images/mycenter.jpg" class="mycenter_img">
				 	<a href="${rc.contextPath}/user/tologin" class="c_login">登录</a>
				 <a href="${rc.contextPath}/user/toregister/1" class="c_register">注册</a>
			<#else>
			     <img src="${rc.contextPath}/pages/images/mycenter.jpg" class="mycenter_img">
			     <div class="userinfo">
				   <#if userimage??  >
				     <img src='${userimage!""}' class="userimg">
				   <#else>
				     <img src="${rc.contextPath}/pages/images/qq.png" class="userimg">${userimage!""}
				   </#if>
					<p>${accountid?if_exists}</p>
				 </div>
			</#if>
			</div>
			<div class="c_order"><a href="${rc.contextPath}/order/list/0"><img src="${rc.contextPath}/pages/images/myorder.png" />全部订单</a></div>
			<div class="c_order_list">
				<dl>
					<a href="${rc.contextPath}/order/list/1">
						<dt><em><img src="${rc.contextPath}/pages/images/unpay.png">
						   <#if islogin?exists && islogin== 1  >
						    <#if orderstatus1??  >
						      <p class="">${orderstatus1?default("0")}  </p>
						    </#if>
						   </#if>
						  </em></dt>
						<dd>待付款</dd>
					</a>
				</dl>

				<dl>
					<a href="${rc.contextPath}/order/list/2">
						<dt><em><img src="${rc.contextPath}/pages/images/redeliver.png">
						  <#if islogin?exists && islogin== 1>
						     <#if orderstatus2??  >
						       <p class="">${orderstatus2?default("0")}</p>
						     </#if>
						   </#if>
						</em></dt>
						<dd>待发货</dd>
					</a>
				</dl>
				<dl>
					<a href="${rc.contextPath}/order/list/3">
						<dt><em><img src="${rc.contextPath}/pages/images/retake.png">
						 <#if islogin?exists && islogin== 1>
						    <#if orderstatus3??  >
						       <p class="">${orderstatus3?default("0")}</p>
						    </#if>
						 </#if>
						</em></dt>
						<dd>已发货</dd>
					</a>
				</dl>
				<dl>
					<a href="${rc.contextPath}/order/list/4">
						<dt><em><img src="${rc.contextPath}/pages/images/trade.png">
						 <!--
						   <#if islogin?exists && islogin== 1>
						     <#if orderstatus4?? >
						       <p class="">${orderstatus4?default("0")}</p>
						     </#if> 
						 </#if> 
						 -->
						</em></dt>
						<dd>交易成功</dd>
					</a>
				</dl>
			</div>
			
			<a href="${rc.contextPath}/coupon/myCoupon"><div class="lista"><img src="${rc.contextPath}/pages/images/mycoupon.png"/>我的优惠券</div></a>
			<a href="${rc.contextPath}/integral/myIntegral"><div class="lista1"><img src="${rc.contextPath}/pages/images/myintegral.png"/>我的积分</div></a>
			<a href="${rc.contextPath}/ads/listads"><div class="lista"><img src="${rc.contextPath}/pages/images/profile.png"/>收货地址管理</div></a>
			<#if Session.yggwebapp_current_user_key?? && Session.userimage?? >
			    
			<#elseif Session.yggwebapp_current_user_key??  >
			   <a href="${rc.contextPath}/user/tomodifypwd"><div class="lista1"><img src="${rc.contextPath}/pages/images/mdfpwd.png" />修改密码</div></a>
			</#if>
			<a href="${rc.contextPath}/orderrefund/orderRefundIndex"><div class="listb"><img src="${rc.contextPath}/pages/images/contact.png" />联系左岸城堡</span></div></a>
			<a href="http://static.gegejia.com/custom/about_gegejia.html"><div class="listc"><img src="${rc.contextPath}/pages/images/about.png" />关于左岸城堡</div></a>
			<#if Session.yggwebapp_current_user_key?? >
			<a href="${rc.contextPath}/user/logout" class="logout">退出当前账号</a>
			</#if>
		</div>		
	</body>
</html>