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
	<meta name="description" content="支付宝账号" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>

	<header class="header">
		<h1 class="title">支付宝账号</h1>
		
		<#if isApp ?? && isApp =='1'>
		    <a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		   <a href="javascript:history.back();" class="header-btn return">返回</a>
		</#if>
		<a href="javascript:;" class="header-btn save js_save">保存</a>
	</header>
	<div class="protips"></div>
	<!-- 新增账户 -->
	<form class="js_account_alipay_add" action="${rc.contextPath}/ac/editAccountCard" method="POST">
		<ul class="account-alipay-add">
			<li class="form-group alipay-type clearfix js_input">
				<label class="title" for="alipay">支付宝账号</label>
			   <#if ace?? && ace.cardNumber?? >
				  <input name="cardNumber" type="text" id="cardNumber" value="${ace.cardNumber!""}" maxlength="30">
				<#else>
				  <input name="cardNumber" type="text" id="cardNumber" value="" maxlength="30">
			   </#if>
			</li>
			<li class="form-group clearfix js_input">
				<label class="title" for="name[alipay]">姓名</label>
				<#if ace?? && ace.cardName?? >
				   <input name="cardName" type="text" id="cardName" value="${ace.cardName!""}" maxlength="10">
				<#else>
				   <input name="cardName" type="text" id="cardName" value="" maxlength="10">
				</#if>
			</li>

			<li class="btns">
				<button class="btn" type="submit">保存</button>
			</li>
		</ul>
<input type="hidden" name="type" value="2" />
<#if ace?? && ace.id?? >
		 <input type="hidden" name="id" value="${ace.id?c!"0"}" />
		</#if>
	</form>
<script>
	$(function(){
	
	   <#if errorCode?? && errorCode =='2'>
		  showTipMsg("不能重复增加");
	   </#if>
		var $form = $('.js_account_alipay_add'),
			$account = $('#cardNumber'),
			$name = $('#cardName'),
			_account, _name;

		//保存验证,表单同步提交
		$form.submit(function(){
			_account = $.trim($account.val()); 
			_name = $.trim($name.val());

			if(_account== ''){
				showTipMsg('支付宝账号不能为空') ;
				return false;
			}
			if(_account.length>30){
				showTipMsg('支付宝账号不能超过30个字符') ;
				return false;
			}
			if(_name == ''){
				showTipMsg('姓名不能为空') ;
				return false;
			}
			if(_name.length>10){
				showTipMsg('姓名不能超过10个字符') ;
				return false;
			}
		});

		//点击右上角保存
		$('.js_save').click(function(){
			$form.submit();
		});
	});
</script>

</body>
</html>