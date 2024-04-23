<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>질문 게시판</title>
    <link rel="shortcut icon" href="./img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="./css/global.css">
    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min_4.5.0.css">
    <script src="./js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="./js/bootstrap.min_4.5.0.js" type="text/javascript"></script>
    <script src="./js/popper.min.js" type="text/javascript"></script>
    <script>
        function deleteFaq(faq_code) {
            if (confirm("정말로 이 게시물을 삭제하시겠습니까?")) {
                // 확인을 누르면 해당 FAQ 게시물을 삭제하는 서버로 요청을 전송합니다.
                window.location.href = "./FaqDelete.so?faq_code=" + faq_code;
            }
        }
    </script>
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
    <ul class="navbar-nav ml-auto py-3 mb-3 pl-3 pr-3">
    <li>
        <a href="./index.jsp" class="navbar-brand text-white">
            <i class="fa fa-home"></i>홈
        </a>
    </li>
</ul>
   </div>
  </div>
 </nav> 
 <header id="main-header" class="py-2 bg-secondary"> </header>
 <section class="py-3 mb-3 bg-light">
  <div class="container">
   <div class="row col-md-12"></div>
  </div>
 </section>
 <section id="board">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h3 class="text-muted">
                            <i class="fas fa-sticky-note mr-sm-1"></i>글 내용
                        </h3>
                        <c:choose>
                            <c:when test="${sessionScope.member_code eq faqDTO.member_code}">
                                <div class="form-group row">
                                    <div class="col-10 offset-2 pl-0 py-1">
                                        <a href="./FaqUpdate.so?faq_code=${faqDTO.faq_code}" class="btn btn-outline-secondary mr-1">
                                            <i class="fas fa-edit"></i> 수정
                                        </a>
                                        <!-- 삭제 버튼에 onclick 이벤트 추가 -->
                                        <button onclick="deleteFaq(${faqDTO.faq_code})" class="btn btn-outline-danger">
                                            <i class="fas fa-trash"></i> 삭제
                                        </button>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <div class="row pl-1 pr-1">
                            <div class="col col-md-10">
                                <input type="hidden" name="faq_code" value="${faqDTO.faq_code}">
                                <div class="form-group row">
                                    <label for="subject" class="col-2 col-form-label pr-0"><i class="fas fa-pen-alt mr-sm-1"></i>제목</label>
                                    <div class="col-10 pl-0  py-1">
                                        ${faqDTO.faq_title}
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="question" class="col-2 col-form-label pr-0"><i class="fas fa-edit mr-sm-1"></i>내용</label>
                                    <div class="col-10 pl-0  py-1">
                                        ${faqDTO.faq_question}
                                    </div>
                                </div>
                                <c:if test="${not empty faqDTO.faq_answer}">
    <div class="form-group row">
        <label for="answer" class="col-2 col-form-label pr-0"><i class="fas fa-comments mr-sm-1"></i>답변</label>
        <div class="col-10 pl-0 py-1">
            ${faqDTO.faq_answer}
            <span class="text-muted ml-2">작성일: ${faqDTO.faq_answer_date}</span>
            <c:choose>
                <c:when test="${sessionScope.member_rating eq 'admin'}">
                    <a href="./FaqAnswerUpdate.so?faq_answer_code=${faqDTO.faq_answer_code}" class="btn btn-outline-secondary btn-sm mr-1">
                        <i class="fas fa-edit"></i> 수정
                    </a>
                    <a href="./FaqAnswerDelete.so?faq_answer_code=${faqDTO.faq_answer_code}" class="btn btn-outline-danger btn-sm">
                        <i class="fas fa-trash"></i> 삭제
                    </a>
                </c:when>
                <c:otherwise>
                    <!-- Do nothing -->
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:if>
                                <c:if test="${empty faqDTO.faq_answer && sessionScope.member_rating eq 'admin'}">
                                    <div class="form-group row">
                                        <div class="col-12 text-right">
                                            <a href="./FaqAnswerUpdate.so?faq_answer_code=${faqDTO.faq_answer_code}" class="btn btn-primary">댓글 작성</a>
                                        </div>
                                    </div>
                                </c:if>
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





