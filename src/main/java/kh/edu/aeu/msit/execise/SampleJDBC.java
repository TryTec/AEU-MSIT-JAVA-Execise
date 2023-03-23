/**
 * 
 */
package kh.edu.aeu.msit.execise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @author Try
 *
 */
public class SampleJDBC {
	private static final Logger logger = LogManager.getLogger(SampleJDBC.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			SampleJDBC objSampleJDBC = new SampleJDBC();
			
			logger.info(" --- Sample JDBC --- ");
			
			Class.forName("com.mysql.jdbc.Driver");
			String baseUrl = "jdbc:mysql://localhost:3306/javacambodia-jdbc?useUnicode=true&characterEncoding=UTF-8&detectCustomCollations=true";
			String user = "root";
			String pass = "123456";
			
			Connection conn = DriverManager.getConnection(baseUrl, user, pass);
			logger.info("Database connected!");
			
			Statement stm = conn.createStatement();
			objSampleJDBC.readStudent(stm);
			
			objSampleJDBC.createStudent(stm, "San", "Sok");
			objSampleJDBC.createStudent(stm, "Sour", "Bopha");
			
			logger.info("All reading student: ");
			objSampleJDBC.readStudent(stm);
			
			stm.close();
			conn.close();
			logger.info("Connection has been closed.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("ClassNotFoundException: " + e.getMessage(), e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQLException: " + e.getMessage(), e);
		}
	}
	
	private void readStudent(Statement stm) throws SQLException {
		String sql = "SELECT stu_id, stu_first_name, stu_last_name FROM td_student";
		ResultSet rs = stm.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt("stu_id");
			String firstname = rs.getString("stu_first_name");
			String lastName = rs.getString("stu_last_name");
			
			logger.info("> " + id + " : " + firstname + " " + lastName);
		}
		logger.info("Read has been done");
		rs.close();
	}
	
	private void createStudent(Statement stm, String firstname, String lastname) throws SQLException {
		String sql = "INSERT INTO td_student (stu_first_name, stu_last_name) "
				+ "(SELECT '" + firstname + "', '" + lastname + "' FROM DUAL "
				+ "	WHERE NOT EXISTS (SELECT 1 FROM td_student "
				+ "			WHERE stu_first_name = '" + firstname + "' AND stu_last_name = '" + lastname + "'))";
		
		String fullname = firstname + " " + lastname;
		int index = stm.executeUpdate(sql);
		
		if (index == 1) {
			logger.info(fullname + " is created.");
		} else {
			logger.warn(fullname + " is not create.");
		}
	}
}
