<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>左岸城堡${title}~</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <meta name="viewport" content="width=device-width, initial-scale=0.5, maximum-scale=10.0, minimum-scale=0.5, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="" rel="stylesheet">
    
	<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}"></script>
</head>
    
    <style>
      *{margin: 0;padding: 0;font-family: 'microsoft yahei';-webkit-touch-callout:none;}
              body{background: #fdd43c;}
              .mheader{height: 120px;margin-bottom:40px;}
              .mbox{width: 100%;margin: 0 auto;padding-bottom: 100px;}
              img{display: block;max-width: 100%;margin: 0 auto;}
              .mdown{width: 100%; border-bottom: 0px solid #808080;background: #fff;overflow: hidden;display: block;position: fixed;top: 0;}
              p{text-align: center;font-size: 30px;line-height: 40px;color: #564400;}
              .mtext{padding: 10px 0; }
              .mbox1{color: #564400;width: 490px;padding:40px;margin: 20px auto;border-radius: 5px;background: rgba(255,255,255,.7);font-size: 30px;}
              .mbox1 input{width: 100%;background: #fff;border: none;padding: 20px 0; text-align: center;font-size: 30px;margin: 20px 0;
                  border-radius: 3px;;}
              .mbox1 p{font-size: 30px;margin-bottom: 15px;}
              .mbox2{display: none;}
              .mbtn{display: block;width: 100%;padding: 20px 0;text-align: center;background: #ff3e00;color: #fff;text-decoration: none;}
              .protips{ display: none; padding: 40px 10px;  width: 85%; background: rgba(0,0,0,0.8); line-height: 29px; position: absolute; top: 80%; left: 40%; margin-left: -33%; border-radius: 3px; -webkit-border-radius: 3px; -moz-border-radius: 3px; -ms-border-radius: 3px; color: #ffffff; font-size: 28px; text-align: center; z-index: 1}
              .mimg{width: 70%;}
    </style>
    
<body>
<div class="protips"></div>
<div class="mbox">
<input type="hidden" value=${gameId?c} id="gameId" />
    <div class="mheader"><a href="http://download.gegejia.com" class="mdown"><img src="${rc.contextPath}/pages/images/spead/1.jpg" alt="下载左岸城堡"/></a></div>
    <img class="mimg" src="${imgUrl}" alt="" style="width:200px;height:200px;"/>
    <div class="mtext">
        <p>${name}</p>
        <!--<p>精选全球美食</p>-->
        <!--<p>邀你一起实现吃货终极梦想</p>-->
    </div>
    <div class="mbox1" id="mbox1">
        <div style="text-align:center;">领取左岸城堡${info}</div>
        <input type="hidden" id="code" value='${code!""}' />
        <input id="phone" type="number" placeholder="输入手机号领取"/>
        <a class="mbtn" id="mbtn" href="javascript:;">立即领取</a>
    </div>
    <div class="mbox1 mbox2" id="mbox2">
        <p>${info}已发放至您账户</p>
        <p>请下载左岸城堡APP后</p>
        <p>使用此手机号注册登陆后查看</p>
        <a class="mbtn" href="http://download.gegejia.com">下载左岸城堡APP</a>
    </div>
    
<script>

function showTipMsg(msg)
{
	 /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
    /* 弹出层 */
	$('.protips').html(msg);
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.protips').css('top',xtop);
	$('.protips').css('display','block');
	setTimeout(function(){			
		$('.protips').css('display','none');
	},2000);
}

        $(function(){
            var $mbox1 = $('#mbox1'),
                $mbox2 = $('#mbox2'),
                $mbtn = $('#mbtn'),
                $phone = $('#phone'),
                $code = $("#code"),
                reg = /^1[3-8][0-9]{9}$/;
                
             var gameId = $('#gameId').val();

            $mbtn.on('click',function(){
                var _this = $(this),
                	_code = $code.val(),
                    _val = $phone.val();

                if(!reg.test(_val)){
                	showTipMsg('请输入正确的手机号码');
                    return;
                }

                if(_this.hasClass('loading')){
                    return;
                }

                _this.addClass('loading').html('领取中...');

                $.ajax({
                    url: '${rc.contextPath}/game/receive',
                    type: 'post',
                    dataType: 'json',
                    data: {
                    	gameId:gameId,
                        phone: _val
                    },
                    success: function(data){
                        if(data.status == 1){
                        	showTipMsg("领取成功");
                            $mbox1.hide();
                            $mbox2.show();
                        }else{
                        	showTipMsg(data.msg);
                        }
                        _this.removeClass('loading').html('立即领取');
                    },
                    error: function(xhr){
                        _this.removeClass('loading').html('立即领取');
                        showTipMsg('服务器忙，刷新后重试' + xhr.status);
                    }
                })

            })
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