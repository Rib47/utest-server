package org.rib.gamemap.model;

import lombok.Getter;
import org.rib.gamemap.model.generator.DataGenerator;

import java.util.Arrays;
import java.util.List;

import static org.rib.gamemap.common.Constants.Tasks.TASKS_MAX_COUNT;

@Getter
public class TaskList {

    // stores time of task end (unix time, sec), or null if task not active
    private Long[] tasks = new Long[TASKS_MAX_COUNT];
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
        return Arrays.asList(tasks);
    }

    // stop and clear all tasks
    public void stopAllTasks() {
        for (int i = 0; i < TASKS_MAX_COUNT; i++) {
            stopTask(i);
        }
        freeTaskSlotsCount = TASKS_MAX_COUNT;
    }

    private boolean addTask(final long currTime, final boolean useTimeout) {
        if (freeTaskSlotsCount > 0) {
            long timeAfterLastGeneration = currTime - lastGenerationTimeSec;
            if ((!useTimeout) || (timeAfterLastGeneration >= taskGenerationTimeoutSec)) {
                long taskTime = DataGenerator.generateTaskTimeSec();
                for (int taskIndex = 0; taskIndex < TASKS_MAX_COUNT; taskIndex++) {
                    if (tasks[taskIndex] == null) {
                        long taskEndTime =  currTime + taskTime;
                        createTask(taskIndex, taskEndTime);
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
                Long taskEndTime = tasks[taskIndex];
                if (taskEndTime != null) {
                    if ((taskEndTime - currTimeSec) <= 0) {  // task is finished
                        stopTask(taskIndex);
                        freeSlots++;
                    }
                } else {
                    freeSlots++;
                }
            }
            freeTaskSlotsCount = freeSlots;
        } else {
            freeTaskSlotsCount = TASKS_MAX_COUNT;  // correction if any troubles
        }

    }

    private void stopTask(final int taskIndex) {
        tasks[taskIndex] = null;
    }

    private void createTask(final int taskIndex, long endTime) {
        tasks[taskIndex] = new Long(endTime);
    }

    @Override
    public String toString() {
        long currTime = System.currentTimeMillis() / 1000;

        final StringBuilder sb = new StringBuilder("TaskList{");

        sb.append("tasks=[");
        for (int i = 0; i < 4; i++) {
            if (tasks[i] != null) {
                sb.append(tasks[i] - currTime);
            } else {
                sb.append("null");
            }
            if (i < 3) {
                sb.append(", ");
            }
        }
        sb.append("]");

        sb.append(", taskGenerationTimeoutSec=").append(taskGenerationTimeoutSec);
        sb.append(", lastGenerationTimeSec=").append(lastGenerationTimeSec);
        sb.append(", freeTaskSlotsCount=").append(freeTaskSlotsCount);
        sb.append('}');
        return sb.toString();
    }
}
