package min.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdSearchController implements Controller {
	private static final Log log = LogFactory.getLog(IdSearchController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		log.info(name);
		String email = request.getParameter("email");
		log.info(email);
		MemberDTO memberDTO = new MemberDTO( );
		memberDTO.setName(name);
		memberDTO.setEmail(email);

		MemberDAO memberDAO = new MemberDAO( );
		memberDTO = memberDAO.memberSearchId(name, email);
		log.info(memberDTO);
		request.setAttribute("id", memberDTO.getId( ));

		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/login/id_search_view.jsp");

		return handlerAdapter;
	}

}
