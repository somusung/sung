package min.board.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import min.board.dto.BoardDTO;
import min.board.service.BoardService;

public class BoardDAO implements BoardService {
	private static Log log = LogFactory.getLog(BoardDAO.class);

//제네릭 BoardDTO 클래스 반환 자료형으로 전체 데이터를 조회한다.
	@Override
	public List<BoardDTO> boardSelectAll(int page, int limit) {
		List<BoardDTO> list = new ArrayList<BoardDTO>( );
		// 읽기 시작할 열 번호를 계산하고 페이지마다 출력될 행을 획득한다.
		int startrow = (page - 1) * 10 + 1;
		// 읽을 마지막 열 번호를 계산하고 페이지마다 출력될 행을 획득한다.
		int endrow = startrow + limit - 1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 슈도 컬럼을 이용해서 해당 페이지에 출력될 레코드들을 조회하여 페이징 처리를 한다.
			String sql = "select * from (select rownum rnum, num, name, subject,content,";
			sql += "attachedfile, answernum, answerlev, answerseq, readcount, to_char(writeday, 'yyyy-mm-dd') writeday ";
			sql += " from ( select * from board order by answernum desc, answerseq asc))";
			sql += " where rnum>=? and rnum<=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, startrow);
			preparedStatement.setInt(2, endrow);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				BoardDTO boardDTO = new BoardDTO( );
				boardDTO.setNum(resultSet.getInt("num"));
				boardDTO.setName(resultSet.getString("name"));
				boardDTO.setSubject(resultSet.getString("subject"));
				boardDTO.setContent(resultSet.getString("content"));
				boardDTO.setAttachedfile(resultSet.getString("attachedfile"));
				boardDTO.setAnswernum(resultSet.getInt("answernum"));
				boardDTO.setAnswerlev(resultSet.getInt("answerlev"));
				boardDTO.setAnswerseq(resultSet.getInt("answerseq"));
				boardDTO.setReadcount(resultSet.getInt("readcount"));
				boardDTO.setWriteday(resultSet.getString("writeday"));
				list.add(boardDTO);

			}
			return list;
		} catch(Exception e) {
			log.info("글 목록보기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}

		return null;

	}

	// boolean 반환 자료형으로 데이터를 입력한다.
	@Override
	public boolean boardInsert(BoardDTO boardDTO) {

		int num = 0;
		String sql = "";
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 글 번호의 최대값을 조회하여 글을 등록할 때 글 번호를 순차적으로 설정한다.
			/*
			 * max 함수로 최대값을 얻는 대신 count 함수로 레코드 수를 얻어서 1을 더하는 방법은 레코드를 삭제하고 등록할 때 무결성 제약 조건에
			 * 위배되므로 max 함수를 사용하여 글 번호를 계산해서 지정하는 것이 정확하다.
			 */
			sql = "select max(num) from board";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				num = resultSet.getInt(1) + 1;

			} else {
				num = 1;
			}
			// 사전 컴파일된 SQL 문을 실행하고 생성된 결과를 반환하는 데 사용된 객체에 대한 자원 해제를 한다.
			preparedStatement.close( );
			sql = "insert into board (num, name, password, subject, content, attachedfile,";
			sql += "answernum, answerlev, answerseq, readcount, writeday, id)";
			sql += " values(?,?,?,?,?,?,?,?,?,?,sysdate,?)";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			preparedStatement.setString(2, boardDTO.getName( ));
			preparedStatement.setString(3, boardDTO.getPassword( ));
			preparedStatement.setString(4, boardDTO.getSubject( ));
			preparedStatement.setString(5, boardDTO.getContent( ));
			preparedStatement.setString(6, boardDTO.getAttachedfile( ));
			preparedStatement.setInt(7, num);
			preparedStatement.setInt(8, 0);
			preparedStatement.setInt(9, 0);
			preparedStatement.setInt(10, 0);
			preparedStatement.setString(11, boardDTO.getId( ));
			result = preparedStatement.executeUpdate( );
			if(result == 0) {
				return false;
			}
			return true;
		} catch(Exception e) {
			log.info("글 등록 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return false;
	}

//boolean 반환 자료형으로 데이터를 수정한다.
	@Override
	public boolean boardUpdate(BoardDTO boardDTO) {
		String fileName = boardDTO.getOldfile( );
		String realFolder = "";

		realFolder = realFolder + fileName;
		File file = new File(realFolder);

		if(boardDTO.getAttachedfile( ) == null) {
			boardDTO.setAttachedfile(fileName);

		} else {
			if(file.exists( )) {
				file.delete( );
			}
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "update board set name=?, subject=?, content=?, attachedfile=?";
			sql += " where num=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getName( ));
			preparedStatement.setString(2, boardDTO.getSubject( ));
			preparedStatement.setString(3, boardDTO.getContent( ));
			preparedStatement.setString(4, boardDTO.getAttachedfile( ));
			preparedStatement.setInt(5, boardDTO.getNum( ));
			preparedStatement.executeUpdate( );
			return true;

		} catch(Exception e) {
			log.info("글 수정 실패 - " + e);
		} finally {
			try {
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return false;
	}

//boolean 반환 자료형으로 데이터를 삭제한다.
	@Override
	public boolean boardDelete(int num) {

		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "delete from board where num=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			result = preparedStatement.executeUpdate( );

			if(result == 0) {
				return false;

			}
		} catch(Exception e) {
			log.info("글 삭제 실패 - " + e);
		} finally {
			try {
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e2) {
				e2.printStackTrace( );
			}
		}

		return false;
	}

	// BoardDTO 클래스 반환 자료형으로 특정 데이터를 조회한다.
	@Override
	public BoardDTO boardSelect(int num) {
		BoardDTO boardDTO = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );

			String sql = "select num, name, subject, content, attachedfile, answernum, answerlev, answerseq, readcount, to_char(writeday, 'yyyy-mm-dd') writeday, id from board ";
			sql += " where num = ?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				boardDTO = new BoardDTO( );
				boardDTO.setNum(resultSet.getInt("num"));
				boardDTO.setName(resultSet.getString("name"));
				boardDTO.setSubject(resultSet.getString("subject"));
				boardDTO.setContent(resultSet.getString("content"));
				boardDTO.setAttachedfile(resultSet.getString("attachedfile"));
				boardDTO.setAnswernum(resultSet.getInt("answernum"));
				boardDTO.setAnswerlev(resultSet.getInt("answerlev"));
				boardDTO.setAnswerseq(resultSet.getInt("answerseq"));
				boardDTO.setReadcount(resultSet.getInt("readcount"));
				boardDTO.setWriteday(resultSet.getString("writeday"));
				boardDTO.setId(resultSet.getString("id"));
			}
			return boardDTO;
		} catch(Exception e) {
			log.info("글 내용 보기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return null;
	}

//int 반환 자료형으로 입력 데이터의 개수를 구한다.
	@Override
	public int boardCount( ) {
		int i = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 모든 행의 개수를 조회한다.
			String sql = "select count(*) from board";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				// 첫 번째 결과값인 카운트 개수를 int 자료형으로 할당한다.
				i = resultSet.getInt(1);
			}
		} catch(Exception e) {
			log.info("글 개수 구하기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return i;
	}

	// void 반환 자료형으로 데이터의 조회수를 구한다.
	@Override
	public void boardHitNumber(int num) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// num 인스턴스 파라미터를 호출하여 업데이트한다.
			String sql = "update board set readcount = readcount+1 where num =" + num;
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate( );
		} catch(Exception e) {
			log.info("조회수 업데이트 실패 - " + e);
		} finally {
			try {
				preparedStatement.close( );
				connection.close( );

			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
	}

	// int 반환 자료형으로 답변 데이터를 수정한다.
	@Override
	public int boardReply(BoardDTO boardDTO) {
		int num = 0;
		// 답변할 원본 글에 대한 그룹 번호를 설정한다.
		/*
		 * 답변을 달게 되면 답변 글은 번호가 같은 관련 글 번호를 갖게 되어 같은 그룹에 속하게 되므로 글 목록이 출력될 때 하나의 그룹으로 묶여서
		 * 출력된다.
		 */
		int answernum = boardDTO.getAnswernum( );
		// 답변 글의 레벨을 설정한다.
		/* 글에 대한 답 글이 출력될 때 한 번 들여쓰기 처리가 되고 답 글에 대한 답 글은 들여쓰기가 두 번 처리되게 된다. */
		int answerlev = boardDTO.getAnswerlev( );
		// 같은 관련 글 중에서 해당 글이 출력되는 순서를 설정한다.
		/*
		 * 같은 관련 글 중에서 해당 글이 출력되는 순서로 원래 글의 값은 0이 되고 원래 글의 답 글은 1이 되며 답 글의 답 글은 2가 된다.
		 */
		int answerseq = boardDTO.getAnswerseq( );

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select max(num) from board";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				num = resultSet.getInt(1) + 1;
			} else {
				num = 1;
			}
			// 사전 컴파일된 SQL 문을 실행하고 생성된 결과를 반환하는 데 사용된 객체에 대한 자원 해제를 한다.
			preparedStatement.close( );
			// 값을 1씩 증가시켜 현재 글을 답변 대상 글 바로 아래에 출력되게 처리한다.
			sql = "update board set answerseq=answerseq+1";
			sql += " where answernum=? and answerseq > ?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, answernum);
			preparedStatement.setInt(2, answerseq);
			preparedStatement.executeUpdate( );
			answerseq = answerseq + 1;
			answerlev = answerlev + 1;
			sql = "insert into board (num, name, password, subject, content, attachedfile,";
			sql += "answernum, answerlev, answerseq, readcount, writeday, id)";
			sql += " values(?,?,?,?,?,?,?,?,?,?,sysdate,?)";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			preparedStatement.setString(2, boardDTO.getName( ));
			preparedStatement.setString(3, boardDTO.getPassword( ));
			preparedStatement.setString(4, boardDTO.getSubject( ));
			preparedStatement.setString(5, boardDTO.getContent( ));
			preparedStatement.setString(6, boardDTO.getAttachedfile( ));
			preparedStatement.setInt(7, answernum);
			preparedStatement.setInt(8, answerlev);
			preparedStatement.setInt(9, answerseq);
			preparedStatement.setInt(10, 0);
			preparedStatement.setString(11, boardDTO.getId( ));
			preparedStatement.executeUpdate( );
			return num;
		} catch(Exception e) {
			log.info("글 답변 실패: " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return 0;

	}

//boolean 반환 자료형으로 아이디 데이터를 조회한다.
	@Override
	public boolean boardId(int num, String id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select * from board where num=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			resultSet = preparedStatement.executeQuery( );

			if(resultSet.next( )) {
				if(id.equals(resultSet.getString("id")) || id.equals("admin")) {
					return true;
				}
			}
		} catch(Exception e) {
			log.info("글쓴이 확인 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return false;
	}

	// 제네릭 ? 와일드 카드 반환 자료형으로 데이터를 검색한다.
	@Override
	public List<?> boardSearch(String keyword, String keyfield, int page, int limit) {
		log.info("검색 단어 확인 - " + keyword);
		log.info("검색 영역 확인 - " + keyfield);
		String searchCall = "";
		// 공백이 아닌 조건을 설정한다.
		if(!"".equals(keyword)) {
			// HTML의 태그 속성값으로 검색한다.
			if("all".equals(keyfield)) {
				searchCall = "(subject like '%' || '" + keyword + "' || '%' ) or ( name like '%' || '" + keyword
				  + "' || '%') or ( content like '%' || '" + keyword + "' || '%')";
			} else if("subject".equals(keyfield)) {
				searchCall = " subject like '%' || '" + keyword + "' || '%'";
			} else if("name".equals(keyfield)) {
				searchCall = " name like '%' || '" + keyword + "' || '%'";
			} else if("content".equals(keyfield)) {
				searchCall = " content like '%' || '" + keyword + "' || '%'";
			}
		}
		List<BoardDTO> list = new ArrayList<BoardDTO>( );
		int startrow = (page - 1) * 10 + 1;
		int endrow = startrow + limit - 1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select * from (select rownum rnum,num,name,subject,content,";
			sql += "attachedfile,answernum,answerlev,answerseq,readcount,to_char(writeday, 'yyyy-mm-dd') writeday ";
			sql += " from (select * from board order by answernum desc, answerseq asc) ";
			sql += " where " + searchCall + ")";
			sql += " where rnum>=? and rnum<=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, startrow);
			log.info("시작열 확인 - " + startrow);
			preparedStatement.setInt(2, endrow);
			log.info("마지막열 확인 - " + startrow);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				BoardDTO boardDTO = new BoardDTO( );
				boardDTO.setNum(resultSet.getInt("num"));
				boardDTO.setName(resultSet.getString("name"));
				boardDTO.setSubject(resultSet.getString("subject"));
				boardDTO.setContent(resultSet.getString("content"));
				boardDTO.setAttachedfile(resultSet.getString("attachedfile"));
				boardDTO.setAnswernum(resultSet.getInt("answernum"));
				boardDTO.setAnswerlev(resultSet.getInt("answerlev"));
				boardDTO.setAnswerseq(resultSet.getInt("answerseq"));
				boardDTO.setReadcount(resultSet.getInt("readcount"));
				boardDTO.setWriteday(resultSet.getString("writeday"));
				list.add(boardDTO);
			}
			return list;
		} catch(Exception e) {
			log.info("검색 실패 - " + e);

		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e2) {
				e2.printStackTrace( );
			}
		}
		return null;
	}

//int 반환 자료형으로 검색 데이터의 개수를 구한다.
	@Override
	public int boardSearchCount(String keyword, String keyfield) {
		log.info("검색 단어 확인 - " + keyword);
		log.info("검색 영역 확인 - " + keyfield);
		String searchCall = "";
		if(!"".equals(keyword)) {
			if("all".equals(keyfield)) {
				searchCall = "(subject like '%' || '" + keyword + "' || '%' ) or ( name like '%' || '" + keyword
				  + "' || '%') or ( content like '%' || '" + keyword + "' || '%')";
			} else if("subject".equals(keyfield)) {
				searchCall = " subject like '%' || '" + keyword + "' || '%'";
			} else if("name".equals(keyfield)) {
				searchCall = " name like '%' || '" + keyword + "' || '%'";
			} else if("content".equals(keyfield)) {
				searchCall = " content like '%' || '" + keyword + "' || '%'";
			}
		}
		log.info("검색 - " + searchCall);
		int i = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select count(*) from board where" + searchCall;
			log.info("SQL 확인 - " + sql);
			log.info("검색 - " + searchCall);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				i = resultSet.getInt(1);
			}
		} catch(Exception e) {
			log.info("검색 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return i;
	}
}
