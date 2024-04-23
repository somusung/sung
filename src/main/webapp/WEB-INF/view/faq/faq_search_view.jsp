<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 검색 결과</title>
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
                <h1>게시판</h1>
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
                <a href="./FaqSelect.so" class="btn btn-primary">홈으로 이동</a>
            </div>
        </div>
    </div>
</header>
<section class="py-4 mb-4 bg-light">
</section>
<section id="boards">
    <div class="container">
        <!-- 검색된 게시글 목록 표시 -->
        <table class="table table-hover">
            <thead class="thead-light">
                <tr class="text-center">
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="faq" items="${searchresult}">
                    <tr class="text-center">
                        <td>${faq.faq_code}</td>
                        <td><a href="./FaqSelectDetail.so?faq_code=${faq.faq_code}">${faq.faq_title}</a></td>
                        <td>${faq.faq_question_date}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!-- 검색된 게시글이 없을 때 메시지 표시 -->
        <c:if test="${empty searchresult}">
            <div class="alert alert-warning" role="alert">
                입력하신 내용으로 검색한 글이 없습니다.
            </div>
        </c:if>
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
</body>
</html>
