package Controller;

import Repository.UserRepository;
import View.LoginView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {
	
	public static boolean checkPasswordFormat(String password) {
		boolean numeric = false, upper = false, lower = false;
		int len = password.length();
		for (int i = 0; i < len; i++) {
			if (password.charAt(i) >= '0' && password.charAt(i) <= '9') numeric = true;
			else if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') upper = true;
			else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') lower = true;
		}
		
		return numeric && upper && lower;
	}
	
	public static boolean checkEmailFormat(String email) {
		if (email.startsWith("@") || email.endsWith("@") || email.startsWith(".") || email.endsWith(".") || !email.endsWith(".com")) return false;
		
		int len = email.length(), countA = 0, countDot = 0;
		for (int i = 0; i < len; i++) {
			if (email.charAt(i) == '@') countA++;
			if (countA > 1) return false;
			if (email.charAt(i) == '.' && email.charAt(i + 1) == '@') return false;
			if (countA == 1 && email.charAt(i) == '.') countDot++;
			if (countDot > 1) return false;
		}
		
		if (countA < 1 || countDot < 1) return false;
		
		return true;
	}
	
	public static void displayAlert(AlertType alertType, String errorMessage) {
		Alert error = new Alert(alertType);
		error.setContentText(errorMessage);
		error.show();
	}
	
	public static boolean validateInput(String username, String password, String email, String gender, String confirmPassword) {
		if (username.isEmpty()) {
			displayAlert(AlertType.ERROR, "Username can't be empty");
			return false;
		} else if (password.isEmpty()) {
			displayAlert(AlertType.ERROR, "Password can't be empty");
			return false;
		} else if (!RegisterController.checkPasswordFormat(password)) {
			displayAlert(AlertType.ERROR, "Password must contains number, upper case, and lower case letter");
			return false;
		} else if (!password.equals(confirmPassword)) {
			displayAlert(AlertType.ERROR, "Confirm password doesn't match with password");
			return false;
		} else if (!RegisterController.checkEmailFormat(email)) {
			displayAlert(AlertType.ERROR, "Email must be in valid format");
			return false;
		} else if (gender.equals("")) {
			displayAlert(AlertType.ERROR, "Gender must be selected either Male or Female");
			return false;
		}
		return true;
	}
	
	public static void doRegister(String username, String password, String email, String gender, String confirmPassword) {
		if(!validateInput(username, password, email, gender, confirmPassword)) return;
		
		UserRepository.insertUser(username, confirmPassword, email, gender);
		RegisterController.displayAlert(AlertType.INFORMATION, "You have been successfully registered");
		new LoginView();
		
	}
	
}
