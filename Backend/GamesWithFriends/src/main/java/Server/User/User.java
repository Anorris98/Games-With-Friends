package Server.User;
import Server.FriendGroup.FriendGroup;
import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String username;
    private String displayName;
    private String password;
    private String description;
    private String profilePicture;

    @ManyToMany
    private List<FriendGroup> friendGroupList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.displayName = "";
        this.description = "";
        this.profilePicture = "";
        friendGroupList = new ArrayList<>();
    }

    public User(String username, String password, String displayName, String description, String profilePicture, List<FriendGroup> friendGroupList) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.description = description;
        this.profilePicture = profilePicture;
        this.friendGroupList = friendGroupList;
    }
}
