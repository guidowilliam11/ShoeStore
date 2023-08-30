package Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Model.History;
import Util.Connect;
import View.LandingView;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class TransactionRepository {

	public static Connect connect = Connect.getInstance();
	
	public static void getTransaction(TableView<History> historyTable) {
		String query = "SELECT th.TransactionID, TransactionDate, SUM(Quantity) AS Tickets FROM transactionheader th JOIN transactiondetail td ON th.TransactionID = td.TransactionID WHERE UserID LIKE '"+LandingView.userID+"' GROUP BY th.TransactionID, TransactionDate";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				Integer transactionID = connect.rs.getInt("TransactionID");
				String transactionDate = connect.rs.getString("TransactionDate");
				Integer quantity = connect.rs.getInt("Tickets");
				
				LandingView.transactions.add(new History(transactionID, transactionDate, quantity, new Button("Detail")));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void insertTransaction(int userID, DateTimeFormatter dtf, LocalDateTime now, int moneyValue) {
		String query = "INSERT INTO `transactionheader`(`TransactionID`, `UserID`, `TransactionDate`, `payment`) " + "VALUES ('0','"+userID+"','"+dtf.format(now)+"','"+moneyValue+"')";
		connect.execUpdate(query);
	}
	

}
