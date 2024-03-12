package com.desai.demo.cassandra.controller;

import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;
import com.desai.demo.cassandra.service.GameLeaderboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/leaderboards")
@Slf4j
@Tag(name = "GameLeaderboard controller", description = "Game Leaderboard API")
public class GameLeaderboardController {
    @Autowired
    private GameLeaderboardService gameLeaderboardService;

    @Operation(summary = "Save seed Leaderboard data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved leaderboard data"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save() {
        log.info("*** Storing seed data in Cassandra ***");
        List<GameLeaderboard> list = gameLeaderboardService.save();
        log.info("Stored seed data in DB: {}", list);
    }

    @Operation(summary = "Get a Leaderboard by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Leaderboard",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GameLeaderboard.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Leaderboard not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{gameId}/{userId}")
    public ResponseEntity<GameLeaderboard> findById(@PathVariable String gameId, @PathVariable String userId) {
        LeaderboardKey key = LeaderboardKey.builder().gameId(gameId).userId(userId).build();
        log.info("*** Getting Leaderboard from DB using Key: {}", key);
        GameLeaderboard gameLeaderboard = gameLeaderboardService.findById(key);

        if (isNull(gameLeaderboard.getGameInfo())) {
            return ResponseEntity.notFound().build();
        }
        log.info("Found Leaderboard: {}", gameLeaderboard);
        return ResponseEntity.ok().body(gameLeaderboard);
    }

    @Operation(summary = "Get all Leaderboards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboards list",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = GameLeaderboard.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<GameLeaderboard>> findAll() {
        log.info("*** Getting Leaderboards from DB ***");
        List<GameLeaderboard> list = gameLeaderboardService.findAll();
        log.info("Leaderboards fetched from DB: {}", list);

        return ResponseEntity.ok().body(list);
    }
    
    @Operation(summary = "Create Leaderboard ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save Leaderboard",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GameLeaderboard.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GameLeaderboard> save(
            @Parameter(description = "GameLeaderboard object to save") @RequestBody GameLeaderboard gameLeaderboard) {
        log.info("*** Saving GameLeaderboard to DB: {}", gameLeaderboard);
        GameLeaderboard savedGameLeaderboard = gameLeaderboardService.save(gameLeaderboard);
        log.info("*** Saved GameLeaderboard to DB ***");

        return ResponseEntity.ok().body(savedGameLeaderboard);
    }

    @Operation(summary = "Update Leaderboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Leaderboard",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GameLeaderboard.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping
    public ResponseEntity<GameLeaderboard> update(@Parameter(description = "GameLeaderboard object to be updated") @RequestBody GameLeaderboard gameLeaderboard) {
        log.info("*** Updating Leaderboard: {}", gameLeaderboard);
        GameLeaderboard updatedGameLeaderboard = gameLeaderboardService.update(gameLeaderboard);
        log.info("*** Updated Leaderboard: {}", gameLeaderboard);

        return ResponseEntity.ok().body(updatedGameLeaderboard);
    }

    @Operation(summary = "Delete the Leaderboard by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the Leaderboard",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))})
    })
    @DeleteMapping("/{gameId}/{userId}")
    public ResponseEntity<String> delete(@PathVariable String gameId, @PathVariable String userId) {
        LeaderboardKey key = LeaderboardKey.builder().gameId(gameId).userId(userId).build();
        log.info("*** Deleting Leaderboard from DB for Key: {}", key);
        gameLeaderboardService.delete(key);
        log.info("*** Deleted Leaderboard from DB for Key: {}", key);

        return ResponseEntity.ok().body("Delete successful...!");
    }
}
