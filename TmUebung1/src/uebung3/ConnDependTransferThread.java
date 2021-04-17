package uebung3;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import baseStuff.RunnableAdapter;

public class ConnDependTransferThread extends RunnableAdapter {
	private final Connection conn;
	private final String sourceAccount;
	private final String targetAccount;
	private final BigDecimal amount;
	private final Integer TransferNum;
	private Integer ErrorNum = 0;

	public ConnDependTransferThread(Connection conn, String source, String target, BigDecimal amount,
			Integer TransferNum) {
		super();
		this.conn = conn;
		this.sourceAccount = source;
		this.targetAccount = target;
		this.amount = amount;
		this.TransferNum = TransferNum;
	}

	@Override
	public void run() {
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < this.TransferNum; i++) {
				uebung1.Minimalbeispiel.bankTransfer(conn, this.sourceAccount, this.targetAccount, this.amount);
				if (i % 2 == 0) {
					conn.rollback();
				} else {
					conn.commit();
				}
				this.delay(200);
				conn.commit();
			}

		} catch (SQLException e) {
			ErrorNum++;
			e.printStackTrace();
		}

		// conn.rollback();

		System.err.println("There where: " + ErrorNum + " Errors");

	}
}
