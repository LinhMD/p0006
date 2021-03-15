/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p0006.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Vector;

/**
 *
 * @author USER
 */
public class Category {
    public static Vector<String> DATA_HEADER = new Vector<>();
    static {
        DATA_HEADER.add("ID");
        DATA_HEADER.add("Name");
        DATA_HEADER.add("Description");
    }

    @NotBlank(message = "Category id can not be empty")
    @Length(max = 10, message = "Category id length must  lower than 10")
    private String id = "";
    @NotBlank(message = "Category name can not be empty")
    @Length(max = 50, message = "Category name length must  lower than 50")
    private String name = "";
    @NotBlank(message = "Category description can not be empty")
    @Length(max = 200, message = "Category description length must  lower than 50")
    private String description = "";

    public Category() {
    }

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(String id){
        this.id = id;
    }


    public Category(Vector<String> strings) {
        this(strings.get(0), strings.get(1), strings.get(2));
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean update(Category category){
        this.setName(category.getName());
        this.setDescription(category.getDescription());
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        return Objects.equals(this.id, other.id);
    }

    public Vector<Object> toVector(){
        Vector<Object> vector = new Vector<>();
        vector.add(this.id);
        vector.add(this);
        vector.add(this.description);
        return vector;
    }


    
}
