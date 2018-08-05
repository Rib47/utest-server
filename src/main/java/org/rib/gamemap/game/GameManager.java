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
import static org.rib.gamemap.common.Constants.Tasks.TASK_GENERATOR_PERIOD;

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
        scheduler.scheduleAtFixedRate(new TaskGenerator(), 1, TASK_GENERATOR_PERIOD, TimeUnit.SECONDS);
    }

    private void regeneratePlayersOnMap(int playersCount) {
        mappingHelper.clearAllData();
        playerStorage.clearAllData();
        for (int i = 0; i < playersCount; i++) {
            Player player = DataGenerator.generateNextRandomPlayer();
            saveNewPlayer(player);
            player.getTaskList().generateNewTasks(DataGenerator.generateTasksCount());
            log.debug("Player generated: {}", player);
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
            try {
                playerStorage.getPlayersMap().values().forEach((player) -> {
                    try {
                        player.getTaskList().generateNewTaskUsingTimeout();
                    } catch (Exception ex) {
                        log.trace("Error message while generating new task: {}", ex.getMessage());
                    }
                });

                log.debug("Tasks generated. Players list: ");
                playerStorage.getPlayersMap().values()
                        .forEach(player -> log.debug(player.toString()));
                printBigLogDelimiter();
            } catch (Exception ex) {
                log.trace("Error message: {}", ex.getMessage());
            }
        }
    }


    private void printBigLogDelimiter() {
        log.debug("*******");
        log.debug("*******");
        log.debug("*******");
    }

}
