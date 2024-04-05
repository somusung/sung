package sung.dept.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sung.dept.control.Controller;
import sung.dept.dao.DeptDAO;
import sung.dept.dto.DeptDTO;
import sung.dept.hander.DeptHandlerAdapter;

public class DeptDeleteController implements Controller{
	 private static Log log = LogFactory.getLog(DeptDeleteController.class);
	 @Override
	 public DeptHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int deptno = Integer.parseInt(request.getParameter("deptno"));
	 log.info(deptno);
	 DeptDAO deptDao = new DeptDAO( );
	 DeptDTO deptDTO = new DeptDTO( );
	 deptDTO.setDeptno(deptno);
	 request.setAttribute("deptDTO", deptDTO);
	 deptDTO = deptDao.deptDelete(deptno);
	 log.info(deptDTO);
	 DeptHandlerAdapter deptHandlerAdapter = new DeptHandlerAdapter( );
	 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_delete_view.jsp");
	 return deptHandlerAdapter;
	 }
	 }

