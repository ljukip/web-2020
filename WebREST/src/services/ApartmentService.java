package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.message.internal.ReaderWriter;

import beans.Adress;
import beans.Amenity;
import beans.Apartment;
import beans.ApartmentDto;
import beans.Location;
import beans.Reservation;
import beans.Review;
import dao.AmenityDao;
import dao.ApartmentDao;
import dao.LocationDao;
import dao.ReservationDao;
import dao.ReviewDao;
import dao.UserDao;

@Path("/apartment")
public class ApartmentService {
	@Context
	ServletContext ctx;
	String contextPath;
	
	public ApartmentService() {
		super();
	}

	@PostConstruct
	public void init() {
		contextPath = ctx.getRealPath("");
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
			if (ctx.getAttribute("reviewDao") == null) {
				ctx.setAttribute("reviewDao", new ReviewDao(contextPath));
			}
			if (ctx.getAttribute("reviewDao") == null) {
				ctx.setAttribute("reviewDao", new ReviewDao(contextPath));
			}
		}
		
	}
	
	
	@POST
	@Path("/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addApartment(@Context HttpServletRequest request,  @PathParam("role") String role, String type) {
		
		//String username=apartment.getHost();
		System.out.println("usao u save apartment primio:" + type );
		String[] tokens1 = type.split("\\[");
		for (int i=0;i<tokens1.length;i++) {
			tokens1[i].trim();
			System.out.println("tokeni1:"+ tokens1[i]);};
		String amenitiesString=tokens1[1];
		
		String delims = "[\\\":,{}]+";
		String[] tokens = type.split(delims);
		for (int i=0;i<tokens.length;i++) {
			tokens[i].trim();
			System.out.println("tokeni:"+ tokens[i]);};
		Apartment apartment= new Apartment();
		apartment.setType(Apartment.Type.valueOf(tokens[2]));
		apartment.setCapacity(Integer.parseInt(tokens[4]));
		if (!tokens[6].equals("null")) {apartment.setRooms(Integer.parseInt(tokens[6]));}
		else {apartment.setRooms(1);}
		Adress address=new Adress(tokens[14],tokens[16],tokens[18]);
		Location location=new Location(tokens[9],tokens[11],address); //proveri da li lokacija postoji, ako ne, dodaj je
		LocationDao locationDao = (LocationDao) ctx.getAttribute("locationDao");
		location.setId(locationDao.save(location));
		apartment.setLocation(location);
		apartment.setPricePerNight(Integer.parseInt(tokens[20]));
		apartment.setCheckin(tokens[22]);
		apartment.setCheckout(tokens[24]);

		System.out.println("amenity string:" + amenitiesString);
		ArrayList<Amenity> amenities=new ArrayList<Amenity>();
		AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
		String[] names=amenitiesString.split("[\\\":,{}]+");
		for(int i=0; i<names.length;i++) {
			System.out.println("names:"+names[i]);
			if(names[i].equals("id")) {
				int b=i+1;
				System.out.print("za upis:"+names[b]);
				Amenity amenity=amenityDao.findAmenity(Integer.parseInt(names[b]));
				 amenities.add(amenity);
			}
		}
		apartment.setAmenities(amenities);
		apartment.setTo(Long.parseLong(tokens[26]));
		apartment.setFrom(Long.parseLong(tokens[28]));
		apartment.setHost(tokens[30]);
		
		System.out.println("apartment za upis: " + apartment.getLocation()+ apartment.getRooms() + apartment.getHost()+ apartment.getAmenities());
		
		
		if (role.equals("GUEST")) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		Apartment newApartment = apartmentDao.save(apartment);

		return Response.status(Response.Status.CREATED).entity(newApartment).build();
		
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateApartment(@Context HttpServletRequest request, Apartment apartment) {
	
		System.out.println("novi apartment: " + apartment.getLocation()+ apartment.getRooms() + apartment.getHost()+ apartment.getAmenities() +apartment.getImages());
		
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		Apartment newApartment = apartmentDao.update(apartment, ctx.getRealPath(""));

		return Response.status(Response.Status.CREATED).entity(newApartment).build();
		
	}

	@POST
	@Path("/{id}/upload")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadPhotos(@Context HttpServletRequest request, @PathParam("id") String id,
			FormDataMultiPart multiPart) {
		
		System.out.println("*****************************************************************");
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		List<FormDataBodyPart> fields = multiPart.getFields("image");
		for (FormDataBodyPart filePart : fields) {
			ContentDisposition fileDetail = filePart.getContentDisposition();

			// TODO: Check if paths work on windows
			//String path = ctx.getRealPath("") + "images/" + id;
			String path= apartmentDao.getPath() + "/web-2020/WebRest/WebContent/imgs/" + id;
			System.out.println("path:" + path);
			new File(path).mkdirs();
			File file = new File(path, fileDetail.getFileName());

			try (FileOutputStream out = new FileOutputStream(file)) {
				ReaderWriter.writeTo(filePart.getEntityAs(InputStream.class), out);
				apartmentDao.saveImage(ctx.getRealPath(""), "imgs/" + id + "/" + fileDetail.getFileName(), id);
			} catch (IOException e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	@Path("/{role}/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ApartmentDto> getAllApartments(@Context HttpServletRequest request, @PathParam("role") String role, @PathParam("username") String username) {
    	AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
    	ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");

    	Collection<Apartment> apartments = apartmentDao.findAll();
		Collection<ApartmentDto> retApartment = new ArrayList<>();
		
		//for gueast and unregistered user return all active apartments
		if (role== null || role.equals("GUEST")) {
			apartments = apartments.stream()
					.filter(a ->  a.getStatus().equals("active"))
					.collect(Collectors.toList());
		}
		//for host return all his apartments(both active and inactive)
		else if (role.equals("HOST")) {	
			apartments = apartmentDao.findByHostUsername(username);
		}
		
		else if(role.equals("ADMIN")) {apartments=apartments;}

		for (Apartment a : apartments) {
			ArrayList<Amenity> amenitiesOriginal = amenityDao.findAllByApartmentId(ctx.getRealPath(""), a.getId());
			ArrayList<String> amenities = new ArrayList<>();
			for (Amenity amenity : amenitiesOriginal) {
				amenities.add(amenity.getName());
			}
			ApartmentDto dto = new ApartmentDto(a.getId(), a.getType(), a.getRooms(), a.getCapacity(), a.getLocation(),
					a.getTo(), a.getFrom(), a.getHost(), a.getPricePerNight(), a.getStatus(), amenities);
			retApartment.add(dto);
		}
		System.out.println("procitao" + retApartment.toString());

		return retApartment;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatus(@Context HttpServletRequest request,  @PathParam("id") String id) {
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		Collection<Apartment> apartments = apartmentDao.findAll();
		Apartment apartment= new Apartment();
		
		for (Apartment a : apartments) {
			System.out.println("menajmo status za:" + a.getId() + id);
			if(a.getId().equals(id)) {
				apartment=a;
				apartment.setStatus("active");
				System.out.println("menajmo status u:" + apartment.getStatus());
			}
		}
		return Response.status(Response.Status.CREATED).entity(apartmentDao.update(apartment, ctx.getRealPath(""))).build();
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAmenity(@Context HttpServletRequest request,  @PathParam("id") String id) {
		
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		System.out.println("id za brisanje je:"+ id);
		Apartment apartment = apartmentDao.findOne(id);
		apartmentDao.delete(apartment, ctx.getRealPath(""));
		return Response.status(Response.Status.CREATED).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response forEditApartment(@Context HttpServletRequest request,  @PathParam("id") String id) {
		
		ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");
		AmenityDao amenityDao = (AmenityDao) ctx.getAttribute("amenityDao");
		ReservationDao reservationDao = (ReservationDao) ctx.getAttribute("reservationDao");
		System.out.println("id za eidt je:"+ id);
		
		Apartment apartment = apartmentDao.findOne(id);
		Collection<Reservation> reservations=reservationDao.findAllApartmentId(id);
		apartment.setReservations(reservations);
		//ucitati slike i review
		apartment.setImages(apartmentDao.loadImages(ctx.getRealPath(""), id));
		apartment.setAmenities(amenityDao.findAllByApartmentId(ctx.getRealPath(""), id));
		ReviewDao reviewDao = (ReviewDao) ctx.getAttribute("reviewDao");
		Collection <Review> reviews = reviewDao.getApartments(id);
		apartment.setReviews(reviews);
		
		return Response.status(Response.Status.CREATED).entity(apartment).build();
	}
	
	 @GET
		@Path("/search/{role}/{username}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response searchApartments(@Context HttpServletRequest request, @PathParam("role") String role, @PathParam("username") String username,
				@QueryParam("location") String location,
				@QueryParam("from") Long from,
				@QueryParam("to") Long to,
				@QueryParam("roomsMin") Long roomsMin,
				@QueryParam("roomsMax") Long roomsMax,
				@QueryParam("guests") Long guests,
				@QueryParam("priceMin") Long priceMin,
				@QueryParam("priceMax") Long priceMax) {

		 
	    	System.out.println("location: " + location);
	    	System.out.println("from: " + from);
	    	System.out.println("to: " + to);
	    	System.out.println("roomsMin: " + roomsMin);
	    	System.out.println("roomsMax: " + roomsMax);
	    	System.out.println("guests: " + guests);
	    	System.out.println("priceMin: " + priceMin);
	    	System.out.println("priceMax: " + priceMax);

			UserDao userDao = (UserDao) ctx.getAttribute("userDao");
	    	ApartmentDao apartmentDao = (ApartmentDao) ctx.getAttribute("apartmentDao");		
			Collection<Apartment> apartments = apartmentDao.findAll();

			if(role == null) {
				apartments = apartments.stream()
						.filter(a ->  a.getStatus().equals("active"))
						.collect(Collectors.toList());
			}
			else if (role.equals("GUEST")) {
				apartments = apartments.stream()
						.filter(a ->  a.getStatus().equals("active"))
						.collect(Collectors.toList());
			}
			
			else if (role.equals("HOST")) {
				
				apartments = apartmentDao.findByHostUsername(username);
				apartments = apartments.stream()
						.filter(a ->  a.getStatus().equals("active"))
						.collect(Collectors.toList());
			}
			else{
				apartments = apartments;
			}
	 
			if(location != null) {
				apartments = apartments.stream()
					.filter(l -> l.getLocation().getAdress().getCity().toLowerCase().contains(location.toLowerCase()))
					.collect(Collectors.toList());
			}
			if(priceMin != null) {
				apartments = apartments.stream()
					.filter(a -> a.getPricePerNight() >= priceMin)
					.collect(Collectors.toList());
			}
			if(priceMax != null) {
				apartments = apartments.stream()
					.filter(a -> a.getPricePerNight() <= priceMax)
					.collect(Collectors.toList());
			}
			if(guests != null) {
				apartments = apartments.stream()
					.filter(a -> a.getCapacity() >= guests)
					.collect(Collectors.toList());
			}
			if(roomsMin != null) {
				apartments = apartments.stream()
					.filter(a -> a.getRooms() >= roomsMin)
					.collect(Collectors.toList());
			}
			if(roomsMax != null) {
				apartments = apartments.stream()
					.filter(a -> a.getRooms() <= roomsMax)
					.collect(Collectors.toList());
			}
			if (from != null) {
				apartments = apartments.stream()
						.filter(a -> a.getTo() < from)
						.collect(Collectors.toList());
			}
			if (to != null) {
				apartments = apartments.stream()
						.filter(a -> a.getFrom() > to)
						.collect(Collectors.toList());
			}
			if (from != null && to != null) {
				apartments = apartments.stream()
						.filter(apartment -> {
							Collection<Reservation> reservations = apartment.getReservations().stream()
									.filter(r -> (r.getFrom() < from && r.getTo() > from
												|| r.getFrom() > from && r.getTo() < to
												|| r.getFrom() < to && r.getTo() > to
											))
									.collect(Collectors.toList());
							return reservations.isEmpty();
						})
						.collect(Collectors.toList());
			}

			return Response.status(Response.Status.OK).entity(apartments).build();
	    }

	
}
