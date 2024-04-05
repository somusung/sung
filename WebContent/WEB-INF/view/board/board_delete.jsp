<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>어깨동무 게시판</title>
</head>
<body>

<c:choose>
 <c:when test="${sessionScope.id == 'admin'}">
  <script type="text/javascript">
  confirm("게시글을 삭제하시겠습니까?.")
    location.href="./BoardDelete.do?num=${param.num}&id=admin"
  </script>
 </c:when>
 <c:otherwise>
  <script type="text/javascript">
  confirm("게시글을 삭제하시겠습니까?.")
   location.href="./BoardDelete.do?num=${param.num}&id=${sessionScope.id}"
  </script>
 </c:otherwise>
</c:choose>
 </body>
</html>