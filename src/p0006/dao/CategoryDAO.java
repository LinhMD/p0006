/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p0006.dao;

import java.util.Vector;
import java.util.stream.Collectors;

import p0006.dto.Category;

/**
 *
 * @author USER
 */
public class CategoryDAO {
    public boolean insertCategory(Category category) throws Exception{
        String sql = "insert into tbl_categories(category_id, category_name, description)\n" +
                        "values (?, ?, ?)";
        return SQLQuery.executeNonQuery(sql, category.getId(), category.getName(), category.getDescription());
    }
    
    public Vector<Category> getAllCategory() throws Exception{
        String sql = "select c.category_id, c.category_name, c.description\n" +
                    "from tbl_categories c";
        return SQLQuery.executeQuery(sql)
                        .stream()
                        .map(Category::new)
                        .collect(Collectors.toCollection(Vector::new));
    }

    public boolean deleteCategory(String id) throws Exception {
        if(id == null) return false;
        String sql  = "delete tbl_categories\n" +
                    "where category_id = ?";
        return SQLQuery.executeNonQuery(sql, id);
    }

    public boolean updateCategory(Category category) throws Exception {
        if(category == null) return false;
        String sql = "update tbl_categories \n" +
                "set category_name = ?, description = ?\n" +
                "where category_id = ?";
        return SQLQuery.executeNonQuery(sql, category.getName(), category.getDescription(), category.getId());
    }

    public static void main(String[] args) {
        try {
            System.out.println(new CategoryDAO().getAllCategory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
