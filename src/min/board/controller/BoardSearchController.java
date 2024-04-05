package min.board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import min.board.dao.BoardDAO;
import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;

public class BoardSearchController implements Controller {

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession( );
		String keyword = (String) session.getAttribute("keyword");
		String keyfield = (String) session.getAttribute("keyfield");
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		BoardDAO boardDAO = new BoardDAO( );
		List<?> searchBoardlist = new ArrayList<Object>( );

		int page = 1;
		int limit = 10;

		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
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

		// handlerAdapter.setRedirect(false);
		handlerAdapter.setPath("/WEB-INF/view/board/board_search_view.jsp");
		return handlerAdapter;
	}
}
