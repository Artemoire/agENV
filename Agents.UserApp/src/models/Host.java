package models;

public class Host {
	
	private String adress;
	private String alias;
	
	public Host() {
		
	}
	
	public Host(String adress, String alias) {
		super();
		this.adress = adress;
		this.alias = alias;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "Host [adress=" + adress + ", alias=" + alias + "]";
	}
	
	
}
