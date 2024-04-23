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

public class FaqSelectDetailController implements Controller {
    private static Log log = LogFactory.getLog(FaqSelectDetailController.class);

    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션에서 현재 로그인한 회원의 회원 코드를 가져옴
        HttpSession session = request.getSession();
        session.setAttribute("member_code", 5); // 임의의 회원 코드로 설정
        session.setAttribute("member_rating", "user"); // 관리자로 설정

        Integer member_code = (Integer) session.getAttribute("member_code");
        String member_rating = (String) session.getAttribute("member_rating");
        // 세션에서 회원 코드가 없는 경우 로그인 페이지로 리다이렉트 또는 에러 메시지를 표시하는 등의 처리 추가
        if (member_code == null) {
            log.error("회원 코드를 찾을 수 없습니다. 로그인이 필요합니다.");
            // 로그인 페이지로 리다이렉트 또는 에러 메시지를 표시하는 등의 처리 추가
            // 예를 들어, response.sendRedirect("login.jsp");
            // 또는 request.setAttribute("errorMessage", "로그인이 필요합니다.");
            // 그 후에 적절한 뷰로 포워드
            // return handlerAdapter.setPath("/WEB-INF/view/login.jsp");
        }

        String faqCodeParam = request.getParameter("faq_code");
        int faq_code = 0; // 기본값 설정

        if (faqCodeParam != null && !faqCodeParam.isEmpty()) {
            faq_code = Integer.parseInt(faqCodeParam);
        } else {
            log.error("faq_code 파라미터가 없습니다.");
            // 처리할 수 있는 방법에 따라서 적절한 에러 처리를 해주시면 됩니다.
        }

        log.info(faq_code);

        FaqDAO faqDao = new FaqDAO();
        FaqDTO faqDTO = new FaqDTO();

        // 특정 게시글의 상세 정보를 조회할 때 세션에 저장된 회원 코드를 전달
        faqDTO = faqDao.faqSelect(faq_code, member_code);

        log.info(faqDTO);
        request.setAttribute("faqDTO", faqDTO);
        request.setAttribute("faq_code", faq_code);

        HandlerAdapter handlerAdapter = new HandlerAdapter();
        log.info("특정 게시글 조회");

        handlerAdapter.setPath("/WEB-INF/view/faq/faq_select_detail_view.jsp");
        return handlerAdapter;
    }
}




