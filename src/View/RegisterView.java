package View;

import Controller.RegisterController;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class RegisterView extends Page{

	Scene register;
	
	BorderPane borderContainer;
	GridPane gridContainer, genderContainer;
	
	Label registerLB, usernameLB, passwordLB, confirmLB, emailLB, GenderLB;
	TextField usernameTF, emailTF;
	PasswordField passwordPF, confirmPF;
	ToggleGroup genderTG;
	RadioButton maleRB, femaleRB;
	Button registerBTN, gotoLoginBTN;
	
	public RegisterView() {
		initComp();
		addComp();
		arrangeComp();
		action();
		displayView(register);
	}
	
	@Override
	protected void initComp() {
		borderContainer = new BorderPane();
		gridContainer = new GridPane();
		
		registerLB = new Label("Register");
		usernameLB = new Label("Username :");
		passwordLB = new Label("Password :");
		confirmLB = new Label("Confirm Password :");
		emailLB = new Label("Email :");
		GenderLB = new Label("Gender :");
		usernameTF = new TextField();
		emailTF = new TextField();
		passwordPF = new PasswordField();
		confirmPF = new PasswordField();
		
		genderContainer = new GridPane();
		genderTG = new ToggleGroup();
		maleRB = new RadioButton("Male");
		femaleRB = new RadioButton("Female");
		
		registerBTN = new Button("Register");
		gotoLoginBTN = new Button("Already have an account ?\n               Login here !");
		
		register = new Scene(borderContainer, 1000, 600);
	}

	@Override
	protected void addComp() {
		borderContainer.setCenter(gridContainer);
		maleRB.setToggleGroup(genderTG);
		femaleRB.setToggleGroup(genderTG);
		
		genderContainer.add(femaleRB, 0, 0);
		genderContainer.add(maleRB, 1, 0);
		
		addGirdContainer();
	}

	private void addGirdContainer() {
		gridContainer.add(registerLB, 0, 0);
		gridContainer.add(usernameLB, 0, 1);
		gridContainer.add(usernameTF, 0, 2);
		gridContainer.add(passwordLB, 0, 3);
		gridContainer.add(passwordPF, 0, 4);
		gridContainer.add(confirmLB, 0, 5);
		gridContainer.add(confirmPF, 0, 6);
		gridContainer.add(emailLB, 0, 7);
		gridContainer.add(emailTF, 0, 8);
		gridContainer.add(GenderLB, 0, 9);
		gridContainer.add(genderContainer, 0, 10);
		gridContainer.add(registerBTN, 0, 11);
		gridContainer.add(gotoLoginBTN, 0, 12);
	}

	@Override
	protected void arrangeComp() {
		BorderPane.setAlignment(gridContainer, Pos.CENTER);
		gridContainer.setAlignment(Pos.CENTER);
		gridContainer.setVgap(5);
		genderContainer.setHgap(15);
		
		setGridPaneAlignment();
		
		genderContainer.setAlignment(Pos.CENTER);
		
		usernameTF.setPrefWidth(250);
		passwordPF.setPrefWidth(250);
		Font font = Font.font("Open Sans", 25);
		registerLB.setFont(font);
		
		gotoLoginBTN.setStyle("-fx-background-color: transparent;");
	}

	private void setGridPaneAlignment() {
		GridPane.setHalignment(registerLB, HPos.CENTER);
		GridPane.setHalignment(usernameLB, HPos.CENTER);
		GridPane.setHalignment(usernameTF, HPos.CENTER);
		GridPane.setHalignment(passwordLB, HPos.CENTER);
		GridPane.setHalignment(passwordPF, HPos.CENTER);
		GridPane.setHalignment(confirmLB, HPos.CENTER);
		GridPane.setHalignment(confirmPF, HPos.CENTER);
		GridPane.setHalignment(emailLB, HPos.CENTER);
		GridPane.setHalignment(emailTF, HPos.CENTER);
		GridPane.setHalignment(emailLB, HPos.CENTER);
		GridPane.setHalignment(GenderLB, HPos.CENTER);
		GridPane.setHalignment(genderContainer, HPos.CENTER);
		GridPane.setHalignment(registerBTN, HPos.CENTER);
		GridPane.setHalignment(gotoLoginBTN, HPos.CENTER);
	}

	@Override
	protected void action() {
		registerBTN.setOnAction(e -> {
			String username = usernameTF.getText(); 
			String password = passwordPF.getText();
			String confirmPassword = confirmPF.getText();
			String email = emailTF.getText();
			String gender = (maleRB.isSelected())? "Male" : (femaleRB.isSelected())? "Female" : "";
			
			RegisterController.doRegister(username, password, email, gender, confirmPassword);
		});
		
		gotoLoginBTN.setOnAction(e -> {
			new LoginView();
		});
	}
}
