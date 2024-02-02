package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    // Changed "COM S 309" to "Application Development Practices!"
    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to Application Development Practices!";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
}
