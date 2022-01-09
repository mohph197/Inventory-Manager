package classes;

public class Product {
    private String name;
    private String category;
    private String ref;
    private String description;
    private String[] specs;
    private float price;
    
    
	public Product(String name, String category, String ref, String description, String[] specs, float price) {
		super();
		this.name = name;
		this.category = category;
		this.ref = ref;
		this.description = description;
		this.specs = specs;
		this.price = price;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getSpecs() {
		return specs;
	}
	public void setSpecs(String[] specs) {
		this.specs = specs;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
    
    
    
}
