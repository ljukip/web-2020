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
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import beans.Amenity;
import beans.Apartment;
import beans.Location;

public class ApartmentDao {

	private List<Apartment> apartments = new ArrayList<>();
	private LocationDao dao;
	private AmenityDao amenityDao;

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public ApartmentDao(String contextPath, LocationDao dao, AmenityDao amenityDao) {
		read(contextPath);
		this.dao=dao;
		this.amenityDao=amenityDao;
		read(contextPath);
	}
	public ApartmentDao(String contextPath) {
		read(contextPath);
	}
	public String getPath() {
			return path;
		}
	 public List<Apartment> getAll() {
	        return apartments;
	    }
	 public Collection<Apartment> findAll() {
			return apartments;
		}
	 public Apartment findOne(String id) {
			for(int i=0; i<apartments.size();i++ ) {
				if(apartments.get(i).getId().equals(id))
					return apartments.get(i);
			}
			return null;
		}
	 public Collection<Apartment> findByHostUsername(String username) {
			Collection<Apartment> allApartments = apartments;
			System.out.println("apartmani**********************************"+ allApartments);
			Collection<Apartment> hostsApartments = new ArrayList<Apartment>();
			for (Apartment a : allApartments) {
				if (a.getHost().equals(username)) {
					hostsApartments.add(a);
				}
			}
			return hostsApartments;
		}
	 
	 public Apartment update(Apartment apartment, String contextPath) {
		 boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/apartments.txt");
	    		File tempFile = new File(contextPath + "apartmentsTemp.txt");
				BufferedReader in = new BufferedReader(new FileReader(file));
				BufferedWriter writer =null;
				writer = new BufferedWriter(new FileWriter(tempFile, true));
				
				String s = "", newS = "";
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					//s = s.trim();
					if (s.equals(""))
						continue;
					//uzimamo reci odvojene zarezom, trimujemo, proveravamo da li smo nasli trazeni id
					String[] tokens = s.split(",");
					String id= tokens[0];
					System.out.println(id +"+++++++++++update++++++++++++++" +apartment.getId()); 
					if (id.equals(apartment.getId())) {
						//update-ujemo
						newS += apartment.getId() + "," +
				    			apartment.getType()+ "," +
				    			apartment.getCapacity()+ "," +
				    			apartment.getRooms()+ "," +
				    			apartment.getLocation().getId().toString() + "," +
				    			apartment.getPricePerNight()+ "," +
				    			apartment.getCheckin()+ "," +
				    			apartment.getCheckout()+ "," +
				    			apartment.getTo()+ "," +
				    			apartment.getFrom()+ "," +
				    			apartment.getHost()+ "," +
				    			apartment.getStatus() +"\r\n"; 
				    	
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
		    	Apartment removeMe = null;
		    	for (Apartment l : apartments) {
		    	    if (apartment.getId().equals(l.getId())) {
		    	    	removeMe=l;
		    	    }
		    	}
		    	apartments.remove(removeMe);
		    	apartments.add(apartment);
		    	amenityDao.updateApartmentAmenities(contextPath, apartment);
				read(contextPath);
		    	return apartment;
	    	}
	    	else
	    		return null;
	        
	    }
	 public void delete(Apartment apartment, String contextPath) {

			boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/apartments.txt");
	    		File tempFile = new File(contextPath + "apartmentsTemp.txt");
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
					System.out.println(id +"+++++++++++++++++++++++++" +apartment.getId()); //ne moze da se menja username!!!
					if (id.equals(apartment.getId())) {
						//update-ujemo
						newS += "";
				    	
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
		    	apartments.remove(apartment);
	    	}
	 }

	 
	 public Apartment save(Apartment apartment) {
	    	//set id byincrementation {check all ids, take the max and asign next}
	    	int maxId=-1; //first will be 0 (maxId+1)
	    	for (int i=0; i<apartments.size();i++) {
	    		int amenityId=Integer.parseInt(apartments.get(i).getId());
	    		if(maxId<amenityId) {
	    			maxId=amenityId;
	    		}
	    	}
	    	maxId++;
	    	apartment.setId(Integer.toString(maxId));
	    	apartment.setStatus("inactive");
	    	
	    	String s = apartment.getId() + "," +
	    			apartment.getType()+ "," +
	    			apartment.getCapacity()+ "," +
	    			apartment.getRooms()+ "," +
	    			apartment.getLocation().getId().toString()+ "," +
	    			apartment.getPricePerNight()+ "," +
	    			apartment.getCheckin()+ "," +
	    			apartment.getCheckout()+ "," +
	    			apartment.getTo()+ "," +
	    			apartment.getFrom()+ "," +
	    			apartment.getHost()+ "," +
	    			apartment.getStatus();   			
	    	
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/apartments.txt");
				System.out.println("put:"+file.getAbsolutePath());
				writer = new BufferedWriter(new FileWriter(file, true));
				PrintWriter out = new PrintWriter(writer); //txt
				out.println(s);
				System.out.println("upisuje u apartments: " + s);
				out.close();
				//upisujemo u novi fajl id apartmana i amenety koje ima
				for (Amenity amenity : apartment.getAmenities()) {
					String line2 = apartment.getId() + "," + amenity.getId();
					System.out.println(line2);

					File file2 = new File(path + "/web-2020/WebRest/apartment-amenities.txt");
					BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2, true));
					PrintWriter out2 = new PrintWriter(writer2);
					out2.println(line2);
					out2.close();
				}
					
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
	    	
	        apartments.add(apartment);
			return apartment;
	    }
	 
	 public void read (String contextPath) {
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/apartments.txt");
				in = new BufferedReader(new FileReader(file));
				String s;
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					s = s.trim();
					if (s.equals(""))
						continue;
					st = new StringTokenizer(s, ",");
					System.out.println("krece da cita apartments:");
					while (st.hasMoreTokens()) {
						String id= st.nextToken().trim();
						Apartment.Type type = Apartment.Type.valueOf(st.nextToken().trim());
						int guests = Integer.parseInt(st.nextToken().trim());
						int rooms = Integer.parseInt(st.nextToken().trim());
						Location location = dao.findOne(st.nextToken().trim());
						int price = Integer.parseInt( st.nextToken().trim());
						String checkin = st.nextToken().trim();
						String checkout = st.nextToken().trim();
						long to = Long.parseLong(st.nextToken().trim());
						long from = Long.parseLong(st.nextToken().trim());
						String host = st.nextToken().trim();
						String status = st.nextToken().trim();
						ArrayList<String> images = loadImages(contextPath, id);
						ArrayList<Amenity> amenities = amenityDao.findAllByApartmentId(contextPath, id);
						
						apartments.add(new Apartment(id,type,guests,rooms,location,to,from,host,images,price,checkin,checkout,status,amenities));
						
						System.out.println(id);
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
	 
	 public ArrayList<String> loadImages(String contextPath, String id) {
			ArrayList<String> images = new ArrayList<>();
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/images.txt");
				in = new BufferedReader(new FileReader(file));
				String line;
				StringTokenizer st;
				while ((line = in.readLine()) != null) {
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, ";");
					while (st.hasMoreTokens()) {
						String apartmentId = st.nextToken().trim();
						String image = st.nextToken().trim();
						if (apartmentId.equals(id)) {
							images.add(image);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						return null;
					}
				}
			}
			return images;
		}
	 
	 public void saveImage(String contextPath, String imagePath, String apartmentId) {
			String line = apartmentId + ";" + imagePath;
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/images.txt");
				writer = new BufferedWriter(new FileWriter(file, true));
				PrintWriter out = new PrintWriter(writer);
				out.println(line);
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (Exception e) { }
				}
			}
			read(contextPath);
		}

}
