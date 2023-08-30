package Model;


public class Detail {
	
	private int transactionID;
	private String ShoeName, BrandName;
	private int quantity;
	
	public Detail(int transactionID, String ShoeName, String BrandName, int quantity) {
		super();
		this.transactionID = transactionID;
		this.ShoeName = ShoeName;
		this.BrandName = BrandName;
		this.quantity = quantity;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
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
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

