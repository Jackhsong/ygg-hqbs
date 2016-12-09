<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>左岸城堡</title>
	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
	<link rel="apple-touch-icon" href="custom_icon.png">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta content="email=no" name="format-detection" />
	<meta property="wb:webmaster" content="1d1b018b9190b96c" />
	<meta property="qc:admins" content="342774075752116375" />
	<link href="${rc.contextPath}/pages/css/base.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/scripts/css/list.css">
	<script type="text/javascript" src="${rc.contextPath}/scripts/js/zepto.min.js"></script>
	<!-- <script type="text/javascript" src="${rc.contextPath}/scripts/js/h5self-adaption.js"></script> -->
	<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
	<#include "../common/otherShare.ftl">
</head>
<body>
	 <div class="main">
	     <div class="touxiang vertical-center-outer t_c" style="background:#000000;">
	        <span class="vertical-center">
	 	 	 <div class="circleImg"><img src="${image}"></div>
	 	 	  <div class="desc">
	 	 	 		<ul>
	 	 	 			<li><p>${nickName}</p></li>
	 	 	 			<li><p class="id">ID:${id?c}</p></li>


	 	 	 		</ul>
	 	 	  </div>
	 	 	</span>
	     </div>


	 	 <div class="list">
	 	 	 <ul>
	 	 	 	  <li>

	 	 	 	     	 <a href="${rc.contextPath}/spokesPerson/getQrCode">
		 	 	 	     	 <div class="erweima">
			 	 	 	  	     <p>我的二维码</p>

			 	 	 	  	        <img class="code" src="${rc.contextPath}/scripts/images/code.png" >
		                            <img class="right" src="${rc.contextPath}/scripts/images/right.png" >

			 	 	 	  	  </div>
		 	 	 	  	 </a>


	 	 	 	  </li>
	 	 	 	  <li>
	 	 	 	      <a href="${rc.contextPath}/spokesPerson/getReward">
	 	 	 	  	  <div class="tixian">
	 	 	 	  	  	   <p>历史累计奖励(元)</p>
	 	 	 	  	  	   <h1>${cumulativeReward?replace('"',"")}</h1>
	 	 	 	  	  	     <img class="right" class="right" src="${rc.contextPath}/scripts/images/right.png" >
	 	 	 	  	  	   <P class="jl">可提现金额 <span>${withdrawCash?replace('"',"")}</span>元</P>
	 	 	 	  	  </div>
	 	 	 	  	  </a>
	 	 	 	  </li>
	 	 	 	  <li>
	 	 	 	            <a href="${rc.contextPath}/spokesPerson/getFansLists">
		 	 	 	       	    <div class="my">
		 	 	 	       	          我的粉丝
		 	 	 	       	         <img class="rightIcon" class="right" src="${rc.contextPath}/scripts/images/right.png" >
		 	 	 	       	         <span class="num"> ${(fans)!}人</span>
		 	 	 	       	    </div>
	 	 	 	            </a>
	 	 	 	       	    <a href="${rc.contextPath}/spokesPerson/getFansOrderList">
	 	 	 	       	    	<div class="sale">
		 	 	 	       	         粉丝销量
		 	 	 	       	          <img class="rightIcon" class="right" src="${rc.contextPath}/scripts/images/right.png" >
		 	 	 	       	         <span class="num"> ${(fansOrderPrice?replace('"',""))}元 </span>
		 	 	 	       	    </div>
	 	 	 	       	    </a>
	 	 	 	  </li>
	 	 	 </ul>
	 	 </div>

	 	 <p class="tj">您是由【${recommendedPerson?replace('"',"")}】推荐</p>




		</div>

	 </div>
	 <!--底部导航 Start-->
	<input type="hidden" id="navFooterToPage" value="3">
	<#include "../common/navFooter.ftl">
	<!--底部导航 End-->
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
