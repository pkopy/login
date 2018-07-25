package pl.pkopy.login.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "register")
public class RegisterEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "user_name")
    private String userName;
    private String email;
    private String location;
    private String password;
    @Column(name = "register_key")
    private String registerKey;
    private String ip;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;


}
