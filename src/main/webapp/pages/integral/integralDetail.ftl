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
		<#include "../common/integralDetailHeader.ftl">
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body>
		<div class="page">
			<nav class="tit">
				积分明细
				<a href="javascript:history.back();" class="tit_la"  id="gotolistscId"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>	
			</nav>
			<section>
				<ul class="list">
					<li class="thead clear">
						<i class="th c01">变动原因</i>
						<i class="th c02">积分数量</i>
						<i class="th c03">积分余额</i>
					</li>
					<#if pointDetails??>
					<#list pointDetails as l>
						<li class="tr clear">
							<i class="td c01">
								<b>${l.type}</b>
								<em>${l.operateTime}</em>
							</i>
							<i class="td c02">
								<strong>${l.operatePoint}</strong>
							</i>
							<i class="td c03">
								<strong>${l.totalPoint}</strong>
							</i>
						</li>
					</#list>
            		</#if>
					<!-- <li class="tr clear">
						<i class="td c01">
							<b>购物得积分</b>
							<em>2015-05-19</em>
						</i>
						<i class="td c02">
							<strong>-1000</strong>
						</i>
						<i class="td c03">
							<strong>200</strong>
						</i>
					</li> -->
				</ul>
			</section>
		</div>
<script>
(function(){
	$(function(){
	});
}());

/*
 wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${(appid)!"wx7849b287f9c51f82"}', // 必填，公众号的唯一标识
	    timestamp: ${(timestamp)!"0"}, // 必填，生成签名的时间戳
	    nonceStr: '${(nonceStr)!"0"}', // 必填，生成签名的随机串
	    signature: '${(signature)!"0"}',// 必填，签名，见附录1
	    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	    alert(res.errMsg);
	});
*/
</script>
		
<input type="hidden" id="iswx5version" name="iswx5version" value="${(iswx5version)!"0"}" />
</body>
</html>