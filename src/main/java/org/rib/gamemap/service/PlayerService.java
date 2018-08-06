package org.rib.gamemap.service;

import org.rib.gamemap.game.PlayerStorage;
import org.rib.gamemap.game.PlayersMappingHelper;
import org.rib.gamemap.model.Player;
import org.rib.gamemap.model.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.rib.gamemap.common.Constants.Game.LOOK_SIZE;

@Service
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

    public List<Player> getRandomPlayers(int count) {
        return playerStorage.getRandomPlayers(count);
    }

    public Player getPlayer(int playerId) {
        return playerStorage.getPlayer(playerId);
    }

    public void stopAllTasksForPlayer(int playerId) {
        playerStorage.getPlayer(playerId).getTaskList().stopAllTasks();
    }


    public int createTasksForPlayer(int playerId, int taskCount) {
        TaskList taskList = playerStorage.getPlayer(playerId).getTaskList();
        return taskList.generateNewTasks(taskCount);
    }

}
