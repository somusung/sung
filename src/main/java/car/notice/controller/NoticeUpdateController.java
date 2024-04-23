
package car.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.notice.dao.NoticeDAO;
import car.notice.dto.NoticeDTO;

public class NoticeUpdateController implements Controller {
    private static Log log = LogFactory.getLog(NoticeUpdateController.class);
    
    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
    
    	int notice_code = Integer.parseInt(request.getParameter("notice_code"));
        log.info(notice_code);
        NoticeDAO noticeDao = new NoticeDAO();
        NoticeDTO noticeDTO = new NoticeDTO();  
        noticeDTO = noticeDao.noticeSelect(notice_code);
        request.setAttribute("noticeDTO", noticeDTO);
        HandlerAdapter handlerAdapter = new HandlerAdapter();
        log.info("특정 글 조회");

        handlerAdapter.setPath("/WEB-INF/view/notice/notice_update.jsp");
        return handlerAdapter;
    }
}

