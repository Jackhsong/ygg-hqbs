<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>我的二维码</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script>
	<#include "../common/otherShare.ftl">
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/qrcode.css">
	<link href="../images/favicon.ico" rel="shortcut icon">
</head>
<body>
	 <div class="main">
	   <img src="${rc.contextPath}/scripts/images/qrcode.jpg" style="width:100%;opacity:0">
	 	 <div class="bg">
	 	 	    <div class="touxiang"><img src="${image}"></div>

	 	 	    <#if status?? && status == "1">

		 	 	    <#if spokesperson?? && spokesperson == '0'>
		 	 	    	<div class="code"><img src="${rc.contextPath}/scripts/images/emptyCode.png"></div>
		 	 	    <#else>
		 	 	    	<div class="code"><img src=${qrCode?replace('"',"")}></div>
		 	 	    </#if>
	 	 	    <#else>
	 	 	    	<div class="code codeun">生成二维码异常,请稍后再试</div>
	 	 	 	</#if>
	 	 </div>
	 </div>
 	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
