<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
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
    	html{background:#f0f0f0;}
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;padding-bottom:1.3rem;background:#f0f0f0;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body{background: #f0f0f0;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0px;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        .quphone{position:relative;display:none;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 750px;margin: 0 auto;position: relative;}
        .main img{display: block;width: 100%;}
        .banner{background: #fff;border-bottom: 1px solid #d6d6d6;}
        .banner img{height:6.5rem;}
        .banner article{width: 95%;padding: 0.3rem 0 0.5rem 0;margin:  0 auto;}
        .banner article p{text-indent: 2em;color: #505050;font-size: 0.4rem;line-height: 150%;}
        .repeatModel{margin-top: .7rem;border-top: 1px solid #ff3e00;position: relative;z-index: 5;}
        .repeatModel header{background: #fff;border-bottom: 1px solid #e1e1e1;margin-bottom: 0.2rem;}
        .repeatModel header .tit{color: #ff3e00;text-align: center;height:1rem;line-height:1rem;border-radius: 3px;border: 1px solid #ff3e00;background: #fff;margin:-0.6rem 0 0 2.2rem;font-size: 0.45rem;width: 5.5rem;font-weight:bold;}
        .repeatModel header .desc{color: #505050;font-size: 0.4rem;width: 95%;margin: 0.3rem 0 0.6rem 0.3rem;line-height: 150%;}
        .repeatModel li{background: #fff;margin-bottom: 0.1rem;padding-bottom: 0.2rem;border-radius: 3px;}
        .repeatModel .tit{color: #000000;font-size: 0.45rem;line-height: 150%;width: 95%;margin: 0 auto;display: block;margin-top:.1rem;}
        .repeatModel .desc{color: #505050;display:block;font-size: 0.4rem;line-height: 150%;width: 95%;margin-left:0.18rem;display: block;}
        .repeatModel .price{width: 95%;margin: 0 auto;display:block;}
        .repeatModel .price i{margin-top: 0.3rem;}
        .repeatModel .price .oldPrice{margin-top: 0.35rem;}
        .repeatModel .img{height:4.69rem;overflow: hidden;display: block;border-top-left-radius: 3px;border-top-right-radius: 3px;position:relative;}
        .repeatModel .img img{display: inline-block;border-top-left-radius: 3px;border-top-right-radius: 3px;height:4.7rem;}
        .repeatModel .nowPrice{color: #ff3300;font-size: 0.5rem;margin: 0 0.2rem 0 0;font-family:"Arial"}
        .repeatModel .oldPrice{color: #999999;font-size: 0.4rem;text-decoration: line-through;font-family:"Arial"}
        .buyCar{width: 0.8rem;height: 0.8rem;background: url("${rc.contextPath}/pages/images/special/img03.jpg") no-repeat;background-size: 100%;position: relative;}
        .repeatModel .double{background: none;padding-bottom: 0;}
        .repeatModel .double .price{width:94%;margin:0 0 0 0.2rem;}
        .repeatModel .double .tit{font-size: 0.38rem;line-height: 0.53rem;margin-top: 0.1rem;margin-bottom: 0.1rem;margin-left:0.17rem;height: 1rem;overflow: hidden;}
        .repeatModel .double .desc{display:block;font-size: 0.35rem;line-height: 130%;height: 1.35rem;overflow: hidden;}
        .repeatModel .left,.repeatModel .right{width: 48.5%;background: #fff;border-radius: 3px;height:8.5rem;overflow:hidden;}
        .repeatModel .left{margin:0 1%;}
        #navWrap{width:100%;}
        .navList{width: 100%;background: #f0f0f0;border-top: 1px solid #d2d2d2;margin:0 auto;}
        .navList ul{border-left: 1px solid #d2d2d2;width: 100%;}
        .navList li{text-align: center;line-height: 200%;border-bottom: 1px solid #d2d2d2;border-right: 1px solid #d2d2d2;font-size: 0.4rem;}
        .navList .normal i{color: #3c3c3c;}
        .navList .active i{color: #ff264f;}
        .navList .li02_04{width: 49.7%;}
        .navList .li03_06{width: 33.05%;}
        .head{background:#f26255;}
        .head .return{width:0.7rem;height: 0.7rem;background: url("${rc.contextPath}/pages/images/special/img05.png") no-repeat;background-size: 100%;top: 0.3rem;left: 0.5rem;}
        .head h4{text-align: center;color: #f4f4f4;font-size: 0.55rem;padding: 0.4rem 0;}
        .ids{width: 100%;height: 1rem;top: -3.5rem;z-index: 1;display: block;opacity: 0;}
        .toTop{position: fixed;display:none;right: 0.05rem;bottom:0;background: url("${rc.contextPath}/pages/images/special/totop.png") center no-repeat;width: 1.5rem; height: 1.5rem;background-size: 100%;z-index: 300;}
        .single{border-radius: 3px;}
        .single .buyLink{width: 50%;background: url("${rc.contextPath}/pages/images/special/buyLink.png") no-repeat;background-size: 100%;height: 1rem;}
        .double .buyLink{width: 80%;margin: 0 auto;display: block;background: url("${rc.contextPath}/pages/images/special/buyLink.png") no-repeat;background-size: 100%;height: 0.7rem;margin-top: 0.4rem;}
        .quphone span{width:2.2rem;height:1rem;right:.4rem;top:.5rem;}
        .single .img{height:9.5rem;}
        .single .img,.double .img,.banner img{background:url('${rc.contextPath}/pages/images/special/holder_logo.png') no-repeat center center #d7d9db;background-size:50%;}
        .single .img img{height:9.5rem;}
        .statusIcon{width:100%;top:0;left:0;height:100%;text-align:center;line-height:100%;}
        .single .statusIcon{line-height:9.5rem;}
        .double .statusIcon{line-height:4.7rem;}
        .single .statusIcon img,.double .statusIcon img{width:35%;display:inline-block;vertical-align:middle;height:auto;}
        .double .statusIcon img{width:40%;}
        .single .price .buyCar{width:0.9rem;height:0.9rem;margin-top:0.1rem;}
        .double .left .buyCar,.double .right .buyCar{margin-top:0.15rem;}
        
        @media screen and (max-width: 1024px) {
            .main{width:100%;}
        }
        @media screen and (device-aspect-ratio: 40/71) {
        	.navList .li02_04{width: 49.6%;}
            .navList .li03_06{width: 33%;}
            .repeatModel .price .oldPrice{margin-top: 0.3rem;}
            .repeatModel .nowPrice{margin-right:0.1rem;}
        }
    </style>
</head>
<body>
<div class="main">
<div id="topHeight">
    <header class="head relative" id="head">
        <!-- <a class="abso return" href="#"></a> -->
        <!-- <h4></h4> -->
        <div class="quphone">
        	<span class="abso"></span>
			<a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')">
				<img src="${rc.contextPath}/pages/images/special/sidebar.png">
			</a>
		</div>
    </header>
    <nav class="nav" id="nav">
    	<div id="navWrap">
        <ul class="navList box">
        <#if activity.categoryList?? && ((activity.categoryList?size == 2) || (activity.categoryList?size == 4)) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
	            	<li class="fl li02_04 active"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li02_04 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        <#if activity.categoryList?? && ((activity.categoryList?size == 3)||(activity.categoryList?size == 6)) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
        			<li class="fl li03_06 active"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        <#if activity.categoryList?? && (activity.categoryList?size == 5) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
        			<li class="fl li03_06 active"><i>${(tit)!""}</i></li>
	            <#elseif tit_index < 3>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li02_04 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        <#if activity.categoryList?? && (activity.categoryList?size == 7) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
        			<li class="fl li03_06 active"><i>${(tit)!""}</i></li>
	            <#elseif tit_index < 3>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li02_04 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        <#if activity.categoryList?? && (activity.categoryList?size == 8) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
        			<li class="fl li03_06 active"><i>${(tit)!""}</i></li>
	            <#elseif tit_index<6>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li02_04 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        <#if activity.categoryList?? && (activity.categoryList?size == 9) >
        	<#list activity.categoryList as tit>
        		<#if tit_index==0>
        			<li class="fl li03_06 active"><i>${(tit)!""}</i></li>
	            <#elseif tit_index < 3>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            <#else>
	            	<li class="fl li03_06 normal"><i>${(tit)!""}</i></li>
	            </#if>
            </#list>
        </#if>
        </ul>
        </div>
    </nav>
    <section class="banner" id="banner">
        <img src="${activity.image}"/>
        <article>
            <p>${activity.desc}</p>
        </article>
    </section>
</div>
<div class="repeatBox">
<#list activity.layoutList as list>
<section class="repeatModel" id="r0${list_index+1}">
    <header class="box">
        <h2 class="tit fl">${list.title}</h2>
        <p class="desc fl">${list.desc}</p>
    </header>
    <ul class="box">
    <#list list.productList as list2>
    	<#if list2.displayType==1>
    		<li class="single">
	            <a class="img" href="${list2.url}">
	            	<#if list2.type==1 && list2.status!="1">
		            	<i class="statusIcon abso">
		            		<#if list2.status=="3">
		            			<img src="${rc.contextPath}/pages/images/special/startIcon.png"/>
		            		</#if>
		            		<#if list2.status=="4">
		            			<img src="${rc.contextPath}/pages/images/special/overIcon.png"/>
		            		</#if>
		            	</i>
		            </#if>
	                <img class="imglazyload" data-original="${list2.image}"/>
	            </a>
	            <a href="${list2.url}" class="tit">${list2.name}</a>
	            <a href="${list2.url}" class="desc">${list2.desc}</a>
	            <a href="${list2.url}" class="price box">
		            <#if list2.type==1>
		                <i class="nowPrice fl">¥${list2.salesPrice}</i>
		                <i class="oldPrice fl">¥${list2.marketPrice}</i>
		                <#if list2.status=="1">
		                	<i class="buyCar fr"></i>
		            	</#if>
		            </#if>
		            <#if list2.type==2>
		                <i href="${list2.url}" class="buyLink fr"></i>
		            </#if>
	            </a>
        	</li>
    	</#if>
    	<#if list2.displayType==2>
        <li class="double box">
            <div class="left fl">
                <a class="img" href="${list2.left.url}">
                	<#if list2.left.type==1 && list2.left.status!="1">
		            	<i class="statusIcon abso">
		            		<#if list2.left.status=="3">
		            			<img src="${rc.contextPath}/pages/images/special/startIcon.png"/>
		            		</#if>
		            		<#if list2.left.status=="4">
		            			<img src="${rc.contextPath}/pages/images/special/overIcon.png"/>
		            		</#if>
		            	</i>
		            </#if>
                    <img class="imglazyload" data-original="${list2.left.image}"/>
                </a>
                <a href="${list2.left.url}" class="tit">${list2.left.name}</a>
                <a href="${list2.left.url}" class="desc">${list2.left.desc}</a>
                <a href="${list2.left.url}"  class="price box">
                	<#if list2.left.type==1>
	                    <i class="nowPrice fl">¥${list2.left.salesPrice}</i>
	                    <i class="oldPrice fl">¥${list2.left.marketPrice}</i>
	                    <#if list2.left.status=="1">
	                    	<i class="buyCar fr"></i>	
	                    </#if>
                    </#if>
                    <#if list2.left.type==2>
                    	<i class="buyLink"></i>
                    </#if>
                </a>
            </div>
            <div class="right fl">
            	<#if list2.right.url?exists && (list2.right.url!='')>
                <a class="img" href="${list2.right.url}">
                	<#if list2.right.type==1 && list2.right.status!="1">
		            	<i class="statusIcon abso">
		            		<#if list2.right.status=="3">
		            			<img src="${rc.contextPath}/pages/images/special/startIcon.png"/>
		            		</#if>
		            		<#if list2.right.status=="4">
		            			<img src="${rc.contextPath}/pages/images/special/overIcon.png"/>
		            		</#if>
		            	</i>
		            </#if>
                    <img class="imglazyload" data-original="${list2.right.image}"/>
                </a>
                <a href="${list2.right.url!'#'}" class="tit">${list2.right.name!""} </a>
                <a href="${list2.right.url!'#'}" class="desc">${list2.right.desc!""}</a>
                <a href="${list2.right.url}" class="price box">
                	<#if list2.right.type==1>
	                    <i class="nowPrice fl">¥${list2.right.salesPrice}</i>
	                    <i class="oldPrice fl">¥${list2.right.marketPrice}</i>
	                    <#if list2.right.status=="1">
	                    	<i class="buyCar fr"></i>	
	                    </#if>
                    </#if>
                    <#if list2.right.type==2>
                    	<i class="buyLink"></i>
                    </#if>
                </a>
                </#if>
            </div>
        </li>
        </#if>
      </#list>  
    </ul>
</section>
</#list>
</div>
<a class="toTop" id="toTop"></a>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
        (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
<script>
;(function($){
    $.fn.picLazyLoad = function(settings){
        var $this = $(this),
            _winScrollTop = 0,
            _winHeight = $(window).height();

        settings = $.extend({
            threshold: 0, // 提前高度加载
            placeholder: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC'
        }, settings||{});

        // 执行懒加载图片
        lazyLoadPic();

        // 滚动触发换图
        $(window).on('scroll',function(){
            _winScrollTop = $(window).scrollTop();
            lazyLoadPic();
        });
        // 懒加载图片
        function lazyLoadPic(){
            $this.each(function(){
                var $self = $(this);
                // 如果是img
                if($self.is('img')){
                    if($self.attr('data-original')){
                        var _offsetTop = $self.offset().top;
                        if((_offsetTop - settings.threshold) <= (_winHeight + _winScrollTop)){
                            $self.attr('src',$self.attr('data-original'));
                            $self.removeAttr('data-original');
                        }
                    }
                    // 如果是背景图
                }else{
                    if($self.attr('data-original')){
                        // 默认占位图片
                        if($self.css('background-image') == 'none'){
                            $self.css('background-image','url('+settings.placeholder+')');
                        }
                        var _offsetTop = $self.offset().top;
                        if((_offsetTop - settings.threshold) <= (_winHeight + _winScrollTop)){
                            $self.css('background-image','url('+$self.attr('data-original')+')');
                            $self.removeAttr('data-original');
                        }
                    }
                }
            });
        }
    }

})($);
</script>
<script>
    $(function(){
    	events.init();
        var qxzcookie1=getCookie("ggjquxiazai");
    	if(parseInt(qxzcookie1) != 1){
    		$('.quphone').show();
    		events.init();
    	}
    	$('.quphone span').click(function(){
    		var qxzcookie=getCookie("ggjquxiazai");
    		 
    		if(qxzcookie == null){
    			setCookie("ggjquxiazai","1","d3");  
    			$('.quphone').animate({height:'0',paddingTop:'0'});
    			$('.quphone').hide();
    			
    		}else{
    			$('.quphone').hide();
    			events.init();
    		}
    	});
    });
    var events={
        init:function(){
        	$(".imglazyload").picLazyLoad({
                threshold: 800,
                placeholder: ''
            });
			setTimeout(function(){
				$("#navWrap").height($(".navList").height()+1+"px").css("overflow","hidden");
				$("#nav").height($(".navList").height()+1+"px").css("overflow","hidden");
				events.clickScroll();
			},500);
            $(window).on("scroll",function(){
            	var wH=$(window).height(),wTop=$(window).scrollTop();
            	if(wTop>=wH){
            		$(".toTop").show();
            	}
            	if(wTop<wH){
            		$(".toTop").hide();
            	}
                var headHeight=$(".head").height(),$nav=$(".navList"),$navWrap=$("#navWrap"),
                        navHeight=$nav.height(),$scrollTop=$(this).scrollTop();
                if($scrollTop>=headHeight){
                	if($(window).width()>800){
                		$nav.width("750px");
                	}
                    $navWrap.css({position:"fixed",top:0,left:0,zIndex:201});
                }else if($scrollTop<=headHeight+navHeight+100){
                	$navWrap.css({position:"static"});
                }
            });
            this.toTop();
        },
        clickScroll:function(){
        	var moveHeight={};
            moveHeight.height1=$("#topHeight").height()-$("#nav").height()-4;
            var goH=moveHeight.height1;
            $(".navList li").each(function(){
            	var index=$(this).index();
            	$(this).attr("mtop",goH);
            	var len=$(".navList li").length;
            	if(len<=6){
            		goH+=$(".repeatModel").eq(index).height();
            	}else{
            		goH+=$(".repeatModel").eq(index).height()-20;
            	}
            	
            });
            $(".navList li").on("click",function(){
            	if($(this).index()!=0){
        			var moveTop=parseInt($(this).attr("mtop"))+$("#nav").height()*($(this).index()/2)-2;
        				$("body").animate({scrollTop:moveTop+"px"});
        		}else{
        			var moveTop=parseInt($(this).attr("mtop"));
        			$("body").animate({scrollTop:moveTop+"px"});
        		}
        		$(this).addClass("active").removeClass("normal");
                $(this).siblings().removeClass("active").addClass("normal");
        	});
            $(window).on("scroll",function(){
                var $this=$(this),sTop=$this.scrollTop();
                $(".navList li").each(function(){
                    var $thisHeight=$(this).attr("mtop");
                    if(sTop>=$thisHeight){
                        $(this).addClass("active").removeClass("normal");
                        $(this).siblings().removeClass("active").addClass("normal");
                    }
                });
            });
        },
        toTop:function(){
            $(".toTop").on("touchstart",function(){
                $("body").animate({scrollTop:0});
                $(".navList li").eq(0).addClass("active").removeClass("normal");
                $(".navList li").eq(0).siblings().removeClass("active").addClass("normal");
            });
        }
    }
    function setCookie(name,value,time)
    {
        var strsec = getsec(time);
        var exp = new Date();
        exp.setTime(exp.getTime() + strsec*1);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

    }
    function getsec(str)
    {
       var str1=str.substring(1,str.length)*1;
       var str2=str.substring(0,1);
       if (str2=="s")
       {
            return str1*1000;
       }
       else if (str2=="h")
       {
           return str1*60*60*1000;
       }
       else if (str2=="d")
       {
           return str1*24*60*60*1000;
       }
    }
    function getCookie(name)
    {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
     
        if(arr=document.cookie.match(reg))
     
            return (arr[2]);
        else
            return null;
    }
</script>
</body>
</html>