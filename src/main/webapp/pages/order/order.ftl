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
		<#include "../common/otherShare.ftl">
	</head>
	<body>
		<div class="page">
			 <div class="cnotice"><img src="${rc.contextPath}/pages/images/buytime.png"><em>请在<span id="ordertime"><span class="minute"></span><span class="second"></span></span>内提交订单，时间结束后商品可能被抢完。</em></div>
			 <#if tips?? && (tips?size>0)>
			  <div class="cnoticefail">
			    <#list tips as tp>
			     <img src="${rc.contextPath}/pages/images/trumpet.png"><em>${(tp)!""}</em><br/>
			     </#list>
			   </div>
			 </#if>
            <div>
            <!--<p class="loerror" style="display:block;">${(errorCode)?if_exists}</p>-->
            </div>
            <div class="protips"></div>
            <div class="pronotice " ><img src="${rc.contextPath}/pages/images/loading1.gif">正在发起支付</div>
           <#if addressId?exists && addressId!='' &&addressId !='-1' >
             <#if defaultAddress?? && ( defaultAddress.id >0 ) >
                 <#assign  da=defaultAddress />
						<a href="${rc.contextPath}/ads/listorderads/${(ordertype)!"1"}/${(isBonded)!"0"}">
							<ul class="consignee clear">
								<li><img src="${rc.contextPath}/pages/images/consign.png"></li>
								<li class="conmain">
									<div>收货人：<span>${(da.fullName)!""}</span><em>${(da.mobileNumber)!""}</em></div>
									<div class="conmain_detailadd">收货地址：${(da.detailAddress)!""}</div>
									<#if isBonded?? && isBonded =='1'>
									  <div class="conmain_detailadd" >身份证：<span id="addressIdCard">${(da.idCard)!""}</span></div>
									</#if>
								</li>
								<li><img src="${rc.contextPath}/pages/images/wangle.png"></li>
							</ul>
						</a>
			   <#else>
					<a href="${rc.contextPath}/ads/toorderaddads/${(ordertype)!"1"}/${(isBonded)!"0"}">
						<ul class="consignadd clear">
							<li><img src="${rc.contextPath}/pages/images/consign.png"></li>
							<li class="conmain">
								<div>增加收货地址</div>
							</li>
							<li><img src="${rc.contextPath}/pages/images/wangle.png"></li>
						</ul>
					</a>
			 </#if>
            <#else>
				<a href="${rc.contextPath}/ads/toorderaddads/${(ordertype)!"1"}/${(isBonded)!"0"}">
					<ul class="consignadd clear">
						<li><img src="${rc.contextPath}/pages/images/consign.png"></li>
						<li class="conmain">
							<div>增加收货地址</div>
						</li>
						<li><img src="${rc.contextPath}/pages/images/wangle.png"></li>
					</ul>
				</a>
           </#if>
			<div class="ordermain">
				<!-- 订单类别 -->
				<#if confirmOrderList?exists && (confirmOrderList?size>0 ) >
				  <#list confirmOrderList as cl>
						<a href="javascript:void(0)">
							<div class="order">
								<div class="order_tit clear"><span>订单${(cl_index+1)}</span><span>
									杭州左岸有限公司
								发货 <#if (cl.ismailbag)?exists && (cl.ismailbag) =='0'>(包邮)</#if></span></div>
								<!-- 订单商品列表 -->
								<#if cl.orderDetailList?exists && (cl.orderDetailList?size >0 ) >
								    <#list  cl.orderDetailList  as  ol >
										<dl class="order_list clear">
											<dt><img src="${(ol.image)!""}"></dt>
											<dd class="orderptit">${(ol.shortName)!""}</dd>
											<dd>￥${(ol.salesPrice)!"0"}<span>X${(ol.count)!"0"}</span></dd>
										</dl>
								    </#list>
								</#if>
								<!-- 订单商品列表 End-->
								<div class="order_foot">发货方式<span>快递：<#if (cl.ismailbag)?exists && (cl.ismailbag) =='0'>0元(包邮)<#else>${(cl.logisticsMoney)!"0"}元</#if></span></div>
								<div class="order_foot">订单总计<span>￥${(cl.allTotalPrice)!"0"}</span></div>
							</div>
						</a>
				  </#list>
				</#if>
				<!-- 订单类别 End -->
			</div>
			<div class="paytype">
				<div class="choosepay">选择支付方式<span>合计金额：<em class="payPrice">${allTotalPrice!"0.00"}</em></span></div>
				<div class="paytb" id="paywexin"><img src="${rc.contextPath}/pages/images/wxpay.png" class="cpay">微信支付<span><img src="${rc.contextPath}/pages/images/<#if iswx5version?exists && (iswx5version == '1')>select.png<#else>unselect.png</#if>" class="cpayflag"></span></div>
			</div>
			<div class="pay clear">
				<ul>
					<li>微信支付</li>
					<li class="off"></li>
				</ul>
				总计￥<span class="payPrice">${allTotalPrice!"0.00"}</span>
				<a href="javascript:void(0)" id="topayId">去付款</a>
			</div>
		</div>
		<form id="modifyidcardForm" name="modifyidcardForm" action="" method="post">
		 <input type="hidden" name="source" value="orderidcardinvalid" />
		</form>
		<script>
			$(function(){
				/*
					是否使用积分
				*/
				   slideBtn();
				   function slideBtn(){
					   //计算积分
					   var allPoint=parseInt($("#availablePoint").val()),
					   	   allPrice=parseFloat($("#couponPrice").val()).toFixed(2),
					   	   usePoint=allPrice*100<allPoint?allPrice*100:allPoint,
					   	   pointToMoney=usePoint/100,//积分抵的价格
					   	   payMoney=allPrice-pointToMoney;//实际需要付的金额
					   $("#point").text(usePoint);
					   $("#money").text(pointToMoney);
					   $(".payPrice").text(allPrice);
					   $(".unbind").unbind();
					   //按钮滑动效果
					   var isOpen=false,ev="click";
					   if($("body").width()<=750){
						   ev="touchstart";
					   }
					   if(allPrice>0){
						   $(".pointBtn").on(ev,function(){
							   var $this=$(this);
							   if(isOpen){
								   $this.attr("class","pointBtn closeAni close");
								   $this.attr("isOpen","false");
								   $this.find("i").attr("class","closeInnerCircleAni");
								   isOpen=false;
								   $("#couponPrice").val(allPrice);
								   $(".payPrice").text(allPrice);
								   $("#usedPoint").val(0);
							   }else{
								   $this.attr("isOpen","true");
								   $this.attr("class","pointBtn openAni open");
								   $this.find("i").attr("class","openInnerCircleAni");
								   isOpen=true;
								   $("#couponPrice").val((payMoney).toFixed(2));
								   $(".payPrice").text((payMoney).toFixed(2));
								   $("#usedPoint").val(usePoint);
							   }
						   });
					   }
				   }
			 var iswx5version  = '${(iswx5version)!"0"}' ; // 判断是否为微信环境
			 if(iswx5version !=undefined && iswx5version=='0')
			{
			   var paywexin  = $('#paywexin') ;
			   if(paywexin!=undefined)
			      paywexin.addClass('off') ;
			}

			   <#if errorCode?? && errorCode !=''>
		         var errormsg = "${(errorCode)?default("")}" ;
		         showTipMsg(errormsg);
			   </#if>
				var etime="${(endSecond)!""}";
				var sid="#ordertime";
				var timer = setInterval(function(){
					etime--;
					orderCdown(etime,sid);
				},980);
				orderCdown(etime,sid);

				/* $('.payta').click(function(){
					$(this).find('.cpayflag').attr('src','${rc.contextPath}/pages/images/select.png');
					$('.paytb').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
				});
				$('.paytb').click(function(){
					$(this).find('.cpayflag').attr('src','${rc.contextPath}/pages/images/select.png');
					$('.payta').find('.cpayflag').attr('src','${rc.contextPath}/pages/images/unselect.png');
				}); */


				$('#gotolistscId').click(function(){
				     document.form1.action="${rc.contextPath}/spcart/listsc";
				     document.form1.submit();
				});

				$('#topayId').click(function(){
				   $('.pronotice').hide();
				      var addressIdCard = $("#addressIdCard").text();
				      if( document.getElementById('addressIdCard')!=undefined && document.getElementById('addressIdCard')!=null    &&  addressIdCard =='')
				      {
				          // window.location.href="${rc.contextPath}/ads/toordermdfads/${(addressId)!"-1"}/${(ordertype)!"1"}/${(isBonded)!"0"}";
				          document.modifyidcardForm.action="${rc.contextPath}/ads/toordermdfads/${(addressId)!"-1"}/${(ordertype)!"1"}/${(isBonded)!"0"}";
				          document.modifyidcardForm.submit();
				          return ;
				      }
				      //微信支付和另两种方式不太一样，只能在微信中支付，支付宝在微信中也不能支付
				      var  paytype = $("#form1 input[name=paytype]").val();
				      if(paytype !=undefined && ( paytype=='2' || paytype=='1')  ) /*支付宝*/
				      {
				          document.form1.action="${rc.contextPath}/order/add";
				          document.form1.submit();
				          return ;

				      }else if(paytype !=undefined && paytype=='3')
					  {
					       var iswx5version = $("#iswx5version").val();
					       if(iswx5version==undefined || iswx5version =='0')
					        {
					           alert('请使用微信5.0以上版本才能支付!');
					           return ;
					        }
					      var requesturl ="${rc.contextPath}/order/addajax"
					      var requestdata = $("#form1").serializeJson2()  ;

					       // ajax请求
					       $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
						        var addoderstatus = msgjsonobj.addoderstatus ; // 定单新增时出错
						        var redirecturl = msgjsonobj.redirecturl ;
							       if(addoderstatus !=undefined && addoderstatus == '1')
							       {
							          showTipLoadingMsg();
								          var accountId = msgjsonobj.accountId ;
								          var orderIdList = msgjsonobj.orderIdList ;
								          var wxtopayurl = "${rc.contextPath}"+redirecturl; /* /order/topaywx/3 */
								          var wxtopayreqdata = "accountId="+accountId+"&orderIdList="+orderIdList  ;

								          $.ajaxquery({"url":wxtopayurl,"data":wxtopayreqdata,"success":function(msgjsonobj){
								                  $('.pronotice').hide();
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
												             alert("请重新选择支付方式");
												             return ;
												           }else if(errorCode!=undefined && errorCode=='4'){
												              alert("订单状态错误,请刷新!");
												              return ;
												           }
										           }else if(status !=undefined && status =='1')
										           {
										                  var obj  =  msgjsonobj.wxprv ;
										                  WeixinJSBridge.invoke('getBrandWCPayRequest',{
												                 "appId" : obj.appid,    //公众号名称，由商户传入
												                 "timeStamp":obj.timestamp,          //时间戳，自 1970 年以来的秒数
												                 "nonceStr" : obj.nonce_str, //随机串
												                 "package" : obj.packageValue,
												                 "signType" : obj.signType,          //微信签名方式:
												                 "paySign" : obj.sign //微信签名
												                 },function(res){
												                    if(res.err_msg == "get_brand_wcpay_request:ok"){
												                         // window.location.href=obj.sendUrl;
												                         // alert("支付成功!");
												                         // window.location.href="${rc.contextPath}/mycenter/show" ;
												                         window.location.href="${rc.contextPath}/order/pay/weixinreturnback/"+obj.orderIds;
												                     }else if(res.err_msg == "get_brand_wcpay_request:cancel")
												                     {
												                        // 支付过程中用户取消  定单过期
												                       // window.location.href="${rc.contextPath}/order/list/1" ;
												                       window.location.href="${rc.contextPath}/order/pay/aliwxpayfail/"+orderIdList ;
												                     }else{
                                                                        //alert(res.err_msg);
												                        alert("支付失败,请刷新页面!");
												                     }
												             });
										           }
                                                   else if(status !=undefined && status =='2')
												   {
                                                       window.location.href="${rc.contextPath}/order/pay/weixinreturnback/"+orderIdList;
												   }
								          }});
							       }else if(addoderstatus !=undefined && addoderstatus == '0')
							       {
							              var errorCode = msgjsonobj.errorCode ;
							              var errorMessage = msgjsonobj.errorMessage;
							              var redirectlocation = "${rc.contextPath}"+redirecturl;
							              if(errorCode !=undefined && errorCode == '7')
							              {
							                   window.location.href=redirectlocation ;
							              }else if (errorCode !=undefined && errorCode == '6') // 定单过期
							              {
							                 window.location.href=redirectlocation ;
							              }else if(errorCode !=undefined && errorCode == '5')
							              {
							                 showTipMsg("身份证不合法,请到收货地址中去修改!");
							              }else if(errorCode !=undefined && errorCode == '4') // 购物车过期
							              {
							                 window.location.href=redirectlocation ;
							              }else if(errorCode !=undefined && errorCode == '3') // 请选择收货地址
							              {
							                 showTipMsg("请先增加收货地址!");
							              }else if(errorCode !=undefined && ( errorCode == '2' ||errorCode == '1' )　　)
							              {
							                 window.location.href=redirectlocation ;
							              }else if(errorCode !=undefined && errorCode == '0')
							              {
							                 window.location.reload();
							              }else if(errorCode !=undefined && errorCode == '11')//不支持的配送地区
							              {
							            	  showTipMsg(errorMessage);
							              }
							       }

                           }});

					   }
					   /*else if(paytype !=undefined && paytype=='1')
					   {

					   }*/
					   /*滑动按钮*/

				});

			})
		</script>

		<form id="form1" name="form1" action="" method="post">
		<input type="hidden" id="ordertype" name="ordertype"  value="${(ordertype)!"1"}"/>
		<input type="hidden" id="confirmId" name="confirmId"  value="${(confirmId)!"0"}"/>
		<input type="hidden" id="addressId" name="addressId"  value="${(addressId)!"-1"}"/>
		<input type="hidden" id="isBonded"  name="isBonded"   value="${(isBonded)!"0"}"/>
		<input type="hidden" id="couponId"  name="couponId"   value="${(couponId)!0}"/>
		<input type="hidden" id="availablePoint"  name="availablePoint" value="${(availablePoint)!0}"/><!-- 用户可用积分 -->
		<input type="hidden" id="paytype"  name="paytype"   value="<#if iswx5version?exists && (iswx5version == '1')>3<#else>1</#if>"/><!-- 付款类型 -->
		<input type="hidden" id="totalPrice"  name="totalPrice"   value="${allTotalPrice!0.00}"/><!-- 订单总价 -->
		<input type="hidden" id="couponPrice"  name="couponPrice" value="${couponPrice!0.00}"/><!-- 订单总价- 减掉优惠券 -积分   =couponPrice的价格 -->
		<input type="hidden" id="usedPoint"  name="usedPoint"   value="${usedPoint!0.00}"/><!-- 使用的积分数量 -->
		</form>
		<input type="hidden" id="rccontextPath"  name="rccontextPath"   value="${rc.contextPath}"/>

<script>

/*
 wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${(appid)!"wx7849b287f9c51f82"}', // 必填，公众号的唯一标识
	    timestamp: ${(timestamp)!"0"}, // 必填，生成签名的时间戳
	    nonceStr: '${(nonceStr)!"0"}', // 必填，生成签名的随机串
	    signature: '${(signature)!"0"}',// 必填，签名，见附录1
	    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
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
