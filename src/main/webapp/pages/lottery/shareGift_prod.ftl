<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>左岸城堡土豪爱分享，宴请我的好友</title>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<script>
    //淘宝H5自适应解决方案
    //https://github.com/amfe/lib-flexible
    ;(function(win, lib) {
        var doc = win.document;
        var docEl = doc.documentElement;
        var metaEl = doc.querySelector('meta[name="viewport"]');
        var flexibleEl = doc.querySelector('meta[name="flexible"]');
        var dpr = 0;
        var scale = 0;
        var tid;
        var flexible = lib.flexible || (lib.flexible = {});

        if (metaEl) {
            console.warn('将根据已有的meta标签来设置缩放比例');
            var match = metaEl.getAttribute('content').match(/initial\-scale=([\d\.]+)/);
            if (match) {
                scale = parseFloat(match[1]);
                dpr = parseInt(1 / scale);
            }
        } else if (flexibleEl) {
            var content = flexibleEl.getAttribute('content');
            if (content) {
                var initialDpr = content.match(/initial\-dpr=([\d\.]+)/);
                var maximumDpr = content.match(/maximum\-dpr=([\d\.]+)/);
                if (initialDpr) {
                    dpr = parseFloat(initialDpr[1]);
                    scale = parseFloat((1 / dpr).toFixed(2));
                }
                if (maximumDpr) {
                    dpr = parseFloat(maximumDpr[1]);
                    scale = parseFloat((1 / dpr).toFixed(2));
                }
            }
        }

        if (!dpr && !scale) {
            var isAndroid = win.navigator.appVersion.match(/android/gi);
            var isIPhone = win.navigator.appVersion.match(/iphone/gi);
            var devicePixelRatio = win.devicePixelRatio;
            if (isIPhone) {
                // iOS下，对于2和3的屏，用2倍的方案，其余的用1倍方案
                if (devicePixelRatio >= 3 && (!dpr || dpr >= 3)) {
                    dpr = 3;
                } else if (devicePixelRatio >= 2 && (!dpr || dpr >= 2)){
                    dpr = 2;
                } else {
                    dpr = 1;
                }
            } else {
                // 其他设备下，仍旧使用1倍的方案
                dpr = 1;
            }
            scale = 1 / dpr;
        }

        docEl.setAttribute('data-dpr', dpr);
        if (!metaEl) {
            metaEl = doc.createElement('meta');
            metaEl.setAttribute('name', 'viewport');
            metaEl.setAttribute('content', 'initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
            if (docEl.firstElementChild) {
                docEl.firstElementChild.appendChild(metaEl);
            } else {
                var wrap = doc.createElement('div');
                wrap.appendChild(metaEl);
                doc.write(wrap.innerHTML);
            }
        }

        function refreshRem(){
            var width = docEl.getBoundingClientRect().width;
            if (width / dpr > 750) {
                width = 750 * dpr;
            }
            var rem = width / 10;
            docEl.style.fontSize = rem + 'px';
            flexible.rem = win.rem = rem;
        }

        win.addEventListener('resize', function() {
            clearTimeout(tid);
            tid = setTimeout(refreshRem, 300);
        }, false);
        win.addEventListener('pageshow', function(e) {
            if (e.persisted) {
                clearTimeout(tid);
                tid = setTimeout(refreshRem, 300);
            }
        }, false);

        if (doc.readyState === 'complete') {
            doc.body.style.fontSize = 12 * dpr + 'px';
        } else {
            doc.addEventListener('DOMContentLoaded', function(e) {
                doc.body.style.fontSize = 12 * dpr + 'px';
            }, false);
        }


        refreshRem();

        flexible.dpr = win.dpr = dpr;
        flexible.refreshRem = refreshRem;

    })(window, window['lib'] || (window['lib'] = {}));
</script>
<style>
	*{margin: 0;padding: 0;}
	em,i{font-style: normal;}
	html,body{font-family: sans-serif;}
	body{background: url(http://m.gegejia.com/ygg/pages/images/shareGiftImages/bg.jpg) center repeat;}
	img{max-width: 10rem; display: block;margin: 0 auto;}
	.text{text-align: center;font-size: .6rem;line-height: 1rem;color: #713b0f;}
	.img1{height: 7rem;}
	.text2{font-size: .4rem;line-height: .6rem;color: #713b0f;text-align: center;}
	.text2 span{display: inline-block;background: #ed433f;padding: 0 .2rem;color: #fff;margin: .5rem 0;}
	.share {color: #fff;text-align: center;margin-bottom: 1rem;line-height: 1rem;}
	.share span.bgimg{display: inline-block;background: url(http://m.gegejia.com/ygg/pages/images/shareGiftImages/wx.png) left center no-repeat;padding-left: .8rem;}
	.share a.bgcolor{color: #fff;background: #713b0f;display: inline-block;padding:.1rem 1.2rem;border-radius: 1rem;text-decoration: none;font-size: .5rem;}
</style>
<body>
	<img class="img1" src="${rc.contextPath}/pages/images/shareGiftImages/1.jpg" alt=""/>
	<div class="text">
	    <p>分享后你的好友可获得</p>
	    <p>总价值777元的吃货礼包一份</p>
	</div>
	<img src="${rc.contextPath}/pages/images/shareGiftImages/play.gif" alt=""/>
	<p class="text2">分享成功后，你将获得一张<span>&yen;15</span>无限制现金券</p>
	<p class="share"><a href="ggj://open/resource/share" class="bgcolor"><span class="bgimg">分享到朋友圈</span></a></p>
	
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