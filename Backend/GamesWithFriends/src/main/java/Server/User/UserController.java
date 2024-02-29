package Server.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //for testing purposes
    //TODO: replace with actual db functionality
    public static final Map<Integer, User> userMap = new HashMap<>();

    @PostMapping("/users")
    public ResponseEntity<Integer> createUser(@RequestBody UserCredentialsDTO newCredentials) {

        //checks if body data is not empty
        if(newCredentials.email().isEmpty() || newCredentials.password().isEmpty())
            return ResponseEntity.badRequest().build();

        //looks if user already exists within db
        Optional<User> existingUser = userRepository.findByEmail(newCredentials.email());
        if(existingUser.isPresent())
            return ResponseEntity.status(409).build();

        //creates and saves new user to db
        User newUser = new User(newCredentials.email(), newCredentials.password());
        newUser = userRepository.save(newUser);

        System.out.println("Registered new user with the id:" + newUser.getID());

        return ResponseEntity.ok(newUser.getID());
    }

    @PostMapping("/login")
    public ResponseEntity<Integer> userLogin(@RequestBody UserCredentialsDTO loginData) {
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
    public ResponseEntity<List<UserDetailsWithoutPassword>> getAllUsers(@RequestBody UserCredentialsDTO userCredentials) {
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
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails() {
        List<UserDetailsDTO> tempList = new ArrayList<>();
        for (User user : userMap.values())
            tempList.add(new UserDetailsDTO(user.getDisplayName(), user.getDescription(), user.getProfilePicture()));

        if(tempList.isEmpty())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(tempList);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsDTO> getUserInformationForSpecificUser(@PathVariable int id) {

        User requestedUser = userMap.get(id);
        if(requestedUser != null)
            return ResponseEntity.ok(requestedUser.userToDetailsDTO());

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserDetails(@PathVariable int id, @RequestBody UserDetailsDTO userDetails) {
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
    public ResponseEntity<?> deleteUserEntry(@PathVariable int id, @RequestBody UserCredentialsDTO userCredentials) {
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
    public ResponseEntity<?> updateUserPassword(@PathVariable int id, @RequestBody UserCredentialsDTO scuffXD) {
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
