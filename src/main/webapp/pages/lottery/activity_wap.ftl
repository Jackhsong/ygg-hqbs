<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <title>给你2次成为土壕的机会,快来试试~</title>
    
<script>
    // 淘宝H5自适应解决方案
    // https://github.com/amfe/lib-flexible
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
<style>
*{margin: 0;padding: 0;}
em,i{font-style: normal;}
html,body{font-family: sans-serif;}
body{background: url(${rc.contextPath}/pages/images/lotteryImage/bg.png) center repeat;font-family: 'microsoft yahei';}
img{max-width: 16rem; display: block;margin: 0 auto;}

.down{height: 3rem;background: #fff;width: 100%;border-bottom: 2px solid #808080;}
.down a{position: fixed;height: 3rem;display: block;width: 100%;}
.down a img{display: block;margin: 0 auto;height: 3rem;}


.text p{font-size: .4rem;color: #fdf0c5;text-align: center;line-height: .6rem;}
.text p span{background: #c20a0a;padding: .1rem 0.2rem 0.14rem .2rem;border-radius: 12px;}
.text{padding-bottom: .3rem;margin-top: -.5rem;}
.btn{width: 11rem;margin-left: 16%;}
.btn a{display: block;width:100%;height: 1.7rem;line-height: 1.7rem;font-size: .9rem;text-decoration: none;border-radius: 13px;box-shadow: 3px 3px 0 #881f00; background: #ffd33c;margin: 0 auto;text-align: center;color: #653700;}
.nums{text-align: center;font-size: .5rem;color: #8c0000;margin: .5rem 0;}
.share {color: #fff;text-align: center;margin-bottom: 1rem;line-height: 1rem;}
.share span{  display: inline-block;background: url(${rc.contextPath}/pages/images/lotteryImage/wx.png) left center no-repeat;padding-left: 1rem;background-size: contain;}
.share img{display: inline-block;vertical-align: middle;}
.share a{color: #fff;text-decoration: underline;font-size: .7rem;}
.card{width:11.2rem;height: 5.3rem; text-align: center;background: url(${rc.contextPath}/pages/images/lotteryImage/card.png) -8px no-repeat; background-size: contain; margin: 0 auto; }
.card_price{font-size: 1.1rem;color: #ce3f2c;height: 4.3rem;font-family: 'microsoft yahei';}
.card_price strong{display: block;padding-top: 1.6rem;}
.card_price10000 strong{display: block;padding-top: 0.8rem;width: 56%;margin: 0 auto;line-height: 1.4rem;}
.card_price strong i{font-family: 'microsoft yahei';}
.card_txt{max-width:16rem;margin: 0 auto;color: #fdf0c5;text-align: center;line-height: 1rem;font-size: .6rem;}
.box2 .text p{font-size: .6rem;}
.play_box{text-align: center;margin: 1.5rem 0 1rem 0;}
.play_box a.btn{width: 7rem;height: 1.5rem; display: inline-block; line-height:1.5rem; font-size: .7rem;}
.play_box a.btn:nth-child(1){margin-right: 1rem;}
.share_text{width: 14rem;margin: 0 auto; text-align: center;font-size: .6rem;color: #8c0000;margin-top: .5rem;}
.img_t{width: 100%;}

.tel{width: 10rem;margin-left:16%;margin-top: .3rem;box-shadow: 3px 3px 0 #881f00;margin-bottom: 0.5rem;padding:0rem 1rem 0 0;border-radius: 13px;}
.tel input{width: 100%;display: block;height: 1.1rem; border:1px solid #fff;font-size: 0.7rem;padding:0.3rem .5rem;text-align: center;border-radius: 13px;}


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
.foot{text-align: center;margin: 1rem 0 1rem 0;}
.foot span{display: inline-block;padding: .1rem .5rem;background: rgba(255,255,255,.2);color: #cc0001;border-radius: .7rem;}
.share_wx{ display: none; width: 100%;height: 100%;background: rgba(0,0,0,.7);position: absolute;left: 0;top: 0;}
.share_wx img{position: fixed;top: -1rem;left: 0.8rem;width: 100%;}
#phone{font-family: 'microsoft yahei';overflow: hidden;}
.share_wx .close{width: 2rem;height: 2rem;background: url("${rc.contextPath}/pages/images/lotteryImage/close.png") no-repeat;background-size: 100%;position: absolute;top: 5px;left: 0;display: block;}
.play_box.btn{width: 100%;}
</style>
</head>
<body>

<div class="down">
    <a href="http://download.gegejia.com" class="mdown"><img src="${rc.contextPath}/pages/images/lotteryImage/dowm.jpg" alt="下载左岸城堡"/></a>
</div>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/1.jpg"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/2.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/3.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/4.png"/>
<img class="img_t" src="${rc.contextPath}/pages/images/lotteryImage/5.png"/>
    
<div class="box">
<input type="hidden" value=${lotteryId?c} id="lotteryId" />
   <div class="box1" id="box1">
       <div class="text">
           <p>偷偷告诉你：<span>8月19日10点</span> 12星座吃货盛宴准点开席！</p>
           <p>满100元减100元白吃白喝优惠券已出现，最高满减1万元。</p>
           <p>能不能成为土壕吃货，看你的了！</p>
       </div>
       <div class="tel"><input id="phone" type="number" placeholder="输入手机号抽奖"></div>
       <div class="btn">
           <a href="javascript:;" id="btn">立即抽奖</a>
       </div>
       <p class="nums">每个手机号每天<i id="nums">2</i>次抽奖机会</p>
       <div class="foot"><span>左岸城堡：进口食品免税店</span></div>
   </div>

   <div class="box2" id="box2" style="display:none">
       <div class="text">
           <p>8月19日10点12星座吃货盛宴，领好券放开吃！</p>
       </div>
       <div class="card" id="card"></div>
       <div class="play_box btn">
           <a href="javascript:;" class="btn" id="again">再玩一次</a><a href="javascript:;" class="btn" id="share">分享给好友</a>
           <p class="share_text">使用此手机号码注册登录左岸城堡即可查看优惠券</p>
       </div>
       <div class="foot"><span>左岸城堡：进口食品免税店</span></div>
   </div>

</div>

<div class="share_wx" id="share_wx">
    <img src="${rc.contextPath}/pages/images/lotteryImage/share.png">
    <i class="close "></i>
</div>
    
<script>
$(function(){

    var oPhone = {},
        _val = '';
    
    var lotteryId = $('#lotteryId').val();

    var $phone = $('#phone'),
        reg = /^1[3-8][0-9]{9}$/

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

    Lottery();

    //抽奖
    function Lottery(){

        //点击抽奖

        var $box1 = $('#box1'),
            $box2 = $('#box2');

        $('#btn').on('click',function(){

            var _this = $(this);

            _val = $phone.val();

            if(!reg.test(_val)){
                dialog.msg('亲，这个真的是手机号码么？');
                return;
            }

            if(oPhone[_val] === undefined){
                oPhone[_val] = {
                    nums: 2
                };
            }

            if(oPhone[_val].nums == 0){
                dialog.msg('<br/>哎呀！机会用光啦。进入APP首页->幸运大抽奖，分享给好友额外增加两次机会');
                return;
            }

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
                    phone: _val
                },
                success: function(data){
                    if(data.status == 1){
                       dialog.msg(data.msg);
                       oPhone[_val].nums = data.nums;
                       if(data.man == 0){
                    	   data.man = data.jian;
                       }
                       var str='';
  						if(data.man >= 10000){
  							str += '<div class="card_price card_price10000"><strong>满<i>'+data.man+'元</i>减<i>'+data.jian+'元</i></strong></div><div class="card_txt">'+data.text+'</div><div class="card_txt">您已获得满'+data.man+'元减'+data.jian+'优惠券</div>';
  						}else{
  							str += '<div class="card_price"><strong>满<i>'+data.man+'元</i>减<i>'+data.jian+'元</i></strong></div><div class="card_txt">'+data.text+'</div><div class="card_txt">您已获得满'+data.man+'元减'+data.jian+'优惠券</div>';
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
             oPhone[_val].nums;
             if(oPhone[_val].nums == 0){
                oPhone[_val].nums = 0;
             }
        })
    }

    //分享
    $('#share').on('click',function(){
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
</div>
</body>
</html>