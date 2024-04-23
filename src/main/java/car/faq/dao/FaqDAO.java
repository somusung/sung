package car.faq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import car.faq.dto.FaqDTO;
import car.faq.service.FaqService;




public class FaqDAO implements FaqService {
	private static Log log = LogFactory.getLog(FaqDAO.class);
	@Override
	public ArrayList<FaqDTO> faqSelectAll() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<FaqDTO> arrayList = new ArrayList<FaqDTO>();
	    try {
	        Context context = new InitialContext();
	        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	        connection = dataSource.getConnection();
	        String sql = "SELECT FAQ_CODE, FAQ_TITLE, FAQ_QUESTION_DATE FROM FAQ ORDER BY faq_code";
	        log.info("SQL 확인 - " + sql);
	        preparedStatement = connection.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            FaqDTO faqDTO = new FaqDTO();
	            faqDTO.setFaq_code(resultSet.getInt("FAQ_CODE"));
	            faqDTO.setFaq_title(resultSet.getString("FAQ_TITLE"));
	            faqDTO.setFaq_question_date(resultSet.getString("FAQ_QUESTION_DATE"));
	            arrayList.add(faqDTO);
	        }
	        resultSet.getRow();
	        if (resultSet.getRow() == 0) {
	            log.info("등록된 FAQ가 없습니다. FAQ를 등록해주세요.");
	        }
	    } catch (Exception e) {
	        log.info("FAQ 목록 조회 실패 - " + e);
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return arrayList;
	}
	@Override
	public FaqDTO faqSelect(int faq_code, int member_code) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    FaqDTO faqDTO = new FaqDTO();
	    try {
	        Context context = new InitialContext();
	        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	        connection = dataSource.getConnection();
	        String sql = "SELECT faq_code, faq_answer_code, faq_title, faq_question, faq_answer, faq_question_date, faq_answer_date, member_code FROM faq WHERE faq_code = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, faq_code);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            int faq_member_code = resultSet.getInt("member_code");
	            if (member_code == faq_member_code) {
	                // 현재 사용자가 작성한 게시글인 경우, 수정 및 삭제 권한을 부여
	                faqDTO.setEditable(true);
	            } else {
	                // 다른 사용자가 작성한 게시글인 경우, 수정 및 삭제 권한을 제한
	                faqDTO.setEditable(false);
	            }
	            faqDTO.setFaq_code(resultSet.getInt("faq_code"));
	            faqDTO.setFaq_answer_code(resultSet.getInt("faq_answer_code"));
	            faqDTO.setFaq_title(resultSet.getString("faq_title"));
	            faqDTO.setFaq_question(resultSet.getString("faq_question"));
	            faqDTO.setFaq_answer(resultSet.getString("faq_answer"));
	            faqDTO.setFaq_question_date(resultSet.getString("faq_question_date"));
	            faqDTO.setFaq_answer_date(resultSet.getString("faq_answer_date"));
	            faqDTO.setMember_code(resultSet.getInt("member_code")); // member_code 설정 추가
	        }
	    } catch (Exception e) {
	        log.info("특정 게시글 조회 실패 - " + e);
	    } finally {
	        try {
	            resultSet.close();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return faqDTO;
	}

	 @Override
	 public ArrayList<FaqDTO> faqSearch(String faq_title) {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        ArrayList<FaqDTO> arrayList = new ArrayList<>();
	        try {
	            Context context = new InitialContext();
	            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	            connection = dataSource.getConnection();

	            String sql = "SELECT * FROM FAQ WHERE FAQ_TITLE LIKE ?";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, "%" + faq_title + "%");

	            resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                FaqDTO faqDTO = new FaqDTO();
	                faqDTO.setFaq_code(resultSet.getInt("FAQ_CODE"));
	                faqDTO.setFaq_title(resultSet.getString("FAQ_TITLE"));
	                faqDTO.setFaq_question(resultSet.getString("FAQ_QUESTION"));
	                faqDTO.setFaq_question_date(resultSet.getString("FAQ_QUESTION_DATE"));
	                arrayList.add(faqDTO);
	            }
	        } catch (Exception e) {
	            log.error("게시글 검색 실패: " + e);
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                log.error("DB 자원 해제 실패: " + e);
	            }
	        }
	        log.info("검색 결과: " + arrayList);
	        return arrayList;
	    }
	// Service Layer에서 DAO 메서드 호출 부분 수정
	 @Override
	 public FaqDTO faqInsert(FaqDTO faqDTO, int member_code) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     ResultSet resultSet = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         // 새로운 faq_code 생성
	         String sql = "SELECT faq_code_seq.NEXTVAL FROM DUAL";
	         preparedStatement = connection.prepareStatement(sql);
	         resultSet = preparedStatement.executeQuery();

	         int newFaqCode = 0;
	         if (resultSet.next()) {
	             newFaqCode = resultSet.getInt(1);
	         }

	         // 새로운 faq_answer_code 생성
	         sql = "SELECT faq_answer_code_seq.NEXTVAL FROM DUAL";
	         preparedStatement = connection.prepareStatement(sql);
	         resultSet = preparedStatement.executeQuery();

	         int newFaqAnswerCode = 0;
	         if (resultSet.next()) {
	             newFaqAnswerCode = resultSet.getInt(1);
	         }

	         sql = "INSERT INTO faq (faq_code, faq_title, faq_question, faq_question_date, faq_answer_code, member_code) VALUES (?, ?, ?, ?, ?, ?)";
	         log.info("SQL 확인 - " + sql);
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setInt(1, newFaqCode); // 새로운 faq_code 설정
	         preparedStatement.setString(2, faqDTO.getFaq_title());
	         preparedStatement.setString(3, faqDTO.getFaq_question());
	         preparedStatement.setString(4, faqDTO.getFaq_question_date());
	         preparedStatement.setInt(5, newFaqAnswerCode); // 새로운 faq_answer_code 설정
	         preparedStatement.setInt(6, member_code); // 세션으로부터 가져온 멤버 코드 설정
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
	     return faqDTO;
	 }

	 @Override
	 public FaqDTO faqUpdate(FaqDTO faqDTO) {
		 Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         String sql = "UPDATE faq SET faq_title = ?, faq_question = ? WHERE faq_code = ?";
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setString(1, faqDTO.getFaq_title());
	         preparedStatement.setString(2, faqDTO.getFaq_question());
	         preparedStatement.setInt(3, faqDTO.getFaq_code());
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
	         log.error("게시글 수정 실패", e); // 예외 메시지와 스택 트레이스를 로그에 추가
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
	     return faqDTO;
	 }
	 //DeptDTO 클래스 반환 타입으로 데이터를 삭제한다.
	 @Override
	 public FaqDTO faqDelete(int faq_code) {
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 try {
		 Context context = new InitialContext( );
		 DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		 connection = dataSource.getConnection( );
		 String sql = "delete from faq ";
		 sql += " where faq_code = ?";
		 log.info("SQL 확인 - " + sql);
		 preparedStatement = connection.prepareStatement(sql);
		 preparedStatement.setInt(1, faq_code);
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
		 log.info("게시글 삭제 실패 - " + e);
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
	 public FaqDTO faqAnswerSelect(int faq_answer_code) {
		    Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;
		    FaqDTO faqDTO = new FaqDTO();

		    try {
		        Context context = new InitialContext();
		        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		        connection = dataSource.getConnection();
		        
		        // 댓글이 작성된 게시물의 코드를 조회하는 쿼리
		        String query = "SELECT faq_code FROM faq WHERE faq_answer_code = ?";
		        preparedStatement = connection.prepareStatement(query);
		        preparedStatement.setInt(1, faq_answer_code);
		        resultSet = preparedStatement.executeQuery();
		        
		        // 댓글이 작성된 게시물의 코드를 가져옴
		        int faq_code = 0;
		        if (resultSet.next()) {
		            faq_code = resultSet.getInt("faq_code");
		        } else {
		            log.info("해당 댓글 코드에 속하는 댓글이 없습니다.");
		            return null; // 댓글이 없으면 null 반환
		        }

		        // 해당 댓글이 작성된 게시물의 코드와 댓글 내용 등을 조회하는 쿼리
		        String sql = "SELECT faq_answer_code, faq_answer, faq_answer_date FROM faq";
		        sql += " WHERE faq_answer_code = ? AND faq_code = ?";
		        log.info("SQL 확인 - " + sql);
		        preparedStatement = connection.prepareStatement(sql);
		        preparedStatement.setInt(1, faq_answer_code);
		        preparedStatement.setInt(2, faq_code); // 게시물 코드 설정
		        resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            faqDTO.setFaq_answer_code(resultSet.getInt("faq_answer_code"));
		            faqDTO.setFaq_answer(resultSet.getString("faq_answer"));
		            faqDTO.setFaq_answer_date(resultSet.getString("faq_answer_date"));
		            faqDTO.setFaq_code(faq_code); // 게시물 코드 설정
		        } else {
		            log.info("댓글이 없거나 해당 댓글 코드에 속하는 댓글이 없습니다.");
		        }
		    } catch (Exception e) {
		        log.info("특정 댓글 조회 실패 - " + e);
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
		    return faqDTO;
		}
	 
	 
	 @Override
	 public FaqDTO faqAnswerUpdate(FaqDTO faqDTO) {
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     try {
	         Context context = new InitialContext();
	         DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	         connection = dataSource.getConnection();

	         // 자동 커밋 해제
	         connection.setAutoCommit(false);

	         String sql = "UPDATE faq SET faq_answer = ? WHERE faq_answer_code = ?";
	         preparedStatement = connection.prepareStatement(sql);
	         preparedStatement.setString(1, faqDTO.getFaq_answer());
	         preparedStatement.setInt(2, faqDTO.getFaq_answer_code());
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
	         log.error("댓글 수정 실패", e); // 예외 메시지와 스택 트레이스를 로그에 추가
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
	     return faqDTO;
	 }

	 @Override
	 public FaqDTO faqAnswerDelete(int faq_answer_code) {
		 Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    try {
		        Context context = new InitialContext();
		        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
		        connection = dataSource.getConnection();
		        
		       String sql = "UPDATE faq SET faq_answer = NULL WHERE faq_answer_code = ?";
		        preparedStatement = connection.prepareStatement(sql);
		        preparedStatement.setInt(1, faq_answer_code);

		        int count = preparedStatement.executeUpdate();
		        if (count > 0) {
		            connection.commit();
		            log.info("댓글이 삭제되었습니다.");
		        } else {
		            connection.rollback();
		            log.info("댓글 삭제 실패");
		        }
		    } catch (Exception e) {
		        log.error("댓글 삭제 중 오류 발생", e);
		    } finally {
		        try {
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
		    return null;
		}
	 
}

	 


