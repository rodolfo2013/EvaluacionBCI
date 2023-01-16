package cl.bci.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserExtendDTO extends UserDTO{

    private String uuid;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate last_login;
    private String token;
    private Boolean isactive;

    @JsonIgnore
    private String error;
}
