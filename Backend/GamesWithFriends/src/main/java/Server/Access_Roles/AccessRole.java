package Server.Access_Roles;

import Server.User.User;
import Server.Access_Roles.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.management.relation.Role;
import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private int userId;

    RoleEnum roleEnum;

    @OneToMany(mappedBy = "role")
    private List<User> userList = new ArrayList<>();

    public AccessRole(int userId, int roleValue)
    {
        this.userId = userId;

        switch(roleValue) {
            case 0:
                roleEnum = RoleEnum.ADMIN;
                break;
            case 1:
                roleEnum = RoleEnum.MOD;
                break;
            default:
                roleEnum = RoleEnum.USER;
                break;
        }
    }

    public RoleDetailsDTO toRoleDTO()
    {
        return new RoleDetailsDTO(this.getID(), this.userId, this.roleEnum);
    }

    public void updateRole(int newRole)
    {
        switch(newRole) {
            case 0:
                roleEnum = RoleEnum.ADMIN;
                break;
            case 1:
                roleEnum = RoleEnum.MOD;
                break;
            default:
                roleEnum = RoleEnum.USER;
                break;
        }
    }

}