package min.member.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberSearchController implements Controller {
	private static final Log log = LogFactory.getLog(MemberSearchController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		log.info("키워드 확인 - " + keyword);
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		if(keyword == null) {
			handlerAdapter.setPath("/WEB-INF/view/login/login.jsp");
			return handlerAdapter;
		}

		MemberDAO memberDAO = new MemberDAO( );
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>( );
		int page = 1;
		int limit = 10;
		if(request.getParameter("page") != null && !request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if(request.getParameter("limit") != null && !request.getParameter("limit").equals("")) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		arrayList = memberDAO.memberSearch(keyword, page, limit);
		log.info(arrayList);
		int listCount = memberDAO.memberSearchCount(keyword);
		log.info(listCount);
		int maxpage = (int) ((double) listCount / limit + 0.9);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if(endpage > maxpage) {
			endpage = maxpage;
		}
		request.setAttribute("arrayList", arrayList);
		request.setAttribute("limit", limit);
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listCount", listCount);
		request.setAttribute("keyword", keyword);

		handlerAdapter.setPath("/WEB-INF/view/member/member_search_view.jsp");
		return handlerAdapter;
	}

}
