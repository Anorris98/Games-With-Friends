package Server.User;
import Server.FriendGroup.FriendGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int ID;

    private String email;
    private String displayName;
    private String password;
    private String description;
    private String profilePicture;

    private List<FriendGroup> friendGroupList;

    public User(String username, String password) {
        this.email = username;
        this.password = password;
        this.displayName = "";
        this.description = "";
        this.profilePicture = "";
        friendGroupList = new ArrayList<>();
        this.ID = new Random().nextInt();
    }

    public User(String username, String password, String displayName, String description, String profilePicture, List<FriendGroup> friendGroupList) {
        this.email = username;
        this.password = password;
        this.displayName = displayName;
        this.description = description;
        this.profilePicture = profilePicture;
        this.friendGroupList = friendGroupList;
    }

    public userDetailsDTO userToDetailsDTO() {
        return new userDetailsDTO(this.getDisplayName(), this.getEmail(), this.getProfilePicture());
    }
}
