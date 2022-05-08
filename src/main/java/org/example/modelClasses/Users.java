package org.example.modelClasses;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private long id;

    @Column(name="username", nullable = false)

    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Notebooks> notebooks = new HashSet<Notebooks>();

    public Users() {
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Notebooks> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(Set<Notebooks> notebooks) {
        this.notebooks = notebooks;
    }
}
