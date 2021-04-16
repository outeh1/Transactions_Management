package uebung2;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Starter {
	private static final Integer TransferThreadNum = 1;
	private static final Integer ThreadTransferNum = 100;
	private static final BigDecimal betrag = BigDecimal.valueOf(100, 0);

	public static void main(String[] args) {
		uebung1.Minimalbeispiel.toggleVerbose();
		final String mysqliteURL = "jdbc:sqlite:helloFhdw.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";

		final String[] urls = { mysqliteURL };
		// final String[] urls = {postgreSQLURL,mariadbURL,mysqliteURL};
		for (String url : urls) {
			try (final Connection conn = DriverManager.getConnection(url)) {
				System.out.println("Verbunden mit Datenbank" + url);
				/*
				 * CREATE TABLE "konten" ( "kto" INTEGER NOT NULL, "betrag" INTEGER NOT NULL,
				 * "kunde" TEXT NOT NULL, PRIMARY KEY("kto") );
				 */
				uebung1.Minimalbeispiel.ResetTable(conn);
				uebung1.Minimalbeispiel.kundenListing(conn);

				List<TransferThread> Threads = new ArrayList<TransferThread>();
				for (int i = 0; i < TransferThreadNum; i++) {
					Threads.add(new TransferThread(url, "A", "B", betrag, ThreadTransferNum));
					Threads.add(new TransferThread(url, "B", "C", betrag, ThreadTransferNum));
					Threads.add(new TransferThread(url, "C", "A", betrag, ThreadTransferNum));
				}
				for (TransferThread thread : Threads) {
					thread.start();
				}
				for (TransferThread thread : Threads) {
					thread.join();
				}
				uebung1.Minimalbeispiel.kundenListing(conn);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
}
