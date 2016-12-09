<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>任意门，一起穿越吧！</title>
    <script>
        //淘宝H5自适应解决方案
        //https://github.com/amfe/lib-flexible
        (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
    </script>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;background: #fffdf2;line-height: 150%;height: 100%;overflow: hidden;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body{background: #969696;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0px;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 39%;margin: 0 auto;height: 100%;overflow: hidden;}
        .main img{display: block;width: 100%;}
        #texts dt{text-align: center;font-size: 0.5rem;line-height: 180%;margin: 0.1rem 0;}
        #texts dd{font-size: 0.3rem;font-family: '宋体';line-height: 130%;}
        .content{width: 80%;margin: 4rem auto 0 auto;background: #f94c50;border-radius: 15px;padding: 10px;}
        .content .inner{background: #ffebec;border-radius: 8px;}
        #opc{display: none;width: 100%;height: 100%;background: url("${rc.contextPath}/pages/images/gateImage/repeat.png") repeat;top: 0;left: 0;}
        #close{width: 1.5rem;height: 1.5rem;background: #f74d50;border-radius: 100%;right: -0.6rem;top: -0.6rem;background-image: url('${rc.contextPath}/pages/images/gateImage/close.png');background-position: center center;background-size: 50%;background-repeat: no-repeat;}
        .rules{width: 1.8rem;height: 0.6rem;right: 0.7rem;top: 2rem;}
        .time{color: #e9563a;font-weight: bold;font-size: 0.4rem;left: 35%;top: 1.2rem;}
        .toPage{width: 2rem;height: 2.5rem;right: 1.7rem;top: 0.6rem;}
        .ruleText{width: 95%;padding: 0.4rem 0 0.8rem 0;margin: 0 auto;}
        .ruleText dt{color: #ad161b;text-align: center;font-size: 0.6rem;line-height: 200%;}
        .ruleText dd{color: #c72c30;font-size: 0.4rem;margin-top: 0.2rem;line-height: 150%;}
        .btn{width: 3rem;height: 1rem;background: url("${rc.contextPath}/pages/images/gateImage/btn.png") no-repeat center center;background-size: 100%;left: 34%;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main">
    <div class="relative">
        <a class="rules abso" id="btn"></a>
        <img src="${rc.contextPath}/pages/images/gateImage/img09.jpg"/>
    </div>
    <img src="${rc.contextPath}/pages/images/gateImage/img10.jpg"/>
    <div class="relative">
        <#if nextTime??>
        <span class="abso btn"></span>
        <span class="abso time">${nextTime}</span>
        </#if>
        <img src="${rc.contextPath}/pages/images/gateImage/img11.jpg"/>
    </div>
    <div class="relative">
        <a href="http://download.gegejia.com" target="_blank" class="abso toPage"></a>
        <img src="${rc.contextPath}/pages/images/gateImage/img12.jpg"/>
    </div>
    <img src="${rc.contextPath}/pages/images/gateImage/img13.jpg"/>
    <div class="abso" id="opc">
        <div class="content relative">
            <i class="abso" id="close"></i>
            <div class="inner">
                <dl class="ruleText">
                     <dt>任意门规则</dt>
                     <dd>1、任意门日常开启时间为每周六、周日，共两天。特殊活动期间会不定期随活动一同开启。</dd>
                     <dd>2、每天一个任意门口令，根据提示猜口令。</dd>
                     <dd>3、未猜中可以一直猜直到猜中为止，也可邀请请好友帮忙一起猜。</dd>
                     <dd>4、猜中即可打开任意门获得随机金额的现金券奖励。</dd>
                </dl>
            </div>
        </div>
    </div>
</div>
<script src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<script>
    $(function(){
        $("#close").click(function(){
            $("#opc").hide("fast");
        });
        $("#btn").click(function(){
            $("#opc").show("fast").height("150%");
        });
    });
</script>
</body>
</html>