package cl.bci.login.database.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    private String uuid;
    private String name;
    private String email;
    private String password;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate last_login;
    private String token;
    private Boolean isactive;
}
