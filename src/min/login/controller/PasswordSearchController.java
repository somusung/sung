package min.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PasswordSearchController implements Controller {
	private static final Log log = LogFactory.getLog(PasswordSearchController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		log.info(id);
		String email = request.getParameter("email");
		log.info(email);
		MemberDTO memberDTO = new MemberDTO( );
		memberDTO.setId(id);
		memberDTO.setEmail(email);

		MemberDAO memberDAO = new MemberDAO( );
		memberDTO = memberDAO.memberSearchPassword(id, email);
		log.info(memberDTO);
		request.setAttribute("password", memberDTO.getPassword( ));

		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/login/password_search_view.jsp");

		return handlerAdapter;
	}

}
