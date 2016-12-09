<!-- 自助退款页面 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
	<!-- For the third generation iPad with high-resolution Retina Display -->
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="">
	<!-- For the iPhone 4 with high-resolution Retina Display -->
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="">
	<title>左岸城堡</title>
	<meta name="keyword" content="左岸城堡" >
	<meta name="description" content="管理账户" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>

	<header class="header">
		<h1 class="title">管理账户</h1>
		<#if isApp ?? && isApp =='1'>
		    	<a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		    	<a href="javascript:history.back();" class="header-btn return">返回</a>
		</#if>
	
	</header>
	<!-- 新增账户 -->
	<#if (isaddBank?? && isaddBank=='1') || (isaddAliPay?? && isaddAliPay=='1')>

		<#if isaddBank?? && isaddBank=='1'>
		<ul class="account-manage-list">
			<li class="account-item clearfix">
				<a class="account account-add" href="${rc.contextPath}/ac/toaddBank"><span class="type">退款银行卡</span>新增</a>
			</li>
		
			<#if isaddAliPay?? && isaddAliPay=='1'> 
				<!--
					<li class="account-item clearfix">
						<a class="account account-add" href="${rc.contextPath}/ac/toaddAli"><span class="type">退款支付宝</span>新增</a>
					</li>
				-->
			</#if>
		
		</#if> 
		</ul>
	</#if>
	<#if acesList?? && (acesList?size>0)>
		<!-- 原有账户列表 -->
		<ul class="account-manage-list">
		   <#list acesList as al >
			<li class="account-item">
		     	<a href="${rc.contextPath}/ac/tomodify?acId=${al.id?c!"0"}&type=${al.type!"1"}" >
				  <#if al.type ?? && al.type==1>
					<span class="type">退款银行卡</span>
					<p class="account">
					<#if al.bankType==1>工商银行
					<#elseif al.bankType==2>农业银行
					<#elseif al.bankType==3>中国银行
					<#elseif al.bankType==4>建设银行
					<#elseif al.bankType==5>邮政储蓄
					<#elseif al.bankType==6>交通银行
					<#elseif al.bankType==7>招商银行
					<#elseif al.bankType==8>光大银行
					<#elseif al.bankType==9>中信银行
					</#if>&nbsp;尾号${al.cardNumber!""}&nbsp;${al.cardName!""}</p>
				  <#else>
				  <!--
				    <span class="type">退款支付宝</span>
				    <p class="account">支付宝&nbsp;${al.cardNumber!""}&nbsp;${al.cardName!""}</p>
				    
				  -->
				  </#if>
			 	</a>
			</li>
		   </#list>
		</ul>
   <#else>
	    
  </#if>
</body>
</html>