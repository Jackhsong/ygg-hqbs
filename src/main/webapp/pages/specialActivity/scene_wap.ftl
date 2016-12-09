<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title></title>
    <link rel="stylesheet" href="${rc.contextPath}/pages/css/scene/reset.css">
</head>
<body>
<!-------------------------------------------------   首页  ------------------------------------------>
<link rel="stylesheet" href="${rc.contextPath}/pages/css/scene/productModel.css">
<div class="wrap">
    <!-------------------------------------------  头部   ------------------------------------------->
    <header class="head relative" id="head">
        <div class="quphone">
            <span class="abso"></span>
            <a href="javascript:void(0);" onclick="window.open('http://download.gegejia.com')">
                <img src="${rc.contextPath}/pages/images/activity/sidebar.png">
            </a>
        </div>
    </header>
    <!--banner-->
    <section class="bannerWrap">
        <img  data-original="${url}" class="imglazyload"/>
    </section>
    <!--对应商品-->
    <section class="goods">
        <ul class="list">
        <#if productList?exists && (productList?size>0 ) >
        <#list productList as list>
            <li>
                <a class="link" href="${list.buyUrl}">
                    <i class="img relative">
                        <img data-original="${list.url}" class="imglazyload"/>
                        <#if list.status==3 >
                        	<em class="abso startCircle"><i>即将<br/>开始</i></em>
                        </#if>
                        <#if list.status==4 >
                        	<em class="abso overCircle">已抢完</em>
                        </#if>
                    </i>
                    <b class="goodsTit"><strong class="hot"><i>${list.keyword}</i></strong>${list.name}</b>
                    <p class="goodsDesc"><i>格格说 / </i>${list.desc}</p>
                    <article class="priceBox clearfix">
                        <i class="newPrice fl">￥${list.salesPrice}</i>
                        <del class="oldPrice fl">￥${list.gegePrice}</del>
                        <em class="arrow fr"><img src="${rc.contextPath}/pages/images/scene/arrow.png"/></em>
                    </article>
                </a>
            </li>
            </#list>
            </#if>
        </ul>
    </section>
    <!--更多商品-->
    <section class="moreGoods">
        <header class="moreHeader">
            <img src="${rc.contextPath}/pages/images/scene/moreTit.png" class="moreTit"/>
        </header>
        <ul class="list clearfix">
        	<#if productMoreList?exists && (productMoreList?size>0 ) >
        	<#list productMoreList as list>
            <li class="fl">
                <a class="link" href="${list.buyUrl}">
                    <i class="img relative">
                    	<#if list.status==3 >
                        	<em class="abso startCircle"><i>即将<br/>开始</i></em>
                        </#if>
                        <#if list.status==4 >
                        	<em class="abso overCircle">已抢完</em>
                        </#if>
                        <img data-original="${list.url}" class="imglazyload"/>
                    </i>
                    <b class="moreGoodsTit">${list.name}</b>
                    <article class="priceBox clearfix">
                        <i class="newPrice fl">￥${list.salesPrice}</i>
                        <del class="oldPrice fl">￥${list.gegePrice}</del>
                        <em class="buyCar fr"><img src="${rc.contextPath}/pages/images/scene/buycar.png"/></em>
                    </article>
                </a>
            </li>
            </#list>
            </#if>
        </ul>
    </section>
</div>
<script type="text/javascript" src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<script>
    $(function(){
    
    	//1比1图片
       function editImg(){
    	   $(".imglazyload").each(function(i,n){
       	    if(i==0){
       	    	return;
       	    }
       	    else{
       	    	
       	    	var w = $(n).css("width")-10;
                $(n).css({"height":w,"margin":"0 auto","display":"block"});
       	    }
           });
      }
      
       editImg();
        
       
       
        $(".imglazyload").picLazyLoad({
            threshold: 500,
            placeholder: ''
        });
        var qxzcookie1=getCookie("ggjquxiazai");
        if(parseInt(qxzcookie1) != 1){
            $('.quphone').show();
        }
        $('.quphone span').click(function(){
            var qxzcookie=getCookie("ggjquxiazai");

            if(qxzcookie == null){
                setCookie("ggjquxiazai","1","d3");
                $('.quphone').css({height:'0',paddingTop:'0'});
                $('.quphone').hide();

            }else{
                $('.quphone').hide();
            }
        });
    });
    function setCookie(name,value,time)
    {
        var strsec = getsec(time);
        var exp = new Date();
        exp.setTime(exp.getTime() + strsec*1);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

    }
    function getsec(str)
    {
        var str1=str.substring(1,str.length)*1;
        var str2=str.substring(0,1);
        if (str2=="s")
        {
            return str1*1000;
        }
        else if (str2=="h")
        {
            return str1*60*60*1000;
        }
        else if (str2=="d")
        {
            return str1*24*60*60*1000;
        }
    }
    function getCookie(name)
    {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

        if(arr=document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }
</script>

</body>
</html>