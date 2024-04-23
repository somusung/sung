package car.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.notice.dao.NoticeDAO;
import car.notice.dto.NoticeDTO;

public class NoticeDeleteController implements Controller {
	 private static Log log = LogFactory.getLog(NoticeDeleteController.class);
	 @Override
	 public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int notice_code = Integer.parseInt(request.getParameter("notice_code"));
	 log.info(notice_code);
	 NoticeDAO noticeDao = new NoticeDAO( );
	 NoticeDTO noticeDTO = new NoticeDTO( );

	 noticeDTO.setNotice_code(notice_code);
	 //속성에 DTO 객체를 속성값으로 저장한다.
	 request.setAttribute("noticeDTO", noticeDTO);
	 //삭제한 데이터를 반환하여 deptDTO 인스턴스로 할당하고 할당하지 않으면 null 값이 반환된다.
	 noticeDTO = noticeDao.noticeDelete(notice_code);
	 log.info(noticeDTO);
	 HandlerAdapter handlerAdapter = new HandlerAdapter( );
	 //포워드로 파라미터를 전송한다.
	 handlerAdapter.setPath("/WEB-INF/view/notice/notice_delete_view.jsp");
	 return handlerAdapter;
	 }

}
