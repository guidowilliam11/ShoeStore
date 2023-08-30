package Controller;

import Model.Cart;
import Repository.CartRepository;
import Repository.ShoeRepository;
import Model.Shoe;
import View.LandingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class CartController {
	public static void displayAlert(AlertType alertType, String errorMessage) {
		Alert error = new Alert(alertType);
		error.setContentText(errorMessage);
		error.show();
	}
	public static void refreshCart(TableView<Cart> cartTable) {

		LandingView.carts.removeAllElements();
		
		CartRepository.getDataCart(LandingView.carts);
		ObservableList<Cart> cartObs = FXCollections.observableArrayList(LandingView.carts);
		cartTable.setItems(cartObs);
	}
	public static void doInsert(String quantityTF) {
		if(LandingView.ShoeNameCB.getSelectionModel().isEmpty()) {
			displayAlert(AlertType.ERROR, "You must select Shoe you want to buy");
		}else {
			Shoe selectedCB = (Shoe) LandingView.ShoeNameCB.getSelectionModel().getSelectedItem();
			@SuppressWarnings("unused")
			String ShoeName = selectedCB.getShoeName();
			if(quantityTF.isEmpty()) {
				displayAlert(AlertType.ERROR, "The order has to be filled");
			}else {
				Integer quantity = Integer.valueOf(quantityTF);
				if(quantity<1) {
					displayAlert(AlertType.ERROR, "The order has to be more than 0");
				}else if(quantity>selectedCB.getTicketStock()){
					displayAlert(AlertType.ERROR, "The order must not more than the stock");
				}else {
					for (Cart cart : LandingView.carts) {
						if(cart.getShoeID().equals(selectedCB.getShoeID())) {
							CartRepository.updateDataCart(cart.getQuantity(),Integer.valueOf(quantityTF),selectedCB.getShoeID());
						}
					}
					CartRepository.insertCart(selectedCB, quantityTF);
					displayAlert(AlertType.INFORMATION, "The order has beed added to cart");
//					ShoeRepository.updateStock(selectedCB.getTicketStock()-Integer.valueOf(quantityTF), selectedCB.getShoeID());
				}											
			}
		}
		
	}
	public static void emptyCart() {
		CartRepository.deleteCart();
		
		LandingView.carts.removeAllElements();
	}
}
