var _hmt = _hmt || [];

var brandtimer='';
function preimg(){	
	$('.imgplace').hide();
	$('.imgorg').show();
}


$(function(){
	
	$(document).scroll(function(){
		var docTop=$(document).scrollTop();
		if(docTop>350){
			$('.topbar').fadeIn();
		}else{
			$('.topbar').fadeOut();
		}
	});

	$('.topbar').click(function(){
		$('html,body').animate({scrollTop:'0'},800);
		return false;
	});

	
	var qxzcookie1=getCookie("ggjquxiazai");
	if(qxzcookie1 != 1){
		
		$('.quphone').show();
	}
	
	$('.quphone span').click(function(){
		var qxzcookie=getCookie("ggjquxiazai");
		 
		if(qxzcookie == null){
			setCookie("ggjquxiazai","1","d3");  
			$('.quphone').animate({height:'0',paddingTop:'0'});
			
		}else{
			$('.quphone').hide();
		}
	});
	
	 
	
	$('.gegetalk1').click(function(){
		$('.gegetalk1 ul').css('height','auto');
		var gimg1=$('.gegeright_img1').hasClass('off');
		var gimg2=$('.gegeright_img2').hasClass('off');
		var ggtop=parseInt($('.gegetalk1 ul').css('height'));
		if(ggtop==48){
			
		}else{
			if(gimg2){
				$('.gegeright_img1').addClass('off');
				$('.gegeright_img2').removeClass('off');
			}else{
				$('.gegetalk1 ul').css('height','48');
				$('.gegeright_img2').addClass('off');
				$('.gegeright_img1').removeClass('off');
			}
		}
	});
	
	var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?ffbc348348dc3a0b834dbeadd52e4462";
    var s = document.getElementsByTagName("script")[0]; 
    s.parentNode.insertBefore(hm, s);
	

	browserRedirect();
	/*$('.cmath_c').click(add);*/
	/*$('.cmath_ca').click(substract);*/
	/*$('.clist_foot img').click(cartdel);*/
	$('.payta').click(function(){
		$('.pay ul li:eq(0)').text('支付宝支付');
		$(this).find('.cpayflag').attr('src','/ygg-hqbs/pages/images/select.png');
		$('.paytb').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		
		var  paytype = $("#form1 input[name=paytype]") ; 
		if(paytype!=undefined &&paytype !='undefined')
		    $("#paytype").val(2) ; // 支付宝
	});
	$('.paytb').click(function(){
		$('.pay ul li:eq(0)').text('微信支付');
		$(this).find('.cpayflag').attr('src','/ygg-hqbs/pages/images/select.png');
		$('.payta').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		
		var  paytype = $("#form1 input[name=paytype]") ; 
		if(paytype!=undefined &&paytype !='undefined')
			paytype.val(3) ; //微信支付
	});
	$('.paytc').click(function(){
		$('.pay ul li:eq(0)').text('银联支付');
		$(this).find('.cpayflag').attr('src','/ygg-hqbs/pages/images/select.png');
		$('.payta').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		$('.paytb').find('.cpayflag').attr('src','/ygg-hqbs/pages/images/unselect.png');
		
		var  paytype = $("#form1 input[name=paytype]") ; 
		if(paytype!=undefined &&paytype !='undefined')
			paytype.val(1) ; //联支付
	});
	/*$('.monav li').click(function(){
		$('.monav .checked').css('display','none');
		$(this).find('.checked').css('display','block');
	});*/

	/* 单品页面购买倒计时 */
	$('#addbegin1').click(function(){
		var num=$('.buysub').text();
		var sum=$('.rSup').text();
		num++;
		sum++;
		$('.rSup').text(sum);
		$('.buysub').text(num);
		$('.buytime').removeClass('off');		

		$.ajax({
			url:'',
			type:'POST',
			data:{},
			dataType:'json',
			success:function(data){



				//提供点击购买倒计时秒数				
				var etime=417;
				var sid="#buytime";
				var timer = setInterval(function(){
					etime--;
					proCdown(etime,sid);
				},1000);
				proCdown(etime,sid);


				/* 弹出层 */
				$('.protips').html('提示文字没有库存等！');
				var scrollTop=$(document).scrollTop();
				var windowTop=$(window).height();
				var xtop=windowTop/2+scrollTop;
				$('.protips').css('top',xtop);
				$('.protips').css('display','block');
				setTimeout(function(){			
					$('.protips').css('display','none');
				},2000);


			},
			error:function(){
				/* console.log('er'); */
			}
		});
	});

	/* 我的订单 全部 */
	$('#orall').click(function(){
		$.ajax({
			url:'',
			type:'POST',
			data:{},
			dataType:'json',
			success:function(data){
				
			},
			error:function(){
				/* console.log('er'); */
			}
		});
	});


});
/* check browser */
function browserRedirect() {  
	$('.buy').animate({bottom:0});
	$('.carttotal').animate({bottom:0});
	$('.pay').animate({bottom:0});

	
    var sUserAgent = navigator.userAgent.toLowerCase();  
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";  
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";  
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";  
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";  
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";  
    var bIsAndroid = sUserAgent.match(/android/i) == "android";  
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";  
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";  
    if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){  
    	$('.page').removeClass('page').addClass('wpage');
        $('.pagea').removeClass('pagea').css({'width':'540px','height':'100%','margin':'0 auto'});
        $('.pagep').removeClass('pagep').addClass('wpagep');
        $('.swiper-slide img').css('width','540');
        $('.buy').css({'width':'515','left':'50%','marginLeft':'-270px'});
        $('.buyitem').css('marginRight','0');
        $('.carttotal').css({'width':'520','left':'50%','marginLeft':'-270px'});
        $('.carttotal a').css('marginRight','0');
        $('.pay a').css('marginRight','0');
        $('.pay').css({'width':'520','left':'50%','marginLeft':'-270px'});
        $('.begin p').css('paddingTop','1.8em');
        // $('.swiper-container').css({'width': '100%','height': '219px'});
        $('.gegetalk dd').css('width','84%');
        $('.intro span').css('width','81%');
        $('.tips li').css('marginLeft','-24px');
        $('.list ul').css('width','85%');
    }else{
    	var ggtop=$('.gegetalk1 ul').height();
    	if(ggtop<=48){
		 $('.gegeright').hide();
		 $('.gegetalk1 ul').css('marginRight',0);
		 $('.gegetalk1').css('paddingRight',10);
		}else{
			$('.gegetalk1 ul').css('height','48');
		}
    }
    if(bIsIphoneOs){
    	$('.txtarea span').css('width','78px');  	
    }
    
} 
           
/* count down */
function countDown(time,id){
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
		//$(day_elem).text((day<10?'0'+day:day)+'天');//计算天

		//$(day_elem).text(day+'天');//计算天
		if(day==0){
			$(day_elem).text('');
		}else{
			$(day_elem).text(day+'天');//计算天
		}
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
		
	} else {
		//时间到达后执行操作
		//$('.buyitem').hide();
		//$('#endbegin').show();
		$('.pgoods_time1').hide();
		$('.pgoods_time').hide();
		 
		//$(id).hide();
		//clearInterval(timer);
		modifystatus();
	}	
}
/* count down */
function countDownA(time,id,etime){
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
		//$(day_elem).text((day<10?'0'+day:day)+'天');//计算天
		//$(day_elem).text(day+'天');//计算天
		if(day==0){
			$(day_elem).text('');
		}else{
			$(day_elem).text(day+'天');//计算天
		}
		
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		//$('.buyitem').hide();
		//$('#addbegin').show();
		$('.pgoods_time').show();
		$('.pgoods_time1').hide();
		var sid="#clockbox";
		var timer = setInterval(function(){
			etime--;
			countDown(etime,sid);
		},1000);
		countDown(etime,sid);	
		// modifystatus();
	}	
}
/* 购物车倒计时 */
function cartCdown(time,id){

	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;
	
	if (end_time > 0) {
		
		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);
	
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
		
	} else {
		//时间到达后执行操作
		$('.cnotice').addClass('off');
		$('.cnoticefail').removeClass('off');		
		if( $('.cmath_c') !=undefined && $('.cmath_c') !='undefined')
			{
			   $('.cmath_c').unbind('click') ;
			   $('.cmath_c').addClass('off') ;
			}
		     
		if( $('.cmath_ca') !=undefined && $('.cmath_ca') !='undefined')
			{
			   $('.cmath_ca').unbind('click') ;
			   $('.cmath_ca').addClass('off') ;
			}
		     
		if( $('.clist_foot img') !=undefined && $('.clist_foot img') !='undefined')
			{
			   $('.clist_foot img').unbind('click') ;  
			}
		$('.delcart').addClass('off') ; 
		var iscarttimeout = $('#iscarttimeout') ;
		if(iscarttimeout !=undefined)
			iscarttimeout.val(1) ; // 购物车超时
	}	
}

/**
 * 此方法中的 reqmodifystatusurl 在 producthtml.ftl中 
 */
function modifystatus()
{
/////更新商品的状态//////
    $.ajax({
              type:'POST',
              url: reqmodifystatusurl ,
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
}


/* 购物车倒计时 */
function orderCdown(time,id){

	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;
	
	if (end_time > 0) {
		
		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);
	
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$(minute_elem).text('00:');//计算分钟
		$(second_elem).text('00');//计算秒杀
		
		if($('.cnotice') !=undefined && $('.cnotice') !='undefined' )
		     $('.cnotice').addClass('off');
		if($('.cnoticefail') !=undefined && $('.cnoticefail') !='undefined' )
		     $('.cnoticefail').removeClass('off');	
	}	
}

/* index countdown */
function indexCdown(time,id,goodsId){
	
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
			$(day_elem).text(day+'天');//计算天
		}
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀

	} else {
		//时间到达后执行操作
		$('#gd'+goodsId).hide();
							
	}	
}

function indexRdown(time,id,goodsId){
	
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
			$(day_elem).text((day<10?'0'+day:day)+'天');//计算天
		}
		
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀

	} else {
		//时间到达后执行操作
		$('#regd'+goodsId).hide();	
			
	}	
}

/* brand count down */
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
			$(day_elem).text(' 0 天');
		}else{
			$(day_elem).text(day+'天');//计算天
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

/* product count down */
function proCdown(time,id){
	
	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;	
	if (end_time > 0) {		
		
		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);
	
		/*$('#buytime').show();*/
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
		$()
	} else {
		//时间到达后执行操作
		$(minute_elem).text('00:');//计算分钟
		$(second_elem).text('00');//计算秒杀
		//$(minute_elem).text('');//计算分钟
		//$(second_elem).text('');//计算秒杀
		/*$('#buytime').hide();*/
		// $('#nextbegin').show();
		//$(id).hide();
		clearInterval(timer);
	}	
}

function proCdown1(time,id){
	
	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = time;	
	if (end_time > 0) {		
		
		var minute = Math.floor((end_time / 60) % 60);
		var second = Math.floor(end_time % 60);
	
		/*$('#buytime').show();*/
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$(minute_elem).text('00:');//计算分钟
		$(second_elem).text('00');//计算秒杀
		/*$('#buytime').hide();*/
		// $('#nextbegin').show();
		//$(id).hide();
		//clearInterval(timer);
	}	
}

/* cart math */
function add(){
	var tot=$(this).parent().find('#currentproduct').text();
	var num=++tot;
	//var prosum=parseInt($('.amount em').text());
	var totalprice=parseFloat($('.carttotal span').text());
	var sinprice=parseInt($(this).parent().parent().find('.sinprice').text());
	
	totalprice+=sinprice;
	//prosum++;
	//$('.amount em').text(prosum);
	$('.carttotal span').text(totalprice+'.00');
	$(this).parent().find('#currentproduct').text(num);
	
}
function substract(){
	var tot=$(this).parent().find('#currentproduct').text();
	var productId=$(this).parent().find('#productId').val();
	
	var num=--tot;
	//var prosum=parseInt($('.amount em').text());
	var totalprice=parseFloat($('.carttotal span').text());
	var sinprice=parseInt($(this).parent().parent().find('.sinprice').text());
	
	if(num<1){
		
	}else{
		totalprice-=sinprice;
		//prosum--;
		//$('.amount em').text(prosum);
		$('.carttotal span').text(totalprice+'.00');
		$(this).parent().find('#currentproduct').text(num);
	}
	
}

function cartdel(){
	var cend=confirm('确定要删除该商品吗？');
	if(cend){
		
	}else{

	}
}
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
function showTipLoadingMsg()
{
	
    /* 弹出层 */
	// $('.pronotice').show();
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.pronotice').css('top',xtop);
	$('.pronotice').css('display','block');
	$('.pronotice').show();
	 
}

function setCookie(name,value,time)
{
    var strsec = getsec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec*1);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

}
function getsec(str)
{
   var str1=str.substring(1,str.length)*1;
   var str2=str.substring(0,1);
   if (str2=="s")
   {
        return str1*1000;
   }
   else if (str2=="h")
   {
       return str1*60*60*1000;
   }
   else if (str2=="d")
   {
       return str1*24*60*60*1000;
   }
}
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return (arr[2]);
    else
        return null;
}