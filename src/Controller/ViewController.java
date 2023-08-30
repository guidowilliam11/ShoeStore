package Controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewController{
	
	public static Stage stage;
	
	public static void displayPage(Scene newScene) {
		stage.setScene(newScene);
		stage.show();
	}
}
