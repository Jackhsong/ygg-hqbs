<!DOCTYPE html>
<html>
<head>
		<meta charset="utf-8" />
		<title>Ajax上传与下载</title>
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta content="email=no" name="format-detection" />
		<title>左岸城堡</title>
		<link rel="apple-touch-icon" href="custom_icon.png">
		<link href="${rc.contextPath}/pages/css/swiper.css" rel="stylesheet" type="text/css"/>
		<link href="${rc.contextPath}/pages/css/global.css" rel="stylesheet" type="text/css"/>
		<link href="${rc.contextPath}/pages/css/main.css" rel="stylesheet" type="text/css"/>
		<script src="${rc.contextPath}/pages/js/jquery-1.11.2.min.js"></script>
		<script src="${rc.contextPath}/pages/js/ajaxfileupload.js"></script>
	</head>
<body>

<font size="5" style="text-align: center">Ajax 上传文件，图片</font>
 <br/><br/>
 <p>
 <div>
 <input id="img" type="file" size="45" name="img" class="input">
 <button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">Upload</button>
 </div>
  <br/><br/>

  <button class="button" id="buttonUpload" onclick="btnajax();">123Test</button>
 </p>
<script>

function btnajax()
{
   $.ajax({
      url:'${rc.contextPath}/test/test2', //你处理上传文件的服务端
       // url:'http://192.168.1.18:8080/activity/20150214/upload',
       data:'',
       dataType: 'json',
       success:function(data)
       {
           console.log(data) ;
       }
   });
}
 function ajaxFileUpload()
       {
          $.ajaxFileUpload
             (
               {
                     url:'${rc.contextPath}/test/ajaxupload', //你处理上传文件的服务端
                   // url:'http://192.168.1.18:8080/activity/20150214/upload',
                    secureuri:false,
                    fileElementId:'img',
                    dataType: 'json',
                    success: function(data)
                          {
                            console.log(data);
                            alert("data.sc:" + data.success);
                          }
                       }
                 );
               return true;
         } 
</script>
</body>
</html>