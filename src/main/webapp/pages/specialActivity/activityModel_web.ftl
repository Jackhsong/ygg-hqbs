<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <title>${title}</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;transition:all .3s linear;}
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
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        .main{width: 39%;margin: 0 auto;position: relative;}
        .main img{display: block;width: 100%;}
        .bannerImg{display:block;background:url("${rc.contextPath}/pages/images/scene/holder_logo.png") no-repeat center center #fff;background-size: 45%;}
        .repeatModel ul{width: 100%;}
        .repeatModel li{background: #fff;margin-bottom: 0.3rem;padding-bottom: 0.2rem;border-radius: 3px;}
        .repeatModel .tit{color: #000000;font-size: 0.45rem;line-height: 250%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .desc{color: #505050;font-size: 0.4rem;line-height: 150%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .price{width: 95%;margin:0 auto;}
        .repeatModel .price i{margin-top: 0.3rem;font-family: "microsoft yahei";}
        .repeatModel .price .oldPrice{margin-top: 0.35rem;}
        .repeatModel .img{z-index:5;position:relative;background:url("${rc.contextPath}/pages/images/special/activityModel/loading.gif") no-repeat center center;background-size:100%;overflow: hidden;display: block;}
        .repeatModel .img{height: 4.7rem;line-height: 4.6rem;}
        .repeatModel .img img{display: inline-block;vertical-align:middle;}
        .repeatModel .img i{display:none;width: 100%;height: 100%;top:0;left: 0;background: url("${rc.contextPath}/pages/images/special/activityModel/nostart.png") no-repeat center center;background-size: 50%;}
        .repeatModel .nowPrice{color: #333333;font-size: 0.5rem;margin: 0 0.2rem 0 0;}
        .repeatModel .oldPrice{color: #999999;font-size: 0.35rem;text-decoration: line-through;width:1.2rem;overflow:hidden;}
        .buyCar{width: 0.9rem;height: 0.9rem;background: url("${rc.contextPath}/pages/images/special/activityModel/img05.jpg") no-repeat;background-size: 100%;position: relative;margin-top: 0.1rem;}
        .repeatModel .double{background: none;background: #fff;width: 47%;margin-left: 2%;}
        .repeatModel .double .tit{font-size: 0.35rem;line-height: 130%;margin: 0.2rem auto .1rem auto;height: .9rem;overflow: hidden;color: #333333}
        .repeatModel .double .desc{font-size: 0.3rem;line-height: 130%;height: 0.8rem;overflow: hidden;color: #858585;margin: 0 auto;overflow: hidden;}
        .repeatModel .double .img,.repeatModel .double .img img{border-top-left-radius: 3px;border-top-right-radius: 3px;}
        .repeatTitWrap{overflow:hidden; margin-bottom: .2rem; background: #fff3f2;height:1.6rem;}
        .repeatModel .repeatTit{width: 100%;background: url("${rc.contextPath}/pages/images/special/activityModel/titbg.png") no-repeat center 0;background-size: 40% .6rem;height: .6rem;line-height:.6rem;color:#ffffff;font-size:.4rem;text-align: center;}
        .repeatModel .repeatDesc{width: 100%;text-align: center;color: #ff4a19;font-size: .3rem;line-height: 200%;}
        .head{background: #f06252;}
        .toTop{position: fixed;right: 0.05rem;bottom:0;background: url("${rc.contextPath}/pages/images/special/activityModel/totop.png") center no-repeat;width: 1.5rem; height: 1.5rem;background-size: 100%;z-index: 100;}
        .nav{position: static;z-index: 100;}
        .nav .overflow{width: 100%;background:#ffffff;border-bottom:1px solid #e0e0e0;}
        .nav ul{ height: 1.7rem; overflow: hidden; }
        .nav li{height: 0.8rem;}
        .nav ul span{width: 90%;text-align: center;display: block;padding: 0.2rem 0;margin: 0 auto;height: 0.4rem;line-height: 0.4rem;}
        .nav .normal{background: none;color: #33383b;}
        .nav .active span{color: #ff2d19;border-bottom: 2px solid #ff2d19;}
        .opcWrap{width:100%;height:100%;position:fixed;left:0;top:0;background:rgba(0,0,0,.88);z-index:101;display:none;}
        .close{width:7rem;height:.9rem;top:4.85rem;left:1.5rem;}
        .nav ul .two{width: 5rem;}
        .nav ul .three{width: 3.3rem;}
        .nav ul .more{width: 2.4rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
    </style>
</head>
<body>
<div class="main" id="main">
    <div id="topHeight">
	    <header class="download">
	        <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')" class="DownLoad" target="_self">
	            <img src="${rc.contextPath}/pages/images/activity/activitySideBar.png"/>
	        </a>
	    </header>
    	<i class="bannerImg">
    		<img src="${imageUrl}"/>		
    	</i>
        <nav class="nav">
            <div class="overflow navMoveWrap">
                <ul class="navList box" id="navList">
                <#if categoryList?? && ((categoryList?size == 2) || (categoryList?size == 4)) >
                <#list categoryList as list>
                	<#if list_index==0>
                		<li class="fl active two"><span><i>${list}</i></span></li>
                	<#else>
                		<li class="fl normal two"><span><i>${list}</i></span></li>
                	</#if>
                    </#list>
                </#if>
                <#if categoryList?? && ((categoryList?size == 3) || (categoryList?size <= 6)) >
                <#list categoryList as list>
                	<#if list_index==0>
                		<li class="fl active three"><span><i>${list}</i></span></li>
                	<#else>
                		<li class="fl normal three"><span><i>${list}</i></span></li>
                	</#if>
                    </#list>
                </#if>
                <#if categoryList?? && (categoryList?size >6) >
                <#list categoryList as list>
                	<#if list_index==0>
                		<li class="fl active more"><span><i>${list}</i></span></li>
                	<#else>
                		<li class="fl normal more"><span><i>${list}</i></span></li>
                	</#if>
                    </#list>
                </#if>
                </ul>
            </div>
            <!--<span class="abso flag">会场导航<br/><i>>></i></span>-->
        </nav>
    </div>
    <!--<section class="repeatModel" id="r01">-->
    <!--<header class="box">-->
    <!--<h2 class="tit fl">防暑小点</h2>-->
    <!--</header>-->
    <!--<ul class="box">-->
    <!--<li class="double fl">-->
    <!--<div class="left">-->
    <!--<a class="img" href="http://m.gegejia.com/item-3436.htm">-->
    <!--<img class="imglazyload" data-original="${rc.contextPath}/pages/images/activity/goddess/temp02.jpg"/>-->
    <!--</a>-->
    <!--<a href="http://m.gegejia.com/item-3436.htm" class="tit">【十包装】生和堂吸吸龟苓膏253g </a>-->
    <!--<p class="desc">童年记忆里最经典的夏日冷饮，清热解毒、保健养颜，随时随地享受清凉美味。</p>-->
    <!--<div class="price box">-->
    <!--<i class="nowPrice fl">￥41.5</i>-->
    <!--<i class="oldPrice fl">￥80</i>-->
    <!--<a href="http://m.gegejia.com/item-3436.htm" class="buyCar fr"></a>-->
    <!--</div>-->
    <!--</div>-->
    <!--</li>-->
    <!--<li class="double fl">-->
    <!--<div class="left">-->
    <!--<a class="img" href="http://m.gegejia.com/sale-569.htm">-->
    <!--<img class="imglazyload" data-original="${rc.contextPath}/pages/images/activity/goddess/temp02.jpg"/>-->
    <!--</a>-->
    <!--<a href="http://m.gegejia.com/sale-569.htm" class="tit">印尼Smooze果茸冰组合（3种口味）</a>-->
    <!--<p class="desc">可以当冰淇淋吃的美味果汁，优质热带水果制成，果汁含量80%以上。</p>-->
    <!--<div class="price box">-->
    <!--<i class="nowPrice fl">￥45</i>-->
    <!--<i class="oldPrice fl">￥100</i>-->
    <!--<a href="http://m.gegejia.com/sale-569.htm" class="buyCar fr"></a>-->
    <!--</div>-->
    <!--</div>-->
    <!--</li>-->
    <!--</ul>-->
    <!--</section>-->
    <a class="toTop" id="toTop"></a>
    <input type="hidden" value="${activityId}" id="activityId"/>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
<script>
    $(function(){
        events.init();
    });
    var events={
        init:function(){
            $(window).on("scroll",function(){
                var headHeight=$("#topHeight").height(),$nav=$(".nav"),
                        $scrollTop=$(this).scrollTop();
                
                if($scrollTop>=headHeight){
                    $nav.css({"position":"fixed","top":0});
                }else{
                    $nav.css("position","static");
                }
            });
            this.toTop();
            this.ajaxDoing();
        },
        clickScroll:function(){
            var moveHeight={};
            moveHeight.height1=$("#topHeight").height();
//            var titHeight=$(".titImg").height();
            $("#navList li").each(function(){
                var $this=$(this);
                var i=$this.index()+ 1;
                for(var j=0;j<i;j++){
                    var mh=0;
                    if(j!=0){
                        if(j==7){
                        	if($(window).width()<=320){
                        		moveHeight["height"+i]=$("#r0"+i).height()-260;
                        	}
                        	if($(window).width()>=365){
                        		moveHeight["height"+i]=$("#r0"+i).height()-310;
                        	}
                        	if($(window).width()>=414){
                        		moveHeight["height"+i]=$("#r0"+i).height()-335;
                        	}
                        }else{
                        	moveHeight["height"+i]=$("#r0"+i).height();	
                        }
                    }
                    for(var z in moveHeight){
                        mh+=moveHeight[z];
                    }
                    $this.attr("mtop",mh);
                }
                $this.on("touchend",function(e){
                    var $this=$(this);
                    $this.addClass("active").removeClass("normal");
                    $this.siblings().removeClass("active").addClass("normal");
                    e.preventDefault();
                    var titHeight=$(".repeatTitWrap").height();
                    var navHeight=$(".nav").height();
                    var mtop=parseFloat($this.attr("mtop"))-titHeight-navHeight-20;
                    $("body").animate({scrollTop:mtop+"px"});
                    $this.addClass("active").removeClass("normal").siblings().addClass("normal").removeClass("active");
                });
            });
            $(window).on("scroll",function(){
                var $this=$(this),sTop=$this.scrollTop();
                $("#navList li").each(function(){
                    var $thisHeight=parseInt($(this).attr("mtop"));
                    var navHeight=$(".nav").height();
                    var titHeight=$(".repeatTitWrap").height();
                    if(sTop>=$thisHeight-navHeight-titHeight-40){
                        $(this).addClass("active").removeClass("normal");
                        $(this).siblings().removeClass("active").addClass("normal");
                    }
                });
            });
        },
        toTop:function(){
            $(".toTop").click(function(){
                $("body").animate({scrollTop:0});
            });
        },
        ajaxDoing:function(){
        	var $activityId=$("#activityId").val();
            $.ajax({
            	type:"get",
                url:"${rc.contextPath}/special/newScene/getData",
                dataType:'json',
                data:{clientType:1,activityId:$activityId},
                success:function(data) {
                	var str1="";
                    if(data.length>0){
                        for(var i=0;i<data.length;i++){
                            var d=data[i];
                            var str2="";
                            str2+='<section class="repeatModel" id="r0'+(i+1)+'">' +
                                    '<header class="repeatTitWrap">' +
                                    	'<img src="'+d.imageUrl+'"/>'+
                                    //'<h2 class="repeatTit">我 是 标 题'+(i+1)+'</h2>' +
                                    //'<p class="repeatDesc">洗干净脸永远是护肤的第一步。</p>' +
                                    '</header>' +
                                    '<ul class="box">';
                            if(d.productList.length>0){
                            	for(var j=0;j<d.productList.length;j++){
                                    var d2= d.productList[j];
                                    str2+='<li class="double fl">' +
                                            '<div class="left">' +
                                            '<a class="img" href="'+d2.buyUrl+'">' +
                                            '<img class="imglazyload" data-original="'+d2.imageUrl+'"/>' +
                                            '<i class="abso"></i>'+
                                            '</a>' +
                                            '<a href="'+d2.buyUrl+'" class="tit">'+ d2.name+'</a>' +
                                            '<a href="'+d2.buyUrl+'" class="desc">' + d2.desc + '</a>' +
                                            '<div class="price box">' +
                                            '<i class="nowPrice fl">￥'+ d2.lowPrice +'</i>' +
                                            '<i class="oldPrice fl">￥'+ d2.highPrice+'</i>' +
                                            '<a href="'+ d2.buyUrl+'" class="buyCar fr"></a>' +
                                            '</div>' +
                                            '</div>' +
                                            '</li>';
                                }
                            }
                            str2+="</ul></section>";
                            $("#main").append(str2);
                        }
                        var len=$(".repeatModel").length;
                        if(len==data.length){
                        	setTimeout(events.clickScroll,2000);
                        }
                        $(".imglazyload").picLazyLoad({
                            threshold: 100,
                            placeholder: ''
                        });
                    }
                },
                timeout:3000
            });
        }
    }
</script>
</body>
</html>