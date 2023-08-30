package Model;


public class Shoe {
	
	private String ShoeID, ShoeName, BrandName, ShoeColor;
	private int ticketPrice, ticketStock;
	
	public Shoe(String ShoeName, String BrandName, int ticketPrice, int ticketStock, String ShoeID, String ShoeColor) {
		super();
		this.ShoeName = ShoeName;
		this.BrandName = BrandName;
		this.setShoeColor(ShoeColor);
		this.ticketPrice = ticketPrice;
		this.ticketStock = ticketStock;
		this.setShoeID(ShoeID);
	}
	
	public String getShoeName() {
		return ShoeName;
	}
	
	public void setShoeName(String ShoeName) {
		this.ShoeName = ShoeName;
	}
	
	public String getBrandName() {
		return BrandName;
	}
	
	public void setBrandName(String BrandName) {
		this.BrandName = BrandName;
	}
	
	public int getTicketPrice() {
		return ticketPrice;
	}
	
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public int getTicketStock() {
		return ticketStock;
	}
	
	public void setTicketStock(int ticketStock) {
		this.ticketStock = ticketStock;
	}
	
	public String getShoeID() {
		return ShoeID;
	}
	
	public void setShoeID(String ShoeID) {
		this.ShoeID = ShoeID;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.ShoeName;
	}

	public String getShoeColor() {
		return ShoeColor;
	}

	public void setShoeColor(String shoeColor) {
		ShoeColor = shoeColor;
	}
}

