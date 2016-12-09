<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>左岸城堡--凑单团购</title>
    <script>
        //淘宝H5自适应解决方案
        //https://github.com/amfe/lib-flexible
        (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
    </script>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font: 12px/1.5 microsoft yahei,tahoma,arial,b8b4f53;width: 100%;background: #fffdf2;line-height: 150%;}
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
        .main{width: 39%;margin: 0 auto;}
        .download{width: 100%;}
        .download img{width: 100%;}
        .goods,.join,.user{width: 95%;margin: 0 auto;padding: 0.6rem 0.3rem;border-bottom: 1px solid #cccccc;}
        .goods .goodsImg{width: 3.8rem;border-radius: 5px;border: 1px solid #cccccc;overflow: hidden;}
        .goods .goodsImg img{width: 100%;height:2.8rem;background: url("${rc.contextPath}/pages/images/groupBuy/loading.gif") center no-repeat;background-size: 20%;}
        .goods dl{width: 5.3rem;margin-left: 0.2rem;line-height: 100%;}
        .goods dl a{color: #505050;font-size: 0.3rem;line-height:130%;}
        .goods dt{height:4.3em;}
        .goods dd:nth-child(2){margin-top: 0.2rem;}
        .goods dd:nth-child(2) i{color: #ff3300;font-size: 0.45rem;}
        .goods dd:nth-child(2) b{font-size: 0.3rem;color: #fff;text-align: center;background: #ff4000;border-radius: 3px;line-height:140%;padding: 0 5px;margin-left:0.3rem; margin-top: -0.1rem;}
        .goods dd:nth-child(3){margin-top: 0.2rem;}
        .goods dd:nth-child(3) em{color: #999999;font-size: 0.45rem;}
        .goods .over{right: 0.3rem;bottom: -1.8rem;display: none;}
        .join h2{text-align: center;color: #707070;font-size: 0.5rem;line-height: 200%;}
        .join h2 i{color: #ff4000;font-family: 'arial';font-size: 0.7rem;display: inline-block;margin: 0 5px;font-weight: bold;}
        .join li{float: left;margin:0.3rem 0.45rem 0 0;}
        .join li:nth-child(1){margin-left: 1rem;}
        .join li:nth-child(1) i,.userList li:nth-child(1) span{text-align: center;font-size: 0.2rem;color: #fff;background: #ff4000;border-radius: 3px;padding: 0 5px;line-height: 0.4rem;}
        .join li:nth-child(1) i{bottom: -0.2rem;left: 33%;}
        .userList li:nth-child(1) span{margin-left: 0.35rem;}
        .join li img{border-radius: 100%;width: 2rem;height: 2rem;background: #f5f5f5;}
        .join .have img{border: 1px solid #ff997f;}
        .join .none img{border: 1px solid #d1d1d1;}
        .join p{width: 100%;text-align: center;color: #707070;font-size: 0.4rem;margin-top: 0.7rem;}
        .join .joinBtn{width: 55%;text-align: center;color: #fff;background: #ff4000;border-radius: 25px;display: block;margin: 0 auto;font-size: 0.5rem;line-height: 200%;margin-top:0.5rem;}
        .user{border-bottom: none;}
        .user li{border-bottom: 1px solid #d1d1d1;padding: 0.3rem 0;}
        .user li img{width: 1.2rem;height: 1.2rem;border-radius: 100%;background: #f5f5f5;border: 1px solid #ff997f;}
        .user li b,.user li span,.user li em{margin-top: 0.4rem;line-height: 0.4rem;}
        .user li em{color: #707070;font-size: 0.3rem;}
        .user li b{font-size: 0.4rem;margin-left: 0.2rem;color: #505050;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        @media screen and (min-width: 750px) {
            .goods .goodsImg{width: 40%;height:auto;border-radius: 5px;border: 1px solid #cccccc;}
            .goods dl{width: 45%;margin-left: 5%;}
            .goods dl a{font-size: 20px;}
            .goods dd:nth-child(2) i{color: #ff3300;font-size: 29px;}
            .goods dd:nth-child(2) b{font-size: 12px;color: #fff;text-align: center;background: #ff4000;border-radius: 3px;line-height: 150%;padding: 0 5px;margin-left:0.3rem; }
            .goods dd:nth-child(3) em{color: #999999;font-size: 29px;line-height: 200%;}
            .goods dd:nth-child(2){margin-top: 30px;}
        }
        @media screen and (min-width: 320px) {
            .goods dd:nth-child(2) b{margin-top: 0;}
        }
    </style>
</head>
<body>
    <div class="main">
        <header class="download">
            <a href="" target="_self">
                <img src="${rc.contextPath}/pages/images/groupBuy/img01.png"/>
            </a>
        </header>
        <section class="goods box relative">
            <a class="fl goodsImg" id="goodsImg" target="_blank">
                <img src=""/>
            </a>
            <dl class="fl">
                <dt><a href="#" class="tit" id="tit"></a></dt>
                <dd class="box">
                    <i class="fl">组团价:￥<span id="nowPrice"></span></i>
                    <b class="fl">折后9折</b>
                </dd>
                <dd>
                    <em>特卖价:<del>￥<span id="oldPrice"></span></del></em>
                </dd>
            </dl>
            <img src="${rc.contextPath}/pages/images/groupBuy/img03.png" class="abso over" width="30%">
        </section>
        <section class="join">
            <h2>本团口令 : <i id="code"></i><span class="overText">(长按复制)</span></h2>
            <ul class="box" id="userImg"></ul>
            <p>3人就成团，快来一起享受组团优惠～</p>
            <a class="joinBtn" id="joinBtn" href="#" target="_blank">立即参与</a>
        </section>
        <section class="user">
            <ul class="userList" id="userList"></ul>
        </section>
    </div>
<script src="${rc.contextPath}/pages/js/group/zepto.min.js"></script>
<script>
    $(function(){
        getStatus();
    });
    function getStatus(){
        $.ajax({
            url:"${rc.contextPath}/group/getGroupProductInfo",
            dataType:'jsonp',
            data:{'groupProductId':${groupProductId?c}},
            jsonp:'callback',
            success:function(data) {
                $("#code").text(data.code);
                $("#goodsImg img").attr("src",data.productImg);
                $("#tit").text(data.name);
                $("#nowPrice").text(data.groupPrice);
                $("#oldPrice").text(data.salesPrice);
                $("#joinBtn").attr("href",data.joinUrl);
                if(data.isAvailable==1){
                    $(".over").hide();
                    $(".overText").show();
                }else{
                    $(".over").show();
                    $(".overText").hide();
                    $("#joinBtn").text("查 看");
                }
               var userImgStr="",userListStr="";
                for(var i=0;i<3;i++){
                    var elem=data.groupPeople[i];
                    if(elem){
                        if (i == 0) {
                            userImgStr += '<li class="have relative"><img src="' + elem.image + '"/><i class="abso">团长</i></li>';
                        }else if (elem.image) {
                            userImgStr += '<li class="have relative"><img src="' + elem.image + '"/></li>';
                        }
                    }else {
                        userImgStr += '<li class="none relative"><img src="${rc.contextPath}/pages/images/groupBuy/img02.png"/></li>';
                    }
                }
                data.groupPeople.forEach(function(elem,i){
                    if(i==0){
                          userListStr+='<li class="box"><img class="fl" src="'+elem.image+'"/><b class="fl">'+elem.username+'</b><span class="fl">团长</span><em class="fr">'+elem.groupTime+'&nbsp;&nbsp;'+elem.detailMsg+'</em></li>';
                    }else{
                          userListStr += '<li class="box"><img class="fl" src="' + elem.image + '"/><b class="fl">' + elem.username + '</b><em class="fr">' + elem.groupTime + '&nbsp;&nbsp;' + elem.detailMsg + '</em></li>';
                    }
                });
                $("#userImg").html(userImgStr);
                $("#userList").html(userListStr);
                $(".goodsImg > img").height("auto");
            },
            jsonpCallback:'callback',
            timeout:3000
        });
    }
</script>
</body>
</html>