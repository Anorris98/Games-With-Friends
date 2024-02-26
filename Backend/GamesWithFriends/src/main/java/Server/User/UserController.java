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
            if(user.getUsername().equals(newUser.getUsername()))
                return ResponseEntity.status(409).build();
        }

        userMap.put(newUser.getID(), newUser);

        return ResponseEntity.ok(newUser.getID());
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> userLogin(@RequestBody userCredentialsDTO loginData) {
        for(User user : userMap.values()) {
            if(user.getUsername().equals(loginData.username())) {
                if(!user.getPassword().equals(loginData.password()))
                    return ResponseEntity.status(401).build();

                return ResponseEntity.ok(user.getID());
            }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserInformationForSpecificUser(@PathVariable int id) {

        User requestedUser = userMap.get(id);
        if(requestedUser != null)
            return ResponseEntity.ok(new User("admin", "admin"));

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable int id, @RequestBody userDetailsDTO userDetails) {
        if(!userMap.containsKey(id))
            return ResponseEntity.status(404).build();

        User tempUser = userMap.get(id);
        tempUser.setDisplayName(userDetails.displayName());
        tempUser.setDescription(userDetails.description());
        tempUser.setProfilePicture(userDetails.profilePicture());

        userMap.replace(id, tempUser);

        return ResponseEntity.ok().build();
    }

    //TODO: update API specification for added response codes
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserEntry(@PathVariable int id, @RequestBody userCredentialsDTO userCredentials) {
        for(User user : userMap.values()) {
            if (user.getUsername().equals(userCredentials.username())) {
                if(!user.getPassword().equals(userCredentials.password()))
                    return ResponseEntity.status(401).build();

                if(!userMap.containsKey(id))
                    return ResponseEntity.status(404).build();

                userMap.remove(id);
                return ResponseEntity.ok().build();
            }
        }
        //TODO: update to different response code
        return ResponseEntity.status(401).build();
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable int id, @RequestBody userCredentialsDTO scuffXD) {
        if(!userMap.containsKey(id))
            return ResponseEntity.status(404).build();

        if(!userMap.get(id).getPassword().equals(scuffXD.username()))
            return ResponseEntity.status(401).build();

        User tempUser = userMap.get(id);
        tempUser.setPassword(scuffXD.password());
        userMap.replace(id, tempUser);

        return ResponseEntity.ok().build();
    }
}
