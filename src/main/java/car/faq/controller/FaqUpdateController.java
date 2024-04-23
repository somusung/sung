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

public class FaqUpdateController implements Controller {
	  private static Log log = LogFactory.getLog(FaqUpdateController.class);
	   

		    @Override
		    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		        HttpSession session = request.getSession();
		        Integer member_code = (Integer) session.getAttribute("member_code");

		        if (member_code == null) {
		            log.error("회원 코드를 찾을 수 없습니다. 로그인이 필요합니다.");
		            // 적절한 처리 추가: 로그인 페이지로 리다이렉트 또는 에러 메시지 표시 후 적절한 뷰로 포워드
		            // 예: response.sendRedirect("login.jsp");
		            // 또는 request.setAttribute("errorMessage", "로그인이 필요합니다.");
		            // 그 후에 적절한 뷰로 포워드
		            // return handlerAdapter.setPath("/WEB-INF/view/login.jsp");
		        }

		        String faqCodeParam = request.getParameter("faq_code");
		        if (faqCodeParam == null || faqCodeParam.isEmpty()) {
		            log.error("faq_code 파라미터가 없습니다.");
		            // 적절한 처리 추가: 에러 메시지 표시 후 적절한 뷰로 포워드
		            // 예: request.setAttribute("errorMessage", "faq_code 파라미터가 없습니다.");
		            // 그 후에 적절한 뷰로 포워드
		            // return handlerAdapter.setPath("/WEB-INF/view/error.jsp");
		        }

		        int faq_code = Integer.parseInt(faqCodeParam);
		        log.info("게시글 코드: " + faq_code);

		        FaqDAO faqDao = new FaqDAO();
		        FaqDTO faqDTO = faqDao.faqSelect(faq_code, member_code); // 수정된 부분

		        if (faqDTO == null) {
		            log.error("해당 게시글을 찾을 수 없습니다.");
		            // 적절한 처리 추가: 에러 메시지 표시 후 적절한 뷰로 포워드
		            // 예: request.setAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
		            // 그 후에 적절한 뷰로 포워드
		            // return handlerAdapter.setPath("/WEB-INF/view/error.jsp");
		        }

		        request.setAttribute("faqDTO", faqDTO);
		        HandlerAdapter handlerAdapter = new HandlerAdapter();
		        log.info("게시글 조회 성공");

		        handlerAdapter.setPath("/WEB-INF/view/faq/faq_update.jsp");
		        return handlerAdapter;
		    }
		}

