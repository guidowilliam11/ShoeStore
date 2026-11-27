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
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.feather.Feather;

public class CustomerMainView extends Page{
	
	//Base
	GridPane GridContainer1, GridContainer2, GridContainer3;
	
	//Attribute
	Label titleLB1, titleLB2, toCartLB, ShoeNameLB, quantityLB, totalLB;
	Button checkoutBTN, toCartBTN, printBTN;
	TextField quantityTF, moneyTF, nameTF, brandTF, colorTF, priceTF;
	
	int paymentAmount = 0;
	FlowPane shopGrid;
	Label selectedLB, selectedMeta, changeLB;
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
		totalLB.setText(Util.Rupiah.format(0));
		moneyTF = new TextField();
		moneyTF.setPromptText("Amount paid");
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
				String paidText = moneyTF.getText().replaceAll("[^0-9]", "");
				if (paidText.isEmpty() || LandingView.carts.isEmpty()) {
					Alert error = new Alert(AlertType.ERROR);
					error.setContentText(LandingView.carts.isEmpty() ? "Your cart is empty" : "Enter the amount paid");
					error.show();
					return;
				}
				int moneyValue = Integer.parseInt(paidText);
			    
			    if (moneyValue >= paymentAmount) {
			    	TransactionController.doInsert(moneyValue);
			    	DetailController.doInsert();
			    	CartController.emptyCart();
			    	CartController.refreshCart(cartTable);
			    	ShoeController.refreshShoe(ShoeTable);
			    	renderShop();
			    	totalLB.setText(Util.Rupiah.format(0));
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
		totalLB.setText(Util.Rupiah.format(paymentAmount));
		if (changeLB != null) updateChange();
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
		totalLB.setText(Util.Rupiah.format(0));
		updateTotal();
		ShoeNameLB = new Label("Shoe Name");
		quantityLB = new Label("Quantity");
		quantityTF = new TextField();
		toCartBTN = new Button("Add to Cart");
		nameTF = new TextField();
		nameTF.setPromptText("Shoe");
		nameTF.setDisable(true);
		brandTF = new TextField();
		brandTF.setPromptText("Brand");
		brandTF.setDisable(true);
		colorTF = new TextField();
		colorTF.setPromptText("Color");
		colorTF.setDisable(true);
		priceTF = new TextField();
		priceTF.setPromptText("Price");
		priceTF.setDisable(true);
		
	}

	@SuppressWarnings("unchecked")
	private void addBuy() {
	ShoeController.refreshShoe(ShoeTable);
	CartController.refreshCart(cartTable);
	updateTotal();
	cartTable.getColumns().addAll(nameCCol, quantityCCol, priceCCol);

	// Left: the shop as a grid of product cards. Picking a card selects that shoe for the order.
	shopGrid = new FlowPane(14, 14);
	shopGrid.setPadding(new Insets(4, 4, 16, 4));
	renderShop();
	ScrollPane shopScroll = new ScrollPane(shopGrid);
	shopScroll.setFitToWidth(true);
	shopScroll.getStyleClass().add("edge-to-edge");
	shopScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
	Label shopTitle = new Label("Shop");
	shopTitle.getStyleClass().add("title");
	Label shopNote = new Label(LandingView.Shoes.size() + " models in stock");
	shopNote.getStyleClass().add("muted");
	VBox left = new VBox(4, shopTitle, shopNote, new Region(), shopScroll);
	((Region) left.getChildren().get(2)).setMinHeight(8);
	VBox.setVgrow(shopScroll, Priority.ALWAYS);
	HBox.setHgrow(left, Priority.ALWAYS);

	// Right: the order. The chosen shoe, a quantity, then the cart and payment with live change.
	selectedLB = new Label("Pick a shoe on the left");
	selectedLB.getStyleClass().add("section-title");
	selectedMeta = new Label(" ");
	selectedMeta.getStyleClass().add("muted");
	quantityTF.setPromptText("Qty");
	quantityTF.setPrefColumnCount(4);
	HBox addRow = new HBox(8, quantityTF, toCartBTN);
	HBox.setHgrow(toCartBTN, Priority.ALWAYS);
	toCartBTN.setMaxWidth(Double.MAX_VALUE);

	Label cartTitle = new Label("Cart");
	cartTitle.getStyleClass().add("section-title");
	cartTable.setPrefHeight(150);

	Label totalCaption = new Label("Total");
	totalCaption.getStyleClass().add("muted");
	Region g1 = new Region(); HBox.setHgrow(g1, Priority.ALWAYS);
	HBox totalRow = new HBox(totalCaption, g1, totalLB);
	totalRow.setAlignment(Pos.BASELINE_LEFT);
	changeLB = new Label(" ");
	changeLB.getStyleClass().add("change");
	moneyTF.textProperty().addListener((o, was, now) -> updateChange());
	HBox payRow = new HBox(8, moneyTF, checkoutBTN);
	HBox.setHgrow(moneyTF, Priority.ALWAYS);

	VBox order = new VBox(10, selectedLB, selectedMeta, addRow, new Separator(), cartTitle, cartTable, totalRow, payRow, changeLB);
	order.getStyleClass().add("panel");
	order.setPrefWidth(372);
	order.setMinWidth(372);

	HBox page = new HBox(20, left, order);
	page.setFillHeight(true);
	order.setMaxHeight(Region.USE_PREF_SIZE);
	page.setPadding(new Insets(20, 24, 20, 24));
	page.getStyleClass().add("page");
	LandingView.mainContainer.setPadding(Insets.EMPTY);
	LandingView.mainContainer.setCenter(page);
	}

	private void renderShop() {
		shopGrid.getChildren().clear();
		for (Shoe shoe : LandingView.Shoes) {
			ProductCard card = new ProductCard(shoe);
			card.setOnMouseClicked(e -> select(card));
			shopGrid.getChildren().add(card);
		}
	}

	private void select(ProductCard card) {
		shopGrid.getChildren().forEach(n -> ((ProductCard) n).setSelected(n == card));
		Shoe shoe = card.getShoe();
		LandingView.ShoeNameCB.setValue(getSelectedShoe(shoe.getShoeID()));
		selectedLB.setText(shoe.getShoeName());
		selectedMeta.setText(shoe.getBrandName() + " · " + shoe.getShoeColor() + " · " + Util.Rupiah.format(shoe.getTicketPrice()));
		quantityTF.requestFocus();
	}

	// Change due, or how much is still missing, as the amount paid is typed.
	private void updateChange() {
		String text = moneyTF.getText().replaceAll("[^0-9]", "");
		changeLB.getStyleClass().remove("short");
		if (text.isEmpty() || paymentAmount == 0) { changeLB.setText(" "); return; }
		long paid = Long.parseLong(text);
		if (paid >= paymentAmount) changeLB.setText("Change " + Util.Rupiah.format(paid - paymentAmount));
		else { changeLB.setText(Util.Rupiah.format(paymentAmount - paid) + " short"); changeLB.getStyleClass().add("short"); }
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
		Util.Rupiah.apply(priceCCol);
		Util.Theme.number(quantityCCol);
		cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		cartTable.setPlaceholder(new Label("Your cart is empty"));
		Util.Theme.style(toCartBTN, "primary");
		Util.Theme.style(checkoutBTN, "primary");
		Util.Theme.icon(toCartBTN, Feather.SHOPPING_CART);
		Util.Theme.icon(checkoutBTN, Feather.CHECK);
		Util.Theme.style(totalLB, "total");
		moneyTF.setPromptText("Amount paid");
		nameCCol.setMinWidth(130);
		quantityCCol.setMinWidth(48);
		quantityCCol.setMaxWidth(56);
		priceCCol.setMinWidth(116);
		quantityCCol.setText("Qty");
		priceCCol.setText("Total");
		checkoutBTN.setMinWidth(Region.USE_PREF_SIZE);
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
