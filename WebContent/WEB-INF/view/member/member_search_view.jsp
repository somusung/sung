<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>어깨동무 회원가입</title>
<link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="./css/global.css">
<link rel="stylesheet" type="text/css" href="./css/all.min.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
<script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
 $("#limit").change(function() {
 	location.href="./MemberSearch.me?limit="+$(this).val()+"&keyword=${keyword}";
 });
 if ("${!empty limit}") {
  $("#limit").val("${limit}").prop('selected', true);
 };
});
</script>
</head>
<body>
 <nav class="navbar navbar-expand-md bg-dark navbar-dark text-white">
  <div class="container">
   <div class="col-md-6">
    <h1>
     <i class="fas fa-users"></i>
     회원 가입
    </h1>
   </div>
   <div id="toggle" class="navbar-collapse collapse">
    <ul class="navbar-nav">
    </ul>
    <ul class="navbar-nav ml-auto">
     <li class="nav-item dropdown mr-3">
      <c:if test="${!empty sessionScope.id}">
       <p class="nav-link dropdown-toggle" data-toggle="dropdown">
        <i class="fas fa-user mr-sm-1"></i>
        ${sessionScope.id} 님
       </p>
      </c:if>
      <div class="dropdown-menu">
       <c:choose>
        <c:when test="${!empty sessionScope.id}">
         <a class="dropdown-item" href="./BoardSelect.do?id=${sessionScope.id}">
          <i class="fas fa-user-tag"></i>
          게시판
         </a>
        </c:when>
       </c:choose>
      </div>
      <c:choose>
       <c:when test="${empty sessionScope.id}">
        <li class="nav-item">
         <a href="./LoginView.me" class="nav-link">
          <i class="fas fa-user-circle mr-sm-1"></i>
          로그인
         </a>
       </c:when>
       <c:otherwise>
        <li class="nav-item">
         <a href="./LoginView.me" class="nav-link">
          <i class="fas fa-user-times mr-sm-1"></i>
          로그아웃
         </a>
       </c:otherwise>
      </c:choose>
    </ul>
    <ul class="navbar-nav py-3 mb-3 pl-3 pr-3">
     <li><a href="./index.jsp" class="navbar-brand">
    <i class="fa fa-home"></i>홈
   </a> 
   </li>    
    </ul>
   </div>
  </div>
 </nav> 
 <header id="main-header" class="py-2 bg-secondary"> </header>
 <section id="search" class="py-3 mb-3 bg-light">
  <div class="container">
   <div class="row col-md-12">   
    <div class="col-md-6 ml-auto">
     <form action="./MemberSearch.me" method="post" name="searchForm" id="signupForm" enctype="application/x-www-form-urlencoded" >
      <fieldset>
       <div class="input-group">
        <select name="limit" id="limit" class="form-control mr-sm-1">
         <option value="10">10명씩 보기
  <option value="20">20명씩 보기
        </select>
        <input type="search" id="keyword" name="keyword" class="form-control mr-sm-1" placeholder="아이디 입력">
        <button class="btn btn-outline-success" type="submit">
         <i class="fas fa-search mr-sm-1"></i>검색
        </button>
       </div>
      </fieldset>
     </form>
    </div>
   </div>
  </div>
 </section>
 <section id="member">
  <div class="container">
   <div class="row">
    <div class="col-md-12">
     <div class="card">
      <div class="card-header">       
       <h3 class="text-muted"><i class="fas fa-users-cog mr-sm-1"></i>회원 목록</h3>
       <p align="right">
        검색된 회원:   ${listCount}명
       </p>  
      
      </div>
      <table class="table table-bordered">
       <thead class="thead-light">
        <tr class="text-center">
         <th>번호</th>
         <th>아이디</th>         
         <th>이름</th>          
         <th>이메일</th>
         <th>나이</th>
         
         <th>생일</th>
         <th>가입일</th>
         <th>성별</th>               
        </tr>
       </thead>
       <tbody>
       <c:choose>
    <c:when test="${listCount > 0}">
     <c:forEach var="arrayList" items="${arrayList}">
      <tr class="text-center">
       <td>${arrayList.num}</td>
       <td><a href="./MemberSelectDetail.me?id=${arrayList.id}">${arrayList.id}</a></td>           
         <td> 
         <c:set var="now" value="<%=new java.util.Date( )%>"/>
         <fmt:formatDate var="nowDay" value="${now}" type="date" pattern="yyyy-MM-dd"/>
<c:if test="${arrayList.joinday == nowDay}">
         <span class="badge badge-danger mr-sm-1"> new </span> </c:if>
         
         ${arrayList.name}</td>          
           <td>${arrayList.email}</td>
            <td>${arrayList.age}</td>            
       <td>${arrayList.birthday}</td>
       <td>${arrayList.joinday}</td>
        <td>${arrayList.gender}</td>
      </tr>
     </c:forEach>    
    </c:when>
    <c:otherwise>
     <tr><td colspan="8" align="center">등록된 회원이 없습니다.</td></tr> 
    </c:otherwise>
   </c:choose>        
       </tbody>
      </table>      
      <nav class="ml-4">
      <ul class="pagination justify-content-center">      
      <c:choose>
  <c:when test="${page <= 1}">
  <li class="page-item disabled">
         <a class="page-link">이전</a>
        </li>
  </c:when>
  <c:otherwise>   
   <li class="page-item">
         <a href="./MemberSearch.me?page=${page-1}&limit=${limit}&keyword=${keyword}" class="page-link">이전</a>
        </li>
  </c:otherwise>
 </c:choose> 
 <c:forEach var="start" begin="${startpage}" end="${endpage}" step="1">
  <c:choose>
   <c:when test="${start==page}">
    <li class="page-item active">
         <a class="page-link">${start}</a>
        </li>   
  </c:when>
   <c:otherwise>      
    <li class="page-item">
         <a href="./MemberSearch.me?page=${start}&limit=${limit}&keyword=${keyword}" class="page-link">${start}</a>
        </li>    
   </c:otherwise>
  </c:choose>
 </c:forEach> 
 <c:choose>
  <c:when test="${page >= maxpage}">
  <li class="page-item">
         <a class="page-link"> 다음 </a>
        </li>
        </c:when>
  <c:otherwise>
   <a href="./MemberSelect.me?page=${page+1}&limit=${limit}">
   <li class="page-item">
         <a href="./MemberSearch.me?page=${page+1}&limit=${limit}&keyword=${keyword}" class="page-link"> 다음 </a>
        </li>   
   </a>
  </c:otherwise>
 </c:choose>
 </ul>
      
      </nav>
      <nav class="navbar justify-content-end">
       <div class="btn-group">
        <button type="button" class="btn btn-outline-info mr-sm-1" onclick="location.href='./MemberInsertView.me'">
         <i class="fas fa-user-edit mr-sm-1"></i>회원 등록
        </button>
       </div>
      </nav>
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