/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.entity.GameEntity;
import com.blazartech.scrabble.data.entity.GamePlayerEntity;
import com.blazartech.scrabble.data.entity.PlayerEntity;
import com.blazartech.scrabble.data.entity.repos.GameEntityRepository;
import com.blazartech.scrabble.data.entity.repos.GamePlayerEntityRepository;
import com.blazartech.scrabble.data.entity.repos.GamePlayerRoundEntityRepository;
import com.blazartech.scrabble.data.entity.repos.PlayerEntityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author scott
 */
@Service
@Slf4j
public class ScrabbleDataAccessImpl implements ScrabbleDataAccess {

    @Autowired
    private GameEntityRepository gameRepository;

    @Autowired
    private GamePlayerEntityRepository gamePlayerRepository;

    @Autowired
    private GamePlayerRoundEntityRepository gamePlayerRoundRepository;

    @Autowired
    private PlayerEntityRepository playerRepository;

    private Game buildGame(Optional<GameEntity> ge) {
        if (ge.isPresent()) {
            GameEntity g = ge.get();

            Game game = new Game();
            game.setEndTimestamp(g.getEndDtm());
            game.setGameStatus(GameStatus.findByDBValue(g.getStsCde()));
            game.setId(g.getGameId());
            game.setStartTimestamp(g.getStartDtm());

            PlayerEntity winner = g.getWinnerId();
            game.setWinnerPlayerId(winner != null ? winner.getPlayerId() : null);

            return game;
        } else {
            return null;
        }
    }

    @Override
    public Game getGame(int gameId) {
        log.info("getting game {}", gameId);

        Optional<GameEntity> ge = gameRepository.findById(gameId);
        return buildGame(ge);
    }

    private GamePlayer buildGamePlayer(GamePlayerEntity gpe) {
        GamePlayer player = new GamePlayer();
        player.setGameId(gpe.getGameId().getGameId());
        player.setId(gpe.getGamePlayerId());
        player.setPlayerId(gpe.getGamePlayerId());
        player.setScore(gpe.getScoreCnt());
        player.setSequenceNumber(gpe.getOrderSeq());
        return player;
    }

    @Override
    public Collection<GamePlayer> getPlayersForGame(int gameId) {
        log.info("getting players for game {}", gameId);

        Optional<GameEntity> ge = gameRepository.findById(gameId);
        if (ge.isPresent()) {
            Collection<GamePlayerEntity> playersE = ge.get().getGamePlayerCollection();
            Collection<GamePlayer> players = playersE.stream()
                    .map(e -> buildGamePlayer(e))
                    .sorted((p1, p2) -> Integer.compare(p1.getSequenceNumber(), p2.getSequenceNumber()))
                    .collect(Collectors.toList());
            return players;
        }
        return new ArrayList<>();
    }
}
