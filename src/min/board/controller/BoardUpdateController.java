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

public class BoardUpdateController implements Controller {
	private static Log log = LogFactory.getLog(BoardUpdateController.class);

	@Override
	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		HandlerAdapter handlerAdapter = new HandlerAdapter( );

		BoardDAO boardDAO = new BoardDAO( );
		BoardDTO boardDTO = new BoardDTO( );
		boolean result = false;

		String realFolder = "";
		String saveFolder = "./boardUpload";

		int fileSize = 10 * 1024 * 1024;

		realFolder = request.getSession( ).getServletContext( ).getRealPath(saveFolder);

		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, realFolder, fileSize, "UTF-8",
			  new DefaultFileRenamePolicy( ));
			// 글의 비밀번호와 수정 페이지에서 입력한 비밀번호가 일치하는지 확인한다.
			int num = Integer.parseInt(multipartRequest.getParameter("num"));
			// 글의 비밀번호와 수정 페이지에서 입력한 비밀번호가 일치하는지 확인한다.
			boolean usercheck = boardDAO.boardId(num, multipartRequest.getParameter("password"));
			if(usercheck == false) {
				handlerAdapter.setPath("./WEB-INF/view/board/board_update_view.jsp");
				return handlerAdapter;
			}

			boardDTO.setNum(num);
			boardDTO.setName(multipartRequest.getParameter("name"));
			boardDTO.setSubject(multipartRequest.getParameter("subject"));
			boardDTO.setContent(multipartRequest.getParameter("content"));
			boardDTO
			  .setAttachedfile(multipartRequest.getFilesystemName((String) multipartRequest.getFileNames( ).nextElement( )));
			// 업로드한 파일의 이전 이름을 설정한다.
			boardDTO.setOldfile(multipartRequest.getParameter("oldfile"));

			result = boardDAO.boardUpdate(boardDTO);
			log.info("게시글 수정 확인 - " + result);
			// 리다이렉트로 파라미트를 전송한다.
			handlerAdapter.setRedirect(true);
			handlerAdapter.setPath("./BoardSelectDetail.do?num=" + boardDTO.getNum( ));
			return handlerAdapter;
		} catch(Exception e) {
			e.printStackTrace( );
		}

		return null;
	}

}
