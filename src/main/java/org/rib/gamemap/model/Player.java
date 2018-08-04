package org.rib.gamemap.model;

import lombok.Data;

import java.util.Random;

import static org.rib.gamemap.common.Constants.Game.FIELD_SIZE;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MAX_TIMEOUT_SEC;
import static org.rib.gamemap.common.Constants.Tasks.TASK_MIN_TIMEOUT_SEC;

@Data
public class Player {

    private static Random random = new Random();
    private static int currId = 1;


    private int id;
    // coordinates
    private int x = 0;
    private int y = 0;

    private int taskTimeout = TASK_MIN_TIMEOUT_SEC + random.nextInt(TASK_MAX_TIMEOUT_SEC - TASK_MIN_TIMEOUT_SEC);

    public Player(final int id, final int x, final int y, final int taskTimeout) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.taskTimeout = taskTimeout;
    }

    public static Player generateNextPlayer() {
        int id = currId++;
        int x = random.nextInt(FIELD_SIZE);
        int y = random.nextInt(FIELD_SIZE);
        int taskTimeout = TASK_MIN_TIMEOUT_SEC + random.nextInt(TASK_MAX_TIMEOUT_SEC - TASK_MIN_TIMEOUT_SEC);
        return new Player(x, y, id, taskTimeout);
    }


}
