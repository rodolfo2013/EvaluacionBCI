package cl.bci.login.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {


    private String name;
    private String email;
    private String password;
    private List<PhonesDTO> phones;
}
