package View;

import java.io.InputStream;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import Model.Shoe;
import Util.Rupiah;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

// One shoe in the shop grid: photo, name, brand and colour, price and how many are left.
public class ProductCard extends VBox {
	public static final int LOW_STOCK = 10;
	private static final double W = 226;
	private static final double PH = 168; // photo height: 4:3, so two rows fit without scrolling

	private final Shoe shoe;

	public ProductCard(Shoe shoe) {
		this.shoe = shoe;
		getStyleClass().add("product-card");
		setSpacing(6);
		setPrefWidth(W);
		setMaxWidth(W);

		StackPane photo = new StackPane();
		photo.getStyleClass().add("photo");
		photo.setPrefSize(W - 16, PH);
		photo.setMinSize(W - 16, PH);
		Image image = load(shoe.getShoeID());
		if (image != null) {
			ImageView view = new ImageView(image);
			view.setFitWidth(W - 16);
			view.setFitHeight(PH);
			view.setPreserveRatio(false);
			// Square photos are cropped to the frame's ratio from the centre, not squashed.
			double side = Math.min(image.getWidth(), image.getHeight());
			double cropH = side * PH / (W - 16);
			view.setViewport(new javafx.geometry.Rectangle2D(0, (image.getHeight() - cropH) / 2, side, cropH));
			Rectangle clip = new Rectangle(W - 16, PH);
			clip.setArcWidth(16);
			clip.setArcHeight(16);
			view.setClip(clip);
			photo.getChildren().add(view);
		} else {
			FontIcon none = new FontIcon(Feather.IMAGE);
			none.setIconSize(28);
			photo.getChildren().add(none);
		}
		Label badge = stockBadge(shoe.getTicketStock());
		StackPane.setAlignment(badge, Pos.TOP_LEFT);
		StackPane.setMargin(badge, new javafx.geometry.Insets(8));
		photo.getChildren().add(badge);

		Label name = new Label(shoe.getShoeName());
		name.getStyleClass().add("name");
		Label meta = new Label(shoe.getBrandName() + " · " + shoe.getShoeColor());
		meta.getStyleClass().add("muted");

		Label price = new Label(Rupiah.format(shoe.getTicketPrice()));
		price.getStyleClass().add("price");
		Region gap = new Region();
		HBox.setHgrow(gap, Priority.ALWAYS);
		Label stock = new Label(shoe.getTicketStock() + " left");
		stock.getStyleClass().add("muted");
		HBox bottom = new HBox(price, gap, stock);
		bottom.setAlignment(Pos.BASELINE_LEFT);

		getChildren().addAll(photo, name, meta, bottom);
		if (shoe.getTicketStock() == 0) setDisable(true);
	}

	public Shoe getShoe() { return shoe; }

	public void setSelected(boolean selected) {
		if (selected) { if (!getStyleClass().contains("selected")) getStyleClass().add("selected"); }
		else getStyleClass().remove("selected");
	}

	// Only shown when it matters: sold out, or running low.
	private static Label stockBadge(int stock) {
		Label badge = new Label(stock == 0 ? "Sold out" : "Only " + stock + " left");
		badge.getStyleClass().addAll("badge", stock == 0 ? "out" : "low");
		badge.setVisible(stock < LOW_STOCK);
		badge.setManaged(stock < LOW_STOCK);
		return badge;
	}

	private static Image load(String shoeID) {
		InputStream in = ProductCard.class.getResourceAsStream("/Util/shoes/" + shoeID + ".jpg");
		return in == null ? null : new Image(in);
	}
}
