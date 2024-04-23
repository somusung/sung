package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;

public class FaqAnswerUpdateController implements Controller {
    private static Log log = LogFactory.getLog(FaqAnswerUpdateController.class);

    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        int faq_answer_code = Integer.parseInt(request.getParameter("faq_answer_code"));
        log.info("댓글 코드: " + faq_answer_code);

        // 게시물 코드 파라미터가 null이 아닌 경우에만 게시물 코드 변수에 할당
        Integer faq_code = null;
        String faqCodeParam = request.getParameter("faq_code");
        if (faqCodeParam != null && !faqCodeParam.isEmpty()) {
            faq_code = Integer.parseInt(faqCodeParam);
        }

        // DAO를 통해 댓글 조회
        FaqDAO faqDao = new FaqDAO();
        FaqDTO faqDTO = faqDao.faqAnswerSelect(faq_answer_code);

        // 댓글 정보와 게시물 코드를 request에 설정
        request.setAttribute("faqDTO", faqDTO);
        request.setAttribute("faq_code", faq_code);

        // 댓글 수정 페이지로 이동
        HandlerAdapter handlerAdapter = new HandlerAdapter();
        log.info("댓글 수정 페이지 조회");
        handlerAdapter.setPath("/WEB-INF/view/faq/faq_answer_update.jsp");
        return handlerAdapter;
    }
}



