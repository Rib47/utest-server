package org.rib.gamemap.service;

import org.rib.gamemap.game.PlayerStorage;
import org.rib.gamemap.game.PlayersMappingHelper;
import org.rib.gamemap.model.Player;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.rib.gamemap.common.Constants.Game.LOOK_SIZE;

public class PlayerService {

    @Autowired
    private PlayersMappingHelper mappingHelper;
    @Autowired
    private PlayerStorage playerStorage;

    public List<Player> getPlayersForLookArea(int lookAreaTopX, int lookAreaTopY) {
        List<Integer> playerIds =
                mappingHelper.getPlayerIdsForLookArea(lookAreaTopX, lookAreaTopY, lookAreaTopX + LOOK_SIZE, lookAreaTopY + LOOK_SIZE);
        return playerStorage.getPlayersByIds(playerIds);
    }



}
