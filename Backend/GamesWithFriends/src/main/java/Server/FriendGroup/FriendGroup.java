package Server.FriendGroup;

import Server.User.User;
import lombok.*;

import java.util.List;
import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FriendGroup {

    private List<User> members;

    private int id;

    private String name;

    public FriendGroup(String name, List<User> members) {
        this.id = new Random().nextInt();
        this.name = name;
        this.members = members;
    }

    public boolean containsUserWithId(int id) {
        for(User user : members) {
            if (user.getID() == id)
                return true;
        }
        return false;
    }
}
