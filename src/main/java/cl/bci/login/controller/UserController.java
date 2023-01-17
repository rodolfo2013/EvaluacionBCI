package cl.bci.login.controller;

import cl.bci.login.dto.ErrorDTO;
import cl.bci.login.dto.UserDTO;
import cl.bci.login.dto.UserExtendDTO;
import cl.bci.login.exception.ValidationException;
import cl.bci.login.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Graba el usuario"
            ,notes = "Al consumir el servicio con el JSON especificado, el proceso realiza validaciones y realiza la gravacion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El usuario fue grabado", response = UserExtendDTO.class ),
            @ApiResponse(code = 400, message = "ERROR, las validaciones no fueron exitosas", response = ErrorDTO.class),
            @ApiResponse(code = 500, message = "Error inesperado del sistema") })
    @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
    public ResponseEntity<UserExtendDTO> save(@RequestBody UserDTO userDto) throws Exception{
        UserExtendDTO userExtendDTO = userService.saveUser(userDto);
        return ResponseEntity.ok().body(userExtendDTO);
    }



    @ApiOperation(value = "Lista usuarios"
            ,notes = "Entrega todos los usuarios registrados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. Lista los usuarios", response = UserExtendDTO.class ),
            @ApiResponse(code = 400, message = "ERROR, upss, algo paso", response = ErrorDTO.class) })
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public ResponseEntity<List<UserExtendDTO>> getAll() throws Exception{
        List<UserExtendDTO> userDTO = userService.getUsers();
        return ResponseEntity.ok().body(userDTO);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> errorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ErrorDTO error = new ErrorDTO(ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> errorHandlerRinTime(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ErrorDTO error = new ErrorDTO(ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

}
