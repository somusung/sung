package min.smboard.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.board.controller.BoardInsertController;
import min.board.controller.BoardDeleteController;
import min.board.controller.BoardSelectDetailController;
import min.board.controller.BoardDownloadController;
import min.board.controller.BoardSelectController;
import min.board.controller.BoardUpdateDetailController;
import min.board.controller.BoardUpdateController;
import min.board.controller.BoardReplyInsertController;
import min.board.controller.BoardReplyController;
import min.board.controller.BoardSearchController;
import min.board.controller.BoardSearchSelectController;
import min.smboard.control.Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardDispatcherServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(BoardDispatcherServlet.class);

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
		String reuqstURI = request.getRequestURI( );
		String contextPath = request.getContextPath( );
		String pathURL = reuqstURI.substring(contextPath.length( ));
		HandlerAdapter handlerAdapter = null;
		Controller controller = null;
		if(pathURL.equals("/BoardSelect.do")) {
			controller = new BoardSelectController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 전체 조회 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardInsertView.do")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/board/board_insert_view.jsp");
			log.info("글 등록 화면 뷰 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardInsert.do")) {
			controller = new BoardInsertController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 등록 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardUpdate.do")) {
			controller = new BoardUpdateController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 수정 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardDeleteView.do")) {
			handlerAdapter = new HandlerAdapter( );
			handlerAdapter.setPath("/WEB-INF/view/board/board_delete.jsp");
			log.info("글 삭제 화면 뷰 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardDelete.do")) {
			controller = new BoardDeleteController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 삭제 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardSelectDetail.do")) {
			controller = new BoardSelectDetailController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 상세 내용 확인 -" + handlerAdapter);
		} else if(pathURL.equals("/BoardUpdateDetail.do")) {
			controller = new BoardUpdateDetailController( );
			handlerAdapter = controller.execute(request, response);
			log.info("글 상세 내용 수정 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardDownload.do")) {
			controller = new BoardDownloadController( );
			handlerAdapter = controller.execute(request, response);
			log.info("다운로드 확인 - " + handlerAdapter);
		} else if(pathURL.equals("/BoardReply.do")) {
			controller = new BoardReplyController( );
			handlerAdapter = controller.execute(request, response);
			log.info("답변글 내용  확인 -" + handlerAdapter);
		} else if(pathURL.equals("/BoardReplyInsert.do")) {
			controller = new BoardReplyInsertController( );
			handlerAdapter = controller.execute(request, response);
			log.info("답변글 등록 확인 -" + handlerAdapter);
		} else if(pathURL.equals("/BoardSearch.do")) {
			controller = new BoardSearchController( );
			handlerAdapter = controller.execute(request, response);
			log.info("검색 확인 -" + handlerAdapter);
		} else if(pathURL.equals("/BoardSearchSelect.do")) {
			controller = new BoardSearchSelectController( );
			handlerAdapter = controller.execute(request, response);
			log.info("검색 조회 확인 -" + handlerAdapter);
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
