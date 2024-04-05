package sung.dept.frontcontroller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sung.dept.control.Controller;
import sung.dept.controller.DeptDeleteController;
import sung.dept.controller.DeptInsertController;
import sung.dept.controller.DeptSelectController;
import sung.dept.controller.DeptSelectDetailController;
import sung.dept.controller.DeptUpdateController;
import sung.dept.controller.DeptUpdateViewController;
import sung.dept.hander.DeptHandlerAdapter;



public class DeptDispatcherServlet extends HttpServlet implements Servlet{
	private static final long serialVersionUID = 1L;
	 private static Log log = LogFactory.getLog(DeptDispatcherServlet.class);
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String pathURL = requestURI.substring(contextPath.length());
		log.info("맵핑명 조회 - " + pathURL);
		DeptHandlerAdapter deptHandlerAdapter = null;
		Controller controller = null;
		if(pathURL.equals("/DeptSelect.so")) {
			 controller = new DeptSelectController( );
			 deptHandlerAdapter = controller.execute(request, response);
			 log.info("부서 조회 확인 - " + deptHandlerAdapter);
			 }
		else if(pathURL.equals("/DeptSelectDetail.so")) {	
			 controller = new DeptSelectDetailController( );
			 deptHandlerAdapter = controller.execute(request, response);
			 log.info("상세 부서 조회 확인 - " + deptHandlerAdapter);
			 }
		else if(pathURL.equals("/DeptInsertView.so")) {
			 deptHandlerAdapter = new DeptHandlerAdapter( );
			 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_insert.jsp"); 
			 log.info("부서 등록 확인 - " + deptHandlerAdapter);
			 }
		 else if(pathURL.equals("/DeptInsert.so")) {
			 controller = new DeptInsertController( );
			 deptHandlerAdapter = controller.execute(request, response);
			 log.info("부서 등록 확인 - " + deptHandlerAdapter);
			 }
			 else if(pathURL.equals("/DeptUpdateView.so")) {
			 controller = new DeptUpdateViewController( );
			 deptHandlerAdapter = controller.execute(request, response); 
			log.info("부서 수정 화면 뷰 확인 - " + deptHandlerAdapter);
			 }
			 else if(pathURL.equals("/DeptUpdate.so")) {
			 controller = new DeptUpdateController( ); 
			deptHandlerAdapter = controller.execute(request, response); 
			log.info("부서 수정 확인 - " + deptHandlerAdapter);
			 }
			 else if(pathURL.equals("/DeptDeleteView.so")) {
				 deptHandlerAdapter = new DeptHandlerAdapter( );
				 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_delete.jsp");
				 log.info("부서 삭제 화면 뷰 확인 - " + deptHandlerAdapter);
				 }
				 else if(pathURL.equals("/DeptDelete.so")) {
				 controller = new DeptDeleteController( );
				 deptHandlerAdapter = controller.execute(request, response); 
				log.info("부서 삭제 확인 - " + deptHandlerAdapter);
				 }
				 if(deptHandlerAdapter != null) {
				 if(deptHandlerAdapter.isRedirect( )) {
				 response.sendRedirect(deptHandlerAdapter.getPath( ));
				 } else {
				 RequestDispatcher dispatcher = request.getRequestDispatcher(deptHandlerAdapter.getPath( ));
				 dispatcher.forward(request, response);
				 }
	}
	}
}
