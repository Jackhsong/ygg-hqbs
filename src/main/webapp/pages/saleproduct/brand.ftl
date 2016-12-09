<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection" />
		<title>${(wxsharetitle)!""} - 左岸城堡</title>
		<link rel="shortcut icon"  href="${yggcontextPath}/pages/images/favicon.ico">
		<link rel="apple-touch-icon" href="custom_icon.png">
		 
		<link href="${yggcontextPath}/pages/css/global.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>
		<link href="${yggcontextPath}/pages/css/goods.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>		
		<script src="${yggcontextPath}/pages/js/jquery-1.11.2.min.js"></script>
		<script src="${yggcontextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}" type="text/javascript" ></script>
		<script src="${yggcontextPath}/pages/js/shoppingcart.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
		<script src="${yggcontextPath}/pages/js/extutil.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
		 
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body>
	
	测试测试${detail}
		<div class="page">
		<div class="quphone"><a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')"></a><img src="${yggcontextPath}/pages/images/sidebar.png"><span></span></div>
		   <a href="${yggcontextPath}/spcart/listsc" ><div class="indexcart"><img src="${yggcontextPath}/pages/images/indexCart.png"></div>
		    <div class="brandcart">
				<img src="${yggcontextPath}/pages/images/brandCart.png">
				<span class="rSup" id="showCartCount"></span>
			 	<div class="goods_time2" id="buytime">
					<span class="minute"></span><span class="second"></span>
					
				</div><!-- 倒计时 -->				
			</div></a>
			<div class="tit">
				${(name)!""}
				<a href="${yggcontextPath}/index" class="tit_la"><img src="${yggcontextPath}/pages/images/cancle.png" class="tit_center"></a><!-- 返回链接 --><!-- 个人中心链接 -->
				<a href="${yggcontextPath}/index" class="tit_ra" id="saleIdright"><img src="${yggcontextPath}/pages/images/home.png" class="tit_cart"></a><!-- 购物车链接 -->
			</div>
			<div id="banner">				
	        	<a href="#"><img class="banner_img" src="${(bannerImage)!""}"></a><!-- 商品展示图片 -->	
	        	<div class="goods_time" id="clockbox1">
					<img class="goods_time_img" src="${yggcontextPath}/pages/images/timeLeft.png">剩<span class="day"></span><span class="hour"></span><span class="minute"></span><span class="second"></span>
				</div><!-- 倒计时 -->
				<script type="text/javascript">
					$(function(){
						/* count down 放在下面 
						var stime="${(second)!"0"}" ;
						var sid="#clockbox1";
						var timer = setInterval(function(){
							stime--;
							brandCdown(stime,sid);
						},980);
						brandCdown(stime,sid);
						*/
					})
				</script>				     
			</div>		
			<div class="gegetalk1 clear">
				<div class="gegeleft"><img src="${yggcontextPath}/pages/images/userimg/gege.png"></div>				
				<div class="gegeright"><img src="${yggcontextPath}/pages/images/shousuo.png" class="gegeright_img1"><img src="${yggcontextPath}/pages/images/zhankai.png" class="gegeright_img2 off"></div>				
				<ul>
					<li>格格说:</li>
					<li>${(gegesay)!""}</li>
				</ul>
			</div>	
			<!-- 商品列表 -->
			<div class="protips"></div>
			<div class="pronotice " ><img src="${yggcontextPath}/pages/images/loading1.gif">正在加入购物车...</div>
			  <#if productDetailList?exists>
			     <#list productDetailList as pl >
				        <dl class="brand_list clear">
				            <!-- <a href="${yggcontextPath}/product/single/${(pl.productId)!"0"}"> -->
				             <a href="/item-${(pl.productId)!"0"}.htm">
				              
								<dt>
								    <img class="brand_list_img" src="${(pl.image)!""}"><!-- 商品列表展示图片 -->
									<#if pl.type?exists && pl.type=='1'>
									<#elseif pl.type?exists && pl.type=='2'>
									     <div class="chance "><img src="${yggcontextPath}/pages/images/goods/chance.png"></div><!-- 还有机会 -->
									<#elseif pl.type?exists && pl.type=='3'>
									     <div class="ready "><img src="${yggcontextPath}/pages/images/goods/ready.png"></div><!-- 即将开始 -->
									<#elseif pl.type?exists && pl.type=='4'>
									     <div class="end "><img src="${yggcontextPath}/pages/images/goods/end.png"></div><!-- 已抢完 -->
									</#if>
									
								</dt>
								<dd>${(pl.name)!"0"}</dd>
								<dd>￥${(pl.lowPrice)!"0.00"}<span class="delprice">￥${(pl.highPrice)!"0.00"}</span></dd>
							</a>   
							<dd class="ddthree"><p class="cartadd">
						    <!--<dd class="ddthree"><span id="productsellcount${(pl.productId)!"0"}"></span>人已经购买<p class="cartadd">-->
						    <#if pl.type?? && (pl.type =='3' || pl.type =='4')  >
						    <#else>
						      <img src="${yggcontextPath}/pages/images/cartadd.png">
						    </#if>
						    <input type="hidden" id="productcount${(pl.productId)!"0"}" name="productcount" value="${(pl.productCount)!"0"}" /><input type="hidden" id="indexId" name="indexId" value="${pl_index}" /><div class="loading off"><img src="${yggcontextPath}/pages/images/loading.gif"></div></p></dd>	
				        </dl>
				        <input type="hidden" name="productId${pl_index}" id="productId${pl_index}"   value="${(pl.productId)!"0"}"/>
			     </#list>
			  </#if>
			<!-- 商品列表 End-->
	   	</div>	
	   <input type="hidden" name="caId" id="caId" value="${(caId)!"0"}" />
	   	<script>
	   	     var  flag = false ;　　  /* 防复重提交 */
	   	     $(function(){
	   	           /* $("#saleId").click(function(){
			             sendform("${yggcontextPath}/spcart/addsc");
			       })  */
	   	       $('.cartadd').css('cursor', 'pointer').bind("click",cartadd) ;
	   	       
	   	       /*购物车增加*/
	   	       function cartadd(){
	   	        
	   	        $('.indexcart').hide();
				$('.brandcart').show();
			
			 
	
	   	          var productcount=$(this).find('input[name=productcount]'); //.find('input:eq(0)') ; // .parent()
	   	       　　　　　 // 　 var productcount=$(this).parent().find('#productcount') ;
	   	           var  indexId = $(this).parent().find('#indexId') ;
	   	           var productId = $("#productId"+indexId.val()).val() ;
	   	           var loading = $(this).parent().find('.loading') ;
	   	           var productCountVal = parseInt(productcount.val())+1 ;
	   	           
	   	           var data = "productid="+productId+"&productcount="+productCountVal ;
	   	           var flag = editshoppingcartbrand("${yggcontextPath}/spcart/editscbsp",data,loading);
	   	           if(flag)
	   	           {
	   	               /* 成功后购物车数量加1,此商品的productcount+1 */
	   	              // $(this).parent().find('#productcount').val(productCountVal) ;
	   	               productcount.val(productCountVal);
	   	               $(".rSup").removeClass("off") ;
	   	               var spcounttmp =$("#showCartCount").html() ;
	   	               var spcount = 1 ;
	   	               if(spcounttmp ==undefined || spcounttmp == 'undefined' || spcounttmp=='')
	   	                  spcount = parseInt(0)+1 ;
	   	               else
	   	                  spcount = parseInt($("#showCartCount").html())+1 ;
	   	              // $("#showCartCount").html(spcount) ; 
	   	              
	   	              // $.setCartCountVal(spcount);
	   	              // showBrandTipMsg("已加入购物车!");
	   	           }
	   	       } ;        
var editshoppingcartbrand=function(url,data,loading){
     var flag = true ;
     
        $.ajax({
			           type:'POST',
			           url: url ,  
			           data: data ,
			           async:true,    
			           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
			           beforeSend:function(){
			             showbrandTipLoadingMsg();
			           },
			           success: function(msg){
			               if(msg !=null && msg.status!=null && msg.status!='undefined' && msg.status =='1'){ /*成功*/
			                 var productcount = msg.productcount ;
			                  if(loading!=undefined && loading!='undefined')
			                     loading.hide();
			                  flag =true ;   
			                   var msg = "已加入购物车!";  
			                   showBrandTipMsg(msg) ;   
			                           
			               }else if(msg !=null && msg.status!=null && msg.status!= undefined && msg.status =='0' ){
			                   var errorMsg='';
			                    flag =false ;
			                    if(loading!=undefined && loading!='undefined')
			                        loading.hide();
			                    var errorCode = msg.errorCode ;  /*一定要有*/
			                    var stockCount = msg.stockCount ; /*errorCode=4　时有*/
			                    var restrictionCount = msg.restrictionCount ;  /*errorCode=5　时有 限购数量　*/
			                    
			                    if(errorCode!=undefined && errorCode!= 'undefined' )
			                    {
			                     
			                        if(errorCode == '0')
			                           errorMsg = '请刷新页面' ; /* '亲，服务器未知错误'; */
			                        else if(errorCode =='1')
			                           errorMsg = '末登录,请先登录';
			                        else if(errorCode =='2')
			                           errorMsg = '商品已被删除';
			                        else if(errorCode =='3')
			                            errorMsg = '临时账号不存在';
			                        else if(errorCode=='4' && stockCount !=undefined && stockCount !='undefined') /*库存不足*/
			                         {
			                            errorMsg = '慢了一步，商品库存不足了～';
			                         }else if(errorCode=='5' && restrictionCount !=undefined && restrictionCount !='undefined') /*库存不足*/
			                         {
			                            errorMsg = '数量有限，限购'+restrictionCount+'件哦';
			                         }
			                            /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
										if(errorMsg !=''){
				                                showErrorMsg(errorMsg) ;
				                                return ;
				                         }
										
			                    }
			               }
			               $('.pronotice').hide();
			               $.getCartCount('${yggcontextPath}/spcart/showcartcount',''); /*每次去服务端更新购物车的最新值，防止微信返回不执行ajax请求*/     
			            },
			           error:function(err){
			        	  flag = false ;
			        	  $('.pronotice').hide();
			           },complete:function()
			           {
			              $('.pronotice').hide();
			           } 
			       }) ;
			 
		return flag ;
} ;

function  showErrorMsg(errorMsg)
{
   /* 弹出层 */
	$('.protips').html(errorMsg);
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.protips').css('top',xtop);
	$('.protips').css('display','block');
	setTimeout(function(){			
			$('.protips').css('display','none');
	},2000);
 
};	 	       
function showBrandTipMsg(msg)
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
	   	 
function  showbrandTipLoadingMsg()
{
   var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.pronotice').css('top',xtop);
	$('.pronotice').show();
	
};	
   	       
})
	   	</script>
<form id="refreshFormBrand" name="refreshFormBrand" action="${yggcontextPath}/cnty/toac/${(caId)!"0"}" method="post">
   <input type="hidden" name="reload" value="1" /> <!---->
</form>
	   	<script>
 var requestmodifyurl = "${yggcontextPath}/cnty/toac/${(caId)!"0"}" ;
		    $(function(){
		         $.getCartCount('${yggcontextPath}/spcart/showcartcount',''); /*查询购物车的数量*/
		         
		         var  caId = $("#caId").val() ; 
		         var  requrl =  "${yggcontextPath}/cnty/getbrandendsecond/"+caId ;
		         //倒计时请求
		         $.ajax({
		                       type:'POST',
		                       url: requrl ,
		                       data:'',
		                       dataType: 'json' ,
		                       success: function(msg){
		                             var d = msg.second ;
		                             if(d ==undefined || d=='')
		                             {
		                               $('#clockbox1').hide();
		                                return ;
		                             }
		                             var stime=　d　 ;
									　var sid="#clockbox1";
									  window.timer1 = setInterval(function(){
										　stime--;
										　brandCdown(stime,sid);
									　},980);
									　brandCdown(stime,sid);
		                         },
		                       error:function(err){
		                         }
		               });  
		         
			         //用一个请求去得到    给每个商品加一个事件处理去查询productcount
			         var requesturl = "${yggcontextPath}/cnty/showproductcounts" ;   
				     var requestdata ="productids=${productIds!"0"}";
					  $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
					     var status = msgjsonobj.status ;
					     var products = msgjsonobj.products ;
					     if(status !=undefined && status =='1' && products.length>0 )
					     {
					           $.each(products, function(i, item) {
						                var productcount= item.productcount; 
						                var productsellcount = item.productsellcount ;
						                var productid = item.productid ;
						                var productcountobj = $('#productcount'+productid) ;
						                var productsellcountobj  = $('#productsellcount'+productid) ;
						                 
						                if(productcountobj !=undefined ) 
						                   productcountobj.html(productcount);
						                if(productsellcountobj !=undefined )
						                   productsellcountobj.html(productsellcount);
										 
						        });
					     }
	                 }});    
		    }) ;
		  
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
/*
wx.error(function(res){
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		    alert(res.errMsg);
		});
*/
wx.ready(function () {
		
			 // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		     wx.onMenuShareTimeline({
		         title: '${(wxsharetitle)!""}', // 分享标题
		         link: '${(link)!"0"}',
		         imgUrl: '${(imgUrl)!""}' // 分享图标
		     });
		     
		     // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			  wx.onMenuShareAppMessage({
		      title: '${(wxsharetitle)!""}',
		      desc: '${(wxsharedesc)!""}',
		      link: '${(link)!"0"}',
		      imgUrl: '${(imgUrl)!""}'
		    });
});
</script>
<script type="text/javascript">
	   		var sUserAgent = navigator.userAgent.toLowerCase();  
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";  
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";  
		    var bIsMidp = sUserAgent.match(/midp/i) == "midp";  
		    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";  
		    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";  
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";  
		    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";  
		    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    var bWeXin=sUserAgent.match(/MicroMessenger/i) == "micromessenger";
	   		 if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){  
	   		 	var ggtop=$('.gegetalk1 ul li').eq(1).html().length;
		        if(ggtop<=72){
					 $('.gegeright').hide();
					 $('.gegetalk1 ul').css('marginRight',0);
					 $('.gegetalk1').css('paddingRight',10);
				}else{
					$('.gegetalk1 ul').css('height','48');
				}
	   		 }
</script>
</body>
</html>