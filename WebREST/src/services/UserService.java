package services;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.catalina.Role;

import beans.Apartment;
import beans.Reservation;
import beans.User;
import dao.AmenityDao;
import dao.ApartmentDao;
import dao.LocationDao;
import dao.ReservationDao;
import dao.UserDao;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//path annotation identifies the URI path template to which the resource responds
@Path("")
public class UserService {

	//object instances related to the context of HTTP requests
	@Context
	ServletContext ctx;

	public UserService() {
		super();
	}
	
	//assures that the method is executed after dependency injection is done to perform any initialization
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDao") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDao", new UserDao(contextPath));
			
			if (ctx.getAttribute("reservationDao") == null) {
				ctx.setAttribute("reservationDao", new ReservationDao(contextPath));
			}
			
			if (ctx.getAttribute("apartmentDao") == null) {
				if (ctx.getAttribute("amenityDao") == null) {
					ctx.setAttribute("amenityDao", new AmenityDao(contextPath));
				}
				if (ctx.getAttribute("locationDao") == null) {
					ctx.setAttribute("locationDao", new LocationDao(contextPath));
				}
				ctx.setAttribute("apartmentDao", new ApartmentDao(contextPath,(LocationDao) ctx.getAttribute("locationDao"), (AmenityDao) ctx.getAttribute("amenityDao")));
			}
			System.out.println(contextPath);
		}
	}
	
	//The JWT signature algorithm we will be using to sign the token
	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	//updating server that client has requested to login
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		UserDao userDao = (UserDao) ctx.getAttribute("userDao");
		//System.out.println("radi sifra " + "" + user.getPassword());
		User userForLogin = userDao.find(user.getUsername(), user.getPassword());
		//System.out.println(userForLogin.getUsername() + userForLogin.getPassword());
		//if user is found in dao then create and set a json web token upon it
		//build responce status ok
		System.out.println("userForLogin: " + userForLogin);
		if (userForLogin != null) {
			String jwtSession = createJwtSession(userForLogin.getUsername());
			userForLogin.setJwt(jwtSession); //set token to the object in request
			System.out.println("ulogovani korisnik je:"+userForLogin.getRole());
			System.out.println("status ok");
			return Response.status(Response.Status.OK).entity(userForLogin).build();
		}

		//if the user with the given username is not found, build responce bad request
		System.out.println("bad request");
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	public String createJwtSession(String subject) {
		String jwtSession = Jwts.builder()
				//we cant have two identical usernames (subject ides the jwt)
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 1000*9000L))
				.signWith(key)
				.compact();
		return jwtSession;
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(User user) {
		//check if the username is unique
		//if it is, create user, if not bad request
		String usernameForReg=user.getUsername();
		UserDao userDao = (UserDao) ctx.getAttribute("userDao");
		System.out.println("username posted:" + "" + usernameForReg);
		if (userDao==null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		if (!userDao.find(usernameForReg)) {
			userDao.save(user);
			//asign new jwtSession to user
			String jwtSession = createJwtSession(user.getUsername());
			user.setJwt(jwtSession);
			System.out.println(jwtSession);
			return Response.status(Response.Status.OK).entity(user).build();
		}
		else {
			System.out.println("username vec postoji");
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("profileUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(@Context HttpServletRequest request, @PathParam("username") String username) {
		
			UserDao userDao = (UserDao) ctx.getAttribute("userDao");
			System.out.println("username iz requesta je:" + username);
			System.out.println("useDao iz requesta je:" + userDao);
			User user = userDao.findUser(username);
			if (user != null) {
				return Response.status(Response.Status.OK).entity(user).build();
			}
			else
				return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@PUT
	@Path("profileUser/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editProfile(@Context HttpServletRequest request, @PathParam("username") String username,
			User user) {
			System.out.println("in put method-----------------------------------------------------------------");
			UserDao userDao = (UserDao) ctx.getAttribute("userDao");
			User updatedUser = userDao.update(user, ctx.getRealPath(""));
			System.out.println("updating user:"+updatedUser.getUsername());
			if (updatedUser != null) {
				return Response.status(Response.Status.CREATED).entity(updatedUser).build();
			}
			return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@GET
	@Path("users/all/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context HttpServletRequest request,  @PathParam("role") String role) {
		UserDao userDao = (UserDao) ctx.getAttribute("userDao");
		if(role.equals("ADMIN")) {
			Collection<User> users =userDao.findAll();
			return Response.status(Response.Status.OK).entity(users).build();
		}
	return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@GET
	@Path("users/customers/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers(@Context HttpServletRequest request,  @PathParam("username") String username) {
		
		Collection<User> users=GetHostsUsers(username);
		
		return Response.status(Response.Status.OK).entity(users).build();
	}
	
	public Collection<User> GetHostsUsers(String username) {
		UserDao userDao = (UserDao) ctx.getAttribute("userDao");
		ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
			
		Collection<User> users = new ArrayList<User>();
		Collection<Apartment> hostsApartments = apartmentDao.findByHostUsername(username);
		
		for (Apartment a : hostsApartments) {
			System.out.println("hosts apartments:" + hostsApartments);
			Collection<Reservation> reservations = reservationDao.findAllApartmentId(a.getId());
		
			for (Reservation r : reservations) {
				System.out.print("hosts id:" + r.getGuestId());
				User user = userDao.findUser(r.getGuestId());
				if (!users.contains(user))
					users.add(user);
			}
		}
		return users;
	}
	
	@GET
	@Path("user/search/{role}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUsers(@Context HttpServletRequest request, @PathParam("role") String loggedInRole,@PathParam("username") String hostUsername, @QueryParam("name") String name, @QueryParam("surname") String surname, @QueryParam("username") String username,
			@QueryParam("gender") String gender, @QueryParam("role") String role) {

		UserDao userDao = (UserDao) ctx.getAttribute("userDao");
		if(loggedInRole.equals("ADMIN")) {
			Collection<User> usersToFilter =userDao.findAll();
			Collection<User> filteredUsers= userDao.filter(usersToFilter, name, surname, username, gender,role);
			return Response.status(Response.Status.OK).entity(filteredUsers).build();
		}
		else if(loggedInRole.equals("HOST")) {
			Collection<User> usersToFilter =GetHostsUsers(hostUsername);
			Collection<User> filteredUsers= userDao.filter(usersToFilter, name, surname, username, gender,role);
			return Response.status(Response.Status.OK).entity(filteredUsers).build();
		}
		
		return Response.status(Response.Status.FORBIDDEN).build();
	}

}
