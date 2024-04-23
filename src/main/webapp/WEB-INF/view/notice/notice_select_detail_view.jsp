<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 상세 내용</title>
    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
    <link rel="stylesheet" type="text/css" href="./css/global.css">
    <script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
</head>
<body>
<header id="main-header" class="py-2 btn-dark text-white">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h1>공지사항 상세 내용</h1>
            </div>
        </div>
    </div>
</header>
<section class="py-4 mb-4 bg-light"></section>
<section id="notice-detail">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${noticeDTO.notice_title}</h5>
                        <p class="card-text">${noticeDTO.notice_content}</p>
                        <div class="text-center">
                            <c:if test="${member_rating eq 'admin'}">
                                <a href="./NoticeUpdate.so?notice_code=${noticeDTO.notice_code}" class="btn btn-warning">수정</a>
                                <a href="./NoticeDeleteView.so?notice_code=${noticeDTO.notice_code}" class="btn btn-danger">삭제</a>
                            </c:if>
                        </div>
                        <div class="row mt-4">
                            <div class="col-md-12">
                                <a href="./NoticeSelect.so" class="btn btn-primary btn-block">공지사항 목록으로 돌아가기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>

