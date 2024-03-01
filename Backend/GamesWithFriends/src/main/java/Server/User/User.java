package Server.User;
import Server.FriendGroup.FriendGroup;
import Server.Trophies.Trophy;
import Server.Access_Roles.AccessRole;
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
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String email;
    private String displayName;
    private String password;
    private String description;
    private String profilePicture;

    @ManyToMany
    @JoinTable(
            name = "user_friendgroup",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "friendgroup_id", referencedColumnName = "ID")
    )
    private List<FriendGroup> friendGroupList;

    @ManyToMany
    @JoinTable(
            name = "user_trophy",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "trophy_id", referencedColumnName = "ID")
    )
    private List<Trophy> trophiesList;

//    @ManyToOne(name = "role_id", fetch = FetchType.LAZY, referencedComlumnName = "ID")
//    @JoinColumn(name = "role_id")
//    private  AccessRole role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.displayName = "";
        this.description = "";
        this.profilePicture = "";
    }

    public UserDetailsDTO userToDetailsDTO() {
        return new UserDetailsDTO(this.getDisplayName(), this.getDescription(), this.getProfilePicture());
    }

    public void updateUserDetails(UserDetailsDTO details) {
        this.description = details.description();
        this.displayName = details.displayName();
        this.profilePicture = details.profilePicture();
    }
}
