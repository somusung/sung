 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html>
 <html>
 <head>
 <meta charset="UTF-8">
 <title> 부서 입력 </title>
 <c:forEach var="arrayList" items="${arrayList}">
 <c:if test="${arrayList.deptno==param.deptno}"> 
<script type="text/javascript"> 
alert("입력하신 ${param.deptno}번의 부서 번호가 존재합니다. 다시 입력하세요");
 location.href="./DeptInsertView.so";
 </script>
 </c:if> 
</c:forEach>
 </head>
 <body>
 <script type="text/javascript">
alert("입력하신 ${deptDTO.deptno}번의 부서 번호를 등록하였습니다.");
 location.href="./DeptSelect.so";
 </script>
 </body>
 </html>