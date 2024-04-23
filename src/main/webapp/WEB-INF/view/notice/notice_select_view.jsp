<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지 사항 목록</title>
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
                <h1>이벤트 및 공지사항 관리</h1>
            </div>
            <div class="col-md-6 text-right">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">공지사항</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn-warning" href="./NoticeSelectEvent.so">이벤트</a>
                    </li>
                    <c:if test="${member_rating eq 'admin'}">
                        <li>
                            <a href="./NoticeInsertView.so" class="btn btn-success btn-block">글 작성</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</header>
<section class="py-4 mb-4 bg-light">
</section>
<section id="notices">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5>공지사항 목록</h5>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead class="thead-light">
                            <tr class="text-center">
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성 날짜</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="arrayList" items="${arrayList}">
                                <tr class="text-center">
                                    <td>${arrayList.notice_code}</td>
                                    <td><a href="./NoticeSelectDetail.so?notice_code=${arrayList.notice_code}">${arrayList.notice_title}</a></td>
                                    <td>${arrayList.notice_date}</td>                              
                                </tr>
                            </c:forEach>
                            <c:if test="${empty arrayList}">
                                <tr>
                                    <td colspan="4">등록된 공지사항이 없습니다.</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                        <div>
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>


