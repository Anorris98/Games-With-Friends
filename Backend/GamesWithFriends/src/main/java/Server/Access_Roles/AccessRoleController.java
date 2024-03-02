package Server.Access_Roles;

import Server.User.User;
import Server.User.UserRepository;
import org.apache.coyote.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.rmi.AccessException;
import java.util.*;
@RestController
public class AccessRoleController
{
    @Autowired
    AccessRoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/access_roles")
    @Transactional
    public ResponseEntity<?> promoteUser(@RequestBody PromoteUserDTO promoteUser)
    {
        Optional<User> userToUpdate = userRepository.findById(promoteUser.userId());

        if (roleRepository.findById((promoteUser.role())).isEmpty() || userToUpdate.isEmpty())
            return ResponseEntity.badRequest().build();

        if (roleRepository.existsById(promoteUser.userId()))
            return ResponseEntity.status(409).build();

        AccessRole newRole = new AccessRole(promoteUser.userId(), promoteUser.role());
        userToUpdate.get().setRole(newRole);

        roleRepository.save(newRole);
        userRepository.save(userToUpdate.get());

        return ResponseEntity.ok(newRole.toRoleDTO());
    }

    @GetMapping("/access_roles")
    public ResponseEntity<List<?>> listRoles()
    {
        List<RoleDetailsDTO> roleList = new ArrayList<>();

        for (AccessRole role : roleRepository.findAll())
            roleList.add(role.toRoleDTO());

        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/access_roles/{roleId}")
    public ResponseEntity<?> findRole(@PathVariable int roleId)
    {
        Optional<AccessRole> role = roleRepository.findById(roleId);

        if(role.isPresent())
         return ResponseEntity.ok(role.get().toRoleDTO());

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/access_roles")
    @Transactional
    public ResponseEntity<?> updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO)
    {
        Optional<User> userToUpdate = userRepository.findById(roleUpdateDTO.userId());

        if(userToUpdate.isPresent())
        {
            AccessRole newRole = userToUpdate.get().getRole();
            newRole.updateRole(roleUpdateDTO.newRole());

            return ResponseEntity.ok(newRole.toRoleDTO());
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/access_roles/{userId}")
    @Transactional
    public ResponseEntity<?> demoteUser(@PathVariable int userId)
    {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent())
        {
            AccessRole role = user.get().getRole();
            role.updateRole(2);
            return ResponseEntity.ok(role.toRoleDTO());
        }
            return ResponseEntity.badRequest().build();
    }

//    @PostMapping("/access_roles")
//    public ResponseEntity<AccessRole> promoteUser(@ResponseBody user)
//    {
//        if (roleMap.isEmpty())
//        {
//            AccessRole newRole = new AccessRole(user.id, user.userId, user.roleId)
//            return ResponseEntity.status(201).build();
//        }
//
//        for(AccessRole role : roleMap.values())
//        {
//            if (role.getID() == user.roleId)
//            {
//                return ResponseEntity.status(409).build();
//            }
//            else
//            {
//                continue;
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//    @GetMapping("/access_roles")
//    public ResponseEntity<AccessRole> listRoles()
//    {
//        ArrayList<AccessRole> roleList = new ArrayList();
//
//        if (roleMap.isEmpty())
//        {
//            return ResponseEntity.badRequest().build();
//        }
//
//        for(AccessRole role : roleMap.values())
//        {
//            roleList.add(role);
//        }
//
//        return ResponseEntity.ok(roleList);
//    }
//
//    @PutMapping("/access_role")
//    public ResponseEntity<AccessRole> changeRole(@ResponseBody user)
//    {
//        for (AccessRole role : roleMap.values())
//        {
//            if (role.getID() != user.id)
//            {
//                continue;
//            }
//            else
//            {
//                role.setRoleId(user.roleId)
//                return ResponseEntity.ok("Role updated.");
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//    @DeleteMapping("/access_role")
//    public ResponseEntity<AccessRole> deleteRole(@RequestBody role)
//    {
//        for(AccessRole r : roleMap.values())
//        {
//            if (r.getID() != role.roleId)
//            {
//                continue;
//            }
//            else
//            {
//                roleMap.remove(role);
//                return ResponseEntity.ok("Delete successful.");
//            }
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
}
