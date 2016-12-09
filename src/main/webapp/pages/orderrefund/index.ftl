<!-- 左岸城堡客服首页 -->
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
	<meta name="description" content="左岸城堡" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>
	<header class="header">
	
		<h1 class="title">左岸城堡客服</h1>
		<#if isApp ?? && isApp =='1'>
		    <a href="ggj://redirect/account/home" class="header-btn app-return">返回</a>
		<#else>
		    <a href="${rc.contextPath}/mycenter/show" class="header-btn return">返回</a>
		</#if>
		
	</header>

	<section class="index-box">
		<div class="index-box-title">
			<span>联系客服</span>
			<small>(早8:30－晚0:00)</small>
		</div>
		<ul class="index-box-con">
			<li class="index-box-btn">
			<#if isApp ?? && isApp =='1'>
				<a href="ggj://open/account/onlineService/meiqia">
			<#else>
				<!-- 美洽客服聊天 弃用 <a href="javascript:void(0)" onclick="mechatClick()"> -->
				<a href="javascript:void(0)" onclick="easemobIM()">
			</#if>
					<i class="icon icon-online"></i>
					<span>在线客服</span>
				</a>
			</li>

			<li class="index-box-btn">
				<a href="tel:400-160-3602">
					<i class="icon icon-phone"></i>
					<span>客服热线</span>
				</a>
			</li>
		</ul>
	</section>

	<section class="index-box">
		<div class="index-box-title"><span>售后服务</span></div>
		<ul class="index-box-con">
			<li class="index-box-btn">
				<#if isLogin ?? && isLogin =='0'>
				    <a href="ggj://alert/account/login">
				<#else>
					<a href="${rc.contextPath}/orderrefund/getOrderInfo"/>
				</#if>
					<i class="icon icon-autorefund"></i>
					<span>自助退款</span>
				</a>
			</li>

			<li class="index-box-btn">
				<#if isLogin ?? && isLogin =='0'>
				    <a href="ggj://alert/account/login">
				<#else>
					<a href="${rc.contextPath}/orderrefund/getReturnProcessInfo">
				</#if>
					<i class="icon icon-refundprosse"></i>
					<#if count?? && count!=0>
					  <i class="badge">${count}</i>
					</#if>
					<span>退款进度</span>
				</a>
			</li>
		</ul>
	</section>

	<section class="index-box">
		<div class="index-box-title"><span>左岸城堡微信</span></div>
		<ul class="index-box-con">
			<li class="index-box-list clearfix">
				<img class="avatar" src="${rc.contextPath}/pages/images/1.png" alt="">
				<p>格格私人微信号，${(weixinGege)!'yangege02'}，有任何问题都可以随时联系格格哦，另外朋友圈常常会有惊喜：）</p>
			</li>
		</ul>
	</section>
<script>
 
var easemobIM = { config: {} };
 
////必填////
 
easemobIM.config.tenantId = '3392';//企业id
 
easemobIM.config.to = 'gegejiaservices';//必填, 指定关联对应的im号
 
easemobIM.config.appKey = '0001234#gegejia';//必填, appKey
 
////非必填////
 
easemobIM.config.buttonText = '联系客服';//设置小按钮的文案
 
easemobIM.config.hide = true;//是否隐藏小的悬浮按钮
 
easemobIM.config.mobile = /mobile/i.test(navigator.userAgent);//是否做移动端适配
 
easemobIM.config.dragEnable = true;//是否允许拖拽
 
easemobIM.config.dialogWidth = '400px';//聊天窗口宽度,建议宽度不小于400px
 
easemobIM.config.dialogHeight = '500px';//聊天窗口高度,建议宽度不小于500px
 
easemobIM.config.defaultAvatar = 'static/img/avatar.png';//默认头像
 
easemobIM.config.minimum = true;//是否允许窗口最小化，如不允许则默认展开
 
easemobIM.config.visitorSatisfactionEvaluate = false;//是否允许访客主动发起满意度评价
 
easemobIM.config.soundReminder = true;//是否启用声音提醒
 
easemobIM.config.fixedButtonPosition = {x: '10px', y: '10px'};//悬浮初始位置，坐标以视口右边距和下边距为基准
 
easemobIM.config.dialogPosition = {x: '10px', y: '10px'};//窗口初始位置，坐标以视口右边距和下边距为基准
 
easemobIM.config.titleSlide = true;//是否允许收到消息的时候网页title滚动
 
easemobIM.config.error = function ( error ) { alert(error); };//错误回调
 
easemobIM.config.onReceive = function ( from, to, message ) { /*console.log('收到一条消息', arguments);*/ };//收消息回调
 
easemobIM.config.authMode = 'password';//验证方式
 
easemobIM.config.user = {
 
    //可集成自己的用户，如不集成，则使用当前的appkey创建随机访客
 
    name: '${username}',//集成时必填
 
    password: '${password}'//authMode设置为password时必填,与token二选一 
 
};
 
</script>
<script src="${rc.contextPath}/pages/huanxin/static/js/easemob.js"></script> 
</body>
</html>