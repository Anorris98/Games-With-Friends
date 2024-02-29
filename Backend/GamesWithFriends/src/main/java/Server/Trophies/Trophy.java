package Server.Trophies;

import Server.User.User;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Trophies")
@Entity
public class Trophy
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //Name of trophy
    private String name;

    //A count of user progress to unlocking this trophy
    private int requirementCount;
    //The requirement to unlock this trophy.
    private int requirement;
    //Description of this trophy's requirements
    private String requirementDescription;
    //A flag of whether this trophy is unlocked for a user. 0 = locked, 1 = unlocked
    private int lockStatus;



    public Trophy(String name, String requirementDescription, int requirement)
    {
        this.name = name;
        this.requirementDescription = requirementDescription;
        this.requirement = requirement;

        this.requirementCount = 0;
        this.lockStatus = 0;
    }
}