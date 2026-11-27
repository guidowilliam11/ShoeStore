package Util;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

// Shows a whole-rupiah amount as "Rp 2.000.000" in a table column.
public class Rupiah {
	private static final NumberFormat FORMAT = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

	public static String format(long amount) {
		return "Rp " + FORMAT.format(amount);
	}

	public static <S> void apply(TableColumn<S, Integer> column) {
		column.getStyleClass().add("number");
		column.setCellFactory(c -> new TableCell<S, Integer>() {
			{ getStyleClass().add("number"); }
			@Override
			protected void updateItem(Integer value, boolean empty) {
				super.updateItem(value, empty);
				setText(empty || value == null ? null : format(value));
			}
		});
	}
}
