package Repository;

import java.sql.ResultSet;

import Util.Connect;

public class UserRepository {
	
	public static Connect connect = Connect.getInstance();
	
	public static ResultSet getUser(String username, String password) {
		String query = "SELECT * FROM user WHERE Username = '" + username + "' AND UserPassword = '" + password + "'";
		return connect.execQuery(query);
	}
	
	public static void insertUser(String username, String password, String email, String gender) {
		String query = "INSERT INTO user (UserID, Username, UserPassword, UserRole, UserEmail, UserGender) VALUES ('0', '"
				+ username + "', '" + password + "', 'customer', '" + email + "', '" + gender + "');";
		connect.execUpdate(query);
	}
}
