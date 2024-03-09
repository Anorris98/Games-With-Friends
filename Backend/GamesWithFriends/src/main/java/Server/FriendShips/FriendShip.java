package Server.FriendShips;

import Server.User.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "friendships")
public class FriendShip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;

    @ManyToOne
    User user;

    @ManyToOne
    User friend;

    String note;

    Date since;

    public FriendShip(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.since = new Date();
        this.note = "";
    }
}
