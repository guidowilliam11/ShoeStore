package Controller;

import java.util.Vector;

import Model.Shoe;
import Repository.BrandRepository;
import Repository.ShoeRepository;
import Model.ManageShoe;
import View.LandingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ShoeController {
	
	
	public static void displayAlert(AlertType alertType, String errorMessage) {
		Alert error = new Alert(alertType);
		error.setContentText(errorMessage);
		error.show();
	}
	
	public static String generateShoeID(Vector<ManageShoe>manageShoes, String name) {
		String latest = String.format("%03d" , ShoeRepository.getLatestShoeID()+1);
		String ShoeID = name.charAt(0)+latest;
		return ShoeID;
	}
	
	public static void doInsert(Vector<ManageShoe>manageShoes, int BrandID, String ShoeName, String price, int stock, String color) {
		if(ShoeName.isEmpty()) {
			displayAlert(AlertType.ERROR, "Shoe Name must not be empty");
			return;
		}
		ShoeRepository.insertShoe(generateShoeID(manageShoes, BrandRepository.getBrandName(BrandID)), BrandID, ShoeName, price, stock, color);
		
	}
	
	public static void doUpdate(int BrandID, String ShoeName, String price, int stock, String ShoeID, String color) {
		ShoeRepository.updateShoe(BrandID, ShoeName, price, stock, ShoeID, color);
	}
	
	public static void refreshManageShoe(TableView<ManageShoe> allShoeTable) {
		LandingView.manageShoes.removeAllElements();
		
		ShoeRepository.getManageShoe(LandingView.manageShoes);
		ObservableList<ManageShoe> allShoeObs = FXCollections.observableArrayList(LandingView.manageShoes);
		allShoeTable.setItems(allShoeObs);
		getComboBox();
		

	}
	
	@SuppressWarnings("unchecked")
	public static void getComboBox() {
		LandingView.Shoes.removeAllElements();
		ShoeRepository.getShoes(LandingView.Shoes);
		ObservableList<Shoe> allShoeObs = FXCollections.observableArrayList(LandingView.Shoes);
		LandingView.ShoeNameCB.setItems(allShoeObs);

	}
	
	public static void refreshShoe(TableView<Shoe> ShoeTable) {
		LandingView.Shoes.removeAllElements();
		
		ShoeRepository.getShoes(LandingView.Shoes);
		ObservableList<Shoe> allShoeObs = FXCollections.observableArrayList(LandingView.Shoes);
		ShoeTable.setItems(allShoeObs);
		getComboBox();
	}

	public static void doDelete(String ShoeID) {
		ShoeRepository.deleteShoe(ShoeID);
	}
}
