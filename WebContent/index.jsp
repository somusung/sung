<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>어깨동무 게시판</title>
<link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="./css/global.css">
<link rel="stylesheet" type="text/css" href="./css/all.min.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
<script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
<script src="./js/popper.min.js" type="text/javascript"></script>
</head>
<body>
 <header id="main-header" class="py-2 bg-primary text-white">
  <div class="container">
   <div class="row mt-3 p-3">
    <div class="col-md-6"></div>
   </div>
  </div>
 </header>
 <section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
   <div class="row"></div>
  </div>
 </section>
 <section id="posts">
  <div class="container">
   <div class="row justify-content-center">
    <div class="col-md-3">
     <div class="card text-center bg-primary text-white mb-3">
      <div class="card-body">
       <h3>게시판</h3>
       <h4 class="display-4">
        <i class="fas fa-user-tag"></i>
       </h4>
       <c:choose>
 <c:when test="${!empty sessionScope.id}">
   <button type="button" class="btn btn-outline-light btn-sm" onclick="location.href='./BoardSelect.do'">클릭</button>  
 </c:when>
 <c:otherwise>
  <button type="button" class="btn btn-outline-light btn-sm" onclick="location.href='./LoginView.me'">클릭</button>
 </c:otherwise>
</c:choose>
      
      </div>
     </div>
    </div>
    <div class="col-md-3">
     <div class="card text-center bg-success text-white mb-3">
      <div class="card-body">
       <h3>회원 가입</h3>
       <h4 class="display-4">
        <i class="fas fa-users"></i>
       </h4>
       <button type="button" class="btn btn-outline-light btn-sm" onclick="location.href='MemberInsertView.me'">클릭</button>       
      </div>
     </div>
    </div>
    <div class="col-md-3">
     <div class="card text-center bg-warning text-white mb-3">
      <div class="card-body">
       <h3>로그인</h3>
       <h4 class="display-4">
        <i class="fas fa-user"></i>
       </h4>
       <button type="button" class="btn btn-outline-light btn-sm" onclick="location.href='LoginView.me'">클릭</button>       
      </div>
     </div>
    </div>
    <p class="text-danger"> <i class="fa fa-check-square mr-sm-2"></i>아이디가 없으신 분은 회원가입 후 이용하실 수 있습니다. </p>     
   </div>
   <div class="row justify-content-center">
   <img src="./img/index.png">
   </div>
  </div>
 </section>
 <footer id="main-footer" class="bg-secondary text-white mt-4 p-4">
  <div class="container">
   <div class="row">
    <div class="col">
     <p class="lead text-center">
      <img src="./img/log.png" class="mr-sm-1">
      어깨동무 게시판 &copy; 2000
     </p>
    </div>
   </div>
  </div>
 </footer>
</body>
</html>