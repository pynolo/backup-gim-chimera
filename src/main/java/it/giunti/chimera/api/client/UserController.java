package it.giunti.chimera.api.client;

import java.util.List;

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

import it.giunti.chimera.model.entity.User;
import it.giunti.chimera.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
 
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
