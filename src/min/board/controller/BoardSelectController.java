package min.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardSelectController implements Controller {
	private static Log log = LogFactory.getLog(BoardSelectController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDAO boardDAO = new BoardDAO( );
		// 객체 타입으로 해당 페이지에 출력될 글을 저장한다.
		List<?> boardList = new ArrayList<Object>( );
		// 페이지가 지정되지 않으면 기본적으로 1페이지부터 보여준다.
		int page = 1;
		// 페이징 처리한 글의 개수를 10개로 설정한다.
		int limit = 10;
		// page 파라미터로 전달받은 페이지 수를 할당한다.
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		// 게시판의 총 글 수를 호출한다.
		int listcount = boardDAO.boardCount( );
		// 페이지와 리스트에 표시할 글 수를 파라미터로 전달하여 글 목록을 호출한다.
		// 글 목록 데이터를 반환하여 boardList 인스턴스로 할당하고 할당하지 않으면 null 값이 반환된다.
		boardList = boardDAO.boardSelectAll(page, limit);
		// 총 글 개수에서 표시할 글의 수를 나눈 후 0.95를 더해서 총 페이지 수를 구한다.
		log.info("글 등록 확인 - " + boardList);
		int maxpage = (int) ((double) listcount / limit + 0.95);
		// 현재 페이지에 보여줄 시작 페이지 수를 구한다.
		/*
		 * 시작 페이지 수는 해당 페이지 그룹에서 맨 처음에 표시될 페이지이다. 시작 페이지 수는 현재 페이지에 따라 이동 가능한 최소의 페이지
		 * 번호이다. 페이지 수가 15일 때 15까지 다 표시되기는 너무 많은 양이므로 보통 한 페이지에는 10페이지 정도까지 이동할 수 있게
		 * 표시한다. 1페이지일 경우 ((1 / 10 + 0.9) - 1) * 10 + 1) = 1로 계산되어 첫 번째 페이지 그룹이 되고
		 * 15페이지일 경우 ((15 / 10 + 0.9) - 1) * 10 + 1) = 11로 계산되어 두 번째 페이지 그룹이 된다.
		 */
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에서 보여줄 마지막 페이지 수를 구한다.
		/*
		 * 현재 페이지에서 보여줄 마지막 페이지는 시작 페이지와 반대로 현재 페이지 그룹에서 이동 가능한 최대의 페이지 번호이다. 기본적으로 페이지
		 * 수가 많지 않다고 가정하여 최대 페이지 수만큼 설정한다. 현재 페이지에 보여줄 마지막 페이지 수는 10, 20, 30 등이다.
		 */
		int endpage = startpage + 10 - 1;
		// 마지막 페이지 그룹이라면 마지막 그룹의 페이지 번호는 최대 페이지 개수까지만 출력한다.
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listcount", listcount);
		request.setAttribute("boardList", boardList);

		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		// 포워드로 파라미터를 전송한다.
		handlerAdapter.setPath("/WEB-INF/view/board/board_select_view.jsp");
		return handlerAdapter;
	}
}
