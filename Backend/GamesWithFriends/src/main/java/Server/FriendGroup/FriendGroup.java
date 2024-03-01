package Server.FriendGroup;

import Server.User.User;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "friendgroups")
public class FriendGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String name;

    @ManyToMany(mappedBy = "friendGroupList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> members;

    public FriendGroup(String name) {
        this.name = name;
    }

    public boolean containsUserWithId(int id) {
        for(User user : members) {
            if (user.getID() == id)
                return true;
        }
        return false;
    }

    public void addMembers(List<User> usersToAdd) {
        this.members.addAll(usersToAdd);
    }

    public void removeMembers(List<User> usersToRemove) {
        this.members.removeAll(usersToRemove);
    }
}
