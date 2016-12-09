<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>送你一份左岸城堡1~100元随机红包，让我沾沾你的好手气！</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;transition:all 0s linear;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body,html{background: #ee4444;}
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
        .text{width: 5rem;margin: 0 auto;}
        .btn{display: block;width: 8rem;margin: .5rem auto 0 auto;}
        .textWord{color: #fff3c9;font-size: .4rem;text-align: center;width: 6rem;margin: 0 auto;}
        .textWord1{font-size: .35rem;line-height: 150%;margin-top: .1rem;}
        .textWord2{line-height: 200%;margin-top: .3rem;}
        .rule{margin-top: .3rem;}
        .listWrap{background: #fff;}
        .list{width: 95%;margin: 0 auto;}
        .list li{border-bottom: 1px solid #e0e0e0;padding: .3rem 0;}
        .list li:last-child{border-bottom: none;}
        .list li i{width: 1rem;height: 1rem;background: #c9c9c9;border-radius: 100%;overflow: hidden;}
        .list li i img{width: 1rem;height: 1rem;}
        .list li b{color: #ee4444;font-size: .4rem;margin-left: .4rem;width: 3rem;height: 0.6rem;overflow: hidden;}
        .list li time{color: #999999;font-size: .32rem;}
        .list li strong{color: #ee4444;display:block;font-size: .4rem;text-align: center;font-weight: normal;}
        .list li em{display: block;font-size: .35rem; margin-top: .1rem;color: #ffc601;padding-left: 0.5rem;background: url("${rc.contextPath}/pages/images/activity/packet/web/bestIcon.png") no-repeat left 0.07rem;background-size: 0.4rem;font-family: 'Arial',"microsoft yahei";}
        .list li span{width: 2.2rem;}
        .list li span,.list li time,.list li b{margin-top: .3rem;line-height:150%;}
        .list li time{margin-top: .32rem;}
        .noShare{color:#623700;text-align: center;line-height:200%;font-size:0.4rem;padding-bottom:0.4rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main relative" id="main">
	<#if isShare=="0">
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img01.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img02.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img03.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img04.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/text.png" class="text"/>
     <#if isLogin=="0">
     	<a href="ggj://alert/account/login" class="btn"><img src="${rc.contextPath}/pages/images/activity/packet/web/btn.png"/></a>
     </#if>
     <#if isLogin=="1">
   		<a href="ggj://open/resource/share" class="btn"><img src="${rc.contextPath}/pages/images/activity/packet/web/btn.png"/></a>
     </#if>
    <img src="${rc.contextPath}/pages/images/activity/packet/web/rules.png" class="rule"/>
    <img src="${rc.contextPath}/pages/images/activity/packet/web/img05.jpg"/>
    </#if>
    <#if isShare=="1">
    <img src="${rc.contextPath}/pages/images/activity/packet/web/img01.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img02.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img03.jpg"/>
     <img src="${rc.contextPath}/pages/images/activity/packet/web/img04.jpg"/>
     <a class="btn" href="ggj://open/resource/share"><img src="${rc.contextPath}/pages/images/activity/packet/web/btn.png"/></a>
     <#if isSuccess=="1">
     	<p class="textWord textWord1">已满10人抽红包，金额最高为：${maxReduce}元，${maxReduce}元现金券红包已放入您账户中。</p>
     </#if>
     <#if isSuccess=="0">
     	<p class="textWord textWord2">当前已领取人数：${drawNums}人</p>
     </#if>
    <img src="${rc.contextPath}/pages/images/activity/packet/web/rules.png" class="rule"/>
    <img src="${rc.contextPath}/pages/images/activity/packet/web/img05.jpg"/>
    <section class="listWrap">
        <ul class="list">
        <#if drawRecord?? && (drawRecord?size>0)>
        	<#list drawRecord as list>
        		<#if list_index==0>
		            <li class="box">
		                <i class="fl"><img src="${list.userImage}"/></i>
		                <b class="fl">${list.username}</b>
		                <time class="fl">${list.createTime}</time>
		                <span class="fr relative">
		                    <strong>${list.reduce}</strong>
		                    <em>手气最佳</em>
		                </span>
		            </li>
		       	 <#else>
		             <li class="box">
		                <i class="fl"><img src="${list.userImage}"/></i>
		                <b class="fl">${list.username}</b>
		                <time class="fl">${list.createTime}</time>
		                <span class="fr relative">
		                    <strong>${list.reduce}</strong>
		                </span>
	            	</li>
	            </#if>
            </#list>
            <#else>
            	<li><p class="noShare">还没有闺蜜领取你的红包哦~快去派红包，坐收渔翁利！</p></li>
         </#if>
       </ul>
    </section>
    </#if>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
</body>
</html>