// Klasa Role
package com.example.carrental.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
    public static Role createAdminRole() {
        return new Role("Admin");
    }
    public static Role createClientRole() { return new Role("Client");}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
