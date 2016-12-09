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
		<link rel="apple-touch-icon" href="custom_icon.png">	
		<link href="${rc.contextPath}/pages/css/global.css" rel="stylesheet" type="text/css"/>	
		<link href="${rc.contextPath}/pages/css/mycenter.css" rel="stylesheet" type="text/css"/>		
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
					<img src="${rc.contextPath}/pages/images/qq.png" class="userimg">
					<p>${accountid?if_exists}</p>
				 </div>
			</#if>
			</div>
			<div class="c_order"><a href="${rc.contextPath}/order/list/0">全部订单</a></div>
			<div class="c_order_list">
				<dl>
					<a href="${rc.contextPath}/order/list/1">
						<dt><em><img src="${rc.contextPath}/pages/images/unpay.png">
						   <#if islogin?exists && islogin== 1  >
						    <#if orderstatus1?? && orderstatus1 >0 >
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
						  <#if islogin?exists && islogin== 0>
						     <p class="off">0</p>
						  <#else>
						      <p class="">${orderstatus2?default("0")}</p>
						   </#if>
						</em></dt>
						<dd>待发货</dd>
					</a>
				</dl>
				<dl>
					<a href="${rc.contextPath}/order/list/3">
						<dt><em><img src="${rc.contextPath}/pages/images/retake.png">
						
						 <#if islogin?exists && islogin== 0>
						     <p class="off">0</p>
						  <#else>
						      <p class="">${orderstatus3?default("0")}</p>
						   </#if>
						
						</em></dt>
						<dd>已发货</dd>
					</a>
				</dl>
				<dl>
					<a href="${rc.contextPath}/order/list/4">
						<dt><em><img src="${rc.contextPath}/pages/images/trade.png">
						
						<#if islogin?exists && islogin== 0>
						     <p class="off">0</p>
						  <#else>
						      <p class="">${orderstatus4?default("0")}</p>
						   </#if>
						
						</em></dt>
						<dd>交易完成</dd>
					</a>
				</dl>
			</div>
			<a href="${rc.contextPath}/ads/listads"><div class="lista">收货地址管理</div></a>
			<a href="tel:0571-86888702"><div class="listb">联系客服<span>0571-86888702（早8:30 - 晚0:00）</span></div></a>
			<a href="#"><div class="listc">关于良食</div></a>
			<a href="${rc.contextPath}/user/logout" class="logout">退出当前账号</a>
		</div>		
	</body>
</html>