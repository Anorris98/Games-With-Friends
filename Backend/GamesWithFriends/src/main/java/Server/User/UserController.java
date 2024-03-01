package Server.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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

        if(loginData.email().isEmpty() || loginData.password().isEmpty())
            return ResponseEntity.badRequest().build();

        Optional<User> existingUser = userRepository.findByEmail(loginData.email());

        if(existingUser.isEmpty())
            return ResponseEntity.status(404).build();

        if(!existingUser.get().getPassword().equals(loginData.password()))
            return ResponseEntity.status(401).build();

        return ResponseEntity.ok(existingUser.get().getID());
    }

    @GetMapping("/admin/users") //call only for admins
    public ResponseEntity<List<UserDetailsWithoutPasswordDTO>> getAllUsers(@RequestHeader(value = "Authorization") String auth) {

        String[] parts = auth.split(" ");
        if (parts.length < 2) {
            return ResponseEntity.badRequest().build();
        }

        int userId;

        try {
            userId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(500).build();
        }

        boolean check = true; //for now because we do not have a way to check if a user is an admin

        Optional<User> currentUser = userRepository.findById(userId);

        if(currentUser.isEmpty())
            return ResponseEntity.status(404).build();

        //TODO: insert checking for admin access here
        if(!check)
            return ResponseEntity.status(401).build();


        List<UserDetailsWithoutPasswordDTO> tempList = new ArrayList<>();
        for(User user : userRepository.findAll())
            tempList.add(new UserDetailsWithoutPasswordDTO(user.getID(), user.getEmail(), user.getDisplayName()));

        return ResponseEntity.ok(tempList);
    }

    @GetMapping("/users")
    public ResponseEntity<UserDetailsArrayDTO> getAllUserDetails() {

        UserDetailsArrayDTO output = new UserDetailsArrayDTO(new ArrayList<>());
        for(User user : userRepository.findAll())
            output.userDetails().add(user.userToDetailsDTO());

        return  !output.userDetails().isEmpty() ? ResponseEntity.ok(output) : ResponseEntity.status(500).build();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsDTO> getUserInformationForSpecificUser(@PathVariable int id) {

        Optional<User> requestedUser = userRepository.findById(id);

        return requestedUser.isPresent() ? ResponseEntity.ok(requestedUser.get().userToDetailsDTO()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}")
    @Transactional
    public ResponseEntity<?> updateUserDetails(@PathVariable int id, @RequestBody UserDetailsDTO userDetails) {

        Optional<User> requestedUser = userRepository.findById(id);

        if(requestedUser.isEmpty())
            return ResponseEntity.status(404).build();

        if(userDetails.displayName().isEmpty())
            return ResponseEntity.status(500).build();

        requestedUser.get().updateUserDetails(userDetails);

        userRepository.save(requestedUser.get());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserEntry(@PathVariable int id, @RequestHeader(value = "Authorization") String auth) {

        String[] parts = auth.split(" ");
        if (parts.length < 2) {
            return ResponseEntity.badRequest().build();
        }

        int sourceId;

        try {
            sourceId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(500).body(e);
        }

        Optional<User> sourceUser = userRepository.findById(sourceId);

        //checks if sourceId is valid
        if(sourceUser.isEmpty())
            return ResponseEntity.status(403).build();

        //checks if sourceUser is the user to be deleted
        //TODO: add additional checking for if the sourceUser is an admin
        if(sourceUser.get().getID() != id)
            return ResponseEntity.status(401).build();

        Optional<User> toUpdateUser = userRepository.findById(id);

        if(toUpdateUser.isEmpty())
            return ResponseEntity.status(404).build();

        userRepository.delete(toUpdateUser.get());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/password")
    @Transactional
    public ResponseEntity<?> updateUserPassword(@PathVariable int id, @RequestBody UserUpdatePasswordDTO update) {

        Optional<User> userToUpdate = userRepository.findById(id);

        if(userToUpdate.isEmpty())
            return ResponseEntity.status(404).build();

        if(!userToUpdate.get().getPassword().equals(update.oldPassword()))
            return ResponseEntity.status(401).build();

        if(update.newPassword().isEmpty())
            return ResponseEntity.badRequest().build();

        userToUpdate.get().setPassword(update.newPassword());

        userRepository.save(userToUpdate.get());

        return ResponseEntity.ok().build();
    }
}
