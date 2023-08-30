package Model;

import java.util.ArrayList;

import Controller.DetailController;
import View.CustomerMainView;
import View.LandingView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class History {
	
	private int transactionID;
	private String transactionDate;
	private int ticketBought;
	private Button action;
	
	public History(int transactionID, String transactionDate, int ticketBought, Button action) {
		super();
		this.transactionID = transactionID;
		this.transactionDate = transactionDate;
		this.ticketBought = ticketBought;
		this.action = action;
		this.action.setOnMouseClicked(showDetail());
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public int getTicketBought() {
		return ticketBought;
	}
	
	public void setTicketBought(int ticketBought) {
		this.ticketBought = ticketBought;
	}
	
	public Button getAction() {
		return action;
	}
	
	public void setAction(Button action) {
		this.action = action;
	}
	
	public EventHandler<MouseEvent> showDetail() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Invoice.change = 0;
				Invoice.payment = -1;
				Invoice.shoeBrand = new ArrayList<>();
				Invoice.shoeColor = new ArrayList<>();
				Invoice.shoeID = new ArrayList<>();
				Invoice.shoeName = new ArrayList<>();
				Invoice.shoePrice = new ArrayList<>();
				Invoice.shoeQuantity = new ArrayList<>();
				LandingView.detailID = getTransactionID();
				DetailController.Print(getTransactionID());
				DetailController.refreshDetail(CustomerMainView.detailTable);

			}
		};
	}
	
}

