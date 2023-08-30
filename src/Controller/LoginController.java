package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;
import Repository.UserRepository;
import View.LandingView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
	
	public static void displayAlert(AlertType alertType, String errorMessage) {
		Alert error = new Alert(alertType);
		error.setContentText(errorMessage);
		error.show();
	}
	
	public static boolean validateInput(String username, String password) {
		if(username.isEmpty()) {
			displayAlert(AlertType.ERROR, "Username must not be empty");
			return false;
		}
		if(password.isEmpty()) {
			displayAlert(AlertType.ERROR, "Password must not be empty");			
			return false;
		}
		return true;
	}
	
	public static void doLogin(String username, String password){
		if(!validateInput(username, password)) return;

		ResultSet rs = UserRepository.getUser(username, password);
		try {
			if (!rs.isBeforeFirst()) {
				displayAlert(AlertType.ERROR, "Username and password doesn't match");
			} else {
				rs.next();
				new LandingView(new User(rs.getString("UserRole"), rs.getInt("UserID")));

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return;
	}
}
