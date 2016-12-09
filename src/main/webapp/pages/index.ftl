<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection" />
		<meta property="wb:webmaster" content="1d1b018b9190b96c" />
		<meta property="qc:admins" content="342774075752116375" />
		<title>左岸城堡 - 优选全球放心食品，100%优质正品，每天10点上新</title>
		<link rel="shortcut icon"  href="${yggcontextPath}/pages/images/favicon.ico">
		<link rel="apple-touch-icon" href="custom_icon.png">
		<#include "./common/indexHeader.ftl">
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<!--<script src="//meiqia.com/js/mechat.js?unitid=55345f2e4eae35570400000d" charset="UTF-8" async="async"></script>-->
		<style>
		 .rSup1{ color:#ffffff; position: absolute; left:25px; top:3px; background: #ff1919; border-radius:50%; -webkit-border-radius:50%; -moz-border-radius:50%; font-size: 12px;  width: 16px; height: 16px; line-height:16px; text-align: center;}
		.rSup{ color:#ffffff; position: absolute; left:17px; top:3px; right:-15%; top:-25%; background: #ff1919; border-radius:50%; -webkit-border-radius:50%; -moz-border-radius:50%; font-size: 0.75em;  width: 1.25em; display: none}
		</style>
	</head>
	<body>
		<div class="page">
		<div class="quphone"><a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')"></a><img src="${yggcontextPath}/pages/images/sidebar.png"><span></span></div>
		<a href="${yggcontextPath}/spcart/listsc" ><div class="indexcart"><img src="${yggcontextPath}/pages/images/indexCart.png"></div>
		    <div class="brandcart">
				<img src="${yggcontextPath}/pages/images/brandCart.png">
				<span class="rSup1" id="showCartCount1"></span>
			 	<div class="goods_time2" id="buytime">
					<span class="minute"></span><span class="second"></span>
				</div><!-- 倒计时 -->				
			</div></a>
		<div class="topbar"><img src="${yggcontextPath}/pages/images/return.png"></div>
			
			<div class="tit">
				<img src="${yggcontextPath}/pages/images/gege.png" class="tit_logo">
				<a href="${yggcontextPath}/mycenter/show" class="tit_la"><img src="${yggcontextPath}/pages/images/myCenter.png" class="tit_index"></a>
				<a href="${yggcontextPath}/spcart/listsc" class="tit_ra"><img src="${yggcontextPath}/pages/images/cart.png" class="tit_cart"><span class="rSup" id="showCartCount"><!--${(cartCount)!"0"}--></span></a>
			</div>
			<div id="banner">
				<div class="swiper-container">
				    <div class="swiper-wrapper">
				    <#if bannerList ? exists>
				      <#list bannerList as bl >
				            <div class="swiper-slide">
				                <#if bl.type ? exists && bl.type='1'><!-- 单品-->
					        	 <!-- <a href="${yggcontextPath}/product/single/${(bl.id)!"0"}"><img src="${(bl.image)?if_exists}"></a> -->
					        	  <a href="/item-${(bl.id)!"#"}.htm"><img src="${(bl.image)?if_exists}"></a>
					        	   
					        	</#if>
					        	<#if bl.type ? exists && bl.type='2'><!-- 通用专场-->
					        	  <!--<a href="${yggcontextPath}/cnty/toac/${(bl.id)!"0"}"><img src="${(bl.image)?if_exists}"></a>-->
					        	  <a href="/sale-${(bl.id)!"#"}.htm"><img src="${(bl.image)?if_exists}"></a>
					        	</#if>
					        	<#if bl.type ? exists && bl.type='3'><!-- 自定义-->
					        	  <a href="${(bl.url)!"#"}"><img src="${(bl.image)?if_exists}"></a>
					        	</#if>
					      </div>
				       </#list>
				     <#else><!-- 没有banner时，显示一个默认img -->
				         <div class="swiper-slide">
					        	<a href="javascript:void(0)"><img src="${yggcontextPath}/pages/images/goods/1.jpg"></a>
					      </div>
				    </#if>
				    </div>
			   		<div class="pagination"></div>
			   </div>			 
			</div>
			  <!--
			  <script src="${yggcontextPath}/pages/js/jquery-1.11.2.min.js?v=${yggJsVersion!"1"}"></script>
			  <script src="${yggcontextPath}/pages/js/swiper.min.js?v=${yggJsVersion!"1"}"></script>
			  <script src="${yggcontextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
			  <script src="${yggcontextPath}/pages/js/shoppingcart.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
			  <script src="${yggcontextPath}/pages/js/extutil.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
			  -->
			  <script>
			     $(function(){
			        
			           var mySwiper = new Swiper('.swiper-container',{
				    	pagination: '.pagination',
				   		
					     autoplay:4000
				          })
			     });
			  </script>
			<div class="main">
			  <#if nowList ? exists>
				 <p class="main_tit">今日特卖<span class="main_span">每日10点更新</span></p>
				    <#list nowList as bl >
				        <#if bl.type ? exists && bl.type='1'><!-- 单品-->
				              <!--<a href="${yggcontextPath}/product/single/${(bl.id)!"0"}">-->
				              <a href="/item-${(bl.id)!"0"}.htm">
				               
				        </#if>
				        <#if bl.type ? exists && bl.type='2'><!-- 通用专场-->
					        	<!--<a href="${yggcontextPath}/cnty/toac/${(bl.id)!"0"}"> -->
					        	<a href="/sale-${(bl.id)!"0"}.htm">
					    </#if>
					    <#if bl.type ? exists && bl.type='3'><!-- 自定义-->
					        	<a href="${(bl.url)!"0"}"> 
					    </#if>
								<div class="goods">
									<div class="gd">
									 <img src="${yggcontextPath}/pages/images/imgplace.jpg" class="imgplace"><!-- 商品占位图片 -->
									 
										<img src="${(bl.image)?if_exists}" onload="preimg();" class="imgorg" >
										<#if (bl.status)?exists && (bl.status) =='3'>
										  <div class="soldOut on" >
											   <img src="${yggcontextPath}/pages/images/soldOut.png"><!-- 固定 -->
										  </div>
										  <#else>
										     <#if bl.type ? exists && bl.type='1'>
										       <div class="soldOut off" id="itemId${(bl.id)!"0"}">
											     <img src="${yggcontextPath}/pages/images/soldOut.png"><!-- 固定 -->
										       </div>
										     <#elseif bl.type ? exists && bl.type='2'>
										        <div class="soldOut off" id="saleId${(bl.id)!"0"}">
											     <img src="${yggcontextPath}/pages/images/soldOut.png"><!-- 固定 -->
										        </div>
										     <#else>
										        <div class="soldOut off" id="otherId${(bl.id)!"0"}">
											     <img src="${yggcontextPath}/pages/images/soldOut.png"><!-- 固定 -->
										        </div>
										    </#if>
										   
										</#if>
									</div>	
									<p>${(bl.leftDesc)?if_exists}<span>${(bl.rightDesc)?if_exists}</span></p>
									
									<div class="goods_time on" id="nowclockbox${(bl.sellWindowId)!'0'}">
									<img src="${yggcontextPath}/pages/images/timeLeft.png">剩<span class="day"></span><span class="hour"></span><span class="minute"></span><span class="second"></span>
									</div><!-- 倒计时 -->
									
									  <#if bl.labers ? exists>
									    <ul class="goods_flag">
									       <#list bl.labers  as st >
									             <li class="flag1 fl on"><img src="${(st)?if_exists}" ></li>
									       </#list>
									    </ul>
									  </#if>
									
								</div>
							</a>
				    </#list>
				    <#if nowSwIds?? &&  nowSwIds!=''>
				          <script type="text/javascript"> <!-- 倒计时 -->
							$(function(){
							    var requesturl = "${yggcontextPath}/salewindow/getendsecond" ;  
							    var requestdata ="noworlater=0&swid=${nowSwIds!"0"}";
								 $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
								     var success = msgjsonobj.success ;
								     var endseconds = msgjsonobj.endseconds ;
								     if(success !=undefined && success =='1' && endseconds.length>0 )
								     {
								           $.each(endseconds, function(i, item) {
									            var stime1= item.endSecond;
												var sid1= "#nowclockbox"+item.swId;
												var goodsId1="1";
												var timer1 = setInterval(function(){
													stime1--;
													indexCdown(stime1,sid1,goodsId1);
												},980);
												indexCdown(stime1,sid1,goodsId1);
									        });
								     }
	                            }});    
							})
						</script>
				    </#if>
				 </#if>
				 
				 <#if laterList ? exists>
				    <p class="main_tit">即将开始</p>
					   <#list laterList as bl >
						       <!-- 即将开始类别商品 -->
								 <#if bl.type ? exists && bl.type='1'><!-- 单品-->
					              <!--<a href="${yggcontextPath}/product/single/${(bl.id)!"0"}">-->
					              <a href="/item-${(bl.id)!"0"}.htm">
					               
						        </#if>
						        <#if bl.type ? exists && bl.type='2'><!-- 通用专场-->
							        	<!--<a href="${yggcontextPath}/cnty/toac/${(bl.id)!"0"}"> -->
							        	<a href="/sale-${(bl.id)!"0"}.htm">
							        	 
							    </#if>
							    <#if bl.type ? exists && bl.type='3'><!-- 自定义-->
							        	<a href="${(bl.url)!"0"}"> 
							    </#if>
									<div class="goods">
										<div class="gd">
										<img src="${yggcontextPath}/pages/images/imgplace.jpg" class="imgplace"><!-- 商品占位图片 -->
											<div class="begin on">
												<p>即将开始</p>
												<span id="laterclockbox${(bl.sellWindowId)!'0'}" class="begin_span">
													<img src="${yggcontextPath}/pages/images/timeBegin.png"><span class="day"></span><span class="hour"></span><span class="minute"></span><span class="second"></span>
												</span><!-- 倒计时 -->
												
												<!-- <div class="begin_notice"><img src="${yggcontextPath}/pages/images/timeBegin.png">开团提醒</div> -->
											</div>
											<img src="${(bl.image)?if_exists}"  onload="preimg();" class="imgorg" /><!-- 商品展示图片 -->							
										</div>							
										<p>${(bl.leftDesc)?if_exists}<span>${(bl.rightDesc)?if_exists}</span></p><!-- 商品展示文字  -->	
										
										  <#if bl.labers ? exists>
										     <ul class="goods_flag">
										       <#list bl.labers  as st >
										             <li class="flag1 fl on"><img src="${(st)?if_exists}" ></li>
										       </#list>
										      </ul>
										  </#if>
									  					
									</div>
									
								</a>
							<!-- 即将开始类别商品 End -->
					   </#list>
					   <#if laterSwIds?? &&  laterSwIds!=''>
					         <script type="text/javascript"> <!-- 倒计时 -->
								$(function(){
								    var requesturl = "${yggcontextPath}/salewindow/getendsecond" ;   
								    var requestdata ="noworlater=1&swid=${laterSwIds!"0"}";
									 $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
									     var success = msgjsonobj.success ;
									     var endseconds = msgjsonobj.endseconds ;
									     if(success !=undefined && success =='1' && endseconds.length>0 )
									     {
									           $.each(endseconds, function(i, item) {
										                var ntime12= item.endSecond; ; 
														var nid12="#laterclockbox"+item.swId;
														var regoodsId12="12";
														var timer12 = setInterval(function(){
															ntime12--;
															indexRdown(ntime12,nid12,regoodsId12);
														},980);
														indexRdown(ntime12,nid12,regoodsId12);
										        });
									     }
		                            }});    
								})
							</script>
					   </#if>
					 </#if>
				 
			</div>			
		</div>	
<script>
		    $(function(){
		    var timer  ;
		       $.getCartCount('${yggcontextPath}/spcart/showcartcount',''); /*查询购物车的数量*/
		       
		       ///////////////////每次请求得到 一个时间,是和每天10点的时间差,到0时,刷新页面////////////////
		       var refreshurl = "${yggcontextPath}/getrefreshtime"; // 在home中
		        $.ajaxquery({"url":refreshurl,"data":'',"success":function(msgjsonobj){
					     var status = msgjsonobj.status ;
					     var refreshtime = msgjsonobj.refreshtime ;
					     if(status !=undefined && status =='1' )
					     {
					            var ntime= refreshtime ; 
					            
							   timer = setInterval(function(){
									ntime--;
									refreshTimeDown(ntime);
								},980);
								refreshTimeDown(ntime);
								
							var nvs = msgjsonobj.nvs ;
							$.each(nvs, function(i, item){      
						           if(item.type !=undefined && item.type ==1)
						              $('#itemId'+item.id).addClass('on');
						           else if(item.type !=undefined && item.type ==2)
						              $('#saleId'+item.id).addClass('on');
						      　}); 
					     }
                    }});  
		          
		    });
		    
function refreshTimeDown(time){
 
	var end_time = time;	
	if (end_time > 0) {		
		
	} else {
		 window.location.href="${yggcontextPath}/index";
		// document.refreshForm.submit();
		// clearInterval(timer);
	}	
}		    
</script>	
<form id="refreshForm" name="refreshForm" action="${yggcontextPath}/index" method="post">
 <input type="hidden" name="reload" value="1" /> <!-- may be has probleam -->
</form>

</body>
</html>