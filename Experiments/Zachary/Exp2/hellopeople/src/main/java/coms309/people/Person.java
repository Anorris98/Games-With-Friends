package coms309.people;

import java.util.ArrayList;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    //private ArrayList<Person> friendList;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String address, String telephone/*, ArrayList<Person> friendList*/){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        //this.friendList = friendList;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*
     Adds a person to the friend list of this person.

    public boolean addFriend(Person person)
    {
        return friendList.add(person);
    }*/

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + address + " "
               + telephone;
    }
}
