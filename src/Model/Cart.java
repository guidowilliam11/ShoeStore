package Model;


public class Cart {
	
	private String ShoeName;
	private int quantity, price;
	private String ShoeID;
	
	public Cart(String ShoeName, int quantity, int price, String ShoeID) {
		super();
		this.ShoeName = ShoeName;
		this.quantity = quantity;
		this.price = price;
		this.ShoeID = ShoeID;
	}
	public String getShoeName() {
		return ShoeName;
	}
	
	public void setShoeName(String ShoeName) {
		this.ShoeName = ShoeName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getShoeID() {
		return ShoeID;
	}
	
	public void setShoeID(String ShoeID) {
		this.ShoeID = ShoeID;
	}
	
}

