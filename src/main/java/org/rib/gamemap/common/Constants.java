package org.rib.gamemap.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {

    @UtilityClass
    public final class Game {
        public static final int FIELD_SIZE = 512;
        public static final int LOOK_SIZE = 32;
        public static final int PLAYERS_COUNT = 10; // 20_000;

    }

    @UtilityClass
    public final class Tasks {
        public static final int TASKS_MAX_COUNT = 4;

        public static final int TASK_MIN_TIME_SEC = 10;  // 10 sec
        public static final int TASK_MAX_TIME_SEC = 30;  // 10 * 60; // 10 min

        public static final int TASK_MIN_TIMEOUT_SEC = 1;       // 1 sec
        public static final int TASK_MAX_TIMEOUT_SEC = 30;   // 10 * 60; // 10 min

        public static final int TASK_GENERATOR_PERIOD = 1;   // 1;
    }


    @UtilityClass
    public final class Rest {
        public static final String VERSION = "/v1";

        public static final String PLAYERS_PATH = "/players";
        public static final String PLAYER_ID_PATH = "/{playerId}";
        public static final String PLAYER_ID_PLACEHOLDER = "{playerId}";
        public static final String PLAYER_TASK_PATH = PLAYERS_PATH + PLAYER_ID_PATH + "/tasks";
    }


}
