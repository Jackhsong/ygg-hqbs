<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>粉丝订单明细</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<meta property="wb:webmaster" content="1d1b018b9190b96c" />
	<meta property="qc:admins" content="342774075752116375" />
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script>
	<#include "../common/otherShare.ftl">
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/orderDetail.css">
	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
    <link rel="apple-touch-icon" href="custom_icon.png">
</head>
<body>
	<div class="main">
	     
	    <div class="head">
			<h1 class="left">累计奖励</h1>
			<#if orderDetail.status==1>
			<h1 class="right1">￥${orderDetail.withdrawCash}</h1>
			</#if>
			<#if orderDetail.status==2>
			<h1 class="right1">￥${orderDetail.withdrawCash}</h1>
			</#if>
			<#if orderDetail.status==3>
			<h1 class="right1">￥${orderDetail.withdrawCash}</h1>
			</#if>
			<#if orderDetail.status==4>
			<h1 class="right2">￥${orderDetail.withdrawCash}</h1>
			</#if>
			
		</div>
		<div class="info">
		    <div>
		       <span class="left">订单号</span>
			   <span class="right">${orderDetail.number}</span>
			</div>
			<div class="clear"></div>

			 <div>
		       <span class="left">订单状态</span>
		       <#if orderDetail.status==1>
			   <span class="right">已付款</span>
			   </#if>
			    <#if orderDetail.status==2>
			   <span class="right">已发货</span>
			   </#if>
			    <#if orderDetail.status==3>
			   <span class="right">已收货</span>
			   </#if>
			    <#if orderDetail.status==4>
			   <span class="right">可提现</span>
			   </#if>
			</div>
			<div class="clear"></div>

			 <div>
		       <span class="left">下单时间</span>
			   <span class="right">${orderDetail.createTime[0..18]}</span>
			</div>
			<div class="clear"></div>
            
            <!-- <div>
		       <span class="left">收获时间</span>
			   <span class="right">未收货</span>
			</div>
			<div class="clear"></div> -->

			 <div>
		       <span class="left">订单金额</span>
			   <span class="right">￥${orderDetail.realPrice}</span>
			</div>
			<div class="clear"></div>

			<div>
		       <span class="left">粉丝昵称</span>
			   <span class="right">${orderDetail.fansNickname}</span>
			</div>
			<div class="clear"></div>

			<div>
		       <span class="left">粉丝ID</span>
			  <span class="right">${orderDetail.fansAccountId?c}</span>
			</div>
			<div class="clear"></div>
			<!-- <div>
		       <span class="left">粉丝级别</span>
		       <#if orderDetail.level==1>
			   <span class="right">直接粉丝</span>
			   </#if>
			   <#if orderDetail.level==2>
			   <span class="right">间接粉丝</span>
			   </#if>
			   <#if orderDetail.level==3>
			   <span class="right">间接粉丝</span>
			   </#if>
			</div> -->
			<div class="clear"></div>
		</div>     
		
	</div>
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>