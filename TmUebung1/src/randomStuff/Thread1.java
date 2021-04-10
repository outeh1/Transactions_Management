package randomStuff;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import baseStuff.RunnableAdapter;

public class Thread1 extends RunnableAdapter{

	@Override
	public void run() {
		final String mysqliteURL = "jdbc:sqlite:LostUpdates.sqlite.db";
		final String mariadbURL = "jdbc:mariadb://localhost:3306/test?user=outeh";
		final String postgreSQLURL = "jdbc:postgresql://localhost/postgres?user=outeh";
		
		final String url = mysqliteURL;
		try ( final Connection conn = DriverManager . getConnection ( url )) {
			System.out.println(this.getWhereIRunIn()+  " Verbunden mit Datenbank" + url);
			System.out.println(this.getWhereIRunIn()+" sagt der count ist: "+StartLostUpdates.ShowCount(conn).toString());
			StartLostUpdates.Increment(conn);
			
	}catch (SQLException e) {
		// TODO: handle exception
	}
		
	}

}
