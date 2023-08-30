package View;

import java.util.Vector;

import Controller.BrandController;
import Controller.ShoeController;
import Model.Brand;
import Model.Shoe;
import Model.ManageBrand;
import Model.ManageShoe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StaffMainView extends Page{
	
	
	//Base
	GridPane GridContainer, ActionContainer, FormContainer;
	BorderPane ActionBorder;
	
	//Attribute
	Label tableLb, ticketStock;
	TextField tf1, tf2, colorTF;
	Button insertBtn, updateBtn, submitBtn;
	Spinner<Integer> stockSpinner = new Spinner<Integer>();
	
	//Table
	///Manage Brand
	public static TableView<ManageBrand> allBrandTable;
	TableColumn<ManageBrand, String> brandIDCol;
	TableColumn<ManageBrand, String> brandNameCol;
	TableColumn<ManageBrand, Button> deleteCol;
	
	  
	///Manage Shoe
	static TableView<ManageShoe> allShoeTable;
	TableColumn<ManageShoe, String> ShoeIDCol;
	TableColumn<ManageShoe, String> nameCLCol;
	TableColumn<ManageShoe, String> BrandCLCol;
	TableColumn<ManageShoe, String> colorCLCol;
	TableColumn<ManageShoe, Integer> priceCLCol;
	TableColumn<ManageShoe, Integer> stockCLCol;
	TableColumn<ManageShoe, Button> deleteCLCol;

	public StaffMainView() {
		LandingView.generateMenu("Manage", "Manage Brand", "Manage Shoe");
	}
	
	@Override
	protected void initComp() {
		
		GridContainer = new GridPane();
		ActionContainer = new GridPane();
		ActionBorder = new BorderPane();
		insertBtn = new Button("Insert");
		updateBtn = new Button("Update");
		submitBtn = new Button();
		FormContainer = new GridPane();
		colorTF = new TextField("Shoe Color");
		
		if(LandingView.user.getUserAction().equals("Manage Brand")) {
			initBrand();
		}
		else initShoe();
	}

	@Override
	protected void addComp() {
		
		if(LandingView.user.getUserAction().equals("Manage Brand")) {
			addBrand();
		}
		else addShoe();
		
		GridContainer.add(tableLb, 0, 0);
		GridContainer.add(ActionBorder, 0, 2);
		
		ActionBorder.setTop(ActionContainer);
		ActionBorder.setLeft(FormContainer);
		ActionBorder.setBottom(submitBtn);
		
		ActionContainer.add(insertBtn, 0, 0);
		ActionContainer.add(updateBtn, 1, 0);
		
		LandingView.mainContainer.setCenter(GridContainer);
	}

	@Override
	protected void arrangeComp() {
		
		FormContainer.setVisible(false);
		submitBtn.setVisible(false);
		FormContainer.setPadding(new Insets(10));
		FormContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		if(LandingView.user.getUserAction().equals("Manage Brand")) {
			arrangeBrand();
		}
		else arrangeShoe();
	}

	@Override
	protected void action() {
		insertBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				submitBtn.setText("Insert");
				FormContainer.getChildren().clear();
				
				if(LandingView.user.getUserAction().equals("Manage Brand")) {
					tf1.clear();
					FormContainer.add(tf1, 0, 0);	
					tf1.setPromptText("Insert Brand Name");
				}else {
					tf1.clear();
					tf2.clear();
					colorTF.clear();
					FormContainer.add(tf1, 0, 0);
					FormContainer.add(tf2, 1, 0);
					FormContainer.add(LandingView.BrandNameCB, 0, 1);
					FormContainer.add(colorTF, 0, 2);
					FormContainer.add(new Label("Shoe Stock"), 1, 1);
					FormContainer.add(stockSpinner, 1, 2);	
					tf1.setPromptText("Insert Shoe Name");
					tf2.setPromptText("Insert Shoe Price");
				}
				
				FormContainer.setVisible(true);
				submitBtn.setVisible(true);
			}
		});
		
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				submitBtn.setText("Update");
				FormContainer.getChildren().clear();
				
				if(LandingView.user.getUserAction().equals("Manage Brand")) {
					tf1.clear();
					FormContainer.add(LandingView.BrandNameCB, 0, 0);
					FormContainer.add(tf1, 0, 1);
					tf1.setPromptText("Update Brand Name");
					
				}else {
					tf1.clear();
					tf2.clear();
					colorTF.clear();
					FormContainer.add(LandingView.ShoeNameCB, 0, 0);
					FormContainer.add(tf1, 0, 1);
					FormContainer.add(tf2, 1, 1);
					FormContainer.add(LandingView.BrandNameCB, 0, 2);
					FormContainer.add(colorTF, 0, 3);
					FormContainer.add(new Label("Shoe Stock"), 1, 2);
					FormContainer.add(stockSpinner, 1, 3);	
					tf1.setPromptText("Update Shoe Name");
					tf2.setPromptText("Update Shoe Price");
				}
				
				FormContainer.setVisible(true);
				submitBtn.setVisible(true);
			}
			
		});
		
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(submitBtn.getText().equals("Insert")) {
					doInsert();
				}else {
					doUpdate();
				}
			}
		});
	}
	
	private void initBrand() {
		
		tableLb = new Label("Brand List");
		
		allBrandTable = new TableView<>();
		brandIDCol = new TableColumn<>("Brand ID");
		brandNameCol = new TableColumn<>("Brand Name");
		deleteCol = new TableColumn<>();
		LandingView.manageBrands = new Vector<>();
		LandingView.Brands = new Vector<>();
		
		brandIDCol.setCellValueFactory(new PropertyValueFactory<>("BrandID"));
		brandNameCol.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
		deleteCol.setCellValueFactory(new PropertyValueFactory<>("deleteBTN"));
		
		tf1 = new TextField();
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void addBrand() {
		BrandController.refreshManageBrand(allBrandTable);
		allBrandTable.getColumns().addAll(brandIDCol, brandNameCol, deleteCol);
		GridContainer.add(allBrandTable, 0, 1);
	}
	
	private void arrangeBrand() {
		allBrandTable.setPrefWidth(1000);
		allBrandTable.setMaxHeight(300);
		
		brandIDCol.setPrefWidth(100);
		brandNameCol.setPrefWidth(300);
		deleteCol.setPrefWidth(100);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initShoe() {
		tableLb = new Label("Shoe List");
		
		allShoeTable = new TableView<>();
		ShoeIDCol = new TableColumn<>("Shoe ID");
		BrandCLCol = new TableColumn<>("Brand Name");
		nameCLCol = new TableColumn<>("Shoe Name");
		colorCLCol = new TableColumn<>("Shoe Color");
		priceCLCol = new TableColumn<>("Shoe Price");
		stockCLCol = new TableColumn<>("Shoe Stock");
		deleteCLCol = new TableColumn<>();
		
		ShoeIDCol.setCellValueFactory(new PropertyValueFactory<>("ShoeID"));
		BrandCLCol.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
		nameCLCol.setCellValueFactory(new PropertyValueFactory<>("ShoeName"));
		colorCLCol.setCellValueFactory(new PropertyValueFactory<>("ShoeColor"));
		priceCLCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
		stockCLCol.setCellValueFactory(new PropertyValueFactory<>("ticketStock"));
		deleteCLCol.setCellValueFactory(new PropertyValueFactory<>("deleteBTN"));
	
		LandingView.manageShoes = new Vector<>();
		LandingView.Brands = new Vector<>();
		LandingView.Shoes = new Vector<>();
		stockSpinner = new Spinner(0,1000,0);
		
		tf1 = new TextField();
		tf2 = new TextField();
	}
	
	@SuppressWarnings("unchecked")
	private void addShoe() {
		ShoeController.refreshManageShoe(allShoeTable);
		BrandController.getComboBox();
		allShoeTable.getColumns().addAll(ShoeIDCol, nameCLCol, colorCLCol, BrandCLCol, priceCLCol, stockCLCol, deleteCLCol);
		GridContainer.add(allShoeTable, 0, 1);
		
	}
	
	private void arrangeShoe() {
		allShoeTable.setMinWidth(1000);
		allShoeTable.setMaxHeight(250);
		
		ShoeIDCol.setPrefWidth(100);
		nameCLCol.setPrefWidth(150);
		colorCLCol.setPrefWidth(150);
		BrandCLCol.setPrefWidth(150);
		priceCLCol.setPrefWidth(150);
		stockCLCol.setPrefWidth(100);
		deleteCLCol.setPrefWidth(100);
		
	}
	
	
	
	public void doInsert() {
		if(LandingView.user.getUserAction().equals("Manage Brand")) {
			BrandController.doInsert(tf1.getText());
			BrandController.refreshManageBrand(allBrandTable);
			tf1.clear();
		}else {
			Brand selectedCB = (Brand) LandingView.BrandNameCB.getSelectionModel().getSelectedItem();
			ShoeController.doInsert(LandingView.manageShoes, selectedCB.getBrandID(), tf1.getText(), tf2.getText(), stockSpinner.getValue(), colorTF.getText());
			ShoeController.refreshManageShoe(allShoeTable);
			tf1.clear();
			tf2.clear();
			stockSpinner.getEditor().clear();
		}
	}
	
	public void doUpdate() {
		if(LandingView.user.getUserAction().equals("Manage Brand")) {
			Brand selectedCB = (Brand) LandingView.BrandNameCB.getSelectionModel().getSelectedItem();
			BrandController.doUpdate(selectedCB.getBrandID(), tf1.getText());
			BrandController.refreshManageBrand(allBrandTable);
			tf1.clear();

		}else {
			Shoe selectedCB = (Shoe) LandingView.ShoeNameCB.getSelectionModel().getSelectedItem();
			Brand selectedCB2 = (Brand) LandingView.BrandNameCB.getSelectionModel().getSelectedItem();
			ShoeController.doUpdate(selectedCB2.getBrandID(), tf1.getText(), tf2.getText(), stockSpinner.getValue(), selectedCB.getShoeID(), colorTF.getText());
			ShoeController.refreshManageShoe(allShoeTable);
			tf1.clear();
			tf2.clear();
			stockSpinner.getEditor().clear();
		}
	}

	public static void deleteDataBrand(int BrandID) {
		BrandController.doDelete(BrandID);
		BrandController.refreshManageBrand(allBrandTable);
	}

	public static void deleteDataBrand(String ShoeID) {
		ShoeController.doDelete(ShoeID);
		ShoeController.refreshManageShoe(allShoeTable);
	}
	
}
