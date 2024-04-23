package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;


public class FaqAnswerSelectDetailController implements Controller {
    private static Log log = LogFactory.getLog(FaqAnswerSelectDetailController.class);

        @Override
        public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
            // 요청 파라미터로부터 댓글 코드 받아오기
            int faq_answer_code = Integer.parseInt(request.getParameter("faq_answer_code"));

            // 로그 출력
            log.info("댓글 코드: " + faq_answer_code);

            // DAO를 통해 댓글 조회
            FaqDAO faqDao = new FaqDAO();
            FaqDTO faqDTO = faqDao.faqAnswerSelect(faq_answer_code);

            // 댓글 정보를 request에 설정
            request.setAttribute("faqDTO", faqDTO);

            // 댓글 수정 입력 폼으로 이동
            HandlerAdapter handlerAdapter = new HandlerAdapter();
            log.info("댓글 수정 입력 폼으로 이동");
            handlerAdapter.setPath("/WEB-INF/view/faq/faq_answer_update.jsp");
            return handlerAdapter;
        }
    }



