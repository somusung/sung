package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;

public class FaqAnswerDeleteViewController implements Controller{
	private static final Log log = LogFactory.getLog(FaqAnswerDeleteController.class);
	  @Override
	    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	        // 요청 파라미터로부터 댓글 코드와 수정할 내용 받아오기
	        int faq_answer_code = Integer.parseInt(request.getParameter("faq_answer_code"));
	        int faq_code = Integer.parseInt(request.getParameter("faq_code")); // 요청 파라미터에서 faq_code 가져오기
	        
	        // 로그 출력
	        log.info("댓글 삭제 컨트롤러 실행됨. faq_answer_code: " + faq_answer_code);
	        log.info("댓글 삭제 컨트롤러 실행됨. faq_code: " + faq_code); // 로그에 faq_code 출력하기
	        
	        // DTO에 값 설정
	        FaqDTO faqDTO = new FaqDTO();
	        faqDTO.setFaq_answer_code(faq_answer_code);
	        faqDTO.setFaq_code(faq_code); // faq_code 설정하기

	        // DAO를 통해 댓글 업데이트 수행
	        FaqDAO faqDao = new FaqDAO();
	        faqDTO = faqDao.faqAnswerDelete(faq_answer_code);
	        
	        // 로그 출력
	        log.info("댓글 삭제 컨트롤러 실행됨. faqDTO: " + faqDTO);
	        log.info("댓글 삭제 컨트롤러 실행됨. 댓글 삭제 업데이트 완료");

	        // 수정된 댓글이 속한 게시물의 코드를 JSP로 전달
	        request.setAttribute("faq_code", faq_code);

	        HandlerAdapter handlerAdapter = new HandlerAdapter();
	        handlerAdapter.setPath("/WEB-INF/view/faq/faq_answer_delete_view.jsp");
	        return handlerAdapter;
	    } }
