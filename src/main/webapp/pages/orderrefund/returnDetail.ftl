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
<div class="protips"></div>
	<header class="header">
		<h1 class="title">退款详情</h1>
		<#if isApp ?? && isApp =='1'>
		   <a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		   <a href="javascript:history.back();" class="header-btn return">返回</a>
		</#if>
	
	</header>
    <#if refundStatus =='1'><#elseif refundStatus =='2'>
    <section class="refund-tips done">退款申请已通过，请将商品寄回到左岸城堡退货地址。</section>
    <#elseif refundStatus =='3'>
    	<section class="refund-tips done">退货信息已提交，左岸城堡将在收到商品后将款项打入您的账户。</section>
    	
    <#elseif refundStatus == '4'>
       <section class="refund-tips done">退款成功，款项已打入您的账户，如有疑问，请随时与左岸城堡客服联系：）</section>
    <#elseif refundStatus == '5'>
    	<section class="refund-tips done">退款关闭，感谢您对左岸城堡的信任和支持：）</section>
    </#if>

   <#if productList?? >
	<section class="order-msg">
		<p class="order-status"><#if refundStatus =='1'>申请中<#elseif refundStatus =='2'>待退货<#elseif refundStatus =='3'>待退款<#elseif refundStatus =='4'>退款成功<#elseif refundStatus =='5'>退款关闭<#elseif refundStatus =='6'>退款取消</#if></p>
		<p class="serial">订单编号：${productList.orderNumber}</p>
		<p class="time">成交时间：${productList.operateTime}</p>
		<p class="status">订单状态：<#if productList.status=='2'>待发货<#elseif productList.status=='3'>已发货<#elseif productList.status=='4'>交易成功<#elseif productList.status=='5'>用户取消</#if></p>
	</section>
   </#if>

	<section class="goods-msg">
		<h3 class="goods-title">商品信息</h3>
		<ul class="goods-msg-body goods-msg-view">
		  <#if productList?? >
			<li class="clearfix">
				<div class="title"><img class="thumb" src="${productList.image}" alt=""></div>
				<div class="desc summary">
					<p>${productList.shortName}</p>
					<span>合计：&yen; ${productList.totalPrice}（&yen;${productList.salesPrice} &times; ${productList.count}）</span>
				</div>
			</li>
		  </#if>
		</ul>
	</section>

	<section class="return-msg">
		<h3 class="return-title">退款信息</h3>
		<dl>
			<dt>退货数量：</dt>
			<dd>${count!"0"}</dd>
			<dt>退款金额：</dt>
			<dd>&yen; ${money!"0"}</dd>
			<dt>退款需求：</dt>
			<dd>退款并退货</dd>
			<dt>退款说明：</dt>
			<dd>${explain!"0"}</dd>
			<dt>退款账号：</dt>
			<dd>${accountCardValue!"0"}</dd>
		</dl>
	</section>
	
	<section class="return-msg">
		<h3 class="return-title">退货信息</h3>
		<dl>
			<dt>退货地址</dt>
			<dd>${receiveAddress!"浙江杭州西湖区浙商财富中心4号楼606室 左岸城堡（收）0571-86888702"}</dd>
		</dl>
	</section>

<#if refundStatus =='2' || (number?? && number!='')>
		<section class="goods-msg express-msg">
		<h3 class="goods-title express-title">物流信息</h3>
		<form id="logisticsForm" action="${rc.contextPath}/orderrefund/submitReturnGood" method="POST">
		   <input type="hidden" id="orderProductRefundId" name="orderProductRefundId" value="${orderProductRefundId!""}" />
			<ul>
				<li class="form-group clearfix">
					<label for="express-company">物流公司</label>
					<p class="desc">
					<#if refundStatus =='2'>
						<select id="channel" name="channel">
							<option value ="0">请选择</option>
							<option value="shunfeng">顺丰速运</option>
							<option value="yuantong">圆通速递</option>
							<option value="shentong">申通快递</option>
							<option value="zhongtong">中通速递</option>
							<option value="yunda">韵达快运</option>
							<option value="huitongkuaidi">汇通快运</option>
							<option value="tiantian">天天快递</option>
							<option value="zhaijisong">宅急送</option>
							<option value="ems">EMS</option>
							<option value ="guotongkuaidi">国通快递</option>
						</select>
					<#else>
						<!-- <input type="text" id="number" name="number" value="${channel!""}" /> -->
						<p class="desc">${channel!""}</p>
					</#if>
					</p>
				</li>
				<li class="form-group clearfix">
					<label for="express-series">物流单号</label>
					<p class="desc"><#if refundStatus =='2'><input type="text" id="number" name="number" value="" /><#else>${number!""}</#if></p>
				</li>
				<li class="btns">
				<#if refundStatus =='2'>
				  <a class="btn" href="javascript:void(0);" onclick="submitGoods()"  >确认退货</a>
				<#else>
				  <a class="btn" href="${rc.contextPath}/order/logistic/${(orderProductRefundId)!"0"}/10">查看物流详情</a>
				</#if>
				</li>
			</ul>
		</form>
	</section>		
</#if>

<script>
	var $form = $('#logisticsForm'),
		_channel,_number;
	
	function submitGoods(){
		_channel = $.trim($("#channel").val()); 
		_number = $.trim($("#number").val());
		if(_channel== '0'){
			showTipMsg('请选择物流公司') ;
			return false;
		}
		if(_number == ''){
			showTipMsg('物流单号不能为空') ;
			return false;
		}else if(!/^\w+$/.test(_number)){
			showTipMsg('物流单号不正确') ;
			return false;
		}
		$form.submit();
	}
</script>
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
		
	}	
}
</script>

</body>
</html>