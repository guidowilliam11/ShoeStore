package Model;

public class User {
	private String rolePas;
	private int userIDPass;
	private String userAction;
	
	public User(String rolePas, int userIDPass) {
		super();
		this.rolePas = rolePas;
		this.userIDPass = userIDPass;
		this.setUserAction("non");
	}

	public String getRolePas() {
		return rolePas;
	}

	public int getUserIDPass() {
		return userIDPass;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
	
	
}
