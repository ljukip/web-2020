package beans;

import java.util.Date;

public class Reservation {
	
	private enum Status {created, declined, canceled, accepted, completed}
	
	private Apartment apartmentReserved;
	private Date startDate;
	private int numberOfNights;
	private float price;
	private String notes;
	private String guest;
	private String status;
	
	public Reservation(Apartment apartmentReserved, Date startDate, int numberOfNights, float price, String notes,
			String guest, String status) {
		super();
		this.apartmentReserved = apartmentReserved;
		this.startDate = startDate;
		this.numberOfNights = numberOfNights;
		this.price = price;
		this.notes = notes;
		this.guest = guest;
		this.status = status;
	}

	public Apartment getApartmentReserved() {
		return apartmentReserved;
	}

	public void setApartmentReserved(Apartment apartmentReserved) {
		this.apartmentReserved = apartmentReserved;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
