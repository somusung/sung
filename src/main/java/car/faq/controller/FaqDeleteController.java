package car.faq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;


public class FaqDeleteController implements Controller {
	 private static Log log = LogFactory.getLog(FaqDeleteController.class);
	 @Override
	 public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 int faq_code = Integer.parseInt(request.getParameter("faq_code"));
	 log.info(faq_code);
	 FaqDAO faqDao = new FaqDAO( );
	 faqDao.faqDelete(faq_code);

	 HandlerAdapter handlerAdapter = new HandlerAdapter();
     handlerAdapter.setPath("/WEB-INF/view/faq/faq_delete_view.jsp");
     
     return handlerAdapter;
} }
