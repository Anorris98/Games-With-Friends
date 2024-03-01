package Server.Access_Roles;

import Server.User.User;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "access_roles")
@Entity
public class AccessRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY);
    private int ID;

    private String name;

    private int userId;

    private int roleId;

    public AccessRole(String name, int userId, int roldId)
    {
        this.name = name;
        this.userId = userId;
        this.roleId = roldId;
    }
}