function sendsms( url,data){
	var flag = true ;
		   $.ajax({
						  type: 'POST',
						  async: false,  
						  url:  url , /// "${rc.contextPath}/user/sendsms",
						  data: data , ///  "accountid="+$("#mobile").val()+"&type=1" , /// {'accountid':$("#mobile").val(),'type':'1'},// "accountid="+$("#mobile").val()+"&type=1",
						  dataType: "json" , // 指定后对json格式有严格要求
						  success: function(msg){
						    // alert(JSON.stringify(msg) ) ;
						    //alert(typeof(msg) ) ;
						    // alert(typeof(msg) == 'object' ) ;
							var errorMsg = "";
						    if(typeof(msg) == 'object'){
						        var status =msg.status ;
						        
						        if(status ==1)
						        {
						        	flag = true ;
						        	errorMsg ="发送成功";
						        }
						        else{
						        var errorCode = msg.errorCode ;
						           if(errorCode == 1)
						        	   errorMsg = "请输入正确的手机号码";
						              //alert("手机号不合法");
						           else if(errorCode == 2)
						        	   errorMsg = "手机号已注册";
						              //alert("重复手机号");
						           else if(errorCode == 3) 
						        	   errorMsg = "手机号未注册，请检查输入是否正确";
						              // alert("手机号不存在");
						           else if(errorCode == 4) 
						        	   errorMsg = "请等待一分钟重试";
						               //alert("请求过于频繁，请稍后再试"); 
						           else if(errorCode == 5)
						        	   errorMsg = "请输入正确的图片验证码";
						           else if(errorCode == 6)
						        	   errorMsg = "发送短息次数超过限制，请稍后重试";
						           $("#verifyCodeImage").attr('src',$("#contextPath").val()+"/verify/getCode?rnd=" + Math.random());
						           flag = false ;
						        }
						    }
						    if(errorMsg!="")
						    	showTipMsg(errorMsg) ;
						  },
						  error:function(err){
						    alert("发送失败!") ;
						  }
						});
    return flag ;
} ;
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
(function($){
	
	 $.jsonObjToStr=function(jsonObj){
			return  JSON.stringify(jsonObj) ;
		 }
	 
	 $.jsonStrToObj=function(jsonStr){
			  var dataObj = eval("("+jsonStr+")");  ;
			  return dataObj;
		 }
	 
	 $.bastPath=function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			return basePath ;
		};
		
	/**
	 * 取contextPath
	 */
	$.contextPath=function(){
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		return contextPath ;
	};	 

	
	  /* 
	   * 对于单个ajax请求的调用
	   * 传 url ,data,success方法三个即可
	   *  $.ajaxquery({"url":requesturl,"data":requestdata,"success":function(msgjsonobj){
       * }});   // 单个的ajax的查询,msgjsonobj已经转成json对象
	  */
	 $.ajaxquery=function(options){
		 var defaultOptions = {
		    		type:"POST",
		    		url:null,
		    		dataType:"json",
		    		data:null,
		    		isLoading:false,
		    		success:null 
		    	};
		    	defaultOptions = $.extend(defaultOptions,options||{});
		    	$.ajax({
		    		type:defaultOptions.type,
        			url:defaultOptions.url,
        			dataType:defaultOptions.dataType,
        			data:defaultOptions.data,
        			success:function(msg){
	      				   // var dataObj = eval("("+dstr+")");  // json字符串转成 json对象,不正规的JSON串的转法
	      				   // JSON.stringify(msgjsonobj) ;  // 传成json字符串 
	        				defaultOptions.success(msg) ;
        			},
        			error:function(err) {
        				// alert(err);
        			} 
		    		
		    	});
	 };
	 
	 
	 /**
      * 重写form serialize 方法对于checkbox 可识别多个值
      * $('#formId').serializeJson(); Form表单的序列化,对多个checkbox支持   
      */
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;	
			}
		});
		return serializeObj;
	};
	 
	$.fn.serializeJson2=function(){
		var params = "" ;
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
					params +=this.name+"="+this.value+"&";
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
					params +=this.name+"="+this.value+"&";
				}
			}else{
				serializeObj[this.name]=this.value;	
				params +=this.name+"="+this.value+"&";
			}
		});
		return params;
	}; 
	
})(jQuery);

function  resetpwd(){
	var mobile=$('#mobile').val();
	var pwd=$('#password1').val();
	var vfy=$('#vfy').val();
	var errorMsg="";
	if( mobile == '' || mobile == '请输入11位手机号'){
		//$('.loerror').text('* 请输入手机号');
		//$('.loerror').show();
		errorMsg = "* 请输入手机号";
		showTipMsg(errorMsg) ;
	}else if( pwd == '' || pwd == '6-16位字母和数字的新密码'){
		//$('.loerror').text('* 请输入新密码');
		//$('.loerror').show();
		errorMsg = "* 请输入新密码";
		showTipMsg(errorMsg) ;
	}else if( vfy == '' || vfy == '请输入短信验证码'){
		//$('.loerror').text('* 请输入验证码');
		//$('.loerror').show();
		errorMsg = "* 请输入验证码";
		showTipMsg(errorMsg) ;
	}else{
		var reg=/(^1[3-9]\d{9}$)/;
		var reg1=/^[a-zA-z\d]{6,16}$/;
		var reg2=/^\d{4}$/;
		if(!reg.test(mobile)){
			//$('.loerror').text('* 手机号格式不正确');
			//$('.loerror').show();
			errorMsg = "请输入正确的手机号码";
			showTipMsg(errorMsg) ;
			return false;
		}
		if(!reg1.test(pwd)){
			//$('.loerror').text('* 密码格式不正确，请输入6-16位字母和数字的密码');
			//$('.loerror').show();
			errorMsg = "* 密码格式不正确，请输入6-16位字母和数字的密码";
			showTipMsg(errorMsg) ;
			return false;
		}
		if(!reg2.test(vfy)){
			///$('.loerror').text('* 验证码格式不正确');
			//$('.loerror').show();
			errorMsg = "* 验证码格式不正确";
			showTipMsg(errorMsg) ;
			return false;
		}
		//表单提交submit
		/*console.log('1');*/
		return true ;
	}
	return false ;
};
function  register(){
	var mobile=$('#mobile').val();
	var pwd=$('#password').val();
	var vfy=$('#vfy').val();
	if( mobile == '' || mobile == '请输入11位手机号'){
		//$('.loerror').text('* 请输入手机号');
		//$('.loerror').show();
		errorMsg = "* 请输入手机号";
		showTipMsg(errorMsg) ;
	}else if( pwd == '' || pwd == '6-16位字母和数字的密码'){
		//$('.loerror').text('* 请输入密码');
		//$('.loerror').show();
		errorMsg = "* 请输入密码";
		showTipMsg(errorMsg) ;
	}else if( vfy == '' || vfy == '请输入短信验证码'){
		//$('.loerror').text('* 请输入验证码');
		//$('.loerror').show();
		errorMsg = "* 请输入验证码";
		showTipMsg(errorMsg) ;
	}else{
		var reg=/(^1[3-9]\d{9}$)/;
		var reg1=/^[a-zA-z\d]{6,16}$/;
		var reg2=/^\d{4}$/;
		if(!reg.test(mobile)){
			//$('.loerror').text('* 手机号格式不正确');
			//$('.loerror').show();
			errorMsg = "请输入正确的手机号码";
			showTipMsg(errorMsg) ;
			return false;
		}
		if(!reg1.test(pwd)){
			//$('.loerror').text('* 密码格式不正确，请输入6-16位字母和数字的密码');
			//$('.loerror').show();
			errorMsg = "* 密码格式不正确，请输入6-16位字母和数字的密码";
			showTipMsg(errorMsg) ;
			return false;
		}
		if(!reg2.test(vfy)){
			//$('.loerror').text('* 验证码格式不正确');
			//$('.loerror').show();
			errorMsg = "* 验证码格式不正确";
			showTipMsg(errorMsg) ;
			return false;
		}
		//表单提交submit
		/*console.log('1');*/
		return true ;
	}
	return false ;
};

