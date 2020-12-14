package beans;

public class Review {
	
	private String guest;
	private Apartment apartment;
	private String review;
	private int rating;
	
	public Review(String guest, Apartment apartment, String review, int rating) {
		super();
		this.guest = guest;
		this.apartment = apartment;
		this.review = review;
		this.rating = rating;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	

}
