package Server.FriendGroup;

import Server.User.User;
import Server.User.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendGroupController {
    private final Map<Integer, FriendGroup> friendGroups = new HashMap<>();

    @PostMapping("/friend_groups")
    public ResponseEntity<?> createFriendGroup(@RequestBody FriendGroupMembersDTO memberIds) {
        List<User> tempList = new ArrayList<>();
        for(int id : memberIds.memberIds()) {
            if (!UserController.userMap.containsKey(id))
                return ResponseEntity.status(404).build();
            tempList.add(UserController.userMap.get(id));
        }
        FriendGroup newFriendGroup = new FriendGroup("test", tempList);
        System.out.println(newFriendGroup.getId());
        friendGroups.put(newFriendGroup.getId(), newFriendGroup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friend_groups")
    public ResponseEntity<List<Integer>> getAllFriendGroupsOfUser(@RequestBody int userId) {
        List<Integer> returnList = new ArrayList<>();

        for (FriendGroup group : friendGroups.values()) {
            if (group.containsUserWithId(userId))
                returnList.add(group.getId());
        }

        if(returnList.isEmpty())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(returnList);
    }
}
