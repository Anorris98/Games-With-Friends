package Server.FriendGroup;

import Server.User.User;
import Server.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FriendGroupController {

    @Autowired
    FriendGroupRepository friendGroupRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/friend_groups")
    public ResponseEntity<?> createFriendGroup(@RequestBody FriendGroupMembersDTO memberIds) {

        List<User> tempList = new ArrayList<>();

        for(int id : memberIds.memberIds()) {
            if(userRepository.findById(id).isEmpty())
                return ResponseEntity.status(404).build();
            tempList.add(userRepository.findById(id).get());
        }

        System.out.println(tempList.get(1).getEmail());

        FriendGroup newFriendGroup = new FriendGroup("test");
        newFriendGroup.setMembers(tempList);
        System.out.println(newFriendGroup.getMembers().get(1).getEmail());
        System.out.println(newFriendGroup.getID());
        friendGroupRepository.save(newFriendGroup);
        return ResponseEntity.ok(newFriendGroup.getID());
    }

    @GetMapping("/friend_groups")
    public ResponseEntity<List<Integer>> getAllFriendGroupsOfUser(@RequestHeader(value = "Authorization") String auth) {

        String[] parts = auth.split(" ");
        if (parts.length < 2) {
            return ResponseEntity.badRequest().build();
        }

        int sourceId;

        try {
            sourceId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(500).build();
        }

        if(userRepository.findById(sourceId).isEmpty())
            return ResponseEntity.status(403).build();

        List<Integer> outputList = new ArrayList<>();
        for(FriendGroup group : userRepository.findById(sourceId).get().getFriendGroupList())
            outputList.add(group.getID());

        if(outputList.isEmpty())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(outputList);
    }

    @PutMapping("/friend_groups/{id}")
    @Transactional
    public ResponseEntity<?> addUsersToFriendgroup(@PathVariable int id, @RequestBody FriendGroupMembersDTO members) {

        Optional<FriendGroup> groupOptional = friendGroupRepository.findById(id);
        if (groupOptional.isEmpty())
            return ResponseEntity.badRequest().build();

        FriendGroup toUpdate = groupOptional.get();
        List<User> usersToAdd = new ArrayList<>();

        for (int userId : members.memberIds()) {
            Optional<User> tempUser = userRepository.findById(userId);
            if (tempUser.isEmpty())
                return ResponseEntity.status(404).build();

            usersToAdd.add(tempUser.get());
        }

        toUpdate.addMembers(usersToAdd);
        friendGroupRepository.save(toUpdate);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/friend_groups/{id}")
    public ResponseEntity<?> getAllUserIdsFromFriendList(@PathVariable int id) {

        if (friendGroupRepository.findById(id).isEmpty())
            return ResponseEntity.status(404).build();

        List<Integer> userIds = new ArrayList<>();

        for(User user : friendGroupRepository.findById(id).get().getMembers()) {
            System.out.println(user.getEmail());
            userIds.add(user.getID());
        }

        return ResponseEntity.ok(userIds);
    }

    @DeleteMapping("/friend_groups/{id}")
    public ResponseEntity<?> deleteSpecificFriendGroup(@PathVariable int id, @RequestHeader(value = "Authorization") String auth) {

        boolean check = false;

        String[] parts = auth.split(" ");
        if (parts.length < 2) {
            return ResponseEntity.badRequest().build();
        }

        int sourceId;

        try {
            sourceId = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(500).build();
        }

        if (friendGroupRepository.findById(id).isEmpty())
            return ResponseEntity.status(404).build();

        if(userRepository.findById(sourceId).isEmpty())
            return ResponseEntity.status(401).build();

        //TODO: add check if source is admin

        if(friendGroupRepository.findById(id).get().getMembers().contains(userRepository.findById(sourceId).get()))
            check = true;

        if(!check)
            return ResponseEntity.status(401).build();

        friendGroupRepository.delete(friendGroupRepository.findById(id).get());

        return ResponseEntity.ok().build();
    }
}
