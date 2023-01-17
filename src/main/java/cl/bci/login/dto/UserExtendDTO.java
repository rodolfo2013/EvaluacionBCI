package cl.bci.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserExtendDTO extends UserDTO{

    private String uuid;
    private String created;
    private String modified;
    private String last_login;
    private String token;
    private Boolean isactive;

}
