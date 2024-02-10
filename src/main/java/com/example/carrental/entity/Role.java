// Klasa Role
package com.example.carrental.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Getter
@Entity

@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
    public static Role createAdminRole() {
        return new Role("ADMIN");
    }
    public static Role createClientRole() { return new Role("CLIENT");}

    public void setName(String name) {
        this.name = name;
    }

}
