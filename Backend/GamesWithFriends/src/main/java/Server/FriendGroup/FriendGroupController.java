package Server.FriendGroup;

import Server.User.User;
import Server.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendGroupController {

    @Autowired
    FriendGroupRepository friendGroupRepository;

    @Autowired
    UserRepository userRepository;

    private final Map<Integer, FriendGroup> friendGroups = new HashMap<>();

    /*@PostMapping("/friend_groups")
    public ResponseEntity<?> createFriendGroup(@RequestBody FriendGroupMembersDTO memberIds) {
        List<User> tempList = new ArrayList<>();
        for(int id : memberIds.memberIds()) {
            if (!UserController.userMap.containsKey(id))
                return ResponseEntity.status(404).build();
            tempList.add(UserController.userMap.get(id));
        }
        FriendGroup newFriendGroup = new FriendGroup("test");
        //System.out.println(newFriendGroup.getId());
        //friendGroups.put(newFriendGroup.getId(), newFriendGroup);
        return ResponseEntity.ok().build();
    }*/

    @GetMapping("/friend_groups")
    public ResponseEntity<List<Integer>> getAllFriendGroupsOfUser(@RequestBody int userId) {
        List<Integer> returnList = new ArrayList<>();

        /*for (FriendGroup group : friendGroups.values()) {
            if (group.containsUserWithId(userId))
                returnList.add(group.getId());
        }*/

        if(returnList.isEmpty())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(returnList);
    }

    /*@PutMapping("/friend_groups/{id}")
    public ResponseEntity<?> updateUsersOfFriendGroup(@RequestParam int groupId, @RequestBody FriendGroupMembersDTO members) {

        if(!friendGroups.containsKey(groupId))
            return ResponseEntity.badRequest().build();

        List<User> tempList = new ArrayList<>();
        for(int id : members.memberIds()) {
            if (!UserController.userMap.containsKey(id))
                return ResponseEntity.status(404).build();
            tempList.add(UserController.userMap.get(id));
        }

        friendGroups.get(groupId).setMembers(tempList);
        return ResponseEntity.ok().build();
    }*/

    @GetMapping("/friend_groups/{id}")
    public ResponseEntity<?> getAllUserIdsFromFriendList(@RequestParam int groupId) {
        if (!friendGroups.containsKey(groupId))
            return ResponseEntity.status(404).build();

        FriendGroupMembersDTO tempDTO = new FriendGroupMembersDTO(new ArrayList<>());

        for(User user : friendGroups.get(groupId).getMembers()) {
            tempDTO.memberIds().add(user.getID());
        }

        return ResponseEntity.ok(tempDTO);
    }

    @DeleteMapping("/friend_groups/{id}")
    public ResponseEntity<?> deleteSpecificFriendGroup(@RequestParam int groupId) {
        if (!friendGroups.containsKey(groupId))
            return ResponseEntity.status(404).build();

        friendGroups.remove(groupId);

        return ResponseEntity.ok().build();
    }
}
