package View;

import Controller.ViewController;
import javafx.scene.Scene;

public abstract class Page{
	
	protected abstract void initComp();
	protected abstract void addComp();
	protected abstract void arrangeComp();
	protected abstract void action();
	
	protected void displayView(Scene currentScene) {
		ViewController.displayPage(currentScene);
	}
	
}
