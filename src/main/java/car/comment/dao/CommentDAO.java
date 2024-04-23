package car.comment.dao;

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

import car.comment.dto.CommentDTO;
import car.comment.service.CommentService;

public class CommentDAO implements CommentService {
	private static Log log = LogFactory.getLog(CommentDAO.class);
	 @Override
	 public ArrayList<CommentDTO> commentSelectAll(int parking_code) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 ArrayList<CommentDTO> arrayList = new ArrayList<CommentDTO>( );
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "SELECT comment_code, comment_content, comment_date, member_code from comments where parking_code = ?";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 preparedStatement.setInt(1, parking_code); 
		 resultSet = preparedStatement.executeQuery( );
		 while(resultSet.next( )) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setComment_code(resultSet.getInt("comment_code"));
		    commentDTO.setComment_content(resultSet.getString("comment_content"));
		    commentDTO.setComment_date(resultSet.getString("comment_date"));
		    commentDTO.setMember_code(resultSet.getInt("member_code"));
		    arrayList.add(commentDTO);
		 }
		 resultSet.getRow( );
		 if(resultSet.getRow( ) == 0) {
		 log.info("등록한 리뷰 댓글이 없습니다 댓글을 등록해주세요");
		 }
		 } catch(Exception e) {
		 log.info("리뷰 댓글 목록 조회 실패 - " + e);
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
	 public CommentDTO commentSelect(int comment_code) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;
	     CommentDTO commentDTO = null;

	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 댓글 코드를 기반으로 댓글 상세 조회하는 쿼리
	         String sql = "SELECT comment_code, comment_content, comment_date, parking_code FROM comments WHERE comment_code = ?";
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setInt(1, comment_code);
	         resultSet = preparedStatement.executeQuery();

	         if (resultSet.next()) {
	             commentDTO = new CommentDTO();
	             commentDTO.setComment_code(resultSet.getInt("comment_code"));
	             commentDTO.setComment_content(resultSet.getString("comment_content"));
	             commentDTO.setComment_date(resultSet.getString("comment_date"));
	             commentDTO.setParking_code(resultSet.getInt("parking_code")); // 주차장 코드 설정
	         } else {
	             log.info("해당 댓글 코드에 속하는 댓글이 없습니다.");
	         }
	     } catch (Exception e) {
	         log.info("댓글 상세 조회 실패 - " + e);
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
	             e.printStackTrace();
	         }
	     }
	     return commentDTO;
	 }

		 //DeptDTO 클래스 반환 타입으로 데이터를 입력한다.
	 @Override
	 public CommentDTO commentInsert(CommentDTO commentDTO, int member_code) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         // 가장 큰 댓글 코드 조회
	         String maxCommentCodeQuery = "SELECT MAX(comment_code) FROM comments";
	         preparedStatement = connection.prepareStatement(maxCommentCodeQuery);
	         resultSet = preparedStatement.executeQuery();

	         int maxCommentCode = 0;
	         if (resultSet.next()) {
	             maxCommentCode = resultSet.getInt(1);
	         }

	         // 새로운 댓글 코드 생성
	         int newCommentCode = maxCommentCode + 1;

	         // parking_code 설정
	         int parkingCode = 3; // 변경 가능

	         // 삽입 쿼리 준비
	         String insertQuery = "INSERT INTO comments (comment_code, parking_code, comment_content, comment_date, member_code) VALUES (?, ?, ?, ?, ?)";
	         log.info("SQL 확인 - " + insertQuery);
	         preparedStatement = connection.prepareStatement(insertQuery);
	         preparedStatement.setInt(1, newCommentCode); // 새로운 comment_code 사용
	         preparedStatement.setInt(2, parkingCode); // parking_code 설정
	         preparedStatement.setString(3, commentDTO.getComment_content());
	         preparedStatement.setString(4, commentDTO.getComment_date());
	         preparedStatement.setInt(5, member_code); // 세션에서 가져온 회원 코드 사용

	         // 쿼리 실행 및 커밋 처리
	         int count = preparedStatement.executeUpdate();
	         if (count > 0) {
	             connection.commit();
	             log.info("커밋되었습니다.");
	         } else {
	             connection.rollback();
	             log.info("롤백되었습니다.");
	         }
	     } catch (Exception e) {
	         log.info("댓글 입력 실패 - " + e);
	         try {
	             if (connection != null) {
	                 connection.rollback(); // 롤백 처리
	                 connection.setAutoCommit(true); // 다시 자동 커밋 모드로 변경
	             }
	         } catch (SQLException ex) {
	             log.error("롤백 실패", ex);
	         }
	     } finally {
	         // 자원 해제
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
	     return commentDTO;
	 }


		 @Override
		 public CommentDTO commentUpdate(CommentDTO commentDTO) {
			  Connection connection = null;
			  PreparedStatement preparedStatement = null;
			     try {
			         Context context = new InitialContext();
			         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			         connection = dataSource.getConnection();

			         // 자동 커밋 해제
			         connection.setAutoCommit(false);

			         String sql = "UPDATE comments SET comment_content = ? WHERE comment_code = ?";
			         preparedStatement = connection.prepareStatement(sql);
			         preparedStatement.setString(1, commentDTO.getComment_content());
			         preparedStatement.setInt(2, commentDTO.getComment_code());
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
			     return commentDTO;
			 }
		 @Override
		 public CommentDTO commentDelete(int comment_code) {
		        Connection connection = null;
		        PreparedStatement preparedStatement = null;
		        CommentDTO deletedComment = null;
		        try {
		            Context context = new InitialContext();
		            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		            connection = dataSource.getConnection();
		            String sql = "DELETE FROM comments WHERE comment_code = ?";
		            log.info("SQL 확인 - " + sql);
		            preparedStatement = connection.prepareStatement(sql);
		            preparedStatement.setInt(1, comment_code);
		            int count = preparedStatement.executeUpdate();
		            if (count > 0) {
		                connection.commit();
		                log.info("댓글이 삭제되었습니다.");
		                deletedComment = new CommentDTO();
		                deletedComment.setComment_code(comment_code);
		            } else {
		                connection.rollback();
		                log.info("댓글 삭제 실패: 해당하는 댓글이 없습니다.");
		            }
		        } catch (Exception e) {
		            log.info("댓글 삭제 실패 - " + e);
		        } finally {
		            try {
		                if (preparedStatement != null) {
		                    preparedStatement.close();
		                }
		                if (connection != null) {
		                    connection.close();
		                }
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        return deletedComment;
		    }
		}


