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
    //private int progress;
    //The requirement to unlock this trophy.
    private int requirement;
    //Description of this trophy's requirements
    private String requirementDescription;
    //A flag of whether this trophy is unlocked for a user. 0 = locked, 1 = unlocked
    //private int lockStatus;



    public Trophy(String name, String requirementDescription, int requirement)
    {
        this.name = name;
        this.requirementDescription = requirementDescription;
        this.requirement = requirement;

        //this.progress = 0;
        //this.lockStatus = 0;
    }

    public TrophyDetailsDTO toTrophyDTO()
    {
        return new TrophyDetailsDTO(this.getID(), this.getName(), this.getRequirementDescription(), this.getRequirement()/*this.getProgress(), this.getLockStatus()*/);
    }


    /*
    The way that this method works for right now, each time we update a trophy, the progress/requirement ratio is checked
        even after lock status is changed. This will lead to repeated unlocks of this trophy.

     TODO: Update so that repeated unlocked are not possible.
     */
//    public void updateTrophy(int update)
//    {
//        progress += update;
//    }
//
//    private void unlockTrophy()
//    {
//        if (progress >= requirement)
//        {
//            lockStatus = 1;
//        }
//    }
}