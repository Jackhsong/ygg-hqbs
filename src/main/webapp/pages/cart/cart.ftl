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
		<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
		<link rel="apple-touch-icon" href="custom_icon.png">
		<#include "../common/otherShare.ftl">
		<#include "../common/cartHeader.ftl">
	</head>
	<body>

		<div class="pagea">
            <#if productList?exists && (productList?size<= 0) >
                 <!-- 空购物车显示-->
			     <div class="emptyadd">
				    <img src="${rc.contextPath}/pages/images/carte.png">
				    <p>你的购物车空空如也</p>
				    <span>快去挑点宝贝吧</span>
					<a href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo">去逛逛</a>
			     </div>
			      <!-- 空购物车显示 End-->
            <#else>
			<div class="cnoticefail off"><img src="${rc.contextPath}/pages/images/trumpet.png"><em>又逛超时了！立即结算，抢回还有货的商品吧。</em></div>
			<!-- <div class="amount">商品数量:<em>2</em><span>总计:<span>2264.00</span></span></div> -->
			<div class="cmain">
			      <div class="protips"></div>
			       <!-- 商品列表 -->
                   <#if productList?exists && (productList?size>0) >
                     <#list   productList as pl>
                    	<ul class="clist clear" id="product${pl_index}">
                    		  <a href="${rc.contextPath}/product/single/${(pl.productId)!"0"}">
							  <!-- a href="/item-${(pl.productId)!"0"}"-->
                    	      <li class="clist_img fl"><img src="${(pl.image)!""}" /><!-- <div class="chance"><img src="${rc.contextPath}/pages/images/goods/chance.png"></div> 还有机会 -->
					    	    <#if (pl.status)?exists && (pl.status)=='2'>
					            <div class="end "><img src="${rc.contextPath}/pages/images/goods/end.png" /></div><!-- 已抢完 -->
					           </#if>
					          </li>
					        </a>
					    	<li class="clist_tit">${(pl.shortName)!""}</li>
					    	<li class="clist_foot">￥<div class="sinprice">${(pl.salesPrice)!"0.00"}</div>
                            <img src="${rc.contextPath}/pages/images/del.png" class="delcart" /><div class="cmath fr"><span class="cmath_ca">-</span><span id="currentproduct">${(pl.count)!"0"}</span><span class="cmath_c">+</span><input type="hidden" id="productId" name="productId" value="${(pl.productId)!"0"}" /><input type="hidden" id="productstatus" name="productstatus" value="${(pl.status)!"0"}" /><input type="hidden" id="indexId" name="indexId" value="${pl_index}" /></div></li>
				   	        <div class="loading off"><img src="${rc.contextPath}/pages/images/loading.gif"></div>
				   	    </ul>
                        <form id="form${pl_index}" name="form${pl_index}" action="" method="post">
							<input type="hidden" id="productId" name="productId" value="${(pl.productId)!"0"}" />
                        	<input type="hidden" id="stockCount" name="stockCount" value="${(pl.stockCount)!"0"}" />
							<input type="hidden" id="restriction" name="restriction" value="${(pl.restriction)!"0"}" />
                        	<input type="hidden" id="status" name="status" value="${(pl.status)!"0"}" />
                        </form>
                    </#list>
                 </#if>
			     <!-- 商品列表 End -->
			</div>
			<div class="ctips">
				<!-- <p>*全场包邮。</p> -->
				<#if tipsList?? && (tipsList?size > 0) >
			     <#list tipsList as tl>
			        <p>*全场包邮</p>
				 </#list>
			   </#if>
			</div>
            <#if productList?exists && (productList?size>0) >
			    <p class="carttotal">总计￥<span>${(totalPrice)!""}</span><a href="javascript:void(0)" onclick="submitsc()">去结算</a></p>
            </#if>

		</div>
        </#if>
        <!--底部导航 Start-->
			<input type="hidden" id="navFooterToPage" value="1"/>
			<#include "../common/navFooter.ftl">
			<!--底部导航 End-->
        <!-- <script src="${rc.contextPath}/pages/js/h5self-adaption.js" type="text/javascript"></script> -->
		<script>
			$(function(){

			   $('#iscarttimeout').val(0);
			   $('.cmath_c').bind("click",cartadd) ;   /*如果过期后在gegejia.js中会cartCdown　取消绑定的事件*/
			   $('.cmath_ca').bind("click",cartsubstract);
			   $('.clist_foot img').bind("click",deletecart);
				var etime="${(endSecond)!"0"}";
				var sid="#carttime";
				var timer = setInterval(function(){
					etime--;
					cartCdown(etime,sid);
				},980);
				cartCdown(etime,sid);

/*商品加减方法*/
function cartadd(){

	/*超时和已抢完 + -　都不能操作*/
    var status=$(this).parent().find('#productstatus') ;
    var productstatus = parseInt(status.val()) ;   /* 2 已抢完*/
    if(productstatus ==undefined || productstatus == 'undefined' || productstatus ==2)
     return false;
    var obj =  $(this) ;
    var loading = obj.parent().parent().parent().find('.loading') ;
	var tot=$(this).parent().find('#currentproduct').text();
	var productId=$(this).parent().find('#productId').val();
	var productcount = parseInt(tot)+1 ;
	var data = "productid="+productId+"&productcount="+productcount ;
	var flag = editshoppingcart("${rc.contextPath}/spcart/editsc",data,loading,'1');
	$.ajax({
        type:'POST',
        url: '${rc.contextPath}/spcart/showcartcount',
        dataType: 'json' ,
        success: function(msg){

           		 $(".carNum").text(parseInt(msg.cartCount));
        }
     });
	if(flag)
	{
	        var tot=$(this).parent().find('#currentproduct').text();
			var num=++tot;
			//var prosum=parseInt($('.amount em').text());
			var totalprice=parseFloat($('.carttotal span').text());
			var sinprice=parseFloat($(this).parent().parent().find('.sinprice').text());

			totalprice+=sinprice;
			//prosum++;
			//$('.amount em').text(prosum);
			$('.carttotal span').text(totalprice.toFixed(2)); // +'.00'
			$(this).parent().find('#currentproduct').text(num);
	}



} ;

/*商品的减法*/
function cartsubstract(){

	/*超时和已抢完 + -　都不能操作*/
    var tot=$(this).parent().find('#currentproduct').text();
    var status=$(this).parent().find('#productstatus') ;
     var obj =  $(this) ;
    var loading = obj.parent().parent().parent().find('.loading') ;
    var productstatus = parseInt(status.val()) ;   /* 2 已抢完*/
    if(productstatus ==undefined || productstatus == 'undefined' || productstatus ==2)
      return false ;
    if(parseInt(tot) <=1)
      return false ;

	var productId=$(this).parent().find('#productId').val();
	var productcount = parseInt(tot)-1 ;
	var data = "productid="+productId+"&productcount="+productcount ;
	var flag = editshoppingcart("${rc.contextPath}/spcart/editsc",data,loading,'2');
	$.ajax({
        type:'POST',
        url: '${rc.contextPath}/spcart/showcartcount',
        dataType: 'json' ,
        success: function(msg){
        	 if(!flag){
           		 return false;
           	 }else{
           		 $(".carNum").text(parseInt(msg.cartCount));
           	 }

        }
     });
	if(flag)
	{
	        var tot=$(this).parent().find('#currentproduct').text();
			var productId=$(this).parent().find('#productId').val();

			var num=--tot;
			//var prosum=parseInt($('.amount em').text());
			var totalprice=parseFloat($('.carttotal span').text());
			var sinprice=parseFloat($(this).parent().parent().find('.sinprice').text());

			if(num<0){

			}else{
				totalprice-=sinprice;
				//prosum--;
				//$('.amount em').text(prosum);
				$('.carttotal span').text(totalprice.toFixed(2)); // +'.00'
				$(this).parent().find('#currentproduct').text(num);
			}
	}

} ;


function showMsg(msg)
{
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

var editshoppingcart=function(url,data,loading,addorsub){
if(loading!=undefined && loading!='undefined')
   loading.show();
     var flag = true ;
        $.ajax({
			           type:'POST',
			           url: url ,
			           data: data ,
			           async:false ,
			           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
			           beforeSend:function(){
			               if(loading!=undefined && loading!='undefined')
			                  loading.show();
			           },
			           success: function(msg){
			               /* console.log(msg); */
			               var errorMsg='';
			               if(msg !=null && msg.status!=null && msg.status!='undefined' && msg.status =='1'){ /*成功*/
			                if(loading!=undefined && loading!='undefined')
			                     loading.hide();
			                 if(addorsub =='1')
			                    showMsg('已增加');
			                 else if(addorsub =='2')
			                    showMsg('已减少');
			                  flag =true ;
			               }else if(msg !=null && msg.status!=null && msg.status!= undefined && msg.status =='0' ){
			                    flag =false ;
			                    if(loading!=undefined && loading!='undefined')
			                        loading.hide();
			                    var errorCode = msg.errorCode ;  /*一定要有*/
			                    var stockCount = msg.stockCount ; /*errorCode=4　时有*/
			                    var restrictionCount = msg.restrictionCount ;  /*errorCode=5　时有 限购数量　*/

			                    if(errorCode!=undefined && errorCode!= 'undefined' )
			                    {
			                        if(errorCode == '0')
			                           errorMsg ='请刷新页面' ; /* '亲，服务器未知错误'; */
			                        else if(errorCode =='1')
			                           errorMsg = '请先登录';
			                        else if(errorCode =='2')
			                            errorMsg = '商品不存在或被删除';
			                        else if(errorCode =='3')
			                            errorMsg = '临时账号不存在';
			                        else if(errorCode=='4' && stockCount !=undefined && stockCount !='undefined') /*库存不足*/
			                            if(addorsub =='1'){
			                            	errorMsg = '商品库存不足';
			                            }else if(addorsub =='2'){
			                            	errorMsg = '商品库存不足,已移出购物车';
			                            }
									else if(errorCode=='5' && restrictionCount !=undefined && restrictionCount !='undefined') /*库存不足*/
			                            errorMsg = '数量有限，限购'+restrictionCount+'件哦';
									else if(errorCode=='6' && msg.showMsg != undefined)
										errorMsg = msg.showMsg;
									/* 给出一个浮层弹出框,显示出errorMsg,1秒消失!*/
									if(errorMsg !='')
				                    	showErrorMsg(errorMsg) ;

			                    }
			               }
			            }
			           ,
			           error:function(err){
			        	  flag = false ;
			           }
			       })
		return flag ;
} ;

function deletecart(){
	var indexId=$(this).parent().find('#indexId').val();
	var cend=confirm('确定要删除该商品吗?');
	if(cend){
       		  var productId = $("#form"+indexId+" input[name=productId]").val() ;
       		  var status = $("#form"+indexId+" input[name=status]").val() ;
			  var data  ;
			  if(status == 2) // 已抢完
			     data = "productid="+productId+"&productcount=-1" ;
			  else
			     data = "productid="+productId+"&productcount=0" ;

              deleteproduct("${rc.contextPath}/spcart/editsc",data,indexId);
　　　　　            }
} ;






})
</script>

<script>
function deletefun(indexId)
{
	var cend=confirm('确定要删除该商品吗？');
	if(cend){
       		  var productId = $("#form"+indexId+" input[name=productId]").val() ;
			  var data = "productid="+productId+"&productcount=0" ;
              deleteproduct("${rc.contextPath}/spcart/editsc",data,indexId);
　　　　　            }
}
var flag = false ;
function deleteproduct(url,data,indexId){
				    if(!flag){
					    $.ajax({
						           type:'POST',
						           url: url ,
						           data: data ,
						           dataType: 'json' , /*返回的要是标准格式的json串，会自动转成json对象*/
						           success: function(msg){
						               /* console.log(msg); */
						               var errorMsg='';
						               if(msg !=null && msg.status!=null && msg.status!='undefined' && msg.status =='1'){ /*成功*/
						                  /*要把当前行去掉*/
						                   var porductIndex = $("#product"+indexId) ;
						                   if(porductIndex !=undefined && porductIndex !='undefined')
						                           porductIndex.hide();
						                   window.location.href="${rc.contextPath}/spcart/listsc" ;// 刷新页面价格
						               }else if(msg !=null && msg.status!=null && msg.status!= undefined && msg.status =='0' ){
                                             errorMsg = '亲，删除出错,请刷新页面!';
						                            /* 弹出层 */
													$('.protips').html(errorMsg);
													var scrollTop=$(document).scrollTop();
													var windowTop=$(window).height();
													var xtop=windowTop/2+scrollTop;
													$('.protips').css('top',xtop);
													$('.protips').css('display','block');
													setTimeout(function(){
														$('.protips').css('display','none');
													},2000);
						                    　
						               }
						              flag = false ;
						            },
						           error:function(err){
						           }
						       });
					    flag = true ;
					    return true ;
				    }else{
				        alert("正在删除!");
				    }
			}

function submitsc()
{
    var iscarttimeoutVal = $('#iscarttimeout').val();
     $.ajax({
                url:'${rc.contextPath}/spcart/submitsc',
                type:'POST',
				data:'iscarttimeout='+iscarttimeoutVal,
				dataType:'json',
				success:function(data){
				          /*如果库存不足，或是用户选的商品，过期了只有一部分，都要给出一个提示，或都刷新此页面，如果都成功了，而用户没有login，就跳到login页面,已是login后，就跳到定单确认的请求*/
				          var errorCode = data.errorCode ;
				          var status    = data.status ;
				          var ordertype = data.orderType ;
				          var errorMsg = '';
				          var locklist = data.lock ;  /*一定会有或为空*/
				          var unlocklist = data.unlock ;
				          var lacklist  = data.lack ;
				          var endSecond = data.endSecond ;
				          var iscarttimeout = data.iscarttimeout ;

				          if(ordertype !=undefined && ordertype !='undefined')
				              $("#ordertype").val(ordertype) ;
				          else
				              ordertype ='2' ;

				          if(errorCode !=undefined && errorCode!='undefined'  )
				          {
				             if(errorCode =='0' )
				                 errorMsg ='亲，请再执行一次';
				              else if(errorCode == '1')
				                 errorMsg ='亲，账号不存在';
				              else if(errorCode == '2')
				                 errorMsg = '亲，临时账号不存在';
				          }
				           /*显示errorMsg*/
				           if(errorMsg !='')
				           {
				                 showErrorMsg(errorMsg) ;
				                 return false ;　　　
				           }

				           var lockLength = JSON.parse(locklist).length ;
				           var unlockLength =JSON.parse(unlocklist).length ;
				           var lackLength =JSON.parse(lacklist).length ;

				           if(iscarttimeout =='1')
				           {
				              if( unlockLength > 0 || lackLength > 0 )
						      {
						        errorMsg = "有商品库存不足，已移出购物车";
						        showSubmitErrorMsg(errorMsg) ;
						      }else{
						      	 window.location.reload();
						      }
				              return false ;
				           }

						   if(lockLength>0 && unlockLength==0 && lackLength==0  )
						   {
						      if(ordertype=='2') /*有货而又没有login*/
						       {
						           document.formsubmit.action="${rc.contextPath}/user/tologin";
  								   document.formsubmit.submit();
						           /* window.location.href="${rc.contextPath}/user/tologin/2"*/

						        }
						      else if(ordertype=='1' ) /*有货而又login*/
						      {
						           window.location.href="${rc.contextPath}/order/confirm/1";
						      }
						   }else if( unlockLength > 0 || lackLength > 0 )
						   {
						        errorMsg = "有商品库存不足，已移出购物车";
						        showSubmitErrorMsg(errorMsg) ;
						        /* window.location.href="${rc.contextPath}/spcart/listsc"; */
						   }
				       },
			       error:function(err){
			             //alert(err);
				       }
           });

}

function  showErrorMsg(errorMsg)
{
   /* 弹出层 */
	$('.protips').html(errorMsg);
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.protips').css('top',xtop);
	$('.protips').css('display','block');
	setTimeout(function(){
			$('.protips').css('display','none');
	},2000);

}

function  showSubmitErrorMsg(errorMsg)
{
   /* 弹出层 */
	$('.protips').html(errorMsg);
	var scrollTop=$(document).scrollTop();
	var windowTop=$(window).height();
	var xtop=windowTop/2+scrollTop;
	$('.protips').css('top',xtop);
	$('.protips').css('display','block');
	setTimeout(function(){
			$('.protips').css('display','none');
		    window.location.href="${rc.contextPath}/spcart/listsc";
	},2000);

}

</script>
<form id="formsubmit" name="formsubmit" action="" method="post">
    <input type="hidden" id="ordertype"  name="ordertype"  value="2" />
</form>
<!--购物车是否超时-->
<input type="hidden" id="iscarttimeout" name="iscarttimeout" value="0" />
	<div class="tongjicnzz" style="display:none;">
		<#include "../common/tongjicnzz.ftl">
	</div>
</body>
</html>
