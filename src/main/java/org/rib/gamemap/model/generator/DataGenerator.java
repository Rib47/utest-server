package org.rib.gamemap.model.generator;

import lombok.experimental.UtilityClass;
import org.rib.gamemap.model.Player;

import java.util.Random;

import static org.rib.gamemap.common.Constants.Game.FIELD_SIZE;
import static org.rib.gamemap.common.Constants.Tasks.TASKS_MAX_COUNT;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MAX_TIMEOUT_SEC;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MAX_TIME_SEC;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MIN_TIMEOUT_SEC;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MIN_TIME_SEC;

@UtilityClass
public final class DataGenerator {

    private static Random random = new Random();
    private static int currId = 1;

    public static Player generateNextRandomPlayer() {
        int id = currId++;
        int x = random.nextInt(FIELD_SIZE);
        int y = random.nextInt(FIELD_SIZE);
        int taskTimeout = TASK_MIN_TIMEOUT_SEC + random.nextInt(TASK_MAX_TIMEOUT_SEC - TASK_MIN_TIMEOUT_SEC);
        return new Player(x, y, id, taskTimeout);
    }

    public static long generateTaskTimeSec() {
        return TASK_MIN_TIME_SEC + random.nextInt(TASK_MAX_TIME_SEC - TASK_MIN_TIME_SEC);
    }

    public static int generateTasksCount() {
        return 1 + random.nextInt(TASKS_MAX_COUNT);
    }

}
