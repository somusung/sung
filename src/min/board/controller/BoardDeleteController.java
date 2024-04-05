package min.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardDeleteController implements Controller {
	private static Log log = LogFactory.getLog(BoardDeleteController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {

		HandlerAdapter handlerAdapter = new HandlerAdapter( );

		boolean result = false;
		boolean usercheck = false;
		String id = request.getParameter("id");
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO boardDAO = new BoardDAO( );

		usercheck = boardDAO.boardId(num, id);

		if(usercheck == false) {
			handlerAdapter.setPath("/WEB-INF/view/board/board_delete_view.jsp");
			return handlerAdapter;
		}
		// 게시글 삭제를 처리한다.
		result = boardDAO.boardDelete(num);
		log.info("게시글 삭제 확인 - " + result);

		// 리다이렉트로 파라미트를 전송한다.
		handlerAdapter.setRedirect(true);
		handlerAdapter.setPath("./BoardSelect.do");
		return handlerAdapter;

	}
}