package car.term.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.cmn.control.Controller;
import car.cmn.handler.HandlerAdapter;
import car.term.dao.TermDAO;
import car.term.dto.TermDTO;

public class TermSelectController implements Controller {
    private static Log log = LogFactory.getLog(TermSelectController.class);

    @Override
    public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
        TermDAO termDao = new TermDAO();
        ArrayList<TermDTO> arrayList = termDao.termSelectAll();
        log.info("이용약관 조회 결과: " + arrayList);

        request.setAttribute("arrayList", arrayList);

        HandlerAdapter handlerAdapter = new HandlerAdapter();
        handlerAdapter.setPath("/WEB-INF/view/term/term_select_view.jsp");

        return handlerAdapter;
    }
}

