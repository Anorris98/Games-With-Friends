package onetoone.Laptops;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import onetoone.Users.User;
import onetoone.Users.UserRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class LaptopController {

    @Autowired
    LaptopRepository laptopRepository;

    @Autowired
    UserRepository userRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/laptops")
    List<Laptop> getAllLaptops(){
        return laptopRepository.findAll();
    }

    @GetMapping(path = "/laptops/{id}")
    Laptop getLaptopById(@PathVariable int id){
        return laptopRepository.findById(id);
    }

    @PostMapping(path = "/laptops")
    ResponseEntity<String> createLaptop(@RequestBody Laptop Laptop){
        if (Laptop == null)
            return ResponseEntity.badRequest().body(failure);

        return ResponseEntity.ok().body(
                "{\n\"message\":\"success\"," + "\n\"User-ID\": "
                        + laptopRepository.save(Laptop) + "\n}");
    }

    @PutMapping(path = "/laptops/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Laptop request){
        Laptop laptop = laptopRepository.findById(id);
        if(laptop == null)
            return null;
        laptopRepository.save(request);
        return laptopRepository.findById(id);
    }

    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id){

        // Check if there is an object depending on user and then remove the dependency
        User user = userRepository.findByLaptop_Id(id);
        user.setLaptop(null);
        userRepository.save(user);

        // delete the laptop if the changes have not been reflected by the above statement
        laptopRepository.deleteById(id);
        return success;
    }
}
