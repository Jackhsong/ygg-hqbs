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
    <#include "../common/couponsHeader.ftl">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<div class="page">
	<div class="protips"></div>
	<div class="opcBox">
	    <section class="tipBox">
	        <ul>
	            <li>
	                <input type="text" id="couponsCode" placeholder="请输入优惠券码兑换优惠券"/>
	            </li>
	            <li>
	                <a class="cancel">取消</a>
	                <a class="toExchange" id="toExchange">立刻兑换</a>
	            </li>
	        </ul>
	    </section>
    </div>
    <nav class="tit">
                     我的优惠券
        <a href="javascript:history.back();" class="tit_la"  id="gotolistscId"><img src="${rc.contextPath}/pages/images/cancle.png" class="tit_center"></a>
        <a id="exchange" class="tit_cart">兑换</a>
    </nav>
    <section>
        <nav class="tabTit">
            <div>
                <a>未使用</a>
                <a>已使用</a>
                <a>已过期</a>
                <i id="activeLine"></i>
            </div>
        </nav>
        <div class="noCoupons" id="noCoupons">
            <img src="${rc.contextPath}/pages/images/coupons/noConpons.png"/>
            <i>您还没有优惠券</i>
        </div>
        <div class="contentOverflow" id="haveCoupons">
            <div class="content clear" id="content">
                <ul id="notUsed" class="list fl"></ul>
                <ul id="used" class="list fl"></ul>
                <ul id="passed" class="list fl"></ul>
            </div>
        </div>
    </section>
</div>
<script>
    (function(){
        $(function(){
            var isHave01=getData(1,$(".list").eq(0));//获取未使用 的优惠券数据
            var isHave02=getData(2,$(".list").eq(1));//获取已使用 的优惠券数据
            var isHave03=getData(3,$(".list").eq(2));//获取已过期 的优惠券数据
            if(isHave01+isHave02+isHave03==0){//判断是否有优惠券包括未使用  已使用  已过期
            	$("#noCoupons").show();
            	$("#haveCoupons").hide();
            }else{
            	$("#haveCoupons").show();
            	$("#noCoupons").hide();
            }
            var wWid=window.screen.availWidth;
            if(isUc()){
            	wWid=document.body.offsetWidth;
            }
            if(wWid>750){
            	wWid=548;
            	$(".content").width(($(".list").width()+10)*3+55+"px");//设置 .content宽度
            }else{
            	$(".content").width(($(".list").width()+10)*3+30+"px");//设置 .content宽度
            }
            $(".list").width(wWid*0.95+"px");//设置.list的宽度
            exchange();
            setTimeout(function(){
            	tab();//优惠券类型切换
            },800);
            tips();//弹窗兑换优惠券
            
        });
        function tips(){
            $("#exchange").click(function(){
                $(".opcBox").show();
            });
            $(".cancel").click(function(){
                $(".opcBox").hide();
                $("#couponsCode").val("");
            });
        }
        function tab(){
        	$(".content").height($(".list").eq(0).height()+"px");
            var i=0,
                goWid=($(".list").width()+parseInt($(".list").css("margin-right"))),
                titGoWid=($(".tabTit a").eq(0).width()+parseInt($(".tabTit a").eq(0).css("margin-left")))+15;
            $(".tabTit a").on("click",function(){
                var $this=$(this),index=$this.index();
                i=index;
                go(index);
            });
            var hammertime = new Hammer(document.querySelector(".content"));
            //为该dom元素指定触屏移动事件
            hammertime.on("swipe", function (ev) {
            	var len=$(".list").length;
                if(ev.deltaX>0){
                	i--;
                	if(i<0){
                        i=0;
                    }
                    go(i);
                }else{
                	i++;
                	if(i>=len){
                        i=len-1;
                    }
                    go(i);
                }
                //控制台输出
            });
            function go(z){
                var CTX=z*goWid;
                var TX=z*titGoWid;
                $("#activeLine").css({"transform":"translateX("+TX+"px)"});
                $("#activeLine").css({"-webkit-transform":"translateX("+TX+"px)"});
                $("#activeLine").css({"-moz-transform":"translateX("+TX+"px)"});
                $("#activeLine").css({"-ms-transform":"translateX("+TX+"px)"});
                $("#activeLine").css({"-o-transform":"translateX("+TX+"px)"});
                if($("body").width()>750){
                	$(".content").animate({"margin-left":-(CTX-10)+"px"},"fast",function(){
                		$(".content").height($(".list").eq(z).height()+"px");
                	});
                	
                }else{
                    $(".content").css({"transform":"translateX(-"+CTX+"px)"});
                    $(".content").css({"-webkit-transform":"translateX(-"+CTX+"px)"});
                    $(".content").css({"-moz-transform":"translateX(-"+CTX+"px)"});
                    $(".content").css({"-ms-transform":"translateX(-"+CTX+"px)"});
                    $(".content").css({"-o-transform":"translateX(-"+CTX+"px)"});
                    setTimeout(function(){
                    	$(".content").height($(".list").eq(z).height()+"px");
                    },300)
                    
                }
            }
        }
        function exchange(){
        	$("#toExchange").click(function(e){
        		var code=$("#couponsCode").val();
        		if(code!=""){
        			$.ajax({
        				url:"${rc.contextPath}/coupon/codeChangeCoupon",
        				dataType:"json",
        				data:{code:code},
        				success:function(data){
        					if(data.status=="1"){
        						$(".opcBox").hide();
        						showTipMsg("兑换成功！~");
        						setTimeout(function(){
        							window.location.reload();
        						},1000);
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
        //ajax data
        function getData(type,dom){
            $.ajax({
                url:"${rc.contextPath}/coupon/couponList",
                dataType:"json",
                data:{type:type},
                success:function(data){
                    if(parseInt(data.status)==1){
                        var str="";
                        if(data.coupons.length>0){
                        	for(var i=0;i<data.coupons.length;i++){
                                var d=data.coupons[i];
                                var className=d.isAvailable=='0'?'noAllowUse':'';
                                if(type==1){//未使用数据
                                	if(d.type=="1"){//满减
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>满'+d.thresholdPrice+'元使用</dt>' +
                                        '<dd class="'+className+'">有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：'+d.scope+'</dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}else if(d.type=="2"){//现金券
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>现金券</dt>' +
                                        '<dd class="'+className+'">有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：'+d.scope+'</dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}
                                }else if(type==2){//已使用数据
                                	if(d.type=="1"){//满减
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>满'+d.thresholdPrice+'元使用</dt>' +
                                        '<dd>有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：'+d.scope+'</dd>' +
                                        '<dd class="passIcon"></dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}else if(d.type=="2"){//现金券
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>现金券</dt>' +
                                        '<dd>有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：'+d.scope+'</dd>' +
                                        '<dd class="passIcon"></dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}
                                }else if(type==3){//已过期数据
                                	if(d.type=="1"){//满减
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>满'+d.thresholdPrice+'元使用</dt>' +
                                        '<dd>有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：全场通用</dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}else if(d.type=="2"){
                                		str+='<li class="clear">' +
                                        '<span class="fl amount">' +
                                        '<i>'+d.reducePrice+'</i><b>元</b>' +
                                        '</span>' +
                                        '<dl class="fl detail">' +
                                        '<dt>现金券</dt>' +
                                        '<dd>有效期：'+d.startTime+' 至 '+d.endTime+'</dd>' +
                                        '<dd>使用范围：'+d.scope+'</dd>' +
                                        '</dl>' +
                                        '</li>';	
                                	}
                                    
                                }else {
                                    console.log("参数错误");
                                }
                            }
                        }else{
                        	if(type==1){
                        		var text="您还没有未使用的优惠券";
                        	}
                        	if(type==2){
                        		var text="您还没有已使用的优惠券";
                        	}
                        	if(type==3){
                        		var text="您还没有已过期的优惠券";
                        	}
                        	str+='<li class="noCoupons"><img src="${rc.contextPath}/pages/images/coupons/noConpons.png"/><i>'+text+'</i></li>'
                        }
                        dom.html(str);
                        return 0;
                    }else{
                        console.log(data.msg);
                    }
                },
                timeout:3000
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
        	$('.protips').css('top',"20%").css("z-index","9999");
        	$('.protips').css('display','block');
        	setTimeout(function(){			
        		$('.protips').css('display','none');
        	},2000);
        }
        function isUc(){
        	var ua=navigator.userAgent.toLowerCase();
    		if(ua.indexOf("ucbrowser")>-1){
    			return true;
    		}else{
    			return false;
    		}
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