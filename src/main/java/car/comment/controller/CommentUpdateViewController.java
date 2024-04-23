package car.comment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;
import car.comment.dto.CommentDTO;

public class CommentUpdateViewController implements Controller {
	 private static Log log = LogFactory.getLog(CommentUpdateViewController.class);

	    @Override
	    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	        // 요청 파라미터로부터 댓글 코드와 수정할 내용 받아오기
	        int comment_code = Integer.parseInt(request.getParameter("comment_code"));
	        String comment_content = request.getParameter("comment_content");
	        int parking_code = Integer.parseInt(request.getParameter("parking_code")); // 요청 파라미터에서 faq_code 가져오기
	        
	        // 로그 출력
	        log.info("댓글 수정 컨트롤러 실행됨. faq_answer_code: " + comment_code);
	        log.info("댓글 수정 컨트롤러 실행됨. faq_answer: " + comment_content);
	        log.info("댓글 수정 컨트롤러 실행됨. faq_code: " + parking_code); // 로그에 faq_code 출력하기
	        
	        // DTO에 값 설정
	        CommentDTO commentDTO = new CommentDTO();
	        commentDTO.setComment_code(comment_code);
	        commentDTO.setComment_content(comment_content);
	        commentDTO.setParking_code(parking_code); // faq_code 설정하기

	        // DAO를 통해 댓글 업데이트 수행
	        CommentDAO commentDao = new CommentDAO();
	        commentDTO = commentDao.commentUpdate(commentDTO);
	        
	        // 로그 출력
	        log.info("댓글 수정 컨트롤러 실행됨. faqDTO: " + commentDTO);
	        log.info("댓글 수정 컨트롤러 실행됨. 수정한 댓글 업데이트 완료");

	        // 업데이트 뷰로 이동
	        request.setAttribute("message", "댓글 내용을 수정하였습니다.");

	        // 수정된 댓글이 속한 게시물의 코드를 JSP로 전달
	        request.setAttribute("faq_code", parking_code);

	        HandlerAdapter handlerAdapter = new HandlerAdapter();
	        handlerAdapter.setPath("/WEB-INF/view/comment/comment_update_view.jsp");
	        return handlerAdapter;
	    } }
