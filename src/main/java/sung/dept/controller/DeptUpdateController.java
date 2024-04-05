package sung.dept.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sung.dept.control.Controller;
import sung.dept.dao.DeptDAO;
import sung.dept.dto.DeptDTO;
import sung.dept.hander.DeptHandlerAdapter;

public class DeptUpdateController implements Controller{
	 private static Log log = LogFactory.getLog(DeptUpdateController.class);
	 @Override
	 public DeptHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int deptno = Integer.parseInt(request.getParameter("deptno"));
	 log.info(deptno);
	 System.out.println(deptno);
	 DeptDAO deptDao = new DeptDAO( );
	 DeptDTO deptDTO = new DeptDTO( );
	 deptDTO = deptDao.deptSelect(deptno);
	 request.setAttribute("deptDTO", deptDTO);
	 DeptHandlerAdapter deptHandlerAdapter = new DeptHandlerAdapter( );
	 log.info("특정 부서 조회");
	 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_update.jsp");
	 return deptHandlerAdapter;
	 }
	 }

