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
     <script src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-1.9.0.min.js"></script>
		<style>
      .mydetail img { width:100%; height:auto; }
    </style>
		<script>
	 $(document).ready(function(){
 $(".lmore").hide();
 var l=$('.mydetail').children('img').length;
 if(l>12){
 $(".lmore").show();
 for(var i=12;i<l;i++){$(".mydetail").find("img").eq(i).hide();}
 $(".lmore").click(function(){
			for(var j=12;j<l;j++){$(".mydetail").find("img").eq(j).show();$(".lmore").hide();}
			 });
 }
 });
	</script>

		<#include "../common/productShare.ftl">
	</head>
	<body>
		<div class="page">
			<a href="${yggcontextPath}/hqbsHomeInfo/getHomeInfo" class="top-back-home">
				<img src="http://zuoan.waibao58.com/ygg-hqbs/logo-2.png">
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
					        	<a href="javascript:void(0)"><img src="${yggcontextPath}/pages/images/goods/2.jpg"></a><!-- 商品详情展示图片 -->
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
			</div>	   <!-- ${sellCount!"0"}  ${stockCount!"0"} -->
			<div class="count off"><em id="psellCount"></em>人已购买<span>还剩<em id="stockCount"></em>件</span></div>
			 <!--  <#if isBonded?exists && isBonded == '1' >
			      <ul class="tips">
			           <li><span>*</span>保税仓直接发货，根据海关规定，下单时需要提供身份证号码。</li>
				       <li><span>*</span>本产品由品牌方“三只松鼠”为您发货。</li>
				   </ul>
			   </#if> -->
			   <#if notesList?? && (notesList?size > 0) >
			   <ul class="tips">
			     <#list notesList as nl>
						<li><span>${nl?replace("左岸城堡","左岸城堡")}</span></li>
				 </#list>
				  </ul>
			   </#if>
			<!-- 产品名称头 End-->
			<!-- 格格说 -->
			<dl class="gegetalk clear">
				<dt><img src="http://zuoan.waibao58.com/ygg-hqbs/logo-1.jpg"></dt>
				<br/>
				<dd class="replace-str">${gegeSay!""}</dd>
			</dl>
			<!-- 格格说 End-->
			<!-- 商品简介
			<dl class="intro">
				<dt>商品简介</dt>
				<#if summary??>
				   <#if summary.brand?? && summary.brand !=''>
				     <dd><em>【品　　牌】</em><span>${(summary.brand)!""}</span></dd>
				   </#if>
				   <#if summary.name?? && summary.name!=''>
					 <dd><em>【名　　称】</em><span>${(summary.name)!""}</span></dd>
				   </#if>
					 <#if summary.placeOfOrigin?? && summary.placeOfOrigin!=''>
					 <dd><em>【产　　地】</em><span>${(summary.placeOfOrigin)!""}</span></dd>
				   </#if>
				   <#if summary.specification?? && summary.specification!=''>
					 <dd><em>【<label>净含量</label>】</em><span>${(summary.specification)!""}</span></dd> 
				   </#if>
					 <#if summary.durabilityPeriod?? && summary.durabilityPeriod!=''>
					 <dd><em>【<label>保质期</label>】</em><span>${(summary.durabilityPeriod)!""}</span></dd>
				   </#if>
					 <#if summary.manufacturerDate?? && summary.manufacturerDate!=''>
					 <dd><em>【质　　地】</em><span>${(summary.manufacturerDate)!""}</span></dd>
				   </#if>
				   <#if summary.foodMethod?? && summary.foodMethod !=''>
					  <dd><em>【主要功能】</em><span>${(summary.foodMethod)!""}</span></dd>
				   </#if>
					 <#if summary.peopleFor?? && summary.peopleFor !=''>
					  <dd><em>【适用人群】</em><span>${(summary.peopleFor)!""}</span></dd>
				   </#if>
				   <#if summary.tip?? && summary.tip !=''>
					  <dd><em>【产品介绍】</em><span>${(summary.tip)!""}</span></dd>
				   </#if>
					 <#if summary.storageMethod?? && summary.storageMethod !=''>
					 <dd><em>【成　　分】</em><span>${(summary.storageMethod)!""}</span></dd>
					</#if>
					 <#if summary.useMethod?? && summary.useMethod !=''>
					 <dd><em>【使用方法】</em><span>${(summary.useMethod)!""}</span></dd>
				   </#if>
				</#if>
			</dl>
			商品简介 End-->
			<!-- 相关链接 -->
			  <#if tipList?? && (tipList?size>0) >
			     <ul class="relatelink" style="display:none;">
			        <#list  tipList as tl >
			            <a href="${(tl.link)!""}"><li><img src="${yggcontextPath}/pages/images/plink.jpg">${(tl.name)!""}</li></a>
			       </#list>
			    </ul>
			  </#if>
			<!-- 相关链接 End-->
			<!-- 查看图文详情 -->
			<#if pcDetail?? >
			<div class="detail">
				<!-- <div class="detail_tit">商品详情</div> -->
				<div class="mydetail"><!-- 商品详情正文处 -->
					${(pcDetail)?replace("<img src=\"http://img.gegejia.com/baobeigaozhi.jpg\" />","")}
				</div>
				<div class="lmore" style=" width:50%; height:30px; line-height:30px; margin-left:25%; font-size:16px;text-align:center;color:#666666; margin-top:20px;">查看更多</div>
			</div>
			</#if>
			<!-- 查看图文详情 End -->
			<!-- 购买 -->
			<div class="buy clear">
				<a href="${yggcontextPath}/spcart/listsc">
					<div class="buy_cart"><!--　购物车的数量 -->
						<img src="${yggcontextPath}/pages/images/cart_b.png"><span class="buysub off" id="showCartCount1"><!--${(cartCount)!"0"}--></span>
					</div>
				<div class="buytime fl off"><!--　购物车的锁定时间 -->
					<img src="${yggcontextPath}/pages/images/buytime.png"><span id="buytime"><span class="minute"></span><span class="second"></span></span>
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
				<a href="javascript:void(0)" class="buyitem fr" id="addbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off" >加入购物车</a>
				<a href="javascript:void(0)" class="buyitem fr" id="nextbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="">还有机会</a><!--在过期范围内lock==stock -->
				<a href="javascript:void(0)" class="buyitem fr" id="rebegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">即将开抢</a>
				<a href="javascript:void(0)" class="buyitem fr blackbg" id="endbegin"><img src="${yggcontextPath}/pages/images/chance.png" class="off">已抢完</a>

				<span class="buyspan">￥${lowPrice!"0.00"}</span>
			</div>
			<!-- 购买 End -->
            <div class="protips"></div>
            <div class="pronotice"><img src="${yggcontextPath}/pages/images/loading1.gif">正在加入购物车...</div>
         <form  id="form1" name="form1" method="post">
             <input type="hidden" name="lowPrice" value="${lowPrice!"0"}" />
             <input type="hidden" name="productid" value="${productid!"0"}" />
             <input type="hidden" name="productcount" value="0" />　 <!-- 单商品的数量　-->
             <input type="hidden" name="highPrice"  value="${highPrice!"0.00"}" />
             <input type="hidden" name="name"  value="${name!""}" />
             <input type="hidden" name="isBonded"  value="${isBonded!""}"  />
             <input type="hidden" name="sellerName"  value="${sellerName!""}"  />
             <input type="hidden" name="gegeSay"  value="${gegeSay!""}"  /><!-- 格格说 描述-->
             <input type="hidden" name="sellCount"  value="${sellCount!"0"}"  />
             <input type="hidden" name="stockCount"  value="${stockCount!"0"}"  /><!--库存要到库中去查询-->
             <input type="hidden" name="spcartcount" id="spcartcount"  value="0"  /> <!-- 购物车的总数量-->
         </form>

		</div>

		<script src="${yggcontextPath}/pages/js/jquery-1.11.2.min.js?v=1"></script>
		<script type="text/javascript" src="${yggcontextPath}/pages/js/gegejia.js?v=${yggJsVersion!"1"}"></script>
		<script src="${yggcontextPath}/pages/js/shoppingcart.js?v=${yggJsVersion!"1"}"  type="text/javascript" ></script>
	   	<script src="${yggcontextPath}/pages/js/swiper.min.js"></script>
	    <script>
	        var reqShowCartCountUrl = '${yggcontextPath}/spcart/showcartcount' ;
	        var reqmodifystatusurl =  "${yggcontextPath}/spcart/getproductstatus/${productid!"0"}";
	        var timer = null ;
	        var  flag = false ;　   /* 防复重提交 */
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

			$(function(){


			     $("#addbegin").click(function(){
			         var productid = $('#form1 input[name=productid]').val();
			         var productcount = $('#form1 input[name=productcount]').val();
			         productcount = parseInt(productcount)+1 ;
			         var data = "productid="+productid+"&productcount="+productcount ;
			         editshoppingcart("${yggcontextPath}/spcart/editscbsp",data);

			         /////更新商品的状态//////
			         var requrl = "${yggcontextPath}/spcart/getproductstatus/${productid!"0"}";
			         $.ajax({
		                       type:'POST',
		                       url: requrl ,
		                       data:'',
		                       dataType: 'json' ,
		                       success: function(msg){
		                             var second= msg.second ;
		                             var productStatus = msg.productStatus ;
		                             showProductStatus(productStatus);
		                         },
		                       error:function(err){
		                         }
		               });

			     });

			     $("#nextbegin").click(function(){
			          showTipMsg("此商品有人拍下未付款，还有机会购买，试着刷新页面看看吧～");
			     });
			     $('#rebegin').click(function(){
			          showTipMsg("现在还抢不了哦～");
			     });
			}) ;

		</script>
		<script>
		    $(function(){
				var _replaceText = $('.replace-str');
	 			_replaceText.each(function(){
	 				var _this = $(this);
	 				replaceStr(_this);
	 			})
		         $.getCartCount('${yggcontextPath}/spcart/showcartcount','productid=${productid!"0"}'); /*查询购物车的数量*/

		         //// 查多少人购买，库存，倒计时 ////
		         var  caId = $("#caId").val() ;
		         var  requrl =  "${yggcontextPath}/product/getproductinfo/${productid!"0"}" ;
		         //倒计时请求
		         $.ajax({
		                       type:'POST',
		                       url: requrl ,
		                       data:'',
		                       dataType: 'json' ,
		                       success: function(msg){
		                             var second= msg.second ;
		                             var sellCount = msg.sellCount ;
		                             var stockCount =msg.stockCount ;
		                             var productStatus = msg.productStatus ;

		                             $('#psellCount').html(sellCount);
		                             $('#stockCount').html(stockCount);
		                              showClockTime(productStatus,second);
		                         },
		                       error:function(err){
		                         }
		               });


		      // $('.buyitem').hide();
		      function showClockTime(productStatus,second)
			  {
					/* count down */
					var stime='';
					var etime='';
					if(productStatus == '1' )
					   {
					     etime=second ;
					  }else if(productStatus == '2')
					  {
					    etime=second ;
					  }else if(productStatus == '3')
					  {
					    stime=second ;
					  }else if(productStatus == '4')
					  {
					    etime=second ;
					  }

					if(stime!='' || stime>0){
						//$('#rebegin').show();
						// $('.pgoods_time').addClass('off');
						$('.pgoods_time1').show();
						var sid="#clockbox1";
						var timer = setInterval(function(){
							stime--;
							countDownA(stime,sid,etime);
						},980);
						countDownA(stime,sid,etime);
					}else{
						if(etime!='' || etime>0){
							//$('#addbegin').show();
							//$('.pgoods_time1').addClass('off');
							$('.pgoods_time').show();
							var sid="#clockbox";
							var timer = setInterval(function(){
								etime--;
								countDown(etime,sid);
							},980);
							countDown(etime,sid);
						}else{
							//$('#endbegin').show();
							//$('.pgoods_time1').addClass('off');
							//$('.pgoods_time').addClass('off');
							$('.pgoods_time1').hide();
							$('.pgoods_time').hide();
						}
					}
					showProductStatus(productStatus);
				}

		    }) ;

	function  showProductStatus(ps)
		 {
		     $('.buyitem').hide();
		      if(ps == '1' )
			  {
			     $('#addbegin').show();
			  }else if(ps == '2')
			  {
			    $('#nextbegin').show();
			  }else if(ps == '3')
			  {
			    $('#rebegin').show();
			  }else if(ps == '4')
			  {
			    $('#endbegin').show();
			  }
		 }

 			//去掉/
 			function replaceStr(str){

 				var _this = $(str),
 					_str = _this.text(),
		    		_replace = '';

		    		if(_str.length == '') return false;

		    		_replace = _str.replace('\\','');

		    		_this.text(_replace);
 			}

		</script>
	</body>
</html>
