<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title>${title}</title>
    <link rel="stylesheet" href="${rc.contextPath}/pages/css/scene/reset.css">
</head>
<body>
<!-------------------------------------------------   首页  ------------------------------------------>
<link rel="stylesheet" href="${rc.contextPath}/pages/css/scene/productModel.css">
<div class="wrap groupImg">
    <#if productList?exists && (productList?size>0 ) >
    	<#list productList as list>
    		<#if list.layoutType==1>
    			<div class="singleImg">
    				<a <#if list.oneType!=4> href="${list.oneUrl}" </#if> >
    					<img data-original="${list.oneImageUrl}" class="imglazyload"/>
    				</a>    			
    			</div>
    		<#else>
    			<div class="doubleImg clearfix">
    				<a <#if list.oneType!=4> href="${list.oneUrl}" </#if> class="fl">
    					<img data-original="${list.oneImageUrl}" class="imglazyload"/>
    				</a>
    				<a <#if list.twoType!=4> href="${list.twoUrl}" </#if> class="fl">
    					<img data-original="${list.twoImageUrl}" class="imglazyload"/>
    				</a>
    			</div>
    		</#if>
    	</#list>
    </#if>
    
    <!--更多商品-->
    
    <#if productMoreList?exists && (productMoreList?size>0 ) >
    <section class="moreGoods">
        <header class="moreHeader">
            <img src="${rc.contextPath}/pages/images/scene/moreTit.png" class="moreTit"/>
        </header>
        <ul class="list clearfix">
        	<#list productMoreList as list2>
            <li class="fl">
                <a class="link" href="${list2.buyUrl}">
                    <i class="img relative">
                    	<#if list2.status==3 >
                        	<em class="abso startCircle"><i>即将<br/>开始</i></em>
                        </#if>
                        <#if list2.status==4 >
                        	<em class="abso overCircle">已抢完</em>
                        </#if>
                        <img data-original="${list2.imageUrl}" class="imglazyload"/>
                    </i>
                    <b class="moreGoodsTit">${list2.name}</b>
                    <article class="priceBox clearfix">
                        <i class="newPrice fl">￥${list2.salesPrice}</i>
                        <del class="oldPrice fl">￥${list2.gegePrice}</del>
                        <em class="buyCar fr"><img src="${rc.contextPath}/pages/images/scene/buycar.png"/></em>
                    </article>
                </a>
            </li>
            </#list>
        </ul>
    </section>
    </#if>
</div>
<script type="text/javascript" src="${rc.contextPath}/pages/js/zepto.min.js"></script>
<script>
    $(function(){
        $(".imglazyload").picLazyLoad({
            threshold: 500,
            placeholder: ''
        });
    });
</script>

</body>
</html>