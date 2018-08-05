package org.rib.gamemap.game;

import lombok.extern.slf4j.Slf4j;
import org.rib.gamemap.model.Player;
import org.rib.gamemap.model.generator.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.rib.gamemap.common.Constants.Game.PLAYERS_COUNT;

@Component
@Slf4j
public class GameManager {

    private final long startTimeMs  = System.currentTimeMillis();

    @Autowired
    private PlayersMappingHelper mappingHelper;
    @Autowired
    private PlayerStorage playerStorage;

    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);


    @PostConstruct
    public void init() {
        regeneratePlayersOnMap(PLAYERS_COUNT);
        scheduler.scheduleAtFixedRate(new TaskGenerator(), 1, 1, TimeUnit.SECONDS);
    }

    private void regeneratePlayersOnMap(int playersCount) {
        mappingHelper.clearAllData();
        playerStorage.clearAllData();
        for (int i = 0; i < playersCount; i++) {
            Player player = DataGenerator.generateNextRandomPlayer();
            log.debug("Player generated: {}", player);
            saveNewPlayer(player);
        }
        printBigLogDelimiter();
    }


    private void saveNewPlayer(final Player player) {
        mappingHelper.addPlayer(player);
        playerStorage.addPlayer(player);
    }


    public class TaskGenerator implements Runnable {
        @Override
        public void run() {
            playerStorage.getPlayersMap().values().forEach((player) -> {
                player.getTaskList().generateNewTaskUsingTimeout();
            });

            log.debug("Tasks generated. Players list: ");
            playerStorage.getPlayersMap().values()
                    .forEach(player -> log.debug(playerStorage.toString()));
            printBigLogDelimiter();
        }
    }


    private void printBigLogDelimiter() {
        log.debug("*******");
        log.debug("*******");
        log.debug("*******");
    }

}
