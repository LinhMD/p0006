package p0006.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Product {

	public static Vector<String> DATA_HEADER = new Vector<>();
	static {
		DATA_HEADER.add("ID");
		DATA_HEADER.add("Name");
		DATA_HEADER.add("Category");
		DATA_HEADER.add("Unit");
		DATA_HEADER.add("Quantity");
		DATA_HEADER.add("Price");
	}
	@NotBlank(message = "Product id can not be empty")
	@Length(max = 10, message = "Product id length must  lower than 10")
	private String id = "";

	@NotBlank(message = "Product name can not be empty")
	@Length(max = 50, message = "Product name length must  lower than 50")
	private String name = "";

	@NotNull(message = "Category not found")
	private Category category = null;

	@NotBlank(message = "Product unit can not be empty")
	@Length(max = 50, message = "Product unit length must lower than 50")
	private String unit = "";

	@Min(value = 1, message = "Quantity must bigger or equal to 1")
	private int quantity = 0;

	@Min(value = 1, message = "Price must bigger or equal to 1")
	private double price = 0;

	public Product(){

	}

	public Product(String id){
		this.id = id;
	}

	public Product(String id, String name, Category category, String unit, int quantity, double price) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.unit = unit;
		this.quantity = quantity;
		this.price = price;
	}

	public Product(List<String> strings){
		this.id = strings.get(0);
		this.name = strings.get(1);
		this.category = new Category(strings.get(2));
		this.unit = strings.get(3);
		this.quantity = Integer.parseInt(strings.get(4));
		this.price = Double.parseDouble(strings.get(5));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Product)) return false;

		Product product = (Product) o;

		return Objects.equals(this.id, product.getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return id;
	}

	public Vector<Object> toVector(){
		Vector<Object> vector = new Vector<>();
		vector.add(this);
		vector.add(this.name);
		vector.add(this.category.getId());
		vector.add(this.unit);
		vector.add(this.quantity);
		vector.add(this.price);
		return vector;
	}
}
