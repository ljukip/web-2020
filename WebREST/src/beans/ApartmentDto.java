package beans;

import java.util.ArrayList;

import beans.Apartment.Type;

public class ApartmentDto {

	private String id;
	private Apartment.Type type;
	private int rooms;
	private int guests;
	private Location location;
	private long to;
	private long from;
	private String host;
	private int price;
	private String status;

	private ArrayList<String> amenities;

	public ApartmentDto(String id, Type type, int rooms, int guests, Location location, long to, long from, String host,
			int price, String status, ArrayList<String> amenities) {
		super();
		this.id = id;
		this.type = type;
		this.rooms = rooms;
		this.guests = guests;
		this.location = location;
		this.to = to;
		this.from = from;
		this.host = host;
		this.price = price;
		this.status = status;
		this.amenities = amenities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Apartment.Type getType() {
		return type;
	}

	public void setType(Apartment.Type type) {
		this.type = type;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(ArrayList<String> amenities) {
		this.amenities = amenities;
	}

	public ApartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
