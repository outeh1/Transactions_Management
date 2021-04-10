package uebung2;

import java.sql.Connection;
import java.sql.*;

public class StartLostUpdates {

	public static void main(String[] args) {
		/**
		 * Table used:
		 * CREATE TABLE "Counter" (
		 * "CurrentCount"	INTEGER NOT NULL,
		 * PRIMARY KEY("CurrentCount")
		 * );
		 */
		final String mysqliteURL = "jdbc:sqlite:LostUpdates.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";
		
		final String url = mysqliteURL;
		try ( final Connection conn = DriverManager . getConnection ( url )) {
			System.out.println("Verbunden mit Datenbank" + url);
	}catch (SQLException e) {
		// TODO: handle exception
	}

}
}
