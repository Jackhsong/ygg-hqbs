<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>送你777元大礼包，这一刻请叫我土壕！</title>
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
	<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<style>
    *{margin: 0;padding: 0;font-family: 'microsoft yahei';}
    em,i{font-style: normal;}
    html,body{font-family: sans-serif;}
    body{background: url(${rc.contextPath}/pages/images/shareGiftImages/bg.jpg) center repeat;}
    img{max-width: 10rem; display: block;margin: 0 auto;}
    .down,.down a{height: 1.85rem;text-align: center;display: block;background: #fff;width: 100%;}
    .down a{position: fixed;top: 0;z-index: 99;}
    .down img{height: 1.85rem;}
    .mtext{text-align: center;font-size: .35rem;line-height: .5rem;color: #713b0f;}
    .img1{height: 5rem;}
    .text2{margin: .3rem 0;}
    .phone{height: 1rem;line-height: .8rem;font-size: .5rem; width: 7rem;text-align: center;border:none;background: #fff; border-radius: 8px;margin: .3rem auto;display: block;}
    .btn{display: block;background: #863c00;color: #fff;margin: .3rem auto;width: 7rem;height: 1rem;text-align: center;line-height: 1rem;font-size: .5rem;border-radius: 8px; text-decoration: none;}
    .copyright{color: #a8840b;display: block;font-size: .4rem;background: rgba(255,255,255,.5);margin: .5rem auto;border-radius: .8rem;height: .8rem;line-height: .8rem; width: 5.5rem;text-align: center;}
    /*msg*/
    .dialog-msg { border-radius: 4px; background: rgba(0, 0, 0, .7); color: #fff; font-size: .5rem; position: fixed; z-index: 999; top: 50%; max-width: 10rem; display: block; }
    .dialog-msg div.text { padding: .4rem; }
    /*loading*/
    .mask { width: 100%; height: 100%; position: absolute; top: 0; left: 0; background: none; z-index: 9999; }
    .loading {font-size: .5rem; width: 3rem; height: 3rem; background: rgba(0, 0, 0, .7); position: fixed; left: 50%; top: 50%; border-radius: .15rem; color: #fff; margin-left: -1.5rem; margin-top: -1.5rem;text-align: center; }
    .loading img{width: 1.2rem;height: 1.2rem;}
    .loading p:nth-child(1){padding: .4rem 0;}
    .box2{display: none;}
    .box2 .mtext{border:2px solid #a8840b;margin: .5rem;padding: .5rem;border-radius: 15px;background: #feea87;line-height: .7rem;font-size: .4rem;}
    .box{ position: relative;width: 10rem;  margin: 0 auto;}
    .box1,.box2{ position: absolute;width: 100%;}
    .share_wx{ display: none; width: 100%;height: 100%;background: rgba(0,0,0,.7);position: absolute;left: 0;top: 0;z-index: 100;}
    .share_wx img{position: fixed;top: -1rem;left: 0.8rem;width: 100%;}
    .share_wx .close{width: 2rem;height: 2rem;background: url("${rc.contextPath}/pages/images/shareGiftImages/close.png") no-repeat;background-size: 100%;position: absolute;top: 5px;left: 0;display: block;}
</style>
<body>
<input type="hidden" value=${giftId?c} id="giftId" />
<div class="down">
    <a href="http://download.gegejia.com"><img src="${rc.contextPath}/pages/images/shareGiftImages/down.jpg" alt=""/></a>
</div>
<img class="img1" src="${rc.contextPath}/pages/images/shareGiftImages/1.jpg" alt=""/>
<div class="box">
    <div class="box1" id="box1">
        <div class="mtext">
            <p>您的土豪朋友送了你一个777元的吃货大礼包，</p>
            <p>并邀您8月19日10点参加12星座吃货盛宴。</p>
        </div>
        <img src="${rc.contextPath}/pages/images/shareGiftImages/play.gif" alt=""/>
        <p class="mtext text2">快领礼包一起来参加！</p>
        <input type="number" class="phone" id="phone" placeholder="输入手机号码领取"/>
        <a class="btn" id="btn" href="javascript:;">立即领取</a>
        <p class="copyright">左岸城堡：进口食品免税店</p>
    </div>
    <div class="box2" id="box2">
        <div class="mtext">
            <p>777元的吃货大礼包已经发放至您的账户，</p>
            <p>请下载左岸城堡APP后，</p>
            <p>使用此手机号注册登录后查看。</p>
        </div>
        <a class="btn" id="btn2" href="javascript:;">分享礼包给好友</a>
        <p class="copyright">左岸城堡：进口食品免税店</p>
    </div>
</div>
<div class="share_wx" id="share_wx">
    <img src="${rc.contextPath}/pages/images/shareGiftImages/share.png">
    <i class="close "></i>
</div>
<script>
    $(function(){
        var dialog = {

            loading: function() {
                $('.loading').remove();

                var bodyH = document.body.clientHeight;

                var html = '<div class="mask" style="height:' + bodyH + 'px;">' +
                        '<div class="loading">' +
                        '<p><img src="http://m.gegejia.com/ygg/pages/images/shareGiftImages/loading.gif" /></p>' +
                        '<p>正在加载</p>' +
                        '</div>' +
                        '</div>';

                $(html).appendTo('body')
            },

            closeLoading: function() {
                $('.loading,.mask').remove();
            },

            //消息框
            msg: function(msg, callback) {

                $('.dialog-msg').remove();

                var time = new Date().getTime() + '' + Math.floor(Math.random() * 10 + 1);
                var html = '<div class="dialog-msg" id="' + time + '"><div class="text">' + msg + '</div></div>';

                $(html).appendTo('body');

                var $dialogMsg = $('#' + time),
                        _w = $dialogMsg.width(),
                        _h = $dialogMsg.height();

                $dialogMsg.css({
                    'marginLeft': -_w / 2,
                    'marginTop': -_h / 2,
                    'left': '50%'
                });

                setTimeout(function() {
                    $dialogMsg.fadeOut(function() {
                        $(this).remove();
                        callback && callback();
                    });
                }, 2400);
            }
        }

        var $phone = $('#phone'),
            $btn = $('#btn'),
            reg = /^1[3-8][0-9]{9}$/;
            
        var giftId = $("#giftId").val();

        $btn.on('click',function() {

            var _this = $(this);

            var _val = $phone.val();

            if (!reg.test(_val)) {
                dialog.msg('亲，这个真的是手机号码么？');
                return;
            }

            dialog.loading();

            $.ajax({
                url: '${rc.contextPath}/activity/gift/draw',
                type:'post',
                dataType: 'json',
                data: {phone: _val,giftId: giftId},
                success: function(data){
                    if(data.status == 1){
                        dialog.msg(data.msg);

                        $('#box1').fadeOut();
                        $('#box2').fadeIn();
                    }else{
                        dialog.msg(data.msg);
                    }
                    dialog.closeLoading();
                },
                error: function(xhr){
                    dialog.closeLoading();
                    dialog.msg('领取失败，刷新后重试' + xhr.status);
                }
            })
        });

        //分享
        $('#btn2').on('click',function(){
            $('#share_wx').show().css('height',$(document).height());
        })
        $('#share_wx').on('click',function(){
            $('#share_wx').hide();
        });

    })
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