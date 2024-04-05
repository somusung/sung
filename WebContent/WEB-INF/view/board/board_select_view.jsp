<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${empty sessionScope.id}">
 <script type="text/javascript">
		location.href = "./LoginView.me"
	</script>
</c:if>
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
</head>
<body>
 <nav class="navbar navbar-expand-md bg-dark navbar-dark text-white">
  <div class="container">
   <div class="col-md-6">
    <h1>
     <i class="fas fa-user-tag"></i>
     게시판
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
        ${sessionScope.name} 님
       </p>
      </c:if>
      <div class="dropdown-menu">
       <c:choose>
        <c:when test="${!empty sessionScope.id}">
         <a class="dropdown-item" href="./MemberSelectDetail.me?id=${sessionScope.id}">
          <i class="fas fa-user-shield"></i>
          회원 정보
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
     <li> <a href="./index.jsp" class="navbar-brand text-white">
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
     <form action="./BoardSearchSelect.do" method="post" name="search" id="signupForm" enctype="application/x-www-form-urlencoded">
      <fieldset>
       <div class="input-group">
        <label for="keyword"></label>
        <select name="keyfield" class="form-control mr-sm-1">
         <%--해당 항목을 기본 선택으로 지정하여 검색한다.--%>
         <option value="all" selected="selected">전체 검색</option>
         <option value="subject" <c:if test="${'subject' == keyfield}">selected="selected"</c:if>>제목</option>
         <option value="name" <c:if test="${'subject' == keyfield}">selected="selected"</c:if>>작성자</option>
         <option value="content" <c:if test="${'subject' == keyfield}">selected="selected"</c:if>>내용</option>
        </select>
        <input type="search" id="keyword" name="keyword" class="form-control mr-sm-1" placeholder="검색어 입력">
        <button class="btn btn-outline-success" type="submit">
         <i class="fas fa-search mr-sm-1"></i>
         검색
        </button>
       </div>
      </fieldset>
     </form>
    </div>
   </div>
  </div>
 </section>
 <section id="board">
  <div class="container">
   <div class="row">
    <div class="col-md-12">
     <div class="card">
      <div class="card-header">
       <h3 class="text-muted">
        <i class="fas fa-file-alt mr-sm-1"></i>
        글 목록
       </h3>
       <p align="right">
        <%--전체 글의 개수를 호출한다.--%>
        전체 글:
        <strong>${listcount}</strong>
        개
       </p>
      </div>
      <table class="table table-hover">
       <c:choose>
        <c:when test="${listcount>0}">
         <thead class="thead-light">
          <tr class="text-center">
           <th>번호</th>
           <th>제목</th>
           <th>작성자</th>
           <th>작성일</th>
           <th>조회수</th>
          </tr>
         </thead>
         <c:forEach var="board" items="${boardList}">
          <tbody>
           <tr class="text-center">
            <td>${board.num}</td>
            <td>
             <c:if test="${board.answerlev>0}">
              <c:forEach var="j" begin="0" end="${board.answerlev-1}" step="1">
               <span class="badge badge badge-success mr-sm-1"> 답글 </span>
              </c:forEach>
             </c:if>
             <%-- 글 제목을 클릭했을 때 글 내용 보기 요청한다.--%>
             <a href="./BoardSelectDetail.do?num=${board.num}">${board.subject} </a>
            </td>
            <td>
             <c:set var="now" value="<%=new java.util.Date( )%>" />
             <fmt:formatDate var="nowDay" value="${now}" type="date" pattern="yyyy-MM-dd" />
             <c:if test="${board.writeday == nowDay}">
              <span class="badge badge-danger mr-sm-1"> new </span>
             </c:if>
             ${board.name}
            </td>
            <td>${board.writeday}</td>
            <td>${board.readcount}</td>
           </tr>
          </tbody>
         </c:forEach>
        </c:when>
       </c:choose>
      </table>
      <table class="table table-hover">
       <tbody>
       </tbody>
       <%-- 등록된 글이 없을 때 출력한다.--%>
       <c:if test="${searchlistcount==0}">
        <tr>
         <td colspan="4"></td>
         <td>등록된 글이 없습니다.</td>
        </tr>
       </c:if>
       <tr>
      </table>
      <nav class="ml-4">
       <ul class="pagination justify-content-center">
        <c:choose>
         <c:when test="${page <= 1}">
          <li class="page-item disabled">
           <a class="page-link"> 이전 </a>
          </li>
         </c:when>
         <c:otherwise>
          <li class="page-item">
           <a href="./BoardSelect.do?page=${page-1}" class="page-link"> 이전 </a>
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
            <a href="./BoardSelect.do?page=${start}" class="page-link">${start} </a>
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
          <a href="./BoardSelect.do?page=${page+1}">
           <li class="page-item">
            <a class="page-link"> 다음 </a>
           </li>
          </a>
         </c:otherwise>
        </c:choose>
       </ul>
      </nav>
      <nav class="navbar justify-content-end">
       <div class="btn-group">
        <button type="button" class="btn btn-outline-info" onclick="location.href='./BoardInsertView.do'">
         <i class="fas fa-edit mr-sm-1 mr-sm-1"></i>
         글 등록
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
      <img src="./img/log.png" class="mr-sm-1">
      어깨동무 게시판 &copy; 2000
     </p>
    </div>
   </div>
  </div>
 </footer>
</body>
</html>