package beans;

public class Adress {
	
	private String streetNum;
	private String city;
	private String zip;
	
	public Adress(String streetNum, String city, String zip) {
		super();
		this.streetNum = streetNum;
		this.city = city;
		this.zip = zip;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	

}
