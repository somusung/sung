package min.board.controller;

import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import min.smboard.hander.HandlerAdapter;
import min.smboard.control.Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BoardDownloadController implements Controller {
	private static Log log = LogFactory.getLog(BoardDownloadController.class);

	public HandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileName = request.getParameter("attachedfile");
			String savePath = "./boardUpload";

			ServletContext context = request.getServletContext( );
			String downPath = context.getRealPath(savePath);
			String filePath = downPath + "\\" + fileName;
			byte b[] = new byte[4096];
			FileInputStream fileInputStream = new FileInputStream(filePath);
			String sEncoding = null;

			boolean MSIE = (request.getHeader("user-agent").indexOf("MSIE") != -1)
			  || (request.getHeader("user-agent").indexOf("Trident") != -1);
			String downType = request.getServletContext( ).getMimeType(filePath);

			if(downType == null)
				downType = "application/octet-stream";
			log.info("다운로드 자료형 확인 : " + downType);
			response.setContentType(downType);

			if(MSIE) {
				sEncoding = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1").replaceAll("\\+", "%20");
			} else {
				sEncoding = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.setHeader("Conten-Disposition", "attachment);filename=\"" + sEncoding + "\"");
			ServletOutputStream servletOutputStream = response.getOutputStream( );

			int nunRead;

			while((nunRead = fileInputStream.read(b, 0, b.length)) != -1) {

				servletOutputStream.write(b, 0, nunRead);

			}

			servletOutputStream.flush( );
			servletOutputStream.close( );
			fileInputStream.close( );

		} catch(Exception e) {
			e.printStackTrace( );
		}
		return null;
	}

}
