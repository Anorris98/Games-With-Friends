package Server.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    //for testing purposes
    private static final Map<Integer, User> userMap = new HashMap<>();

    @PostMapping("/users")
    public ResponseEntity<Integer> createUser(@RequestBody User newUser) {

        if(newUser == null)
            return ResponseEntity.badRequest().build();

        for(User user : userMap.values()) {
            if(user.getUsername() == newUser.getUsername())
                return ResponseEntity.status(409).build();
        }

        userMap.put(newUser.getID(), newUser);

        return ResponseEntity.ok(newUser.getID());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserInformationForSpecificUser(@PathVariable int id) {

        User requestedUser = userMap.get(id);
        if(requestedUser != null)
            return ResponseEntity.ok(new User("admin", "admin"));

        return ResponseEntity.notFound().build();
    }
}
