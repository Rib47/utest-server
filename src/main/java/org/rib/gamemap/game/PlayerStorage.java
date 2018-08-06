package org.rib.gamemap.game;

import lombok.Getter;
import org.rib.gamemap.model.Player;
import org.rib.gamemap.model.generator.DataGenerator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static org.rib.gamemap.common.Constants.Game.PLAYERS_COUNT;

@Component
@Getter
public class PlayerStorage {

    private static Random random = new Random();

    // Player-ID to player-object map
    private Map<Integer, Player> playersMap;


    public PlayerStorage() {
        clearAllData();
    }


    public void clearAllData() {
        playersMap = new HashMap<>(PLAYERS_COUNT);
    }

    public void addPlayer(Player player) {
        playersMap.put(player.getId(), player);
    }

    public Player getPlayer(Integer playerId) {
        return playersMap.get(playerId);
    }

    public List<Player> getPlayersByIds(List<Integer> playerIds) {
        return playerIds
                .parallelStream()
                .map(id -> playersMap.get(id))
                .collect(toList());
    }

    public List<Player> getRandomPlayers(int count) {
        int randomOffset = DataGenerator.generateRandomPlayersOffset(count);
        return playersMap.values()
                .stream()
                .skip(randomOffset)
                .limit(count)
                .collect(toList());
    }

}
