package sung.dept.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sung.dept.hander.DeptHandlerAdapter;

public interface Controller {
	public DeptHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response);

}
