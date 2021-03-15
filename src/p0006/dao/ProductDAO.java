package p0006.dao;

import p0006.dto.Product;

import java.util.Vector;
import java.util.stream.Collectors;

import static p0006.gui.CategoryPanel.CATEGORIES;

public class ProductDAO {
	public Vector<Product> getAllProduct() throws Exception {
		return SQLQuery.executeQuery("select p.product_id, p.product_name, p.category_id, p.unit, p.quantity, p.price\n" + "from tbl_products p")
				.stream()
				.map(Product::new)
				.peek(product -> product.setCategory(CATEGORIES.get(CATEGORIES.indexOf(product.getCategory()))))
				.collect(Collectors.toCollection(Vector::new));
	}

	public boolean deleteProduct(String id) throws Exception {
		String sql = "delete tbl_products\n" +
				"where product_id = ?";
		return SQLQuery.executeNonQuery(sql, id);
	}

	public boolean updateProduct(Product product) throws Exception {
		String sql = "update tbl_products\n" +
				"set product_name = ?,  category_id = ?, unit = ?, quantity = ?, price = ?\n" +
				"where product_id = ?";
		return SQLQuery.executeNonQuery(sql,
				product.getName(),
				product.getCategory().getId(),
				product.getUnit(),
				product.getQuantity(),
				product.getPrice(),
				product.getId());
	}

	public boolean insertProduct(Product product) throws Exception {
		String sql = " insert into tbl_products(product_id, product_name, category_id, unit, quantity, price)\n" +
				"values (?, ?, ?, ?, ?, ?);";
		return SQLQuery.executeNonQuery(sql,
				product.getId(),
				product.getName(),
				product.getCategory().getId(),
				product.getUnit(),
				product.getQuantity(),
				product.getPrice());
	}

	public static void main(String[] args) {
		try {
			new ProductDAO().getAllProduct().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
