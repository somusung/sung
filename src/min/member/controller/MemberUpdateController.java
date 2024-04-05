package min.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberUpdateController implements Controller {
	private static final Log log = LogFactory.getLog(MemberUpdateController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDTO memberDTO = new MemberDTO( );
		MemberDAO memberDAO = new MemberDAO( );
		memberDTO.setId(request.getParameter("id"));
		log.info(memberDTO);
		memberDTO = memberDAO.memberSelect(memberDTO);
		log.info(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/member/member_update.jsp");
		return handlerAdapter;

	}
}