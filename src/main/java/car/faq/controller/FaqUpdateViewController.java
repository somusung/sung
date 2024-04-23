package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;
import car.notice.controller.NoticeUpdateViewController;

public class FaqUpdateViewController implements Controller {
	 private static Log log = LogFactory.getLog(NoticeUpdateViewController.class);
	    
	    @Override
	    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	        // 요청 파라미터로부터 notice_code, notice_title, notice_content 받아오기
	        int faq_code = Integer.parseInt(request.getParameter("faq_code"));
	        String faq_title = request.getParameter("faq_title");
	        String faq_question = request.getParameter("faq_question");
	        
	        // 로그 출력
	        log.info("FaqUpdateViewController 실행됨. faq_code: " + faq_code);
	        log.info("FaqUpdateViewController 실행됨. faq_title: " + faq_title);
	        log.info("FaqUpdateViewController 실행됨. faq_question: " + faq_question);
	        
	        // DTO에 값 설정
	        FaqDTO faqDTO = new FaqDTO();
	        faqDTO.setFaq_code(faq_code);
	        faqDTO.setFaq_title(faq_title);
	        faqDTO.setFaq_question(faq_question);

	        // DAO를 통해 업데이트 수행
	        FaqDAO faqDao = new FaqDAO();
	        faqDTO = faqDao.faqUpdate(faqDTO);
	        
	        // 로그 출력
	        log.info("FaqUpdateViewController 실행됨. faqDTO: " + faqDTO);
	        log.info("FaqUpdateViewController 실행됨. 수정한 글 업데이트 완료");

	        // 업데이트 뷰로 이동
	        HandlerAdapter handlerAdapter = new HandlerAdapter();
	        handlerAdapter.setPath("/WEB-INF/view/faq/faq_update_view.jsp");
	        return handlerAdapter;
	    }

}
