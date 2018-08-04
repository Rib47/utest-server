package org.rib.gamemap.core;

import org.rib.gamemap.model.Coordinates;
import org.rib.gamemap.model.Player;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.rib.gamemap.common.Constants.Game.FIELD_SIZE;
import static org.rib.gamemap.common.Constants.Game.LOOK_SIZE;
import static org.rib.gamemap.common.Constants.Game.PLAYERS_COUNT;

@Component
public class PlayersMappingHelper {

    private static final int SECTION_SIZE = FIELD_SIZE;

    private Map<Coordinates, Set<Integer>> playersByMapSections = new HashMap<>(PLAYERS_COUNT);

    public PlayersMappingHelper() {
        int mapHeightInSections = (FIELD_SIZE + SECTION_SIZE - 1) / SECTION_SIZE;
        playersByMapSections = new HashMap<>(mapHeightInSections * mapHeightInSections)
    }

    public void addPlayer(Player player, int playerX, int playerY) {
        int sectionX = playerX / SECTION_SIZE;
        int sectionY = playerY / SECTION_SIZE;

        Coordinates sectionCoordinates = new Coordinates(sectionX, sectionY);
        playersByMapSections
                .computeIfAbsent(sectionCoordinates, (coord) -> new HashSet<>())
                .add(player.getId());
    }


    public List<Integer> getPlayersIdForSector(int sectorX, int sectorY) {
        int startSectionX = sectorX / SECTION_SIZE;
        int startSectionY = sectorY / SECTION_SIZE;
        int endSectionX = (sectorX + LOOK_SIZE) / SECTION_SIZE;
        int endSectionY = (sectorY + LOOK_SIZE) / SECTION_SIZE;

        return getPlayersIds(startSectionX, startSectionY, endSectionX, endSectionY);
    }

    private List<Integer> getPlayersIds(final int startSectionX, final int startSectionY, final int endSectionX, final int endSectionY) {
        throw new NotImplementedException();
    }

}
