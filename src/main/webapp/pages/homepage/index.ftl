<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<title>左岸城堡</title>
<link href="${rc.contextPath}/pages/css/bootstrap.css" rel="stylesheet" type="text/css">
<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js" type="text/javascript"></script>
<link href="${rc.contextPath}/pages/css/index.css" rel="stylesheet" type="text/css">
<link href="${rc.contextPath}/pages/css/animate.css" rel="stylesheet" type="text/css">
	<#include "../common/otherShare.ftl">
<link href="/ygg-hqbs/pages/css/swiper.css?v=${yggCssVersion!"1"}" rel="stylesheet" type="text/css"/>
<script src="/ygg-hqbs/pages/js/swiper.min.js?v=${yggJsVersion!"1"}"></script>

</head>

<body class="body" style="width:100%;overflow-x:hidden">
<div class="container-fluid" style="width:100%;overflow-x:hidden">
	<div class="row">
    	<div class="col-xs-12 full-row banner">
			<div class="swiper-container">
			    <ul class="swiper-wrapper clearfix">
		            <li class="swiper-slide">
				        <a href="http://zuoan.waibao58.com/ygg-hqbs//cnty/toac/4780" 
				        style="background-image: url('${rc.contextPath}/scripts/images/lunbopic1.jpg')">
				        </a>
				    </li>
		            <li class="swiper-slide">
				        <a href="http://zuoan.waibao58.com/ygg-hqbs//cnty/toac/4781" 
				        style="background-image: url('${rc.contextPath}/scripts/images/lunbopic2.jpg')">
				        </a>
				    </li>				    
			    </ul>
	<!--	   <div class="pagination" style="text-align: center;    position: absolute;z-index:9;"></div>-->
		   </div>    
		   	
        </div>
        <script>
		function mySwiper(){
			var mySwiper = new Swiper('.swiper-container',{
			    moveStartThreshold: 100,
			    autoplayStopOnLast:true,
			    autoplayDisableOnInteraction:false,
			    loop:true,
				autoplay:4000,
				moveStartThreshold:0.1
	        });

		}//图片轮播
		mySwiper();        
        </script>
        <div class="col-xs-12 notice">
            <DIV class="notice-txt " style="position:relative;">左岸城堡精致法式童装上市, 代言人招募中</DIV>
        </div>
        <script>
        var initFlag = true;
        function moveTxt(){
        if(initFlag){
        	spread = 5000;
        }
        else{
        	spread = 10000;
        	$(".notice-txt").css("left","105%");
        }
        initFlag = false;
        	$(".notice-txt").animate({
    			left:'-105%'
  				},10000,
  				function(){
  					moveTxt()
  				}
  			);
        }
  		moveTxt();
        </script>
    </div>
    <div class="row">
    	<div class="col-xs-12 full-row main-pic-01">
    		<a href="http://zuoan.waibao58.com/ygg-hqbs//cnty/toac/4821" >
    		 
    		 <img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/8.16753.jpg"
		                            
		                            >
		       </a>
    	</div>
    	<div class="col-xs-12 full-row main-pic-01">
    	<a href="http://zuoan.waibao58.com/ygg-hqbs//cnty/toac/4796" >
    	
    	 <img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/8.16621.jpg"
		                            
		                            >
    	</a>
    	</div>
    	<div class="col-xs-12 full-row main-pic-01">
    	<a href="http://zuoan.waibao58.com/ygg-hqbs//cnty/toac/4831" >
    	<img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/8.16995.jpg"
		                            
		                            >
    	</a>
    	</div>
    </div>
    <div class="row new">
    	<div class="col-xs-12 full-row title">
            <p class="title-txt">新品</p>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 product">
            <#if saleList ? exists>     
            <#assign len = ((saleList?size)/2)?floor> 
            <#list 0..len as i>
            	<#if i<len>
	            <#assign productLeft=saleList[2*i]>
	            <#assign productRight=saleList[2*i+1]>

		        	<div class="row">
		            	<div class="col-xs-6">
		                <div class="card">
		                	<a href="${productLeft.url}">
		                    <div class="row">
		                        <div class="col-xs-12 card-pic" >
		                            <img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/imgplace01.png"
		                            data-original="${productLeft.image}"
		                            
		                            >
		                            
		                        </div>
		                	</div>
		                	<div class="row">
		                        <div class="col-xs-6 card-txt" >
		                            <p >￥${productLeft.price}</p>
		                        </div>
		                        <div class="col-xs-6 card-btn text-right" >
		                        	<img  src="${rc.contextPath}/pages/images/zuoan_homepage_new_buy_icon.png">
		                        </div>
		                	</div>
		                	</a>
		                </div>
		            	</div>
		            	
		            	<div class="col-xs-6">
			                <div class="card">
			                	<a href="${productRight.url}">
			                    <div class="row">
			                        <div class="col-xs-12 card-pic" >
			                            <img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/imgplace01.png"
		                            data-original="${productRight.image}"
		                            
		                            >
			                        </div>
			                	</div>
			                	<div class="row">
			                        <div class="col-xs-6 card-txt" >
			                            <p >￥  ${productRight.price}</p>
			                        </div>
			                        <div class="col-xs-6 card-btn text-right" >
		                        		<img src="${rc.contextPath}/pages/images/zuoan_homepage_new_buy_icon.png">
			                        </div>
			                	</div>
   			                	</a>
			                </div>
		            	</div>
		            	
		            </div>            	
            	</#if>
            </#list>
            <#assign tail = (saleList?size - 2*len)>
            <#assign size = (saleList?size)-1>
            <#if tail==1>
            	<#assign product=saleList[size]>
				<div class="row">
	               	<a href="${product.url}">
	            	<div class="col-xs-6">
	                <div class="card">
	                    <div class="row">
	                        <div class="col-xs-12 card-pic" >
	                            <img class="img-responsive imglazyload" 
		                            src="${rc.contextPath}/scripts/images/imgplace01.png"
		                            data-original="${product.image}"
		                            
		                            >
	                        </div>
	                	</div>
	                	<div class="row">
	                        <div class="col-xs-6 card-txt" >
	                            <p >￥${product.price}</p>
	                        </div>
	                        <div class="col-xs-6 card-btn text-right" >
		                        <img src="${rc.contextPath}/pages/images/zuoan_homepage_new_buy_icon.png">
	                        </div>
	                	</div>
	                	</a>
	                </div>
	            	</div>
	            	<!--
	            	<div class="col-xs-6">
	                <div class="card">
	                    <div class="row">
	                        <div class="col-xs-12 card-pic" >
	                            <img class="img-responsive" src="${rc.contextPath}/pages/images/zuoan_homepage_new_pic_01.png">
	                        </div>
	                	</div>
	                	<div class="row">
	                        <div class="col-xs-7 card-txt" >
	                            <p >￥  159.00</p>
	                        </div>
	                        <div class="col-xs-4 card-btn" >
	                        	<img src="${rc.contextPath}/pages/images/zuoan_homepage_new_buy_icon.png">
	                        </div>
	                	</div>
	                </div>
	            	</div>
	            	-->
	            </div>            	
            </#if>

           </#if>
    	</div>

    </div>
</div>

<br />
<br />
<br />
<br />
	<script src="${rc.contextPath}/scripts/js/jquery-1.11.2.min.js"></script>
	<script src="${rc.contextPath}/pages/js/touch.js"></script>

	<!--底部导航 Start-->
	<input type="hidden" id="navFooterToPage" value="0"/>
	<#include "../common/navFooter.ftl">
	<!--底部导航 End-->
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>


<script type="text/javascript">

var topValue = 0,// 上次滚动条到顶部的距离
    interval = null;// 定时器

function onCount() {
    // 判断此刻到顶部的距离是否和1秒前的距离相等
    if(document.documentElement.scrollTop == topValue) {
        $(".listcount").hide().prev().fadeIn();
        clearInterval(interval);
        interval = null;
    }
}

$(function(){


    function solidtu(){
    	// 初始化焦点图的高宽
        var $this = $(".swiper-wrapper");
        var $li = $this.find("li");
        var $vw = $(window).width();
        if($vw >= 640){
            $vw = 640;
        }
        var tu_length = $li.length;
        $li.width($vw)
        var tu_w = $li.width() ;
        var list_w = tu_w * tu_length;
        var sum = 5/3;

	    $(".swiper-wrapper").height($vw/sum);
        $this.width(list_w*2);

    }
    solidtu();
    $(window).resize(function(){
    	solidtu();
    })


		 function imgHeightSet(){
            var _height = $("#productList .imgX").find("img").height();
            $("#productList .imgX").height(_height);
            var _bannerheight = $("#banner").find("img").height();
            $("#banner .swiper-container").height(_bannerheight);
        }//图片根据屏幕宽等比例缩放

		var test = setTimeout(function(){
			// imgHeightSet();
	       $(".imglazyload").picLazyLoad({//图片懒加载
	           threshold: 600,
	           placeholder: ''
		    });
	    },500);


		function mySwiper(){
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

		}//图片轮播
		mySwiper();

		/*function picLazyLoadFn(){
			var img1 = new Image(); //创建一个Image对象，实现图片的预下载
		    img1.src = "${rc.contextPath}/scripts/images/imgplace01.png";
		    if (img1.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
		    	var _defaultImgX = img1.src;
				location.href = location.href + "#" + (new Date).getTime(),
		        window.onhashchange = function() {
		             _winScrollTop = $(window).scrollTop();
		              $(".imglazyload").picLazyLoad({//图片懒加载
				           threshold: _winScrollTop,
				           placeholder: _defaultImgX
				       	});
		        }();
		    }

		}//图片懒加载;
		picLazyLoadFn();*/

		//窗口滚动时事件处理
		var listCount = $("#productList a").length,
			countTxt = $(".listall"),
			countCurrent = $(".currentcount"),
			listTop = 0,//$("#productList").offset().top,
			differTop = 0,endTop = 0,count = 0,
			listH = $("#productList a").height(),
			startnum = parseInt($(window).height()/listH),
			docTop = $(document).scrollTop(),
			otherH = $("#banner").height() + $("#footBar").height(),
			endH = $("body").height()-$(window).height();

   		countTxt.text(listCount);

		if(docTop > listTop){
			startnum = parseInt(($(window).height()+docTop-otherH)/listH);
			if(startnum > listCount){
				startnum = listCount;
			}
		}
   		countCurrent.text(startnum);



		$(window).scroll(function(){
			$(".listcount").show().prev().hide();
			docTop=$(window).scrollTop();
			if(docTop>300){
				$('.topbar').fadeIn();
			}else{
				$('.topbar').fadeOut();
			}

			// //根据滚动的距离大小加减商品显示的数额

			var tnum = parseInt(($(window).height()+docTop-otherH)/listH);
			if(tnum <1 || tnum > listCount) return false;
			countCurrent.text(tnum);


			if(interval == null)// 未发起时，启动定时器，1秒1执行
                interval = setInterval("onCount()", 1000);
            topValue = document.documentElement.scrollTop;

		});



		//返回顶部
		$('.topbar').click(function(){
			$('html,body').animate({scrollTop:'0'},800);
			return false;
		});

		//滑动时显示商品数量
		touch.on("body","dragstart drag",function(){
	   		$(".listcount").show().prev().hide();
	   	})
	   	touch.on("body","dragend",function(){
	   		if(docTop == endH){
	   			$(".listcount").hide().prev().fadeIn();
	   		}

	   	})

		function brandCdown(time,id){
			var day_elem = $(id).find('.day');
			var hour_elem = $(id).find('.hour');
			var minute_elem = $(id).find('.minute');
			var second_elem = $(id).find('.second');
			var end_time = time;

			if (end_time > 0) {
				var day = Math.floor((end_time / 3600) / 24);
				var hour = Math.floor((end_time / 3600) % 24);
				var minute = Math.floor((end_time / 60) % 60);
				var second = Math.floor(end_time % 60);
				if(day==0){
					$(day_elem).text('');
				}else{
					$(day_elem).html('<span class="dayNum">'+day+'</span>天');//计算天
				}

				$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
				$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
				$(second_elem).text(second<10?'0'+second:second);//计算秒杀
				// window.location.href=requestmodifyurl ;
			} else {
				//时间到达后执行操作
				//$(day_elem).text('0天');//计算天
				$(hour_elem).text('00:');//计算小时
				$(minute_elem).text('00:');//计算分钟
				$(second_elem).text('00');//计算秒杀

				clearInterval(timer) ;
				 window.location.href=requestmodifyurl ;  // requestmodifyurl 是brandhtml.ftl 中的变量
				// document.refreshFormBrand.submit();  // 倒计时到期后刷新页面
			}
		}

		function countDownTimeX(){
		         var _productListX = $("#productList");
		         var _goodsTime = _productListX.find(".timeBg01");

		         $.each(_goodsTime,function(i) {
		        	 	var stime_i=　_goodsTime.eq(i).attr("value");
						var sid_i= _goodsTime.eq(i);
						var timer12 = setInterval(function(){
							stime_i--;
							brandCdown(stime_i,sid_i);
						},980);
						brandCdown(stime_i,sid_i);
		        });
		 }
	   countDownTimeX();//倒计时请求


	})
</script>
<!-- <script src="${rc.contextPath}/pages/js/h5self-adaption.js" type="text/javascript"></script> -->
<script src="${rc.contextPath}/scripts/js/imglazyLoad.js" type="text/javascript"></script>
<script src="${rc.contextPath}/pages/js/swiper.min.js" type="text/javascript"></script>
</html>
<script>
$(".imglazyload").picLazyLoad({//图片懒加载
	           threshold: 600,
	           placeholder: ''
		    });
</script>

