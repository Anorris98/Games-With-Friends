package Server.FriendShips;

import Server.FriendGroup.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendShipRepository extends JpaRepository<FriendShip, Integer> {
}
