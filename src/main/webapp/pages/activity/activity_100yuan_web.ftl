<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link rel="shortcut icon" href="${rc.contextPath}/pages/images/favicon.ico">
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <title>左岸城堡双十二领优惠券</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;line-height: 150%;transition:all .3s linear;}
        body,html{background: #fbdd66;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body{background: #f52c3c;}
        img{border:none;font-size:0;vertical-align:top;}
        .box:before,.box:after{content:" ";height:0px;display:block;visibility:hidden;}
        .box:after {clear:both;}
        .box {*zoom: 1;}
        .fl{float: left;}
        .fr{float: right;}
        .abso{position: absolute;}
        .relative{position: relative;}
        .main{width: 540px;}
        .main img{width: 100%;display: block;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        .opcWrap{ width: 100%;height: 100%;background: rgba(0,0,0,.7);}
        .showBox{width: 80%;top:20%;left:5%;background: #fff;border-radius: 5px;padding: 30px 15px 30px 15px;}
        .showBox p{color: #000000;text-align: center;font-size: .45rem;line-height:150%;}
        .showBox a{width: 4.5rem;background: #e73835;border-radius: 2px;display: inline-block;color: #fff;text-align: center;text-decoration: none;font-size:.5rem;height: 30px;line-height: 30px;margin-top: .6rem;}
        .showBox .share{background-image: url("${rc.contextPath}/pages/images/activity/activityFoodParty/shareIcon.png");background-repeat: no-repeat;background-size:0.5rem;background-position: 8px center;text-align: left;width: 2.8rem;padding-left: 0.8rem;}
        .showBox article{text-align: center;}
        .showBox article a:nth-child(1){margin-right: 10px;}
        .opcWrapDiv{position: absolute;left: 0;top: 0;z-index: 100;display: none;width: 100%;}
        .showBox article .download{width: 70%;border-radius: 2px;background:#e73835;color: #fff;text-align: center;height: 35px;line-height: 35px;font-size: .5rem;margin-top: .3rem; }
        .main .phoneNo,.main #getBtn{width:4.63rem;left: 2.7rem;}
        .main .phoneNo{height: 0.8rem;line-height: 0.8rem;background:url("${rc.contextPath}/pages/images/activity/activity100yuan/phoneNum.png") no-repeat;background-size:100%;top:0;outline: none; text-align: center;font-size: .4rem;color: #7b390e;}
        .main #getBtn{height: 0.8rem;top: 1.1rem;}
        .main input::-webkit-input-placeholder{color: #949494;}
        .main input::-moz-placeholder{color: #949494;}
        .main input:-ms-input-placeholder{color: #949494;}
        .close{width:0.5rem;height:0.55rem;right:5px;top:5px;background:url("${rc.contextPath}/pages/images/activity/activityFoodParty/close.png") no-repeat;background-size:100%;}
    </style>
</head>
<body>
<div class="main" id="main">
    <header class="download">
        <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')" class="DownLoad" target="_self">
            <img src="${rc.contextPath}/pages/images/activity/activitySideBar.png"/>
        </a>
    </header>
    <div class="opcWrapDiv">
        <section class="opcWrap">

        </section>
        <div class="showBox abso">
            <p class="txt"></p>
            <article class="btnBox">
               <a href="http://download.gegejia.com" class="downloadBtn">下载左岸城堡APP</a>
            </article>
            <i class="close abso"></i>
        </div>
    </div>
    <section>
        <img src="${rc.contextPath}/pages/images/activity/activity100yuan/couponweb01.jpg"/>
        <img src="${rc.contextPath}/pages/images/activity/activity100yuan/couponweb02.jpg"/>
        <img src="${rc.contextPath}/pages/images/activity/activity100yuan/couponweb03.jpg"/>
        <div class="relative">
        	<form action="" method="post" id="form1">
        		<input type="hidden" name="requestFrom" value="${requestFrom}"/>
	            <input type="text" name="phoneNumber" class="phoneNo abso" placeholder="输入手机号码领取" />
	            <a id="getBtn" class="abso"><img src="${rc.contextPath}/pages/images/activity/activity100yuan/getBtn.png"/></a>
            </form>
            <img src="${rc.contextPath}/pages/images/activity/activity100yuan/couponweb04.jpg"/>
        </div>
        <img src="${rc.contextPath}/pages/images/activity/activity100yuan/couponweb05.jpg"/>
    </section>
</div>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
</script>
<script src="http://activity.gegejia.com:81/activity/pages/resource/js/jquery-1.11.2.min.js"></script>
<script>
    $(function(){
        $('#getBtn').on('click',function(){
        	var phoneNumber=$("#form1 input[name='phoneNumber']").val().replace(" ","");
        	var requestFrom=$("#form1 input[name='requestFrom']").val();
            if(phoneNumber==""){
                $(".txt").html("手机号码不能为空.");
                $(".downloadBtn").hide();
                $('.opcWrapDiv').show().find(".opcWrap").css('height',$(".main").height()+"px").css("padding-bottom","100px");
            }else if(!/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.test(phoneNumber)){
                $(".txt").html("请输入正确的手机号.");
                $(".downloadBtn").hide();
                $('.opcWrapDiv').show().find(".opcWrap").css('height',$(".main").height()+"px").css("padding-bottom","100px");
            }else{
            	$.ajax({
                    url:"${rc.contextPath}/activity/redFriday/receive",
                    dataType:'json',
                    data:{phoneNumber:phoneNumber,requestFrom:requestFrom},
                    type: 'post',
                    success:function(data){
                        if(data.status=="1"){
                        	$(".txt").html("100元优惠券组合红包已放入帐号中，快去买好吃的吧！");
                        	$(".downloadBtn").show();
                            $('.opcWrapDiv').show().find(".opcWrap").css('height',$(".main").height()+"px").css("padding-bottom","20px");
                        }else if(data.status == "2"){
                        	$(".txt").html("哎呀~您这个手机号已经领过优惠礼包了，快去帐号内看看！");
                        	$(".downloadBtn").show();
                            $('.opcWrapDiv').show().find(".opcWrap").css('height',$(".main").height()+"px").css("padding-bottom","20px");
                           
                        }else{
                        	$(".txt").html(data.msg);
                        	$(".downloadBtn").show();
                            $('.opcWrapDiv').show().find(".opcWrap").css('height',$(".main").height()+"px").css("padding-bottom","20px");
                        } 
                    }
                });
            }
        });
        $('.opcWrap,.close').on('click',function(e){
            $('.opcWrapDiv').hide();
        });
    });
</script>
</body>
</html>