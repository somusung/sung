package min.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.member.dao.MemberDAO;
import min.member.dto.MemberDTO;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberInsertController implements Controller {
	private static final Log log = LogFactory.getLog(MemberInsertController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		log.info(id);
		String name = request.getParameter("name");
		log.info(name);
		String password = request.getParameter("password");
		log.info(password);
		String gender = request.getParameter("gender");
		log.info(gender);
		int age = Integer.parseInt(request.getParameter("age"));
		log.info(age);
		String birthday = request.getParameter("birthday");
		log.info(birthday);
		String email = request.getParameter("email");
		log.info(email);
		MemberDTO memberDTO = new MemberDTO( );
		MemberDAO memberDAO = new MemberDAO( );
		int num = memberDAO.memberNumber( );
		memberDTO.setNum(num + 1);
		memberDTO.setId(id);
		memberDTO.setName(name);
		memberDTO.setPassword(password);
		memberDTO.setGender(gender);
		memberDTO.setAge(age);
		memberDTO.setBirthday(birthday);
		memberDTO.setEmail(email);
		log.info(memberDTO);
		memberDTO = memberDAO.memberInsert(memberDTO);
		request.setAttribute("id", memberDTO.getId( ));
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		handlerAdapter.setPath("/WEB-INF/view/member/member_insert_view.jsp");
		return handlerAdapter;
	}

}
