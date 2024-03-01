package Server.Access_Roles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Server.Access_Roles.AccessRole;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccessRoleController
{


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
