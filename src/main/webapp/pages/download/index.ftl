<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<title>左岸城堡 - 优选全球健康食品，100%优质正品，每天10点上新</title>
		<meta property="wb:webmaster" content="1d1b018b9190b96c" />
		<meta property="qc:admins" content="342774075752116375" />
		<meta name="keywords" content="左岸城堡，格格，燕格格，美食平台，平台，美食APP" />
    	<meta name="description" content="左岸城堡，把爱和美食带回家" />
    	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/pages/css/common.css">
    	<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
    	<script type="text/javascript" src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
    	<script type="text/javascript" src="${rc.contextPath}/pages/js/index.js"></script>
    	
	</head>
	<body>
		<div class="wrapper">
			<div class="page">
				<a href="#" class="logo"></a>
				<div class="main">
					<div class="iclock" id="clockbox">
						<span class="day"></span><span>天</span><span class="hour"></span><span>:</span><span class="minute"></span><span>:</span><span class="second"></span><div class="grid"></div><div class="grid1"></div><div class="grid2"></div><div class="grid3"></div><div class="grid4"></div><div class="grid5"></div><div class="grid6"></div>
					</div>
					<script type="text/javascript">
						$(function(){
							/* count down */
							var stime='2015/3/15 10:00:00';
							var sid="#clockbox";
							var timer = setInterval(function(){								
								iClockCdown(stime,sid);
							},1000);
							iClockCdown(stime,sid);
						})
					</script>
                    <form id="form1" name="form1" action="" method="post" >	
					   <input type="text" value="请填写您的手机号码" id="phoneno" maxlength="11" name="phoneno"/>
					   <a href="javascript:void(0)" id="phonesubmit"></a>
                    </form>
				</div>
			</div>
			<div class="adwrap">
				<ul class="ad clear">
					<li class="li1"></li>
					<li class="li2"></li>
					<li class="li3"></li>								
				</ul>
				<div class="adwrap_a">
					<a href="javascript:void(0)" class="adwrap_a1 adcolor"></a>
					<a href="javascript:void(0)" class="adwrap_a2"></a>
					<a href="javascript:void(0)" class="adwrap_a3"></a>
				</div>	
			</div>
			<div class="about"></div>
			<div class="footer">Copyright2014-2015  杭州萧剑贸易有限公司   浙ICP备10030838号-6<a href="http://m.kuaidi100.com" target="_blank">快递查询</a></div>
		</div>
		<script>
          $(function(){
                $("#phonesubmit").click(function(){
                          var mobile=$('#phoneno').val();
					      if( mobile == '' || mobile == '请填写您的手机号码'){
                              alert("请输入手机号");
					      } else{
						      var reg=/(^1[3-9]\d{9}$)/;
						      if(!reg.test(mobile)){
                              alert("手机号格式不正确");
							  return false;
						   }
						   //表单提交submit
						   //document.form1.action="${rc.contextPath}/download";
                           //document.form1.submit();
                           
                           $.ajax({
                             url:'${rc.contextPath}/download/reserveygg',
                             type:'POST',
							 data:{'phoneno':mobile},
							 dataType:'json',
				              success:function(data){
				                alert("预约成功!");
				                /*window.location.href="${rc.contextPath}/download";*/
				                $('#phoneno').val('');
				              },
				              error:function(){
				              }
                           });
					}

                  });
          });
        </script>
	</body>
</html>