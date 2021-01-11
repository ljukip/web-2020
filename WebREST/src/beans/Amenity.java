package beans;

public class Amenity {

	public enum Type{
		basic, family_features, dining, recreation, pet, parking,
	}
	
	private int id;
	private String name;
	private Type type;
	
	private boolean deleted;
	
	public Amenity() {
		super();
	}
	
	public Amenity(String name, Type type, int id, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.deleted=deleted;
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
	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
}
