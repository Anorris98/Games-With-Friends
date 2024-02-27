package Server.Trophies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Trophies.Trophy;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList();

@RestController
public class TrophyController
{
    private static final HashMap<Integer, Trophy> trophyMap = new HashMap();

    @PostMapping("/trophies")
    public ResponseEntity<Trophy> createTrophy(@RequestBody Trophy newTrophy)
    {
        if (newTrophy == null)
        {
            return ResponseEntity.bacRequest().build();
        }

        for(Trophy trophy : trophyMap.values())
        {
            if (trophy.getID() == newTrophy.getID())
            {
                return ResponseEntity.status(409).build();
            }
        }

        trophyMap.put(newTrophy.getID(), newTrophy);

        return ResponseEntity.ok(newTrophy.getId());
    }

    @GetMapping("/trophies")
    public ResponseEntity<Trophy> getTrophyList(@PathVariable userId)
    {
        ArrayList<Trohpy> trophyList = new ArrayList();
        for (Trophy trophy : trophyMap.values())
        {
            trophyList.add(trophy);
        }

        return ResponseEntity.ok(trophyList);
    }

    @PutMapping("/trophies/{userId}")
    public ResponseEntity<Trophy> updateTrophy(@RequestBody Trophy targetTrophy)
    {
        for (Trophy trophy : targetTrophy.values())
        {
            if (trophy.getID() != trophyId)
            {
                continue;
            }

            //trophy.setRequirement
        }

        //Not final response, just for error prevention purposes in code.
        return ResponseEntity.ok();
    }

    @DeleteMapping("/trophies/{trophyId}")
    public ResponseEntity<Trophy> deleteTrophy(@PathVariable trophyId)
    {
        for(Trophy trophy : trophyMap.value())
        {
            if (trophy.getID() != trophyId)
            {
                continue;
            }
            (Integer)trophyMap.remove(trophy.getID());
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }
}