package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Model.History;
import Repository.TransactionRepository;
import View.LandingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class TransactionController {

	public static void refreshController(TableView<History> historyTable) {
		LandingView.transactions.removeAllElements();
		TransactionRepository.getTransaction(historyTable);
		ObservableList<History> historyObs = FXCollections.observableArrayList(LandingView.transactions);
		historyTable.setItems(historyObs);
	}
	
	public static void doInsert(int moneyValue) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now(); 
		TransactionRepository.insertTransaction(LandingView.userID, dtf, now, moneyValue);
		
	}

}
