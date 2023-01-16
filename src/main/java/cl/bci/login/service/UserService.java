package cl.bci.login.service;

import cl.bci.login.database.model.User;
import cl.bci.login.database.repository.UserRepository;
import cl.bci.login.dto.UserDTO;
import cl.bci.login.dto.UserExtendDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private String exprecionCorreo = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";
    private String expresionClave = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    private static final String SECRET_KEY = "mysecretkey";
    private static final long EXPIRATION_TIME = 864_000_000;
    public UserExtendDTO saveUser(UserDTO userDTO){
        LocalDate localDate = LocalDate.now();
        UserExtendDTO userExtendDTO = null;

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            userExtendDTO = new UserExtendDTO();
            userExtendDTO.setError("El correo ya registrado");
            return userExtendDTO;
        }

        if (!userDTO.getEmail().matches(exprecionCorreo)){
            userExtendDTO = new UserExtendDTO();
            userExtendDTO.setError("El correo no cumple con el patron aaaaaaaa@dominio.cl");
            return userExtendDTO;
        }

        if (!userDTO.getPassword().matches(expresionClave)){
            userExtendDTO = new UserExtendDTO();
            userExtendDTO.setError("La clave no cumple los requisitos minimos de 8 caracteres, que incluyan letras, numeros y carcateres especiales");
            return userExtendDTO;
        }

        UUID uuid = UUID.nameUUIDFromBytes(userDTO.getEmail().getBytes());

        User user = modelMapper.map(userDTO, User.class);
        user.setUuid(uuid.toString());
        user.setCreated(localDate);
        user.setLast_login(localDate);
        user.setModified(null);
        user.setToken(createToken("Prueba BCI", user.getName(), user.getEmail()));
        user.setIsactive(true);
        user = userRepository.save(user);

        userExtendDTO = modelMapper.map(user, UserExtendDTO.class);

        return userExtendDTO;
    }

    private String createToken(String subject, String name, String email){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("name", name)
                .withClaim("email", email)
                .sign(algorithm);

    }


}
