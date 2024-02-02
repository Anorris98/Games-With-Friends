package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

/*
 Tested and explored using the folowing JSON objects:

 {
    "firstName": "Zachary",
    "lastName": "Sears",
    "address": "123 Street Road",
    "telephone": "1234567890"
}

{
    "firstName": "John",
    "lastName": "Smith",
    "address": "456 Road Street",
    "telephone": "0987654321"
}
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    /*
     I was having many issues with the below method adding a new person to this list.
     Initially, I was trying to use the form-data body type in Postman, but kept getting an
     "unsupported media type" error. I poked around the internet for a while and found that this body type
     does not format the body in JSON, so some more research caused me to switch over to the "raw" body type
     which allows for JSON formatting.
     In this second attempt, I continued using my first format, which used a JSON Object inside a JSON array, i.e.
     [{}] because I thought that all JSON files HAD to include an array/object format.
     This attempt continuously returned a 400 "Bad Request" error. After a bit more research, the idea to remove the
     array came to me, so I did and just POSTed the object ({}) and it added the new person.
     */

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public  String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }



    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }

    /*
     I'm certain that this is completely incorrect as I don't have previous experience with this form of work,
     but initially, I wanted to use PutMapping, but as this signature is pretty much exactly the same as the
     update function above, Postman won't run the request. This is due to some "internal Server Error" status 500,
     likely it not being able to distinguish between mapping types/signatures. However, I feel this sort of method will
     be useful for our application as we will allow users to have a friends list to compete among themselves, so we
     may need something like this that actually works.
     */

    //NEW FUNCTION
    //Adds a person to a friend list
    //Note that this is a very terrible implementation of this form of method I'm sure.

    //@PostMapping("/people/{firstName}")
    /*
    public boolean addFriend(@PathVariable String firstName, @RequestBody Person g)
    {
        Person p = peopleList.get(firstName);
        return p.addFriend(g) && g.addFriend(p);
    }*/
}

