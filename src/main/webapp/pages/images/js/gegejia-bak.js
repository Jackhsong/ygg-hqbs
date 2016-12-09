$(function(){
	browserRedirect();
	/*$('.cmath_c').click(add);*/
	/*$('.cmath_ca').click(substract);*/
	/*$('.clist_foot img').click(cartdel);*/
	$('.payta').click(function(){
		$('.pay ul li:eq(0)').text('支付宝快捷支付');
		$(this).find('.cpayflag').attr('src','/ygg/pages/images/select.png');
		$('.paytb').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		
		var  paytype = $("#form1 input[name=paytype]") ; 
		if(paytype!=undefined &&paytype !='undefined')
		    $("#paytype").val(2) ; // 支付宝
	});
	$('.paytb').click(function(){
		$('.pay ul li:eq(0)').text('微信支付');
		$(this).find('.cpayflag').attr('src','/ygg/pages/images/select.png');
		$('.payta').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		$('.paytc').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		
		var  paytype = $("#form1 input[name=paytype]") ; 
		if(paytype!=undefined &&paytype !='undefined')
			paytype.val(3) ; //微信支付
	});
	$('.paytc').click(function(){
		$('.pay ul li:eq(0)').text('银联支付');
		$(this).find('.cpayflag').attr('src','/ygg/pages/images/select.png');
		$('.payta').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		$('.paytb').find('.cpayflag').attr('src','/ygg/pages/images/unselect.png');
		
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
        $('.pagep').removeClass('pagep').addClass('wpagep');
        $('.pagea').removeClass('pagea').css({'width':'375px','height':'100%','margin':'0 auto'});
        $('.swiper-slide img').css('width','415');       
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
		$(day_elem).text(day+'天');//计算天
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$('.buyitem').hide();
		$('#endbegin').show();
		$('.pgoods_time1').addClass('off');
		$('.pgoods_time').addClass('off');
		//$(id).hide();
		//clearInterval(timer);
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
		$(day_elem).text(day+'天');//计算天
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$('.buyitem').hide();
		$('#addbegin').show();
		$('.pgoods_time').removeClass('off');
		$('.pgoods_time1').addClass('off');
		var sid="#clockbox";
		var timer = setInterval(function(){
			etime--;
			countDown(etime,sid);
		},1000);
		countDown(etime,sid);		
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
		     $('.cmath_c').unbind('click') ;
		if( $('.cmath_ca') !=undefined && $('.cmath_ca') !='undefined')
		     $('.cmath_ca').unbind('click') ;
		if( $('.clist_foot img') !=undefined && $('.clist_foot img') !='undefined')
			{
			   $('.clist_foot img').unbind('click') ;  
			}
		 
	}	
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
		$(day_elem).text(day+'天');//计算天
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
		$(day_elem).text((day<10?'0'+day:day)+'天');//计算天
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
		$(day_elem).text(day+'天');//计算天
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$(day_elem).text('0天');//计算天
		$(hour_elem).text('00:');//计算小时
		$(minute_elem).text('00:');//计算分钟
		$(second_elem).text('00');//计算秒杀
		
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
	} else {
		//时间到达后执行操作
		$(minute_elem).text('00:');//计算分钟
		$(second_elem).text('00');//计算秒杀
		/*$('#buytime').hide();*/
		$('#nextbegin').show();
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
		$('#nextbegin').show();
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

