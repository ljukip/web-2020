package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import dao.ReviewDao;

@Path("/reviews")
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
}
