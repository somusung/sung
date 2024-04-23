package car.faq.controller;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.faq.dao.FaqDAO;
import car.faq.dto.FaqDTO;




public class FaqSelectController implements Controller {
	 private static Log log = LogFactory.getLog(FaqSelectController.class);
	 @Override
	 public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
	 FaqDAO faqDao = new FaqDAO( );
	 FaqDTO faqDTO = new FaqDTO( );
	 log.info(faqDTO);
	 ArrayList<FaqDTO> arrayList = new ArrayList<FaqDTO>( );
	 arrayList = faqDao.faqSelectAll( );
	 log.info(arrayList);
	 request.setAttribute("arrayList", arrayList);
	 HandlerAdapter handlerAdapter = new HandlerAdapter( );
	 log.info("게시판 정보 조회");
	 handlerAdapter.setPath("/WEB-INF/view/faq/faq_select_view.jsp");
	 return handlerAdapter;
	 }
}


