<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>秋高气爽，美人靠养-左岸城堡秋季养生专场</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;}
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
        .repeatModel{margin-top: 0.2rem;}
        .repeatModel header{background: url("img/img06.png") no-repeat;background-size: 100%;height: 1.2rem;}
        .repeatModel header .tit{color: #fff3db;text-align: center;line-height: 150%;padding-top:0.1rem;font-size: 0.45rem;width: 100%;}
        .repeatModel ul,.hot ul{width: 96%;margin: 0 auto;}
        .repeatModel li,.hot li{background: #fff;margin-bottom: 0.3rem;padding-bottom: 0.3rem;}
        .repeatModel .tit,.hot .tit{color: #000000;font-size: 0.45rem;line-height: 250%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .desc,.hot .desc{color: #505050;font-size: 0.4rem;line-height: 150%;width: 95%;margin: 0 auto;display: block;}
        .repeatModel .price,.hot .price{width: 95%;margin: 0.2rem auto 0 auto;}
        .repeatModel .price i,.hot .price i{margin-top: 0.3rem;}
        .repeatModel .price .oldPrice,.hot .price .oldPrice{margin-top: 0.35rem;}
        .repeatModel .img,.hot .img{height:4.7rem;background:url("img/loading.gif") no-repeat center center;background-size:100%;overflow: hidden;display: block;line-height: 4.7rem;}
        .repeatModel .img img,.hot .img img{display: inline-block;vertical-align:middle;}
        .repeatModel .nowPrice,.hot .nowPrice{color: #ff3300;font-size: 0.5rem;margin: 0 0.2rem 0 0;}
        .repeatModel .oldPrice,.hot .oldPrice{color: #999999;font-size: 0.4rem;text-decoration: line-through;}
        .buyCar{width: 0.7rem;height: 0.7rem;background: url("img/img05.jpg") no-repeat;background-size: 100%;position: relative;margin-top: 0.2rem;}
        .repeatModel .double,.hot .double{background: none;padding-bottom: 0;}
        .repeatModel .double .tit,.hot .double .tit{font-size: 0.35rem;line-height: 130%;margin: 0.2rem auto 0.2rem auto;height: 1rem;overflow: hidden;}
        .repeatModel .double .desc,.hot .double .desc{font-size: 0.3rem;line-height: 130%;height: 1.2rem;overflow: hidden;}
        .repeatModel .left,.hot .left,.repeatModel .right,.hot .right{width: 49%;background: #fff;padding-bottom: 0.3rem;}
        .repeatModel .left,.hot .left{margin-right: 2%;}
        .head{background: #f06252;}
        .head .return{width:0.7rem;height: 0.7rem;background: url("img/img05.png") no-repeat;background-size: 100%;top: 0.3rem;left: 0.5rem;}
        .head h4{text-align: center;color: #f4f4f4;font-size: 0.55rem;padding: 0.4rem 0;}
        .toTop{position: fixed;right: 0.05rem;bottom:0;background: url("img/totop.png") center no-repeat;width: 1.5rem; height: 1.5rem;background-size: 100%;z-index: 300;}
        .nav{position: fixed;width: 30%;padding-left:0.1rem;background:url("img/blackopc.png") repeat;display: none;top: 30%;
            -webkit-transition:all .3s linear;
            -moz-transition:all .3s linear;
            -ms-transition:all .3s linear;
            -o-transition:all .3s linear;
            transition:all .3s linear;
        }
        .nav .overflow{overflow: hidden;width: 95%;padding-bottom: 0.3rem;}
        .nav ul{width: 110%;padding: 0.2rem 0;overflow: hidden;height: 4.2rem;}
        .nav ul li{width: 45%;text-align: center;border-bottom: 2px solid #ffb303;border-right: 2px solid #ffb303;font-size: 0.3rem;color: #ffb303;padding: 0.2rem 0;}
        .nav .flag{font-size:0.45rem;bottom: 0;right: -0.7rem;padding:0.2rem 0.1rem;border-top-right-radius: 3px;border-bottom-right-radius: 3px;background: url("img/yellowopc.png") repeat;color: #33383b;width: 0.5rem;line-height: 110%;}
        .nav .flag i{display: block;
            transition:all .3s linear;
            -webkit-transition:all .3s linear;
            -moz-transition:all .3s linear;
            -ms-transition:all .3s linear;
            -o-transition:all .3s linear;
            -webkit-transform:scale(0.6,1);margin-left: -0.06rem;
            -moz-transform:scale(0.6,1);margin-left: -0.06rem;
            -ms-transform:scale(0.6,1);margin-left: -0.06rem;
            -o-transform:scale(0.6,1);margin-left: -0.06rem;
            transform:scale(0.6,1);margin-left: -0.06rem;
        }
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        @media screen and (max-width: 320px) {
            .nav ul{height: 4.7rem;}
        }
    </style>
</head>
<body>
<div class="main">
 <nav class="nav">
     <div class="overflow">
         <ul class="navList box">
             <li class="fl"><i>白羊座</i></li>
             <li class="fl"><i>金牛座</i></li>
             <li class="fl"><i>双子座</i></li>
             <li class="fl"><i>巨蟹座</i></li>
             <li class="fl"><i>狮子座</i></li>
             <li class="fl"><i>处女座</i></li>
             <li class="fl"><i>天秤座</i></li>
             <li class="fl"><i>天蝎座</i></li>
             <li class="fl"><i>射手座</i></li>
             <li class="fl"><i>摩羯座</i></li>
         </ul>
     </div>
     <span class="abso flag">会场导航<br/><i><<</i></span>
 </nav>
<div id="topHeight">
    <header class="head relative" id="head">
        <a class="abso return" href="#"></a>
        <h4>12星座主会场</h4>
    </header>
    <section class="banner" id="banner">
        <img src="img/img01.jpg"/>
        <img src="img/img02.jpg"/>
        <img src="img/img03.jpg"/>
        <img src="img/img04.jpg"/>
    </section>
    <section class="hot">
        <ul>
            <li class="single">
                <a class="img">
                    <img class="imglazyload" data-original="img/temp01.jpg"/>
                </a>
                <a href="#" class="tit">日本进口芥末章鱼煎饼香浓芥末味110g </a>
                <p class="desc">作为一天中重要的能量来源早餐一定要摄进主食。以碳水化合物为主，进食一些淀粉类食品，谷类食品吸收后能很快分解成葡萄糖，纠正睡眠后的低血糖现象。</p>
                <div class="price box">
                    <i class="nowPrice fl">￥289.5</i>
                    <i class="oldPrice fl">￥342</i>
                    <a href="" class="buyCar fr"></a>
                </div>
            </li>
        </ul>
        <ul>
            <li class="double box">
                <div class="left fl">
                    <a class="img" href="http://m.gegejia.com/item-3436.htm">
                        <img class="imglazyload" data-original="img/temp02.jpg"/>
                    </a>
                    <a href="http://m.gegejia.com/item-3436.htm" class="tit">【十包装】生和堂吸吸龟苓膏253g </a>
                    <p class="desc">童年记忆里最经典的夏日冷饮，清热解毒、保健养颜，随时随地享受清凉美味。</p>
                    <div class="price box">
                        <i class="nowPrice fl">￥41.5</i>
                        <i class="oldPrice fl">￥80</i>
                        <a href="http://m.gegejia.com/item-3436.htm" class="buyCar fr"></a>
                    </div>
                </div>
                <div class="right fl">
                    <a class="img" href="http://m.gegejia.com/item-4452.htm">
                        <img class="imglazyload" data-original="img/temp02.jpg"/>
                    </a>
                    <a href="http://m.gegejia.com/item-4452.htm" class="tit">【两包装】日本宇治抹菜蕨菜饼 </a>
                    <p class="desc">夏日必备清爽日式茶点，清新自然的浓郁抹茶清香，口感柔滑细腻。</p>
                    <div class="price box">
                        <i class="nowPrice fl">￥48</i>
                        <i class="oldPrice fl">￥65</i>
                        <a href="http://m.gegejia.com/item-4452.htm" class="buyCar fr"></a>
                    </div>
                </div>
            </li>
        </ul>
    </section>
</div>
<section class="repeatModel" id="r01">
    <header class="box">
        <h2 class="tit fl">防暑小点</h2>
    </header>
    <ul>
        <li class="double box">
            <div class="left fl">
                <a class="img" href="http://m.gegejia.com/item-3436.htm">
                    <img class="imglazyload" data-original="img/temp02.jpg"/>
                </a>
                <a href="http://m.gegejia.com/item-3436.htm" class="tit">【十包装】生和堂吸吸龟苓膏253g </a>
                <p class="desc">童年记忆里最经典的夏日冷饮，清热解毒、保健养颜，随时随地享受清凉美味。</p>
                <div class="price box">
                    <i class="nowPrice fl">￥41.5</i>
                    <i class="oldPrice fl">￥80</i>
                    <a href="http://m.gegejia.com/item-3436.htm" class="buyCar fr"></a>
                </div>
            </div>
            <div class="right fl">
                <a class="img" href="http://m.gegejia.com/item-4452.htm">
                    <img class="imglazyload" data-original="img/temp02.jpg"/>
                </a>
                <a href="http://m.gegejia.com/item-4452.htm" class="tit">【两包装】日本宇治抹菜蕨菜饼 </a>
                <p class="desc">夏日必备清爽日式茶点，清新自然的浓郁抹茶清香，口感柔滑细腻。</p>
                <div class="price box">
                    <i class="nowPrice fl">￥48</i>
                    <i class="oldPrice fl">￥65</i>
                    <a href="http://m.gegejia.com/item-4452.htm" class="buyCar fr"></a>
                </div>
            </div>
        </li>
        <li class="double box">
            <div class="left fl">
                <a class="img" href="http://m.gegejia.com/sale-569.htm">
                    <img class="imglazyload" data-original="img/temp02.jpg"/>
                </a>
                <a href="http://m.gegejia.com/sale-569.htm" class="tit">印尼Smooze果茸冰组合（3种口味）</a>
                <p class="desc">可以当冰淇淋吃的美味果汁，优质热带水果制成，果汁含量80%以上。</p>
                <div class="price box">
                    <i class="nowPrice fl">￥45</i>
                    <i class="oldPrice fl">￥100</i>
                    <a href="http://m.gegejia.com/sale-569.htm" class="buyCar fr"></a>
                </div>
            </div>
            <div class="right fl">
                <a class="img" href="http://m.gegejia.com/item-4722.htm">
                    <img class="imglazyload" data-original="img/temp02.jpg"/>
                </a>
                <a href="http://m.gegejia.com/item-4722.htm" class="tit">南非地扪糖水黄桃片罐头825g</a>
                <p class="desc">全天然种植的整块黄桃果肉，晶莹剔透、入口清爽，轻松消除夏日燥热</p>
                <div class="price box">
                    <i class="nowPrice fl">￥29</i>
                    <i class="oldPrice fl">￥40</i>
                    <a href="http://m.gegejia.com/item-4722.htm" class="buyCar fr"></a>
                </div>
            </div>
        </li>
    </ul>
</section>
<a class="toTop" id="toTop"></a>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="js/jquery-1.11.2.min.js"></script>
<script>
    $(function(){
        events.init();
    });
    var events={
        init:function(){
            $(".imglazyload").picLazyLoad({
                threshold: 100,
                placeholder: ''
            });
            $(window).on("scroll",function(){
                var headHeight=$(".head").height()+$(".hot").height()+$(".banner").height(),$nav=$(".nav"),
                        $scrollTop=$(this).scrollTop();
                if($scrollTop>=headHeight){
                    $nav.show();
                }else{
                    $nav.css("display","none");
                }
            });
            this.clickScroll();
            this.toTop();
            this.isShow();
        },
        isShow:function(){
            var f=false;
            $(".flag").click(function(){
                if(!f){
                    f=true;
                    $(".nav").css("transform","translateX(-100%)");
                    $(".flag i").css({"transform":"rotate(180deg) scale(0.6,1)","margin-left":"0.1rem"});
                }else{
                    f=false;
                    $(".nav").css("transform","translateX(0)");
                    $(".flag i").css({"transform":"rotate(0) scale(0.6,1)","margin-left":"-0.06rem"});
                }
            });
        },
        clickScroll:function(){
            var moveHeight={};
            moveHeight.height1=$("#topHeight").height();
            $(".navList li").each(function(){
                var $this=$(this);
                var i=$this.index()+ 1;
                for(var j=0;j<i;j++){
                    var mh=0;
                    if(j!=0){
                        var mTop=parseInt($("#r0"+i).css("margin-top"));
                        moveHeight["height"+i]=$("#r0"+i).height()+mTop;
                    }
                    for(var z in moveHeight){
                        mh+=moveHeight[z];
                    }
                    $this.attr("mtop",mh-$("#nav").height()-30);
                }
                $this.on("touchstart",function(e){
                    e.preventDefault();
                    var $this=$(this);
                    $("body").animate({scrollTop:$this.attr("mtop")+"px"});
                    $this.addClass("active").removeClass("normal").siblings().addClass("normal").removeClass("active");
                });
            });
        },
        toTop:function(){
            $(".toTop").click(function(){
                $("body").animate({scrollTop:0});
            });
        }
    }
</script>
</body>
</html>