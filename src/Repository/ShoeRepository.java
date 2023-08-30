package Repository;

import java.util.Vector;

import Model.Cart;
import Model.ManageShoe;
import Model.Shoe;
import Util.Connect;
import javafx.scene.control.Button;

public class ShoeRepository {
	
	public static Connect connect = Connect.getInstance();
	
	public static int getLatestShoeID() {
		String query = "SELECT MAX(CAST(SUBSTRING(ShoeID, 2, 4) AS SIGNED)) AS id FROM Shoe;";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				String ShoeNumber = connect.rs.getString("id");
				int intValue = Integer.parseInt(ShoeNumber);
				return intValue;
			}
			
		} catch (Exception e) {
		}
		return -1;
	}
	
	public static void insertShoe(String generateShoeID, int BrandID, String ShoeName, String price, int stock, String color) {
		String query = "INSERT INTO Shoe " + "VALUES ('"+generateShoeID+"','"+BrandID+"','"+ShoeName+"','"+price+"','"+stock+"','"+color+"')";
		connect.execUpdate(query);
	}

	public static void updateShoe(int BrandID, String ShoeName, String price, int stock, String ShoeID, String color) {
		String query = "UPDATE Shoe SET BrandID = '"+BrandID+"', ShoeName = '"+ShoeName+"', TicketPrice = '"+price+"', TicketStock = '"+stock+"', ShoeColor = '"+color+"' WHERE ShoeID LIKE '"+ShoeID+"'";
		connect.execUpdate(query);
	}
	
	public static void updateStock(int stock, String ShoeID) {
		String query = "UPDATE Shoe SET TicketStock = '"+stock+"' WHERE ShoeID LIKE '"+ShoeID+"'";
		connect.execUpdate(query);
	}
	
	public static void getManageShoe(Vector<ManageShoe>manageShoes) {
		

		String query = "SELECT a.BrandID, ShoeID, ShoeName, BrandName, TicketPrice, TicketStock, ShoeColor FROM Brand a JOIN Shoe c ON a.BrandID = c.BrandID";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String ShoeID = connect.rs.getString("ShoeID");
				String ShoeName = connect.rs.getString("ShoeName");
				String BrandName = connect.rs.getString("BrandName");
				Integer ticketPrice = connect.rs.getInt("TicketPrice");
				Integer ticketStock = connect.rs.getInt("TicketStock");
				String ShoeColor = connect.rs.getString("ShoeColor");
				
				manageShoes.add(new ManageShoe(ShoeID, ShoeName, BrandName, ticketPrice, ticketStock, new Button("Delete"), ShoeColor));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void getShoes(Vector<Shoe>Shoes) {
		

		String query = "SELECT a.BrandID, ShoeID, ShoeName, BrandName, TicketPrice, TicketStock, ShoeColor FROM Brand a JOIN Shoe c ON a.BrandID = c.BrandID";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String ShoeID = connect.rs.getString("ShoeID");
				String ShoeName = connect.rs.getString("ShoeName");
				String BrandName = connect.rs.getString("BrandName");
				Integer ticketPrice = connect.rs.getInt("TicketPrice");
				Integer ticketStock = connect.rs.getInt("TicketStock");
				String ShoeColor = connect.rs.getString("ShoeColor");
				
				Shoes.add(new Shoe(ShoeName, BrandName, ticketPrice, ticketStock, ShoeID, ShoeColor));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void updateTicketStock(Cart cart) {
		String update = "UPDATE Shoe SET TicketStock = TicketStock-"+cart.getQuantity()+" WHERE ShoeID LIKE '"+cart.getShoeID()+"'";
		connect.execUpdate(update);
	}

	public static void deleteShoe(String ShoeID) {
		String query = "DELETE FROM Shoe WHERE ShoeID LIKE '"+ShoeID+"'";
		connect.execUpdate(query);
	}
}
