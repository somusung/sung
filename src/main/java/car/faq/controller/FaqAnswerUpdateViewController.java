package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;

public class FaqAnswerUpdateViewController implements Controller {
    private static Log log = LogFactory.getLog(FaqAnswerUpdateViewController.class);

    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        // 요청 파라미터로부터 댓글 코드와 수정할 내용 받아오기
        int faq_answer_code = Integer.parseInt(request.getParameter("faq_answer_code"));
        String faq_answer = request.getParameter("faq_answer");
        int faq_code = Integer.parseInt(request.getParameter("faq_code")); // 요청 파라미터에서 faq_code 가져오기
        
        // 로그 출력
        log.info("댓글 수정 컨트롤러 실행됨. faq_answer_code: " + faq_answer_code);
        log.info("댓글 수정 컨트롤러 실행됨. faq_answer: " + faq_answer);
        log.info("댓글 수정 컨트롤러 실행됨. faq_code: " + faq_code); // 로그에 faq_code 출력하기
        
        // DTO에 값 설정
        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setFaq_answer_code(faq_answer_code);
        faqDTO.setFaq_answer(faq_answer);
        faqDTO.setFaq_code(faq_code); // faq_code 설정하기

        // DAO를 통해 댓글 업데이트 수행
        FaqDAO faqDao = new FaqDAO();
        faqDTO = faqDao.faqAnswerUpdate(faqDTO);
        
        // 로그 출력
        log.info("댓글 수정 컨트롤러 실행됨. faqDTO: " + faqDTO);
        log.info("댓글 수정 컨트롤러 실행됨. 수정한 댓글 업데이트 완료");

        // 업데이트 뷰로 이동
        request.setAttribute("message", "댓글 내용을 수정하였습니다.");

        // 수정된 댓글이 속한 게시물의 코드를 JSP로 전달
        request.setAttribute("faq_code", faq_code);

        HandlerAdapter handlerAdapter = new HandlerAdapter();
        handlerAdapter.setPath("/WEB-INF/view/faq/faq_answer_update_view.jsp");
        return handlerAdapter;
    } }


