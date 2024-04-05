package sung.dept.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sung.dept.control.Controller;
import sung.dept.dao.DeptDAO;
import sung.dept.dto.DeptDTO;
import sung.dept.hander.DeptHandlerAdapter;

public class DeptUpdateViewController implements Controller {
	 private static Log log = LogFactory.getLog(DeptUpdateViewController.class);
	 @Override
	 public DeptHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int deptno = Integer.parseInt(request.getParameter("deptno"));
	 log.info(deptno);
	 String dname = request.getParameter("dname");
	 log.info(dname);
	 String loc = request.getParameter("loc");
	 log.info(loc);
	 DeptDAO deptDao = new DeptDAO( );
	 DeptDTO deptDTO = new DeptDTO( );
	 deptDTO.setDeptno(deptno);
	 deptDTO.setDname(dname);
	 deptDTO.setLoc(loc);
	 deptDTO = deptDao.deptUpdate(deptDTO);
	 log.info(deptDTO);
	 request.setAttribute("deptDTO", deptDTO);
	 DeptHandlerAdapter deptHandlerAdapter = new DeptHandlerAdapter( );
	 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_update_view.jsp");
	 return deptHandlerAdapter;
	 }
	 }

