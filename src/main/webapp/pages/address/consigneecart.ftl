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
		<#include "../common/addressHeader.ftl">
	</head>
	<body>
		<div class="page">
			<div class="tit">
				收货地址管理
				<#if  islogin?exists && islogin=='1'>
			     <#if addressList?exists && (addressList?size>0) >
			         <a href="${rc.contextPath}/ads/listorderads/${(ordertype)!"1"}/${(isBonded)!"0"}" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>
			     <#else>
			         <a href="${rc.contextPath}/order/confirm/${(ordertype)!"1"}" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>
			     </#if>
			    </#if>
			    
				<#if (Session.yggwebapp_current_user_key.id?c)?exists>
				   <a href="javascript:void(0)" onclick="orderaddaddress()" class="reg tit_ra">新增</a>
				</#if>
			</div>
			<div class="conframe">
			<#if  islogin?exists && islogin=='1'>
			 <#if addressList?exists && (addressList?size>0) >
			 <#list  addressList as al >
					                <a href="toordermdfads/${(al.addressId)!"0"}/${(ordertype)!"1"}/${(isBonded)!"0"}">
										<div class="list">
											<ul class="fl">
												<li class="con_tit clear"><span class="fl">${al.fullName?if_exists}</span><span class="fr">${al.mobileNumber?if_exists}</span></li>
												<li class="con_content">${al.detailAddress?if_exists}</li>
											</ul>
											<div class="angle fl"><img src="${rc.contextPath}/pages/images/angle.png"></div>
										</div>
									</a>
					       </#list>  
				  <#else>                       <!-- 为空时显示此 -->
					       <div class="emptyadd">
							<img src="${rc.contextPath}/pages/images/emptyadd.png">
							<p>收货地址为空</p>
							<a href="${rc.contextPath}/ads/toaddads">新增地址</a>
						  </div>   
				  </#if>
			<#else>
			     <div class="emptyadd">
					<img src="${rc.contextPath}/pages/images/emptyadd.png">
					<p>请先登录</p>
					<a href="${rc.contextPath}/user/tologin">登录</a>
				</div>
			</#if>	
				<!-- 为空时显示此
				<div class="emptyadd">
					<img src="${rc.contextPath}/pages/images/emptyadd.png">
					<p>收货地址为空</p>
					<a href="${rc.contextPath}/ads/toaddads">新增地址</a>
				</div>
				-->
			</div>
		</div>
		<form id="form1" name="form1" action="" method="post" >
		  <input type="hidden" id="source" name="source" value="consignc" />		
		</form>
		<script>
		    function orderaddaddress()
		    {
		        document.form1.action="${rc.contextPath}/ads/toorderaddads/${(ordertype)!"1"}/${(isBonded)!"0"}" ;
		        document.form1.submit();
		    }
		</script>
	</body>
</html>