<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="user/1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="put">
    </form>
    
    <form action="user/1" method="post">
        <input type="submit" value="post">
    </form>
    
    <form action="user/1" method="get">
        <input type="submit" value="get">
    </form>
    
    <form action="user/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="delete">
    </form>
</body>
</html>