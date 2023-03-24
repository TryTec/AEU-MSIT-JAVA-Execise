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

//			objSampleJDBC.readStudent(stm);

//			objSampleJDBC.createStudent(stm, "Mean", "Ban", "2000-03-24");
//			objSampleJDBC.createStudent(stm, "Rich", "Ray", "2001-01-12");
			
//			objSampleJDBC.deleteStudent(stm, 4);
			 
			logger.info("All reading student: ");
			objSampleJDBC.readStudent(stm);
			
//			objSampleJDBC.readLession(stm);
//			objSampleJDBC.createLesson(stm, "Lesson4");
//			objSampleJDBC.deleteLesson(stm, 4);
			
			logger.info("All reading lesson: ");
			objSampleJDBC.readLession(stm);

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
		String sql = "SELECT stu_id, stu_first_name, stu_last_name, stu_DOB FROM td_student";
		ResultSet rs = stm.executeQuery(sql);

		while (rs.next()) {
			int id = rs.getInt("stu_id");
			String firstname = rs.getString("stu_first_name");
			String lastName = rs.getString("stu_last_name");
			String dob = rs.getString("stu_DOB");

			logger.info("> " + id + " : " + firstname + " " + lastName + " " + dob);
		}
		logger.info("Read student has been done");
		rs.close();
	}

	private void createStudent(Statement stm, String firstname, String lastname, String dob) throws SQLException {
		String sql = "INSERT INTO td_student (stu_first_name, stu_last_name, stu_DOB) " + 
				"VALUES('" + firstname + "', '" + lastname + "', '" + dob + "');";

		String fullname = firstname + " " + lastname;
		int status = stm.executeUpdate(sql);

		if (status == 1) {
			logger.info(fullname + " is created.");
		} else {
			logger.warn(fullname + " is not create.");
		}
	}

	private void deleteStudent(Statement stm, int stu_id) throws SQLException {
		String sql = "DELETE FROM td_student WHERE stu_id = " + stu_id + ";";
		int status = stm.executeUpdate(sql);

		if (status == 1) {
			logger.info("this record id " + stu_id + " has been deleted.");
		} else {
			logger.warn("delete fail for id " + stu_id);
		}
	}
	
	private void readLession(Statement stm) throws SQLException {
		String sql = "SELECT les_id, les_name FROM td_lession;";
		ResultSet rs = stm.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("les_id");
			String les_name = rs.getString("les_name");
			
			logger.info("> " + id + " : " + les_name);
		}
		logger.info("Read lession has been done");
		rs.close();
	}

	private void createLesson(Statement stm, String les_name) throws SQLException {
		String sql = "INSERT INTO td_lession (les_name) VALUES('" + les_name + "');";
		
		int status = stm.executeUpdate(sql);
		
		if (status == 1) {
			logger.info(les_name + " is created.");
		} else {
			logger.warn(les_name + " is not create.");
		}
	}

	private void deleteLesson(Statement stm, int les_id) throws SQLException {
		String sql = "DELETE FROM td_lession WHERE les_id = " + les_id + ";";
		int status = stm.executeUpdate(sql);
		
		if (status == 1) {
			logger.info("this record id " + les_id + " has been deleted.");
		} else {
			logger.info("delete fail for id " + les_id);
		}
	}
}
