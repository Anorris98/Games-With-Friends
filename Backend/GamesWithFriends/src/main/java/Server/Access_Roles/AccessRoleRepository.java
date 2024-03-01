package Server.Access_Roles;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Access;
import java.util.Optional;

public interface AccessRoleRepository extends JpaRepository<AccessRole, Integer>
{
    Optional<AccessRole> findById(int ID);
}
