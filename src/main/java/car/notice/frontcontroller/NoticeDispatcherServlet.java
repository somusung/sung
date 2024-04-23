package car.notice.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.comment.controller.CommentDeleteController;
import car.comment.controller.CommentInsertController;
import car.comment.controller.CommentSelectController;
import car.comment.controller.CommentSelectDetailController;
import car.comment.controller.CommentUpdateController;
import car.comment.controller.CommentUpdateViewController;
import car.faq.controller.FaqAnswerDeleteController;
import car.faq.controller.FaqAnswerDeleteViewController;
import car.faq.controller.FaqAnswerSelectDetailController;
import car.faq.controller.FaqAnswerUpdateController;
import car.faq.controller.FaqAnswerUpdateViewController;
import car.faq.controller.FaqDeleteController;
import car.faq.controller.FaqInsertController;
import car.faq.controller.FaqSearchController;
import car.faq.controller.FaqSelectController;
import car.faq.controller.FaqSelectDetailController;
import car.faq.controller.FaqUpdateController;
import car.faq.controller.FaqUpdateViewController;
import car.notice.controller.NoticeDeleteController;
import car.notice.controller.NoticeInsertController;
import car.notice.controller.NoticeSelectController;
import car.notice.controller.NoticeSelectDetailController;
import car.notice.controller.NoticeSelectEventController;
import car.notice.controller.NoticeSelectEventDetailController;
import car.notice.controller.NoticeUpdateController;
import car.notice.controller.NoticeUpdateEventController;
import car.notice.controller.NoticeUpdateViewController;
import car.term.controller.TermSelectController;



@WebServlet("/NoticeDispatcherServlet")
public class NoticeDispatcherServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(NoticeDispatcherServlet.class);  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	
	 protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 String requestURI = request.getRequestURI( );
		 String contextPath = request.getContextPath( );
		 String pathURL = requestURI.substring(contextPath.length( ));
		 log.info("매핑명 조회 - " + pathURL);	
		 HandlerAdapter HandlerAdapter = null;
		 Controller controller = null;
		 if(pathURL.equals("/NoticeSelect.so")) {
	            controller = new NoticeSelectController();
	            HandlerAdapter = controller.execute(request, response);
	            log.info("공지사항 조회 확인 - " + HandlerAdapter);
	      }  else if(pathURL.equals("/NoticeSelectDetail.so")) { 
	    	controller = new NoticeSelectDetailController(); 
	    	HandlerAdapter = controller.execute(request,response); 
	        log.info("공지사항 상세 조회 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/NoticeSelectEvent.so")) { 
	    	  controller = new NoticeSelectEventController(); 
	    	  HandlerAdapter = controller.execute(request, response); 
	    	  log.info("이벤트 조회 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/NoticeSelectEventDetail.so")) { 
			 controller = new NoticeSelectEventDetailController(); HandlerAdapter =
				 controller.execute(request, response); log.info("이벤트 상세 조회 확인 - " +
				 HandlerAdapter); }
		 else if(pathURL.equals("/NoticeInsertView.so")) {
			  HandlerAdapter = new HandlerAdapter();
			  HandlerAdapter.setPath("/WEB-INF/view/notice/notice_insert.jsp");
			  log.info("글 등록 화면 뷰 확인- " + HandlerAdapter); } 
		 else if(pathURL.equals("/NoticeInsert.so")) { controller = new
			 NoticeInsertController(); HandlerAdapter = controller.execute(request,
			  response); log.info("글 등록 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/NoticeUpdate.so")) { 
			 controller = new NoticeUpdateController(); 
			 HandlerAdapter = controller.execute(request, response); 
			 log.info("공지글 수정 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/NoticeUpdateEvent.so")) { 
			 controller = new NoticeUpdateEventController(); 
			 HandlerAdapter = controller.execute(request, response); 
			 log.info("이벤트글 수정 확인 - " + HandlerAdapter); }	 
		 else if(pathURL.equals("/NoticeUpdateView.so")) { 
			  controller = new NoticeUpdateViewController(); 
			  HandlerAdapter = controller.execute(request, response); 
			  log.info("작성 글 수정 화면 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/NoticeDeleteView.so")) {
			 HandlerAdapter = new HandlerAdapter();
			 HandlerAdapter.setPath("/WEB-INF/view/notice/notice_delete.jsp");
			 log.info("작성글 삭제 화면 뷰 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/NoticeDelete.so")) { 
			 controller = new NoticeDeleteController(); 
			 HandlerAdapter = controller.execute(request,
			 response); log.info("작성글 삭제 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/FaqSelect.so")) {
				controller = new FaqSelectController( );
				HandlerAdapter = controller.execute(request, response);
				log.info("글 전체 조회 확인 - " + HandlerAdapter);
			} 
		 else if(pathURL.equals("/FaqSelectDetail.so")) { 
		    	controller = new FaqSelectDetailController(); 
		    	HandlerAdapter = controller.execute(request,response); 
		        log.info("게시글 상세 조회 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqSearch.so")) {
				controller = new FaqSearchController();
				HandlerAdapter = controller.execute(request, response);
				log.info("검색 조회 확인 -" + HandlerAdapter);
			}
		 else if(pathURL.equals("/FaqInsertView.so")) {
			  HandlerAdapter = new HandlerAdapter();
			  HandlerAdapter.setPath("/WEB-INF/view/faq/faq_insert.jsp");
			  log.info("글 등록 화면 뷰 확인- " + HandlerAdapter); } 
		 else if(pathURL.equals("/FaqInsert.so")) { controller = new
			 FaqInsertController(); HandlerAdapter = controller.execute(request,
			  response); log.info("게시글 등록 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/FaqUpdate.so")) { 
			 controller = new FaqUpdateController(); 
			 HandlerAdapter = controller.execute(request, response); 
			 log.info("게시글 수정 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqUpdateView.so")) { 
			  controller = new FaqUpdateViewController(); 
			  HandlerAdapter = controller.execute(request, response); 
			  log.info("게시글 수정 화면 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqDeleteView.so")) {
			 HandlerAdapter = new HandlerAdapter();
			 HandlerAdapter.setPath("/WEB-INF/view/faq/faq_delete.jsp");
			 log.info("게시글 삭제 화면 뷰 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/FaqDelete.so")) { 
			 controller = new FaqDeleteController(); 
			 HandlerAdapter = controller.execute(request,
			 response); log.info("작성글 삭제 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/FaqAnswerSelectDetail.so")) { 
		    	controller = new FaqAnswerSelectDetailController(); 
		    	HandlerAdapter = controller.execute(request,response); 
		        log.info("댓글 상세 조회 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqAnswerUpdate.so")) { 
			 controller = new FaqAnswerUpdateController(); 
			 HandlerAdapter = controller.execute(request, response); 
			 log.info("댓글 수정 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqAnswerUpdateView.so")) { 
			  controller = new FaqAnswerUpdateViewController(); 
			  HandlerAdapter = controller.execute(request, response); 
			  log.info("댓글 수정 화면 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqAnswerDeleteView.so")) {
			 controller = new FaqAnswerDeleteViewController(); 
			  HandlerAdapter = controller.execute(request, response); 
			  log.info("댓글 삭제 화면 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/FaqAnswerDelete.so")) { 
			 controller = new FaqAnswerDeleteController(); 
			 HandlerAdapter = controller.execute(request,
			 response); log.info("댓글 삭제 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/TermSelect.so")) {
				controller = new TermSelectController( );
				HandlerAdapter = controller.execute(request, response);
				log.info("이용약관 전체 조회 확인 - " + HandlerAdapter);
			} 
		 else if(pathURL.equals("/CommentSelect.so")) {
				controller = new CommentSelectController( );
				HandlerAdapter = controller.execute(request, response);
				log.info("리뷰 댓글 목록 조회 확인 - " + HandlerAdapter);
			} 
		 else if(pathURL.equals("/CommentSelectDetail.so")) { 
		    	controller = new CommentSelectDetailController(); 
		    	HandlerAdapter = controller.execute(request,response); 
		        log.info("리뷰 댓글 상세 조회 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/CommentInsertView.so")) {
			  HandlerAdapter = new HandlerAdapter();
			  HandlerAdapter.setPath("/WEB-INF/view/comment/comment_insert.jsp");
			  log.info("리뷰 댓글 등록 화면 뷰 확인- " + HandlerAdapter); } 
		 else if(pathURL.equals("/CommentInsert.so")) { controller = new
			 CommentInsertController(); HandlerAdapter = controller.execute(request,
			  response); log.info("리뷰 댓글 등록 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/CommentUpdate.so")) { 
			 controller = new CommentUpdateController(); 
			 HandlerAdapter = controller.execute(request, response); 
			 log.info("리뷰 댓글 수정 확인 - " + HandlerAdapter); }
		 else if(pathURL.equals("/CommentUpdateView.so")) { 
			  controller = new CommentUpdateViewController(); 
			  HandlerAdapter = controller.execute(request, response); 
			  log.info("리뷰 댓글 수정 화면 확인 - " + HandlerAdapter); } 
		 else if(pathURL.equals("/CommentDelete.so")) { 
			 controller = new CommentDeleteController(); 
			 HandlerAdapter = controller.execute(request,
			 response); log.info("리뷰 댓글 삭제 확인 - " + HandlerAdapter); }
		 
		 
		 
		  if(HandlerAdapter != null) { if(HandlerAdapter.isRedirect()) {
		  response.sendRedirect(HandlerAdapter.getPath()); } else { RequestDispatcher
		  dispatcher = request.getRequestDispatcher(HandlerAdapter.getPath());
		  dispatcher.forward(request, response); } } } }
		 
 

