<!DOCTYPE html>
<html lang="en">
<head>
	<title>领取左岸城堡20元现金券</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<style>
	html{font-size: 100px;}
	body{font-size: 0.14rem;/*实际相当于14px*/}
	body{
		padding:0;
		margin:0;
		background: #fdd43c;
	}
	.container{
		width: 3.2rem;
	/*	max-width: 640px;*/
		margin: 0 auto;
		margin-bottom: 100px;
		position: relative;
		font
	}
	.box-img{
		width: 100%;
		display: block;
	}
	.invitation_code{
		width: 3.2rem;
		position: absolute;
		/*top: 0;*/
		bottom: 0.82rem;
		left: 0;

		font-size: 0.16rem;
		text-align: center;
		 letter-spacing: 3px;
		 font-family: sans-serif;
	}
	.down{display: block; position: fixed;bottom: 0;left: 0;width: 100%; height: 35px;line-height: 35px;background: #fff;text-align: center;padding: 8px 0;text-decoration: none;}
.down span{display: block;background: #ff3e00;border-radius: 3px;color: #fff;margin:0 8px;font-size: 16px;}

	</style>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="http://m.gegejia.com/ygg/pages/js/gegejia.js"></script>
</head>
<body>
	<div class="container">
		<img class="box-img" src="http://m.gegejia.com/ygg/pages/images/01.jpg" alt="">
		<img class="box-img" src="http://m.gegejia.com/ygg/pages/images/02.jpg" alt="" >
		<img class="box-img" src="http://m.gegejia.com/ygg/pages/images/03.jpg" alt="">
		<span class="invitation_code">${code!""}</span>
	</div>
	<a href="http://download.gegejia.com" class="down">
        <span>下载左岸城堡APP</span>
    </a>	
</body>
<script>

(function (doc, win) {
	// 分辨率Resolution适配
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth > 640){
            	clientWidth = 640;
            }
            docEl.style.fontSize = 100 * (clientWidth / 320) + 'px';
        };

    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
    	
})(document, window);	
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

</html>