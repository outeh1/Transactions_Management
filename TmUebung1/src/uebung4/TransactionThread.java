package uebung4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;

import baseStuff.RunnableAdapter;

public class TransactionThread extends RunnableAdapter {
	private final Integer WhatToDo;
	private final String connectionString;
	private final BigDecimal amount;
	private Integer ErrorNum = 0;

	public TransactionThread(Integer WhatToDo, String connString, BigDecimal amount) {
		super();
		this.WhatToDo = WhatToDo;
		this.connectionString = connString;
		this.amount = amount;
	}

	@Override
	public void run() {
		try (Connection conn = DriverManager.getConnection(this.connectionString)) {
			conn.setAutoCommit(false);

			if (this.WhatToDo == 1) {

			} else if (WhatToDo == 2) {

			} else if (WhatToDo == 3) {

			} else {
				throw new Exception("Hilfe Hilfe Was soll ich tun, ich kenn doch nur 3 Sachen und soll jetzt aber "
						+ WhatToDo.toString() + " machen!");
			}

			conn.commit();

		} catch (Exception e) {
			ErrorNum++;
			e.printStackTrace();
		}
		System.err.println("There where: " + ErrorNum + " Errors");

	}
}
