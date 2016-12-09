$(function(){
	browserRedirect();
	$('.cmath_c').click(add);
	$('.cmath_ca').click(substract);
	$('.clist_foot img').click(cartdel);
	$('.payta').click(function(){
		$(this).find('.cpayflag').attr('src','images/select.png');
		$('.paytb').find('.cpayflag').attr('src','images/unselect.png');
	})
	$('.paytb').click(function(){
		$(this).find('.cpayflag').attr('src','images/select.png');
		$('.payta').find('.cpayflag').attr('src','images/unselect.png');
	})
})
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
    } 
} 
           
/* count down */
function countDown(time,id){
	var day_elem = $(id).find('.day');
	var hour_elem = $(id).find('.hour');
	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = new Date(time).getTime(),//月份是实际月份-1
	sys_second = (end_time-new Date().getTime())/1000;	
	if (sys_second > 1) {
		sys_second -= 1;
		var day = Math.floor((sys_second / 3600) / 24);
		var hour = Math.floor((sys_second / 3600) % 24);
		var minute = Math.floor((sys_second / 60) % 60);
		var second = Math.floor(sys_second % 60);
		$(day_elem).text((day<10?'0'+day:day)+'天');//计算天
		$(hour_elem).text((hour<10?'0'+hour:hour)+':');//计算小时
		$(minute_elem).text((minute<10?'0'+minute:minute)+':');//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		//$(id).hide();
		//clearInterval(timer);
	}	
}

/* cart math */
function add(){
	var tot=$(this).parent().find('#total').text();
	var num=++tot;
	$(this).parent().find('#total').text(num);
}
function substract(){
	var tot=$(this).parent().find('#total').text();
	var num=--tot;
	if(num<1){
		
	}else{
		$(this).parent().find('#total').text(num);
	}
	
}

function cartdel(){
	var cend=confirm('确定要删除该商品吗？');
	if(cend){
		
	}else{

	}
}

