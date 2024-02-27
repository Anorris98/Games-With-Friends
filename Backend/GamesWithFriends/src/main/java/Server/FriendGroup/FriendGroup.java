package Server.FriendGroup;

import Server.User.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class FriendGroup {

    @ManyToMany
    private List<User> members;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public FriendGroup(String name, List<User> members) {
        this.name = name;
        this.members = members;
    }
}
