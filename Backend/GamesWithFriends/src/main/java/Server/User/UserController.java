package Server.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
public class UserController {

    //for testing purposes
    //TODO: replace with actual db functionality
    public static final Map<Integer, User> userMap = new HashMap<>();

    @PostMapping("/users")
    public ResponseEntity<Integer> createUser(@RequestBody userCredentialsDTO newCredentials) {

        User newUser = new User(newCredentials.email(), newCredentials.password());

        if(newUser.getEmail().isEmpty() || newUser.getPassword().isEmpty())
            return ResponseEntity.badRequest().build();

        for(User user : userMap.values()) {
            if(user.getEmail().equals(newUser.getEmail()))
                return ResponseEntity.status(409).build();
        }

        userMap.put(newUser.getID(), newUser);

        System.out.println(newUser.getID());

        return ResponseEntity.ok(newUser.getID());
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> userLogin(@RequestBody userCredentialsDTO loginData) {
        for(User user : userMap.values()) {
            if(user.getEmail().equals(loginData.email())) {
                if(!user.getPassword().equals(loginData.password()))
                    return ResponseEntity.status(401).build();

                return ResponseEntity.ok(user.getID());
            }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/admin/users") //call only for admins
    public ResponseEntity<List<UserDetailsWithoutPassword>> getAllUsers(@RequestBody userCredentialsDTO userCredentials) {
        boolean check = false;

        for(User user : userMap.values()) {
            if (user.getEmail().equals(userCredentials.email())) {
                if(user.getPassword().equals(userCredentials.password()))
                    check = true;
            }
        }
        if(!check)
            return ResponseEntity.status(401).build();


        List<UserDetailsWithoutPassword> tempList = new ArrayList<>();
        for(User user : userMap.values())
            tempList.add(new UserDetailsWithoutPassword(user.getID(), user.getEmail(), user.getDisplayName()));

        return ResponseEntity.ok(tempList);
    }

    @GetMapping("/users")
    public ResponseEntity<List<userDetailsDTO>> getAllUserDetails() {
        List<userDetailsDTO> tempList = new ArrayList<>();
        for (User user : userMap.values())
            tempList.add(new userDetailsDTO(user.getDisplayName(), user.getDescription(), user.getProfilePicture()));

        if(tempList.isEmpty())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(tempList);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<userDetailsDTO> getUserInformationForSpecificUser(@PathVariable int id) {

        User requestedUser = userMap.get(id);
        if(requestedUser != null)
            return ResponseEntity.ok(requestedUser.userToDetailsDTO());

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
            if (user.getEmail().equals(userCredentials.email())) {
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

        if(!userMap.get(id).getPassword().equals(scuffXD.email()))
            return ResponseEntity.status(401).build();

        User tempUser = userMap.get(id);
        tempUser.setPassword(scuffXD.password());
        userMap.replace(id, tempUser);

        return ResponseEntity.ok().build();
    }
}
