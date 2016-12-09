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
		<#include "../common/orderHeader.ftl">
		<style type="text/css">
	        .coninfo_phone{color: #5d626b;font-size: 15px;padding: 10px 10px 8px 10px;margin-top:10px;border-bottom:1px solid #dbdbdb;border-top:1px solid #dbdbdb;background: #fff;}
	        .coninfo_phone i{color: #31363a;font-family: 'Arial';font-style: normal;}
	        .coninfo_phone a{text-decoration: underline;float: right;margin-right: 5px;}
	        .coninfo_phone b{float: left;background:url("${rc.contextPath}/pages/images/phone.png") no-repeat center center;background-size: 100%;width: 20px;height: 20px;margin-top: 2px;}
	        .coninfo_list div:first-child{color: #009d42;}
	        .coninfo_list p{font-size: 14px;line-height: 150%;}
    	</style>
	</head>
	<body>
		<div class="page">
			<#if logistics?? >
			<ul class="odetailc">
				<li>
					<p>${(logistics.channel)!""}</p>
					<p>运单编号：${(logistics.number)!""} 
					<#if logistics.status?? && logistics.status =='0'>
					 <p>物流状态：在途中</p>
					<#elseif logistics.status?? && logistics.status =='1'>
					  <p>物流状态：已揽收</p> 
					<#elseif logistics.status?? && logistics.status =='2'>
					  <p>物流状态：问题件</p> 
					<#elseif logistics.status?? && logistics.status =='3'>
					  <p>物流状态：已签收</p>
					<#elseif logistics.status?? && logistics.status =='4'>
					  <p>物流状态：退签</p> 
					<#elseif logistics.status?? && logistics.status =='5'>
					  <p>物流状态：同城派送中</p> 
					<#elseif logistics.status?? && logistics.status =='6'>
					  <p>物流状态：退回</p> 
					<#elseif logistics.status?? && logistics.status =='7'>
					  <p>物流状态：转单</p>  
					</#if>
				</li>
			</ul>
			</#if>
			
			<#if logistics?? && logistics.telePhone??>
			<div class="coninfo_phone clear"><b></b>物流电话：<i>${(logistics.telePhone)!""}</i><a href="tel:${logistics.telePhone}">拨打</a></div>
			</#if>
			<div class="coninfo">
				<div class="coninfo_list">物流跟踪</div>
				<ul class="coninfo_list">
					<li>
						<!-- 物流信息列表
						<div>
							<p>杭州市【西湖区4部】，谁正西湖区4部】，谁正西湖区4部】，谁正西湖区4部】，谁正西湖区4部】，谁正在派件</p>
							<p>2014-06-29 13:01:59</p>
						</div> -->
						<!-- 物流信息列表 End -->
						<#if logisticsDetailList?? && (logisticsDetailList?size >0 )  > 
						  <#list logisticsDetailList as  map > 
						    <div>
								<p>${map['content']?replace("左岸城堡","左岸城堡")}</p>
						        <p>${map['operateTime']}</p>
						    </div>
						  </#list>			
					   <#else>
					                暂无物流信息
					   </#if>
						<!--<div class="logisfoot"><p></p></div>-->
					</li>									
				</ul>
			</div>
		</div>		
	</body>
</html>