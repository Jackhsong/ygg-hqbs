<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8" />
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
	<meta name="description" content="提交申请" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>
<div class="protips"></div>
	<header class="header">
		<h1 class="title">提交申请</h1>
		
		<!--
		<#if isApp_isAccountIdEmpty ?? && isApp_isAccountIdEmpty =='1'>
		    <a href="${rc.contextPath}/orderrefund/getOrderInfo" class="header-btn app-return">返回</a>
		<#else>
		    <#if isApp ?? && isApp =='1'>
			   <a href="ggj://redirect" class="header-btn app-return">返回</a>
		    <#else>
			  <a href="${rc.contextPath}/orderrefund/getOrderInfo" class="header-btn return">返回</a>
		    </#if>
		</#if>
		-->
		<a href="javascript:history.back();" class="header-btn return">返回</a>
	</header>

<#if productList?? >
	<section class="order-msg">
		<p class="order-status apply"><#if productList.status=='2'>待发货<#elseif productList.status=='3'>已发货<#elseif productList.status=='4'>交易成功<#elseif productList.status=='5'>用户取消<#elseif productList.status=='6'>超时取消</#if></p>
		<p class="serial">订单编号：${productList.orderNumber!""}</p>
		<p class="time">${productList.operateTime[0..18]!""}</p>
	</section>
</#if>
	<section class="goods-msg">
		<h3 class="goods-title">商品信息</h3>
		<form class="js_refund_form" action="${rc.contextPath}/orderrefund/submitApplication" id="form1" name="form1"  method="POST">
			<ul class="goods-msg-body">
			  <#if productList?? >
					<li class="clearfix">
						<div class="title"><img class="thumb" src="${productList.image!""}" alt=""></div>
						<div class="desc summary">
							<p>${productList.shortName!""}</p>
							<input type="hidden" value="${productList.totalPrice!"0.00"}" id="productTotalPrice"/>
							<span>合计：&yen; ${productList.totalPrice!"0.00"}（&yen;${productList.salesPrice!"0.00"} &times; ${productList.count!"0"}）</span>
						</div>
					</li>
 			   </#if>
				<li class="clearfix">
					<label class="title">退款数量</label>
					<select name="count" id="refund-number">
					  <#if countList?? && (countList?size>0) >
					  	<#if countList?? && (countList?size>1) ><option value="">点击选择数量</option></#if>
						<#list countList as cl >
						  <option value="${cl}" <#if (oprf.count)?exists && (oprf.count == cl)>selected</#if> >${cl}</option>
						</#list>
					  <#else>
						 <option value="1" selected=true>1</option>
					  </#if>
					</select>
				</li>

				<li class="clearfix js_input">
					<label class="title">退款金额</label>
					<!--<input name="refund_price" type="text" placeholder="0"> -->
					<#if oprf?? && oprf.applyMoney!=0 >
						<input type="text" name="applyMoney" id="applyMoney" placeholder="点击输入退款金额"  value="${oprf.applyMoney?string('0.00')}">
					<#else>
					  <input type="text" name="applyMoney" id="applyMoney" placeholder="点击输入退款金额">
					</#if>
				</li>

				<li class="clearfix">
					<div class="title">退款需求</div>
					<ul class="desc refund-type js_refund_type">
					 <#if oprf?? && oprf.type ==2>
						<li >仅退款</li>
						<li class="cur">退款并退货</li>
						<input id="refund_type"  name="type" type="hidden" value="${(oprf.type)!"2"}">
					<#else>
						<li class="cur">仅退款</li>
						<li >退款并退货</li>
						
						<input id="refund_type"  name="type" type="hidden" value="${(oprf.type)!"1"}">
					</#if>
					</ul>
				</li>

				<li class="clearfix js_input">
					<label class="title">退款说明</label>
					<#if oprf?? >
					   <textarea class="desc refund-notice js_refund_notice" name="explain">${oprf.explain!""}</textarea>
					<#else>
					   <textarea class="desc refund-notice js_refund_notice" name="explain"></textarea>
					</#if>
					
				</li>
				
				<li class="clearfix">
					<label class="title">上传照片</label>
					<div class="desc refund-upload">
						<!-- 第一张 -->
						<#if (oprf.image1)?? && oprf.image1 != ''>
							<div class="img-box uploaded js_file_upload">
						<#else>
							<div class="img-box js_file_upload">
						</#if>
							<div class="box">
							<#if (oprf.image1)?? && oprf.image1 != ''>
								<img class="thumb js_img" src="${oprf.image1!''}" alt="">
							<#else>
								<img class="thumb js_img" src="" alt="">
							</#if>
								<input type="file" accept="image/*" capture="camera"/>
								<#if (oprf.image1)?? && oprf.image1 != ''>
									<input type="hidden" name="image1" value="${oprf.image1!''}" />
								<#else>
									<input type="hidden" name="image1" />
								</#if>
								<p class="tips js_tips">上传图片</p>
							</div>
							<i class="remove js_remove"></i>
						</div>
						
						<!-- 第二张 -->
						<#if (oprf.image1)?? && oprf.image1 != ''>
							<#if (oprf.image2)?? && oprf.image2 != ''>
								<div class="img-box uploaded js_file_upload">
							<#else>
								<div class="img-box js_file_upload">
							</#if>
							<div class="box">
								<#if (oprf.image2)?? && oprf.image2 != ''>
									<img class="thumb js_img" src="${oprf.image2!''}" alt="">
								<#else>
									<img class="thumb js_img" src="" alt="">
								</#if>
									<input type="file" accept="image/*" capture="camera"/>
									<#if (oprf.image2)?? && oprf.image2 != ''>
										<input type="hidden" name="image2" value="${oprf.image2!''}" />
									<#else>
										<input type="hidden" name="image2" />
									</#if>
									<p class="tips js_tips">上传图片</p>
								</div>
								<i class="remove js_remove"></i>
							</div>
						</#if>
						
						<!-- 第三张 -->
						<#if (oprf.image2)?? && oprf.image2 != ''>
							<#if (oprf.image3)?? && oprf.image3 != ''>
								<div class="img-box uploaded js_file_upload">
							<#else>
								<div class="img-box js_file_upload">
							</#if>
							<div class="box">
								<#if (oprf.image3)?? && oprf.image3 != ''>
									<img class="thumb js_img" src="${oprf.image3!''}" alt="">
								<#else>
									<img class="thumb js_img" src="" alt="">
								</#if>
									<input type="file" accept="image/*" capture="camera"/>
									<#if (oprf.image3)?? && oprf.image3 != ''>
										<input type="hidden" name="image3" value="${oprf.image3!''}" />
									<#else>
										<input type="hidden" name="image3" />
									</#if>
									<p class="tips js_tips">上传图片</p>
								</div>
								<i class="remove js_remove"></i>
							</div>
						</#if>
					</div>
				</li>

				<li class="clearfix">
					<!-- <div class="desc set-account js_set_account">设置退款账户<i></i></div> -->
					<input type="hidden" name="canReturnPay" value="<#if canReturnPay?exists>${canReturnPay}<#else>0</#if>" />
					<!--
					<#if canReturnPay?exists && canReturnPay == "3">
						<#assign noSelectAccountCardMsg="原路返回">
						<#assign noSelectAccountCardValue="3">
						<a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
					<#elseif canReturnPay?exists && canReturnPay == "2">
						<#assign noSelectAccountCardMsg="原路返回">
						<#assign noSelectAccountCardValue="2">
						<a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
					<#elseif canReturnPay?exists && canReturnPay == "1">
						<#assign noSelectAccountCardMsg="退款帐号选择">
						<#assign noSelectAccountCardValue="1">
						<#if oprf?? >
				           <a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${oprf.accountCardVal!noSelectAccountCardMsg}<i></i></a>
			           <#else>
			           		<a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
			           </#if>
					<#else>
						<#assign noSelectAccountCardMsg="设置退款账户">
						<#assign noSelectAccountCardValue="-1">
						<a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
					</#if>
					-->
					<!-- <#if oprf?? >
			           <a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${oprf.accountCardVal!noSelectAccountCardMsg}<i></i></a>
					<#else>
					   <a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
					</#if> -->
					
					<#if canReturnPay?exists && canReturnPay != "0">
						<#assign noSelectAccountCardMsg="原路返回">
						<#assign noSelectAccountCardValue="0">
					<#else>
						<#assign noSelectAccountCardMsg="设置退款账户">
						<#assign noSelectAccountCardValue="-1">
					</#if>
					<#if oprf?? >
			           <a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${oprf.accountCardVal!noSelectAccountCardMsg}<i></i></a>
					<#else>
					   <a class="desc set-account js_set_account" href="javascript:void(0)" onClick="setAccountCard();"><label class="title">退款账号</label>${noSelectAccountCardMsg}<i></i></a>
					</#if>

					<input name="account_type" type="hidden">
					<input name="account" type="hidden">
				</li>
			</ul>

			<div class="btns"><button class="btn" type="submit">提交申请</button></div>
			<#if oprf?? >
			   <input type="hidden" name="accountCardId" value="${oprf.accountCardId?c!noSelectAccountCardValue}" />
			   <input type="hidden" name="accountCardVal" value="${oprf.accountCardVal!noSelectAccountCardMsg}" />
			   <input type="hidden" name="id" value="${oprf.id?c!"0"}" />
			<#else>
			   <input type="hidden" name="accountCardId" value="${noSelectAccountCardValue}" />
			   <input type="hidden" name="id" value="0" />
			</#if>
			<input type="hidden" name="orderProductId" value="<#if orderProductId?exists>${orderProductId}<#else>0</#if>" />
			<input type="hidden" name="productId" value="<#if productId?exists>${productId}<#else>0</#if>" />
			<input type="hidden" name="orderId" value="<#if orderId?exists>${orderId}<#else>0</#if>" />
			<input type="hidden" name="ordSource" value="2" />
			<input type="hidden" name="canReturnPay" value="<#if canReturnPay?exists>${canReturnPay}<#else>0</#if>" />
			
		</form>

	</section>

<script src="${rc.contextPath}/pages/js/lrz.mobile.min.js"></script>
<script src="${rc.contextPath}/pages/js/apply.js?vs=${yggJsVersion!"1"}"></script>
<script>

function  setAccountCard()
{
  var  refundPrice = $('#applyMoney').val();
  var refundNumber = $('#refund-number').val();//数量
  
  if(!$.isNumeric(refundNumber)){
		/* showTipMsg("请选择退款数量");
		return false; */
		$('#refund-number').val(0);
	}
  
  if(!$.isNumeric( refundPrice)){
	/* showTipMsg("请输入退款金额");
	return false; */
	$('#applyMoney').val(0)
  }
   document.form1.action="${rc.contextPath}/ac/toSelectAccountCard";
   document.form1.submit();

   if(!$.isNumeric( refundPrice)){
	$('#applyMoney').val("");
  }
   
}

function  ajaxFileUpload(base64,$input,$img)
{
   $.ajax({
		             	type: 'POST',
		             	 url: '${fileUploadUrl!""}',
		             	//url: 'http://localhost:8080/ygg/orderrefund/fileUpLoad',
		             	data: {'ysimg': base64},
		             	dataType: 'json',
		             	success: function(res){
                            if(res.status =='0')
		                 	  {
                            	showTipMsg("上传失败!");
                            	return ;
		             	      }
		             		//todo 需要返回服务器图片地址，并赋值到input；
		             		$input.val(res.imageUrl);
		             		$img.attr('src', res.imageUrl);
		             		showTipMsg("上传成功!");
		             	}
		             });
}

</script>
<script>
<#if errorCode ?? && errorCode =='5'>
  showTipMsg("退款数量不能大于购买的最大数量!");
<#elseif errorCode ?? && errorCode =='7'>
　　showTipMsg("请输入正确的退款金额!");
<#elseif errorCode ?? && errorCode =='8'>
　　showTipMsg("退款金额不能大于商品总金额哦!");
<#elseif errorCode ?? && errorCode =='9'>
　　showTipMsg("申请退款已经提交,请到订单中查看!");
<#elseif errorCode ?? && errorCode =='10'>
	showTipMsg("您的订单只能申请仅退款哦");
<#elseif errorCode ?? && errorCode =='2'>
　　showTipMsg("请设置退款帐号");
</#if>
</script>

</body>
</html>