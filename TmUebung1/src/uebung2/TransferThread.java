package uebung2;

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

	public TransferThread(String connString, String source, String target, BigDecimal amount) {
		super();
		this.connectionString = connString;
		this.sourceAccount = source;
		this.targetAccount = target;
		this.amount = amount;
	}

	@Override
	public void run() {
		try (Connection conn = DriverManager.getConnection(this.connectionString)) {
			for (int i = 0; i < 1000; i++) {
				uebung1.Minimalbeispiel.bankTransfer(conn, this.sourceAccount, this.targetAccount, this.amount);
				this.delay(200);
			}
			System.out.printf("%s hat Überweisungen ausgeführt.%n", this.getWhereIRunIn());
		} catch (SQLException e) {
			// TODO: handle exception
		}

	}

}
