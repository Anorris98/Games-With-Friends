package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to GamesWithFriends";
    }

    @GetMapping("/{name}")
    public String welcomeUser(@PathVariable String name) {
        return "Hello " + name + " and welcome to GamesWithFriends";
    }

    @PostMapping("/")
    public String welcomePost() {
        return "No post calls can be handled yet!";
    }
}
