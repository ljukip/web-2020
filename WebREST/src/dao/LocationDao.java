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

import beans.Location;


public class LocationDao {
	private List<Location> locations = new ArrayList<>();

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public LocationDao(String contextPath) {
		read(contextPath);
	}
	
	 public List<Location> getAll() {
	        return locations;
	    }
	 public Collection<Location> findAll() {
			return locations;
		}
	 public Location findOne(String id) {
		 Location location = null;
		 System.out.println("in findone:");
			for(int i=0; i<locations.size();i++ ) {
				System.out.println("poredi id"+ id + "i" +locations.get(i).getId());
				if(locations.get(i).getId().equals(id))
					location= locations.get(i);
			}
			return location;
		}
	 
	 public void read (String contextPath) {
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/locations.txt");
				in = new BufferedReader(new FileReader(file));
				String s;
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					s = s.trim();
					if (s.equals(""))
						continue;
					st = new StringTokenizer(s, ",");
					System.out.println("krece da cita locations:");
					while (st.hasMoreTokens()) {
						String longitude = st.nextToken().trim();
						String latitude = st.nextToken().trim();
						String id= st.nextToken().trim();
						String street = st.nextToken().trim();
						String city = st.nextToken().trim();
						String zip = st.nextToken().trim();
						
						
						locations.add(new Location(longitude,latitude,street,city,zip,id));
						
						System.out.println(street);
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
	 public String save(Location location, String contextPath) {
	    	//set id byincrementation {check all ids, take the max and asign next}
	    	int maxId=-1; //first will be 0 (maxId+1)
	    	for (int i=0; i<locations.size();i++) {
	    		int amenityId=Integer.parseInt(locations.get(i).getId());
	    		if(maxId<amenityId) {
	    			maxId=amenityId;
	    		}
	    	}
	    	maxId++;
	    	location.setId(Integer.toString(maxId));
	    	
	    	String s = location.getLongitude() + "," +
	    			location.getLatitude() + "," +
	    			location.getId()+ "," +
	    			location.getAdress().getStreetNum() + "," +
	    			location.getAdress().getCity() + "," +
	    			location.getAdress().getZip();
	    	
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/locations.txt");
				System.out.println("put:"+file.getAbsolutePath());
				writer = new BufferedWriter(new FileWriter(file, true));
				PrintWriter out = new PrintWriter(writer); //txt
				out.println(s);
				System.out.println("upisuje u locations: " + s);
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
	    	
	        locations.add(location);
	        return location.getId();
	    }
	 
	 public Location update(Location location, String contextPath) {
		 boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/locations.txt");
	    		File tempFile = new File(contextPath + "locationsTemp.txt");
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
					String id= tokens[2];
					System.out.println(id +"+++++++++++++++++++++++++" +location.getId()); 
					if (id.equals(location.getId())) {
						//update-ujemo
						newS += location.getLongitude() + "," +
								location.getLongitude() + "," +
								location.getId() + "," +
								location.getAdress().getStreetNum()+ "," +
								location.getAdress().getCity()+ "," +
								location.getAdress().getZip()+ "\r\n";
				    	
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
		    	Location removeMe = null;
		    	for (Location l : locations) {
		    	    if (location.getId().equals(l.getId())) {
		    	    	removeMe=l;
		    	    }
		    	}
	    	    locations.remove(removeMe);
		    	locations.add(location);
		    	return location;
	    	}
	    	else
	    		return null;
	        
	    }
	 

}
