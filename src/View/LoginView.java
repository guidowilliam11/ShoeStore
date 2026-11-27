package View;

import Controller.LoginController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class LoginView extends Page{

	Scene login;
	
	BorderPane borderContainer;
	GridPane gridContainer;
	
	Label loginLB, usernameLB, passwordLB;
	TextField usernameTF;
	PasswordField passwordPF;
	Button loginBTN, gotoRegisterBTN;
	
	public LoginView() {
		initComp();
		addComp();
		arrangeComp();
		action();
		displayView(login);
	}

	@Override
	protected void initComp() {
		borderContainer = new BorderPane();
		gridContainer = new GridPane();
		
		loginLB = new Label("Sign in to ShoeStore");
		usernameLB = new Label("Username");
		passwordLB = new Label("Password");
		gotoRegisterBTN = new Button("No account yet? Register");
		usernameTF = new TextField();
		passwordPF = new PasswordField();
		
		loginBTN = new Button("Login");
		
		login = Util.Theme.apply(new Scene(borderContainer, 1000, 600));
	}

	@Override
	protected void addComp() {
		borderContainer.setCenter(gridContainer);
		addGridContainer();
	}

	private void addGridContainer() {
		gridContainer.add(loginLB, 0, 0);
		gridContainer.add(usernameLB, 0, 1);
		gridContainer.add(usernameTF, 0, 2);
		gridContainer.add(passwordLB, 0, 3);
		gridContainer.add(passwordPF, 0, 4);
		gridContainer.add(loginBTN, 0, 5);
		gridContainer.add(gotoRegisterBTN, 0, 6);
	}

	@Override
	protected void arrangeComp() {
		BorderPane.setAlignment(gridContainer, Pos.CENTER);
		gridContainer.setAlignment(Pos.CENTER);
		gridContainer.setVgap(10);
		
		setGridPaneAlignment();
		
		usernameTF.setPrefWidth(250);
		passwordPF.setPrefWidth(250);
		Util.Theme.style(loginLB, "title");
		Util.Theme.style(loginBTN, "primary");
		Util.Theme.style(gotoRegisterBTN, "link");
		loginBTN.setPrefWidth(250);
	}

	private void setGridPaneAlignment() {
		GridPane.setHalignment(loginLB, HPos.CENTER);
		GridPane.setHalignment(usernameLB, HPos.CENTER);
		GridPane.setHalignment(usernameTF, HPos.CENTER);
		GridPane.setHalignment(passwordLB, HPos.CENTER);
		GridPane.setHalignment(passwordPF, HPos.CENTER);
		GridPane.setHalignment(loginBTN, HPos.CENTER);
		GridPane.setHalignment(gotoRegisterBTN, HPos.CENTER);
	}

	@Override
	protected void action() {
		loginBTN.setOnAction(e -> {
			String username = usernameTF.getText();
			String password = passwordPF.getText();
			
			LoginController.doLogin(username, password);
		});
		
		gotoRegisterBTN.setOnAction(e -> {
			new RegisterView();
		});
	}

}
