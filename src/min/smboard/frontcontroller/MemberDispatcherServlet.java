package min.smboard.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import min.login.controller.IdCheckController;
import min.login.controller.LoginController;
import min.login.controller.LogoutController;
import min.login.controller.IdSearchController;
import min.login.controller.PasswordSearchController;
import min.member.controller.MemberDeleteController;
import min.member.controller.MemberInsertController;
import min.member.controller.MemberSearchController;
import min.member.controller.MemberSelectDetailController;
import min.member.controller.MemberSelectController;
import min.member.controller.MemberUpdateController;
import min.member.controller.MemberUpdateViewController;
import min.smboard.control.Controller;
import min.smboard.hander.HandlerAdapter;

public class MemberDispatcherServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(MemberDispatcherServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI( );
		String contextPath = request.getContextPath( );
		String pathURI = requestURI.substring(contextPath.length( ));
		HandlerAdapter handlerAdapter = null;
		Controller controller = null;
		if(pathURI.equals("/MemberSelect.me")) {
			controller = new MemberSelectController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 전체 조회 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberInsertView.me")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/member/member_insert.jsp");
			log.info("회원 가입 화면 뷰 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberInsert.me")) {
			controller = new MemberInsertController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 가입 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberUpdate.me")) {
			controller = new MemberUpdateController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 수정 페이지 이동 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberUpdateView.me")) {
			controller = new MemberUpdateViewController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 수정 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberDelete.me")) {
			controller = new MemberDeleteController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 삭제 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberSelectDetail.me")) {
			controller = new MemberSelectDetailController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 상세 조회 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/LoginView.me")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/login/login.jsp");
			log.info("로그인 화면 뷰 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/Login.me")) {
			controller = new LoginController( );
			handlerAdapter = controller.execute(request, response);
			log.info("로그인 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/Logout.me")) {
			controller = new LogoutController( );
			handlerAdapter = controller.execute(request, response);
			log.info("로그아웃 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/IdCheck.me")) {
			controller = new IdCheckController( );
			handlerAdapter = controller.execute(request, response);
			log.info("아이디 체크 확인 -" + handlerAdapter);
		} else if(pathURI.equals("/IdSearchView.me")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/login/id_search.jsp");
			log.info("아이디 찾기 뷰화면  확인 -" + handlerAdapter);
		} else if(pathURI.equals("/IdSearch.me")) {
			controller = new IdSearchController( );
			handlerAdapter = controller.execute(request, response);
			log.info("아이디 찾기 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/PasswordSearchView.me")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/login/password_search.jsp");
			log.info("비밀번호 찾기 화면 뷰 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/PasswordSearch.me")) {
			controller = new PasswordSearchController( );
			handlerAdapter = controller.execute(request, response);
			log.info("비밀번호 찾기 확인 - " + handlerAdapter);
		} else if(pathURI.equals("/MemberSearch.me")) {
			controller = new MemberSearchController( );
			handlerAdapter = controller.execute(request, response);
			log.info("회원 검색 확인 -" + handlerAdapter);
		}
		if(handlerAdapter != null) {
			if(handlerAdapter.isRedirect( )) {
				response.sendRedirect(handlerAdapter.getPath( ));
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(handlerAdapter.getPath( ));
				dispatcher.forward(request, response);
			}
		}
	}
}