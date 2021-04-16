package uebung1;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Minimalbeispiel {
	private static boolean Verbosity = true;
	private static final String deleteKontenSQL = "DELETE FROM konten;";
	private static final String insertUsersSQL = "INSERT INTO konten(kto, betrag, kunde) VALUES (1, 1000, 'A'), (2, 1000, 'B'), (3, 1000, 'C');";

	/**
	 * Opens a Databaseconnection and does Stuff with it
	 */
	public static void main(String[] args) {
		final String mysqliteURL = "jdbc:sqlite:helloFhdw.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";

		final String url = mysqliteURL;
		try (final Connection conn = DriverManager.getConnection(url)) {
			System.out.println("Verbunden mit Datenbank" + url);
			/*
			 * CREATE TABLE "konten" ( "kto" INTEGER NOT NULL, "betrag" INTEGER NOT NULL,
			 * "kunde" TEXT NOT NULL, PRIMARY KEY("kto") );
			 */

			kundenListing(conn);

			final String von = "A";
			final String nach = "B";
			final BigDecimal betrag = BigDecimal.valueOf(100, 0);
			bankTransfer(conn, von, nach, betrag);

			kundenListing(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * toggles verbosity
	 */
	public static void toggleVerbose() {
		if (Verbosity) {
			Verbosity = false;
		} else {
			Verbosity = true;
		}
	}

	/**
	 * Lists every "kunde" and his "betrag"
	 * 
	 * @throws SQLException
	 */
	public static void kundenListing(Connection conn) throws SQLException {
		try (final Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery("Select kunde, betrag from konten")) {
				while (rs.next()) {
					final String kunde = rs.getString("kunde");
					final BigDecimal kontostand = rs.getBigDecimal("betrag");
					System.out.printf("Kunde %s hat %s Geldeinheiten%n", kunde, kontostand);
				}
				System.out.println("Ende aller Kunden");
			}
		}
	}

	/**
	 * changes the "betrag" of a "kunde" to a desired new value
	 * 
	 * @throws SQLException
	 */
	private static void changeKontostand(Connection conn, String kunde, BigDecimal betrag) throws SQLException {
		final String selectBetrag = "Select betrag From konten Where kunde = ?";
		final String updateBetrag = "Update konten Set betrag = ? Where kunde = ?";
		try (final PreparedStatement stmt = conn.prepareStatement(selectBetrag)) {
			stmt.setString(1, kunde);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BigDecimal kontostand = rs.getBigDecimal("betrag");
				if (Verbosity) {
					System.out.printf("Der Kontostand von %s beträgt %s%n", kunde, kontostand);
				}
				kontostand = betrag;
				if (Verbosity) {
					System.out.printf("der Kontostand von %s soll geänder werden, auf %s%n", kunde, kontostand);
				}
				try (final PreparedStatement us = conn.prepareStatement(updateBetrag)) {
					us.setBigDecimal(1, kontostand);
					us.setString(2, kunde);
					int count = us.executeUpdate();
					if (count == 1) {
						if (Verbosity) {
							System.out.println("Kontostand erfolgreich geändert");
						}
					}
				}
			}

		}
	}

	public static void bankTransfer(Connection conn, String von, String nach, BigDecimal betrag) throws SQLException {
		final String selectBetrag = "Select betrag From konten Where kunde = ?";
		try (final PreparedStatement stmt = conn.prepareStatement(selectBetrag)) {
			stmt.setString(1, von);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BigDecimal kontostandVon = rs.getBigDecimal("betrag");
				if (Verbosity) {
					System.out.printf("Der Kontostand von %s beträgt %s%n", von, kontostandVon);
				}
				kontostandVon = kontostandVon.subtract(betrag);

				changeKontostand(conn, von, kontostandVon);
			}
			stmt.setString(1, nach);
			final ResultSet rs1 = stmt.executeQuery();
			if (rs1.next()) {
				BigDecimal kontostandNach = rs1.getBigDecimal("betrag");
				if (Verbosity) {
					System.out.printf("Der Kontostand von %s beträgt %s%n", nach, kontostandNach);
				}
				kontostandNach = kontostandNach.add(betrag);

				changeKontostand(conn, nach, kontostandNach);

			}
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
	/*
	 * try(final Statement stmt = conn.createStatement()){ // 1. Einzug von "von"
	 * stmt.executeQuery("select betrag from konten where kunde ="+von); // 2.
	 * Gutschrift zu "nach" }catch (SQLException e) { // TODO: handle exception }
	 */
}
