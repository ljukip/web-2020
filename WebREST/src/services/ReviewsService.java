package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Reservation;
import beans.Review;
import dao.ReservationDao;
import dao.ReviewDao;

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
				System.out.println(contextPath);
			}
		}
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response add(@Context HttpServletRequest request,  Review review) {
			System.out.println("usao u ReviewDao request");
			ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
			review.setPublished(false);
			reviewDao.save(review, ctx.getRealPath(""));
			return Response.status(Response.Status.OK).entity(review).build();
		

		}
}
