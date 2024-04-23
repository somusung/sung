package car.comment.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;
import car.comment.dto.CommentDTO;
import car.faq.controller.FaqInsertController;

public class CommentInsertController implements Controller{
	private static Log log = LogFactory.getLog(FaqInsertController.class);
	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		 HttpSession session = request.getSession();
		 session.setAttribute("member_code", 3); 
		 Integer member_code = (Integer) session.getAttribute("member_code");

		    // 세션에서 회원 코드가 없는 경우 로그인 페이지로 리다이렉트 또는 에러 메시지를 표시하는 등의 처리 추가
		    if (member_code == null) {
		        log.error("회원 코드를 찾을 수 없습니다. 로그인이 필요합니다.");
		        // 로그인 페이지로 리다이렉트 또는 에러 메시지를 표시하는 등의 처리 추가
		        // 예를 들어, response.sendRedirect("login.jsp");
		        // 또는 request.setAttribute("errorMessage", "로그인이 필요합니다.");
		        // 그 후에 적절한 뷰로 포워드
		        // return handlerAdapter.setPath("/WEB-INF/view/login.jsp");
		    }
	    String comment_content = request.getParameter("comment_content");
	    String comment_date = request.getParameter("comment_date");
	    int parking_code = 3; 

	    CommentDAO commentDao = new CommentDAO();
	    CommentDTO commentDTO = new CommentDTO();
	    ArrayList<CommentDTO> arrayList = new ArrayList<CommentDTO>();

	    // 이전에 등록된 댓글 목록을 가져오는 부분
	    arrayList = commentDao.commentSelectAll(parking_code);
	    request.setAttribute("arrayList", arrayList);

	    // 댓글 내용과 작성일을 DTO에 설정
	    commentDTO.setComment_content(comment_content);
	    commentDTO.setComment_date(comment_date);

	    // 댓글을 DB에 등록하는 부분
	    commentDTO = commentDao.commentInsert(commentDTO, member_code);
	    request.setAttribute("commentDTO", commentDTO);

	    // 등록된 댓글 정보를 확인하는 로그
	    log.info("댓글 정보 등록: " + commentDTO);

	    HandlerAdapter handlerAdapter = new HandlerAdapter();
	    handlerAdapter.setPath("/WEB-INF/view/comment/comment_insert_view.jsp");
	    return handlerAdapter;
	}
}
