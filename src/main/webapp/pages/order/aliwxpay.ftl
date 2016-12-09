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
		<style type="text/css">
			body{ height: 100%}
			html{  height: 100%; background: #303030;}
		</style>
		<#include "../common/orderHeader.ftl">
	</head>
	<body>
		<div class="succ">
			<a href="${rc.contextPath}/order/pay/aliwxpayfail/${(orderId)!""}" id="sd"><img src="${rc.contextPath}/pages/images/shutdown.png" class="sd"></a>
			<img src="${rc.contextPath}/pages/images/android.png" id="androidimg" class="off">
			<img src="${rc.contextPath}/pages/images/safari.png" id="safariimg" class="off">			
			<a href="${rc.contextPath}/order/pay/aliwxpayfail/${(orderId)!""}" class="preload"></a>
		</div>
		<div>
		  <#if errorCode??>
		       <div><p class="loerror" style="display:block;">${(errorMsg)!""}</p></div>
		       <#if errorCode=='1'>
		         <a href="${rc.contextPath}/user/tologin">返回</a>
		       <#elseif errorCode=='2'>
		          <a href="${rc.contextPath}/mycenter/show">返回</a>
		       <#elseif errorCode=='3'>
		          <a href="${rc.contextPath}/mycenter/show">返回</a>
		       <#elseif errorCode=='4'>
		          <a href="${rc.contextPath}/mycenter/show">返回</a>
		       <#else>
		          <a href="javascript:history.back()">返回</a>
		       </#if>
		    <#else>
		      ${(requestUrl)!""}
		       <script>
		         
		       </script>
		    </#if>
	    </div>
		<script type="text/javascript">
			$(function(){
				var sUserAgent = navigator.userAgent.toLowerCase();
				var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";     
			    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";  	   
			    var bIsAndroid = sUserAgent.match(/android/i) == "android";
			    if( bIsIpad || bIsIphoneOs){
			    	$('#safariimg').show();
			    }
			    if( bIsAndroid){
			    	$('#androidimg').show();
			    }
			});
		</script>
	</body>
</html>