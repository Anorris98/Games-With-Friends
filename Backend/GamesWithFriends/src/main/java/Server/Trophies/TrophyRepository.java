package Server.Trophies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrophyRepository extends JpaRepository<Trophy, Integer>
{
    Optional<Trophy> findById(int ID);
}
