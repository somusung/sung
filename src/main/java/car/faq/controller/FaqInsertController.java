package car.faq.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;


public class FaqInsertController implements Controller {
	private static Log log = LogFactory.getLog(FaqInsertController.class);
	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	    // 세션에서 멤버 코드 가져오기
	    HttpSession session = request.getSession();
	    session.setAttribute("member_code", 5); 
	    int member_code = (int) session.getAttribute("member_code");

	    String faq_title = request.getParameter("faq_title");
	    String faq_question = request.getParameter("faq_question");
	    String faq_question_date = request.getParameter("faq_question_date");

	    FaqDAO faqDao = new FaqDAO();
	    FaqDTO faqDTO = new FaqDTO();
	    ArrayList<FaqDTO> arrayList = new ArrayList<FaqDTO>();

	    arrayList = faqDao.faqSelectAll();
	    log.info(arrayList);
	    request.setAttribute("arrayList", arrayList);

	    faqDTO.setFaq_title(faq_title);
	    faqDTO.setFaq_question(faq_question);
	    faqDTO.setFaq_question_date(faq_question_date);
	    // 멤버 코드 설정
	    faqDTO.setMember_code(member_code);

	    faqDTO = faqDao.faqInsert(faqDTO, member_code);
	    log.info(faqDTO);
	    request.setAttribute("faqDTO", faqDTO);
	    log.info("게시글 정보 등록");

	    HandlerAdapter handlerAdapter = new HandlerAdapter();
	    handlerAdapter.setPath("/WEB-INF/view/faq/faq_insert_view.jsp");
	    return handlerAdapter;
	}

}










