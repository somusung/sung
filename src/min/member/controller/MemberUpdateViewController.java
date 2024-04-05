package min.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberUpdateViewController implements Controller {
	private static final Log log = LogFactory.getLog(MemberUpdateViewController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO memberDAO = new MemberDAO( );
		MemberDTO memberDTO = new MemberDTO( );
		memberDTO.setId(request.getParameter("id"));
		memberDTO.setName(request.getParameter("name"));
		memberDTO.setPassword(request.getParameter("password"));
		memberDTO.setEmail(request.getParameter("email"));
		memberDTO.setGender(request.getParameter("gender"));
		memberDTO.setAge(Integer.parseInt(request.getParameter("age")));
		memberDTO.setBirthday(request.getParameter("birthday"));

		log.info("업데이터 정보 - " + memberDTO);
		memberDTO = memberDAO.memberUpdate(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/member/member_update_view.jsp");
		return handlerAdapter;
	}
}
