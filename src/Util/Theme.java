package Util;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

// The app's look: AtlantaFX for the controls, shoestore.css for the brand and the product cards,
// Ikonli (Feather) for icons.
public class Theme {
	private static final String SHEET = Theme.class.getResource("shoestore.css").toExternalForm();

	// Call once at start-up, before the first scene.
	public static void install() {
		Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
	}

	public static Scene apply(Scene scene) {
		scene.getStylesheets().add(SHEET);
		return scene;
	}

	// Our names for AtlantaFX's button styles, so views say what a button is for.
	public static <T extends Node> T style(T node, String... classes) {
		for (String c : classes) {
			switch (c) {
				case "primary": node.getStyleClass().add("accent"); break;
				case "link": node.getStyleClass().addAll("flat", "accent"); break;
				case "danger": node.getStyleClass().addAll("danger", "flat", "small"); break;
				default: node.getStyleClass().add(c);
			}
		}
		return node;
	}

	public static <T extends Labeled> T icon(T control, Ikon ikon) {
		FontIcon icon = new FontIcon(ikon);
		icon.setIconSize(15);
		control.setGraphic(icon);
		control.setGraphicTextGap(8);
		return control;
	}

	// Whole numbers right-aligned, so stock and quantities line up by digit.
	public static <S> void number(TableColumn<S, Integer> column) {
		column.getStyleClass().add("number");
		column.setCellFactory(c -> new TableCell<S, Integer>() {
			{ getStyleClass().add("number"); }
			@Override
			protected void updateItem(Integer value, boolean empty) {
				super.updateItem(value, empty);
				setText(empty || value == null ? null : String.valueOf(value));
			}
		});
	}
}
