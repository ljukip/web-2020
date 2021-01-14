package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Apartment {
	
	public enum Type {room, apartment}
	
	private Type type;
	private int capacity;
	private int rooms;
	private Location location;
	private ArrayList <Date> datesAvailable; //host desides
	private ArrayList <Date> availability; //dates left available after reservation has been made
	private long to;
	private long from;
	private String host;
	private Collection<Review> reviews;
	private ArrayList<String> images;
	private int price;
	private String checkin;
	private String checkout;
	private String status;
	private ArrayList <Amenity> amenities;
	private Collection <Reservation> reservations;
	private String id;
	
	
	
	public Apartment(String id, Type type, int capacity, int rooms, Location location, long to, long from, String host,
			ArrayList<String> images, int pricePerNight, String checkin, String checkout, String status,
			ArrayList<Amenity> amenities) {
		super();
		this.type = type;
		this.capacity = capacity;
		this.rooms = rooms;
		this.location = location;
		this.to = to;
		this.from = from;
		this.host = host;
		this.images = images;
		this.price = pricePerNight;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
		this.amenities = amenities;
		this.id = id;
	}



	public Apartment(Type type, int capacity, int rooms, Location location, ArrayList<Date> datesAvailable,
			ArrayList<Date> availability, String host, Collection<Review> reviews, ArrayList<String> images,
			int pricePerNight, String checkin, String checkout, String status, ArrayList<Amenity> amenities,
			Collection<Reservation> reservations) {
		super();
		this.type = type;
		this.capacity = capacity;
		this.rooms = rooms;
		this.location = location;
		this.datesAvailable = datesAvailable;
		this.availability = availability;
		this.host = host;
		this.reviews = reviews;
		this.images = images;
		this.price = pricePerNight;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
		this.amenities = amenities;
		this.reservations = reservations;
	}
	
	

	public Apartment() {
		// TODO Auto-generated constructor stub
		super();
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



	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public int getRooms() {
		return rooms;
	}


	public void setRooms(int rooms) {
		this.rooms = rooms;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public ArrayList<Date> getDatesAvailable() {
		return datesAvailable;
	}


	public void setDatesAvailable(ArrayList<Date> datesAvailable) {
		this.datesAvailable = datesAvailable;
	}


	public ArrayList<Date> getAvailability() {
		return availability;
	}


	public void setAvailability(ArrayList<Date> availability) {
		this.availability = availability;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public Collection<Review> getReviews() {
		return reviews;
	}


	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}


	public ArrayList<String> getImages() {
		return images;
	}


	public void setImages(ArrayList<String> images) {
		this.images = images;
	}


	public int getPricePerNight() {
		return price;
	}


	public void setPricePerNight(int pricePerNight) {
		this.price = pricePerNight;
	}


	public String getCheckin() {
		return checkin;
	}


	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}


	public String getCheckout() {
		return checkout;
	}


	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ArrayList<Amenity> getAmenities() {
		return amenities;
	}


	public void setAmenities(ArrayList<Amenity> amenities) {
		this.amenities = amenities;
	}


	public Collection<Reservation> getReservations() {
		return reservations;
	}


	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
