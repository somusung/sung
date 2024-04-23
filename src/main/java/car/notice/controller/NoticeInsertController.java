package car.notice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.notice.dao.NoticeDAO;
import car.notice.dto.NoticeDTO;

public class NoticeInsertController implements Controller {
    private static Log log = LogFactory.getLog(NoticeInsertController.class);

    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        String notice_title = request.getParameter("notice_title");
        String notice_content = request.getParameter("notice_content");
        String notice_date =  request.getParameter("notice_date");

        // 라디오 버튼의 값을 가져옴
        String notice_type = request.getParameter("notice_type");

        NoticeDAO noticeDao = new NoticeDAO();
        NoticeDTO noticeDTO = new NoticeDTO();
        ArrayList<NoticeDTO> arrayList = new ArrayList<NoticeDTO>();

        arrayList = noticeDao.noticeSelectAll();
        log.info(arrayList);
        request.setAttribute("arrayList", arrayList);

        noticeDTO.setNotice_title(notice_title);
        noticeDTO.setNotice_content(notice_content);
        noticeDTO.setNotice_date(notice_date);

        // 라디오 버튼의 값을 모델 객체에 설정
        noticeDTO.setNotice_type(notice_type);

        noticeDTO = noticeDao.noticeInsert(noticeDTO);
        log.info(noticeDTO);
        request.setAttribute("noticeDTO", noticeDTO);
        log.info("글 정보 등록");

        HandlerAdapter handlerAdapter = new HandlerAdapter();
        handlerAdapter.setPath("/WEB-INF/view/notice/notice_insert_view.jsp");
        return handlerAdapter;
    }
}
