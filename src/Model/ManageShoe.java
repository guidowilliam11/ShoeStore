package Model;

import View.StaffMainView;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ManageShoe extends Shoe{
	private Button deleteBTN;
	
	public ManageShoe(String ShoeID, String ShoeName, String BrandName, int ticketPrice, int ticketQuantity, Button deleteBTN, String ShoeColor) {
		super(ShoeName, BrandName, ticketPrice, ticketQuantity, ShoeID, ShoeColor);
		this.deleteBTN = deleteBTN;
		this.deleteBTN.setOnMouseClicked(deleteData());
	}
	
	public Button getDeleteBTN() {
		return deleteBTN;
	}
	
	public void setDeleteBTN(Button deleteBTN) {
		this.deleteBTN = deleteBTN;
	}
	
	public EventHandler<MouseEvent> deleteData() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String errorMessage = "Shoe deleted";
				Alert success = new Alert(AlertType.INFORMATION);
				success.setContentText(errorMessage);
				success.show();
				StaffMainView.deleteDataBrand(getShoeID());
			}
		};
	}
	
}

