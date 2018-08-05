package org.rib.gamemap.model;

import lombok.Data;

@Data
public class Player {
    private int id;
    private int x = 0;
    private int y = 0;

    private TaskList taskList;

    public Player(final int id, final int x, final int y, final int taskTimeoutSec) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.taskList = new TaskList(taskTimeoutSec);
    }

}
