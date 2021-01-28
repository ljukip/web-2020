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

import beans.Reservation;
import beans.Review;
import dao.AmenityDao;
import dao.ApartmentDao;
import dao.LocationDao;
import dao.ReservationDao;
import dao.ReviewDao;
import dao.UserDao;

@Path("/review")
public class ReviewsService {
	@Context
	ServletContext ctx;

	public ReviewsService() {
		super();
		// TODO Auto-generated constructor stub
	}
	//assures that the method is executed after dependency injection is done to perform any initialization
		@PostConstruct
		public void init() {
			if (ctx.getAttribute("reviewDao") == null) {
				String contextPath = ctx.getRealPath("");
				ctx.setAttribute("reviewDao", new ReviewDao(contextPath));
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
				System.out.println(contextPath);
				}
			}
		}
		
		@PUT
		@Path("/publish/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response Publish(@Context HttpServletRequest request,  @PathParam("id") String id) {
			ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
			Collection <Review> reviews = reviewDao.getAll();
			Review review= new Review();
			
			
			for (Review a : reviews) {
				System.out.println("menajmo status za:" +  a.getId() + id);
				if(a.getId().equals(id)) {
					review=a;
					review.setPublished(true);
				}
			}
			return Response.status(Response.Status.CREATED).entity(reviewDao.update(ctx.getRealPath(""), review)).build();
		}
		
		@PUT
		@Path("/unpublish/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response Unpublish(@Context HttpServletRequest request,  @PathParam("id") String id) {
			ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
			Collection <Review> reviews = reviewDao.getAll();
			Review review= new Review();
			
			
			for (Review a : reviews) {
				System.out.println("menajmo status za:" +  a.getId() + id);
				if(a.getId().equals(id)) {
					review=a;
					review.setPublished(false);
				}
			}
			return Response.status(Response.Status.CREATED).entity(reviewDao.update(ctx.getRealPath(""), review)).build();
		}
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response add(@Context HttpServletRequest request,  Review review) {
			System.out.println("usao u ReviewDao request");
			ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
			review.setPublished(false);
			reviewDao.save(review);
			return Response.status(Response.Status.OK).entity(review).build();
		

		}
		
		@GET
		@Path("/{role}/{username}")
		@Produces(MediaType.APPLICATION_JSON)
		public Collection<Review> getReservations(@Context HttpServletRequest request, @PathParam("role") String role, @PathParam("username") String username) {
			
			ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
			UserDao userDao = (UserDao) ctx.getAttribute("userDao");
			ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
			System.out.println("get username:"+ username);
			
			List<Review> reviews = reviewDao.getAll();
			
			if (role.equals("HOST")) {
				List <Review> reviewsHost = new ArrayList<Review>();
				for (int i=0; i< reviews.size();i++){
					if(apartmentDao.findOne(reviews.get(i).getApartmentId()).getHost().equals(username)) {
						reviewsHost.add(reviews.get(i));
					}
				}
				System.out.print("hosts-reservations:"+ reviewsHost);
				return reviewsHost;
			}
			//else return all
			
			return reviews;
		}
}
