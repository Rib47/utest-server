package org.rib.gamemap.game;

import lombok.Getter;
import org.rib.gamemap.model.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.rib.gamemap.common.Constants.Game.PLAYERS_COUNT;

@Component
@Getter
public class PlayerStorage {

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

    public List<Player> getPlayersByIds(List<Integer> playerIds) {

        return playerIds
                .parallelStream()
                .map(id -> playersMap.get(id))
                .collect(Collectors.toList());

        //        List<Player> players = new ArrayList<>(playerIds.size());
        //        playerIds.forEach(id -> players.add(playersMap.get(id)));
        //        return players;
    }



}
