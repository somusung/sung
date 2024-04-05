package min.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;
import min.board.dao.BoardDAO;
import min.board.dto.BoardDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardReplyController implements Controller {
	private static Log log = LogFactory.getLog(BoardReplyController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {

		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		BoardDAO boarDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );

		int result = 0;
		String realFolder = "";
		String saveFolder = "./boardUpload";
		int fileSize = 10 * 1024 * 1024;
		realFolder = request.getSession( ).getServletContext( ).getRealPath(saveFolder);
		try {
			MultipartRequest multiRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
			  new DefaultFileRenamePolicy( ));
			boardDTO.setNum(Integer.parseInt(multiRequest.getParameter("num")));
			boardDTO.setName(multiRequest.getParameter("name"));
			boardDTO.setPassword(multiRequest.getParameter("password"));
			boardDTO.setSubject(multiRequest.getParameter("subject"));
			boardDTO.setContent(multiRequest.getParameter("content"));
			boardDTO.setAnswernum(Integer.parseInt(multiRequest.getParameter("answernum")));
			boardDTO.setAnswerlev(Integer.parseInt(multiRequest.getParameter("answerlev")));
			boardDTO.setAnswerseq(Integer.parseInt(multiRequest.getParameter("answerseq")));
			boardDTO.setId(multiRequest.getParameter("id"));
			boardDTO.setAttachedfile(multiRequest.getFilesystemName((String) multiRequest.getFileNames( ).nextElement( )));
			result = boarDAO.boardReply(boardDTO);
			log.info("답변 확인 - " + result);
			// 리다이렉트로 파라미트를 전송한다.
			handlerAdapter.setRedirect(true);
			// handlerAdapter.setPath("./BoardSelectDetail.do?num=" + result);
			handlerAdapter.setPath("./BoardSelect.do");
		} catch(Exception e) {
			e.printStackTrace( );
		}

		return handlerAdapter;
	}

}
