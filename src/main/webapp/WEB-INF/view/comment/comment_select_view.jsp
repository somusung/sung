<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>리뷰 댓글 목록</title>
    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
    <link rel="stylesheet" type="text/css" href="./css/global.css">
    <script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
    <script>
        function deleteComment(comment_code) {
            // 확인을 누르면 삭제 요청을 서버로 전송하고 페이지를 새로고침합니다.
            if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
                window.location.href = "./CommentDelete.so?comment_code=" + comment_code;
            }
        }

        function goToAddComment() {
            window.location.href = "./CommentInsertView.so";
        }
    </script>
</head>
<body>
<header id="main-header" class="py-2 btn-dark text-white">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h1>댓글 목록</h1>
            </div>
            <div class="col-md-6 text-right">
                <button onclick="goToAddComment()" class="btn btn-primary">댓글 작성</button>
            </div>
        </div>
    </div>
</header>
<section id="comments">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5>댓글 목록</h5>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead class="thead-light">
                            <tr class="text-center">
                                <th>댓글 코드</th>
                                <th>댓글 내용</th>
                                <th>작성 날짜</th>
                                <th>액션</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="arrayList" items="${arrayList}">
                               <tr class="text-center">
    <td>${arrayList.comment_code}</td>
    <td>${arrayList.comment_content}</td>
    <td>${arrayList.comment_date}</td>
    <td>
        <c:if test="${arrayList.member_code eq sessionScope.member_code}">
            <a href="./CommentUpdate.so?comment_code=${arrayList.comment_code}" class="btn btn-warning">수정</a>
            <button onclick="deleteComment(${arrayList.comment_code})" class="btn btn-danger">삭제</button>
        </c:if>
    </td>
</tr>
                            </c:forEach>
                            <c:if test="${empty arrayList}">
                                <tr>
                                    <td colspan="4">등록된 댓글이 없습니다.</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>




