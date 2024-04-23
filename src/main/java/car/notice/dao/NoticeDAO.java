package car.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.notice.dto.NoticeDTO;
import car.notice.service.NoticeService;

public class NoticeDAO implements NoticeService {
	 private static Log log = LogFactory.getLog(NoticeDAO.class);
	 @Override
	 public ArrayList<NoticeDTO> noticeSelectAll( ) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 ArrayList<NoticeDTO> arrayList = new ArrayList<NoticeDTO>( );
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "SELECT notice_code, notice_title, notice_content, notice_date from notice WHERE notice_type = '공지사항'";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 resultSet = preparedStatement.executeQuery( );
		 while(resultSet.next( )) {
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setNotice_code(resultSet.getInt("notice_code"));
		    noticeDTO.setNotice_title(resultSet.getString("notice_title"));
		    noticeDTO.setNotice_content(resultSet.getString("notice_content"));
		    noticeDTO.setNotice_date(resultSet.getString("notice_date"));
		    arrayList.add(noticeDTO);
		 }
		 resultSet.getRow( );
		 if(resultSet.getRow( ) == 0) {
		 log.info("등록한 공지사항이 없습니다 공지사항을 등록해주세요");
		 }
		 } catch(Exception e) {
		 log.info("공지사항 목록 조회 실패 - " + e);
		 } finally {
		 try {
		 resultSet.close( );
		 preparedStatement.close( );
		 connection.close( );
		 } catch(SQLException e) {
		 e.printStackTrace( );
		 }
		 }
		 return arrayList;
		 }
	 @Override
	 public NoticeDTO noticeSelect(int notice_code) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 NoticeDTO noticeDTO = new NoticeDTO( );
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "SELECT notice_code, notice_title, notice_content, notice_date from notice WHERE notice_type = '공지사항'";
		 sql += " AND notice_code = ? ";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 preparedStatement.setInt(1, notice_code);
		 resultSet = preparedStatement.executeQuery( );
		 while(resultSet.next( )) {
				noticeDTO.setNotice_code(resultSet.getInt("notice_code"));
			    noticeDTO.setNotice_title(resultSet.getString("notice_title"));
			    noticeDTO.setNotice_content(resultSet.getString("notice_content"));
			    noticeDTO.setNotice_date(resultSet.getString("notice_date"));
			 }
		 } catch(Exception e) {
		 log.info("특정 공지사항 조회 실패 - " + e);
		 } finally {
		 try {
		 resultSet.close( );
		 preparedStatement.close( );
		 connection.close( );
		 } catch(SQLException e) {
		 e.printStackTrace( );
		 }
		 }
		 return noticeDTO;
		 }
	 @Override
	 public NoticeDTO noticeInsert(NoticeDTO noticeDTO) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();
	         
	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         String sql = "SELECT MAX(notice_code) FROM notice"; // 가장 큰 notice_code를 가져옴
	         preparedStatement = connection.prepareStatement(sql);
	         resultSet = preparedStatement.executeQuery();
	         
	         int maxNoticeCode = 0;
	         if (resultSet.next()) {
	             maxNoticeCode = resultSet.getInt(1);
	         }
	         
	         // 새로운 notice_code 생성
	         int newNoticeCode = maxNoticeCode + 1;
	         
	         sql = "INSERT INTO notice (notice_code, notice_title, notice_content, notice_date, notice_type) VALUES (?, ?, ?, ?, ?)";
	         log.info("SQL 확인 - " + sql);
	         preparedStatement = connection.prepareStatement(sql);  
	         preparedStatement.setInt(1, newNoticeCode); // 새로운 notice_code 사용
	         preparedStatement.setString(2, noticeDTO.getNotice_title());
	         preparedStatement.setString(3, noticeDTO.getNotice_content());
	         preparedStatement.setString(4, noticeDTO.getNotice_date());
	         preparedStatement.setString(5, noticeDTO.getNotice_type());

	         int count = preparedStatement.executeUpdate();
	         if (count > 0) {
	             connection.commit();
	             log.info("커밋되었습니다.");
	         } else {
	             connection.rollback();
	             log.info("롤백되었습니다.");
	         }
	     } catch (Exception e) {
	         log.info("글 입력 실패 - " + e);
	         try {
	             if (connection != null) {
	                 connection.rollback(); // 롤백 처리
	                 connection.setAutoCommit(true); // 다시 자동 커밋 모드로 변경
	             }
	         } catch (SQLException ex) {
	             log.error("롤백 실패", ex);
	         }
	     } finally {
	         try {
	             if (resultSet != null) {
	                 resultSet.close();
	             }
	             if (preparedStatement != null) {
	                 preparedStatement.close();
	             }
	             if (connection != null) {
	                 connection.close();
	             }
	         } catch (SQLException e) {
	             log.error("자원 해제 실패", e);
	         }
	     }
	     return noticeDTO;
	 }
	 @Override
	 public NoticeDTO noticeUpdate(NoticeDTO noticeDTO) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         String sql = "UPDATE notice SET notice_title = ?, notice_content = ? WHERE notice_code = ?";
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setString(1, noticeDTO.getNotice_title());
	         preparedStatement.setString(2, noticeDTO.getNotice_content());
	         preparedStatement.setInt(3, noticeDTO.getNotice_code());
	         log.info("SQL 확인 - " + sql);

	         int count = preparedStatement.executeUpdate();
	         if (count > 0) {
	             connection.commit();
	             log.info("커밋되었습니다.");
	         } else {
	             connection.rollback();
	             log.info("롤백되었습니다.");
	         }
	     } catch (Exception e) {
	         log.error("글 수정 실패", e); // 예외 메시지와 스택 트레이스를 로그에 추가
	         try {
	             if (connection != null) {
	                 connection.rollback(); // 롤백 처리
	             }
	         } catch (SQLException ex) {
	             log.error("롤백 실패", ex);
	         }
	     } finally {
	         try {
	             if (preparedStatement != null) {
	                 preparedStatement.close();
	             }
	             if (connection != null) {
	                 connection.setAutoCommit(true); // 다시 자동 커밋 모드로 변경
	                 connection.close();
	             }
	         } catch (SQLException e) {
	             log.error("자원 해제 실패", e);
	         }
	     }
	     return noticeDTO;
	 }


	 @Override
	 public NoticeDTO noticeDelete(int notice_code) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "delete from notice ";
		 sql += " where notice_code = ?";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 preparedStatement.setInt(1, notice_code);
		 //SQL인 delete…from…where 문을 실행하고 데이터를 삭제한다.
		 int count = preparedStatement.executeUpdate( );
		 if(count > 0) {
		 connection.commit( );
		 log.info("커밋되었습니다.");
		 } else {
		 connection.rollback( );
		 log.info("롤백되었습니다.");
		 }
		 } catch(Exception e) {
		 log.info("글 삭제 실패 - " + e);
		 } finally {
		 try {
		 preparedStatement.close( );
		 connection.close( );
		 } catch(SQLException e) {
		 e.printStackTrace( );
		 }
		 }
		 return null;
		 }
	 @Override
	 public ArrayList<NoticeDTO> noticeSelectEventAll( ) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 ArrayList<NoticeDTO> arrayList = new ArrayList<NoticeDTO>( );
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "SELECT notice_code, notice_title, notice_content, notice_date from notice WHERE notice_type = '이벤트'";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 resultSet = preparedStatement.executeQuery( );
		 while(resultSet.next( )) {
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setNotice_code(resultSet.getInt("notice_code"));
		    noticeDTO.setNotice_title(resultSet.getString("notice_title"));
		    noticeDTO.setNotice_content(resultSet.getString("notice_content"));
		    noticeDTO.setNotice_date(resultSet.getString("notice_date"));
		    arrayList.add(noticeDTO);
		 }
		 resultSet.getRow( );
		 if(resultSet.getRow( ) == 0) {
		 log.info("등록한 이벤트가 없습니다 이벤트를 등록해주세요");
		 }
		 } catch(Exception e) {
		 log.info("이벤트 목록 조회 실패 - " + e);
		 } finally {
		 try {
		 resultSet.close( );
		 preparedStatement.close( );
		 connection.close( );
		 } catch(SQLException e) {
		 e.printStackTrace( );
		 }
		 }
		 return arrayList;
		 }
	 @Override
	 public NoticeDTO noticeSelectEvent(int notice_code) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 NoticeDTO noticeDTO = new NoticeDTO( );
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "SELECT notice_code, notice_title, notice_content, notice_date from notice WHERE notice_type = '이벤트'";
		 sql += " AND notice_code = ? ";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 preparedStatement.setInt(1, notice_code);
		 resultSet = preparedStatement.executeQuery( );
		 while(resultSet.next( )) {
				noticeDTO.setNotice_code(resultSet.getInt("notice_code"));
			    noticeDTO.setNotice_title(resultSet.getString("notice_title"));
			    noticeDTO.setNotice_content(resultSet.getString("notice_content"));
			    noticeDTO.setNotice_date(resultSet.getString("notice_date"));
			 }
		 } catch(Exception e) {
		 log.info("특정 이벤트 조회 실패 - " + e);
		 } finally {
		 try {
		 resultSet.close( );
		 preparedStatement.close( );
		 connection.close( );
		 } catch(SQLException e) {
		 e.printStackTrace( );
		 }
		 }
		 return noticeDTO;
		 }
	 @Override
	 public NoticeDTO noticeUpdateEvent(NoticeDTO noticeDTO) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         String sql = "UPDATE notice SET notice_title = ?, notice_content = ? WHERE notice_code = ?";
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setString(1, noticeDTO.getNotice_title());
	         preparedStatement.setString(2, noticeDTO.getNotice_content());
	         preparedStatement.setInt(3, noticeDTO.getNotice_code());
	         log.info("SQL 확인 - " + sql);

	         int count = preparedStatement.executeUpdate();
	         if (count > 0) {
	             connection.commit();
	             log.info("커밋되었습니다.");
	         } else {
	             connection.rollback();
	             log.info("롤백되었습니다.");
	         }
	     } catch (Exception e) {
	         log.error("글 수정 실패", e); // 예외 메시지와 스택 트레이스를 로그에 추가
	         try {
	             if (connection != null) {
	                 connection.rollback(); // 롤백 처리
	             }
	         } catch (SQLException ex) {
	             log.error("롤백 실패", ex);
	         }
	     } finally {
	         try {
	             if (preparedStatement != null) {
	                 preparedStatement.close();
	             }
	             if (connection != null) {
	                 connection.setAutoCommit(true); // 다시 자동 커밋 모드로 변경
	                 connection.close();
	             }
	         } catch (SQLException e) {
	             log.error("자원 해제 실패", e);
	         }
	     }
	     return noticeDTO;
	 }

	 }

