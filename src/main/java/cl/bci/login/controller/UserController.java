package cl.bci.login.controller;

import cl.bci.login.dto.ErrorDTO;
import cl.bci.login.dto.UserDTO;
import cl.bci.login.dto.UserExtendDTO;
import cl.bci.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
    public ResponseEntity<UserExtendDTO> save(@RequestBody UserDTO userDto) throws Exception{
        UserExtendDTO userExtendDTO = userService.saveUser(userDto);
        if(userExtendDTO == null){
            throw new Exception("Error inesperado");
        }
        if (userExtendDTO.getError() != null){
            throw new Exception(userExtendDTO.getError());
        }
        return ResponseEntity.ok().body(userExtendDTO);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> errorHandler(
            HttpServletRequest request, HttpServletResponse response, Exception ex) {

        ErrorDTO error = new ErrorDTO(ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

}
