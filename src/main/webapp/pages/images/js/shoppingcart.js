var timer='';
(function($){
  
	$.setCartCountVal=function(cartCountVal){
		 /*alert('--------setCartCountVal--------------' + cartCountVal) ; */
		if(cartCountVal==0){
			$(".rSup").addClass("off") ;
			$(".buysub").addClass("off") ;
			$(".buytime").addClass("off") ;
			return ;
		}
		
		$(".rSup").removeClass("off") ;
		$(".buysub").removeClass("off") ;
		$(".buytime").removeClass("off") ;
		 if($("#showCartCount") !=null && $("#showCartCount")!= undefined && $("#showCartCount")!='undefined')
			 {
			    $("#showCartCount").html(cartCountVal);
			 }
     	 if($("#showCartCount1") !=null && $("#showCartCount1")!= undefined && $("#showCartCount1")!='undefined')
      	    $("#showCartCount1").html(cartCountVal);
     	 if($("#showCartCount2") !=null && $("#showCartCount2")!= undefined && $("#showCartCount2")!='undefined')
      	    $("#showCartCount2").html(cartCountVal);
     	 
     	 if($("#spcartcount") !=null && $("#spcartcount") != undefined && $("#spcartcount") != 'undefined')  /*在form1中设一个input值   购物车内的总数量　*/
     		$("#spcartcount").val(cartCountVal) ; 
     	 
	}
	
    $.getCartCount = function(url,data){
    	/*自动请求购物车中的总数量
    	 * 定义三个showCartCount因为一个页面上下可能都有购物车的img 
    	 * */
        $.ajax({
           type:'POST',
           url: url , /*'${yggcontextPath}/spcart/showcartcount', */
           data: data ,
           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
           success: function(msg){
             if(msg !=null && msg.cartCount!=null && msg.cartCount!= undefined && msg.cartCount!='undefined' ){
            	 
            	 if(msg.cartCount > 0)
            	 {
            		 if($('.indexcart') !=undefined )
              	        $('.indexcart').hide();
              	     if($('.brandcart') !=undefined )
              		    $('.brandcart').show();
              	     if($('.rSup') !=undefined)
              	    	 $('.rSup').show();
              	     
              	   $('.buytime').show();
            	 }
            	 

            	 $.setCartCountVal(msg.cartCount) ;
            	 /*if($("#showCartCount") !=null && $("#showCartCount")!='undefined')
            	    $("#showCartCount").html(msg.cartCount);
            	 if($("#showCartCount1") !=null && $("#showCartCount1")!='undefined')
             	    $("#showCartCount1").html(msg.cartCount);
            	 if($("#showCartCount2") !=null && $("#showCartCount2")!='undefined')
             	    $("#showCartCount2").html(msg.cartCount);
             	    */
            	// alert(4) ;
            	 if(timer){
            	//	 alert(54) ;
          		   clearInterval(timer);
          	      }
            	  //提供点击购买倒计时秒数				
				    var etime= msg.endSecond;
				    var sid="#buytime";
				    timer = setInterval(function(){
					   etime--;
					   proCdown(etime,sid);
				    },1000);
				    proCdown(etime,sid);
				    
				    
				    
             }
              
             if(msg !=null && msg.productcount!=null && msg.productcount !=undefined && msg.productcount !='undefined'){
            	 
            	 var  productcountControl = $("#form1 input[name=productcount]") ;  // 先看此input存不存在
            	 if(productcountControl!=null && productcountControl!='undefined'){
            		 productcountControl.val(msg.productcount) ; // 设一个值 hidden 加入购物车成功后要更新此值
            		 /*alert(productcountControl.val());*/
            	 }
             }
             
                 
            },
           error:function(err){
        	   $.setCartCountVal(0) ;
        	   /*if($("#showCartCount") !=null && $("#showCartCount")!='undefined')
                  $("#showCartCount").html(0);
        	   if($("#showCartCount1") !=null && $("#showCartCount1")!='undefined')
                   $("#showCartCount1").html(0);
        	   if($("#showCartCount2") !=null && $("#showCartCount2")!='undefined')
                   $("#showCartCount2").html(0);*/
           }
        });
    } ;


    
    
})(jQuery);

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
	
	 /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
    /* 弹出层 */
	// $('.pronotice').show();
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.pronotice').css('top',xtop);
	$('.pronotice').css('display','block');
	$('.pronotice').show();
	 
}


var editflag =false ;
function editshoppingcart(url,data){
	     if(true){ // if(!editflag){
		    $.ajax({
			           type:'POST',
			           url: url ,  
			           data: data ,
			           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
			           beforeSend:function(){
			        	   showTipLoadingMsg() ;
			        	   
			           },
			           success: function(msg){
			               /* console.log(msg); */
			               var errorMsg='';
			               if(msg !=null && msg.status!=null && msg.status!='undefined' && msg.status =='1'){ /*成功*/
			            	   var productcount = msg.productcount ;
			            	   $.setCartCountVal(productcount) ;
			                   //$.setCartCountVal(parseInt($("#spcartcount").val())+1) ;
			                   var endSecond  = msg.endSecond ;  /*显示一个倒计时*/
			                  
			                   /*单品的数量还要加1*/
			                   var  productcountControl = $("#form1 input[name=productcount]") ; 
			                   var newCountVal  = parseInt(productcountControl.val())+1 ;
			                   // productcountControl.val(newCountVal);
			                   // productcountControl.val(productcount);   /*在 producthtml.ftl中定义*/
			                   $.getCartCount(reqShowCartCountUrl,''); /*每次去服务端更新购物车的最新值，防止微信返回不执行ajax请求*/
			                   var msg = "已加入购物车!"; // "亲，购买成功,数量:"+newCountVal+"件!" 
			                   showTipMsg(msg) ;
			                   
			                   if(timer){
			            		   clearInterval(timer);
			            	   }
			                   //提供点击购买倒计时秒数				
							    var etime= endSecond;
							    var sid="#buytime";
							     timer = setInterval(function(){
								   etime--;
								   proCdown1(etime,sid);
							    },1000);
							     proCdown1(etime,sid);
			                   
			               }else if(msg !=null && msg.status!=null && msg.status!= undefined && msg.status =='0' ){
			                    var errorCode = msg.errorCode ;  /*一定要有*/
			                    var stockCount = msg.stockCount ; /*errorCode=4　时有*/
			                    var restrictionCount = msg.restrictionCount ;  /*errorCode=5　时有 限购数量　*/
			                    var showMsg = msg.showMsg ;  /*errorCode=6　时有 限购数量　*/
			                    if(errorCode!=undefined && errorCode!= 'undefined' )
			                    {
			                        if(errorCode == '0')
			                           errorMsg = '请刷新页面' ; /* '亲，服务器未知错误'; */
			                        else if(errorCode =='1')
			                           errorMsg = '购买该商品需要先登录哦~';
			                        else if(errorCode =='2')
			                            errorMsg = '商品已被删除';
			                        else if(errorCode =='3')
			                            errorMsg = '临时账号不存在';
			                        else if(errorCode=='4' && stockCount !=undefined && stockCount !='undefined') /*库存不足*/
			                            errorMsg = '慢了一步，商品库存不足了～';
			                        else if(errorCode=='5' && restrictionCount !=undefined && restrictionCount !='undefined') /*库存不足*/
			                            errorMsg = '数量有限，限购'+restrictionCount+'件哦';
			                        else if(errorCode=='6' && restrictionCount !=undefined && restrictionCount !='undefined') /*无购买格格福利商品资格*/
				                        errorMsg = showMsg;
			                        
		                            /* 给出一个浮层弹出框,显示出errorMsg,2秒消失!*/
		                            /* 弹出层 */
		                            showTipMsg(errorMsg) ;
			                    }
			               }
			              // editflag = false ;
			               $('.pronotice').hide();
			            },
			           error:function(err){
			        	  alert(err) ;   
			        	  $('.pronotice').hide();
			           }
			       });
		   // editflag = true ;
		    return true ;
	    }else{
	        alert("正在执行,请稍候!");
	    }
}
    