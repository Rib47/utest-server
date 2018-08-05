package org.rib.gamemap.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {

    @UtilityClass
    public final class Game {
        public static final int FIELD_SIZE = 512;
        public static final int LOOK_SIZE = 32;
        public static final int PLAYERS_COUNT = 10; // 20_000;
        public static final int TASKS_MAX_COUNT = 4;

    }

    @UtilityClass
    public final class Tasks {
        public static final int TASKS_MAX_COUNT = 4;

        public static final int TASK_MIN_TIME_SEC = 10;      // 10 sec
        public static final int TASK_MAX_TIME_SEC = 10 * 60; // 10 min

        public static final int TASK_MIN_TIMEOUT_SEC = 1;       // 1 sec
        public static final int TASK_MAX_TIMEOUT_SEC = 10 * 60; // 10 min
    }

}
