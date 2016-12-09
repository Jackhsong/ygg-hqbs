<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>给你2次成为土壕的机会,快来试试~</title>
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
            if (width / dpr > 640) {
                width = 640 * dpr;
            }
            //var rem = width / 10;
            var rem = width / 320 * 20; //基准设置为20px
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
<script type="text/javascript" src="${rc.contextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}"></script>
<script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
<style>
*{margin: 0;padding: 0;}
em,i{font-style: normal;}
html,body{font-family: sans-serif;}
body{background: url(${rc.contextPath}/pages/images/lotteryImage/bg.png) center repeat;font-family: 'microsoft yahei';}
img{max-width: 16rem; display: block;margin: 0 auto;}
.text p{font-size: .4rem;color: #fdf0c5;text-align: center;line-height: .8rem;}
.text p span{background: #c20a0a;padding: .1rem 0.2rem 0.14rem .2rem;border-radius: 12px;}
.text{padding-bottom: .3rem;}
.btn a{display: block;width:10rem;height: 1.8rem;line-height: 1.8rem;font-size: .9rem;text-decoration: none;border-radius: 1rem;box-shadow: 3px 3px 0 #8a2000;font-family: 'microsoft yahei'; background: #ffd33c;margin: 0 auto;margin-top:0.5rem;text-align: center;color: #653700;}
.nums{text-align: center;font-size: .5rem;color: #8c0000;margin: .5rem 0;}
.share {color: #fff;text-align: center;margin-bottom: 1rem;line-height: 0.8rem;margin-top: 0.7rem;}
.share span{  display: inline-block;background: url(${rc.contextPath}/pages/images/lotteryImage/wx.png) left 0.05rem no-repeat;padding-left: 0.8rem;background-size: contain;}
.share img{display: inline-block;vertical-align: middle;}
.share a{color: #fdf0c5;text-decoration: none;font-size: .5rem;padding-bottom: 0.03rem;border-bottom: 1px solid #fdf0c5;}
.card{width:11.2rem;height: 5.3rem; text-align: center;background: url(${rc.contextPath}/pages/images/lotteryImage/card.png) center no-repeat; background-size: contain; margin: 0 auto; }
.card_price{font-size: 1.1rem;color: #ce3f2c;height: 4.3rem;font-family: 'microsoft yahei';}
.card_price strong{display: block;padding-top: 1.6rem;}
.card_price10000 strong{display: block;padding-top: 0.8rem;width: 56%;margin: 0 auto;line-height: 1.4rem;}
.card_price strong i{font-family: 'microsoft yahei';}
.card_txt{max-width:16rem;margin: 0 auto;color: #fdf0c5;text-align: center;line-height: 1rem;font-size: .6rem;}
.box2 .text p{font-size: .6rem;}
.play_box{text-align: center;margin: .5rem 0 1rem 0;}
.play_box a.btn{width: 7rem;height: 2rem; display: inline-block; line-height: 2rem; font-size: .7rem;}
.play_box a.btn:nth-child(1){margin-right: 1rem;}
.share_text{width: 14rem;margin: 0 auto; text-align: right;font-size: .6rem;color: #8c0000;margin-top: .5rem;}
.img_t{width: 100%;}

/*msg*/
.dialog-msg { border-radius: 4px; background: rgba(0, 0, 0, .7); color: #fff; font-size: .7rem; position: fixed; z-index: 999; top: 50%; max-width: 10rem; display: block; }
.dialog-msg div.text { padding: .6rem; }
/*loading*/
.mask { width: 100%; height: 100%; position: absolute; top: 0; left: 0; background: none; z-index: 9999; }
.loading { width: 3.2rem; height: 3.2rem; background: rgba(0, 0, 0, .7); position: fixed; left: 50%; top: 50%; border-radius: .15rem; color: #fff; margin-left: -1.6rem; margin-top: -1.6rem;text-align: center; }
.loading img{width: 1.5rem;height: 1.5rem;}
.loading p:nth-child(1){padding: .3rem 0;}
.box{max-width: 16rem;margin: 0 auto;position: relative;}
.box1,.box2{position: absolute;margin: 0 auto;width: 16rem;}
</style>
<body>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/1.jpg"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/2.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/3.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/4.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/5.png"/>
<div class="box">
<input type="hidden" value=${lotteryId?c} id="lotteryId" />
<input type="hidden" value=${accountId?c} id="accountId" />
    <div class="box1" id="box1">
        <div class="text">
            <p>偷偷告诉你：<span>8月19日10点</span> 12星座吃货盛宴准点开席！</p>
            <p>满100元减100元白吃白喝优惠券已出现，最高满减1万元。</p>
            <p>能不能成为土壕吃货，看你的了！</p>
        </div>
        <div class="btn">
             <#if login>
				<a href="javascript:;" id="btn">立即抽奖</a>
			<#else>
				<a href="ggj://alert/account/login" id="btn_error">立即抽奖</a>
			</#if>
        </div>
        <p class="nums">今天剩余抽奖机会：<i id="nums">0</i>次</p>
        <p class="share"><span><a href="ggj://open/resource/share">分享给好友，每天增加2次机会>></a></span></p>
    </div>

    <div class="box2" id="box2" style="display:none">
        <div class="text">
            <p>8月19日10点12星座吃货盛宴，领好券放开吃！</p>
        </div>
        <div class="card" id="card"></div>
        <div class="play_box btn">
        <a href="javascript:;" class="btn" id="again">再玩一次</a>
             <a href="ggj://open/resource/share" class="btn">到朋友圈炫耀一下</a>
            <p class="share_text">分享给好友，每天增加2次机会</p>
        </div>
    </div>

</div>
<script>
$(function(){

    var nums = 0;

    var lotteryId = $('#lotteryId').val();
    var accountId = $('#accountId').val();
    
    var dialog = {

        loading: function() {
            $('.loading').remove();

            var bodyH = document.body.clientHeight;

            var html = '<div class="mask" style="height:' + bodyH + 'px;">' +
                '<div class="loading">' +
                '<p><img src="http://m.gegejia.com/ygg/pages/images/lotteryImage/loading.gif" /></p>' +
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

    //获取用户抽奖次数 是否需要用户ID?
    function getUserNums(){
        $.ajax({
            url: '${rc.contextPath}/activity/lottery/getChance',
            type: 'post',
            cache: false,
            dataType: 'json',
            data: {
            	lotteryId:lotteryId,
                accountId:accountId
            },
            success: function(data){
                if(data.status == 'success'){
                    nums = data.nums;
                    $('#nums').html(nums);
                     Lottery();
                }else{
                    dialog.msg(data.msg);
                }
            },
            error: function(xhr){
                dialog.msg('服务器忙，刷新后重试' + xhr.status);
            }
        })
    }

    getUserNums();

    //抽奖
    function Lottery(){

        //点击抽奖

        var $box1 = $('#box1'),
            $box2 = $('#box2');

        $('#btn').on('click',function(){

            if(nums == 0){
                dialog.msg('机会好像用光了呢，有分享给好友么？明天记得再来呀');
                return;
            }

            var _this = $(this);
            if(_this.hasClass('on')){
                return;
            }

            _this.addClass('on');

            dialog.loading();

            $.ajax({
                url: '${rc.contextPath}/activity/lottery/draw',
                type: 'post',
                cache: false,
                dataType: 'json',
                data: {
                	lotteryId:lotteryId,
                    accountId:accountId
                },
                success: function(data){
                    if(data.status == 1){
                       dialog.msg(data.msg);
                       nums = data.nums;
                       if(data.man == 0){
                    	   data.man = data.jian;
                       }
                       var str='';
 						if(data.man >= 10000){
 							str += '<div class="card_price card_price10000"><strong>满<i>'+data.man+'元</i>减<i>'+data.jian+'元</i></strong></div><div class="card_txt">'+data.text+'</div>';
 						}else{
 							str += '<div class="card_price"><strong>满<i>'+data.man+'元</i>减<i>'+data.jian+'元</i></strong></div><div class="card_txt">'+data.text+'</div>';
 						} 
 						$('#card').html(str);
                       $box1.fadeOut();
                       $box2.fadeIn();
                    }else{
                        dialog.msg(data.msg);
                    }
                    dialog.closeLoading();
                    _this.removeClass('on');
                },
                error: function(xhr){
                    dialog.msg('服务器忙，刷新后重试' + xhr.status);
                    dialog.closeLoading();
                    _this.removeClass('on');
                }
            })
        })

        $('#again').on('click',function(){
             $box1.fadeIn();
             $box2.fadeOut();
             nums;
             if(nums == 0){
                nums = 0;
             }
             $('#nums').html(nums);   
        })
    }

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