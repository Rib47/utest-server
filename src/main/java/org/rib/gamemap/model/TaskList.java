package org.rib.gamemap.model;

import lombok.Getter;
import lombok.ToString;
import org.rib.gamemap.model.generator.DataGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.rib.gamemap.common.Constants.Game.TASKS_MAX_COUNT;

@Getter
@ToString
public class TaskList {

    // stores time of task end (unix time, sec), or null if task not active
    private List<Long> tasks = new ArrayList<>(TASKS_MAX_COUNT);
    private int taskGenerationTimeoutSec;

    private long lastGenerationTimeSec = 0;
    private int freeTaskSlotsCount = TASKS_MAX_COUNT;


    public TaskList(final int taskGenerationTimeoutSec) {
        this.taskGenerationTimeoutSec = taskGenerationTimeoutSec;
        // reset to not start all players tasks at the server startup
        lastGenerationTimeSec = System.currentTimeMillis() / 1000;
    }


    // task will be generated only if timeout is reached, for internal generation
    public void generateNewTaskUsingTimeout() {
        long currTime = System.currentTimeMillis() / 1000;
        clearFinishedTasks(currTime);
        addTask(currTime, true);
    }

    // don't care about timeout, for external control
    public void generateNewTasks(int count) {
        long currTime = System.currentTimeMillis() / 1000;
        clearFinishedTasks(currTime);
        for (int i = 0; i < count; i++) {
            if (freeTaskSlotsCount > 0) {
                addTask(currTime, false);
            } else {
                return;
            }
        }
    }

    // get current tasks
    public List<Long> getTasks() {
        return new ArrayList<>(tasks);
    }

    // stop and clear all tasks
    public void stopAllTasks() {
        for (int i = 0; i < TASKS_MAX_COUNT; i++) {
            tasks.set(i, null);
        }
        freeTaskSlotsCount = TASKS_MAX_COUNT;
    }

    private boolean addTask(final long currTime, final boolean useTimeout) {
        if (freeTaskSlotsCount > 0) {
            long timeAfterLastGeneration = currTime - lastGenerationTimeSec;
            if ((!useTimeout) || (timeAfterLastGeneration >= taskGenerationTimeoutSec)) {
                long taskTime = DataGenerator.generateTaskTime();
                for (int taskIndex = 0; taskIndex < TASKS_MAX_COUNT; taskIndex++) {
                    Long taskEndTime = tasks.get(taskIndex);
                    if (taskEndTime == null) {
                        tasks.set(taskIndex, currTime + taskTime);
                        freeTaskSlotsCount--;
                        lastGenerationTimeSec = currTime;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void clearFinishedTasks(final long currTimeSec) {
        int freeSlots = 0;
        if (freeTaskSlotsCount < TASKS_MAX_COUNT) {
            for (int taskIndex = 0; taskIndex < TASKS_MAX_COUNT; taskIndex++) {
                Long taskEndTime = tasks.get(taskIndex);
                if (taskEndTime != null) {
                    if ((taskEndTime - currTimeSec) <= 0) {  // task is finished
                        tasks.set(taskIndex, null);
                        freeSlots++;
                    }
                } else {
                    freeSlots++;
                }
            }
        }
        freeTaskSlotsCount = freeSlots;
    }

}
