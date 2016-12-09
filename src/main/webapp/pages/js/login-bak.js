var sendtimer=null;
var lflag=true;
$(function(){
	var mval='';
	$('#mobile').focus(function(){
		mval=$(this).val();
		if(mval == '请输入11位手机号'){
			$(this).val('');
		}
	});
	$('#mobile').blur(function(){
		mval=$(this).val();
		if(!mval){
			$(this).val('请输入11位手机号')
		}
	});

	$('#password').focus(function(){
		mval=$(this).val();
		var tmp=$('#showpwd').html();
		if(mval == '6-16位字母和数字的密码'){
			$(this).val('');
			$(this).attr('type','password');
			$('#showpwd').html('显示密码');
		}
	});
	$('#password').blur(function(){
		mval=$(this).val();
		if(!mval){
			$(this).attr('type','text');
			$(this).val('6-16位字母和数字的密码')
		}
	});

	$('#vfy').focus(function(){
		mval=$(this).val();
		if(mval == '请输入短信验证码'){
			$(this).val('');
		}
	});
	$('#vfy').blur(function(){
		mval=$(this).val();
		if(!mval){			
			$(this).val('请输入短信验证码')
		}
	});
	$('#showpwd').click(function(){
		var b=$(this).html();
		var c=$('#password').val();
		if( c == '6-16位字母和数字的密码' || c==''){

		}else{
			if(b == '显示密码'){
				$(this).html('隐藏密码');
				$('#password').attr('type','text');
				$('#password')[0].focus();
			}else{
				$(this).html('显示密码');
				$('#password').attr('type','password');
				$('#password')[0].focus();
			}
		}
		
		
	});

$('#password1').focus(function(){
		mval=$(this).val();
		var tmp=$('#showpwd1').html();
		if(mval == '6-16位字母和数字的新密码'){
			$(this).val('');
			$(this).attr('type','password');
			$('#showpwd1').html('显示密码');
		}
	});
	$('#password1').blur(function(){
		mval=$(this).val();
		if(!mval){
			$(this).attr('type','text');
			$(this).val('6-16位字母和数字的新密码')
		}
	});
	$('#showpwd1').click(function(){
		var b=$(this).html();
		var c=$('#password1').val();
		if( c == '6-16位字母和数字的新密码' || c==''){

		}else{
			if(b == '显示密码'){
				$(this).html('隐藏密码');
				$('#password1').attr('type','text');
				$('#password1')[0].focus();
			}else{
				$(this).html('显示密码');
				$('#password1').attr('type','password');
				$('#password1')[0].focus();
			}
		}
		
		
	});

	$('#login').click(function(){
		var mobile=$('#mobile').val();
		var pwd=$('#password').val();
		if( mobile == '' || mobile == '请输入11位手机号'){
			$('.loerror').text('* 请输入手机号');
			$('.loerror').show();
		}else if( pwd == '' || pwd == '6-16位字母和数字的密码'){
			$('.loerror').text('* 请输入密码');
			$('.loerror').show();
		}else{
			var reg=/(^1[3-9]\d{9}$)/;
			if(!reg.test(mobile)){
				$('.loerror').text('* 手机号格式不正确');
				$('.loerror').show();
				return false;
			}
			//表单提交submit
			/*console.log('1');*/
		}
	});
	
	
	$('#nowreg').click(function(){
		var mobile=$('#mobile').val();
		var pwd=$('#password').val();
		var vfy=$('#vfy').val();
		if( mobile == '' || mobile == '请输入11位手机号'){
			$('.loerror').text('* 请输入手机号');
			$('.loerror').show();
		}else if( pwd == '' || pwd == '6-16位字母和数字的密码'){
			$('.loerror').text('* 请输入密码');
			$('.loerror').show();
		}else if( vfy == '' || vfy == '请输入短信验证码'){
			$('.loerror').text('* 请输入验证码');
			$('.loerror').show();
		}else{
			var reg=/(^1[3-9]\d{9}$)/;
			var reg1=/^[a-zA-z\d]{6,16}$/;
			var reg2=/^\d{6}$/;
			if(!reg.test(mobile)){
				$('.loerror').text('* 手机号格式不正确');
				$('.loerror').show();
				return false;
			}
			if(!reg1.test(pwd)){
				$('.loerror').text('* 密码格式不正确，请输入6-16位字母和数字的密码');
				$('.loerror').show();
				return false;
			}
			if(!reg2.test(vfy)){
				$('.loerror').text('* 验证码格式不正确');
				$('.loerror').show();
				return false;
			}
			//表单提交submit
			/*console.log('1');*/
		}
	});

	$('#resetreg').click(function(){
		var mobile=$('#mobile').val();
		var pwd=$('#password1').val();
		var vfy=$('#vfy').val();
		if( mobile == '' || mobile == '请输入11位手机号'){
			$('.loerror').text('* 请输入手机号');
			$('.loerror').show();
		}else if( pwd == '' || pwd == '6-16位字母和数字的新密码'){
			$('.loerror').text('* 请输入新密码');
			$('.loerror').show();
		}else if( vfy == '' || vfy == '请输入短信验证码'){
			$('.loerror').text('* 请输入验证码');
			$('.loerror').show();
		}else{
			var reg=/(^1[3-9]\d{9}$)/;
			var reg1=/^[a-zA-z\d]{6,16}$/;
			var reg2=/^\d{6}$/;
			if(!reg.test(mobile)){
				$('.loerror').text('* 手机号格式不正确');
				$('.loerror').show();
				return false;
			}
			if(!reg1.test(pwd)){
				$('.loerror').text('* 密码格式不正确，请输入6-16位字母和数字的密码');
				$('.loerror').show();
				return false;
			}
			if(!reg2.test(vfy)){
				$('.loerror').text('* 验证码格式不正确');
				$('.loerror').show();
				return false;
			}
			//表单提交submit
			/*console.log('1');*/
		}
	});

	
	/* send count down 验证码的发送 */
	function sendCdown(time,id){		
		var second_elem = $(id).find('.second');
		var end_time = time;	
		if (end_time > 0) {			
			var second = Math.floor(end_time % 60);			
			$(second_elem).text(second<10?'0'+second:second);//计算秒数
		} else {
			//时间到达后执行操作			
			$('.retry').text('再次发送');
			if(sendtimer){				
				clearInterval(sendtimer);
			}
			lflag=true;
					
			/* 在此再次发送短信请求 */	
			//alert("#contextPath").val()) ;
           // alert($("#mobile").val()) ;
			
		}
	}

	$('.retry').click(function(){
		if($('.retry').text() == '再次发送'){
			var sendmsgtype = $("#sendmsgtype").val();
			sendsms($("#contextPath").val()+"/user/sendsms","accountid="+$("#mobile").val()+"&type="+sendmsgtype);
			$('.retry').html('<span class="second">60</span>s后重发');
			var etime=60;
			var sid=".retry";
			sendtimer = setInterval(function(){
				etime--;
				sendCdown(etime,sid);
			},1000);
		}
	});
    /*获取验证码*/
	$('.verify_a').click(function(){
		$(this).hide();
		if($('.retry').text() == '再次发送'){
			var sendmsgtype = $("#sendmsgtype").val();
			sendsms($("#contextPath").val()+"/user/sendsms","accountid="+$("#mobile").val()+"&type="+sendmsgtype);
			$('.retry').html('<span class="second">60</span>s后重发');
			if(lflag){
				if(sendtimer){
					clearInterval(sendtimer);
				}
				var etime=60;
				var sid=".retry";
				sendtimer = setInterval(function(){
					etime--;
					sendCdown(etime,sid);
				},1000);
				$('.retry').removeClass('off');
				lflag=false;
			}
		}
		
	});
	
	
}) ;

