package services;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.User;
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
	}
	
	//assures that the method is executed after dependency injection is done to perform any initialization
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDao") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDao", new UserDao(contextPath));
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
			userDao.save(user, ctx.getRealPath(""));
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
	

}
