<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var url=$("#tj").attr("href");
	  $.post(url,function(data){
		 console.log(data);
	  });
	});
</script>
</head>
<body> 
<a href="authority/test">test</a>
<a href="user/getAll" id="tj">testJson</a>
</body>
</html>