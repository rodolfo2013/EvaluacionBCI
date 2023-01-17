package cl.bci.login.service;

import cl.bci.login.database.model.User;
import cl.bci.login.database.repository.UserRepository;
import cl.bci.login.dto.UserDTO;
import cl.bci.login.dto.UserExtendDTO;
import cl.bci.login.exception.ValidationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${exprecionCorreo}")
    private String exprecionCorreo;

    @Value("${expresionClave}")
    private String expresionClave;


    private static final String SECRET_KEY = "QWssRT123*--Dww";
    private static final long EXPIRATION_TIME = 864_000_000;
    public UserExtendDTO saveUser(UserDTO userDTO) throws Exception {

        //Realizo las validaciones solicitadas
        validations(userDTO);

        LocalDateTime localDateTime = LocalDateTime.now();
        UserExtendDTO userExtendDTO = null;

        UUID uuid = UUID.nameUUIDFromBytes(userDTO.getEmail().getBytes());
        String key=uuid.toString();

        User user = modelMapper.map(userDTO, User.class);
        user.setUuid(key);
        user.setCreated(localDateTime);
        user.setLast_login(localDateTime);
        user.setModified(null);
        user.setToken(createToken("Prueba BCI", user.getName(), user.getEmail()));
        user.setIsactive(true);

        //Asocio el uuid a cada elemento phone
        user.getPhones().forEach(e->e.setUuid(key));

        user = userRepository.save(user);

        userExtendDTO = modelMapper.map(user, UserExtendDTO.class);

        return userExtendDTO;
    }


    public List<UserExtendDTO> getUsers() throws Exception {
        List<User> users = userRepository.findAll();

        List<UserExtendDTO> usersDTO = users
                .stream()
                .map(user -> modelMapper.map(user, UserExtendDTO.class))
                .collect(Collectors.toList());
        return usersDTO;
    }

    private void validations(UserDTO userDTO) throws Exception {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new ValidationException("El correo ya registrado");
        }

        if (!userDTO.getEmail().matches(exprecionCorreo)){
            throw new ValidationException("El correo no cumple con el patron aaaaaaaa@dominio.cl");
        }

        if (!userDTO.getPassword().matches(expresionClave)){
            throw new ValidationException("La clave no cumple los requisitos minimos de 8 caracteres, que incluyan letras, numeros y carcateres especiales");
        }
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
