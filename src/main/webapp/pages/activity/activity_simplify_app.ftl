<!DOCTYPE>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
    <title>${activity.title}</title>
    <style>
        html{background: #ffeee4;}
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;padding-bottom:1.3rem;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0px;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 750px;margin: 0 auto;position: relative;}
        .main img{display: block;width: 100%;}
        ul{width: 96%;margin: 0 auto;margin-top: .35rem;}
        li{background: #fff;margin-bottom: 0.3rem;border-radius: 3px;width: 9.6rem;padding: .2rem 0 .35rem 0;}
        .price{width: 95%;margin: 0.3rem auto 0 auto;display:block;}
        .price .oldPrice{margin-top: 0.275rem;}
        .img{height:9.1rem;width:95%;margin:0 auto;overflow: hidden;display: block;background:url('${rc.contextPath}/pages/images/activity/holder_logo.png') no-repeat center center #d7d9db;background-size:50%;}
        .img img{display: inline-block;height:100%;}
        .nowPrice{color: #ff3300;font-size: 0.7rem;margin: .2rem 0.2rem 0 -0.2rem;}
        .oldPrice{color: #999999;font-size: 0.45rem;text-decoration: line-through;}
        .tit{color: #212121;font-size: 0.55rem;width: 95%;display: block;font-size: 0.43rem;line-height: 130%;margin: 0.1rem auto 0.1rem auto;}
        .desc{display:block;color:#505050;font-size: 0.35rem;line-height: 130%;width: 95%;margin: 0 auto;margin-bottom:.3rem;}
        .toTop{position: fixed;display:none;right: 0.05rem;bottom:0;background: url("${rc.contextPath}/pages/images/activity/toTop.png") center no-repeat;width: 1.5rem; height: 1.5rem;background-size: 80%;z-index: 300;}
        .see{background: #ff3e00;border-radius: 3px;text-align: center;color: #fff;line-height: 150%;font-size: .45rem;padding: .1rem .5rem;}
        .statusIcon{width: 100%;text-align:center;top: 0;left: 0;height: 100%;}
        .statusIcon img{width: 30%;display:inline-block;margin-top:3rem;height:auto;vertical-align: middle;}
        .goods{position:relative;z-index:5;}
        @media screen and (max-width: 1024px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main">
    <section class="banner" id="banner">
        <img src="${activity.image}"/>
    </section>
    <section class="goods">
        <ul>
        	<#if activity.productList??>
        	<#list activity.productList as list>
            <li>
                <a href="${list.url}">
                    <b class="tit">${list.title}</b>
                    <p class="desc">${list.desc}</p>
                    <i class="img relative">
                    	<#if list.status!="1">
	                        <b class="abso statusIcon">
	                        	<#if list.status=="3">
	                            	<img src="${rc.contextPath}/pages/images/activity/startIcon.png"/>
	                            </#if>
	                            <#if list.status=="4">
	                            	<img src="${rc.contextPath}/pages/images/activity/overIcon.png"/>
	                            </#if>
	                        </b>
                        </#if>
                        <img class="imglazyload" data-original="${list.image}"/>
                    </i>
                    <span class="price box">
                        <i class="nowPrice fl">￥${list.salesPrice}</i>
                        <i class="oldPrice fl">￥${list.marketPrice}&nbsp;</i>
                        <i class="see fr">查看详情</i>
                    </span>
                </a>
            </li>
            </#list>
            </#if>
        </ul>
    </section>
</div>
<a class="toTop" id="toTop"></a>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<script>
    $(function(){
        events.init();
    });
    var events={
        init:function(){
            $(".imglazyload").picLazyLoad({
                threshold: 800,
                placeholder: ''
            });
            this.toTop();
        },
        toTop:function(){
            $(".toTop").on("click",function(){
                //$("body").scrollTop(0); 
            	$(".main").TopTo();
            });
            $(window).on("scroll",function(){
                var wH=$(window).height(),wTop=$(window).scrollTop();
                if(wTop>=wH){
                    $(".toTop").show();
                }
                if(wTop<wH){
                    $(".toTop").hide();
                }
            });
        }
    }
</script>
</body>
</html>