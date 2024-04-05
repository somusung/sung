package min.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardSearchSelectController implements Controller {
	private static Log log = LogFactory.getLog(BoardSearchSelectController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		// 태그의 속성값을 호출한다.
		String keyword = (String) request.getParameter("keyword");
		String keyfield = (String) request.getParameter("keyfield");
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		if(keyword == null && keyfield == null) {
			handlerAdapter.setPath("/WEB-INF/view/login/login.jsp");
			return handlerAdapter;
		}
		// 세션 객체를 생성하여 할당한다.
		HttpSession session = request.getSession( );
		// 지정된 이름을 사용하여 세션에 객체를 바인딩한다.
		session.setAttribute("keyword", keyword);
		session.setAttribute("keyfield", keyfield);
		BoardDAO boardDAO = new BoardDAO( );
		List<?> searchBoardlist = new ArrayList<Object>( );
		int page = 1;
		int limit = 10;

		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			log.info("페이지 확인 - " + page);
		}
		int searchlistcount = boardDAO.boardSearchCount(keyword, keyfield);
		searchBoardlist = boardDAO.boardSearch(keyword, keyfield, page, limit);
		int maxpage = (int) ((double) searchlistcount / limit + 0.95);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("searchlistcount", searchlistcount);
		request.setAttribute("searchBoardlist", searchBoardlist);
		request.setAttribute("keyword", keyword);
		request.setAttribute("keyfield", keyfield);

		// 포워드로 파라미터를 전송한다.
		handlerAdapter.setPath("/WEB-INF/view/board/board_search_view.jsp");
		return handlerAdapter;
	}
}
