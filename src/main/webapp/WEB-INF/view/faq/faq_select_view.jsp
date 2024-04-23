<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 목록</title>
    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
    <link rel="stylesheet" type="text/css" href="./css/global.css">
    <script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
</head>
<body>
<header id="main-header" class="py-2 btn-dark text-white">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h1>주차관리 질문 게시판</h1>
            </div>
            <div class="col-md-3">
                <!-- 검색 창 -->
                <form action="./FaqSearch.so" method="get" class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="제목 검색" aria-label="Search" name="faq_title">
                    <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">검색</button>
                </form>
            </div>
            <div class="col-md-3 text-right">
                <!-- 글 작성 버튼 -->
                <a href="./FaqInsertView.so" class="btn btn-primary">글쓰기</a>
                <!-- 이용약관 버튼 추가 -->
                <a href="./TermSelect.so" class="btn btn-secondary">이용약관</a>
                <!-- 자주 묻는 질문 모달 버튼 -->
                <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#termsModal">자주 묻는 질문</button>
            </div>
        </div>
    </div>
</header>
<section class="py-4 mb-4 bg-light">
</section>
<section id="boards">
    <div class="container">
        <!-- 게시판 목록 표시 -->
        <table class="table table-hover">
            <thead class="thead-light">
            <tr class="text-center">
                <th>번호</th>
                <th>제목</th>
                <th>작성일</th>
            </tr>
            </thead>
            <tbody>
             <c:forEach var="arrayList" items="${arrayList}">
                <tr class="text-center">
                    <td>${arrayList.faq_code}</td>
                    <td><a href="./FaqSelectDetail.so?faq_code=${arrayList.faq_code}">${arrayList.faq_title}</a></td>
                    <td>${arrayList.faq_question_date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>
<footer class="footer mt-auto py-3 bg-light">
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-center"> 
            </div>
        </div>
    </div>
</footer>

<!-- 이용약관 모달 -->
<div class="modal fade" id="termsModal" tabindex="-1" role="dialog" aria-labelledby="termsModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="termsModalLabel">자주 묻는 질문</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p> 자주 묻는 질문 리스트</p>
        <p> 질문 1</p>
        <p> 질문 1의 답변</p>
        <p> 질문 2</p>
        <p> 질문 2의 답변</p>
        <p> 질문 3</p>
        <p> 질문 3의 답변</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>







