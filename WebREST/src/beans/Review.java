package beans;

public class Review {
	
	private String id;
	private String guestId;
	private String apartmentId;
	private String review;
	private int rating;
	private boolean published;
	
	
	public Review(String id,String guestId, String apartmentId, String review, int rating, boolean published) {
		super();
		this.guestId = guestId;
		this.apartmentId = apartmentId;
		this.review = review;
		this.rating = rating;
		this.published = published;
		this.setId(id);
	}


	public String getGuestId() {
		return guestId;
	}


	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}


	public String getApartmentId() {
		return apartmentId;
	}


	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
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


	public boolean getPublished() {
		return published;
	}


	public void setPublished(boolean published) {
		this.published = published;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}