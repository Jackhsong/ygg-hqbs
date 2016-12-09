$(function(){	
	var contextPath = $("#contextPath").val();
	$('#province').change(function(){
		//showAreaTipLoadingMsg();
		
		var province=$('#province').val();	
		if(province == 0){
			$('#city').attr('disabled','disabled');
			$('#city').html('<option value="0">-- 请选择 --</option>');
			$('#region').attr('disabled','disabled');
			$('#region').html('<option value="0">-- 请选择 --</option>');
			$('.pronotice').hide();
		}else{
			$('#city').removeAttr('disabled');
			
			$('#city').attr('disabled','disabled');
			$('#city').html('<option value="0">-- 请选择 --</option>');
			$('#region').attr('disabled','disabled');
			$('#region').html('<option value="0">-- 请选择 --</option>');
			
			/* 遍历城市 */
			$.ajax({
				url:contextPath+'/ads/getcitiesbypid',
				type:'POST',
				data:{'pid':province},
				dataType:'json',
				success:function(data){
					$('#city').removeAttr('disabled');
					$('#region').removeAttr('disabled');
					$('#region').attr('disabled','disabled');
					$('#region').html('<option value="0">-- 请选择 --</option>');
					//$('.pronotice').hide();
					var cities = data.cities ;
					if(cities != null && cities !=undefined && cities!='undefined'){
						var html='<option value="0">-- 请选择 --</option>';
						$('#city').empty();
						cities =JSON.parse(cities) ; /*转成json串*/
						$.each(cities,function(i,item){
							html+='<option value="'+item.cityId+'">'+item.name+'</option>';
						});
						$('#city').html(html);
					}
				},
				error:function(){
					/*console.log('error');*/
					//$('.pronotice').hide();
					$('#city').removeAttr('disabled');
					$('#region').removeAttr('disabled');
				}
			});
			/* 遍历城市 End */
		}		
	});

	$('#city').change(function(){
		//showAreaTipLoadingMsg();
		var city=$('#city').val();	
		if(city == 0){
			$('#region').attr('disabled','disabled');
			$('#region').html('<option value="0">-- 请选择 --</option>');
			$('.pronotice').hide();
		}else{
			$('#region').removeAttr('disabled');		
		
			$('#region').attr('disabled','disabled');
			$('#region').html('<option value="0">-- 请选择 --</option>');
			
			/* 遍历地区 */
			$.ajax({
				url:contextPath+'/ads/getdistrictsbycid',
				type:'POST',
				data:{'cid':city},
				dataType:'json',
				success:function(data){
					//$('.pronotice').hide();
					$('#region').removeAttr('disabled');
					var districts = data.districts ;
					if(districts != null && districts !=undefined && districts!='undefined'){
						var html='<option value="0">-- 请选择 --</option>';
						$('#region').empty();
						districts =JSON.parse(districts) ; /*转成json串*/
						$.each(districts,function(i,item){
							html+='<option value="'+item.districtId+'">'+item.name+'</option>';
						});
						$('#region').html(html);
					}
					
				},
				error:function(){
					/*console.log('error');*/
					//$('.pronotice').hide();
					$('#city').removeAttr('disabled');
					$('#region').removeAttr('disabled');
				}
			});
			/* 遍历地区 End */
		}		
	});

	$('#conadd').click(function(){
		var conuser=$('#conuser').val();
		var phoneno=$('#phoneno').val();
		var province=$('#province').val();
		var city=$('#city').val();
		var region=$('#region').val();
		var detailadd=$('#detailadd').val();
		var userid=$('#userid').val();
		var errorMsg ="";
		if($('.insertadd li').hasClass('showstar')){
			if(userid == ''){			
				//$('.conerror').text('* 身份证不能为空');
				//$('.conerror').show();
				errorMsg="* 身份证不能为空" ;
				showTipMsg(errorMsg) ;
			}
		}		
		
		if(detailadd == ''){			
			//$('.conerror').text('* 详细地址不能为空');
			//$('.conerror').show();
			errorMsg="* 详细地址不能为空" ;
			showTipMsg(errorMsg) ;
		}else if(detailadd.length<5 || detailadd.length>60)
		{
			  errorMsg="* 详细地址必须在5至60个汉字之间";
			  showTipMsg(errorMsg) ;
	    }
		if(region == 0){			
			//$('.conerror').text('* 请选择地区');
			//$('.conerror').show();
			errorMsg="* 请选择地区" ;
			showTipMsg(errorMsg) ;
		}
		if(city == 0){			
			//$('.conerror').text('* 请选择城市');
			//$('.conerror').show();
			errorMsg="* 请选择城市" ;
			showTipMsg(errorMsg) ;
		}
		if(province == 0){			
			//$('.conerror').text('* 请选择省份');
			//$('.conerror').show();
			errorMsg="* 请选择省份" ;
			showTipMsg(errorMsg) ;
		}
		if(phoneno == ''){			
			//$('.conerror').text('* 手机号码不能为空');
			//$('.conerror').show();
			errorMsg="* 手机号码不能为空" ;
			showTipMsg(errorMsg) ;
		}
		if(conuser == ''){			
			//$('.conerror').text('* 收货人不能为空');
			//$('.conerror').show();
			errorMsg="* 收货人不能为空" ;
			showTipMsg(errorMsg) ;
		}
	});

	$('#consave').click(function(){
		var conuser=$('#conuser').val();
		var phoneno=$('#phoneno').val();
		var province=$('#province').val();
		var city=$('#city').val();
		var region=$('#region').val();
		var detailadd=$('#detailadd').val();
		var userid=$('#userid').val();		
		var errorMsg ="";
		if($('.insertadd li').hasClass('showstar')){
			if(userid == ''){			
				//$('.conerror').text('* 身份证不能为空');
				//$('.conerror').show();
				errorMsg="* 身份证不能为空" ;
				showTipMsg(errorMsg) ;
			}
		}
		if(detailadd == ''){			
			//$('.conerror').text('* 详细地址不能为空');
			//$('.conerror').show();
			errorMsg="* 详细地址不能为空" ;
			showTipMsg(errorMsg) ;
		}else if(detailadd.length<5 || detailadd.length>60)
		{
			  errorMsg="* 详细地址必须在5至60个汉字之间";
			  showTipMsg(errorMsg) ;
	    }	
		if(region == 0){			
			//$('.conerror').text('* 请选择地区');
			//$('.conerror').show();
			errorMsg="* 请选择地区" ;
			showTipMsg(errorMsg) ;
		}
		if(city == 0){			
			//$('.conerror').text('* 请选择城市');
			//$('.conerror').show();
			errorMsg="* 请选择城市" ;
			showTipMsg(errorMsg) ;
		}
		if(province == 0){			
			//$('.conerror').text('* 请选择省份');
			//$('.conerror').show();
			errorMsg="* 请选择省份" ;
			showTipMsg(errorMsg) ;
		}
		if(phoneno == ''){			
			//$('.conerror').text('* 手机号码不能为空');
			//$('.conerror').show();
			errorMsg="* 手机号码不能为空" ;
			showTipMsg(errorMsg) ;
		}
		if(conuser == ''){			
			//$('.conerror').text('* 收货人不能为空');
			//$('.conerror').show();
			errorMsg="* 收货人不能为空" ;
			showTipMsg(errorMsg) ;
		}
	});
 

})
function  showAreaTipLoadingMsg()
{
   var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.pronotice').css('top',xtop);
	$('.pronotice').show();
	
};	

function checkconsignadd()
	{
		var flag  = true ;
		var conuser=$('#conuser').val();
		var phoneno=$('#phoneno').val();
		var province=$('#province').val();
		var city=$('#city').val();
		var region=$('#region').val();
		var detailadd=$('#detailadd').val();
		var userid=$('#userid').val();		
		var errorMsg ="";
		if($('.insertadd li').hasClass('showstar')){
			if(userid == ''){			
				//$('.conerror').text('* 身份证不能为空');
				//$('.conerror').show();
				errorMsg="* 身份证不能为空" ;
				showTipMsg(errorMsg) ;
				flag  = false ;
			}
		}
		if(detailadd == ''){			
			//$('.conerror').text('* 详细地址不能为空');
			//$('.conerror').show();
			errorMsg="* 详细地址不能为空" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}else if(detailadd.length<5 || detailadd.length>60)
		{
			  errorMsg="* 详细地址必须在5至60个汉字之间";
			  showTipMsg(errorMsg) ;
			  flag  = false ;
	    }		
		if(region == 0){			
			//$('.conerror').text('* 请选择地区');
			//$('.conerror').show();
			errorMsg="* 请选择地区" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}
		if(city == 0){			
			//$('.conerror').text('* 请选择城市');
			//$('.conerror').show();
			errorMsg="* 请选择城市" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}
		if(province == 0){			
			//$('.conerror').text('* 请选择省份');
			//$('.conerror').show();
			errorMsg="* 请选择省份" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}
		if(phoneno == ''){			
			//$('.conerror').text('* 手机号码不能为空');
			//$('.conerror').show();
			errorMsg="* 手机号码不能为空" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}
		if(phoneno != '')
		{  
				var reg=/(^1[3-9]\d{9}$)/;
				if(!reg.test(phoneno)){
					//$('.conerror').text('* 手机号格式不正确');
					//$('.conerror').show();
					errorMsg="请输入正确的手机号码" ;
					showTipMsg(errorMsg) ;
					flag  = false ;
				}
		}
		if(conuser == ''){			
			//$('.conerror').text('* 收货人不能为空');
			//$('.conerror').show();
			errorMsg="* 收货人不能为空" ;
			showTipMsg(errorMsg) ;
			flag  = false ;
		}
		
		return flag ;
	} 
	