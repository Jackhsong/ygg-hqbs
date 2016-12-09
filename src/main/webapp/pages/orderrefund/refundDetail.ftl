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
	<meta name="description" content="退款详情" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>
	<header class="header">
		<h1 class="title">退款详情</h1>
          
        <#if isApp_isAccountIdEmpty ?? && isApp_isAccountIdEmpty =='1'>
		   	<a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		    <#if isApp ?? && isApp =='1'>
		        <a href="ggj://redirect" class="header-btn app-return">返回</a>
		   <#else>
		     	<a href="javascript:history.back();" class="header-btn return">返回</a>
		   </#if>
		</#if>
		
		  
		
		
	
	</header>
	<#if refundProductInfo ??>
	    <#if refundProductInfo.status==1>
		    <section class="refund-tips"><div id="clockbox1">已收到您的申请，左岸城堡将在<span class="time"><span class="hour"></span><span class="minute"></span><span class="second"></span></span>内为您处理。</div></section>
		    <script type="text/javascript">
					$(function(){
						var stime="${endSecond!"0"}" ;
						var sid="#clockbox1";
						var timer = setInterval(function(){
							stime--;
							countDown(stime,sid);
						},980);
						countDown(stime,sid);
					})
				</script>
		 <#elseif refundProductInfo.status==3>
		     <section class="refund-tips"><div id="clockbox2">退款申请已通过，左岸城堡将在<span class="time"><span class="hour"></span><span class="minute"></span><span class="second"></span></span>内将款项打入您的账户。</div></section>
		     <script type="text/javascript">
					$(function(){
						var stime="${endSecond!"0"}" ;
						var sid="#clockbox2";
						var timer = setInterval(function(){
							stime--;
							countDown(stime,sid);
						},980);
						countDown(stime,sid);
					})
			 </script>
		 <#elseif refundProductInfo.status==4>
		     <section class="refund-tips done">退款成功，款项已打入您的账户，如有疑问，请随时与左岸城堡客服联系：）</section>
		 <#elseif refundProductInfo.status==5>
		    <section class="refund-tips done">退款关闭，感谢您对左岸城堡的信任和支持：）</section>
		 <#else>
		 </#if>
    </#if>
	<section class="order-msg">
		<p class="order-status money">
		<#if refundProductInfo ??>
		 <#if refundProductInfo.status==1>
		    申请中
		 <#elseif refundProductInfo.status==2>
		   待退货
		 <#elseif refundProductInfo.status==3>
		   待退款
		 <#elseif refundProductInfo.status==4>
		   退款成功
		 <#elseif refundProductInfo.status==5>
		   退款关闭
		 <#elseif refundProductInfo.status==6>
		   退款取消
		 </#if>
		</#if>
		</p>
		<p class="serial">订单编号：${(productList.orderNumber)!""}</p>
		<p class="time">成交时间：${(productList.operateTime[0..18])!""}</p>
		<p class="status">订单状态：<#if productList??><#if productList.status=='2'>待发货<#elseif productList.status=='3'>已发货<#elseif productList.status=='4'>交易成功<#elseif productList.status=='5'>用户取消<#elseif productList.status=='6'>超时取消</#if></#if></p>
	</section>

	<section class="goods-msg">
		<h3 class="goods-title">商品信息</h3>
		<ul class="goods-msg-body goods-msg-view">
		<#if productList?? >
			<li class="clearfix">
				<div class="title"><img class="thumb" src="${productList.image!""}" alt=""></div>
				<div class="desc summary">
					<p>${productList.shortName!""}</p>
					<span>合计：&yen; ${productList.totalPrice!"0.00"}（&yen;${productList.salesPrice!"0.00"} &times; ${productList.count!"0"}）</span>
				</div>
			</li>
			<input type="hidden" id="productId" name="productId" value="${productList.productId!"0"}" />
		 <#else>
         </#if>
			<li>
				<label class="title">退款数量</label>
				<div class="desc"><#if refundProductInfo??>${refundProductInfo.count!"0"}<#else>0</#if></div>
			</li>

			<li>
				<label class="title">退款金额</label>
				<div class="desc"><#if refundProductInfo??>${refundProductInfo.realMoney!"0"}<#else>0</#if></div>
			</li>

			<li class="clearfix">
				<div class="title">退款需求</div>
				<div class="desc">
				<#if refundProductInfo?? && refundProductInfo.type?? && refundProductInfo.type ==1>
				 仅退款
				<#elseif refundProductInfo?? && refundProductInfo.type?? && refundProductInfo.type ==2>
				退款并退货
				<#else>
                </#if>
				</div>
			</li>

			<li class="clearfix">
				<label class="title">退款说明</label>
				<div class="desc"><#if refundProductInfo??>${refundProductInfo.explain!""}<#else></#if></div>
			</li>

			<li class="clearfix">
				<label class="title">上传照片</label>
				<div class="desc refund-upload">
				  <#if refundProductInfo?? && refundProductInfo.image1?? && refundProductInfo.image1 !=''>
					<div class="img-box">
						<div class="box">
							<img class="thumb" src="${refundProductInfo.image1!""}" alt="">
						</div>
					</div>
				  </#if>
				  <#if refundProductInfo?? && refundProductInfo.image2?? && refundProductInfo.image2 !=''>
					<div class="img-box">
						<div class="box">
							<img class="thumb" src="${refundProductInfo.image2!""}" alt="">
						</div>
					</div>
				  </#if>
				  <#if refundProductInfo?? && refundProductInfo.image3??  && refundProductInfo.image3 !=''>
					<div class="img-box">
						<div class="box">
							<img class="thumb" src="${refundProductInfo.image3!""}" alt="">
						</div>
					</div>
				  </#if>
				</div>
			</li>

			<li>
				<label class="title">退款账号</label>
				<div class="desc">
					<#if refundPayType?exists && refundPayType == "2">
						原支付账户
					<#else>
						<#if accountCartList?? && accountCartList.type==2>
					　		支付宝
						<#elseif accountCartList?? && accountCartList.type==1>
							<#if accountCartList.bankType==1>工商银行 尾号
						  	<#elseif accountCartList.bankType==2>农业银行 尾号
						  	<#elseif accountCartList.bankType==3>中国银行 尾号
						  	<#elseif accountCartList.bankType==4>中国建设 尾号
						  	<#elseif accountCartList.bankType==5>邮政储蓄 尾号
						  	<#elseif accountCartList.bankType==6>交通银行 尾号
						  	<#elseif accountCartList.bankType==7>招商银行 尾号
						  	<#elseif accountCartList.bankType==8>光大银行 尾号
						  	<#elseif accountCartList.bankType==9>中信银行 尾号
					  		</#if>
						</#if>
						${accountCartList.cardNumber!""}&nbsp;${accountCartList.cardName!""}
					</#if>
				</div>
			</li>
		</ul>

	</section>
<script>
 function countDown(time,id){
	var hour_elem = $(id).find('.hour');
	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;
	
	if (end_time > 0) {

		var hour =(  (end_time / 3600) >24 ? Math.floor(end_time / 3600) :  Math.floor((end_time / 3600) % 24)  ) ;
		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);
	
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
		
	} else {
		//时间到达后执行操作
		//$('.buyitem').hide();
		//$('#endbegin').show();
		$('.pgoods_time1').hide();
		$('.pgoods_time').hide();
		$(hour_elem).text('00');//计算小时
		$(minute_elem).text(':00');//计算分钟
		$(second_elem).text(':00');//计算秒杀
	}	
}
</script>

</body>
</html>