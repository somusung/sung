package car.comment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;
import car.comment.dto.CommentDTO;



public class CommentUpdateController implements Controller{
    private static Log log = LogFactory.getLog(CommentUpdateController.class);
    
@Override
public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
    int comment_code = Integer.parseInt(request.getParameter("comment_code"));
    log.info("리뷰 댓글 코드: " + comment_code);

    // 게시물 코드 파라미터가 null이 아닌 경우에만 게시물 코드 변수에 할당
    Integer parking_code = null;
    String parkingCodeParam = request.getParameter("parking_code");
    if (parkingCodeParam != null && !parkingCodeParam.isEmpty()) {
        parking_code = Integer.parseInt(parkingCodeParam);
    }

    // DAO를 통해 댓글 조회
    CommentDAO commentDao = new CommentDAO();
    CommentDTO commentDTO = commentDao.commentSelect(comment_code);

    // 댓글 정보와 게시물 코드를 request에 설정
    request.setAttribute("commentDTO", commentDTO);
    request.setAttribute("parking_code", parking_code);

    // 댓글 수정 페이지로 이동
    HandlerAdapter handlerAdapter = new HandlerAdapter();
    log.info("댓글 수정 페이지 조회");
    handlerAdapter.setPath("/WEB-INF/view/comment/comment_update.jsp");
    return handlerAdapter;
}
}

