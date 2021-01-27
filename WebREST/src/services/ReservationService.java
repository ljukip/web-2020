package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Amenity;
import beans.Reservation;
import beans.User;
import dao.AmenityDao;
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
			if (ctx.getAttribute("reservationDao") == null) {
				String contextPath = ctx.getRealPath("");
				ctx.setAttribute("reservationDao", new ReservationDao(contextPath));
				if (ctx.getAttribute("userDao") == null) {
					ctx.setAttribute("userDao", new UserDao(contextPath));
				}
				System.out.println(contextPath);
			}
		}
		
		@GET
		@Path("/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response forEditApartment(@Context HttpServletRequest request, @PathParam("id") String id) {
			
			ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
			System.out.println("get id:"+ id);
			Reservation reservation = reservationDao.findApartmentId(id);
			
			return Response.status(Response.Status.CREATED).entity(reservation).build();
		}
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response add(@Context HttpServletRequest request,  Reservation reservation) {
			System.out.println("usao u reservation request");
			ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
			reservation.setStatus(Reservation.Status.valueOf("created"));
			reservationDao.save(reservation, ctx.getRealPath(""));
			return Response.status(Response.Status.OK).entity(reservation).build();
		

		}
}