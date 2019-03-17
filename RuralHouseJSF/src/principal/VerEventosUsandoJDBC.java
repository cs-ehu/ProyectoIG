package principal;

import java.sql.*;
import java.util.Date;

public class VerEventosUsandoJDBC {
	public static void main(String[] args) {
		Connection c;
		Statement s;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost/eventos", "root", "root");
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM EVENTOLOGIN");
			System.out.println("EVENTOLOGIN (ID, DESCRIPCION, FECHA");
			while (rs.next()) {
				Long id = rs.getLong("ID");
				String descripcion = rs.getString("DESCRIPCION");
				Date fecha = rs.getDate("FECHA");
				System.out.println(id + " / " + descripcion + " / " + fecha);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}