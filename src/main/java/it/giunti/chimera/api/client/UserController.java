package it.giunti.chimera.api.client;

import java.util.LinkedHashMap;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.exception.Unauthorized401Exception;
import it.giunti.chimera.model.entity.User;
import it.giunti.chimera.service.AuthService;
import it.giunti.chimera.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("authService")
    private AuthService authService;
    
    @PostMapping("/api/client/authenticate")
    public User authenticate(@Valid @RequestBody LinkedHashMap<String, String> paramsMap) 
    		throws Unauthorized401Exception {
    	try {
    		String username = paramsMap.get("username");
    		String password = paramsMap.get("password");
			authService.authenticate(username, password);
			User user = userService.getUserByUsername(username);
			return user;
		} catch (AuthenticationException e) {
			throw new Unauthorized401Exception(e.getMessage(), e);
		}
    }
    
    @PostMapping("/api/client/createuser")
    public User createNewUser(@Valid @RequestBody User user) {
    	return userService.addUser(user);
    }
 
    @PutMapping("/api/client/changeuser")
    public void changeExistingUser(@Valid @RequestBody User user) {
    	userService.modifyUser(user);
    }
 
    @DeleteMapping("/api/client/removeuser/{username}")
    public void removeUser(@PathVariable(value = "id") String username) {
    	userService.removeUser(username);
    }
 
    @GetMapping("/api/client/viewsingleuser/{username}")
    public User viewUserByUsername(@PathVariable(value = "id") String username) {
        return userService.getUserByUsername(username);
    }
 
    @GetMapping("/api/client/viewallusers")
    public List<User> viewAllUsers() {
        return userService.getAllUsers();
    }
 
}
