<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <title>格格派我发红包啦！</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body,html{background: #fff5e8;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        .hidden{display: none;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 750px;margin: 0 auto;position: relative;overflow:hidden;}
        .main img{width: 100%;display: block;margin: 0 auto;}
        .userInfo{width: 77%;left: 10%;top:1rem;}
        .userImgWrap{width: 1.4rem;height: 1.4rem;border-radius: 100%;border: 2px solid #fff;}
        .paopaoWrap{width: 77%;margin-left: .1rem;}
        .paopaoWrap .paopaoWord{width: 5.3rem;color: #33383b;font-size: 0.35rem;left: 0.45rem;top: 0.25rem;line-height:130%;}
        .tipsWord{width: 100%;text-align: center;left: 0.3rem;top: -.5rem;color: #622e0b;font-size: 0.45rem;}
        .userImgWrap img{width: 1.4rem;height: 1.4rem;border-radius: 100%;}
        .packet{height: 5rem;}
        .opcWrap{position: fixed;background: rgba(0,0,0,0.4);left: 0;top: 0;width: 100%;height: 100%;z-index: 10;display: none;opacity: 0;
            transition:opacity 0.3s ease-in 0s;-webkit-transition:opacity 0.3s ease-in 0s;-moz-transition:opacity 0.3s ease-in 0s;-ms-transition:opacity 0.3s ease-in 0s;-o-transition:opacity 0.3s ease-in 0s;
        }
        .showBox{width: 70%;height:3.5rem;left: 15%;top: 20%;background: #fff;border-radius: 5px;padding: .8rem 0;}
        .showBoxScale0{transform:scale(0);-webkit-transform:scale(0);-moz-transform:scale(0);-ms-transform:scale(0);-o-transform:scale(0);transition:transform 0.2s linear 0s;-webkit-transition:-webkit-transform 0.2s linear 0s;-moz-transition:-moz-transform 0.2s linear 0s;-ms-transition:-ms-transform 0.2s linear 0s;-o-transition:-o-transform 0.2s linear 0s;}
        .showBoxScale1{transform:scale(1);-webkit-transform:scale(1);-moz-transform:scale(1);-ms-transform:scale(1);-o-transform:scale(1);transition:transform 0.2s linear 0s;-webkit-transition:-webkit-transform 0.2s linear 0s;-moz-transition:-moz-transform 0.2s linear 0s;-ms-transition:-ms-transform 0.2s linear 0s;-o-transition:-o-transform 0.2s linear 0s;}
        .showBox .title{color: #000000;font-size: .4rem;text-align: center;width: 80%;margin:0 auto; line-height: 150%;}
        .phoneNum{-webkit-appearance: none;-moz-appearance: none;-ms-appearance: none;color: #000;width: 80%;border-radius:3px;display: block;margin: 0 auto;margin-top:0.3rem;border:1px solid #f52c3c;height:.8rem;line-height:.8rem;text-align: center;font-size: .4rem;}
        .phoneNum:focus,.subBtn:focus{outline: none;}
        .phoneNum::-webkit-outer-spin-button,
        .phoneNum::-webkit-inner-spin-button{-webkit-appearance: none !important;margin: 0;}
        input[type="number"]{-moz-appearance:textfield;}
        .phoneNum::-webkit-input-placeholder{color: #7a7a7a;font-family: "microsoft yahei";}
        .phoneNum::-moz-placeholder{color: #7a7a7a;font-family: "microsoft yahei";}
        .phoneNum:-ms-input-placeholder{color: #7a7a7a;font-family: "microsoft yahei";}
        .subBtn{width: 80%;appearance: button; -moz-appearance:button; /* Firefox */ -webkit-appearance:button; border-radius: 3px;display: block;margin: 0 auto;margin-top: 0.25rem;background: none;background-color: #f52c3c;color: #fff;text-align: center;border: none;font-size: 0.4rem;height: .8rem;line-height: .8rem;}
        .close{width: .5rem;height: .5rem;top: .2rem;right: .2rem;}
        .getBtn,.getBtn2,.useBtn{width: 3.6rem;height: 1.1rem;left:3.2rem;}
        .phoneTips{color: #ffffd5;left: 2rem;top: 1.2rem;}
        .phoneTips i{color: #fffb65;}
        .money{color: #fa7905;font-size: 1.8rem;font-family: "Impact";left:3rem;top: 1.7rem; width: 3rem;text-align: center;}
        .money i{font-size: .8rem;font-family: "microsoft yahei";}
        .userList{margin-top:5rem;background: #fff;}
        .userListImgWrap,.userListImgWrap img{width: 1.2rem;height: 1.2rem;border-radius: 100%;}
        .userListNameWrap{width: 6rem;margin-left: 0.2rem;margin-top: 0.1rem;}
        .userListName,.userListTime{display: block;}
        .userListName{color: #333333;font-size: .4rem;line-height: 150%;}
        .userListTime{color: #7a7a7a;font-size: .3rem;}
        .userList .money{color: #e0142d;font-family: 'microsoft yahei';font-size: .4rem;text-align: right;margin-top: .4rem;}
        .userListMoneyWrap{margin-top: .35rem;}
        .userList ul{width: 90%;margin: 0 auto;margin-top: 0.4rem;padding-bottom: 0.5rem;}
        .userList ul li{margin-bottom: 0.4rem;}
        .userList dl{width: 7.7rem;}
		.takeLookBtn{width:3.5rem;height:1rem;left:3.2rem;top:0;}
		.got .tipsWord{top:-.7rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main relative" id="main">
    <section class="opcWrap">
        <div class="showBox abso showBoxScale0">
            <i class="close abso"><img src="${rc.contextPath}/pages/images/orderGift/close.png"/></i>
            <p class="title">领取到的现金券将发放到您的手机账户中!</p>
            <input type="number" class="phoneNum" placeholder="请输入手机号码"/>
            <input type="button" class="subBtn" value="立即领取"/>
        </div>
    </section>
    <header class="head">
        <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')">
            <img src="${rc.contextPath}/pages/images/orderGift/download.jpg"/>
        </a>
    </header>
    <section>
        <div class="relative">
            <dl class="abso box userInfo">
                <dt class="userImgWrap fl">
                    <img src="${shareAccountImage}" id="userimg"/>
                </dt>
                <dd class="fl relative paopaoWrap">
                    <img src="${rc.contextPath}/pages/images/orderGift/paopao.png" class="paopao"/>
                    <p class="paopaoWord abso">我刚刚在左岸城堡全球美食买了一堆好吃的，还领到了<i id="totalNo"></i>个红包，分享给你，快来领取吧！</p>
                </dd>
            </dl>
            <img src="${rc.contextPath}/pages/images/orderGift/packetimg01.jpg"/>
        </div>
        <div class="relative packet">
            <section class="no abso hidden">
                <p class="tipsWord abso hidden">“你还没有领取红包哦~”</p>
                <img src="${rc.contextPath}/pages/images/orderGift/noimg01.jpg"/>
                <div class="relative">
                    <a class="getBtn abso"></a>
                    <img src="${rc.contextPath}/pages/images/orderGift/noimg02.jpg"/>
                </div>
                <img src="${rc.contextPath}/pages/images/orderGift/noimg03.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/noimg04.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/noimg05.jpg"/>
            </section>
            <section class="phone abso hidden">
                <p class="tipsWord abso hidden">“你还没有领取红包哦~”</p>
                <img src="${rc.contextPath}/pages/images/orderGift/phoneimg01.jpg"/>
                <div class="relative">
                    <a class="getBtn2 abso"></a>
                    <p class="phoneTips abso">现金券将放入左岸城堡账户:<i class="updatePhone"></i> <b class="updateBtn">修改 ></b></p>
                    <img src="${rc.contextPath}/pages/images/orderGift/phoneimg02.jpg"/>
                </div>
                <img src="${rc.contextPath}/pages/images/orderGift/phoneimg03.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/phoneimg04.jpg"/>
            </section>
            <section class="got abso hidden">
                <p class="tipsWord abso hidden">“红包已经被领完啦~”</p>
                <img src="${rc.contextPath}/pages/images/orderGift/gotimg01.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/gotimg02.jpg"/>
                <div class="relative">
                	<a class="takeLookBtn abso" href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')">
                		
                	</a>
                		<img src="${rc.contextPath}/pages/images/orderGift/gotimg03.jpg"/>
                	
                </div>
                <img src="${rc.contextPath}/pages/images/orderGift/gotimg04.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/gotimg05.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/gotimg06.jpg"/>
            </section>
            <section class="over abso hidden">
                <p class="tipsWord abso hidden">“你已经领取过红包啦~”</p>
                <div class="relative">
                    <b class="money abso hidden"><i>￥</i><em class="num"></em></b>
                    <img src="${rc.contextPath}/pages/images/orderGift/overimg01.jpg"/>
                    <img src="${rc.contextPath}/pages/images/orderGift/overimg02.jpg"/>
                </div>
                <div class="relative">
                    <i class="useBtn abso" onclick="window.open('http://download.gegejia.com')"></i>
                   <img src="${rc.contextPath}/pages/images/orderGift/overimg03.jpg"/>
                </div>
                <img src="${rc.contextPath}/pages/images/orderGift/overimg04.jpg"/>
                <img src="${rc.contextPath}/pages/images/orderGift/overimg05.jpg"/>
            </section>
            <img src="${rc.contextPath}/pages/images/orderGift/apppacket02.jpg"/>
        </div>
    </section>
    <#if recordList?exists && (recordList?size>0 ) >
    <section class="userList">
        <img src="${rc.contextPath}/pages/images/orderGift/fenge.jpg" class="fenge"/>
        <ul>
        <#list recordList as list>
            <li class="box">
                <i class="userListImgWrap fl"><img src="${list.headImage}"/></i>
                <dl class="box fl">
                    <dt class="fl userListNameWrap">
                        <i class="userListName">${list.username}</i>
                        <time class="userListTime">${list.createTime}</time>
                    </dt>
                    <dd class="fr userListMoneyWrap"><b class="money">${list.reducePrice}元</b></dd>
                </dl>
            </li>
            </#list>
        </ul>
    </section>
    </#if>
    <input  type="hidden" id="leftNum" value="${leftNum!''}"/>
    <input type="hidden" id="totalNum" value="${totalNum!''}"/>
    <input  type="hidden" id="isBindingMobile" value="${isBindingMobile!''}"/>
    <input  type="hidden" id="receiveMobile" value="${receiveMobile!''}"/>
    <input  type="hidden" id="isReceive" value="${isReceive!''}"/>
    <input  type="hidden" id="reducePrice" value="${reducePrice!''}"/>
    <input  type="hidden" id="path" value="${rc.contextPath}"/>
    <input type="hidden" id="giftId" value="${giftId}"/>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="http://m.gegejia.com/ygg/pages/js/zepto-activity.min.js"></script>
<script>
    $(function(){
        init();
    });
    function init(){
        var isReceive=$("#isReceive").val();
        var leftNum=$("#leftNum").val();
        var isBindingMobile=$("#isBindingMobile").val();
        var totalNo=$("#totalNum").val();
        $("#totalNo").text(totalNo);
        if(isReceive=="0"){//未领取
            if(leftNum!="0"){
                if(isBindingMobile=="0"){
                    $(".no").show().siblings().hide();
//                    $(".packet").height($(".no").height()+"px");
					setTimeout(function(){
                    	$(".no .tipsWord").show();
                    },1500)
                }else{
                    var receiveMobile=$("#receiveMobile").val();
                    $(".updatePhone").text(receiveMobile);
                    $(".phone").show().siblings().hide();
                    setTimeout(function(){
                    	$(".phone .tipsWord,.phone .money").show();
                    },1500)
//                    $(".packet").height($(".phone").height()+"px");
                }
            }else{
                $(".got").show().siblings().hide();
//                $(".packet").height($(".got").height()+"px");
				   setTimeout(function(){
                    	$(".got .tipsWord").show();
                    },1500)
            }
        }else{
            var reducePrice=$("#reducePrice").val();
            $(".num").text(reducePrice);
            $(".over").show().siblings().hide();
//            $(".packet").height($(".over").height()+"px");
			setTimeout(function(){
                    	$(".over .tipsWord,.over .money").show();
            },1500);
        }
        $(".close").click(function(){
            $(".opcWrap").css("opacity","0");
            $(".showBox").addClass("showBoxScale0").removeClass("showBoxScale1");
            setTimeout(function(){
                $(".opcWrap").hide();
            },300);
        });
        getCoupon();
        useCoupon();
    }
    function useCoupon(){
    	/*$(".useBtn").click(function () {
            var me = $(this);
            var url = me.attr("ggj-url"),
                href = me.attr("down-url");
            mobileAppInstall.open(url, href);
            return false;
        });*/
    }
    function getChromeIntent(b){return"intent://"+b.split(":")[1]+"#Intent;scheme=gegejia;package=com.yggAndroid;end"}var mobileAppInstall=(function(){var e=navigator.userAgent.toLowerCase(),d=window;var f={isUc:e.indexOf("ucbrowser")>-1,isAndroid:e.indexOf("android")>-1||e.indexOf("linux")>-1,isIos:e.indexOf("iphone")>-1,isIos9:e.indexOf("os 9")>-1,isIos7:e.indexOf("os 7")>-1,isIpad:e.indexOf("ipad")>-1,isDefault:e.indexOf("applewebkit")>-1&&e.indexOf("chrome")>-1,timeout:500,isInstall:false,open:function(c,a){if(f.isIos9||f.isIos7){f.timeout=1500}var b=Date.now();f.openApp(c,a);setTimeout(function(){if(f.isIos9||f.isIos7){window.location.href=a}if(Date.now()-b<f.timeout+100&&!f.isIos9&&!f.isIos7){a&&f.openH5(a)}},f.timeout)},openApp:function(c,b){if(f.isDefault&&!f.isIos){if(f.isAndroid){d.location.href=getChromeIntent(c)}else{d.location.href=c}}else{if(!f.isUc){d.location.href=c}else{var a=document.createElement("iframe");a.setAttribute("src",c);a.setAttribute("style","display:none");document.body.appendChild(a)}}},openH5:function(a){d.open(a,"_blank")}};return f})();
    function getCoupon(){
        $(".getBtn").click(function(){
            $(".opcWrap").show();
            setTimeout(function(){
                $(".opcWrap").css("opacity","1");
                setTimeout(function(){
                    $(".showBox").addClass("showBoxScale1").removeClass("showBoxScale0");
                },200);
            },300);
        });
        $(".updateBtn").click(function(){
            $(".opcWrap").show();
            setTimeout(function(){
                $(".opcWrap").css("opacity","1");
                setTimeout(function(){
                    $(".showBox").addClass("showBoxScale1").removeClass("showBoxScale0");
                },300);
            },300);

            $(".subBtn").val("确认修改").unbind();
            $(".subBtn").click(function(){
                var phone=$(".phoneNum").val(),$this=$(this);
                
                var isPhone=phone!=""?$.phoneTest.test(phone)==false?"手机号码格式不正确，请重新输入~":true:"请输入手机号码~";
                if(isPhone==true){
                	$this.prop("disabled","true").css("opacity","0.7");
                    updatePhone(phone,function(){
                        $this.removeAttr("disabled").css("opacity","1");
                        $(".updatePhone").text(phone);
                        $(".opcWrap").css("opacity","0");
                        $(".showBox").addClass("showBoxScale0").removeClass("showBoxScale1");
                        setTimeout(function(){
                            $(".opcWrap").hide();
                        },300);
                    });
                }else{
                	$this.removeAttr("disabled").css("opacity","1");
                    alert(isPhone);
                }
            });
        });

        $(".subBtn").click(function(){
            var phone=$(".phoneNum").val(),$this=$(this);
            var isPhone=phone!=""?$.phoneTest.test(phone)==false?"手机号码格式不正确，请重新输入~":true:"请输入手机号码~";
            if(isPhone==true){
                $this.prop("disabled","true").css("opacity","0.7");
                subData(phone,function(money,flag){
                    $this.removeAttr("disabled").css("opacity","1");
                    $(".opcWrap").css("opacity","0");
                    $(".showBox").addClass("showBoxScale0").removeClass("showBoxScale1");
                    setTimeout(function(){
                        $(".opcWrap").hide();
                    },300);
                    //if(!flag){
                    	window.location.reload();	
                    //}
                    
                    //$(".over").show().siblings().hide();
//                    $(".packet").height($(".over").height()+"px");
                    //$(".num").text(money);
                });
            }else{
            	$this.removeAttr("disabled").css("opacity","1");
                alert(isPhone);
            }
        });

        $(".getBtn2").click(function(){
            var phone=$(".updatePhone").text();
            subData(phone,function(money){
                //$(".over").show().siblings().hide();
//                $(".packet").height($(".over").height()+"px");
                //$(".num").text(money);
                window.location.reload();
            });
        })
    }
    function subData(phone,cb){
        var basePath=$("#path").val();
        var giftId=$("#giftId").val();
    	$.ajax({
			type:"get",
			dataType:"json",
			url:basePath+"/orderGiftShare/draw",
			data:{mobileNumber:phone,giftId:giftId},
			success:function(data){
				if(data.status==1){
					var result=data.result;
					if(result.status==1){
						alert("领取成功~");
						cb(result.reducePrice);
					}else{
						if(result.status==0){
							if(result.errorCode==0){
								alert("未知错误，请联系相关技术人员~^-^");
								cb();
							}
							if(result.errorCode==1){
								alert("手机号码格式错误~请重新输入~");
								
							}
							if(result.errorCode==2){
								alert("此手机号码已经领过红包啦~！");
								//$(".over").show().siblings().hide();
								//cb();
								$(".subBtn").removeAttr("disabled").css("opacity","1");
							}
							if(result.errorCode==3){
								alert("下手不够快，红包已经被抢完了");
								$(".got").show().siblings().hide();
								
							}
							if(result.errorCode==4){
								alert("不能领取自己发出去的红包哦，分享给小伙伴吧");
								setTimeout(function(){
									cb();
									},2000);
								
							}
						}
					}
				}else{
					alert("系统出错~请联系相关技术人员~^-^");
				}
			}
        });
    }
    function updatePhone(phone,cb){
    	var basePath=$("#path").val();
        $.ajax({
        	type:"get",
			dataType:"json",
			url:basePath+"/orderGiftShare/modifyMobilePhone",
			data:{mobileNumber:phone},
			success:function(data){
				if(data.status==1){
					if(data.result.status==1){
						alert("修改成功~");	
						cb();
					}else{
						alert("未知错误，请联系相关技术人员~^-^");
					}
				}else{
					alert("系统出错~请联系相关技术人员~^-^");
				}
			}
        });
    }
</script>
</body>
</html>