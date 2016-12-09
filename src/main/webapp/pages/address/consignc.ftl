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
				选择收货地址
				<a href="${rc.contextPath}/order/confirm/${(ordertype)!"1"}" class="tit_la"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>
				<a href="javascript:void(0)" class="reg tit_ra"  onclick="addressmgrfun()">管理</a>
			</div>
			<div class="conframe">
			<#if addressList?exists && (addressList?size>0) >
				 <#list  addressList as al >
						   <a href="${rc.contextPath}/ads/selectedads/${(al.addressId)!"0"}/${(ordertype)!"1"}" >
										<div class="list">
											<ul class="fl">
												<li class="con_tit clear"><span class="fl">${al.fullName?if_exists}</span><span class="fr">${al.mobileNumber?if_exists}</span></li>
												<li class="con_content">${al.detailAddress?if_exists}</li>
											</ul>
											<#if selectedAddressId?? && al.addressId?? && al.addressId == selectedAddressId >
											    <div class="choose fl"><img src="${rc.contextPath}/pages/images/choose.png"></div><!-- 去年勾class加上off，例class="choose fl off" -->
											</#if>
										</div>
									</a>
					      </#list>  
				 </#if>  
			</div>
		</div>
	    
	    <form id="form1" name="form1" action="${rc.contextPath}/ads/listadsmgr" method="post">
	        <input type="hidden" id="source" name="source" value="consignc" />
	        <input type="hidden" id="isBonded" name="isBonded" value="${(isBonded)!"0"}" />
	        <input type="hidden" id="ordertype" name="ordertype" value="${(ordertype)!"1"}" />
	    </form>
	    <script>
	        function  addressmgrfun()
	        {
	           document.form1.submit();
	        }
	    </script>
	</body>
</html>