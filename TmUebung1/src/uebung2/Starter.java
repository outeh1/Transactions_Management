package uebung2;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Starter {

	public static void main(String[] args) {
		final String mysqliteURL = "jdbc:sqlite:helloFhdw.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";
		
		final String url = postgreSQLURL;
		try ( final Connection conn = DriverManager . getConnection ( url )) {
			System.out.println("Verbunden mit Datenbank" + url);
			/*
			 * CREATE TABLE "konten" (
			 * "kto"	INTEGER NOT NULL,
			 * "betrag"	INTEGER NOT NULL,
			 * "kunde"	TEXT NOT NULL,
			 * PRIMARY KEY("kto")
			 * );
			 */
			
			
							
			final String von = "A";
			final String nach = "B";
			final BigDecimal betrag = BigDecimal.valueOf(100, 0);
			
				
			}
		catch (SQLException e) {
				e.printStackTrace();

	}
	}
}


