package car.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.notice.dao.NoticeDAO;
import car.notice.dto.NoticeDTO;

public class NoticeUpdateViewController implements Controller {
    private static Log log = LogFactory.getLog(NoticeUpdateViewController.class);
    
    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        // 요청 파라미터로부터 notice_code, notice_title, notice_content 받아오기
        int notice_code = Integer.parseInt(request.getParameter("notice_code"));
        String notice_title = request.getParameter("notice_title");
        String notice_content = request.getParameter("notice_content");
        
        // 로그 출력
        log.info("NoticeUpdateViewController 실행됨. notice_code: " + notice_code);
        log.info("NoticeUpdateViewController 실행됨. notice_title: " + notice_title);
        log.info("NoticeUpdateViewController 실행됨. notice_content: " + notice_content);
        
        // DTO에 값 설정
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNotice_code(notice_code);
        noticeDTO.setNotice_title(notice_title);
        noticeDTO.setNotice_content(notice_content);

        // DAO를 통해 업데이트 수행
        NoticeDAO noticeDao = new NoticeDAO();
        noticeDTO = noticeDao.noticeUpdate(noticeDTO);
        
        // 로그 출력
        log.info("NoticeUpdateViewController 실행됨. noticeDTO: " + noticeDTO);
        log.info("NoticeUpdateViewController 실행됨. 수정한 글 업데이트 완료");

        // 업데이트 뷰로 이동
        HandlerAdapter handlerAdapter = new HandlerAdapter();
        handlerAdapter.setPath("/WEB-INF/view/notice/notice_update_view.jsp");
        return handlerAdapter;
    }
}


