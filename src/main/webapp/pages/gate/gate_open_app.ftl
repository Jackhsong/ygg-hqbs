<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link href="${rc.contextPath}/pages/images/favicon.ico" rel="shortcut icon">
    <title>任意门，一起穿越吧！</title>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
        //淘宝H5自适应解决方案
        //https://github.com/amfe/lib-flexible
        (function(c,f){var s=c.document;var b=s.documentElement;var m=s.querySelector('meta[name="viewport"]');var n=s.querySelector('meta[name="flexible"]');var a=0;var r=0;var l;var d=f.flexible||(f.flexible={});if(m){console.warn("将根据已有的meta标签来设置缩放比例");var e=m.getAttribute("content").match(/initial\-scale=([\d\.]+)/);if(e){r=parseFloat(e[1]);a=parseInt(1/r)}}else{if(n){var j=n.getAttribute("content");if(j){var q=j.match(/initial\-dpr=([\d\.]+)/);var h=j.match(/maximum\-dpr=([\d\.]+)/);if(q){a=parseFloat(q[1]);r=parseFloat((1/a).toFixed(2))}if(h){a=parseFloat(h[1]);r=parseFloat((1/a).toFixed(2))}}}}if(!a&&!r){var p=c.navigator.appVersion.match(/android/gi);var o=c.navigator.appVersion.match(/iphone/gi);var k=c.devicePixelRatio;if(o){if(k>=3&&(!a||a>=3)){a=3}else{if(k>=2&&(!a||a>=2)){a=2}else{a=1}}}else{a=1}r=1/a}b.setAttribute("data-dpr",a);if(!m){m=s.createElement("meta");m.setAttribute("name","viewport");m.setAttribute("content","initial-scale="+r+", maximum-scale="+r+", minimum-scale="+r+", user-scalable=no");if(b.firstElementChild){b.firstElementChild.appendChild(m)}else{var g=s.createElement("div");g.appendChild(m);s.write(g.innerHTML)}}function i(){var t=b.getBoundingClientRect().width;if(t/a>750){t=750*a}var u=t/10;b.style.fontSize=u+"px";d.rem=c.rem=u}c.addEventListener("resize",function(){clearTimeout(l);l=setTimeout(i,300)},false);c.addEventListener("pageshow",function(t){if(t.persisted){clearTimeout(l);l=setTimeout(i,300)}},false);if(s.readyState==="complete"){s.body.style.fontSize=12*a+"px"}else{s.addEventListener("DOMContentLoaded",function(t){s.body.style.fontSize=12*a+"px"},false)}i();d.dpr=c.dpr=a;d.refreshRem=i})(window,window["lib"]||(window["lib"]={}));
    </script>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        body{font-family:'microsoft yahei';width: 100%;background: #fffdf2;line-height: 150%;}
        body em,body i{font-style: normal;}
        body a{text-decoration:none;}
        body b{font-weight: normal;}
        li,dd {list-style:none;}
        body{background: #e8ba58;}
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
        .main img{display: block;width: 100%;}
        #txt{width: 4.5rem;height: 1rem;left:2.7rem;top:1.5rem;background: url("${rc.contextPath}/pages/images/gateImage/img07.jpg") no-repeat;background-size: 100%;text-align: center;color: #000000;font-size: 0.38rem;}
        #btn{width:4.6rem;height: 1rem;left: 2.7rem;top: 0;}
        #texts{width: 47%;left: 2.7rem;color: #863526;}
        #texts dt{text-align: center;font-size: 0.5rem;line-height: 180%;margin: 0.1rem 0;}
        #texts dd{font-size: 0.37rem;font-family: '宋体';line-height: 130%;}
        .content{width: 80%;margin: 4rem auto 0 auto;background: #f94c50;border-radius: 15px;padding: 10px;}
        .content .inner{background: #ffebec;border-radius: 8px;}
        #opc{display: none;width: 100%;height: 100%;background: url("${rc.contextPath}/pages/images/gateImage/repeat.png") repeat;top: 0;left: 0;}
        #close{width: 1.5rem;height: 1.5rem;background: #f74d50;border-radius: 100%;right: -0.6rem;top: -0.6rem;background-image: url('${rc.contextPath}/pages/images/gateImage/close.png');background-position: center center;background-size: 50%;background-repeat: no-repeat;}
        .showText{margin: 0 auto;color: #c72c30;line-height: 150%;display: none;}
        .inner01{text-align: center;width: 95%;font-size: 0.7rem;padding: 1rem 0;}
        .inner02 {width: 90%;padding: 0.5rem 0;}
        .inner02 dt{ width: 80%;margin: 0 auto;font-size: 0.6rem;line-height: 150%;}
        .inner02 dt p{width: auto;margin: 0 auto;}
        .btn{background: #f74d50;width:2.5rem;text-align: center;border-radius: 3px;box-shadow: 1px 1px 0 #dc393e;color: #ffffff;font-size: 0.4rem;line-height: 0.5rem;padding: 0.15rem 0.3rem;display: inline-block;margin: 0.5rem 0 0 0.2rem;}
        .inner03{width:85%;padding: 0.6rem 0;}
        .inner03 dt{text-align: center; font-size: 0.65rem; letter-spacing: 2px;}
        .inner03 .gift{width: 90%;margin: 0.5rem auto 0 auto;height: 2.1rem;background: url("${rc.contextPath}/pages/images/gateImage/img08.png") no-repeat; background-size: 100%;}
        .inner03 .gift i,.inner03 .gift b{display: block;text-align: center;margin: 0 auto;color: #fffad2;line-height: 140%;}
        .inner03 .gift i{font-size: 0.6rem;padding: 0.42rem 0 0.1rem 0;color: #fffad2;}
        .inner03 .gift b{font-size: 0.3rem;color: #fffbd2;}
        .inner03 p{text-align: center;font-size: 0.4rem;line-height: 150%;margin-top: 0.2rem;}
        .inner03 .btn{margin-top: 0.25rem;}
        .rules{width: 1.8rem;height: 0.6rem;right: 0.7rem;top: 2rem;}
        .ruleText{width: 95%;padding: 0.4rem 0 0.8rem 0;margin: 0 auto;}
        .ruleText dt{color: #ad161b;text-align: center;font-size: 0.6rem;line-height: 200%;}
        .ruleText dd{color: #c72c30;font-size: 0.4rem;margin-top: 0.2rem;line-height: 150%;}
        .cyNum{text-align:center;width:4.5rem;overflow:hidden;background:#863526;border-radius:10px;height:0.45rem;line-height:0.45rem;font-size:0.3rem;color:#fff1cc;bottom:0;left:2.75rem;bottom:0.2rem;}
        @media screen and (max-width: 800px) {
            .main{width:100%;}
        }
        .protips{ display: none; padding: 40px 10px;  width: 85%; background: rgba(0,0,0,0.8); line-height: 29px; position: absolute; top: 80%; left: 40%; margin-left: -33%; border-radius: 3px; -webkit-border-radius: 3px; -moz-border-radius: 3px; -ms-border-radius: 3px; color: #ffffff; font-size: 28px; text-align: center; z-index: 1}
         .footerX{ position: relative; }
        .footerX .weixinBg{ position: absolute;  background: url("${rc.contextPath}/pages/images/gateImage/weixinbg.png") no-repeat;background-size: 100%;position: absolute;top: -0.8rem;left: 0.1rem; height:4.86rem; width: 2.7rem; }
        #inputText02{ position: absolute;  width: 1.94rem;  text-align: center;color: #863526; font-size: 0.27rem;top:1.15rem; left: 0.38rem; background: none; border: none; padding-bottom: 3.66rem;}
    </style>
</head>
<body>
	<input type="hidden" value=${gateId} id="gateId" />
	<input type="hidden" value=${accountId} id="accountId" />
	<div class="protips"></div>
    <div class="main">
        <div class="relative">
            <a class="rules abso" id="rule"></a>
            <img src="${rc.contextPath}/pages/images/gateImage/img01.jpg"/>
        </div>
		<div class="relative">
            <input type="text" placeholder="输入口令，穿越得现金券" class="abso" id="txt"/>
            <img src="${rc.contextPath}/pages/images/gateImage/img02.jpg"/>
        </div>
        <div class="relative">
        	<#if login>
        	<span id="btn" class="abso"></span>
            <img src="${rc.contextPath}/pages/images/gateImage/img03.jpg"/>
        	<#else>
        	<span class="abso"></span>
            <a href="ggj://alert/account/login"><img src="${rc.contextPath}/pages/images/gateImage/img03.jpg"/></a>
        	</#if>
        </div>
        <img src="${rc.contextPath}/pages/images/gateImage/img04.jpg"/>
        <div class="relative">
            <dl id="texts" class="abso">
                <dt>${gate.theme!""}</dt>
                <dd>${gate.desc!""}</dd>
            </dl>
            <i class="cyNum abso">当前已有${gate.receiveAmount?c}人穿越成功</i>
            <img src="${rc.contextPath}/pages/images/gateImage/img05.jpg"/>
        </div>
        <div class="footerX">
            <img src="${rc.contextPath}/pages/images/gateImage/img06.jpg"/>
            <div class="weixinBg" id="weixinBg">
                <div id='inputText02'>lovegegejia10</div>
            </div>
        </div>
        <div class="abso" id="opc">
             <div class="content relative">
                 <i class="abso" id="close"></i>
                 <div class="inner">
                     <!--多次点击-->
                    <p class="inner01 showText">您今天已成功穿越过任意门，穿越所获现金券请至“我的优惠券”中查看~</p>
                     <!--未猜中答案-->
                     <dl class="inner02 showText">
                         <dt>
                            <p>口令木有破解成功</p>
                            <p>表灰心，再来一次！</p>
                         </dt>
                         <dd>
                             <span class="btn" id="tryAgain">再来一次</span>
                             <a href="ggj://open/resource/share"><span class="btn">让朋友帮忙</span></a>
                         </dd>
                     </dl>
                     <!--猜中答案-->
                     <dl class="inner03 showText">
                         <dt>恭喜猜中！</dt>
                         <dd>
                             <div class="gift">
                                 <i id="couponInfo"></i>
                                 <b id="validTime"></b>
                             </div>
                         </dd>
                         <dd>
                             <p id="receiveTip"></p>
                         </dd>
                         <dd>
                             <a href="ggj://redirect"><span class="btn">去逛逛</span></a>
                             <a href="ggj://open/resource/share"><span class="btn">让朋友也猜猜</span></a>
                         </dd>
                     </dl>
                     <!--游戏规则-->
                     <dl class="ruleText showText">
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

	function showTipMsg(msg)
	{
		 /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
	    /* 弹出层 */
		$('.protips').html(msg);
		var scrollTop=$(document).scrollTop();
		var windowTop=$(window).height();
		var xtop=windowTop/2;
		$('.protips').css('top',xtop);
		$('.protips').css('display','block');
		setTimeout(function(){			
			$('.protips').css('display','none');
		},2000);
	}
	
    $(function(){
    	 /*var win = navigator.userAgent.toLowerCase();
       if (/iphone|ipad|ipod/.test(win)) {
             $("#weixinBg").html("<input type='text' id='inputText02' readonly='readonly' value='lovegegejia10'>");
         } else if (/android/.test(win)) {
            $("#weixinBg").html("<div id='inputText02'>lovegegejia10</div>");
         }else{
            $("#weixinBg").html("<div id='inputText02'>lovegegejia10</div>");
         }*/
        $("#close").click(function(){
            $("#opc").hide("fast");
        });
        
        $("#tryAgain").click(function(){
        	$("#opc").hide("fast");
        	$("#txt").val('');
        });
        $("#rule").click(function(){
            $("#opc").show("fast");
            $(".showText").eq(3).show().siblings().hide();//0 1 2 3
        });
        $("#btn").click(function(){
        	var answer = $("#txt").val();
        	var gateId = $("#gateId").val();
        	var accountId = $("#accountId").val();
        	if($.trim(answer) == ''){
        		showTipMsg("请输入口令~");
        		return;
        	}else{
        		$.ajax({
                    url: '${rc.contextPath}/gate/activity/app/openDoor',
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    data: {
                    	answer:answer,
                    	gateId:gateId,
                    	accountId:accountId
                    },
                    success: function(data){
                        if(data.status == 1){
                        	// 领取成功
                        	$("#couponInfo").text(data.couponInfo);
       						$("#validTime").text(data.validTime);
       						$("#receiveTip").text(data.receiveTip);
       						$("#txt").val('');
                        	$("#opc").show("fast").css("height","150%");
            				$(".showText").eq(2).show().siblings().hide();
                        	
                        }else if(data.status == 2){
                        	// 口令错误
                        	$("#opc").show("fast").css("height","150%");
                        	$(".showText").eq(1).show().siblings().hide();
                        }else if(data.status == 3){
                        	// 已经领取奖励
                        	$("#txt").val('');
                        	$("#opc").show("fast").css("height","150%");
                        	$(".showText").eq(0).show().siblings().hide();
                        }else if(data.status == 4){
                        	showTipMsg("用户不存在");
                        	return;
                        }else if(data.status == 5){
                        	showTipMsg("等你这么久，现在才来~ 奖品都已经被抢光了~~");
                        	return;
                        }else{
                        	showTipMsg("啊~哦~ 服务器开小差了，稍后再试吧");
                        	return;
                        }
                    },
                    error: function(xhr){
                        dialog.msg('服务器忙，刷新后重试' + xhr.status);
                        dialog.closeLoading();
                        _this.removeClass('on');
                    }
        		});
        	}
            
        });
    });
</script>
<script>
	wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${(appid)!"wx7849b287f9c51f82"}', // 必填，公众号的唯一标识
		    timestamp: ${(timestamp)!"0"}, // 必填，生成签名的时间戳
		    nonceStr: '${(nonceStr)!"0"}', // 必填，生成签名的随机串
		    signature: '${(signature)!"0"}',// 必填，签名，见附录1
		    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
/* wx.error(function(res){
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		    alert(res.errMsg);
		});
*/
	wx.ready(function () {
		
			 // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		     wx.onMenuShareTimeline({
		         title: '${name!""}', // 分享标题
		         link: '${(link)!"0"}',
		         imgUrl: '${(imgUrl)!""}' // 分享图标
		     });
		     
		     // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			  wx.onMenuShareAppMessage({
			      title: '${name!""}',
			      desc: '${wxShareDesc!""}',
			      link: '${(link)!"0"}',
			      imgUrl: '${(imgUrl)!""}'
			});
});
</script>
</body>
</html>