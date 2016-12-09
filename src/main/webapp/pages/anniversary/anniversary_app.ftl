<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">
    <link href="${rc.contextPath}/pages/images/favicon.ico" rel="shortcut icon">
    <script src="http://m.gegejia.com/ygg/pages/js/zepto-activity.min.js"></script>
    <title>左岸城堡-周年庆</title>
    <style>
        body,h1,h2,h3,h4,h5,h6,p,ul,ol,dl,dt,dd,li,img,input,button{padding: 0;margin: 0;border: none;font-weight: normal;}
        header,footer,article,aside,section,nav,menu,hgroup,details,dialog,figure,figcaption{display:block}
        ul,li{ list-style: none; }
        .fn-fl{float:left;}
        .fn-fr{float:right;}
        .clearfix:after {content:"\200B"; display:block; height:0; clear:both;}
		.clearfix{ *zoom:1; }  
        body,html{font-family:'microsoft yahei';}
        .main{width: 750px;margin: 0 auto;}
        @media screen and (max-width: 800px) {.main{width:100%;}}
        /*****头部*****/
        header{background: url("${rc.contextPath}/pages/images/anniversary/top_banner.jpg") no-repeat;background-size:100%; width: 100%; height: 13.40rem; position: relative;}
        header .timeX{ position: absolute; left: 3.45rem;top: 8rem;color: #fff;font-size: 0.43rem;}
         header img{width: 100%;}
         header .miaoshaImg{position: absolute; left: 2.1rem;top: 10.1rem;}
         header .miaoshaImg img{width: 5.82rem;height: 3.33rem;}
        /*****正文列表*****/

	        /*****秒杀*****/
	        #container .timeList-top{ background:#f9c0c4; padding: 0.17rem 0rem;}
	        #container .timeList-top ul{background: url("${rc.contextPath}/pages/images/anniversary/timeList_bg.jpg") no-repeat;background-size:100%; width: 100%; height: 0.925rem; position: relative; overflow: hidden;}
	        #container .timeList-top li{color: #fff;font-size: 0.35rem; position: absolute; width: 2.16rem;height: 0.91rem; line-height: 0.91rem; text-align: center; cursor: pointer;}
	        #container .timeList-top li:first-child{width:2.16rem;left:-0.3rem;}
	        #container .timeList-top li:nth-child(2){left:1.39rem;}
	        #container .timeList-top li:nth-child(3){left:3.05rem;}
	        #container .timeList-top li:nth-child(4){left:4.72rem;}
	        #container .timeList-top li:nth-child(5){left:6.4rem;}
	        #container .timeList-top li:last-child{width:1.81rem; left: 8.0rem; padding-left: 0.2rem}
	        #container .timeList-top li.active{ background:url("${rc.contextPath}/pages/images/anniversary/tab_bg02.png")no-repeat;background-size:100%; color:#ff3544;}
	        #container .timeList-top li:first-child.active{ background:url("${rc.contextPath}/pages/images/anniversary/tab_bg01.png")no-repeat;background-size:100%; height: 100%;}
	        #container .timeList-top li:last-child.active{ background:url("${rc.contextPath}/pages/images/anniversary/tab_bg03.png")no-repeat;background-size:100%; height: 100%;}
	        #container .timeList-container{background: #ff9aab; padding:0.2rem 0rem;}
	        #container .timeList-container ul li{width: 3.16rem;height:5.08rem; overflow: hidden;border:solid 1px #e5e5e5; margin:0.1rem 0rem 0rem 0.1rem;float: left; }
	        #container .timeList-container ul li a{display: block;text-decoration: none;}
	        #container .timeList-container ul li .imgX{height:3.16rem;overflow: hidden; position: relative;}
	        #container .timeList-container ul li .imgX .status{position: absolute;width: 1.62rem;
    height: 1.62rem; left: 0.8rem; top:0.8rem;color: #fff; font-size: 0.4rem; text-align: center;}
    		#container .timeList-container ul li .imgX .status.start{padding-top:0.3rem; height:1.31rem; background:url("${rc.contextPath}/pages/images/anniversary/circle01.png") no-repeat; background-size: 100%;}
    		#container .timeList-container ul li .imgX .status.end{padding-top:0.55rem; height: 1.09rem; background:url("${rc.contextPath}/pages/images/anniversary/circle02.png") no-repeat; background-size: 100%;}
	        #container .timeList-container ul li img{width: 3.18rem;height:3.18rem;}
	        #container .timeList-container ul li .textX{background: #fff; height: 1.82rem; padding-top:0.2rem;}
	        #container .timeList-container ul li .titleX{ color: #33383b;font-size: 0.325rem; width: 2.9rem;overflow: hidden; height: 0.89rem; margin: 0 auto;}
	        #container .timeList-container ul li .priceX{width: 2.9rem;margin:0.15rem auto 0rem auto; }
	        #container .timeList-container ul li .newPriceX{ color: #ff3300; font-size: 0.43rem }
	        #container .timeList-container ul li .oldPriceX{color:#999;font-size: 0.30rem;text-decoration:line-through; padding-top: 0.08rem;}
	        #container .timeList-container ul li.loadingImg{width:1.67rem; float: none; height: 5rem; margin: 0rem auto; border: none;}
	        #container .timeList-container ul li.loadingImg img{ width: 100%; height: 1.67rem;}

	        /*****品牌团*****/
	        #brand-group .brand-top{background:url("${rc.contextPath}/pages/images/anniversary/brand_top.jpg") no-repeat;background-size:100%; width: 100%; height: 1.70rem;}
	        #brand-group .brand-container{background: #ffc3c8; padding:0.1rem 0rem;}
	        #brand-group .brand-container ul li{width:4.72rem; height: 2.79rem; float: left; margin:0.19rem 0rem 0rem 0.19rem;}
	        #brand-group .brand-container img{width:4.72rem; height: 2.79rem;}

	        /*****更多好货推荐*****/
	        #recommend .recommend-top{background:url("${rc.contextPath}/pages/images/anniversary/moreRecommend.jpg") no-repeat;background-size:100%; width: 100%; height: 1.3rem;}
	        #recommend .timeList-container{background: #ffa8c9; padding:0.1rem 0rem;}
	        #recommend .recommend-bottom{background:url("${rc.contextPath}/pages/images/anniversary/moreRecommend_bottom.jpg") no-repeat;background-size:100%; width: 100%; height: 1.27rem;}
	        /*****其它货品*****/
	        #other-goods .otherGoods-container{background: #ffffff; padding:0.1rem 0rem 0.5rem 0rem;}
	        #other-goods .otherGoods-container ul li{width:4.96rem; height: 2.59rem; float: left; margin:0.2rem 0rem 0rem 0.02rem;}
	        #other-goods .otherGoods-container ul li img{width:4.96rem; height: 2.59rem;}
	        #other-goods .otherGoods-container .bigImgX{margin-top:0.2rem;}
	        #other-goods .otherGoods-container .bigImgX img{width:100%; height: 2.59rem;}

		/*****尾部*****/
		footer{background: url("${rc.contextPath}/pages/images/anniversary/bottom_bg01.jpg") no-repeat;background-size:100%; width: 100%; height: 8.3rem; position: relative;}
		footer .weixinNum{ position: absolute;color:#ff545f;font-size: 0.62rem; width: 6.0rem; left: 2.08rem;top:5.13rem; text-align: center;}
		footer .copyBtn{ position: absolute; width: 6.08rem; height: 0.87rem; cursor: pointer; left:2rem;top:5.5rem; }
    </style>
</head>
<body>
	<input type="hidden" value="${clientType}" id="clientType"></input>
	<input type="hidden" value="${timeType}" id="timeType"></input>
	<div class="main">
		<!--头部：Start-->
		<header>
			<div class="timeX" id="countDownTimeX">剩<span class="day">6</span>&nbsp;<span class="hour">22</span><span class="minute">33</span><span class="second">58</span></div>
			<div class="miaoshaImg"><img src="${rc.contextPath}/pages/images/anniversary/miaosha.gif"></div>
		</header>
		<!--头部：End-->
		<!--正文列表开始：Start-->
		<article id="container">
			<!--秒杀：Start-->
			<div class="timeList-top">
				<ul>
					<li timeType="1">10:00</li>
					<li timeType="2">12:00</li>
					<li timeType="3">14:00</li>
					<li timeType="4">16:00</li>
					<li timeType="5">20:00</li>
					<li timeType="6">22:00</li>
				</ul>
			</div>
			<div class="timeList-container">
				<ul class="clearfix" id="timeListUL">
				    
					<#if productList?exists && (productList?size>0) >
		        		<#list productList as list>    	
		                <li>
							<a href="${list.buyUrl}">
								<div class="imgX">
									<img src="${list.imageUrl}">
									<#if list.status==2>
									<div class="status start">还有<br/>机会</div>
									<#elseif list.status==3>
									<div class="status start">即将<br/>开始</div>
									<#elseif list.status==4>
									<div class="status end">已抢完</div>
									<#else>
									</#if>
								</div>
								<div class="textX">
									<div class="titleX" title="${list.productName}">${list.productName}</div>
									<div class="priceX clearfix">
										<div class="newPriceX fn-fl">￥${list.lowPrice}</div>
										<div class="oldPriceX fn-fr">￥${list.highPrice}</div>
									</div>
								</div>
							</a>
						</li>
		                </#list>
		            </#if>
				</ul>
			</div>
			<!--秒杀：End-->
			<!--品牌团：Start-->
			<div id="brand-group">
				<div class="brand-top"></div>
				<div class="brand-container">
					<ul class="clearfix">
						<#if groupList?exists && (groupList?size>0) >
			        		<#list groupList as list>    	
			                <li>
								<a href="${list.buyUrl}"><img src="${list.imageUrl}"/></a>
							</li>
			                </#list>
			            </#if>
					</ul>
				</div>
			</div>
			<!--品牌团：End-->
			<!--更多好货推荐：Start-->
			<div id="recommend">
				<div class="recommend-top"></div>
				<div class="timeList-container">
					<ul class="clearfix">
						<#if recommendList?exists && (recommendList?size>0) >
			        		<#list recommendList as list>    	
			                <li>
								<a href="${list.buyUrl}">
									<div class="imgX">
										<img src="${list.imageUrl}">
									</div>
									<div class="textX">
										<div class="titleX" title="${list.productName}">${list.productName}</div>
										<div class="priceX clearfix">
											<div class="newPriceX fn-fl">￥${list.lowPrice}</div>
											<div class="oldPriceX fn-fr">￥${list.highPrice}</div>
										</div>
									</div>
								</a>
							</li>
			                </#list>
			            </#if>
		            </ul>
				</div>
				<div class="recommend-bottom"></div>
			</div>
			<!--更多好货推荐：End-->
			<!--品牌团：Start-->
			<div id="other-goods">
				<div class="otherGoods-container">
					<ul class="clearfix">
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1003"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img01.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1004"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img02.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1005"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img03.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1006"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img04.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/commonActivitiesList/1781"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img05.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1010"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img06.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1009"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img07.jpg"/></a>
						</li>
						<li>
							<a href="ggj://redirect/resource/appCustomActivitiesDetail/1011"><img src="${rc.contextPath}/pages/images/anniversary/recommend_img08.jpg"/></a>
						</li>
					</ul>
				</div>
			</div>
			<!--品牌团：End-->
		</article>
		<!--正文列表开始：End-->
		<!--尾部开始：Start-->
		<footer>
			<div class="weixinNum">lovegegejia16</div>
		</footer>
		<!--尾部开始：End-->
	</div>
</body>
</html>
<script type="text/javascript">
$(function(){

	 var _clientType = $("#clientType").val();
	 
	 function countDownTimeX(){
	     $.ajax({
	           type:'POST',
	           url:'${rc.contextPath}/activity/anniversary/refreshTime',
	           data:'',
	           dataType: 'json' ,
	           success: function(msg){
	                 var _second = msg ;
	                 if(_second ==undefined || _second=='')
	                 {
	                   $('#countDownTimeX').hide();
	                    return ;
	                 }
	                 var stime=　_second　 ;
					 var sid="#countDownTimeX";
					  window.timer1 = setInterval(function(){
						　stime--;
						　brandCdown(stime,sid);
					　},980);
					　brandCdown(stime,sid);
	             },
	           error:function(err){
	             }
	       }); 
	 }
     
     countDownTimeX();//倒计时请求

     function timeType(){
     	var _timeType = ($("#timeType").val())-1;
	    var _oLI = $("#container .timeList-top").find("li");
	     if(_timeType ==undefined || _timeType==''){
	     	_oLI.eq(0).addClass("active");
	     }else{
			_oLI.eq(_timeType).addClass("active");
	     }
	     $("#container .timeList-top").find("li").on("touchend",function(){
			$(this).addClass("active").siblings().removeClass("active");
			var _timeType = $(this).attr("timeType");
			var _data = {
				timeType:_timeType,
				clientType:_clientType
			}

			function beforeSend(){
				$("#timeListUL").html("<li class='loadingImg'><img src='${rc.contextPath}/pages/images/anniversary/loading.gif' /></li>")
			}
			$.ajax({
	           type:'POST',
	           url:'${rc.contextPath}/activity/anniversary/getProductDate',
	           data:_data,
	           beforeSend:beforeSend,
	           dataType: 'json' ,
	           success: function(data){
	           	    var _html = "";
	                for(var i in data){
	                	var _statusHtml = "";
	                	if(data[i].status == 2){
	                	_statusHtml = "<div class='status start'>还有<br/>机会</div>";
	                	}else if(data[i].status == 3){
	                	_statusHtml = "<div class='status start'>即将<br/>开始</div>";
	                	}else if(data[i].status == 4){
	                	_statusHtml = "<div class='status end'>已抢完</div>";
	                	}
	                	_html +="<li><a href='"+data[i].buyUrl+"'><div class='imgX'>"+
	                		   "<img src='"+data[i].imageUrl+"'>"+_statusHtml+"</div>"+
	                		   "<div class='textX'><div class='titleX' title='"+data[i].productName+"'>"+
	                		   ""+data[i].productName+"</div><div class='priceX clearfix'>"+
	                		   "<div class='newPriceX fn-fl'>￥"+data[i].lowPrice+"</div>"+
	                		   "<div class='oldPriceX fn-fr'>￥"+data[i].highPrice+"</div>"+
	                		   "</div></div></a></li>";
	                } 
	                $("#timeListUL").html(_html);
	             },
	           error:function(err){
	             }
	       }); 
		})
     }
     timeType();//秒杀点击时间刷新
     
})

	
</script>
<script src="${rc.contextPath}/pages/js/h5self-adaption.js"></script>
<script src="${rc.contextPath}/pages/js/gegejia.js"></script>
<div style="display:none"><script src="http://s11.cnzz.com/z_stat.php?id=1257959448&web_id=1257959448" language="JavaScript"></script></div>
<div style="display:none"><script src="http://s11.cnzz.com/z_stat.php?id=1257962989&web_id=1257962989" language="JavaScript"></script></div>
