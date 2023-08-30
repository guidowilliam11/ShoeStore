package Repository;

import java.util.Vector;

import Model.Cart;
import Model.Shoe;
import Util.Connect;
import View.LandingView;

public class CartRepository {
	
	public static Connect connect = Connect.getInstance();
	
	public static void getDataCart(Vector<Cart> carts) {
		String query = "SELECT c.ShoeID, ShoeName, Quantity, TicketPrice FROM cart ca JOIN Shoe c ON ca.ShoeID = c.ShoeID WHERE UserID LIKE "+LandingView.userID+"";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String ShoeID = connect.rs.getString("ShoeID");
				String ShoeName = connect.rs.getString("ShoeName");
				Integer quantity = connect.rs.getInt("Quantity");
				Integer ticketPrice = connect.rs.getInt("TicketPrice");
				carts.add(new Cart(ShoeName, quantity, ticketPrice*quantity, ShoeID));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void updateDataCart(int oldQuantity, int newQuantity, String ShoeID) {
		String query = "UPDATE cart SET Quantity = '"+(oldQuantity+newQuantity)+"' WHERE UserID LIKE '"+LandingView.userID+"' AND ShoeID LIKE '"+ShoeID+"'";
		connect.execUpdate(query);
		
	}

	public static void insertCart(Shoe selectedCB, String quantityTF) {
		String query = "INSERT INTO cart " + "VALUES ('"+LandingView.userID+"','"+selectedCB.getShoeID()+"','"+quantityTF+"')";
		connect.execUpdate(query);
	}

	public static void deleteCart() {
		String query = "DELETE FROM cart WHERE UserID LIKE '"+LandingView.userID+"'";
		connect.execUpdate(query);
	}
}
