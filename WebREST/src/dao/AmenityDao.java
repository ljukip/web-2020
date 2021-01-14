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

public class AmenityDao {

	private List<Amenity> amenities = new ArrayList<>();

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public AmenityDao(String contextPath) {
		read(contextPath);
	}
	
	 public List<Amenity> getAll() {
	        return amenities;
	    }
	 public Collection<Amenity> findAll() {
			return amenities;
		}
	 public Amenity findAmenity(int id) {
	    	for (Amenity a : amenities) {
	    		System.out.println("poredi:"+id + "i" + a.getId());
	    	    if (id==a.getId()) {
	    	    	System.out.println("nasao");
	    	    	return a;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return null;
	    }
	 public Amenity findAmenityName(String name) {
	    	for (Amenity a : amenities) {
	    		System.out.println("poredi:"+name + "i" + a.getName());
	    	    if (name==a.getName()) {
	    	    	System.out.println("nasao");
	    	    	return a;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return null;
	    }
	 
	 public Amenity update(Amenity amenity, String contextPath) {

			boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/amenities.txt");
	    		File tempFile = new File(contextPath + "amenitiesTemp.txt");
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
					String id= tokens[2];
					System.out.println(id +"+++++++++++++++++++++++++" +amenity.getId()); //ne moze da se menja username!!!
					if (id.equals(Integer.toString(amenity.getId()))) {
						//update-ujemo
						newS += amenity.getName() + "," +
				    			amenity.getType() + "," +
				    			Integer.toString(amenity.getId()) + "," +
				    			String.valueOf(amenity.getDeleted()) + "\r\n";
				    	
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
		    	Amenity removeMe = null;
		    	for (Amenity a : amenities) {
		    	    if (amenity.getId()==a.getId()) {
		    	    	removeMe=a;
		    	    }
		    	}
	    	    amenities.remove(removeMe);
		    	amenities.add(amenity);
		    	return amenity;
	    	}
	    	else
	    		return null;
	        
	    }
	 
	 public void delete(Amenity amenity, String contextPath) {

			boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/amenities.txt");
	    		File tempFile = new File(contextPath + "amenitiesTemp.txt");
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
					String id= tokens[2];
					System.out.println(id +"+++++++++++++++++++++++++" +amenity.getId()); //ne moze da se menja username!!!
					if (id.equals(Integer.toString(amenity.getId()))) {
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
		    	amenities.remove(amenity);
	    	}
	 }
	 
	    
	    public void save(Amenity amenity, String contextPath) {
	    	//set id byincrementation {check all ids, take the max and asign next}
	    	int maxId=-1; //first will be 0 (maxId+1)
	    	for (int i=0; i<amenities.size();i++) {
	    		int amenityId=amenities.get(i).getId();
	    		if(maxId<amenityId) {
	    			maxId=amenityId;
	    		}
	    	}
	    	maxId++;
	    	amenity.setId(maxId);
	    	amenity.setDeleted(false);
	    	
	    	String s = amenity.getName() + "," +
	    			amenity.getType() + "," +
	    			Integer.toString(amenity.getId()) + "," +
	    			String.valueOf(amenity.getDeleted());
	    	
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/amenities.txt");
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
	    	
	        amenities.add(amenity);
	    }
	    
	    public void read (String contextPath) {
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/amenities.txt");
				in = new BufferedReader(new FileReader(file));
				String s;
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					s = s.trim();
					if (s.equals(""))
						continue;
					st = new StringTokenizer(s, ",");
					System.out.println("krece da cita amenities:");
					while (st.hasMoreTokens()) {
						String name = st.nextToken().trim();
						String type = st.nextToken().trim();
						int id = Integer.parseInt(st.nextToken().trim());
						boolean deleted = Boolean.parseBoolean(st.nextToken().trim());
						if (!deleted) {
							amenities.add(new Amenity(name,Amenity.Type.valueOf(type),id,deleted));
						}
						
						System.out.println(name);
						System.out.println(type);
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
	    
	    public ArrayList<Amenity> findAllByApartmentId(String contextPath, String id) {
			ArrayList<Amenity> returnAmenities = new ArrayList<>();
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/apartment-amenities.txt");
				in = new BufferedReader(new FileReader(file));
				String line;
				StringTokenizer st;
				while ((line = in.readLine()) != null) {
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, ",");
					while (st.hasMoreTokens()) {
						String apartmentId = st.nextToken().trim();
						String amenityId = st.nextToken().trim();
						for(int i=0; i<amenities.size();i++) {
							if (apartmentId.equals(id) && amenities.get(i).getId()==Integer.parseInt(amenityId)) {
								returnAmenities.add(amenities.get(i));
							}
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
			return returnAmenities;
		}
	    
	    public void updateApartmentAmenities(String contextPath, Apartment apartment) {
			ArrayList<Amenity> amenities = apartment.getAmenities();
			try {
				File file = new File(path + "/web-2020/WebRest/apartment-amenities.txt");
				BufferedReader in = new BufferedReader(new FileReader(file));
				String line = "", text = "";
				StringTokenizer st;
				while ((line = in.readLine()) != null) {
					line = line.trim();
					if (line.equals("") || line.indexOf('#') == 0)
						continue;
					st = new StringTokenizer(line, ",");
					String apartmentId = st.nextToken().trim();
					if (!apartmentId.equals(apartment.getId())) {
						text += line + "\r\n";
					}
				}
				in.close();
				// Avoids doubling of amenities
				for (Amenity amenity : amenities) {
					text += apartment.getId() + "," + amenity.getId() + "\r\n";
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				PrintWriter out = new PrintWriter(writer);
				out.println(text);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
