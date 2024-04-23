package car.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.notice.dao.NoticeDAO;
import car.notice.dto.NoticeDTO;

public class NoticeSelectDetailController implements Controller{
	private static Log log = LogFactory.getLog(NoticeSelectDetailController.class);
	 @Override
	 public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		 HttpSession session = request.getSession();
	     session.setAttribute("member_rating", "admin"); // 관리자 권한으로 설정
	     String member_rating = (String) session.getAttribute("member_rating");
	 int notice_code = Integer.parseInt(request.getParameter("notice_code"));
	 log.info(notice_code);
	 NoticeDAO noticeDao = new NoticeDAO( );
	 NoticeDTO noticeDTO = new NoticeDTO( );
	 noticeDTO = noticeDao.noticeSelect(notice_code);
	 log.info(noticeDTO);
	 request.setAttribute("noticeDTO", noticeDTO);
	 HandlerAdapter handlerAdapter = new HandlerAdapter( );
	 log.info("특정 공지사항 조회");
	 handlerAdapter.setPath("/WEB-INF/view/notice/notice_select_detail_view.jsp");
	 return handlerAdapter;
	 }

}
