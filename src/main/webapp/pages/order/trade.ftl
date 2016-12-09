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
		<#include "../common/orderHeader.ftl">
	</head>
	<body>
		<div class="page">
			<div class="tit">
				交易详情
				<!--<a href="${rc.contextPath}/mycenter/show" class="tit_la"  id="gotolistscId"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>		
				 	-->		
			</div>			
			<#if status?? && status =='1'>
			  <div class="payfail"><img src="${rc.contextPath}/pages/images/paysucc.png">交易成功</div>
			<#else>
			  <div class="payfail"><img src="${rc.contextPath}/pages/images/payfail.png">付款失败</div>
			  <div class="pnot"><img src="${rc.contextPath}/pages/images/buytime.png"><em>请在<span id="tradetime"><span class="minute"></span><span class="second"></span></span>内完成付款，时间结束后商品可能被抢完。</em></div>
			</#if>
			<!--
			  <div class="pnot"><img src="${rc.contextPath}/pages/images/buytime.png"><em>请在<span id="tradetime"><span class="minute"></span><span class="second"></span></span>内内完成付款，时间结束后商品可能被抢完。</em></div>
			-->
			<ul class="paylist">
				<li>订单编号:<span>${(orderNumber)!""}<#if hasMoreThanOneOrder?? && hasMoreThanOneOrder=='1'>等${(failOrderCount)!"几"}个订单</#if></span></li>
				<li>交易时间:<span>${(transactionTime)!""}</span></li>
				<li>支付方式:<span><#if paytype?? && paytype=='1'>银联支付<#elseif paytype?? && paytype =='2'>支付宝支付<#elseif paytype?? && paytype=='3'>微信支付<#else>无需支付</#if></span></li>
				<li>￥${(totalPrice)!"0.00"}</li>
			</ul>

			<div class="payfoot">
			    <#if status?? && status =='0'>
				  <a href="${rc.contextPath}/order/list/1">查看订单</a>
				 <#else>
				  <a href="${rc.contextPath}/order/list/2">查看订单</a>
				</#if>
				<a href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo">回首页</a>
			</div>
		</div>		
		<input type="hidden" id="orderId" name="orderId" value="${(orderId)!"0"}" />
		<script>
			$(function(){
				var etime="${(endSecond)!"0"}";
				var sid="#tradetime";
				var timer = setInterval(function(){
					etime--;
					orderCdown(etime,sid);
				},980);
				orderCdown(etime,sid);	
			})
		</script>
		<#-- 超哥勿删，用于百度订单统计 -->
		<script>
			<#if status?? && status =='1' && pushOrderList?exists >
				<#list  pushOrderList as al >
					 _hmt.push(['_trackOrder', {
                				"orderId": "${al.orderId}",
                				"orderTotal": ${al.orderTotal},
                				"item": ${al.item}
                			 }
                	]);
				</#list>
			</#if>
		</script>
			
	</body>
</html>