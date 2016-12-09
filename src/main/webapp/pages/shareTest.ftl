<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  
    <title>分享测试</title>
    
</head>
<body>

<div>

分享测试

</div>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">

    var appId = "${jsSdk.appId!""}" ;
    var timestamp = "${jsSdk.timestamp!""}";
    var nonceStr = "${jsSdk.nonceStr!""}";
    var signature = "${jsSdk.signature!""}";

    
  wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: appId, // 必填，公众号的唯一标识
    timestamp:timestamp, // 必填，生成签名的时间戳
    nonceStr: nonceStr, // 必填，生成签名的随机串
    signature: signature,// 必填，签名，见附录1
    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

            
wx.ready(function(){              
       wx.onMenuShareAppMessage({
                   title: "测试分享", // 分享标题
                   desc: "测试分享",// 分享描述
                   link: "https://www.baidu.com", // 分享链接
                   imgUrl: "http://yangege.b0.upaiyun.com/product/681e13f03807.jpg!v1product", // 分享图标
                   type: 'link', // 分享类型,music、video或link，不填默认为link
                   dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                   success: function () { 
                         // 用户确认分享后执行的回调函数
                    },
                   cancel: function () { 
                       // 用户取消分享后执行的回调函数
                    },
                   fail: function (res) { 
                          alert(res.errMsg)
                   }
              });
              
              wx.onMenuShareTimeline({
                     title: "测试分享", // 分享标题
                     link: "测试分享", // 分享链接
                     imgUrl: "http://yangege.b0.upaiyun.com/product/681e13f03807.jpg!v1product", // 分享图标
                     success: function () { 
                           // 用户确认分享后执行的回调函数
                       },
                     cancel: function () { 
                          // 用户取消分享后执行的回调函数
                     },
                    fail: function (res) {  
                    }
                });
});



 </script>
</body>
</html>