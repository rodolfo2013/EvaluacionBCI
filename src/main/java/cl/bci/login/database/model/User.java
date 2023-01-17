package cl.bci.login.database.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    private String uuid;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;
    private String token;
    private Boolean isactive;

    @OneToMany(mappedBy="uuid", cascade = {CascadeType.ALL})
    private Set<Phones> phones;
}
