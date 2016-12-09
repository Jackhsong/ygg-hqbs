<meta charset="UTF-8">
<style>
/*****底部导航****/
		#footBar{font:1.2rem/1.2 'Microsoft Yahei',Tahoma,Helvetica,Arial,Sans-serif;}
        #footBar .car,#footBar a.current .car,#footBar .house,#footBar a.current .house,#footBar .person,#footBar a.current .person,#footBar .star,#footBar a.current .star,#footBar .brand,#footBar a.current .brand{margin: 0 auto 0.05rem auto; width:20px;height:20px;}
		#footBar .car{background:url(${rc.contextPath}/scripts/images/car.png) no-repeat;background-size:100% auto; position: relative;}
		#footBar .car .carNum{ display:none;position: absolute; right: -10px; top: -8px; background: #ff0000;  color: #fff; border-radius: 50%; width: 16px;height:16px;line-height:16px; text-align: center; font-size: 12px;  }
		#footBar a.current .car{background:url(${rc.contextPath}/scripts/images/car01.png) no-repeat;background-size:100%;}
		#footBar .house{background:url(${rc.contextPath}/scripts/images/house.png) no-repeat;background-size:100%;}
		#footBar a.current .house{background:url(${rc.contextPath}/scripts/images/house01.png) no-repeat;background-size:100%;}
		#footBar .person{background:url(${rc.contextPath}/scripts/images/person.png) no-repeat;background-size:100%;}
		#footBar a.current .person{background:url(${rc.contextPath}/scripts/images/person01.png) no-repeat;background-size:100%;}
		#footBar .star{background:url(${rc.contextPath}/scripts/images/star.png) no-repeat;background-size:100%;}
		#footBar a.current .star{background:url(${rc.contextPath}/scripts/images/star01.png) no-repeat;background-size:100%;}
		
		#footBar .brand{background:url(${rc.contextPath}/scripts/images/brand.png) no-repeat;background-size:100%;}
		#footBar a.current .brand{background:url(${rc.contextPath}/scripts/images/brand01.png) no-repeat;background-size:100%;}
		
		#footBar { width: 100%; position:fixed; bottom: 0; background: #fff;  margin:0 auto;border-top:solid 1px #e5e5e5; z-index:100000; }
		#footBar a{display: inline-block; text-align: center; color: #999999;  font-size: 0.32rem; padding: 0.3rem 0rem 0.1rem 0rem; text-decoration: none; font-weight: bold;}
		#footBar { width: 100%; max-width:640px;position:fixed; bottom: 0; left:0;background: #fff;  margin:0 auto; }
		#footBar a{ width: 24%; display: inline-block; text-align: center; color: #999999;  font-size: 12px; padding: 12px 0 6px; text-decoration: none; font-weight: normal;}
		#footBar a.current{color: #33383b;}
		@media screen and (min-width: 650px){
			#footBar{left:50%;margin-left:-320px;}
		}
</style>
<!--底部导航 Start-->
<span  id="showCartCount4" name="showCartCount4"></span>
 <div id="footBar">
		<a href="${rc.contextPath}/hqbsHomeInfo/getHomeInfo#wechat_redirect">
			<div class="house"></div>
			<div class="text01">商城首页</div>
		</a>

		<a href="${rc.contextPath}/spcart/listsc#wechat_redirect">
			<div class="car">
				<span  id="showCartCount4" name="showCartCount4" class="carNum"></span>
			     <!-- <div class="carNum">${(cartCount)!"0"}</div> -->
			</div>
			<div class="text01">购物车</div>
		</a><a href="${rc.contextPath}/order/list/0#wechat_redirect">
			<div class="person"></div>
			<div class="text01">我的订单</div>
		</a><a href="${rc.contextPath}/spokesPerson/getList#wechat_redirect">
			<div class="star"></div>
			<div class="text01">我是代言人</div>
		</a>
		
</div> 
<!--底部导航 End-->
<script>
	$(function(){
		var _index = $("#navFooterToPage").val();
		$("#footBar").find("a").eq(_index).addClass("current").siblings().removeClass("current");

		$.ajax({
           type:'POST',
           url: '${rc.contextPath}/spcart/showcartcount',
           dataType: 'json' ,
           success: function(msg){
           			if(msg.cartCount > 0){
           				$(".carNum").show().text(msg.cartCount);
           			}
           },
           error:function(err){
        	   $(".carNum").text(0);
           }
        });
	})
</script>