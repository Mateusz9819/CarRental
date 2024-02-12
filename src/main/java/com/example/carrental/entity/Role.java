// Klasa Role
package com.example.carrental.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
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
        return new Role("ADMIN");
    }
    public static Role createClientRole() { return new Role("CLIENT");}

}
