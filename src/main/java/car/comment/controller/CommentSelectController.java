package car.comment.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;
import car.comment.dto.CommentDTO;

public class CommentSelectController implements Controller {
	    private static Log log = LogFactory.getLog(CommentSelectController.class);

	    @Override
	    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	    	 HttpSession session = request.getSession();
			 session.setAttribute("member_code", 3); 
			 Integer member_code = (Integer) session.getAttribute("member_code");
	        CommentDAO commentDao = new CommentDAO();
	        ArrayList<CommentDTO> arrayList = new ArrayList<CommentDTO>();
	        
	        // 사용자로부터 주차장 코드를 받는 부분이 없으므로 생략합니다. 필요하다면 추가해야 합니다.
	        int parking_code = 3; // 임시로 주차장 코드를 지정합니다.

	        arrayList = commentDao.commentSelectAll(parking_code);

	        log.info(arrayList);
	        request.setAttribute("arrayList", arrayList);

	        HandlerAdapter handlerAdapter = new HandlerAdapter();
	        handlerAdapter.setPath("/WEB-INF/view/comment/comment_select_view.jsp");
	        return handlerAdapter;
	    }
	}

