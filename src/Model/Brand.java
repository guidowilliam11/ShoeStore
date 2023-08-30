package Model;

public class Brand{
	
	private int BrandID;
	private String BrandName;

	public Brand(int BrandID, String BrandName) {
		super();
		this.BrandID = BrandID;
		this.BrandName = BrandName;
	}

	public int getBrandID() {
		return BrandID;
	}

	public void setBrandID(int BrandID) {
		this.BrandID = BrandID;
	}

	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String BrandName) {
		this.BrandName = BrandName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getBrandName();
	}
	
}

