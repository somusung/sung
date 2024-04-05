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

public class BoardInsertController implements Controller {
	private static Log log = LogFactory.getLog(BoardInsertController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {

		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		HandlerAdapter handlerAdapter = new HandlerAdapter( );
		String realFolder = "";
		String saveFolder = "./boardUpload";
		// 실제 서버의 업로드 경로를 설정한다.
		realFolder = request.getSession( ).getServletContext( ).getRealPath(saveFolder);
		int fileSize = 10 * 1024 * 1024;
		boolean result = false;
		try {
			MultipartRequest multiRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
			  new DefaultFileRenamePolicy( ));
			boardDTO.setName(multiRequest.getParameter("name"));
			boardDTO.setPassword(multiRequest.getParameter("password"));
			boardDTO.setSubject(multiRequest.getParameter("subject"));
			boardDTO.setContent(multiRequest.getParameter("content"));
			boardDTO.setId(multiRequest.getParameter("id"));
			boardDTO.setAttachedfile(multiRequest.getFilesystemName((String) multiRequest.getFileNames( ).nextElement( )));
			// 게시판 글 등록을 처리한다.
			result = boardDAO.boardInsert(boardDTO);
			log.info("DTO 확인 - " + boardDTO);
			log.info("게시글 등록 - " + result);
			// 리다이렉트로 파라미트를 전송한다.
			handlerAdapter.setRedirect(true);
			handlerAdapter.setPath("./BoardSelect.do");

			return handlerAdapter;
		} catch(Exception ex) {
			ex.printStackTrace( );
		}

		return null;
	}

}
