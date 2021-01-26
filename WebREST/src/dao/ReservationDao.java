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

import beans.Amenity;
import beans.Apartment;
import beans.Reservation;

public class ReservationDao {
	private List<Reservation> reservations = new ArrayList<>();

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public ReservationDao(String contextPath) {
		read(contextPath);
	}
	
	 public Reservation findApartmentId(String apartmentId) {
	    	for (Reservation r : reservations) {
	    		System.out.println("poredi:"+apartmentId + "i" + r.getApartmentId());
	    	    if (r.getApartmentId().equals(apartmentId)) {
	    	    	System.out.println("nasao");
	    	    	return r;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return null;
	    }
	 
	 public List<Reservation> getAll() {
	        return reservations;
	    }
	 public Reservation update(String contextPath, Reservation reservation) {
			
		 boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/reservations.txt");
	    		File tempFile = new File(contextPath + "reservationsTemp.txt");
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
					System.out.println(id +"+++++++++++++++++++++++++" + reservation.getId()); //ne moze da se menja username!!!
					if (reservation.getId().equals(id)) {
						//update-ujemo
						newS += reservation.getId() + "," +
				    			reservation.getApartmentId() + "," +
				    			reservation.getGuestId() + "," +
				    			reservation.getFrom() + "," +
				    			reservation.getTo() + "," +
				    			reservation.getNight() + "," +
				    			reservation.getPrice() + "," +
				    			reservation.getMessage() + "," +
				    			reservation.getStatus()+ "\r\n";
								
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
		    	Reservation removeMe = null;
		    	for (Reservation r : reservations) {
		    	    if (reservation.getId().equals(r.getId())) {
		    	    	removeMe=r;
		    	    }
		    	}
	    	    reservations.remove(removeMe);
		    	reservations.add(reservation);
		    	return reservation;
	    	}
	    	else
	    		return null;
	        
		}
	 
	 public void save(Reservation reservation, String contextPath) {
	    	//set id byincrementation {check all ids, take the max and asign next}
	    	int maxId=-1; //first will be 0 (maxId+1)
	    	for (int i=0; i<reservations.size();i++) {
	    		int amenityId=Integer.parseInt(reservations.get(i).getId());
	    		if(maxId<amenityId) {
	    			maxId=amenityId;
	    		}
	    	}
	    	maxId++;
	    	reservation.setId(Integer.toString(maxId));
	    	
	    	String s = reservation.getId() + "," +
	    			reservation.getApartmentId() + "," +
	    			reservation.getGuestId() + "," +
	    			reservation.getFrom() + "," +
	    			reservation.getTo() + "," +
	    			reservation.getNight() + "," +
	    			reservation.getPrice() + "," +
	    			reservation.getMessage() + "," +
	    			reservation.getStatus();
	    	
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/reservations.txt");
				System.out.println("put:"+file.getAbsolutePath());
				writer = new BufferedWriter(new FileWriter(file, true));
				PrintWriter out = new PrintWriter(writer); //txt
				out.println(s);
				System.out.println("upisuje u amenities: " + s);
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
	    	
			reservations.add(reservation);
	    }
	 
	    public void read (String contextPath) {
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/reservations.txt");
				in = new BufferedReader(new FileReader(file));
				String s;
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					s = s.trim();
					if (s.equals(""))
						continue;
					st = new StringTokenizer(s, ",");
					System.out.println("krece da cita reservations:");
					while (st.hasMoreTokens()) {
						String id = st.nextToken().trim();
						String apartmentId = st.nextToken().trim();
						String guestId = st.nextToken().trim();
						long from = Long.parseLong(st.nextToken().trim());
						long to = Long.parseLong(st.nextToken().trim());
						int night = Integer.parseInt(st.nextToken().trim());
						int price = Integer.parseInt(st.nextToken().trim());
						String message = st.nextToken().trim();
						String status = st.nextToken().trim();

						reservations.add(new Reservation(id, apartmentId, from, to,
									night, price, message, guestId, Reservation.Status.valueOf(status)));
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
