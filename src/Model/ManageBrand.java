package Model;

import View.StaffMainView;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ManageBrand extends Brand{

	private Button deleteBTN;
	public ManageBrand(int BrandID, String BrandName) {
		super(BrandID, BrandName);
		this.deleteBTN = new Button("Delete");
		this.deleteBTN.setOnMouseClicked(deleteData());
	}
	
	public Button getDeleteBTN() {
		return deleteBTN;
	}
	
	public void setDeleteBTN(Button deleteButton) {
		this.deleteBTN = deleteButton;
	}
	
	public EventHandler<MouseEvent> deleteData() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String errorMessage = "Brand deleted";
				Alert success = new Alert(AlertType.INFORMATION);
				success.setContentText(errorMessage);
				success.show();
				StaffMainView.deleteDataBrand(getBrandID());;
			}
		};
	}
	

}
