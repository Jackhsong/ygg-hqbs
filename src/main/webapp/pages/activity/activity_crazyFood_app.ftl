<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <title>左岸城堡 - 幸运大转盘</title>
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
        .round{left: 2rem;top: .2rem;width: 74%;z-index: 2;}
        .btn{width: 2rem;left: 4.7rem;top: 2.2rem;z-index: 3;}
        .leftTimes{width: 3.5rem;top: 0;right:.5rem;}
        .leftTimes p{color: #fff;font-size: .3rem;width:100%;text-align:center;height: .55rem;line-height:.55rem;}
        .share{width: 100%;height: 0.7rem;top: 0.8rem;}
        .opc{width: 100%;height: 100%;background: url("${rc.contextPath}/pages/images/activity/activityCrazyFood/repeat.png") repeat;top: 0;display:none;left: 0;z-index: 5;position:fixed;}
        .greenBox{width: 80%;left: 10%;padding: 0.5rem 0;top: 3rem;position: absolute;}
        .close{width: 1rem;height: 1rem;right: 0rem;top: 0.5rem;}
        .close,.closed{z-index:100;}
        .greenBox .goodsImg{width: 75%;margin: 0 auto;margin-bottom: 0.35rem;}
        #gotOne p{color: #000;font-size: .35rem;text-align: center;line-height: 130%;width: 85%;margin: 0 auto;font-weight: bold;}
        #missed p{width: 80%;text-align:center;margin: 0 auto;line-height: 150%;color: #000;font-size: 0.35rem;font-weight: bold;}
        .moneyTit,.moneyTxt{color:#a43a00;font-size:.35rem;text-align:center;width:100%;}
        .moneyTit{top:3rem;left:0;}
        .moneyTxt{top:6.5rem;left:0;}
        .money{color:#ffeeeb;font-size:.6rem;width:3rem;text-align:center;top:4.6rem;left:1.8rem;font-weight:bold;}
        .money i{font-size:1.5rem;}
        .toIndex{width:4.9rem;height:.9rem;top:7.9rem;left:1.4rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main relative" id="main">
    <section class="opc">
        <div class="greenBox abso">
            <i class="close abso"></i>
           	<article class="xianjinquan relative hidden">
           		<p class="moneyTit abso">获得一张<i class="xjq">10</i>元无门槛现金券</p>
           		<span class="money abso"><i class="xjq">10</i>元</span>
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/xianjinquan.png"/>
           		<p class="moneyTxt abso">现金券已放入您的账户中！<br/>请注意查收！</p>
           		<a href="ggj://redirect/resource/home" class="toIndex abso"></a>
           	</article>
           	<article class="youhuiquan relative hidden">
           		<p class="moneyTit abso">获得一张满299减<i class="yhq">20</i>优惠券</p>
           		<span class="money abso"><i class="yhq">20</i>元</span>
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/youhuiquan.png"/>
           		<p class="moneyTxt abso">优惠券已放入您的账户中！<br/>请注意查收！</p>
           		<a href="ggj://redirect/resource/home" class="toIndex abso"></a>
           	</article>
           	<article class="gouwudai relative hidden">
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/gouwudai.png"/>
           		<a href="#" class="toIndex gwdLink abso"></a>
           	</article>
           	<article class="cishu relative hidden">
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/cishu.png"/>
           		<a class="toIndex closed abso"></a>
           	</article>
           	<article class="shangxian relative hidden">
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/shangxian.png"/>
           		<a class="toIndex closed abso"></a>
           	</article>
           	<article class="jifen relative hidden">
           		<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/jifen.png"/>
           		<a href="ggj://redirect/resource/home" class="toIndex abso"></a>
           	</article>
        </div>
    </section>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_01.jpg"/>
    <div class="relative">
        <article class="leftTimes abso">
            <p>你有<i id="count">${leftTimes!"0"}</i>次抽奖机会</p>
            <a class="share abso" href="ggj://open/resource/share"></a>
        </article>
        <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_02.jpg"/>
    </div>
    <div class="relative">
        <i class="round abso">
            <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundCircle.png"/>
        </i>
        <#if login>
			<a class="btn abso">
            	<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/button.png"/>
        	</a>
 		<#else>
			<a class="btn abso" href="ggj://alert/account/login">
            	<img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/button.png"/>
        	</a>
		</#if>
        <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_03.jpg"/>
    </div>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_04.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_05.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_06.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_07.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/round_08.jpg"/>
    <input type="hidden" id="accountId" value="${accountId}"/>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<script>
(function(){$(function(){b.init()});var b={init:function(){$(".btn,.round").on("click",function(){"0"!=$("#accountId").val()&&b.ajaxDoing()});$(".close,.closed").click(function(){$(".opc").hide();$(".opc .greenBox").hide();$(".opc .greenBox article").hide()})},turnRound:function(a){var c=3600-60*(a-1)-30;$(".round").css({transition:"none",transform:"rotate(0deg)"});$(".round").css({"-webkit-transition":"none","-webkit-transform":"rotate(0deg)"});$(".round").css({"-moz-transition":"none","-moz-transform":"rotate(0deg)"});
$(".round").css({"-ms-transition":"none","-ms-transform":"rotate(0deg)"});$(".round").css({"-o-transition":"none","-o-transform":"rotate(0deg)"});$(".btn,.round").unbind();var d=function(){var a,c=document.createElement("fakeelement"),b={transition:"transitionend",OTransition:"oTransitionEnd",MozTransition:"transitionend",WebkitTransition:"webkitTransitionEnd"};for(a in b)if(void 0!==c.style[a])return b[a]}();d&&$(".round").on(d,function(){$(".btn,.round").on("click",function(){"0"!=$("#accountId").val()&&
b.ajaxDoing()});switch(a){case 1:$(".greenBox").show();$(".xianjinquan").show();break;case 2:$(".greenBox").show();$(".jifen").show();break;case 3:$(".greenBox").show();$(".xianjinquan").show();break;case 4:$(".greenBox").show();$(".gouwudai").show();break;case 5:$(".greenBox").show(),$(".youhuiquan").show()}$(".opc").show()});setTimeout(function(){$(".round").css({transition:"transform 8s cubic-bezier(0, 0, 0.28, 1.0)",transform:"rotate("+c+"deg)"});$(".round").css({"-webkit-transition":"-webkit-transform 8s cubic-bezier(0, 0, 0.28, 1.0)",
"-webkit-transform":"rotate("+c+"deg)"});$(".round").css({"-moz-transition":"-moz-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-moz-transform":"rotate("+c+"deg)"});$(".round").css({"-ms-transition":"-ms-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-ms-transform":"rotate("+c+"deg)"});$(".round").css({"-o-transition":"-o-transform 8s cubic-bezier(0, 0, 0.28, 1.0)","-o-transform":"rotate("+c+"deg)"})},100)},ajaxDoing:function(){$.ajax({url:"${rc.contextPath}/activity/crazyFood/draw",dataType:"json",
type:"post",data:{accountId:$("#accountId").val()},success:function(a){0==a.status&&alert("\u7cfb\u7edf\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u76f8\u5173\u6280\u672f\u4eba\u5458\u3002");4==a.status&&($("#count").text(a.leftTimes),3==a.prize&&$(".xjq").text(a.reduce),4==a.prize&&$(".gwdLink").attr("href",a.url),b.turnRound(a.prize));2==a.status&&alert("\u6d3b\u52a8\u672a\u5f00\u59cb");3==a.status&&alert("\u6d3b\u52a8\u5df2\u7ed3\u675f");5==a.status&&($(".greenBox").show(),$(".cishu").show(),$(".opc").show());
6==a.status&&($(".greenBox").show(),$(".shangxian").show(),$(".opc").show())},timeout:3E3})}}})();
</script>
<div style="display: none"><script src="http://s11.cnzz.com/z_stat.php?id=1257959955&web_id=1257959955" language="JavaScript"></script></div>
</body>
</html>