package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Apartment {
	
	private String type;
	private int capacity;
	private int rooms;
	private Location location;
	private ArrayList <Date> datesAvailable;
	private ArrayList <Date> availability;
	private String host;
	private Collection<Review> reviews;
	private ArrayList<String> images;
	private double pricePerNight;
	private String checkin;
	private String checkout;
	private String status;
	private ArrayList <Amenity> amenities;
	private Collection <Reservation> reservations;
	
	
	public Apartment(String type, int capacity, int rooms, Location location, ArrayList<Date> datesAvailable,
			ArrayList<Date> availability, String host, Collection<Review> reviews, ArrayList<String> images,
			double pricePerNight, String checkin, String checkout, String status, ArrayList<Amenity> amenities,
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
		this.pricePerNight = pricePerNight;
		this.checkin = checkin;
		this.checkout = checkout;
		this.status = status;
		this.amenities = amenities;
		this.reservations = reservations;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
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


	public double getPricePerNight() {
		return pricePerNight;
	}


	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
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
	
	
	
	
}
