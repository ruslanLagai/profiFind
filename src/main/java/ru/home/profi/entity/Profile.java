package ru.home.profi.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "jpa_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    @Size(min = 6)
    private String password;


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
}
