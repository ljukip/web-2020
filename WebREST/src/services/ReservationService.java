package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Amenity;
import beans.Apartment;
import beans.Reservation;
import beans.User;
import dao.AmenityDao;
import dao.ApartmentDao;
import dao.LocationDao;
import dao.ReservationDao;
import dao.UserDao;

@Path("/reservations")
public class ReservationService {
	@Context
	ServletContext ctx;

	public ReservationService() {
		super();
		// TODO Auto-generated constructor stub
	}
	//assures that the method is executed after dependency injection is done to perform any initialization
		@PostConstruct
		public void init() {
			
			String contextPath = ctx.getRealPath("");
			if (ctx.getAttribute("reservationDao") == null) {
				ctx.setAttribute("reservationDao", new ReservationDao(contextPath));
				if (ctx.getAttribute("userDao") == null) {
					ctx.setAttribute("userDao", new UserDao(contextPath));
				}
				if (ctx.getAttribute("apartmentDao") == null) {
					if (ctx.getAttribute("amenityDao") == null) {
						ctx.setAttribute("amenityDao", new AmenityDao(contextPath));
					}
					if (ctx.getAttribute("locationDao") == null) {
						ctx.setAttribute("locationDao", new LocationDao(contextPath));
					}
					if (ctx.getAttribute("reservationDao") == null) {
						ctx.setAttribute("reservationDao", new ReservationDao(contextPath));
					}
					ctx.setAttribute("apartmentDao", new ApartmentDao(contextPath,(LocationDao) ctx.getAttribute("locationDao"), (AmenityDao) ctx.getAttribute("amenityDao")));
					System.out.println(contextPath);
				}
				System.out.println(contextPath);
			}
		}
		
		@GET
		@Path("/{role}/{username}")
		@Produces(MediaType.APPLICATION_JSON)
		public Collection<Reservation> getReservations(@Context HttpServletRequest request, @PathParam("role") String role, @PathParam("username") String username) {
			
			ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
			UserDao userDao = (UserDao) ctx.getAttribute("userDao");
			ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
			System.out.println("get username:"+ username);
			
			List<Reservation> reservations = reservationDao.getAll();
			if (role.equals("GUEST")) {
				reservations = reservations.stream()
						.filter(r ->  r.getGuestId().equals(username))
						.collect(Collectors.toList());
			}
			else if (role.equals("HOST")) {
				List <Reservation> reservationsHost = new ArrayList<Reservation>();
				for (int i=0; i< reservations.size();i++){
					if(apartmentDao.findOne(reservations.get(i).getApartmentId()).getHost().equals(username)) {
						reservationsHost.add(reservations.get(i));
					}
				}
				System.out.print("hosts-reservations:"+ reservationsHost);
				return reservationsHost;
			}
			//else return all
			
			return reservations;
		}
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response add(@Context HttpServletRequest request,  Reservation reservation) {
			System.out.println("usao u reservation request");
			ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
			reservation.setStatus(Reservation.Status.valueOf("created"));
			reservationDao.save(reservation);
			return Response.status(Response.Status.OK).entity(reservation).build();
		

		}
		
		@PUT
		@Path("/changeStatus/{id}/{status}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateStatus(@Context HttpServletRequest request,  @PathParam("id") String id,@PathParam("status") String status) {
			ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
			Collection <Reservation> reservations = reservationDao.getAll();
			Reservation reservation= new Reservation();
			
			for (Reservation a : reservations) {
				System.out.println("menajmo status za:" +  a.getId() + id);
				if(a.getId().equals(id)) {

					System.out.println("menajmo status u:" + status +':'+Reservation.Status.valueOf(status));
					reservation=a;
					reservation.setStatus(Reservation.Status.valueOf(status));
				}
			}
			return Response.status(Response.Status.CREATED).entity(reservationDao.update(ctx.getRealPath(""), reservation)).build();
		}
}