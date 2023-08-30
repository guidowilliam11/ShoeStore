package View;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import Controller.CartController;
import Controller.ShoeController;
import Controller.DetailController;
import Controller.TransactionController;
import Model.Cart;
import Model.Shoe;
import Model.Detail;
import Model.History;
import Model.Invoice;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CustomerMainView extends Page{
	
	//Base
	GridPane GridContainer1, GridContainer2, GridContainer3;
	
	//Attribute
	Label titleLB1, titleLB2, toCartLB, ShoeNameLB, quantityLB, totalLB;
	Button checkoutBTN, toCartBTN, printBTN;
	TextField quantityTF, moneyTF, nameTF, brandTF, colorTF, priceTF;
	
	int paymentAmount = 0;
	//Table
	///Buy Shoe
	static TableView<Shoe> ShoeTable;
	TableColumn<Shoe, String> BrandACCol;
	TableColumn<Shoe, String> nameACCol;
	TableColumn<Shoe, String> colorACCol;
	TableColumn<Shoe, Integer> priceACCol;
	TableColumn<Shoe, Integer> stockACCol;
	
	
	static TableView<Cart> cartTable;
	TableColumn<Cart, String> nameCCol;
	TableColumn<Cart, Integer> quantityCCol;
	TableColumn<Cart, Integer> priceCCol;
	
	///Transaction History
	Label historyLB, detailLB;
	static TableView<History> historyTable;
	TableColumn<History, Integer> transactionIDHCol;
	TableColumn<History, String> transactionDateHCol;
	TableColumn<History, Integer> ticketBoughtHCol;
	TableColumn<History, Button> actionHCol;
	
	public static TableView<Detail> detailTable;
	TableColumn<Detail, Integer> transactionIDDCol;
	TableColumn<Detail, String> ShoeNameDCol;
	TableColumn<Detail, String> BrandNameDCol;
	TableColumn<Detail, Integer> quantityDCol;
	
	

	public CustomerMainView() {
		LandingView.generateMenu("Transaction", "Buy Shoe", "Transaction History");
	}
	
	@Override
	protected void initComp() {
		toCartBTN = new Button("Add to Cart");
		checkoutBTN = new Button("Checkout");
		printBTN = new Button("Print");
		Invoice.payment = -1;
		totalLB = new Label();
		totalLB.setText("Rp.0");
		moneyTF = new TextField("0");
		if(LandingView.user.getUserAction().equals("Buy Shoe")) {
			initBuy();
		}
		else initTransaction();
		
	}


	@Override
	protected void addComp() {
		if(LandingView.user.getUserAction().equals("Buy Shoe")) {
			addBuy();
		}
		else addTransaction();
	}

	

	@Override
	protected void arrangeComp() {
		if(LandingView.user.getUserAction().equals("Buy Shoe")) {
			arrangeBuy();
		}
		else arrangeTransaction();
	
	}
	
	@Override
	protected void action() {
		
		toCartBTN.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CartController.doInsert(quantityTF.getText());
				quantityTF.clear();					
				CartController.refreshCart(cartTable);
				updateTotal();
			}
		});
		
		checkoutBTN.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				int moneyValue = Integer.parseInt(moneyTF.getText());
			    
			    if (moneyValue >= paymentAmount) {
			    	TransactionController.doInsert(moneyValue);
			    	DetailController.doInsert();
			    	CartController.emptyCart();
			    	CartController.refreshCart(cartTable);
			    	ShoeController.refreshShoe(ShoeTable);
			    	totalLB.setText("Rp.0");
			    	moneyTF.clear();
			    	updateTotal();			
			    }else {
			    	Alert error = new Alert(AlertType.ERROR);
			    	error.setContentText("Money insufficient");
			    	error.show();			    	
			    }
			}
		});
		
		printBTN.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(Invoice.payment == -1) {
					System.out.println("belum ada");
				}else {
					int id = Invoice.id; 
//					String filePath = "/Users/guidowilliam/Downloads/";
					String fileName = "invoice_" + id + ".txt";
			        
			        try {
			            FileWriter writer = new FileWriter(fileName);

			            writer.write("============================================\n");
			            writer.write("              INVOICE DETAILS              \n");
			            writer.write("============================================\n");

			            for (int i = 0; i < Invoice.shoeID.size(); i++) {
			                writer.write("Item " + (i + 1) + ":\n");
			                writer.write("  Shoe ID       : " + Invoice.shoeID.get(i) + "\n");
			                writer.write("  Shoe Model    : " + Invoice.shoeName.get(i) + "\n");
			                writer.write("  Shoe Brand    : " + Invoice.shoeBrand.get(i) + "\n");
			                writer.write("  Shoe Color    : " + Invoice.shoeColor.get(i) + "\n");
			                writer.write("  Shoe Price    : Rp." + Invoice.shoePrice.get(i) + "\n");
			                writer.write("  Shoe Quantity : " + Invoice.shoeQuantity.get(i) + "\n\n");
			            }

			            writer.write("============================================\n");
			            writer.write("         PAYMENT DETAILS         \n");
			            writer.write("============================================\n");
			            writer.write("Total Price : Rp." + (Invoice.payment-Invoice.change) + "\n");
			            writer.write("Paid        : Rp." + Invoice.payment + "\n");
			            writer.write("Change      : Rp." + Invoice.change + "\n");
			            writer.write("============================================\n");

			            writer.close();
			            Alert error = new Alert(AlertType.INFORMATION);
			    		error.setContentText("Invoice details saved to " + fileName);
			    		error.show();
			        } catch (IOException e) {
			            System.out.println("An error occurred while writing to the file.");
			            e.printStackTrace();
			        }
				}
			}
		});
		
		
	}
	
	public void updateTotal() {
		paymentAmount = 0;
		
		if(LandingView.carts.isEmpty()) {
			return;
		}
		for (Cart c : LandingView.carts) {
			paymentAmount+=c.getPrice();
		}
		totalLB.setText("Rp."+paymentAmount);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initBuy() {
		//Base
		GridContainer1 = new GridPane();
		GridContainer2 = new GridPane();
		GridContainer3 = new GridPane();
		//buyShoes
		///left
		ShoeTable = new TableView<>();
		cartTable = new TableView<>();
		titleLB1 = new Label("Available Shoes");
		titleLB2 = new Label("Cart");
		checkoutBTN = new Button("Checkout");
		
		nameACCol = new TableColumn<>("Shoe Name");
		BrandACCol = new TableColumn<>("Brand Name");
		colorACCol = new TableColumn<>("Shoe Color");
		priceACCol = new TableColumn<>("Shoe Price");
		stockACCol = new TableColumn<>("Shoe Stock");
		
		nameCCol = new TableColumn<>("Shoe Name");
		quantityCCol = new TableColumn<>("Quantity");
		priceCCol = new TableColumn<>("Total Price");
		
		nameACCol.setCellValueFactory(new PropertyValueFactory<>("ShoeName"));
		BrandACCol.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
		colorACCol.setCellValueFactory(new PropertyValueFactory<>("ShoeColor"));
		priceACCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
		stockACCol.setCellValueFactory(new PropertyValueFactory<>("ticketStock"));
		
		nameCCol.setCellValueFactory(new PropertyValueFactory<>("ShoeName"));
		quantityCCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		priceCCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		LandingView.Shoes = new Vector();
		LandingView.carts = new Vector();
		///right
		toCartLB = new Label("Add To Cart");
		totalLB.setText("Rp.0");
		updateTotal();
		ShoeNameLB = new Label("Shoe Name");
		quantityLB = new Label("Quantity");
		quantityTF = new TextField();
		toCartBTN = new Button("Add to Cart");
		nameTF = new TextField("Shoe Name");
		nameTF.setDisable(true);
		brandTF = new TextField("Shoe Brand");
		brandTF.setDisable(true);
		colorTF = new TextField("Shoe Color");
		colorTF.setDisable(true);
		priceTF = new TextField("Shoe Price");
		priceTF.setDisable(true);
		
	}

	@SuppressWarnings("unchecked")
	private void addBuy() {
	ShoeController.refreshShoe(ShoeTable);
	CartController.refreshCart(cartTable);
	updateTotal();
	//buyTicket
	///left
	ShoeTable.getColumns().addAll(nameACCol,colorACCol,BrandACCol,priceACCol,stockACCol);
	cartTable.getColumns().addAll(nameCCol, quantityCCol, priceCCol);
	
	GridContainer1.add(titleLB1, 0, 0);
	GridContainer1.add(ShoeTable, 0, 1);
	
	GridContainer1.add(titleLB2, 0, 2);
	GridContainer1.add(cartTable, 0, 3);
	
	GridContainer3.add(checkoutBTN, 0, 0);
	GridContainer3.add(moneyTF, 1, 0);
	GridContainer3.add(totalLB, 2, 0);
	GridContainer1.add(GridContainer3, 0, 4);
	///right
	GridContainer2.add(toCartLB, 0, 0);
	GridContainer2.add(ShoeNameLB, 0, 1);
	GridContainer2.add(LandingView.ShoeNameCB, 0, 2);
	GridContainer2.add(nameTF, 0, 3);
	GridContainer2.add(brandTF, 0, 4);
	GridContainer2.add(colorTF, 0, 5);
	GridContainer2.add(priceTF, 0, 6);
	GridContainer2.add(quantityLB, 0, 7);
	GridContainer2.add(quantityTF, 0, 8);
	GridContainer2.add(toCartBTN, 0, 9);
	
	
	LandingView.mainContainer.setLeft(GridContainer1);
	LandingView.mainContainer.setRight(GridContainer2);
	
	ShoeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
        	nameTF.setText(newSelection.getShoeName());
        	brandTF.setText(newSelection.getBrandName());
        	colorTF.setText(newSelection.getShoeColor());
            priceTF.setText(String.valueOf(newSelection.getTicketPrice()));
            LandingView.ShoeNameCB.setValue(getSelectedShoe(newSelection.getShoeID()));
        }
    });
	}
	
	private Shoe getSelectedShoe(String shoeID) {
	    for (Shoe shoe : LandingView.Shoes) {          
	        if (shoe.getShoeID().equals(shoeID)) {
	            return shoe;
	        }
	    }
	    return null; // Return null if no matching shoe is found
	}

	private void arrangeBuy() {
		GridContainer3.setPadding(new Insets(20));
		GridContainer1.setPadding(new Insets(20));
		GridContainer1.setVgap(5);
		GridContainer2.setPadding(new Insets(20));
		GridContainer2.setVgap(10);
		GridContainer1.setPrefWidth(700);
		
		ShoeTable.setPrefWidth(700);
		cartTable.setPrefWidth(700);
		
		LandingView.ShoeNameCB.setMinWidth(220);
		quantityTF.setMinWidth(220);
		
		nameACCol.setMinWidth(150);
		colorACCol.setMinWidth(150);
		BrandACCol.setMinWidth(150);
		priceACCol.setMinWidth(110);
		stockACCol.setMinWidth(100);
		
		nameCCol.setMinWidth(200);
		quantityCCol.setMinWidth(100);
		priceCCol.setMinWidth(100);

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initTransaction() {
		GridContainer1 = new GridPane();
		titleLB1 = new Label("Transaction History");
		titleLB2 = new Label("Transaction Detail");
		printBTN = new Button("Print");
		historyTable = new TableView<>();
		transactionIDHCol = new TableColumn<>("Transaction ID");
		transactionDateHCol = new TableColumn<>("Transaction Date");
		ticketBoughtHCol = new TableColumn<>("Shoes Bought");
		actionHCol = new TableColumn<>("Action");
		LandingView.details = new Vector();
		
		detailTable = new TableView<>();
		transactionIDDCol = new TableColumn<>("Transaction ID");
		ShoeNameDCol = new TableColumn<>("Shoe Name");
		BrandNameDCol = new TableColumn<>("Brand Name");
		quantityDCol = new TableColumn<>("Quantity");
		LandingView.transactions = new Vector();
		
		transactionIDHCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		transactionDateHCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
		ticketBoughtHCol.setCellValueFactory(new PropertyValueFactory<>("ticketBought"));
		actionHCol.setCellValueFactory(new PropertyValueFactory<>("action"));
		
		transactionIDDCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		ShoeNameDCol.setCellValueFactory(new PropertyValueFactory<>("ShoeName"));
		BrandNameDCol.setCellValueFactory(new PropertyValueFactory<>("BrandName"));
		quantityDCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	}
	
	@SuppressWarnings("unchecked")
	private void addTransaction() {
		
		TransactionController.refreshController(historyTable);
		DetailController.refreshDetail(detailTable);
		
		historyTable.getColumns().addAll(transactionIDHCol, transactionDateHCol, ticketBoughtHCol, actionHCol);
		detailTable.getColumns().addAll(transactionIDDCol, ShoeNameDCol, BrandNameDCol, quantityDCol);
		
		GridContainer1.add(titleLB1, 0, 0);
		GridContainer1.add(historyTable, 0, 1);
		GridContainer1.add(titleLB2, 0, 2);
		GridContainer1.add(detailTable, 0, 3);
		GridContainer1.add(printBTN, 0, 4);
		
		LandingView.mainContainer.setCenter(GridContainer1);
	}
	
	private void arrangeTransaction() {
		GridContainer1.setPadding(new Insets(10));
		
		GridContainer1.setVgap(10);
		
		historyTable.setMinWidth(950);
		detailTable.setMinWidth(950);
		
		transactionIDHCol.setMinWidth(200);
		transactionDateHCol.setMinWidth(200);
		ticketBoughtHCol.setMinWidth(200);
		actionHCol.setMinWidth(100);
		
		transactionIDDCol.setMinWidth(200);
		ShoeNameDCol.setMinWidth(200);
		BrandNameDCol.setMinWidth(200);
		quantityDCol.setMinWidth(100);
	}
}
