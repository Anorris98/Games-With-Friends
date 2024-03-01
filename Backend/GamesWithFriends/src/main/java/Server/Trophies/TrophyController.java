package Server.Trophies;

import org.apache.coyote.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TrophyController
{
    @Autowired
    TrophyRepository trophyRepository;

    /**
     * Creates a new trophy with a unique ID using a request body provided name, requirement description, and unlock requirement.
     * @param newTrophy - A data Transfer Object which contains a new trophy's name, requirement description, and unlock requirement.
     * @return A 404 code if any of the DTO fields are empty or a 200 code if the trophy is created successfully.
     */
    //TODO: Check if trophy exists.
    @PostMapping("/trophies")
    public ResponseEntity<?> createTrophy(@RequestBody CreatedTrophyDTO newTrophy)
    {
        if (newTrophy.name().isEmpty() || newTrophy.requirementDescription().isEmpty() || newTrophy.requirement() < 0)
            return ResponseEntity.badRequest().build();

        Trophy trophy = new Trophy(newTrophy.name(), newTrophy.requirementDescription(), newTrophy.requirement());
        trophyRepository.save(trophy);

        return ResponseEntity.ok(trophy.getID());
    }

    /**
     * Returns a list of all the trophies currently in the table. The response is sent as a list of trophy detail DTOs.
     *      These DTOs contain, in order, a trophy's
     *          id,
     *          name,
     *          requirement description,
     *          unlock requirement,
     *          user progress toward unlock,
     *          and whether the trophy is unlocked.
     * @return A list of all the trophies currently in the list.
     */
    @GetMapping("/trophies")
    public ResponseEntity<List<?>> listTrophies()
    {
        List<TrophyDetailsDTO> trophyList = new ArrayList<>();

        for (Trophy trophy : trophyRepository.findAll())
        {
            trophyList.add(trophy.toTrophyDTO());
        }

        return ResponseEntity.ok(trophyList);
    }

    /**
     * Returns a specific trophy's details which is found by a path embedded id.
     * @param trophyId - The id of the trophy to be found.
     * @return A 404 code if the trophy id does not exist or a trophy detail DTO if found.
     */
    @GetMapping("/trophies/{trophyId}")
    public ResponseEntity<?> findTrophy(@PathVariable int trophyId)
    {
        Optional<Trophy> trophy = trophyRepository.findById(trophyId);

        if (trophy.isPresent())
            return ResponseEntity.ok(trophy.get().toTrophyDTO());

        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates one trophy with a user's progress toward that trophy's unlock requirement.
     * @param updateTrophyDTO - contains an id/progress pair used to identify a trophy and update its progress field.
     * @return A TrophyDetailsDTO contianing the updated progress.
     */
    @PutMapping("/trophies")
    public ResponseEntity<?> updateTrophies(@RequestBody TrophyUpdateDTO updateTrophyDTO)
    {
        for (Trophy trophy : trophyRepository.findAll())
        {
            if (trophy.getID() == updateTrophyDTO.id()) {
                trophy.updateDescription(updateTrophyDTO.requirementDescription());
                return ResponseEntity.ok(trophy.toTrophyDTO());
            }
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes a specific trophy via a path embedded id.
     * @param trophyId - The id of the trophy to be deleted.
     * @return A 404 code if the id does not exist or a 200 if the trophy is found and sucessfully deleted.
     */
    @DeleteMapping("/trophies/{trophyId}")
    public ResponseEntity<?> deleteTrophy(@PathVariable int trophyId)
    {
        for (Trophy trophy : trophyRepository.findAll())
        {
            if (trophy.getID() == trophyId)
            {
                trophyRepository.delete(trophy);
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }
}