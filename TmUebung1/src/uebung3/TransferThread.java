package uebung3;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import baseStuff.RunnableAdapter;

public class TransferThread extends RunnableAdapter {
	private final String connectionString;
	private final String sourceAccount;
	private final String targetAccount;
	private final BigDecimal amount;
	private final Integer TransferNum;
	private Integer ErrorNum = 0;

	public TransferThread(String connString, String source, String target, BigDecimal amount, Integer TransferNum) {
		super();
		this.connectionString = connString;
		this.sourceAccount = source;
		this.targetAccount = target;
		this.amount = amount;
		this.TransferNum = TransferNum;
	}

	@Override
	public void run() {
		try (Connection conn = DriverManager.getConnection(this.connectionString)) {
			conn.setAutoCommit(false);
			for (int i = 0; i < this.TransferNum; i++) {
				uebung1.Minimalbeispiel.bankTransfer(conn, this.sourceAccount, this.targetAccount, this.amount);
//				if (i % 2 == 0) {
//					conn.rollback();
//				} else {
//					conn.commit();
//				}
				this.delay(200);
				conn.commit();
			}

			// conn.rollback();

		} catch (SQLException e) {
			ErrorNum++;
			// e.printStackTrace();
		}
		System.err.println("There where: " + ErrorNum + " Errors");

	}
}
