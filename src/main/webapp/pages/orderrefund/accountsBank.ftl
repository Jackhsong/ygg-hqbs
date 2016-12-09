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
	<meta name="description" content="银行账号" >
	<#include "../common/orderrefundHeader.ftl">
</head>
<body>

	<header class="header">
		<h1 class="title">银行账号</h1>
		
		<#if isApp ?? && isApp =='1'>
		    <a href="javascript:history.back();" class="header-btn app-return">返回</a>
		<#else>
		   <a href="javascript:history.back();" class="header-btn return">返回</a>
		</#if>
		<a href="javascript:;" class="header-btn save js_save">保存</a>
	</header>
	<div class="protips"></div>
	<!-- 新增账户 -->
	<form class="js_account_bank_add" action="${rc.contextPath}/ac/editAccountCard"  method="POST">
		<ul class="account-bank-add">
			<li class="form-group bank-type clearfix">
				<label class="title" for="bank">开户银行</label>
				<select  id="bankType" name="bankType">
					<option value="0">请选择</option>
					<option value="1" <#if (ace.bankType)?exists && (ace.bankType == 1)>selected</#if> >工商银行</option>
					<option value="2" <#if (ace.bankType)?exists && (ace.bankType == 2)>selected</#if>>农业银行</option>
					<option value="3" <#if (ace.bankType)?exists && (ace.bankType == 3)>selected</#if>>中国银行</option>
					<option value="4" <#if (ace.bankType)?exists && (ace.bankType == 4)>selected</#if>>建设银行</option>
					<option value="5" <#if (ace.bankType)?exists && (ace.bankType == 5)>selected</#if>>邮政储蓄</option>
					<option value="6" <#if (ace.bankType)?exists && (ace.bankType == 6)>selected</#if>>交通银行</option>
					<option value="7" <#if (ace.bankType)?exists && (ace.bankType == 7)>selected</#if>>招商银行</option>
					<option value="8" <#if (ace.bankType)?exists && (ace.bankType == 8)>selected</#if>>光大银行</option>
					<option value="9" <#if (ace.bankType)?exists && (ace.bankType == 9)>selected</#if>>中信银行</option>
				</select>
			</li>
			<li class="form-group clearfix js_input">
				<label class="title">银行卡卡号</label>
				<#if ace?? && ace.cardNumber?? >
				 <input class="credit js_credit" type="tel" value="${ace.cardNumber!""}" name="cardNumber" maxlength="30">
				<#else>
				  <input class="credit js_credit" type="tel" value="" name="cardNumber" maxlength="30">
				</#if>
				
			</li>
			<li class="form-group clearfix js_input">
				<label class="title" for="name[bank]">姓名</label>
				<#if ace?? && ace.cardName?? >
				  <input name="cardName" type="text" value="${ace.cardName!""}" maxlength="10">
				 <#else>
				   <input name="cardName" type="text" value="" maxlength="10">
			    </#if>
			</li>

			<li class="btns">
				<button class="btn" type="submit">保存</button>
			</li>
		</ul>
		<input type="hidden" name="type" value="1" />
		<#if ace?? && ace.id?? >
		 <input type="hidden" name="id" value="${ace.id?c!"0"}" />
		</#if>
	</form>
<script>
	$(function(){
	
	   <#if errorCode?? && errorCode =='2'>
		  showTipMsg("不能重复增加");
	   </#if>
	   
		var $form = $('.js_account_bank_add'),
			$bank = $('#bankType'),
			$account = $('.js_credit'),  // cardNumber 
			$name = $('input[name="cardName"]'),
			_bank, _account, _name;

		//保存验证,表单同步提交
		$form.submit(function(){
			_bank = $bank.val();
			_account = $.trim($account.val()); 
			_name = $.trim($name.val());
             
			if(_bank == '0'){
				showTipMsg('请选择银行') ;
				return false;
			}
			if(_account==''){
				showTipMsg('银行账号不能为空') ;
				return false;
			}
			if(_account.length>30){
				showTipMsg('银行账号不能超过30个字符') ;
				return false;
			}
			if(_name==''){
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