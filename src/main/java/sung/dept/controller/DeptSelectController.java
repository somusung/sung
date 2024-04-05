package sung.dept.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sung.dept.control.Controller;
import sung.dept.dao.DeptDAO;
import sung.dept.dto.DeptDTO;
import sung.dept.hander.DeptHandlerAdapter;

public class DeptSelectController implements Controller{
	 private static Log log = LogFactory.getLog(DeptSelectController.class);
	 @Override
	 public DeptHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 DeptDAO deptDao = new DeptDAO( );
	 DeptDTO deptDTO = new DeptDTO( );
	 log.info(deptDTO);
	 ArrayList<DeptDTO> arrayList = new ArrayList<DeptDTO>( );

	 arrayList = deptDao.deptSelectAll( );
	 log.info(arrayList);
	 request.setAttribute("arrayList", arrayList);
	 DeptHandlerAdapter deptHandlerAdapter = new DeptHandlerAdapter( );
	 log.info("부서 정보 조회");
	 deptHandlerAdapter.setPath("/WEB-INF/dept/dept_select_view.jsp");
	 return deptHandlerAdapter;
	 }
}
