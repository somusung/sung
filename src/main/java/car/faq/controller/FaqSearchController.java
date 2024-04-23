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

public class FaqSearchController implements Controller {
    private static Log log = LogFactory.getLog(FaqSearchController.class);

    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        String faq_title = (String) request.getParameter("faq_title");
        HandlerAdapter handlerAdapter = new HandlerAdapter();

        if (faq_title == null || faq_title.trim().isEmpty()) {
            // 검색어가 작성되지 않았을 경우 에러 메시지를 request에 저장
            request.setAttribute("errorMessage", "게시글 제목을 입력해주세요.");
        } else {
            // 세션에 검색어 저장
            HttpSession session = request.getSession();
            session.setAttribute("faq_title", faq_title);

            FaqDAO faqDAO = new FaqDAO();

            // 제목을 기반으로 FAQ 검색
            ArrayList<FaqDTO> searchresult = faqDAO.faqSearch(faq_title);

            if (!searchresult.isEmpty()) {
                // 검색된 FAQ가 있을 경우 결과를 request에 저장
                request.setAttribute("searchresult", searchresult);
                log.info("검색된 FAQ 수: " + searchresult.size());
            } else {
                // 검색된 FAQ가 없을 경우 에러 메시지를 request에 저장
                request.setAttribute("errorMessage", "해당하는 게시글을 찾을 수 없습니다.");
            }
        }

        // 검색 결과를 보여줄 뷰 페이지로 이동
        handlerAdapter.setPath("/WEB-INF/view/faq/faq_search_view.jsp");
        return handlerAdapter;
    }
}



