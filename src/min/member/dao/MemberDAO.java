package min.member.dao;

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

import min.member.dto.MemberDTO;
import min.member.service.MemberService;

public class MemberDAO implements MemberService {
	private static final Log log = LogFactory.getLog(MemberDAO.class);

//제네릭 MemberDTO 클래스 반환 자료형으로 전체 데이터를 조회한다.
	@Override
	public ArrayList<MemberDTO> memberSelectAll(int page, int limit) {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>( );
		int startrow = (page - 1) * 10 + 1;
		int endrow = startrow + limit - 1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 쿼리문 입력
			String sql = "select * from (select rownum rnum, num, id, name, gender, age, to_char(birthday, 'yyyy-mm-dd') birthday, email, to_char(joinday, 'yyyy-mm-dd') joinday from (select * ";
			sql += "from member where id != 'admin' order by num desc))";
			sql += " where rnum between " + startrow + " and " + endrow;
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				MemberDTO memberDTO = new MemberDTO( );
				memberDTO.setNum(resultSet.getInt("num"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setGender(resultSet.getString("gender"));
				memberDTO.setAge(resultSet.getInt("age"));
				memberDTO.setBirthday(resultSet.getString("birthday"));
				memberDTO.setEmail(resultSet.getString("email"));
				memberDTO.setJoinday(resultSet.getString("joinday"));
				arrayList.add(memberDTO);
				log.info("조회 데이터 확인" + memberDTO);
			}
			if(resultSet.getRow( ) == 0) {
				log.info("등록된 회원이 없습니다.");
			}
		} catch(Exception e) {
			log.info("전체 회원 조회 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return arrayList;
	}

	// MemberDTO 반환 자료형으로 데이터를 입력한다.
	@Override
	public MemberDTO memberInsert(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "insert into member(id, name, password, gender, age, birthday, email, num) ";
			sql += " values (?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd'), ?, ?)";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getId( ));
			preparedStatement.setString(2, memberDTO.getName( ));
			preparedStatement.setString(3, memberDTO.getPassword( ));
			preparedStatement.setString(4, memberDTO.getGender( ));
			preparedStatement.setInt(5, memberDTO.getAge( ));
			preparedStatement.setString(6, memberDTO.getBirthday( ));
			preparedStatement.setString(7, memberDTO.getEmail( ));
			preparedStatement.setInt(8, memberDTO.getNum( ));
			int count = preparedStatement.executeUpdate( );
			log.info("입력 데이터 확인 - " + memberDTO);
			if(count > 0) {
				connection.commit( );
				log.info("커밋되었습니다.");
			} else {
				connection.rollback( );
				log.info("롤백되었습니다.");
			}
		} catch(Exception e) {
			log.info("회원 가입 실패 - " + e);
		} finally {
			try {
				connection.close( );
				preparedStatement.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

	// MemberDTO 클래스 반환 자료형으로 데이터를 수정한다.
	@Override
	public MemberDTO memberUpdate(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		log.info("업데이트 정보 - " + memberDTO);
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "update member set name = ?,  password = ?,  email = ?, gender = ?, age = ?, birthday = to_date(?, 'yyyy-mm-dd')  ";
			sql += "where id = ?";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getName( ));
			preparedStatement.setString(2, memberDTO.getPassword( ));
			preparedStatement.setString(3, memberDTO.getEmail( ));
			preparedStatement.setString(4, memberDTO.getGender( ));
			preparedStatement.setInt(5, memberDTO.getAge( ));
			preparedStatement.setString(6, memberDTO.getBirthday( ));
			preparedStatement.setString(7, memberDTO.getId( ));
			int count = preparedStatement.executeUpdate( );
			log.info("수정 데이터 확인 - " + memberDTO);
			if(count > 0) {
				connection.commit( );
				log.info("커밋되었습니다.");
			} else {
				connection.rollback( );
				log.info("롤백되었습니다.");
			}
		} catch(Exception e) {
			log.info("회원 정보 수정 실패 - " + e);
		} finally {
			try {
				connection.close( );
				preparedStatement.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

	// MemberDTO 클래스 반환 자료형으로 데이터를 삭제한다.
	@Override
	public MemberDTO memberDelete(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "delete from member ";
			sql += " where id = ? ";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getId( ));
			int count = preparedStatement.executeUpdate( );
			if(count > 0) {
				connection.commit( );
				log.info("커밋되었습니다.");
			} else {
				connection.rollback( );
				log.info("롤백되었습니다.");
			}
		} catch(Exception e) {
			log.info("회원 삭제 실패 - " + e);
		} finally {
			try {
				connection.close( );
				preparedStatement.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

//MemberDTO 클래스 반환 자료형으로 특정 회원 데이터를 조회한다.
	@Override
	public MemberDTO memberSelect(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 쿼리문 작성
			String sql = "select num, id, name, password, gender, age, to_char(birthday, 'yyyy-mm-dd') birthday, email,  to_char(joinday, 'yyyy-mm-dd') joinday from member";
			sql += " where id = ? ";
			// sql += " where id = '" + memberDTO.getId( ) + "'";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getId( ));
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				log.info("아이디 확인 - " + resultSet.getString("id"));
				memberDTO.setNum(resultSet.getInt("num"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setPassword(resultSet.getString("password"));
				memberDTO.setGender(resultSet.getString("gender"));
				memberDTO.setAge(resultSet.getInt("age"));
				memberDTO.setBirthday(resultSet.getString("birthday"));
				memberDTO.setEmail(resultSet.getString("email"));
				memberDTO.setJoinday(resultSet.getString("joinday"));
			}
		} catch(Exception e) {
			log.info("개별 회원 조회 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

//int 반환 자료형으로 입력 데이터의 번호를 구한다.
	@Override
	public int memberNumber( ) {
		int i = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select max(num) from member";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			if(resultSet.next( )) {
				i = resultSet.getInt("max(num)");
			}
		} catch(Exception e) {
			log.info("최대 회원 번호 검색 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return i;
	}

//int 반환 자료형으로 입력 데이터의 개수를 구한다.
	@Override
	public int memberCount( ) {
		int i = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select count(*) from member where id != 'admin'";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				i = resultSet.getInt(1);
			}
		} catch(Exception e) {
			log.info("현재 회원 수 검색 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return i;
	}

//제네릭 MemberDTO 클래스 반환 자료형으로 데이터를 검색한다.
	@Override
	public ArrayList<MemberDTO> memberSearch(String keyword, int page, int limit) {
		log.info("검색 단어 확인 - " + keyword);
		String searchCall = "";
		if(!keyword.equals("")) {
			searchCall = "where id like '%" + keyword + "%'";
		}
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>( );
		int startrow = (page - 1) * 10 + 1;
		int endrow = startrow + limit - 1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select * from (select rownum rnum, id, name, gender, age, to_char(birthday, 'yyyy-mm-dd') birthday, email, num, to_char(joinday, 'yyyy-mm-dd') joinday  from (select * ";
			sql += "from member " + searchCall + " order by num desc) where id != 'admin')";
			sql += " where rnum between " + startrow + " and " + endrow;
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				MemberDTO memberDTO = new MemberDTO( );
				memberDTO.setNum(resultSet.getInt("num"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setGender(resultSet.getString("gender"));
				memberDTO.setAge(resultSet.getInt("age"));
				memberDTO.setBirthday(resultSet.getString("birthday"));
				memberDTO.setEmail(resultSet.getString("email"));
				memberDTO.setJoinday(resultSet.getString("joinday"));
				arrayList.add(memberDTO);
				log.info("조회 데이터 확인" + memberDTO);
			}
			if(resultSet.getRow( ) == 0) {
				log.info("등록된 회원이 없습니다.");
			}
		} catch(Exception e) {
			log.info("회원 포인트 검색 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return arrayList;
	}

//int 반환 자료형으로 검색 데이터의 개수를 구한다.
	@Override
	public int memberSearchCount(String keyword) {
		int i = 0;
		log.info("검색 단어 확인 - " + keyword);
		String searchCall = "";
		if(!keyword.equals("")) {
			searchCall = "id like '%" + keyword + "%'";
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select count(*) from (select * from member where id != 'admin') where " + searchCall;
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				i = resultSet.getInt(1);
			}
		} catch(Exception e) {
			log.info("아이디 검색 회원 수 검색 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return i;
	}

//MemberDTO 클래스 반환 자료형으로 로그인 데이터를 조회한다.
	@Override
	public MemberDTO memberLogin(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select * from member ";
			sql += "where id = ?";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getId( ));
			// SQL 문인 select…from…where 문을 실행하고 데이터를 조회한다.
			resultSet = preparedStatement.executeQuery( );
			// 커서를 현재 위치에서 한 행 앞으로 이동하면서 조건을 확인한다.
			if(resultSet.next( )) {
				// 입력한 아이디와 비밀번호가 기존의 아이디와 비밀번호가 일치하는지를 검사한다.
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setId(resultSet.getString("id"));
				log.info("아이디 확인 - " + resultSet.getString("id"));
				if(resultSet.getString("password").equals(memberDTO.getPassword( ))) {
					memberDTO.setPassword(resultSet.getString("password"));
					log.info("비밀번호 확인 - " + resultSet.getString("password"));
				} else {
					memberDTO.setPassword("");
				}
			} else {
				memberDTO.setId("");
			}
		} catch(Exception e) {
			log.info("로그인 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

//int 반환 자료형으로 아이디 데이터를 조회한다.
	@Override
	public int memberId(String id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int idCheck = 0;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "select * from member ";
			sql += "where id=? ";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( ) || id.equals("")) {

				// 존재하는 경우
				idCheck = 1;
			} else {
//	 존재하지 않는 경우
				idCheck = 0;
			}

		} catch(Exception e) {
			log.info("회원 아이디 체크 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return idCheck;
	}

//MemberDTO 클래스 반환 자료형으로 아이디 데이터를 검색한다.
	@Override
	public MemberDTO memberSearchId(String name, String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		MemberDTO memberDTO = new MemberDTO( );
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 쿼리문 작성
			String sql = "select * from member where name=? and email=?";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				memberDTO.setNum(resultSet.getInt("num"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setPassword(resultSet.getString("password"));
				memberDTO.setEmail(resultSet.getString("email"));

				log.info("조회 데이터 확인" + memberDTO);
			}
		} catch(Exception e) {
			log.info("아이디 찾기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

//MemberDTO 클래스 반환 자료형으로 비밀번호 데이터를 검색한다.
	public MemberDTO memberSearchPassword(String id, String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		MemberDTO memberDTO = new MemberDTO( );
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 쿼리문 작성
			String sql = "select * from member where id=? and email=?";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, email);
			resultSet = preparedStatement.executeQuery( );
			while(resultSet.next( )) {
				memberDTO.setNum(resultSet.getInt("num"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setPassword(resultSet.getString("password"));
				memberDTO.setEmail(resultSet.getString("email"));
				log.info("조회 데이터 확인" + memberDTO);
			}
		} catch(Exception e) {
			log.info("비밀번호 찾기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(Exception e) {
				e.printStackTrace( );
			}
		}
		return memberDTO;
	}

}
