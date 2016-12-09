<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <title>左岸城堡疯狂抽美食</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;transition:all 0s linear;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body,html{background: #ffd245;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0px;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        .hidden{display: none;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 750px;margin: 0 auto;position: relative;}
        .main img{display: block;width: 100%;}
        .opc{width: 100%;height: 100%;background: url("${rc.contextPath}/pages/images/activity/activityCrazyFood/repeat.png") repeat;top: 0;left: 0;z-index: 5;display: none;}
        .greenBox{width: 80%;left: 10%;background: #96dcd2;border-radius: 5px;border: 3px solid #000000;padding: 0.5rem 0;top: 3rem;position: fixed;}
        .close{width: 1rem;height: 1rem;right: -0.5rem;top: -0.5rem;background: url("${rc.contextPath}/pages/images/activity/activityCrazyFood/close.png") no-repeat;background-size: 100%;}
        .greenBox .goodsImg{width: 75%;margin: 0 auto;margin-bottom: 0.35rem;}
        #gotOne p{color: #000;font-size: .35rem;text-align: center;line-height: 130%;width: 85%;margin: 0 auto;font-weight: bold;}
        #missed p{width: 80%;text-align:center;margin: 0 auto;line-height: 150%;color: #000;font-size: 0.35rem;font-weight: bold;}
        .shareBtn{width: 5rem;height: 1rem;background: url("${rc.contextPath}/pages/images/activity/activityCrazyFood/opcBtn.png") no-repeat;background-size: 100%;font-size:.45rem;color: #fff;font-weight: bold;text-align: center;line-height: 200%;display: block;margin: 0 auto;margin-top: .3rem;}
        #phoneNum{width:6rem;top:.42rem;left:2rem;color: #000000;text-align: center;font-size: .4rem;line-height: 200%;font-family:'microsoft yahei';background: #fff;border: 2px solid #000000;border-radius: 5px;}
        #phoneNum::-webkit-input-placeholder { /* WebKit browsers */
            color: #808080;
        }
        #phoneNum:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: #808080;
        }
        #phoneNum::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #808080;
        }
        #phoneNum:-ms-input-placeholder { /* Internet Explorer 10+ */
            color: #808080;
        }
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        .round{width: 7.4rem;top: 0.8rem;left: 1.3rem;}
        .round img{width:100%;}
        .btn{width: 2rem;top: 3rem;left: 4rem;}
        .leftTimes{font-size: .38rem;color: #f13e53;left: 4rem;top: 0.3rem;font-family: '黑体';}
        .quphone{position:relative;display:none;}
        .quphone span{width:2.2rem;height:1rem;right:.4rem;top:.5rem;}
    </style>
</head>
<body>
<div class="main relative" id="main">
	<input type="hidden" id="activityId" value="${activityId!'0'}"/>
    <header class="head relative" id="head">
        <div class="quphone">
            <span class="abso"></span>
            <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')">
                <img src="${rc.contextPath}/pages/images/activity/sidebar.png">
            </a>
        </div>
    </header>
    <section class="opc abso">
        <div class="greenBox hidden abso" id="gotOne">
            <i class="close abso"></i>
            <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/p02.png" id="goodsImg" class="goodsImg"/>
            <p id="resultTxt"></p>
            <a class="shareBtn" href="http://download.gegejia.com">下载左岸城堡APP</a>
        </div>
        <div class="greenBox abso" id="missed">
            <i class="close abso"></i>
            <div id="missText">
            </div>
            <a class="shareBtn" href="http://download.gegejia.com">下载左岸城堡APP</a>
        </div>
    </section>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap01.png"/>
    <div class="relative">
        <input type="tel" id="phoneNum" class="abso" placeholder="输入手机号参加抽奖" />
        <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap02.png"/>
    </div>
    <div class="relative">
        <i class="round abso">
            <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwround.png"/>
        </i>
        <a class="btn abso">
            <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/w11.png"/>
        </a>
        <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap03.png"/>
    </div>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap04.png"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap05.png"/>
    <div class="relative">
        <p class="leftTimes abso">剩余抽奖次数：<i id="count">1</i>次</p>
        <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap06.png"/>
    </div>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cwap07.png"/>
    </div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/zepto.min.js"></script>
    <script>
    (function(){function d(b){var c;return(c=document.cookie.match(new RegExp("(^| )"+b+"=([^;]*)(;|$)")))?c[2]:null}$(function(){f.init();var b=d("ggjquxiazai");1!=parseInt(b)&&$(".quphone").show();$(".quphone span").click(function(){if(null==d("ggjquxiazai")){var b=new Date;b.setTime(b.getTime()+2592E5);document.cookie="ggjquxiazai="+escape("1")+";expires="+b.toGMTString();$(".quphone").css({height:"0",paddingTop:"0"})}$(".quphone").hide()})});var f={init:function(){$("#phoneNum").focus(function(){$("#count").text("1")});
    $(".btn,.round").on("click",function(){var b=$("#phoneNum").val(),c=$("#activityId").val();""==b?($("#missText").html("<p>\u8bf7\u8f93\u5165\u624b\u673a\u53f7\u7801.</p>"),$(".shareBtn").hide(),$("#missed").show(),$("#gotOne").hide(),$(".opc").show()):/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.test(b.replace(" ",""))?f.ajaxDoing(b,c):($("#missText").html("<p>\u4eb2\uff0c\u4f60\u786e\u5b9a\u8fd9\u662f\u624b\u673a\u53f7\u5417\uff1f</p>"),$(".shareBtn").hide(),$("#missed").show(),$("#gotOne").hide(),
    $(".opc").show())});$(".close").click(function(){$(".opc").hide()})},turnRound:function(b,c){var a=-1;switch(b){case 1:a=1;break;case 2:a=2;break;case 3:a=3;break;case 4:a=4;break;case 5:a=5;break;case 6:a=6;break;default:$("#missText").html("<p>\u53c2\u6570\u4f20\u9012\u9519\u8bef</p>"),$(".shareBtn").hide(),$("#missed").show(),$("#gotOne").hide(),$(".opc").show()}var e=3600-60*(a-1);$(".round").css({transition:"none",transform:"rotate(0deg)"});$(".round").css({"-webkit-transition":"none","-webkit-transform":"rotate(0deg)"});
    $(".round").css({"-moz-transition":"none","-moz-transform":"rotate(0deg)"});$(".round").css({"-ms-transition":"none","-ms-transform":"rotate(0deg)"});$(".round").css({"-o-transition":"none","-o-transform":"rotate(0deg)"});$(".btn,.round").unbind();var d=function(){var a,b=document.createElement("fakeelement"),c={transition:"transitionend",OTransition:"oTransitionEnd",MozTransition:"transitionend",WebkitTransition:"webkitTransitionEnd"};for(a in c)if(void 0!==b.style[a])return c[a]}();d&&$(".round").on(d,
    function(){$(".btn,.round").on("click",function(){var a=$("#phoneNum").val(),b=$("#activityId").val();""==a?($("#missText").html("<p>\u8bf7\u8f93\u5165\u624b\u673a\u53f7\u7801.</p>"),$(".shareBtn").hide(),$("#missed").show(),$("#gotOne").hide(),$(".opc").show()):/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.test(a.replace(" ",""))?f.ajaxDoing(a,b):($("#missText").html("<p>\u4eb2\uff0c\u4f60\u786e\u5b9a\u8fd9\u662f\u624b\u673a\u53f7\u5417\uff1f</p>"),$(".shareBtn").hide(),$("#missed").show(),
    $("#gotOne").hide(),$(".opc").show())});var b="\u53f0\u6e7e\u534e\u5143\u771f\u9c7f\u5473\u7ea2\u70e7\u53e3\u5473 \u53f0\u6e7e\u534e\u5143\u771f\u9c7f\u5473\u9999\u8fa3\u53e3\u5473 5\u5143\u65e0\u95e8\u69db\u4f18\u60e0\u5238 \u97e9\u56fdGilim\u8702\u871c\u9ec4\u6cb9\u674f\u4ec1 \u8c22\u8c22\u53c2\u4e0e \u6ee188\u51cf10\u5143\u4f18\u60e0\u5238".split(" ");if(5!=a){if(1==a||2==a||4==a)$("#goodsImg").attr("src","${rc.contextPath}/pages/images/activity/activityCrazyFood/cp0"+a+".png"),$("#resultTxt").html('\u54c7\uff01\u4f60\u62bd\u4e2d\u4e86<i id="goodsName">'+
    b[a-1]+"</i>\u4e00\u4efd\uff01<br/>\u51ed\u6b64\u9875\u9762\u5411\u73b0\u573a\u5de5\u4f5c\u4eba\u5458\u9886\u53d6\u7f8e\u98df\u4e00\u4efd\uff0c<br/>\u4e0b\u8f7d\u683c\u683c\u5bb6APP\uff0c\u66f4\u591a\u7f8e\u98df\u7b49\u7740\u4f60\uff01"),$("#gotOne").show(),$("#missed").hide();if(3==a||6==a)$("#goodsImg").attr("src","${rc.contextPath}/pages/images/activity/activityCrazyFood/cp0"+a+".png"),$("#resultTxt").html('\u54c7\uff01\u4f60\u62bd\u4e2d\u4e86<i id="goodsName">'+b[a-1]+"</i>\u4e00\u5f20\uff01<br/>\u4e0b\u8f7d\u683c\u683c\u5bb6APP\u5e76\u4f7f\u7528\u62bd\u5956\u624b\u673a\u53f7\u767b\u9646\uff0c<br/>\u5373\u53ef\u4f7f\u7528\u4f18\u60e0\u5238\u8d2d\u4e70\u5168\u7403\u7f8e\u98df\u54e6"),
    $("#gotOne").show(),$("#missed").hide()}else $("#missText").html("<p>\u54ce\u5440\uff0c\u4e00\u4e2a\u7f8e\u98df\u6e9c\u8d70\u4e86~</p><p>\u4e0b\u8f7d\u683c\u683c\u5bb6APP\uff0c\u66f4\u591a\u7f8e\u98df\u7b49\u7740\u4f60~</p>"),$(".shareBtn").show(),$("#missed").show(),$("#gotOne").hide();$(".opc").show()});setTimeout(function(){$(".round").css({transition:"transform 8s cubic-bezier(0, 0, 0.28, 1.0)",transform:"rotate("+e+"deg)"});$(".round").css({"-webkit-transition":"-webkit-transform 8s cubic-bezier(0, 0, 0.28, 1.0)",
    "-webkit-transform":"rotate("+e+"deg)"});$(".round").css({"-moz-transition":"-moz-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-moz-transform":"rotate("+e+"deg)"});$(".round").css({"-ms-transition":"-ms-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-ms-transform":"rotate("+e+"deg)"});$(".round").css({"-o-transition":"-o-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-o-transform":"rotate("+e+"deg)"})},100)},ajaxDoing:function(b,c){$.ajax({url:"${rc.contextPath}/activity/crazyFood/draw",dataType:"json",
    data:{phoneNo:b,activityId:c},type:"post",success:function(a){if(0==parseInt(a.status))return $("#missText").html("<p>"+a.msg+"</p>"),$(".shareBtn").hide(),$("#missed").show(),$("#gotOne").hide(),$(".opc").show(),!1;$("#count").text(0);f.turnRound(a.prize,1)},timeout:3E3})}}})();
    </script>
    
<script>
	wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${(appid)!"wx7849b287f9c51f82"}', // 必填，公众号的唯一标识
		    timestamp: ${(timestamp)!"0"}, // 必填，生成签名的时间戳
		    nonceStr: '${(nonceStr)!"0"}', // 必填，生成签名的随机串
		    signature: '${(signature)!"0"}',// 必填，签名，见附录1
		    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
/* wx.error(function(res){
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		    alert(res.errMsg);
		});
*/
	wx.ready(function () {
		
			 // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		     wx.onMenuShareTimeline({
		         title: '${name!""}', // 分享标题
		         link: '${(link)!"0"}',
		         imgUrl: '${(imgUrl)!""}' // 分享图标
		     });
		     
		     // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			  wx.onMenuShareAppMessage({
		      title: '${name!""}',
		      desc: '${wxShareDesc!""}',
		      link: '${(link)!"0"}',
		      imgUrl: '${(imgUrl)!""}'
	});
});
</script>
</body>
</html>