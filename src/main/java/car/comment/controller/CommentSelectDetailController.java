package car.comment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;
import car.comment.dto.CommentDTO;


public class CommentSelectDetailController implements Controller {
	private static Log log = LogFactory.getLog(CommentSelectDetailController.class);
	 @Override
	 public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int comment_code = Integer.parseInt(request.getParameter("comment_code"));
	 log.info(comment_code);
	 CommentDAO commentDao = new CommentDAO( );
	 CommentDTO commentDTO = new CommentDTO( );
	 commentDTO = commentDao.commentSelect(comment_code);
	 log.info(commentDTO);
	 request.setAttribute("commentDTO", commentDTO);
	 HandlerAdapter handlerAdapter = new HandlerAdapter( );
	 log.info("특정 공지사항 조회");
	 handlerAdapter.setPath("/WEB-INF/view/comment/comment_select_detail_view.jsp");
	 return handlerAdapter;
	 }


}
