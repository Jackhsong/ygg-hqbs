<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection" />
		<title>左岸城堡</title>
		<link rel="shortcut icon"  href="${yggcontextPath}/pages/images/favicon.ico">
		<link rel="apple-touch-icon" href="custom_icon.png">
		<link href="${yggcontextPath}/pages/css/swiper.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>
		 
		<link href="${yggcontextPath}/pages/css/global.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>
		<link href="${yggcontextPath}/pages/css/goods.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>
		<#include "../common/brandShare.ftl">			
		<script src="${yggcontextPath}/pages/js/jquery-1.11.2.min.js"></script>
		<script src="${yggcontextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}" type="text/javascript" ></script>
		<script src="${yggcontextPath}/pages/js/shoppingcart.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
		<script src="${yggcontextPath}/pages/js/extutil.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>	
	</head>
	<body>
		<div class="page">
			<a href="${yggcontextPath}/hqbsHomeInfo/getHomeInfo" class="top-back-home">
				<img src="${yggcontextPath}/scripts/images/logo-2.png">
				<i class="fr t_c">前往首页</i>
			</a>
			<div id="pbanner">
	        	<div class="swiper-container" style="height:auto;">
				    <div class="swiper-wrapper" style="height:auto;">
				    <!-- banner展示商品 -->
				    <#if imageList ? exists>
				      <#list  imageList  as img>
				            <div class="swiper-slide">
					        	<a href="javascript:void(0)"><img src="${(img)?if_exists}"></a><!-- 商品详情展示图片 -->
					        </div>
				      </#list>
				    <#else>
				         <div class="swiper-slide">
					        	<a href="javascript:void(0)"><img src="${image}"></a><!-- 商品详情展示图片 -->
					      </div>
				    </#if>

					<!-- banner展示商品 END -->
				    </div>
			   		<div class="pagination"></div>
			   </div>
			</div>
			<!-- 产品名称头 -->
			<div class="protit">
				<div class="proname replace-str">${name!""}</div>
				<div class="price">￥${lowPrice!"0.00"}<span class="orgprice" style="display:none;">￥${highPrice!"0.00"}</span>
					<!-- 剩几天显示此 -->
					<div class="pgoods_time off" id="clockbox2" style="display:none;">
						<img class="pgoods_time_img" src="${yggcontextPath}/pages/images/timeLeft.png">剩<span class="day"></span><span class="hour"></span><span class="minute"></span><span class="second"></span>
					</div>
					<!-- 剩几天显示此 End -->

					<!-- 几天后开抢显示此 -->
					<div class="pgoods_time1 off" id="clockbox1s" style="display:none;">
						<img class="pgoods_time_img" src="${yggcontextPath}/pages/images/timeLeft.png"><span class="day"></span><span class="hour"></span><span class="minute"></span><span class="second"></span>后开抢
					</div>
					<!-- 几天后开抢显示此 End -->
				</div>
			</div>						
			<dl class="gegetalk clear">
				<dt><img src="http://zuoan.waibao58.com/ygg-hqbs/logo-1.jpg"></dt>
				<br/>
				<dd class="replace-str">${gegesay!""}</dd>
			</dl>
			
			<#if detail?? >
			<div class="detail">
				<!-- <div class="detail_tit">商品详情</div> -->
				<div class="mydetail"><!-- 商品详情正文处 -->
					${detail}
				</div>
			</div>
			</#if>
							
			<!-- 商品列表 -->
			<div class="protips"></div>
			<a name="end"></a>
			<div class="pronotice " ><img src="${yggcontextPath}/pages/images/loading1.gif">正在加入购物车...</div>
			  <#if productDetailList?exists>
			  	<ul class="">
			     <#list productDetailList as pl >
			     		<li class="add" style="display:none;">	
							<!-- a href="/${urlType}-${(pl.productId)!"0"}"-->
							<p class="plist">${(pl.name)!"0"}</p>
							<p class="">
	
							    <input type="hidden" id="productcount${(pl.productId)!"0"}" name="productcount" value="${(pl.productCount)!"0"}" />
								<input type="hidden" class="indexId" name="indexId" value="${pl_index}" />
							    	<div class="loading off"><img src="${yggcontextPath}/pages/images/loading.gif"></div>
							</p>
				        	<input type="hidden" name="productId${pl_index}" id="productId${pl_index}" class="pkey"  value="${(pl.productId)!"0"}"/>
				        </li>
			     </#list>
			     </ul>
			  </#if>
			<!-- 商品列表 End-->
			<div class="select-panel" style="display:none">
				<div class="item-up">
					<div class="item-up-left">
						颜色
					</div>
					<div class="item-up-right">
						
					</div>					
				</div>
				<div class="clear"></div>
				<div calss="item-low">
					<div class="item-low-left">
						尺码
					</div>
					<div class="item-low-right">
						
					</div>							
				</div>
				<div class="clear"></div>

			<!-- 	<div calss="item-num">
					<div class="item-num-left">
						数量: 
					</div>
					<div class="item-num-right">
						<input name="productcount" type="number" value="1"/>
					</div>							
				</div> -->
				<div class="clear"></div>


				<div class="add-row" style="display:none">
					<span class="add add-btn">
					添加 
					</span>
				</div>
			</div>
			<script type="text/javascript">
	
			</script>
			<style type="text/css">
			.select-panel{
				padding: 20px 20px 5px 20px;
			}
			.select-panel a{
				padding: 5px 5px 5px 5px;
				display:inline-block;
			}
				.item-up-left{
			
					padding: 5px 5px 5px 5px;
					margin-bottom: 15px;
				}
				.item-up-right{

					text-align: left;					
				}


				.item-low-left{

					padding: 5px 5px 5px 5px;
					margin-bottom: 15px;
					margin-top: 20px;
				}
				.item-low-right{

					text-align: left;						
				}

				.item-num-left{
					float: left;
					width: 30%;
					padding: 5px 5px 5px 5px;
					margin-bottom: 15px;
					margin-top: 20px;
				}
				.item-num-right{
					float: right;
					width: 60%;
					text-align: left;	

				}				
				.add-row{
					text-align: center;
					margin-top: 30px;
				}
				.button {
					font-size: 12px;
					background: rgba(204, 204, 204, 0.38);
					border-radius: 3px;
					margin: 0px 5px 5px 5px

				}
				.selected-btn{
					color: #FFF;
					background: rgba(230, 46, 27, 1);
				}
				.clear{
					float: clear;
				}
				.add-row{
					text-align: center;
				}
				.add-row span{
					font-size: 14px;
					color: #FFF;
					background: rgba(230, 46, 27, 1);
					padding: 5px 40% 5px 40%;
					width: 80%;
					border-radius: 5px;
				}

			</style>
			<br/>
			<br/>

			<div class="buy clear">
				<a href="${yggcontextPath}/spcart/listsc">
					<div class="buy_cart"><!--　购物车的数量 -->
						<img src="${yggcontextPath}/pages/images/cart_b.png"><span class="buysub off" id="showCartCount1"><!--${(cartCount)!"0"}--></span>
					</div>
				<div class="buytime fl off"><!--　购物车的锁定时间 -->
					
					</span>
				</div>
				</a>
				<!-- 根据后端的状态位来判断　需要时显示出来
				<#if productStatus?exists && productStatus == '1'>
				    <a href="javascript:void(0)" class="buyitem fr" id="addbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off" >加入购物车</a>
				 <#elseif productStatus?exists && productStatus == '2'>
				    <a href="javascript:void(0)" class="buyitem fr" id="nextbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="">还有机会</a>
				 <#elseif productStatus?exists && productStatus == '3'>
				    <a href="javascript:void(0)" class="buyitem fr" id="rebegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">即将开始</a>
				 <#elseif productStatus?exists && productStatus == '4'>
				    <a href="javascript:void(0)" class="buyitem fr blackbg" id="endbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">已抢完</a>
				</#if>
				-->
				<!--  -->
				<a href="#end" class="buyitem fr" onClick="javascript:$('.select-panel').show();cartadd()" id="addbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off" >加入购物车</a>
				<a href="javascript:void(0)" class="buyitem fr" id="nextbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="">还有机会</a><!--在过期范围内lock==stock -->
				<a href="javascript:void(0)" class="buyitem fr" id="rebegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">即将开抢</a>
				<a href="javascript:void(0)" class="buyitem fr blackbg" id="endbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">已抢完</a>
				<script >
				
				</script>
				<span class="buyspan"></span>
			</div>
			
						
	   	</div>	
	   <input type="hidden" name="caId" id="caId" value="${(caId)!"0"}" />



	   	<script src="${yggcontextPath}/pages/js/swiper.min.js"></script>

	   	
	   	<script>
	   		        $(function(){
	        	refUlr();
					var mySwiper = new Swiper('.swiper-container',{
					    pagination: '.pagination',
					    paginationClickable: true,
					    moveStartThreshold: 100,
					    autoplayStopOnLast:true,
					    autoplayDisableOnInteraction:false,
					    loop:true,
						autoplay:4000,
						moveStartThreshold:0.1
					});
			  });
	   	     var  flag = false ;　　  /* 防复重提交 */
	   	     $(function(){
	   	     initFlag = true;
	   	       cartadd =  function (){
	   	        
	   	        $('.indexcart').hide();
				$('.brandcart').show();
				if(initFlag){
								initFlag = false;
				
					return;
				}
			 	var itemUpper = $('.upper.selected-btn').html();
			 	if(typeof itemUpper=="undefined"){
			 		alert('请选择颜色');
			 		return;
			 	}

			 	var itemLower = $('.lower.selected-btn').html();
			 	if(typeof itemLower=="undefined"){
			 		alert('请选择尺寸');
			 		return ;
			 	}

			 	var key = itemUpper+'-'+itemLower;
			 	var pId = pmap[key];

			 	if(typeof pId=="undefined"){
			 		alert('商品缺货');
			 		return;
			 	}
	
	   	          var productcount=$(this).find('input[name=productcount]'); //.find('input:eq(0)') ; // .parent()
	   	       　　　　　 // 　 var productcount=$(this).parent().find('#productcount') ;
	   	           var  indexId = $(this).find('.indexId') ;
	   	           var productId = pId;// $("#productId"+indexId.val()).val() ;
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
	   	     	refUlr();
	   	           /* $("#saleId").click(function(){
			             sendform("${yggcontextPath}/spcart/addsc");
			       })  */
	   	       $('.add').css('cursor', 'pointer').bind("click",cartadd) ;
	   	       
	   	       /*购物车增加*/

				var itemUp = {};
				var itemLow = {};
				var pmap = {};

				$('.plist').each(function(){
					var str = $(this).html();
					var split = str.split('-');
					if(split.length != 3){
						return ;
					}
					var col0 = split[0];
					var col1 = split[1];
					var col2 = split[2];
					itemUp[col1] = col1;
					itemLow[col2] = col2;
					var key = col1+'-'+col2
					pmap[key]= $(this).parent().find('.pkey').val();
				})				

				for(var key in itemUp){
					$('.item-up-right').append('<a href="javascript:void(0)" onClick="upperHandle(this)" class="button upper">'+key+'</a>');
				}
				for(var key in itemLow){
					$('.item-low-right').append('<a href="javascript:void(0)" onClick="lowerHandle(this)" class="button lower">'+key+'</a>')
				}

				 upperHandle = function(node){
					$('.upper').removeClass('selected-btn');
					$(node).addClass('selected-btn');
					console.log($(this).html());
				}
				 lowerHandle = function(node){
					$('.lower').removeClass('selected-btn');
					$(node).addClass('selected-btn');
					console.log($(this).html());
					
				}			   	         
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
		       //   $.ajax({
		       //                 type:'POST',
		       //                 url: requrl ,
		       //                 data:'',
		       //                 dataType: 'json' ,
		       //                 success: function(msg){

		       //                       console.log(msg);
		       //                       var d = msg.second ;
		       //                       if(d ==undefined || d=='')
		       //                       {
		       //                         $('#clockbox1').hide();
		       //                          return ;
		       //                       }
		       //                       var stime=　d　 ;
									// 　var sid="#clockbox1";
									//   window.timer1 = setInterval(function(){
									// 	　stime--;
									// 	　brandCdown(stime,sid);
									// 　},980);
									// 　brandCdown(stime,sid);
		       //                   },
		       //                 error:function(err){
		       //                   }
		       //         });  
		         
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