package min.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import min.board.dto.BoardDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardSelectDetailController implements Controller {
	private static Log log = LogFactory.getLog(BoardSelectDetailController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		// 글 번호 파라미터를 호출하고 읽어온 데이터를 부호 없는 정수로 구문 분석하여 int 자료형 변수에 할당한다.
		int num = Integer.parseInt(request.getParameter("num"));
		// 글 내용을 확인하면 글의 조회수를 증가시킨다.
		boardDAO.boardHitNumber(num);
		// 상세 보기 데이터를 반환하여 deptDTO 인스턴스로 할당하고 할당하지 않으면 null 값이 반환된다.
		boardDTO = boardDAO.boardSelect(num);
		log.info("DTO 확인 - " + boardDTO);
		// 속성에 DTO 객체를 속성값으로 저장한다.
		request.setAttribute("boardDTO", boardDTO);
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		// 포워드로 파라미터를 전송한다.
		handlerAdapter.setPath("/WEB-INF/view/board/board_select_detail_view.jsp");
		return handlerAdapter;
	}

}
