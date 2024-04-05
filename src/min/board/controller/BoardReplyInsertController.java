package min.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import min.board.dto.BoardDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardReplyInsertController implements Controller {
	private static Log log = LogFactory.getLog(BoardReplyInsertController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		int num = Integer.parseInt(request.getParameter("num"));
		boardDTO = boardDAO.boardSelect(num);
		log.info("DTO 확인 - " + boardDTO);
		// 속성에 DTO 객체를 속성값으로 저장한다.
		request.setAttribute("boardDTO", boardDTO);
		// 포워드로 파라미터를 전송한다.
		handlerAdapter.setPath("/WEB-INF/view/board/board_reply_view.jsp");
		return handlerAdapter;
	}
}