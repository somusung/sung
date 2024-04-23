package car.term.dao;

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

import car.term.dto.TermDTO;
import car.term.service.TermService;

public class TermDAO implements TermService{
	private static Log log = LogFactory.getLog(TermDAO.class);
	@Override
	public ArrayList<TermDTO> termSelectAll() {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<TermDTO> arrayList = new ArrayList<TermDTO>();
	    try {
	        Context context = new InitialContext();
	        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
	        connection = dataSource.getConnection();
	        String sql = "SELECT TERM_CODE, TERM_CONTENT FROM TERM ";
	        log.info("SQL 확인 - " + sql);
	        preparedStatement = connection.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            TermDTO termDTO = new TermDTO();
	            termDTO.setTerm_code(resultSet.getInt("TERM_CODE"));
	            termDTO.setTerm_content(resultSet.getString("TERM_CONTENT"));
	            arrayList.add(termDTO);
	        }
	        resultSet.getRow();
	        if (resultSet.getRow() == 0) {
	            log.info("등록된 이용약관이 없습니다. 약관을 등록해주세요.");
	        }
	    } catch (Exception e) {
	        log.info("이용약관 조회 실패 - " + e);
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

}
