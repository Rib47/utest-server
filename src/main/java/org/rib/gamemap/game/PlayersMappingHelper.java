package org.rib.gamemap.game;

import org.rib.gamemap.model.Coordinates;
import org.rib.gamemap.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.rib.gamemap.common.Constants.Game.FIELD_SIZE;
import static org.rib.gamemap.common.Constants.Game.LOOK_SIZE;

@Component
public class PlayersMappingHelper {

    private static final int SECTION_SIZE = FIELD_SIZE;
    private int MAP_HEIGHT_IN_SECTIONS = (FIELD_SIZE + SECTION_SIZE - 1) / SECTION_SIZE;

    private Map<Coordinates, Set<Integer>> playersByMapSections;

    public PlayersMappingHelper() {
        clearAllData();
    }

    public void clearAllData() {
        playersByMapSections = new HashMap<>(MAP_HEIGHT_IN_SECTIONS * MAP_HEIGHT_IN_SECTIONS);
    }

    public void addPlayer(Player player) {
        int sectionX = player.getX() / SECTION_SIZE;
        int sectionY = player.getY() / SECTION_SIZE;

        Coordinates sectionCoordinates = new Coordinates(sectionX, sectionY);
        playersByMapSections
                .computeIfAbsent(sectionCoordinates, (coord) -> new HashSet<>())
                .add(player.getId());
    }


    public List<Integer> getPlayerIdsForLookArea(int lookAreaTopX, int lookAreaTopY) {
        int startSectionX = lookAreaTopX / SECTION_SIZE;
        int startSectionY = lookAreaTopY / SECTION_SIZE;
        int endSectionX = (lookAreaTopX + LOOK_SIZE) / SECTION_SIZE;
        int endSectionY = (lookAreaTopY + LOOK_SIZE) / SECTION_SIZE;

        return getPlayersIdsFromSector(startSectionX, startSectionY, endSectionX, endSectionY);
    }

    private List<Integer> getPlayersIdsFromSector(final int startSectionX, final int startSectionY, final int endSectionX, final int endSectionY) {
        ArrayList<Integer> sectorIds = new ArrayList<>();
        for (int x = startSectionX; x <= endSectionX; x++) {
            for (int y = startSectionY; y <= endSectionY; y++) {
                Set<Integer> sectionIds = playersByMapSections.get(new Coordinates(x, y));
                sectorIds.addAll(sectionIds);
            }
        }
        return sectorIds;
    }

}
