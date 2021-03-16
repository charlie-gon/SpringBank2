<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 
input type에 file이 지정된 경우 form encType을 multipart/form-data로 반드시 지정해야 한다 
파일 업로드는 반드시 post로 값 전달
-->

<form action="insertBoard" method="post" encType="multipart/form-data"> 
작성자<input type="text" name="writer"><br/>
제목<input type="text" name="title"><br/>
내용<textarea name="content"></textarea><br/>

<!-- 파일 하나만 업로드 -->
<!-- 첨부파일<input type="file" name="uploadFile"/><br/>  -->

<!-- 여러개 파일 업로드 -->
첨부파일<input type="file" name="uploadFile" multiple="multiple"/><br/>
<input type="submit" value="저장">

</form>

</body>
</html>