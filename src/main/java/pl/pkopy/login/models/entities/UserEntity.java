package pl.pkopy.login.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String email;
    private String location;
    private String password;



}
