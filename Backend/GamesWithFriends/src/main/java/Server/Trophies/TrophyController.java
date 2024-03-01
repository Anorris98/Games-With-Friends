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
    @PostMapping("/trophies")
    public ResponseEntity<Integer> createTrophy(@RequestBody CreatedTrophyDTO newTrophy)
    {
        if (newTrophy.name().isEmpty() || newTrophy.requirementsDescription().isEmpty() || newTrophy.requirement() < 0)
        {
            return ResponseEntity.badRequest().build();
        }

        Trophy trophy = new Trophy(newTrophy.name(), newTrophy.requirementsDescription(), newTrophy.requirement());
        trophyRepository.save(trophy);

        return ResponseEntity.ok(200);
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
    public ResponseEntity<List<TrophyDetailsDTO>> listTrophies()
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
    public ResponseEntity<TrophyDetailsDTO> findTrophy(@PathVariable int trophyId)
    {
        Optional<Trophy> trophy = trophyRepository.findById(trophyId);

        if (trophy.isPresent())
        {
            return ResponseEntity.ok(trophy.get().toTrophyDTO());
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates a list of trophies by searching the table for request body provided ids.
     * These trophies are then updated by the amount of corresponding progress.
     * Returned is a list of the updated trophy details via DTOs.
     * @param trophyUpdates - A list of id/progress pairs that indicate which trophies need to be updated and by how much
     * @return A list of the updated trophy details.
     */
    //TODO: Update to track a list of id's that do not exist.
    @PutMapping("/trophies")
    public ResponseEntity<List<TrophyDetailsDTO>> updateTrophies(@RequestBody List<TrophyUpdateDTO> trophyUpdates)
    {
        /*
        for each id that must be updated in the updateDTO,
            1. find that trophy's id in the trophyRepo
            2. update that id
         */

        if (trophyUpdates.isEmpty())
        {
            ResponseEntity.status(409).build();
        }

        List<TrophyDetailsDTO> returnList = new ArrayList<>();

        int index = 0;
        for(Trophy trophy : trophyRepository.findAll())
        {
            if (trophyUpdates.contains(trophy.getID()))
            {
                //trophy.updateTrophy(trophyUpdates.get(index).progress());
                returnList.add(trophy.toTrophyDTO());
            }
            index ++;
        }

        return ResponseEntity.ok(returnList);
    }

    /**
     * Deletes a specific trophy via a path embedded id.
     * @param trophyId - The id of the trophy to be deleted.
     * @return A 404 code if the id does not exist or a 200 if the trophy is found and sucessfully deleted.
     */
    @DeleteMapping("/trophies/{trophyId}")
    public ResponseEntity<Integer> deleteTrophy(@PathVariable int trophyId)
    {
        for (Trophy trophy : trophyRepository.findAll())
        {
            if (trophy.getID() == trophyId)
            {
                trophyRepository.delete(trophy);
                return ResponseEntity.ok(200);
            }
        }

        return ResponseEntity.badRequest().build();
    }
}