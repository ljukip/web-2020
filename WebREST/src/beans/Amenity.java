package beans;

public class Amenity {

	private enum Type{
		basic, family_features, dining, recreation, pet, parking,
	}
	
	private int id;
	private String name;
	private Type type;
	
	
	public Amenity(int id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	
	
	
}
