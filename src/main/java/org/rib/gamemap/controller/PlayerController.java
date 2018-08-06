package org.rib.gamemap.controller;

import org.rib.gamemap.model.Player;
import org.rib.gamemap.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.rib.gamemap.common.Constants.Rest.PLAYERS_PATH;
import static org.rib.gamemap.common.Constants.Rest.PLAYER_TASK_PATH;
import static org.rib.gamemap.common.Constants.Rest.VERSION;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = VERSION)
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @ResponseStatus(OK)
    @GetMapping(path = PLAYERS_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getPlayersFromSector(@RequestParam Integer x, @RequestParam Integer y) {
        List<Player> playerList = playerService.getPlayersForLookArea(x, y);
        return ResponseEntity.ok(playerList);
    }

    @ResponseStatus(OK)
    @GetMapping(path = PLAYERS_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getRandomPlayers(@RequestParam Integer count) {
        List<Player> playerList = playerService.getRandomPlayers(count);
        return ResponseEntity.ok(playerList);
    }

    @PostMapping(path = PLAYER_TASK_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createTasks(@PathVariable Integer playerId, @RequestParam(required = false, defaultValue = "1") Integer taskCount) {
        int count = playerService.createTasksForPlayer(playerId, taskCount);
        Player player = playerService.getPlayer(playerId);
        if (count == 0) {
            return ResponseEntity.status(NOT_MODIFIED).body(player);
        }
        return ResponseEntity.ok(player);
    }


    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = PLAYER_TASK_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void stopAllTasks(@PathVariable Integer playerId) {
        playerService.stopAllTasksForPlayer(playerId);
    }


}
