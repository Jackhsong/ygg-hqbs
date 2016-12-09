var g_oTimer=null;
var g_iSpeed=117;
var g_iPauseTime=3000;
var g_oTimerOut=null;
var timer1=null;
var os_type=null;
var ostype=check_os();
var ietype=check_ie();
$(function(){
	
			if(ostype == 'mac'){
				
				if(ietype == 'chrome'){
					$('.iclock').removeClass('iclock').addClass('iclockmac');
					$('.logo').removeClass('logo').addClass('logomac');
				}
				if(ietype == 'safari'){
					$('.iclock').removeClass('iclock').addClass('iclockmac1');
					$('.logo').removeClass('logo').addClass('logomac1');
				}
			}

	$('#phoneno').focus(function(){
		var phno=$('#phoneno').val();
		if(phno=='请填写您的手机号码'){
			$('#phoneno').css('color','#333333');
			$('#phoneno').val('');
		}
		
	});

	$('#phoneno').blur(function(){
		var phno=$('#phoneno').val();
		if(phno==''){
			$('#phoneno').css('color','#c8c8c8');
			$('#phoneno').val('请填写您的手机号码');
		}
		
	});
	
	$('.adwrap_a1').click(function(){		
		$('.adwrap_a a').removeClass('adcolor');
		$('.adwrap_a1').addClass('adcolor');
		
		$(".ad li").eq(2).fadeIn("slow");
		$(".ad li").eq(0).fadeOut("slow");
		$(".ad li").eq(1).fadeOut("slow");			
		startEaseIno(2);
		
		
	});
	$('.adwrap_a2').click(function(){		
		$('.adwrap_a a').removeClass('adcolor');
		$('.adwrap_a2').addClass('adcolor');

		$(".ad li").eq(1).fadeIn("slow");
		$(".ad li").eq(0).fadeOut("slow");
		$(".ad li").eq(2).fadeOut("slow");	
		startEaseIno(1);		
	});
	$('.adwrap_a3').click(function(){
		
		$('.adwrap_a a').removeClass('adcolor');
		$('.adwrap_a3').addClass('adcolor');
	
		$(".ad li").eq(0).fadeIn("slow");
		$(".ad li").eq(1).fadeOut("slow");
		$(".ad li").eq(2).fadeOut("slow");	
		
		startEaseIno(0);
	});	

	    var n = 2;
		startEaseIno(n);

});

function startEaseIno(n){
	if(timer1){
		clearInterval(timer1);
	}
	timer1=setInterval(function(){ 
		easeIno(n); 
		n--;
		if(n < 0){
			n = 2;
		}
	},3000);

}

function easeIno(n){

	if(n==2){
		$(".ad li").eq(2).fadeIn("slow");
		$(".ad li").eq(0).fadeOut("slow");
		$(".ad li").eq(1).fadeOut("slow");

		$(".adwrap_a a").removeClass("adcolor");
		$(".adwrap_a a").eq(0).addClass("adcolor");
	}
	if(n==1){
		$(".ad li").eq(1).fadeIn("slow");
		$(".ad li").eq(0).fadeOut("slow");
		$(".ad li").eq(2).fadeOut("slow");

		$(".adwrap_a a").removeClass("adcolor");
			$(".adwrap_a a").eq(1).addClass("adcolor");
	}
	if(n==0){
		$(".ad li").eq(0).fadeIn("slow");
		$(".ad li").eq(1).fadeOut("slow");
		$(".ad li").eq(2).fadeOut("slow");

		$(".adwrap_a a").removeClass("adcolor");
			$(".adwrap_a a").eq(2).addClass("adcolor");
	}

	
}

function stopMove()
{
	clearInterval(g_oTimer);
	g_oTimer=null;
}

function startMove()
{	
	if(g_oTimer)
	{
		clearInterval(g_oTimer);
	}
	if(g_oTimerOut){
		clearTimeout(g_oTimerOut);
	}
	g_oTimer=setInterval(doMove, 30);
}

function doMove()
{	
	var oUl=$('.ad');
	var aLi=$('.ad li')
	
	var l=oUl.offset().left;
	
	
		l-=g_iSpeed;
		if(l<=-(oUl.width())/2)
		{
			l+=(oUl.width())/2;
		}
	
	
	
		if(Math.abs(l-Math.round(l/aLi.width())*aLi.width())<Math.ceil(g_iSpeed/2))
		{
			var doWidth=parseInt($(document).width());
			var nowWidth=Math.abs($('.ad').offset().left);

			if(nowWidth < doWidth ){
				$('.adwrap_a a').removeClass('adcolor');
				$('.adwrap_a2').addClass('adcolor');
			}else if(nowWidth > doWidth && nowWidth < doWidth*2){
				$('.adwrap_a a').removeClass('adcolor');
				$('.adwrap_a3').addClass('adcolor');
			}else if(nowWidth > doWidth*2 && nowWidth < doWidth*3){
				$('.adwrap_a a').removeClass('adcolor');
				$('.adwrap_a1').addClass('adcolor');
			}else{

			}

			stopMove();
			g_oTimerOut=setTimeout
			(
				function ()
				{
					startMove();
				}, g_iPauseTime
			);
			
			l=Math.round(l/aLi.width())*aLi.width();
		}
	
	
	oUl.css('left',l);
}
function check_ie() {
			chrome = (navigator.userAgent.indexOf("Chrome",0) != -1)?1:0;
			if (chrome){ ie_type = "chrome"; }else{ ie_type="safari";}
			
			
			return ie_type; 
		}

function check_os() {
			windows = (navigator.userAgent.indexOf("Windows",0) != -1)?1:0; 
			mac = (navigator.userAgent.indexOf("Mac",0) != -1)?1:0; 		
			
			if (windows) os_type = "MS Windows"; 
			else if (mac) os_type = "mac"; 
							
			return os_type; 
		}



/* brand count down */
function iClockCdown(time,id){
	var day_elem = $(id).find('.day');
	var hour_elem = $(id).find('.hour');
	var minute_elem = $(id).find('.minute');
	var second_elem = $(id).find('.second');
	var end_time = new Date(time).getTime(),
	sys_second = (end_time-new Date().getTime())/1000;	
	
	if (sys_second > 1) {
		sys_second -= 1;
		var day = Math.floor((sys_second / 3600) / 24);
		var hour = Math.floor((sys_second / 3600) % 24);
		var minute = Math.floor((sys_second / 60) % 60);
		var second = Math.floor(sys_second % 60);
		$(day_elem).text(day<10?'0'+day:day);//计算天
		$(hour_elem).text(hour<10?'0'+hour:hour);//计算小时
		$(minute_elem).text(minute<10?'0'+minute:minute);//计算分钟
		$(second_elem).text(second<10?'0'+second:second);//计算秒杀
	} else {
		//时间到达后执行操作
		$(day_elem).text('00');//计算天
		$(hour_elem).text('00');//计算小时
		$(minute_elem).text('00');//计算分钟
		$(second_elem).text('00');//计算秒杀
		
	}	
}

