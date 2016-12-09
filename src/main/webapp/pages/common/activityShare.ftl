<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	var appId = "${jsSdk.appId?default("")}";
    var timestamp = "${jsSdk.timestamp?default("")}";
    var noncestr = "${jsSdk.noncestr?default("")}";
    var signature = "${jsSdk.signature?default("")}";
    var sharePath = "${jsSdk.sharePath?default("")}";
    var shareImage = "${shareImage?default("")}";
    var shareTitle="${shareTitle?default("")}";
    var shareContent="${shareContent?default("")}";
    
  	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: appId, // 必填，公众号的唯一标识
	    timestamp:timestamp, // 必填，生成签名的时间戳
	    nonceStr: noncestr, // 必填，生成签名的随机串
	    signature: signature,// 必填，签名，见附录1
	    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline','hideMenuItems'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	 });
	wx.ready(function(){  
		wx.hideMenuItems({
    		menuList: ['menuItem:copyUrl','menuItem:share:qq','menuItem:share:email','menuItem:openWithSafari','menuItem:favorite','menuItem:readMode','menuItem:share:QZone'] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		});
		wx.onMenuShareAppMessage({
    	   title: shareTitle, // 分享标题
           desc: shareContent, // 分享描述
           link: sharePath, // 分享链接
           imgUrl: shareImage, // 分享图标
           success: function () { 
                 // 用户确认分享后执行的回调函数
            },
           cancel: function () { 
               // 用户取消分享后执行的回调函数
            },
           fail: function (res) { 
           }
		});
      	wx.onMenuShareTimeline({
             title: shareTitle, // 分享标题
             link: sharePath, // 分享链接
             imgUrl: shareImage, // 分享图标
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
	
	wx.error(function(res){
	});
</script>