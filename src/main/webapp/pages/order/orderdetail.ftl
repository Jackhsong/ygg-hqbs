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
<style>
.pronotice{ background: rgba(0,0,0,0.8); position: absolute; top: 80%; left: 50%; border-radius: 3px; -webkit-border-radius: 3px; -moz-border-radius: 3px; -ms-border-radius: 3px; color: #ffffff; font-size: 13px; text-align: center; z-index: 1}
.pronotice{ display: none; height: 103px; line-height: 133px; width: 42%; margin-left: -21%; }
.pronotice img{ position: absolute; top: 22px; left: 50%; width: 20px; margin-left: -10px}
</style>
		<#include "../common/orderHeader.ftl">
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body>
		<div class="protips"></div>
		<div class="page">
			<!-- 订单待发货、已发货状态时，隐藏掉此段 -->
			<#if orderStatus?? && orderStatus =='1' >
			   <div class="onotice"><img src="${rc.contextPath}/pages/images/buytime.png"><em>请在<span id="ordertime"><span class="minute"></span><span class="second"></span></span>内完成付款，时间结束后商品可能被抢完。</em></div>
			</#if>
			<!-- 订单待发货时隐藏掉此段 End -->
			<!-- 已取消时展示 -->
			<#if orderStatus?? && orderStatus =='5' ||orderStatus =='6' >
			   <!--
			   <div class="cnoticefail off"><img src="${rc.contextPath}/pages/images/buytime.png"><em>提交订单后<span id="ordertimeoff">30:00</span>未付款，订单已取消。</em></div>
			   -->
			</#if>
			<!-- 已取消时展示 End -->
            <#if orderStatus?? && orderStatus =='1'>
				<ul class="odetail clear">
					<li>
						<p><img src="${rc.contextPath}/pages/images/wunpay.png">待付款</p>
						<p>订单编号:${(orderNumber)!""}</p>
						<p>${(createTime[0..18])!""}</p>
					</li>
					<li>
					<#if endSecond?? &&  (endSecond=='-1' || endSecond =='0' ) >
					<#else>
					  <a href="javascript:void(0)" id="cancelOrderId">取消订单</a>
					</#if>
					</li>
				</ul>
            <#elseif  orderStatus =='5' || orderStatus =='6'>
				<!-- 已取消时展示 -->
				<ul class="odetailc clear">
					<li>
						<p><img src="${rc.contextPath}/pages/images/orcancle.png">已取消</p>
						<p>订单编号:${(orderNumber)!""}</p>
						<p>${(createTime[0..18])!""}</p>
					</li>
				</ul>
           <#elseif  orderStatus =='2'>
				<!-- 待发货时展示 -->
				<ul class="odetailc clear blue">
					<li>
						<p><img src="${rc.contextPath}/pages/images/redeli.png">待发货</p>
						<p>订单编号:${(orderNumber)!""}</p>
						<p>${(createTime[0..18])!""}</p>
					</li>
					<#if sellerType?? && sellerType != '1' && bondedNumType = '2'>
						<li class="order-trumpet">
							商品已发货，请耐心等待。
						</li>
					</#if>
				</ul>
            <#elseif  orderStatus =='3'>
				<!-- 已发货时展示 -->
				<ul class="odetailc clear blue">
					<li>
						<p><img src="${rc.contextPath}/pages/images/deli.png">已发货</p>
						<p>订单编号:${(orderNumber)!""}</p>
						<p>${(createTime[0..18])!""}</p>
					</li>
					<#if sellerType?? && sellerType != '1' && bondedNumType = '1'>
						<li class="order-trumpet">
							商品已发货，请耐心等待
						</li>
					</#if>
				</ul>
			<#elseif  orderStatus =='4'>
			   <ul class="odetailc clear blue">
					<li>
						<p><img src="${rc.contextPath}/pages/images/trade_success.png">交易成功</p>
						<p>订单编号:${(orderNumber)!""}</p>
						<p>${(createTime[0..18])!""}</p>
					</li>
				</ul>
			<#else>
			</#if>
			<!--物流信息  -->
			<#if orderStatus?? && ( orderStatus =='3' || orderStatus =='4' ) >
			  <div class="coninfo">
					<div class="coninfo_list"><img src="${rc.contextPath}/pages/images/logistic.png" class="wid">物流信息<span>${(logisticsChannel)!""} ${(logisticsNumber)!""}</span></div>
					<#if logisticsDetailList?? && (logisticsDetailList?size >0 )  >
						<div class="coninfo_list">
						  <#list logisticsDetailList as  map >
							       <p>
							       ${map['operateTime']}　${map['content']}
							       </p>
						  </#list>
						</div>
						<a href="${rc.contextPath}/order/logistic/${(orderId)!"1"}/1">
					       <div class="onlogis">查看更多信息...</div>
				    </a>
					<#else>
					 <#if isBonded?? && isBonded =='1'>
					    <div class="coninfo_list">
					      <p>商品已发货，请耐心等待</p>
					    </div>
					  <#else>
					     <div class="coninfo_list">
					        <p>暂无物流信息</p>
					     </div>
					 </#if>
					</#if>
			  </div>
			</#if>

			<#if orderAddress?? >
			<div class="coninfo">
			<#assign  da=orderAddress />
								<div class="coninfo_list"><img src="${rc.contextPath}/pages/images/landmark.png">收货信息</div>
								<div class="coninfo_list">
									<p>${(da.fullName)!""}　${(da.mobileNumber)!""}</p>
									<p>${(da.detailAddress)!""}</p>
								</div>
			</div>
			</#if>

			<div class="ordermain">
				<!-- 订单类别 -->
				<#if orderProductList?exists && (orderProductList?size>0 ) >
					<div class="order_tit clear"><span>商品信息</span><span>
					<#if sellerName?contains("左岸城堡")>
						${sellerName?replace("左岸城堡","左岸城堡")}
					<#else>
						杭州左岸有限公司
					</#if>
					${(sendAddress)!""}发货<#if freightPrice?? && freightPrice=='0'>(包邮)</#if></span></div>

				   <#list  orderProductList as opl>
						<div class="order">
							<!-- 订单商品列表 -->
							<dl class="order_list clear">
								<dt><a href="${rc.contextPath}/product/single/${(opl.productId)!"0"}"><img src="${(opl.image)!""}"></a></dt>
								<dd class="orderptit"><a href="${rc.contextPath}/product/single/${(opl.productId)!"0"}">${(opl.shortName)!""}</a></dd>
								<dd class="fr">￥${(opl.salesPrice)!"0.00"}<span>X${(opl.count)!"0"}</span></dd>
								<#if opl.refundStatus =='0'>
									    <a class="refund-btn fr" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(orderId)!"0"}&orderProductId=${opl.orderProductId!"0"}&productId=${opl.productId!"0"}">申请退款
									    </a>
									  <#elseif opl.refundStatus=='1'>
									    <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(opl.orderProductRefundId)!"0"}">退款申请中
									    </a>
									  <#elseif opl.refundStatus=='2'>
									    <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(opl.orderProductRefundId)!"0"}&type=${opl.refundStatus!""}">待退货
									    </a>
									  <#elseif opl.refundStatus=='6'>
									    <a class="refund-btn fr" href="${rc.contextPath}/orderrefund/getSubmitApplicationInfo?orderId=${(orderId)!"0"}&orderProductId=${opl.orderProductId!"0"}&productId=${opl.productId!"0"}">申请退款
									    </a>
									  <#else>
										  <#if opl.type =='1'>
											     <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/refundInfo?orderProductRefundId=${(opl.orderProductRefundId)!"0"}"><#if opl.refundStatus=='3'>待退款<#elseif opl.refundStatus=='4'>退款成功<#elseif opl.refundStatus=='5'>退款关闭</#if>
											     </a>
									      <#elseif opl.type =='2'>
											     <a class="refund-btn refund-btn-text fr" href="${rc.contextPath}/orderrefund/returnGoodInfo?orderProductRefundId=${(opl.orderProductRefundId)!"0"}&type=${(opl.refundStatus)!"0"}"><#if opl.refundStatus=='3'>待退款<#elseif opl.refundStatus=='4'>退款成功<#elseif opl.refundStatus=='5'>退款关闭</#if>
											     </a>
										 </#if>
								</#if>
							</dl>
						</div>
				   </#list>
				   <!-- 订单商品列表 End-->
								<div class="order_foot">发货方式<span>快递：<#if freightPrice?? && freightPrice=='0'>0元(包邮)<#else>${freightPrice}元</#if></span></div>
								<div class="order_foot">订单总计<span>￥${(totalPrice)!"0.00"}</span></div>
								<!-- <div class="order_foot">积分优惠<span>￥${(accountPointPrice)!"0.00"}</span></div>
								<div class="order_foot">优惠券优惠<span>￥${(couponPrice)!"0.00"}</span></div> -->
								<#if orderStatus?? && (orderStatus =='2' || orderStatus =='3' || orderStatus =='4') >
									<#if paytype?? && paytype=='1'>
									 <div class="order_foot">支付方式<span>银联支付</span></div>
									<#elseif paytype?? && paytype=='2'>
									 <div class="order_foot">支付方式<span>支付宝支付</span></div>
									<#elseif paytype?? && paytype=='3'>
									 <div class="order_foot">支付方式<span>微信支付</span></div>
								    </#if>
							    </#if>

				</#if>
			</div>
			<!-- 订单未付款时展示 -->
			<#if orderStatus?? && orderStatus =='1' >
			<div class="paytype">
				<div class="choosepay">选择支付方式<span>合计金额：<em>${realPrice!"0.00"}</em></span></div>
				<div class="paytb" id="paywexin"><img src="${rc.contextPath}/pages/images/wxpay.png" class="cpay">微信支付<span><img src="${rc.contextPath}/pages/images/<#if iswx5version?exists && (iswx5version == '1')>select.png<#else>unselect.png</#if>" class="cpayflag"></span></div>
			</div>
			<#else>

			</#if>
			<#if orderStatus?? && orderStatus =='1' >
			 <div class="pay clear">
			 <#else>
			 <div class="pay clear off">
			</#if>
				<ul>
					<li id="paytitle">支付宝支付</li>
					<li></li>
				</ul>
				总计￥<span>${realPrice!"0.00"}</span>
				<#if endSecond?? &&  (endSecond=='-1' || endSecond =='0' ) >
				    <a href="javascript:history.back();">订单已过期</a>
				<#elseif orderStatus?? && orderStatus =='1'  >
				  <a href="javascript:void(0)" id="gotopayId">去付款</a>
				</#if>
			</div>
			<!-- 订单取消时展示 -->
			<#if orderStatus?? && ( orderStatus =='5' || orderStatus =='6' ) >
			   <!--<div class="rebuy"><a href="javascript:void(0)">将有货的商品重新加入购物车</a></div>-->
			</#if>
			<!-- 已发货时展示 -->
			<#if orderStatus?? && orderStatus =='3' >
			   <div class="rebuy"><a href="javascript:void(0)" id="okOrderId">确认收货</a></div>
			</#if>
		</div>
		<div class="pronotice " ><img src="${rc.contextPath}/pages/images/loading1.gif">正在发起支付</div>
		<form id="form1" name="form1" action="" method="post">
			<input type="hidden" id="canUseCoupon" name="canUseCoupon"  value="${(canUseCoupon)!"1"}"/>
			<input type="hidden" id="orderId" name="orderId"  value="${(orderId)!"1"}"/>
			<input type="hidden" id="paytype"  name="paytype"   value="${(paytype)!"1"}"/>
			<input type="hidden" id="realPrice"  name="realPrice"   value="${realPrice!"0.00"}"/>
			<input type="hidden" name="accountId" value="${(Session.yggwebapp_current_user_key.id?c)!"0"}" />
		</form>

<script>
function showTipMsg(msg)
{
	 /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
    /* 弹出层 */
	$('.protips').html(msg);
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.protips').css('top',xtop);
	$('.protips').css('display','block');
	setTimeout(function(){
		$('.protips').css('display','none');
	},2000);
}

$(function(){

var iswx5version  = '${(iswx5version)!"0"}' ; // 判断是否为微信环境
if(iswx5version !=undefined && iswx5version=='0')
{
   var paywexin  = $('#paywexin') ;
   if(paywexin!=undefined)
      paywexin.addClass('off') ;
}

//// 确认收货
$('#okOrderId').click(function(){
        var flag=confirm('是否确认收货？');
        if(flag)
        　　　　　　　window.location.href="${rc.contextPath}/order/modifyokorder/${(orderId)!"0"}";
});

   var etime="${(endSecond)!""}";
		var sid="#ordertime";
		var timer = setInterval(function(){
			etime--;
			orderCdown(etime,sid);
		},1000);
		orderCdown(etime,sid);

init();

function init()
{
    /*paytype*/
    var  paytype =   $("#form1 input[name=paytype]").val() ;
    if(paytype =='2') /**支付宝**/
    {
        $('.payta').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/select.png');
		$('.paytb').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
        $("#paytitle").text('支付宝支付');
    }else if(paytype =='3' ) /*wexin*/
    {
        $('.paytb').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/select.png');
		$('.payta').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
		$("#paytitle").text('微信支付');
    }else if(paytype =='1')
    {
        $('.paytb').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
		$('.payta').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/select.png');
		$("#paytitle").text('银联支付');
    }
} ;

/*ajax支付*/
$("#gotopayId").click(function(){
   $('.pronotice').hide();
    var  paytype = $("#form1 input[name=paytype]").val() ;
    var canUseCoupon = $("#form1 input[name=canUseCoupon]").val();
    if(parseInt(canUseCoupon) == 0){
    	showTipMsg("请使用左岸城堡app付款~");
    	return false;
    }
    if(paytype !=undefined && paytype=='2')
    {
        var url = "${rc.contextPath}/order/topay/"+paytype;
    	//document.form1.action=url ;
    	//document.form1.submit();
    	var params = "orderId=${(orderId)!"1"}&accountId=${(Session.yggwebapp_current_user_key.id?c)!"0"}";
    	url = url+"?"+params ;
    	window.location.href=url ;

    }else if(paytype !=undefined && paytype=='3')
    {
       var iswx5version = $("#iswx5version").val();
       if(iswx5version==undefined || iswx5version =='0')
        {
           alert('请使用微信5.0以上版本才能支付!');
           return ;
        }
         showTipLoadingMsg();
         var requesturl ="${rc.contextPath}/order/topaywx/"+paytype ;
	     // var requestdata =$.jsonObjToStr($("#form1").serializeJson() ) ;
	      var requestdata = $("#form1").serializeJson2()  ; // $.jsonObjToStr() ;
	       // ajax请求
	       $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
		           var status = msgjsonobj.status ;
		           var errorCode=msgjsonobj.errorCode ;
		           if(status !=undefined && status =='0')
		           {
		                  if(errorCode!=undefined && errorCode=='1')
				              window.location.href="${rc.contextPath}/user/tologin" ;
				           else if(errorCode!=undefined && errorCode=='2'){
				              alert("订单已过期");
				              window.location.href="${rc.contextPath}/mycenter/show" ;
				           }else if(errorCode!=undefined && errorCode=='3'){
				             alert("亲，请重新选择支付方式");
				             return ;
				           }else if(errorCode!=undefined && errorCode=='4'){
				              alert("亲，订单状态错误,请刷新!");
				             return ;
				           }
		           }else if(status !=undefined && status =='1')
		           {
		                  $('.pronotice').hide();
		                  var obj  =  msgjsonobj.wxprv ;
		                  WeixinJSBridge.invoke('getBrandWCPayRequest',{
				                 "appId" : obj.appid,    //公众号名称，由商户传入
				                 "timeStamp":obj.timestamp,          //时间戳，自 1970 年以来的秒数
				                 "nonceStr" : obj.nonce_str, //随机串
				                 "package" : obj.packageValue,
				                 "signType" : "MD5",          //微信签名方式:
				                 "paySign" : obj.sign //微信签名
				                 },function(res){

				                    if(res.err_msg == "get_brand_wcpay_request:ok"){
				                         // window.location.href=obj.sendUrl;
				                         // alert("支付成功!");
				                         // window.location.href="${rc.contextPath}/mycenter/show" ;
				                          window.location.href="${rc.contextPath}/order/pay/weixinreturnback/"+obj.orderIds;
				                     }else if(res.err_msg == "get_brand_wcpay_request:cancel")
				                     {
				                       // 支付过程中用户取消
				                      // window.location.href="${rc.contextPath}/order/list/1" ;
				                       window.location.href="${rc.contextPath}/order/pay/aliwxpayfail/${(orderId)!"1"}" ;
				                     }
				                     else{
				                          alert("支付失败,请刷新页面!");
				                     }
				             });
		           }


           }});

    }else if(paytype !=undefined && paytype=='1')
    {
        var url = "${rc.contextPath}/order/topay/"+paytype;
    	document.form1.action=url ;
    	document.form1.submit();
    }

});

/*取消定单*/
$("#cancelOrderId").click(function(){
    var flag=confirm('确定要取消吗？');
    if(!flag)
    return false ;

     var url="${rc.contextPath}/order/modify/${(orderId)!"0"}/${(orderStatus)!"1"}" ;
     $.ajax({
           type:'POST',
           url: url ,
           data: '' ,
           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
           success: function(msg){
               if(msg.status !=undefined && msg.status=='1')
                   //window.location.href="${rc.contextPath}/order/orderdetail/${(orderId)!"1"}" ;
                   window.location.href="${rc.contextPath}/order/list/${(listOrderType)!"0"}" ;
               var errorCode = msg.errorCode ;
               if(errorCode !=undefined && errorCode =='100')
                   window.location.href="${rc.contextPath}/user/tologin";
               //else
                //   alert("取消订单出错,请刷新页面");
            },
           error: function(err){
           }
        });
});


});
</script>
<script>
console.log(${sellerType});
console.log(${bondedNumType});
/*
wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${(appid)!"wx7849b287f9c51f82"}', // 必填，公众号的唯一标识
	    timestamp: ${(timestamp)!"0"}, // 必填，
	    nonceStr: '${(nonceStr)!"0"}', // 必填，
	    signature: '${(signature)!"0"}',// 必填，
	    jsApiList: ['chooseWXPay']
});

wx.error(function(res){
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    alert(res.errMsg);
});
*/
</script>

<input type="hidden" id="iswx5version" name="iswx5version" value="${(iswx5version)!"0"}" />
</body>
</html>
