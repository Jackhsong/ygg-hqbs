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
		<#include "../common/orderHeader.ftl">
	</head>
	<body>
		<div class="page">
			<ul class="monav">
			 <#if type?? >
				<li id="orall">全部<span class="checked ${(type=='0')?string('on','')}" typevalue="0"></span></li>
				<li id="orunpay">待付款<span class="checked ${(type=='1')?string('on','')}" typevalue="1"></span><div class="navflag"></div></li>
				<li id="orredeli">待发货<span class="checked ${(type=='2')?string('on','')}" typevalue="2"></span><div class="navflag"></div></li>
				<li id="ordeli" >已发货<span class="checked ${(type=='3')?string('on','')}" typevalue="3"></span><div class="navflag"></div></li>
				<li id="orsuccess">交易成功<span class="checked ${(type=='4')?string('on','')}" typevalue="4"></span><div class="navflag"></div></li>
			 </#if>
			</ul>

           <#if orderList?? && (orderList?size>0) >
            <#list orderList as ol>
				<div class="navmain">
					<div class="ordersum clear">
						<div class="motype fl">
						<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}?type=${type}" >
						<#if ol.endSecond?? &&  (ol.endSecond=='-1' || ol.endSecond =='0' ) >已过期<#elseif ol.status?? && ol.status =='1'>待付款<#elseif ol.status == '2'>待发货<#elseif ol.status == '3'>已发货<#elseif ol.status == '4'>交易成功<#elseif  (ol.status =='5' || ol.status =='6' ) > 已取消<#else></#if>
						</a></div>
						<div class="orsum_tit fl">
						 <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}?type=${type}" >
							合计：<span>${(ol.allTotalPrice)!"0.00"}</span>
					　　　 </a>
						</div>
						<!--全部定单-->
						<#if type?? && type=='0'>
						    <#if ol.status?? && ol.status=='3'>
							        <div class="modeli">
							         <a href="javascript:void(0)" id="okOrderId3${ol_index}" class="ordersum_a">确认收货</a>
							         <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}" class="ordersum_a">查看物流</a>
									<script>
									  $(function(){
									       $('#okOrderId3${ol_index}').click(function(){
			                                        var flag=confirm('是否确认收货？');
	                                                if(flag)
	                                                　　　　　　　　　　　　　　　　　　　　　　　　　 　window.location.href="${rc.contextPath}/order/modifyokorder/${(ol.orderId)!"0"}" ;
		                                    });
									  })
									</script>
								  </div>
						    <#elseif ol.status=='2'>
							       <div class="modeli">
									 <!-- <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}"  class="ordersum_a">订单详情</a> -->
								   </div>
						    <#elseif ol.status=='1'>
						         <div class="mounpay">
						            <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}?type=${type}" class="ordersum_a">去付款</a>
									<em><img src="${rc.contextPath}/pages/images/buytime.png"><span id="gotopay${ol_index}"><span class="minute"></span><span class="second"></span></span></em>
									    <script>
											$(function(){
												var etime=${(ol.endSecond)!"0"};
												var sid="#gotopay${ol_index}";
												var timer = setInterval(function(){
													etime--;
													orderCdown(etime,sid);
												},980);
												orderCdown(etime,sid);
											})
										</script>
								</div>
						    <#elseif ol.status=='4'>
						          <div class="modesu">
								     <!--<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}">交易成功</a>-->
							      </div>
							<#elseif ol.status=='5'>
							      <div class="modesu">
								     <!--<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}">订单取消</a>-->
							      </div>
							<#elseif ol.status=='6'>
							      <div class="modesu">
								     <!-- <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}">订单取消</a> -->
							      </div>
						    </#if>
						</#if>
						<!-- 已发货时 -->
						<#if type?? && type=='3' >
							<div class="modeli">
							    <a href="javascript:void(0)" id="okOrderId3${ol_index}" class="ordersum_a">确认收货</a>
							    <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}" class="ordersum_a">查看物流</a>
								<script>
								  $(function(){
								       $('#okOrderId3${ol_index}').click(function(){
		                                        var flag=confirm('是否确认收货？');
                                                if(flag)
                                                　　　　　　　　　　　　　　　　　　　　　　　　　 　window.location.href="${rc.contextPath}/order/modifyokorder/${(ol.orderId)!"0"}" ;
	                                    });
								  })
								</script>
							</div>
						</#if>
						<!-- 待发货时 -->
						<#if type?? && type=='2' >
							<div class="modeli">
								<!--<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}">订单详情</a>-->
							</div>
						</#if>
						<!-- 待付款时 -->
					    <#if type?? && type=='1' >
					    　　　　　 <#if ol.endSecond?? &&  (ol.endSecond=='-1' || ol.endSecond =='0' ) >
					    　　　　　     <!-- 已过期　-->
					    　　　　　 <#else>
					          <div class="mounpay">
					          <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}?type=${type}" class="ordersum_a">去付款</a>
							  <em><img src="${rc.contextPath}/pages/images/buytime.png"><span id="gotopay${ol_index}"><span class="minute"></span><span class="second"></span></span></em>
							  <script>
									$(function(){
										var etime=${(ol.endSecond)!"0"};
										var sid="#gotopay${ol_index}";
										var timer = setInterval(function(){
											etime--;
											orderCdown(etime,sid);
										},980);
										orderCdown(etime,sid);
									})
							</script>
							</div>
					    </#if>
						</#if>
						<!-- 交易成功 End -->
						 <#if type?? && type=='4' >
						    <div class="modesu">
								<!--<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}">交易成功</a>-->
							</div>
						 </#if>
					</div>
					   <#if ol.orderDetailList?? && (ol.orderDetailList?size>0)>
						      <#list ol.orderDetailList as odl >
							       <input type="hidden" id="productId${odl_index}" name="productId${odl_index}" value="${(odl.productId)!"0"}" />
							       <a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}?type=${type}">
									<!-- 订单商品列表 -->
									<dl class="order_list clear navm_bott">
										<#if odl.refundStatus=='1'>
									    <i class="refund-txt">退款申请中</i>
									  <#elseif odl.refundStatus=='2'>
									    <i class="refund-txt">待退货</i>
									   <#else>
										  <#if odl.type =='1'>
											     <i class="refund-txt"><#if odl.refundStatus=='3'>待退款<#elseif odl.refundStatus=='4'>退款成功<#elseif odl.refundStatus=='5'>退款关闭</#if>
											     </i>
									      <#elseif odl.type =='2'>
											     <i class="refund-txt"><#if odl.refundStatus=='3'>待退款<#elseif odl.refundStatus=='4'>退款成功<#elseif odl.refundStatus=='5'>退款关闭</#if>
											     </i>
										 </#if>
								        </#if>
										<dt><img src="${(odl.image)!""}"></dt>
										<dd class="orderptit">${(odl.shortName)!""}</dd>
										<dd>￥${(odl.salesPrice)!"0.00"}<span>X${(odl.count)!"0"}</span></dd>

									</dl>
								   </a>
							  </#list>
						</#if>
						<input type="hidden" id="orderId${ol_index}" name="orderId{ol_index}" value="${(ol.orderId)!"0"}" />
					<!-- 订单商品列表 End-->
					<a href="${rc.contextPath}/order/orderdetail/${(ol.orderId)!"0"}" >
					 <p>订单编号：${(ol.orderNumber)!""}<span>${(ol.operateTime[0..18])!""}</span></p>
					</a>
				</div>
		     </#list>
           <#else>
			 <!-- 为空时显示此  -->
			 <div class="emptyorder">
				 <img src="${rc.contextPath}/pages/images/orderempty.png">
				 <p>您还没有相关的订单</p>
				 <p>暂时没有相关数据</p>
			</div>
		  </#if>

		</div>
		<!--底部导航 Start-->
		<input type="hidden" id="navFooterToPage" value="2">
		<#include "../common/navFooter.ftl">
		<!--底部导航 End-->
		<#include "../common/otherShare.ftl">
<!-- <script src="${rc.contextPath}/pages/js/h5self-adaption.js" type="text/javascript"></script> -->
<script>
$(function(){

$(".navmain").last().css("marginBottom","1.2rem");

$('.monav li').click(function(){
		$('.monav .checked').css('display','none');
		$(this).find('.checked').css('display','block');
		var typevalue = $(this).find('.checked').attr('typevalue') ;
		window.location.href="${rc.contextPath}/order/list/"+typevalue ;
	});



function orderCdown(time,id){

	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;

	if (end_time > 0) {

		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);

		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
	}
};



/*确认收货*/
$("#okOrderId").click(function(){
     var url="${rc.contextPath}/order/modify/${(orderId)!"0"}/${(orderStatus)!"1"}" ;
     $.ajax({
           type:'POST',
           url: url ,
           data: '' ,
           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
           success: function(msg){
               if(msg.status !=undefined && msg.status=='1')
                   window.location.href="${rc.contextPath}/order/orderdetail/${(orderId)!"1"}" ;
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
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
