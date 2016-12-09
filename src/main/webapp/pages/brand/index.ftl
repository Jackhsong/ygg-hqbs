<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>左岸城堡</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta content="telephone=no,email=no" name="format-detection">   
    <#include "../common/otherShare.ftl">
    <link href="${rc.contextPath}/pages/images/favicon.ico" rel="shortcut icon"> 
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/pages/css/base.css">
    <script type="text/javascript" src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
    <style type="text/css">
     #main{width:640px;margin:0 auto;}
     @media screen and (max-width:540px){#main{width:100%;}}
     body{background:#fff;}
     #main ul{margin:0 0.5rem;}
     #main ul li{width:49.3%;margin-top:0.6rem;}
     #main ul li:nth-child(odd){float:left;}
     #main ul li:nth-child(even){float:right;} 
     #main ul li img{width:100%;}
     #main li a{width:100%;padding-top:51.4%;background:no-repeat center; background-size: contain;}
     .fn-center{text-align:center;padding-top:2rem;margin-bottom:2rem;}
     #main .title{color:#1c1c20;font-size:0.49rem;font-weight:bold;border-bottom:1px solid #000;padding-bottom:2px;}
     #main .title span{display:inline-block;border-bottom:2px solid #000;padding-bottom:0.3rem;font-size:1.8rem;}   
    </style>
</head>
<body>
	<div id="main">
	
		<div id="products" style="margin-bottom:5.8rem;">
               <#if brandList ? exists>
			                 <#list brandList as bl >
		                     <div >
				                  	<div class="fn-center">
				                  	 	<span class="title"><span>${bl.categoryName}</span></span> 
				                  	 </div>
				                      <ul class="clearfix">
				                      <#if bl.brands ? exists>
				                             <#list bl.brands as bll >
				                                   <li>
				                                   <a href="${bll.url}" style="background-image:url(${bll.image})">
				                                   	<!-- <img src="${bll.image}"> -->
				                                   </a>
				                                   </li>
				                            </#list>
				                      </#if>
				                         
				                          
				                      </ul>      
						    </div>
			                 </#list>
             </#if> 
		</div>

        <!--底部导航 Start-->
		<input type="hidden" id="navFooterToPage" value="1"/>
		<#include "../common/navFooter.ftl">
		<!--底部导航 End-->
	</div>


</body>
</html>