/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.controller;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import com.blazartech.scrabble.data.process.AddScorePAB;
import com.blazartech.scrabble.data.process.GameCompletePAB;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author scott
 */
@RestController
@Slf4j
@OpenAPIDefinition(info = @Info(
        title = "data access services scrabble score tracking",
        version = "1.0"
))
public class ScrabbleDataController {

    @Autowired
    private ScrabbleDataAccess dal;

    @PostMapping("/player")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add a new player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "newly added player",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
                })
    })
    public Player addPlayer(@RequestBody Player player) {

        log.info("adding player {}", player);

        dal.addPlayer(player);

        return player;
    }

    @GetMapping("/player")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get a list of all the available players")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "list of players",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class))
                    )
                })
    })
    public List<Player> getAllPlayers() {

        log.info("getting all players");
        return dal.getPlayers();
    }

    @GetMapping("/player/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get a specific player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the player",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)
                    )
                })
    })
    public Player getPlayer(@Parameter(description = "player ID") @PathVariable int id) {
        log.info("getting player {}", id);

        return dal.getPlayer(id);
    }

    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add a new game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "newly added game",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Game.class)
                    )
                })
    })
    public Game addGame(@RequestBody Game g) {
        log.info("adding game {}", g);

        g = dal.addGame(g);
        return g;
    }

    @GetMapping("/game")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get list of games")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "list of games",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Game.class))
                    )
                })
    })
    public List<Game> getAllGames() {
        log.info("getting all games");
        return dal.getAllGames();
    }

    @GetMapping("/game/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get specific game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the game",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Game.class)
                    )
                })
    })
    public Game getGame(@Parameter(description = "game ID") @PathVariable int id) {
        log.info("getting game {}", id);
        return dal.getGame(id);
    }

    @Autowired
    private GameCompletePAB gameComplete;

    @PatchMapping(path = "/game/{id}", consumes = "application/json-patch+json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "mark a game complete")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the updated game",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Game.class)
                    )
                })
    })
    public Game markGameComplete(@Parameter(description = "game ID") @PathVariable int id, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        log.info("updating game {}", id);

        Game game = dal.getGame(id);
        Game updatedGame = applyPatchToGame(patch, game);
        
        // sanity check
        if (game.getGameStatus() == GameStatus.Playing && updatedGame.getGameStatus() == GameStatus.Complete) {
            gameComplete.markGameComplete(game);
            return game;
        } else {
            dal.updateGame(updatedGame);
            return updatedGame;
        }
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private Game applyPatchToGame(JsonPatch patch, Game game) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(game, JsonNode.class));
        return objectMapper.treeToValue(patched, Game.class);
    }

    @PostMapping("/gamePlayer")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create a game player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "the new game player",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GamePlayer.class)
                    )
                })
    })
    public GamePlayer addGamePlayer(@RequestBody GamePlayer gamePlayer) {
        log.info("adding gamePlayer {}", gamePlayer);

        return dal.addGamePlayer(gamePlayer);
    }

    @GetMapping("/game/{gameId}/gamePlayer")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get list of players for a game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the list of game players",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GamePlayer.class))
                    )
                })
    })
    public List<GamePlayer> getGamePlayersForGame(@Parameter(description = "game ID") @PathVariable int gameId) {
        log.info("getting players for game " + gameId);
        return dal.getGamePlayersForGame(gameId);
    }

    @GetMapping("/game/{gameId}/gamePlayer/{sequenceId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get a specific player in a game according to their order number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the game player",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GamePlayer.class))
                    )
                })
    })
    public GamePlayer getGamePlayerForGame(@Parameter(description = "game ID") @PathVariable int gameId, @Parameter(description = "player sequence number within the game") @PathVariable int sequenceId) {
        log.info("getting player {} for game {}", sequenceId, gameId);
        return dal.getGamePlayerForGameBySequence(gameId, sequenceId);
    }

    @Autowired
    private AddScorePAB addScorePAB;

    @PostMapping("/gamePlayerRound")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create a game player round")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "the new game player found",
                content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GamePlayerRound.class)
                    )
                })
    })
    public GamePlayerRound addGamePlayerRound(@RequestBody GamePlayerRound gamePlayerRound) {
        log.info("adding game player round {}", gamePlayerRound);
        addScorePAB.addScoreToGame(gamePlayerRound);
        return gamePlayerRound;
    }

    @GetMapping("/game/{gameId}/gamePlayer/{sequenceId}/gamePlayerRound")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get a game player'rounds within a game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "the game player rounds",
                content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GamePlayerRound.class))
                    )
                })
    })
    public List<GamePlayerRound> getGamePlayerRoundsForGamePlayer(@Parameter(description = "game ID") @PathVariable(required = true) int gameId, @Parameter(description = "player sequence number within the game") @PathVariable int sequenceId) {
        log.info("getting player rounds for game player sequence number {} in game {}", sequenceId, gameId);

        return dal.getGamePlayerRoundsForGameAndSequence(gameId, sequenceId);
    }
}
