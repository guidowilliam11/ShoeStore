import Controller.ViewController;
import View.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		ViewController.stage = arg0;
		new LoginView();
	}

}
