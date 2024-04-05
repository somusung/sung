package min.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberDeleteController implements Controller {
	private static final Log log = LogFactory.getLog(MemberDeleteController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		log.info(id);
		MemberDTO memberDTO = new MemberDTO( );
		memberDTO.setId(id);
		MemberDAO memberDAO = new MemberDAO( );
		memberDTO = memberDAO.memberDelete(memberDTO);
		log.info(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/member/member_delete.jsp");
		return handlerAdapter;
	}
}
