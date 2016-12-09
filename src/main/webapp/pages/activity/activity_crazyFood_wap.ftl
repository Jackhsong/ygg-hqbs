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
        .downloadBtn{width:4.9rem;height:.9rem;top:7.9rem;left:1.4rem;border:1px solid red;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main relative" id="main">
<header class="download">
        <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')" class="DownLoad" target="_self">
            <img src="${rc.contextPath}/pages/images/activity/activitySideBar.png"/>
        </a>
    </header>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_01.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_02.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_03.jpg"/>
    <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')" class="DownLoad" target="_self">
           <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_04.jpg"/>
        </a>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_05.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_06.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_07.jpg"/>
    <img src="${rc.contextPath}/pages/images/activity/activityCrazyFood/roundweb_08.jpg"/>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<div style="display: none"><script src="http://s11.cnzz.com/z_stat.php?id=1257959955&web_id=1257959955" language="JavaScript"></script></div>
</body>
</html>