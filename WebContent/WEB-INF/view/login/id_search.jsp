<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="./js/jquery.validate.min.js" type="text/javascript"></script>
<script src="./js/validity.js" type="text/javascript"></script>
<script src="./js/jquery.cookie.js" type="text/javascript"></script>
<script src="./js/cookie_login.js" type="text/javascript"></script>
<script src="./js/jquery.placeholder.min.js" type="text/javascript"></script>
<script src="./js/placeholder_foce.js" type="text/javascript"></script>
</head>
<body>
 <header id="main-header" class="py-2 bg-secondary text-white">
  <div class="container"> 
   <div class="row">   
    <div class="col-md-6" align="center">
     <h1><i class="fas fa-user-check mr-sm-1">아이디 찾기</i></h1> 
    </div>
  <ul class="navbar-nav ml-auto">
     <li class="nav-item dropdown mr-3">      
       <div class="py-2">
        <a href="./LoginView.me" class="nav-link text-white">
         <i class="fas fa-user-circle mr-sm-1"></i>
         로그인
        </a>
       </div>   
    </ul>
    <ul class="navbar-nav py-2 mb-2 pl-3 pr-3">
     <li><a href="./index.jsp" class="navbar-brand  text-white">
    <i class="fa fa-home"></i>홈
   </a>   
   </li>  
    </ul>
   </div>
  </div>
 </header>
 <section id="actions" class="py-4 mb-4 bg-light">
  <div class="container">
   <div class="row"> </div>
  </div>
 </section>
 <section id="login">
  <div class="container">
   <div class="row">
    <div class="col-9 mx-auto">
     <div class="card">
      <div class="card-header">
       <h4>아이디 검색</h4>
      </div>
      <div class="card-body">
       <form action="./IdSearch.me" method="post" id="signupForm" enctype="application/x-www-form-urlencoded">
        <fieldset>
         <div class="form-group">
          <label for="name"><i class="fas fa-user mr-sm-1"></i>이름</label>
          <input type="text" name="name" id="name" class="form-control" placeholder="이름">
         </div>
         <div class="form-group">
          <label for="email"><i class="fas fa-envelope-open mr-sm-1"></i>이메일</label>
          <input type="email" name="email" id="email" class="form-control" placeholder="이메일">
         </div>
         <button type="submit" id="register" class="btn btn-info btn-block">
          아이디 찾기
         </button>
        </fieldset>
       </form>
      </div>
     </div>
    </div>
    <div class="col-9 mx-auto py-3 mb-3">
     <div>
      
      <span>
       <button type="button" class="btn btn-outline-warning" onclick="location.href='./PasswordSearchView.me'">
        <i class="fas fa-user-lock mr-sm-1"></i>비밀번호 찾기
       </button>
      </span>     
      <span>
       <button type="button" class="btn btn-outline-info" onclick="location.href='./MemberInsertView.me'">
        <i class="fas fa-user-plus mr-sm-1"></i>회원 가입
       </button>
      </span>
     </div>
    </div>
   </div>
  </div>
 </section>
 <footer id="main-footer" class="bg-secondary text-white mt-3 p-3">
  <div class="container">
   <div class="row">
    <div class="col">
     <p class="lead text-center">
      <img src="./img/log.png" class="mr-sm-1">어깨동무 게시판 &copy; 2000
     </p>
    </div>
   </div>
  </div>
 </footer>
</body>
</html>