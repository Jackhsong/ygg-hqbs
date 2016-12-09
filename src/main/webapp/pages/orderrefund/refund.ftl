<!-- 自助退款页面 -->
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
	<meta name="description" content="自助退款" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body class="page">

	<header class="header">
		<h1 class="title">自助退款</h1>
		<#if isApp ?? && (isApp =='1')>
		    <a href="ggj://redirect" class="header-btn app-return">返回</a>
		<#else>
		    <a href="${rc.contextPath}/orderrefund/orderRefundIndex" class="header-btn return">返回</a>
		</#if>
		
	</header>

	<section class="refund">
		<ul class="orders-list">
		<!-- 订单列表 orders-->
		<#if orderList?? && (orderList?size>0)>
		  <#list orderList as ol>
		   <li class="orders-item">
			        <!-- 订单状态 -->
					<div class="orders-status">
						<div class="status"><#if ol.status??><#if ol.status == '2'>待发货<#elseif ol.status == '3'>待收货<#elseif ol.status == '4'>交易成功<#else></#if></#if></div>
						<p class="total-price">合计：<span>${ol.totalPrice!"0.00"}</span></p>
					</div>
					<!-- 订单商品列表 -->
					<#if ol.orderDetailList?? && (ol.orderDetailList?size>0)>
					  <#list ol.orderDetailList as odl>
							<div class="orders-goods clearfix">
							 <!-- <a class="thumb" href="javascript:;"><img src="${odl.image!""}" alt=""> -->
									<#if odl.refundStatus??>
									
									 <#if odl.refundStatus =='0'>
									  <a class="thumb" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(ol.orderId)!"0"}&orderProductId=${odl.orderProductId!"0"}&productId=${odl.productId!"0"}"><img src="${odl.image!""}" alt=""></a>
									    <a class="refund-btn fr" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(ol.orderId)!"0"}&orderProductId=${odl.orderProductId!"0"}&productId=${odl.productId!"0"}">申请退款
									    </a>
									  <#elseif odl.refundStatus=='1'>
									   <a class="thumb" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}"><img src="${odl.image!""}" alt=""></a>
									    <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}">申请中
									    </a>
									  <#elseif odl.refundStatus=='2'>
									   <a class="thumb" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}&type=${odl.refundStatus!""}"><img src="${odl.image!""}" alt=""></a>
									    <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}&type=${odl.refundStatus!""}">待退货
									    </a>
									  <#elseif odl.refundStatus=='6'>
									   <!-- <a class="thumb" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}"><img src="${odl.image!""}" alt=""></a>
									     <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}">退款取消
									    </a> -->
									    <a class="thumb" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(ol.orderId)!"0"}&orderProductId=${odl.orderProductId!"0"}&productId=${odl.productId!"0"}"><img src="${odl.image!""}" alt=""></a>
									    <a class="refund-btn fr" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(ol.orderId)!"0"}&orderProductId=${odl.orderProductId!"0"}&productId=${odl.productId!"0"}">申请退款
									    </a>
									  <#else>
										  <#if odl.type =='1'>
											     <a class="thumb" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}"><img src="${odl.image!""}" alt=""></a>
											     <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}"><#if odl.refundStatus=='3'>待退款<#elseif odl.refundStatus=='4'>退款成功<#elseif odl.refundStatus=='5'>退款关闭</#if>
											     </a>
									      <#elseif odl.type =='2'>
											     <a class="thumb" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}&type=${(odl.refundStatus)!"0"}"><img src="${odl.image!""}" alt=""></a>
											     <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}&type=${(odl.refundStatus)!"0"}"><#if odl.refundStatus=='3'>待退款<#elseif odl.refundStatus=='4'>退款成功<#elseif odl.refundStatus=='5'>退款关闭</#if>
											     </a>
										 </#if>
										  
									  </#if>
									  
									 <#if odl.refundStatus =='0'>
									     <a style="color: inherit;" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(ol.orderId)!"0"}&orderProductId=${odl.orderProductId!"0"}&productId=${odl.productId}" />
									 <#elseif odl.refundStatus=='1'>
									     <a style="color: inherit;"  href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}" />
									 <#elseif odl.refundStatus=='2'>
									     <a style="color: inherit;"  href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(odl.orderProductRefundId)!"0"}&type=${odl.refundStatus!""}" />
									 </#if>
										  <div class="summary">
											<p class="title" >${odl.shortName!""}</p>
											<p class="unit-price">&yen; ${odl.salesPrice} <small>&times;${odl.count!""}</small></p>
										  </div>
									   </a>
									
									
								</#if>
							</div>
					  </#list>
					 </#if>
					<!-- 订单编号 -->
					<div class="orders-property clearfix">
						<p class="serial fl">订单编号：${ol.orderNumber!""}</p>
						<p class="time fr">${(ol.operateTime[0..18])!""}</p>
					</div>
				</li>
			  </#list>
			<#else>
				 <!-- 为空时显示此  -->
				 <div class="emptyorder">
					 <img src="${rc.contextPath}/pages/images/orderempty.png">
					 <p>您还没有相关的订单</p>
					 <p>暂时没有相关数据</p>
				</div>
			</#if>
			</ul>
	</section>
	
</body>
</html>