package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Reservation;
import beans.Review;

public class ReviewDao {

	private List<Review> reviews = new ArrayList<>();

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public ReviewDao(String contextPath) {
		read(contextPath);
	}
	
	public List<Review> getAll() {
        return reviews;
    }
	
	public List<Review> getApartments(String apartmentId) {
		List<Review> apartmentsReview = new ArrayList<>();
		for (Review r : reviews) {
    		System.out.println("poredi:"+apartmentId + "i" + r.getApartmentId());
    	    if (r.getApartmentId().equals(apartmentId)) {
    	    	System.out.println("nasao");
    	    	apartmentsReview.add(r);
    	    }
    	}
		return apartmentsReview;
    }
	
	public Review update(String contextPath, Review review) {
		
		 boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/reviews.txt");
	    		File tempFile = new File(contextPath + "reviewTemp.txt");
				BufferedReader in = new BufferedReader(new FileReader(file));
				BufferedWriter writer =null;
				writer = new BufferedWriter(new FileWriter(tempFile, true));
				
				String s = "", newS = "";
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					//s = s.trim();
					if (s.equals(""))
						continue;
					//uzimamo reci odvojene zarezom, trimujemo, proveravamo da li smo nasli trazeni username
					String[] tokens = s.split(",");
					String id= tokens[0];
					System.out.println(id +"+++++++++++++++++++++++++" + review.getId());
					if (review.getId().equals(id)) {
						//update-ujemo
						newS +=review.getId() + "," +
				    			review.getApartmentId() + "," +
				    			review.getGuestId() + "," +
				    			review.getReview() + "," +
				    			Integer.toString(review.getRating()) + "," +
				    			String.valueOf(review.getPublished()) + "\r\n";
								
						match=true;
					} else {
						newS += s + "\r\n";
					}
				}
			in.close();
			//cuvamo promene
			PrintWriter out = new PrintWriter(writer);
			out.println(newS);
			System.out.println("novi string:" + newS);
			out.close();
			Files.copy(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        tempFile.delete();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	    	if (match==true) {
	    		Review removeMe = null;
		    	for (Review r : reviews) {
		    	    if (review.getId().equals(r.getId())) {
		    	    	removeMe=r;
		    	    }
		    	}
		    	reviews.remove(removeMe);
		    	reviews.add(review);
		    	return review;
	    	}
	    	else
	    		return null;
	        
		}
	
	
	public void save(Review review, String contextPath) {
    	//set id byincrementation {check all ids, take the max and asign next}
    	int maxId=-1; //first will be 0 (maxId+1)
    	for (int i=0; i<reviews.size();i++) {
    		int amenityId=Integer.parseInt(reviews.get(i).getId());
    		if(maxId<amenityId) {
    			maxId=amenityId;
    		}
    	}
    	maxId++;
    	review.setId(Integer.toString(maxId));
    	
    	String s = review.getId() + "," +
    			review.getApartmentId() + "," +
    			review.getGuestId() + "," +
    			review.getReview() + "," +
    			Integer.toString(review.getRating()) + "," +
    			String.valueOf(review.getPublished());
    	
		BufferedWriter writer = null;
		try {
			File file = new File(path + "/web-2020/WebRest/reviews.txt");
			System.out.println("put:"+file.getAbsolutePath());
			writer = new BufferedWriter(new FileWriter(file, true));
			PrintWriter out = new PrintWriter(writer); //txt
			out.println(s);
			System.out.println("upisuje u reviews: " + s);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
    	
		reviews.add(review);
    }
	
	
	public void read (String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/web-2020/WebRest/reviews.txt");
			in = new BufferedReader(new FileReader(file));
			String s;
			StringTokenizer st;
			while ((s = in.readLine()) != null) {
				s = s.trim();
				if (s.equals(""))
					continue;
				st = new StringTokenizer(s, ",");
				System.out.println("krece da cita reviews:");
				while (st.hasMoreTokens()) {
					String id = st.nextToken().trim();
					String apartmentId = st.nextToken().trim();
					String guestId = st.nextToken().trim();
					String review=st.nextToken().trim();
					int rating = Integer.parseInt(st.nextToken().trim());
					boolean published = Boolean.parseBoolean(st.nextToken().trim());

					reviews.add(new Review(id,guestId, apartmentId, review, rating, published));
					}
				}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) { }
			}
		}
	}
	
}
