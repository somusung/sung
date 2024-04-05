package min.login.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class LoginController implements Controller {
	private static Log log = LogFactory.getLog(LoginController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String save = request.getParameter("save");
		log.info(save);
		MemberDTO memberDTO = new MemberDTO( );
		memberDTO.setId(id);
		memberDTO.setPassword(password);
		log.info(memberDTO);

		MemberDAO memberDAO = new MemberDAO( );
		memberDTO = memberDAO.memberLogin(memberDTO);
		log.info("로그인 내용" + memberDTO);
		request.setAttribute("name", memberDTO.getName( ));
		request.setAttribute("memberDTO", memberDTO);
		if(!memberDTO.getId( ).equals("") & !memberDTO.getPassword( ).equals("")) {
			HttpSession httpSession = request.getSession( );
			httpSession.setAttribute("id", memberDTO.getId( ));
			httpSession.setAttribute("name", memberDTO.getName( ));
			Cookie cookie = new Cookie("id", memberDTO.getId( ));
			cookie.setMaxAge(60 * 60 * 24);
			cookie.setPath("/");
			response.addCookie(cookie);
		}

		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/login/login_check.jsp");
		return handlerAdapter;
	}

}
