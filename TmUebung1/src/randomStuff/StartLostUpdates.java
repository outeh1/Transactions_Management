package randomStuff;

import java.math.BigDecimal;
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
			
			
			
			System.out.println("Der aktuelle Stand des Counters ist: "+ShowCount(conn).toString());
			System.out.println("Imma now gonna Start to Threads to each increment the count by 1");
			Thread1 t1 = new Thread1();
			Thread1 t2 = new Thread1();
			t1.start();
			t2.start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Der aktuelle Stand des Counters ist: "+ShowCount(conn).toString());
			
			
	}catch (SQLException e) {
		// TODO: handle exception
	}

}

	public static BigDecimal ShowCount(Connection conn) {
		try(final Statement stmt = conn.createStatement()){
			try(ResultSet rs = stmt.executeQuery("Select CurrentCount from Counter")){
				if(rs.next()) {
					final BigDecimal count =rs.getBigDecimal("CurrentCount");
					return count;
				}
			}catch (SQLException e) {
				e.printStackTrace();
		}
		}catch (SQLException e) {
			e.printStackTrace();
	}
		return null;
		
	}
	
	public static void Increment(Connection conn) {
		final String updateCount = "Update Counter Set CurrentCount = CurrentCount +1";
		
				try(final PreparedStatement uc = conn.prepareStatement(updateCount)){
					
					int count = uc.executeUpdate();
					if(count == 1) {
						System.out.println("Count erfolgreich geändert");
					}
				}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
