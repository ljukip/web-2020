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

import beans.User;

public class UserDao {
	
	private List<User> users = new ArrayList<>();

	Path currentDir = Paths.get(".");
	String path=currentDir.toAbsolutePath().toString();
	
	public UserDao(String contextPath) {
		read();
	}
	
	    public List<User> getAll() {
	        return users;
	    }
	    
	    public void save(User user) {
	    	
	    	String s = user.getName() + "," +
	    			user.getSurname() + "," +
	    			user.getUsername() + "," +
	    			user.getPassword() + "," +
	    			user.getRole() + "," +
	    			user.getGender();
	    	
			BufferedWriter writer = null;
			try {
				File file = new File(path + "/web-2020/WebRest/users.txt");
				System.out.println("put:"+file.getAbsolutePath());
				writer = new BufferedWriter(new FileWriter(file, true));
				PrintWriter out = new PrintWriter(writer); //txt
				out.println(s);
				System.out.println("upisuje: " + s);
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
	    	
	        users.add(user);
	    }
	    
	    public void read () {
			BufferedReader in = null;
			try {
				File file = new File(path + "/web-2020/WebRest/users.txt");
				in = new BufferedReader(new FileReader(file));
				String s;
				StringTokenizer st;
				while ((s = in.readLine()) != null) {
					s = s.trim();
					if (s.equals(""))
						continue;
					st = new StringTokenizer(s, ",");
					System.out.println("krece da cita:");
					while (st.hasMoreTokens()) {
						String name = st.nextToken().trim();
						String surname = st.nextToken().trim();
						String username = st.nextToken().trim();
						String password = st.nextToken().trim();
						String role = st.nextToken().trim();
						String gender = st.nextToken().trim();
						users.add(new User(username,password, name, surname, gender,  User.Role.valueOf(role)));
						System.out.println(username);
						System.out.println(password);
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
	    
	    public User update(User user, String contextPath) {

			boolean match=false;
	    	try {
	    		File file = new File(path + "/web-2020/WebRest/users.txt");
	    		File tempFile = new File(contextPath + "usersTemp.txt");
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
					String username= tokens[2];
					System.out.println(username +"+++++++++++++++++++++++++" +user.getUsername()); //ne moze da se menja username!!!
					if (user.getUsername().equals(username)) {
						//update-ujemo
						newS += user.getName() + "," +
				    			user.getSurname() + "," +
				    			user.getUsername() + "," +
				    			user.getPassword() + "," +
				    			user.getRole() + "," +
				    			user.getGender() + "\r\n";
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
		    	User removeMe = null;
		    	for (User u : users) {
		    	    if (user.getUsername().equals(u.getUsername())) {
		    	    	removeMe=u;
		    	    }
		    	}
	    	    users.remove(removeMe);
		    	users.add(user);
		    	return user;
	    	}
	    	else
	    		return null;
	        
	    }
	    
	    
	    public void delete(User user) {
	        users.remove(user);
	    }
	    
	    public User find(String username, String password) {
	    	for (User u : users) {
	    		System.out.println("poredi:"+username + "i" + u.getUsername());
	    		System.out.println("poredi:"+password + "i" + u.getPassword());
	    	    if (username.equals(u.getUsername()) & password.equals(u.getPassword())) {
	    	    	System.out.println("nasao");
	    	    	return u;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return null;
	    }
	    public Boolean find(String username) {
	    	for (User u : users) {
	    		System.out.println("poredi:"+username + "i" + u.getUsername());
	    	    if (username.equals(u.getUsername())) {
	    	    	System.out.println("nasao");
	    	    	return true;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return false;
	    }

	    public User findUser(String username) {
	    	for (User u : users) {
	    		System.out.println("poredi:"+username + "i" + u.getUsername());
	    	    if (username.equals(u.getUsername())) {
	    	    	System.out.println("nasao");
	    	    	return u;
	    	    }
	    	}
	    	System.out.println("nije nasao");
			return null;
	    }
	    public Collection<User> findAll() {
			return users;
		}
	    
	    public Collection<User> filter(Collection<User> UsersToFilter, String name,  String surname, String username, String gender, String role) {

			
			if (name != null && name != "") {
				Collection<User> temp = new ArrayList<User>();
				for (User u : UsersToFilter) {
					if (u.getName().toLowerCase().startsWith((name).toLowerCase())) {
						temp.add(u);
					}
				}
				UsersToFilter=temp;
			}
			if (surname != null && surname != "") {
				Collection<User> temp = new ArrayList<User>();
				for (User u : UsersToFilter) {
					if (u.getSurname().toLowerCase().startsWith((surname).toLowerCase())) {
						temp.add(u);
					}
				}
				UsersToFilter=temp;
			}
	    	if (username != null && username != "") {
	    		Collection<User> temp = new ArrayList<User>();
				for (User u : UsersToFilter) {
					if (u.getUsername().toLowerCase().startsWith((username).toLowerCase())) {
						temp.add(u);
					}
				}
				UsersToFilter=temp;
			}

			if (gender != null && username != "") {
				Collection<User> temp = new ArrayList<User>();
				for (User u : UsersToFilter) {
					if (u.getGender().toLowerCase().equals((gender).toLowerCase())) {
						temp.add(u);
					}
				}
				UsersToFilter=temp;
			}

			if (role != null && username != "") {
				Collection<User> temp = new ArrayList<User>();
				for (User u : UsersToFilter) {
					if (u.getRole().toString().toLowerCase().equals((role).toLowerCase())) {
						temp.add(u);
					}
				}
				UsersToFilter=temp;
			}

			return UsersToFilter;
		}


}
