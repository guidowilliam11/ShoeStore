package View;

import java.util.Vector;

import Model.Brand;
import Model.Cart;
import Model.Shoe;
import Model.Detail;
import Model.History;
import Model.ManageBrand;
import Model.ManageShoe;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jfxtras.labs.scene.control.window.Window;

public class LandingView extends Page{
	
	Scene main;
	public Page userRole;
	private static String role;
	public static int userID;
	
	
	//Base
	BorderPane borderContainer;
	HBox menuContainer;
	
	//MenuBar
	static MenuBar menuBar;
	static Menu logoutM;
	static Menu actionM;
	static MenuItem item1, item2;
	Label logoutLB;
	
	//Window
	private Window mainWindow;
	public static BorderPane mainContainer;
	
	//combo box
	public static Vector<ManageShoe>manageShoes;
	public static Vector<Brand>Brands;
	public static Vector<ManageBrand>manageBrands;
	public static Vector<Shoe>Shoes;
	public static Vector<Cart>carts;
	public static Vector<History>transactions;
	public static Vector<Detail>details;
	
	@SuppressWarnings("rawtypes")
	public static ComboBox ShoeNameCB;
	@SuppressWarnings("rawtypes")
	public static ComboBox BrandNameCB;

	public static User user;
	public static int detailID;
	
	public LandingView(User user) {
		LandingView.user = user;
		detailID = 0;
		role = user.getRolePas();
		userID = user.getUserIDPass();
		
		initComp();
		addComp();
		arrangeComp();
		action();
		
		userRole = (role.equals("staff"))? new StaffMainView() : new CustomerMainView();
		
		displayView(main);
	}

	@Override
	protected void initComp() {
		borderContainer = new BorderPane();
		
		menuContainer = new HBox();
		menuBar = new MenuBar();
		
		actionM = new Menu();
		item1 = new MenuItem();
		item2 = new MenuItem();

		logoutM = new Menu();
		logoutLB = new Label("Logout");
		
		mainWindow = new Window();
		mainContainer = new BorderPane();
		
		BrandNameCB = new ComboBox<>();
		ShoeNameCB = new ComboBox<>();
		BrandNameCB.setPromptText("Brand Name");
		ShoeNameCB.setPromptText("Shoe Name");
		
		main = new Scene(borderContainer, 1000, 600);
	}

	@Override
	protected void addComp() {
		logoutM.setGraphic(logoutLB);
		menuContainer.getChildren().add(menuBar);
		borderContainer.setTop(menuContainer);
		
		mainWindow.getContentPane().getChildren().add(mainContainer);
		borderContainer.setCenter(mainWindow);
		
	}

	@Override
	protected void arrangeComp() {
		menuBar.setPrefWidth(main.getWidth());
		mainContainer.setPadding(new Insets(20));
		BrandNameCB.setMinWidth(220);
		ShoeNameCB.setMinWidth(220);
		mainWindow.setVisible(false);
	}

	@Override
	protected void action() {
		logoutLB.setOnMouseClicked(e -> {
			user.setUserAction("non");
			new LoginView();
		});
		
		item1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				generateWindow(item1.getText());
			}
		});
		
		item2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				generateWindow(item2.getText());
			}
		});
		
	}
	
	private void generateWindow(String title) {
		mainWindow.setTitle(title);
		mainContainer.getChildren().clear();
		user.setUserAction(title);
		userRole.initComp();
		userRole.addComp();
		userRole.arrangeComp();
		userRole.action();
		mainWindow.setVisible(true);
	}
	
	public static void generateMenu(String menuName, String itemName1, String itemName2) {
		actionM.setText(menuName);
		item1.setText(itemName1);
		item2.setText(itemName2);
		
		actionM.getItems().add(item1);
		actionM.getItems().add(item2);
		menuBar.getMenus().add(actionM);
		menuBar.getMenus().add(logoutM);

	}
}
