package Controller;

import Model.Brand;
import Repository.BrandRepository;
import Model.ManageBrand;
import View.LandingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;

public class BrandController {

	public static void displayAlert(AlertType alertType, String errorMessage) {
		Alert error = new Alert(alertType);
		error.setContentText(errorMessage);
		error.show();
	}
	
	public static int generateBrandID() {
		return BrandRepository.getLatestBrandID();
	}
	
	public static void doInsert(String BrandName) {
		if(BrandName.isEmpty()) {
			displayAlert(AlertType.ERROR, "Brand Name must not be empty");
			return;
		}
		BrandRepository.insertBrand(generateBrandID(), BrandName);
	}
	
	public static void doUpdate(int id, String newBrand) {
		BrandRepository.updateBrand(id, newBrand);
	}
	
	@SuppressWarnings("unchecked")
	public static void getComboBox() {
		LandingView.Brands.removeAllElements();
		
		BrandRepository.getBrands(LandingView.Brands);
		ObservableList<Brand> BrandNameObs = FXCollections.observableArrayList(LandingView.Brands);
		LandingView.BrandNameCB.setItems(BrandNameObs);
	}

	public static void refreshManageBrand(TableView<ManageBrand> allBrandTable) {

		LandingView.manageBrands.removeAllElements();
		
		BrandRepository.getManageBrand(LandingView.manageBrands);
		ObservableList<ManageBrand> allBrandObs = FXCollections.observableArrayList(LandingView.manageBrands);
		allBrandTable.setItems(allBrandObs);
		
		getComboBox();
	}

	public static void doDelete(int BrandID) {
		BrandRepository.deleteBrand(BrandID);
		
	}
}
