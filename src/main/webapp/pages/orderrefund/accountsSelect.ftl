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
	<meta name="description" content="选择退款帐户" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>

	<header class="header">
		<h1 class="title">选择退款帐户</h1>
		
		<#if isApp ?? && isApp =='1'>
		    <a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		    <a href="javascript:history.back();" class="header-btn return">返回</a>
		</#if>
		
		<a href="${rc.contextPath}/ac/getAllAccountCard" class="header-btn manage">管理</a>
	</header>
	
	<form class="js_account_select" action="/">
	
	<#if acesList?? && (acesList?size>0)>
		<ul class="account-list">
			<#if canReturnPay?exists && canReturnPay != "0">
				<li class="account-item js_account_item" style="cursor: pointer">
					<a href="${rc.contextPath}/ac/selectAccountCard/${(al.id)!"0"}" >
						<span class="type js_type" data-type="returnBack">原路返回</span>
						<p class="account js_account" data-account="">退回原支付账户</p>
				   	</a>
				</li>
			</#if>
		 <#list acesList as al >
			  <#if selectedAccountCardId?? && selectedAccountCardId == al.id>
				 <li class="account-item cur js_account_item" style="cursor: pointer">
			   <#else>
			     <li class="account-item js_account_item" style="cursor: pointer">
			  </#if>
			  <a href="${rc.contextPath}/ac/selectAccountCard/${(al.id?c)!"0"}" >
				      <#if al.type ?? && al.type==1>
						<span class="type js_type" data-type="bankCard">退款银行卡</span>
						<p class="account js_account" data-account="">
						<#if al.bankType==1>工商银行
						<#elseif al.bankType==2>农业银行
						<#elseif al.bankType==3>中国银行
						<#elseif al.bankType==4>建设银行
						<#elseif al.bankType==5>邮政储蓄
						<#elseif al.bankType==6>交通银行
						<#elseif al.bankType==7>招商银行
						<#elseif al.bankType==8>光大银行
						<#elseif al.bankType==9>中信银行
						</#if>&nbsp;尾号${al.cardNumber!""}&nbsp;${al.cardName!""}</p>
					  <#else>
					    <span class="type js_type" data-type="alipay">退款支付宝</span>
					    <p class="account js_account" data-account="">支付宝&nbsp;${al.cardNumber!""}&nbsp;${al.cardName!""}</p>
					  </#if>
		   </a>
				</li>
		</#list>
		</ul>
	<#elseif canReturnPay?exists && canReturnPay != "0">
		<ul class="account-list">
			<li class="account-item cur js_account_item" style="cursor: pointer">
				<a href="${rc.contextPath}/ac/selectAccountCard/${(al.id)!"0"}" >
					<span class="type js_type" data-type="returnBack">原路返回</span>
					<p class="account js_account" data-account="">退货付款账户</p>
			   	</a>
			</li>
		</ul>
	<#else>
		<ul class="orders-list">
			 <div class="emptyorder">
				 <img src="${rc.contextPath}/pages/images/orderempty.png">
				 <p>您还没有退款帐户</p>
				 <p>请到管理中增加退款帐户</p>
			</div>
		</ul>
		
	 </#if>
	  
	  <input name="account_type" type="hidden">
	  <input name="account" type="hidden">
	</form>

<script>
	$(function(){
		var $form = $('.js_account_select'),
			$account_type = $form.find('input[name="account_type"]'),
			$account = $form.find('input[name="account"]');

		$('.js_account_item').click(function(){
			var $this = $(this);
			$this.addClass('cur').siblings().removeClass('cur');

			$account_type.val($this.find('.js_type').data('type'));
			$account.val($this.find('.js_account').data('account'));

			//提交跳转todo
			//$form.submit();
		});
	});
</script>


</body>
</html>