package Server.Trophies;

import com.fasterxml.jackson.annotation.jsonTypeId;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    public int getID()
    {
        return ID;
    }

    public String getTrophyName()
    {
        return this.mame;
    }

    public String getRequirementDescription()
    {
        return this.requirementDescription;
    }

    public void updateRequirementCount(int count)
    {
        this.requirementCount += count;
        updateStatus();
    }

    private void updateStatus()
    {
        if (requirementCount >= requirement)
        {
            lockStatus = 1;
        }
    }

    public int getRequirement()
    {
        return requirement;
    }

    public int getRequirementCount()
    {
        return this.requirementCount;
    }

    public int getLockStatus()
    {
        return lockStatus;
    }
}