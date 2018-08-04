package org.rib.gamemap.core;

import org.rib.gamemap.model.Coordinates;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rib.gamemap.common.Constants.Game.FIELD_SIZE;
import static org.rib.gamemap.common.Constants.Game.PLAYERS_COUNT;

@Component
public class PlayersMappingHelper {

    private static final int SECTION_SIZE = FIELD_SIZE;

    private Map<Coordinates, List<Integer>> playersByMapSecvtions = new HashMap<>(PLAYERS_COUNT);

    public PlayersMappingHelper() {
        int mapSectionsX = (FIELD_SIZE + SECTION_SIZE - 1) / SECTION_SIZE;
        playersByMapSecvtions = new HashMap<>(PLAYERS_COUNT)
    }
}
