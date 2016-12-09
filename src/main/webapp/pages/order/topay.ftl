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
	</head>
	<body>
		 <h3>
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
		 </h3>
	</body>
</html>