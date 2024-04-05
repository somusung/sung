package min.smboard.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;

public interface Controller {
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response);
}
