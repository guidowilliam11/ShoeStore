package Repository;

import Model.Cart;
import Model.Detail;
import Model.Invoice;
import Util.Connect;
import View.CustomerMainView;
import View.LandingView;
import javafx.scene.control.TableView;

public class DetailRepository {

	public static Connect connect = Connect.getInstance();

	public static void getDetail(TableView<Detail> detailTable) {
		
		String query = "SELECT th.TransactionID, ShoeName, BrandName, Quantity FROM transactionheader th JOIN transactiondetail td ON th.TransactionID = td.TransactionID JOIN Shoe c ON c.ShoeID = td.ShoeID JOIN Brand a ON a.BrandID = c.BrandID WHERE th.TransactionID LIKE '"+LandingView.detailID+"'";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				Integer transactionID = connect.rs.getInt("TransactionID");
				String ShoeName = connect.rs.getString("ShoeName");
				String BrandName = connect.rs.getString("BrandName");
				Integer quantity = connect.rs.getInt("Quantity");
				
				LandingView.details.add(new Detail(transactionID, ShoeName, BrandName, quantity));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static int getTransactionID() {
		String query = "SELECT MAX(TransactionID) AS id FROM transactionheader";
		
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				int transactionID = connect.rs.getInt("id");
				return transactionID;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	public static void insertTransaction(Cart cart) {
		String query = "INSERT INTO transactiondetail " + "VALUES ('"+getTransactionID()+"','"+cart.getShoeID()+"','"+cart.getQuantity()+"')";
		connect.execUpdate(query);
	}

	public static void setInvoice(int transactionID) {
		int total = 0;
		String query = "SELECT th.TransactionID, th.payment, ShoeName, BrandName, Quantity, TicketPrice, ShoeColor, td.ShoeID, c.BrandID FROM transactionheader th JOIN transactiondetail td ON th.TransactionID = td.TransactionID JOIN Shoe c ON c.ShoeID = td.ShoeID JOIN Brand a ON a.BrandID = c.BrandID WHERE th.TransactionID LIKE '"+transactionID+"'";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				Integer tID = connect.rs.getInt("TransactionID");
				String ShoeName = connect.rs.getString("ShoeName");
				String BrandName = connect.rs.getString("BrandName");
				Integer Quantity = connect.rs.getInt("Quantity");
				Integer Price = connect.rs.getInt("TicketPrice");
				String ShoeColor = connect.rs.getString("ShoeColor");
				String ShoeID = connect.rs.getString("ShoeID");
				Integer payment = connect.rs.getInt("payment");
				
				Invoice.id = tID;
				Invoice.shoeID.add(ShoeID);
				Invoice.shoeName.add(ShoeName);
				Invoice.shoeBrand.add(BrandName);
				Invoice.shoeColor.add(ShoeColor);
				Invoice.shoePrice.add(Price);
				Invoice.shoeQuantity.add(Quantity);
				Invoice.payment = payment;
				total += Price*Quantity;
			}
			Invoice.change = Invoice.payment - total;
						
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
