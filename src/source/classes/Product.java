package source.classes;

import source.services.FileHandler;

public class Product {
    private String name;
    private String category;
    private String ref;
    private String description;
    private String specs;
    private float price;
    
    
	public Product(String name, String category, String description, String specs, float price) {
		this.name = name;
		this.category = category;
		this.ref = FileHandler.GenerateUID(1);
		this.description = description;
		this.specs = specs;
		this.price = price;
	}
	
	public String StringIt(){
		String returned = this.ref+"|"+this.category+"|"+this.name+"|"+this.description+"|"+this.specs+"|"+this.price;
		return returned;
	}

	public static Product ObjectIt(String prod){
		String[] ProductInfo = prod.split("|");
		Product temp = new Product(ProductInfo[2],ProductInfo[1], ProductInfo[3], ProductInfo[4], Float.parseFloat(ProductInfo[5]));
		temp.setRef(prod.split("|")[0]);
		return temp;
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
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
    
    
    
}
