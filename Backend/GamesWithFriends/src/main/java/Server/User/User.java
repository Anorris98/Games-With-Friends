package Server.User;
import Server.FriendGroup.FriendGroup;
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
@Entity
@Table(name = "users")
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

    public User(String username, String password) {
        this.email = username;
        this.password = password;
        this.displayName = "";
        this.description = "";
        this.profilePicture = "";
    }

    public User(String username, String password, String displayName, String description, String profilePicture, List<FriendGroup> friendGroupList) {
        this.email = username;
        this.password = password;
        this.displayName = displayName;
        this.description = description;
        this.profilePicture = profilePicture;
        this.friendGroupList = friendGroupList;
    }

    public UserDetailsDTO userToDetailsDTO() {
        return new UserDetailsDTO(this.getDisplayName(), this.getDescription(), this.getProfilePicture());
    }
}
