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

        FriendGroup newFriendGroup = new FriendGroup("test");
        newFriendGroup.setMembers(tempList);
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
    public ResponseEntity<?> addUsersToFriendgroup(@RequestParam int groupId, @RequestBody FriendGroupMembersDTO members) {

        Optional<FriendGroup> groupOptional = friendGroupRepository.findById(groupId);
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
    public ResponseEntity<?> getAllUserIdsFromFriendList(@RequestParam int groupId) {
        if (friendGroupRepository.findById(groupId).isEmpty())
            return ResponseEntity.status(404).build();

        FriendGroupMembersDTO tempDTO = new FriendGroupMembersDTO(new ArrayList<>());

        for(User user : friendGroupRepository.findById(groupId).get().getMembers()) {
            tempDTO.memberIds().add(user.getID());
        }

        return ResponseEntity.ok(tempDTO);
    }

    @DeleteMapping("/friend_groups/{id}")
    public ResponseEntity<?> deleteSpecificFriendGroup(@RequestParam int groupId, @RequestHeader(value = "Authorization") String auth) {

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

        if (friendGroupRepository.findById(groupId).isEmpty())
            return ResponseEntity.status(404).build();

        if(userRepository.findById(sourceId).isEmpty())
            return ResponseEntity.status(401).build();

        //TODO: add check if source is admin

        if(friendGroupRepository.findById(groupId).get().getMembers().contains(userRepository.findById(sourceId).get()))
            check = true;

        if(!check)
            return ResponseEntity.status(401).build();

        friendGroupRepository.delete(friendGroupRepository.findById(groupId).get());

        return ResponseEntity.ok().build();
    }
}
