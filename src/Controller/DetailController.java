package Controller;

import Model.Cart;
import Repository.ShoeRepository;
import Model.Detail;
import Repository.DetailRepository;
import View.LandingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DetailController {

	public static void refreshDetail(TableView<Detail> detailTable) {
		LandingView.details.removeAllElements();
		DetailRepository.getDetail(detailTable);
		ObservableList<Detail> detailObs = FXCollections.observableArrayList(LandingView.details);
		detailTable.setItems(detailObs);
	}
	
	public static void doInsert() {
		for (Cart cart : LandingView.carts) {
			DetailRepository.insertTransaction(cart);
			ShoeRepository.updateTicketStock(cart);
		}
	}

	public static void Print(int transactionID) {
		DetailRepository.setInvoice(transactionID);
	}

}
