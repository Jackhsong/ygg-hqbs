<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon"  href="${rc.contextPath}/pages/images/favicon.ico">
</head>
<body>

<font size="5" style="text-align: center">缓存刷新页面</font>
<p align="center"></p>

<p>
 <div><p align="center"><font color="red" size="3" >${(errorCode)?if_exists}</font></p> </div>
 
 <div>
 <form id="form1" name="form1" action="${rc.contextPath}/cache/clearAllCache" method="post"> 
			<input type="button" onclick="clearallcachefun()" value="清空所有缓存"/>
　</form>	
</div>
</br>
<script>
function  clearallcachefun()
{
  var flag=confirm('确定要清空所有缓存吗?');
  if(flag)
       document.form1.submit();
}
</script>
 <div>
 <form action="${rc.contextPath}/cache/cic" method="post"> 
			<input type="submit" value="清空首页缓存"/>
　</form>	
</div>

</br>
 <div>
 <form action="${rc.contextPath}/cache/ric" method="post"> 
			<input type="submit" value="刷新首页缓存"/>
　</form>	
</div>
</br>
<div>
 <form action="${rc.contextPath}/cache/cpc" method="post"> 
			<input type="submit" value="清空所有商品缓存"/>
　</form>	
</div>
</br>
<div>
 <form action="${rc.contextPath}/cache/rpc" method="post"> 
			<input type="submit" value="刷新所有商品缓存"/>
　</form>	
</div>
</br>
 <div>
 <form action="${rc.contextPath}/cache/cbc" method="post"> 
			<input type="submit" value="清空所有专场缓存"/>
　</form>	
</div>
</br>
<div>
 <form action="${rc.contextPath}/cache/rbc" method="post"> 
			<input type="submit" value="刷新所有专场商品缓存"/>
　</form>	
</div>
</br>
<div>
 <form action="${rc.contextPath}/cache/refreshAddress" method="post"> 
			<input type="submit" value="刷新省市区缓存"/>
　</form>	
</div>
</br>
<div>
 <form action="${rc.contextPath}/cache/refreshfreefreightmoney" method="post"> 
			<input type="submit" value="刷新所有的邮费"/>
　</form>	
</div>
</br>
<div>
<form action="${rc.contextPath}/cache/testst1" method="post"> 
			<input type="submit" value="testst1"/>
</form>	
</div>

</p>
</body>
</html>