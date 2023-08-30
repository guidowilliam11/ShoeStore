package Repository;

import java.sql.SQLException;
import java.util.Vector;

import Model.Brand;
import Model.ManageBrand;
import Model.ManageShoe;
import Util.Connect;

public class BrandRepository {

	public static Connect connect = Connect.getInstance();
	
	public static int getLatestBrandID() {
		String query = "SELECT MAX(BrandID) AS id FROM Brand";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				int BrandID = connect.rs.getInt("id");
				return BrandID;
			}
			
		} catch (Exception e) {
		}
		return -1;
	}
	
	public static void insertBrand(int id, String BrandName) {
		id++;
		String query = "INSERT INTO Brand " + "VALUES ('"+id+"','"+BrandName+"')";
		connect.execUpdate(query);
	}
	
	public static void updateBrand(int id, String newBrand) {
		String query = "UPDATE Brand SET BrandID = '"+id+"', BrandName = '"+newBrand+"' WHERE BrandID LIKE '"+id+"'";
		connect.execUpdate(query);
	}

	public static void getManageBrand(Vector<ManageBrand> manageBrands) {
		String query = "SELECT * FROM Brand";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				
				Integer BrandID = connect.rs.getInt("BrandID");
				String BrandName = connect.rs.getString("BrandName");
				
				manageBrands.add(new ManageBrand(BrandID, BrandName));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void getBrands(Vector<Brand> Brands) {
		String query = "SELECT * FROM Brand";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				
				Integer BrandID = connect.rs.getInt("BrandID");
				String BrandName = connect.rs.getString("BrandName");
				
				Brands.add(new Brand(BrandID, BrandName));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void deleteBrand(int BrandID) {
		String query = "DELETE FROM Brand WHERE BrandID LIKE '"+BrandID+"'";
		connect.execUpdate(query);
	}

	public static String getBrandName(int brandID) {
		String query = "SELECT BrandName FROM Brand WHERE BrandID = "+brandID+"";
		connect.rs = connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				String name = connect.rs.getString("BrandName");
				return name;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Testing";
	}
	
}
