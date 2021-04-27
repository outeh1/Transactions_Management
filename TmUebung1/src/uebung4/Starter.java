package uebung4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Starter {
	private static final BigDecimal betrag = BigDecimal.valueOf(100, 0);
	private static final Integer TransferThreadNum = 1;
	private static final Integer ThreadTransferNum = 100;
	private static final String deleteKontenSQL = "DELETE FROM Data;";
	private static final String insertUsersSQL = "INSERT INTO Data(x, y) VALUES (100, 100);";

//	CREATE TABLE "Data" (
//			"x"	INTEGER NOT NULL,
//			"y"	INTEGER NOT NULL
//		);
	public static void main(String[] args) {
		final String mysqliteURL = "jdbc:sqlite:UE4.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";

		final String url = mysqliteURL;
		try (final Connection conn = DriverManager.getConnection(url)) {
			System.out.println("Verbunden mit Datenbank" + url);

			ResetTable(conn);

			List<uebung4.TransactionThread> Threads = new ArrayList<uebung4.TransactionThread>();
			for (int i = 0; i < TransferThreadNum; i++) {
				Threads.add(new uebung4.TransactionThread(1, url, betrag));
				Threads.add(new uebung4.TransactionThread(2, url, betrag));
				Threads.add(new uebung4.TransactionThread(3, url, betrag));
			}
			for (uebung4.TransactionThread thread : Threads) {
				thread.start();
			}
			for (uebung4.TransactionThread thread : Threads) {
				thread.join();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Setzt die Datenbank auf einen Basiszustand zurück.
	 * 
	 * @param conn
	 */
	public static void ResetTable(Connection conn) {
		try (final Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(deleteKontenSQL);
			System.out.println("Konten deleted.");
			stmt.executeUpdate(insertUsersSQL);
			System.out.println("Users inserted.");
		} catch (SQLException e) {
			System.err.println("Database " + conn + " didn't like being reset.");
			;
			e.printStackTrace();
		}

	}

	private static void write(Connection conn, String Datenbestand, BigDecimal betrag) throws SQLException {
		final String selectBetrag = "Select ? From Data Where 1";
		final String updateBetrag = "Update ? Set ? = ?";
		try (final PreparedStatement stmt = conn.prepareStatement(updateBetrag)) {
			stmt.setString(1, Datenbestand);
			int count = stmt.executeUpdate();
			if (count == 1) {
				System.out.println("Kontostand erfolgreich geändert");
			}
//			if (rs.next()) {
//				BigDecimal kontostand = rs.getBigDecimal("betrag");
//				System.out.printf("Der Kontostand von %s beträgt %s%n", kunde, kontostand);
//				kontostand = betrag;
//				System.out.printf("der Kontostand von %s soll geänder werden, auf %s%n", kunde, kontostand);
//				try (final PreparedStatement us = conn.prepareStatement(updateBetrag)) {
//					us.setBigDecimal(1, kontostand);
//					us.setString(2, kunde);
//					int count = us.executeUpdate();
//					if (count == 1) {
//						System.out.println("Kontostand erfolgreich geändert");
//
//					}
//				}
//			}

		}
	}
}
