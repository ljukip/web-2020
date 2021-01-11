package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import beans.User;
import dao.AmenityDao;
import dao.UserDao;

@Path("/amenities")
public class AmenityService {
	@Context
	ServletContext ctx;

	public AmenityService() {
		super();
		// TODO Auto-generated constructor stub
	}
	//assures that the method is executed after dependency injection is done to perform any initialization
		@PostConstruct
		public void init() {
			if (ctx.getAttribute("amenityDao") == null) {
				String contextPath = ctx.getRealPath("");
				ctx.setAttribute("amenityDao", new AmenityDao(contextPath));
				System.out.println(contextPath);
			}
		}
		
		@POST
		@Path("/{role}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response add(@Context HttpServletRequest request, @PathParam("role") String role, Amenity amenity) {
			System.out.println("usao u amenities request");
			AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");

			if (role.equals("ADMIN")) {
				amenityDao.save(amenity, ctx.getRealPath(""));
				return Response.status(Response.Status.OK).entity(amenity).build();
			}
			return Response.status(Response.Status.FORBIDDEN).build();

		}
		
		@GET
		@Path("/all/{role}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getAll(@Context HttpServletRequest request,  @PathParam("role") String role) {
			AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
			if(role.equals("ADMIN")) {
				Collection<Amenity> amenities =amenityDao.findAll();
				return Response.status(Response.Status.OK).entity(amenities).build();
			}
		return Response.status(Response.Status.FORBIDDEN).build();
		}
		
		@DELETE
		@Path("/delete/{role}/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteAmenity(@Context HttpServletRequest request,  @PathParam("role") String role,  @PathParam("id") String id) {
			
			if(!role.equals("ADMIN")) {return Response.status(Response.Status.FORBIDDEN).build();}
			AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
			System.out.println("id za brisanje je:"+ id);
			int findId=Integer.parseInt(id);
			Amenity amenity = amenityDao.findAmenity(findId);
			amenity.setDeleted(true);
			amenityDao.delete(amenity, ctx.getRealPath(""));
			return Response.status(Response.Status.CREATED).build();
		}
		
		@PUT
		@Path("/edit/{role}/{id}/{name}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response editAmenity(@Context HttpServletRequest request,  @PathParam("role") String role,  @PathParam("id") String id,  @PathParam("name") String name) {
			
			if(!role.equals("ADMIN")) {return Response.status(Response.Status.FORBIDDEN).build();}
			AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
			System.out.println("id za edit je:"+ id);
			int findId=Integer.parseInt(id);
			Amenity amenity = amenityDao.findAmenity(findId);
			amenity.setName(name);
			amenityDao.update(amenity, ctx.getRealPath(""));
			return Response.status(Response.Status.CREATED).build();
		}
}
