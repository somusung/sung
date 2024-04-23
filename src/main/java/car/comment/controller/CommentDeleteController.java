package car.comment.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.dao.CommentDAO;



public class CommentDeleteController implements Controller {
    private static Log log = LogFactory.getLog(CommentDeleteController.class);
        
        @Override
        public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
            int comment_code = Integer.parseInt(request.getParameter("comment_code"));
            log.info(comment_code);
            
            CommentDAO commentDao = new CommentDAO();
            commentDao.commentDelete(comment_code);
            
            // 삭제 후 뷰 페이지로 포워드
            HandlerAdapter handlerAdapter = new HandlerAdapter();
            handlerAdapter.setPath("/WEB-INF/view/comment/comment_delete_view.jsp");
            
            return handlerAdapter;
        }
    }



