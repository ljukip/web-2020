package beans;

public class Location {
	
	private String longitude;
	private String latitude;
	private Adress adress;
	private String id;
	
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Location(String longitude, String latitude, Adress adress, String id) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.adress = adress;
		this.setId(id);
	}
	public Location(String longitude, String latitude, Adress adress) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.adress = adress;
	}
	public Location(String longitude, String latitude, String street, String city, String zip, String id) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.adress= new Adress(street,city,zip);
		this.id=id;
	}
	public Location(String longitude, String latitude, String street, String city, String zip) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.adress.setStreetNum(street);
		this.adress.setCity(city);
		this.adress.setZip(zip);
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
