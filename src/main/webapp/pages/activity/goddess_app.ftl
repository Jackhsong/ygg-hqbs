<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <title>3.7女神节主会场</title>
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
        .main{width: 39%;margin: 0 auto;position: relative;background: #fff3db;}
        .main img{display: block;width: 100%;}
        .repeatModel{padding-top: 0.2rem;}
        .repeatModel header{background-size: 100%;height: 1.2rem;}
        .repeatModel header .tit{color: #fff3db;text-align: center;line-height: 150%;padding-top:0.1rem;font-size: 0.45rem;width: 100%;}
        .repeatModel ul{width: 100%;}
        .repeatModel li{background: #fff;margin-bottom: 0.3rem;padding-bottom: 0.3rem;}
        .repeatModel .tit{color: #000000;font-size: 0.45rem;line-height: 250%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .desc{color: #505050;font-size: 0.4rem;line-height: 150%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .price{width: 95%;margin: 0.2rem auto 0 auto;}
        .repeatModel .price i{margin-top: 0.3rem;font-family: "Arial";}
        .repeatModel .price .oldPrice{margin-top: 0.35rem;}
        .repeatModel .img{z-index:5;position:relative;background:url("${rc.contextPath}/pages/images/activity/goddess/loading.gif") no-repeat center center;background-size:100%;overflow: hidden;display: block;}
        .repeatModel .img{height: 4.7rem;line-height: 4.6rem;}
        .repeatModel .img img{display: inline-block;vertical-align:middle;}
        .repeatModel .img i{display:none;width: 100%;height: 100%;top:0;left: 0;background: url("${rc.contextPath}/pages/images/activity/goddess/nostart.png") no-repeat center center;background-size: 50%;}
        .repeatModel .nowPrice{color: #ff3300;font-size: 0.5rem;margin: 0 0.2rem 0 0;}
        .repeatModel .oldPrice{color: #999999;font-size: 0.4rem;text-decoration: line-through;}
        .buyCar{width: 0.7rem;height: 0.7rem;background: url("${rc.contextPath}/pages/images/activity/goddess/img05.jpg") no-repeat;background-size: 100%;position: relative;margin-top: 0.2rem;}
        .repeatModel .double{background: none;background: #fff;width: 47%;margin-left: 2%;}
        .repeatModel .double .tit{font-size: 0.35rem;line-height: 150%;margin: 0.2rem auto 0.2rem auto;height: 1rem;overflow: hidden;}
        .repeatModel .double .desc{font-size: 0.3rem;line-height: 130%;height: 1.2rem;overflow: hidden;}
        /*.repeatModel .left,.hot .left,.repeatModel .right,.hot .right{width: 49%;background: #fff;padding-bottom: 0.3rem;}*/
        /*.repeatModel .left,.hot .left{margin-right: 2%;}*/
        .head{background: #f06252;}
        .titImg{margin-top:-.2rem;}
        /*.head .return{width:0.7rem;height: 0.7rem;background: url("${rc.contextPath}/pages/images/activity/goddess/img05.png") no-repeat;background-size: 100%;top: 0.3rem;left: 0.5rem;}*/
        /*.head h4{text-align: center;color: #f4f4f4;font-size: 0.55rem;padding: 0.4rem 0;}*/
        .toTop{position: fixed;right: 0.05rem;bottom:0;background: url("${rc.contextPath}/pages/images/activity/goddess/totop.png") center no-repeat;width: 1.5rem; height: 1.5rem;background-size: 100%;z-index: 100;}
        #r01{background: #af435f;}
        #r02{background: #dd597b;}
        #r03{background: #e66888;}
        #r04{background: #f5747c;}
        #r05{background: #dc786a;}
        #r06{background: #bd5959;}
        #r07{background: #af435f;}
        .nav{position: fixed;width: 30%;background:rgba(211,55,80,.88);display: none;top: 30%;
            -webkit-transition:all .3s linear;
            -moz-transition:all .3s linear;
            -ms-transition:all .3s linear;
            -o-transition:all .3s linear;
            transition:all .3s linear;
            -webkit-transform:translateX(0);
            -moz-transform:translateX(0);
            -ms-transform:translateX(0);
            -o-transform:translateX(0);
            transform:translateX(0);
            left: 100%;
            z-index: 100;
        }
        .nav .overflow{overflow: hidden;padding-bottom: 0.3rem;}
        .nav ul{overflow: hidden;height: 6.4rem;}
        .nav ul li:last-child span{border:none;}
        .nav ul li{width: 100%;font-size: 0.3rem;}
        .nav ul span{width: 90%;text-align: center;display: block;padding: 0.2rem 0;border-bottom: 1px solid #ffb303;margin: 0 auto;}
        .nav ul b{background: #fff;margin-right:.2rem;text-align: center;width: .5rem;height: .5rem;line-height: .5rem;border-radius: 100%;color: #d84c62;display: inline-block;}
        .nav .active{background: #ffb303;color: #fff;}
        .nav .normal{background: none;color: #fff;}
        .nav .flag{font-size:0.45rem;bottom: 0;left: -0.7rem;padding:0.2rem 0.1rem;border-top-right-radius: 3px;border-bottom-right-radius: 3px;background: rgba(250,116,104,.88);color: #fff;width: 0.5rem;line-height: 110%;}
        .nav .flag i{display: block;
            transition:all .3s linear;
            -webkit-transition:all .3s linear;
            -moz-transition:all .3s linear;
            -ms-transition:all .3s linear;
            -o-transition:all .3s linear;
            -webkit-transform:scale(0.6,1) rotate(180deg);margin-left: -0.06rem;
            -moz-transform:scale(0.6,1) rotate(180deg);margin-left: -0.06rem;
            -ms-transform:scale(0.6,1) rotate(180deg);margin-left: -0.06rem;
            -o-transform:scale(0.6,1) rotate(180deg);margin-left: -0.06rem;
            transform:scale(0.6,1) rotate(180deg);margin-left: -0.06rem;
        }
        #getCoupon{width: 100%;height: 1.7rem;left: 0;top: 0;background: none;}
        #getCoupon:focus{outline: none;}
        .opcWrap{width:100%;height:100%;position:fixed;left:0;top:0;background:rgba(0,0,0,.88);z-index:101;display:none;}
        .couponTc{top:20%;left:0;}
        .close{width:7rem;height:.9rem;top:4.85rem;left:1.5rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        @media screen and (max-width: 320px) {
            .nav ul{height: 5.7rem;}
        }
    </style>
</head>
<body>
<div class="main" id="main">
	<section class="opcWrap">
		<article class="couponTc abso">
			<img src="${rc.contextPath}/pages/images/activity/goddess/tc.png"/>
			<i class="close abso"></i>
		</article>
	</section>
    <nav class="nav">
        <div class="overflow">
            <ul class="navList box" id="navList">
            	<li class="fl active"><span><b>1</b><i>美白祛斑</i></span></li>
            	<li class="fl normal"><span><b>2</b><i>抗老冻龄</i></span></li>
            	<li class="fl normal"><span><b>3</b><i>丰胸瘦身</i></span></li>
            	<li class="fl normal"><span><b>4</b><i>红润气色</i></span></li>
            	<li class="fl normal"><span><b>5</b><i>调理体质</i></span></li>
            	<li class="fl normal"><span><b>6</b><i>均衡营养</i></span></li>
            	<li class="fl normal"><span><b>7</b><i>情调生活</i></span></li>
            </ul>
        </div>
        <span class="abso flag">会场导航<br/><i>>></i></span>
    </nav>
    <div id="topHeight">
        <article class="relative">
            <input type="button" class="abso" value="" id="getCoupon">
            <#if received==0>
            <img src="${rc.contextPath}/pages/images/activity/goddess/getCouponBefore.jpg"/>
            <#else>
            <img src="${rc.contextPath}/pages/images/activity/goddess/getCouponAfter.jpg"/>
            </#if>
        </article>
        <!--<header class="head relative" id="head">-->
        <!--<a class="abso return" href="#"></a>-->
        <!--<h4>12星座主会场</h4>-->
        <!--</header>-->
        <img src="${rc.contextPath}/pages/images/activity/goddess/banner01.jpg"/>
        <img src="${rc.contextPath}/pages/images/activity/goddess/banner02.jpg"/>
        <!-- <img src="${rc.contextPath}/pages/images/activity/goddess/tit01.jpg"/> -->
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
    <input type="hidden" id="isLogin" value="${login}"/>
    <input type="hidden" id="accountId" value="${accountId}"/>
    <input type="hidden" id="received" value="${received}"/>
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
                    $nav.show();
                }else{
                    $nav.css("display","none");
                }
            });
            this.toTop();
            this.ajaxDoing();
            this.getCoupon();
        },
        isShow:function(){
            var f=true;
            $(".flag").click(function(){
                if(!f){
                    f=true;
                    $(".nav").css("transform","translateX(0)");
                    $(".flag i").css({"transform":"rotate(180deg) scale(0.6,1)","margin-left":"0.1rem"});
                }else{
                    f=false;
                    $(".nav").css("transform","translateX(-100%)");
                    $(".flag i").css({"transform":"rotate(0) scale(0.6,1)","margin-left":"-0.06rem"});
                }
            });
            /*var startTime=new Date("2015/8/19 10:00").getTime(),nowTime=new Date().getTime();*/
            /*if(nowTime>=startTime){
                $(".img i").hide();
            }*/
            /*if(nowTime<startTime){
                setTimeout(function () {
                    $(".img i").show();
                },2000);
            }*/
        },
        getCoupon:function(){
        	$(".close").on("touchend",function(){
        		$(".opcWrap").hide();
        	});
        	$("#getCoupon").on("touchend",function(){
        		var $this=$(this);
        		$this.prop("disabled",true);
        		var isLogin=$("#isLogin").val();
        		if(isLogin=="0"){
        			window.location.href="ggj://alert/account/login";
        			return false;
        		}
        		var received=$("#received").val();
        		if(received=="0"){
        			var accountId=$("#accountId").val();
            		$.ajax({
            			url:"${rc.contextPath}/activity/goddess/receive",
            			type:"post",
            			dataType:"json",
            			data:{requestFrom:2,accountId:accountId},
            			success:function(data){
            				if(data.status==0){
            					alert(data.msg);
            				}
            				if(data.status==1){
            					$(".opcWrap").show();
            				}
            				$("#getCoupon").removeAttr("disabled");
            			}
            		});
        		}else{
        			$(".opcWrap").show();
        		}
        		
        	});
        },
        clickScroll:function(){
            var moveHeight={};
            moveHeight.height1=$("#topHeight").height();
            var mTop=parseInt($("#r01").css("padding-top"));
            var titHeight=$(".titImg").height();
            $("#navList li").each(function(){
                var $this=$(this);
                var i=$this.index()+ 1;
                for(var j=0;j<i;j++){
                    var mh=0;
                    if(j!=0){
                        moveHeight["height"+i]=$("#r0"+i).height()+mTop;
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
                    $("body").animate({scrollTop:$this.attr("mtop")+"px"});
                    $this.addClass("active").removeClass("normal").siblings().addClass("normal").removeClass("active");
                });
            });
            $(window).on("scroll",function(){
                var $this=$(this),sTop=$this.scrollTop();
                $("#navList li").each(function(){
                    var $thisHeight=parseInt($(this).attr("mtop"));
                    var mtop=parseInt($("#r01").css("padding-top"));
                    if(sTop>=$thisHeight-mtop){
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
            $.ajax({
                url:"${rc.contextPath}/activity/goddess/getData",
                dataType:'json',
                data:{requestFrom:2},
                success:function(data) {
                	var str1="";
                	if(data.length>0){
                        for(var i=0;i<data.length;i++){
                            var d=data[i];
                            var str2="";
                            str2+='<section class="repeatModel" id="r0'+(i+1)+'"><img class="titImg" src="${rc.contextPath}/pages/images/activity/goddess/tit0'+(i+1)+'.jpg"/><ul class="box">';
                            for(var j=0;j< 10;j++){
                                var d2= d.detail[j];
                                str2+='<li class="double fl">' +
                                        '<div class="left">' +
                                        '<a class="img" href="'+d2.url+'">' +
                                        '<img class="imglazyload" data-original="'+d2.image+'"/>' +
                                        '<i class="abso"></i>'+
                                        '</a>' +
                                        '<a href="'+d2.url+'" class="tit">'+ d2.name+'</a>' +
                                        '<div class="price box">' +
                                        '<i class="nowPrice fl">￥'+ d2.lowPrice +'</i>' +
                                        '<i class="oldPrice fl">￥'+ d2.highPrice+'</i>' +
                                        '<a href="'+ d2.url+'" class="buyCar fr"></a>' +
                                        '</div>' +
                                        '</div>' +
                                        '</li>';
                            }
                            str2+="</ul></section>";
                            $("#main").append(str2);
                            if(i==data.length-1){
                            	setTimeout(events.clickScroll,2000);
                            }
                        }
                        $(".imglazyload").picLazyLoad({
                            threshold: 100,
                            placeholder: ''
                        });
                    }
                    events.isShow();
                },
//                jsonpCallback:'callback',
                timeout:3000
            });
        }
    }
</script>
</body>
</html>