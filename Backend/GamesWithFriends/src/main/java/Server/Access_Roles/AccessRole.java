package Server.Access_Roeles;

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
public class AccessRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //Id regarded user
    private int userId;

    //Id of the role asigned to the user
    private int roleId;

    public AccessRole(int id, int userId, int roleId)
    {
        this.ID = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getID()
    {
        return ID;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setRoleIdq(int newId)
    {
        roleId = newId;
    }

    public int getRoleId()
    {
        return roleId;
    }
}