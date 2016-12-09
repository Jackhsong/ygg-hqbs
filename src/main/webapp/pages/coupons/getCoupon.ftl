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
    <#include "../common/getCouponHeader.ftl">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<div class="page">
	<div class="protips"></div>
    <nav class="tit">
                     使用优惠券
        <a class="tit_la goBack01"  id="gotolistscId">取消</a>
        <a class="tit_ra goBack02">确认</a>
    </nav>
    <section>
    		<input type="hidden" value="${cancelCouponId!0}" id="cancelCouponId"/>
        	<#if availableCouponDetails?? && (availableCouponDetails?size > 0) >
	        <!-- 可用优惠券 -->
	        <header class="titleBox">
	        	<h4>可用优惠券</h4>
	        </header>
	        <ul class="list" id="allowUse">
        	<#list availableCouponDetails as list>
	        	<li class="clear <#if list.selected=='1'>checked<#else>unCheck</#if>" choiced="<#if list.selected=='1'>true<#else>false</#if>"  id="${list.id}">
					<span class="fl amount">
						<i>${list.reducePrice}</i>
						<b>元</b>
					</span>
	                <dl class="fl detail">
	                	<#if list.type=="1">
	                    	<dt>满${list.thresholdPrice}元使用</dt>
	                    <#else>
	                    	<dt>现金券</dt>
	                    </#if>
						<dd>
							有效期：${list.startTime}至 ${list.endTime}
						</dd>
	                    <dd>使用范围：${list.scope}</dd>
	                    <dd class="radio">
	                    	<i></i>
	                    </dd>
	                </dl>
	             </li>
             </#list>
        </ul>
        </#if>
        	<#if unAvailableCouponDetails??  && (unAvailableCouponDetails?size > 0) >
        	<!-- 暂时不可用优惠券 -->
	        <header class="titleBox">
	        	<h4>暂时不可用优惠券</h4>
	        </header>
	        <ul class="list">
        	<#list unAvailableCouponDetails as list>
	        	<li class="clear" id="${list.id}">
					<span class="fl amount">
						<i>${list.reducePrice}</i>
						<b>元</b>
					</span>
	                <dl class="fl detail">
	                    <#if list.type=="1">
	                    	<dt>满${list.thresholdPrice}元使用</dt>
	                    <#else>
	                    	<dt>现金券</dt>
	                    </#if>
						<dd class="noAllowUse">
							有效期：${list.startTime}至 ${list.endTime}
						</dd>
	                    <dd>使用范围：${list.scope}</dd>
	                    
	                </dl>
	             </li>
             </#list>
        </ul>
        </#if>
        <!-- 兑换优惠券 -->
        <header class="titleBox">
        	<h4>兑换优惠券</h4>
        </header>
        <form class="clear getCodeForm">
        	<input type="text" class="fl" placeholder="请输入优惠券码" id="couponsCode"/>
        	<a class="toExchange fr" id="toExchange">立即兑换</a>
        </form>
    </section>
</div>
<script>
    (function(){
        $(function(){
            choiceConpon();
            exchange();
        });
       	function choiceConpon(){//确认使用 优惠券
       		var defaultCoupon=$(".checked").attr("id") || 0;
       		var cancelCouponId=$("#cancelCouponId").val();
       		$(".goBack01").attr("href","${rc.contextPath}/order/selectCoupon/${(ordertype)!1}/"+cancelCouponId+"");
       		$(".goBack02").attr("href","${rc.contextPath}/order/selectCoupon/${(ordertype)!1}/"+defaultCoupon+"");
       		$("#allowUse li").each(function(){
       			var $this=$(this);
       			$this.click(function(){
       				var isChoiced=$this.attr("choiced");
       				if(isChoiced=="false"){
       					$(this).addClass("checked").removeClass("unCheck").siblings().removeClass("checked").addClass("unCheck");
       					$(this).attr("choiced","true").siblings().attr("choiced","false");
       					var cid=$(this).attr("id");
                    	$(".goBack02").attr("href","${rc.contextPath}/order/selectCoupon/${(ordertype)!1}/"+cid+"");
       				}else{
       					$(this).addClass("unCheck").removeClass("checked");
       					$(this).attr("choiced","false");
       					$(".goBack02").removeAttr("href");
       				}
                });	
       		});
       		$(".goBack02").click(function(){
       			if(!$(this).attr("href")){
       				window.location.href="${rc.contextPath}/order/selectCoupon/${(ordertype)!1}/0";
       			}
       		});
       		
       	}
       	function exchange(){//兑换优惠券
       		$("#toExchange").click(function(e){
        		var code=$("#couponsCode").val();
        		if(code!=""){
        			$.ajax({
        				url:"${rc.contextPath}/coupon/codeChangeCoupon",
        				dataType:"json",
        				data:{code:code},
        				success:function(data){
        					if(data.status=="1"){
        						window.location.reload();
        					}else{
        						showTipMsg(data.msg);
        						$("#couponsCode").val("");
        					}
        				},
        				timeout:3000
        			});
        		}else{
        			showTipMsg("亲，请输入优惠券编码哦~");
        		}
        	});
       	}
        function showTipMsg(msg)
        {
        	 /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
            /* 弹出层 */
        	$('.protips').html(msg);
        	var scrollTop=$(document).scrollTop();
        	var windowTop=$(window).height();
        	var xtop=windowTop/2+scrollTop;
        	$('.protips').css('top',"50%");
        	$('.protips').css('display','block');
        	setTimeout(function(){			
        		$('.protips').css('display','none');
        	},2000);
        }
    }());

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