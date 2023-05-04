/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app.access;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.GameStatus;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.entity.GameEntity;
import com.blazartech.scrabble.data.entity.GamePlayerEntity;
import com.blazartech.scrabble.data.entity.GamePlayerRoundEntity;
import com.blazartech.scrabble.data.entity.PlayerEntity;
import com.blazartech.scrabble.data.entity.repos.GameEntityRepository;
import com.blazartech.scrabble.data.entity.repos.GamePlayerEntityRepository;
import com.blazartech.scrabble.data.entity.repos.GamePlayerRoundEntityRepository;
import com.blazartech.scrabble.data.entity.repos.PlayerEntityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

    private GameEntity buildGameEntity(Game g) {
        GameEntity ge = new GameEntity();
        ge.setEndDtm(g.getEndTimestamp());
        ge.setStartDtm(g.getStartTimestamp());
        ge.setStsCde(g.getGameStatus().getDBValue());
        ge.setGameId(g.getId());

        return ge;
    }
    
    @Override
    public List<Game> getAllGames() {
        log.info("getting all games");
        
        Collection<GameEntity> gameEntities = gameRepository.findAll();
        List<Game> games = gameEntities.stream()
                .map(ge -> buildGame(Optional.of(ge)))
                .collect(Collectors.toList());
        return games;
    }

    @Override
    public Game addGame(Game g) {
        log.info("adding game {}", g);

        // sanity chekc that ID is not set.
        if (g.getId() != null) {
            throw new IllegalStateException("attemping to add a game with a set ID");
        }

        // sanity check on dates
        if (g.getStartTimestamp() != null) {
            throw new IllegalStateException("start date must be null for new game");
        }
        if (g.getEndTimestamp() != null) {
            throw new IllegalStateException("end date must be null for new game");
        }

        GameEntity ge = buildGameEntity(g);

        // set the create timestamp, I'm not sure why this is needed, but for now....    
        ge.setStartDtm(new Date());

        // save
        gameRepository.save(ge);
        log.info("saved game {}", ge);

        // get the updated data
        return buildGame(Optional.of(ge));
    }

    @Override
    public void updateGame(Game g) {
        log.info("updating game {}", g);

        // sanity chekc that ID is set.
        if (g.getId() == null) {
            throw new IllegalStateException("attemping to update a game without a set ID");
        }

        GameEntity ge = buildGameEntity(g);
        gameRepository.save(ge);
    }

    @Override
    public GamePlayer getGamePlayer(int id) {
        log.info("getting game player {}", id);
        
        Optional<GamePlayerEntity> gpe = gamePlayerRepository.findById(id);
        if (gpe.isPresent()) {
            return buildGamePlayer(gpe.get());
        }
        return null;
    }
    
    @Override 
    public GamePlayer addGamePlayer(GamePlayer gamePlayer) {
        log.info("adding game player {}", gamePlayer);
        
        GamePlayerEntity gpe = new GamePlayerEntity();
        buildGamePlayerEntity(gamePlayer, gpe);
        
        gamePlayerRepository.save(gpe);
        
        gamePlayer.setId(gpe.getGamePlayerId());
        
        return gamePlayer;
    }
    
    @Override
    public void updateGamePlayer(GamePlayer gamePlayer) {
        log.info("updating game player {}", gamePlayer);
        
        Optional<GamePlayerEntity> gpeo = gamePlayerRepository.findById(gamePlayer.getId());
        if (gpeo.isPresent()) {
            GamePlayerEntity gpe = gpeo.get();
        
            buildGamePlayerEntity(gamePlayer, gpe);
            gamePlayerRepository.save(gpe);
        }
    }
    
    private void buildGamePlayerEntity(GamePlayer gp, GamePlayerEntity gpe) {
        
        gpe.setScoreCnt(gp.getScore());
        gpe.setOrderSeq(gp.getSequenceNumber());
        
        if (gpe.getPlayerId() == null) {
            Optional<PlayerEntity> pe = playerRepository.findById(gp.getPlayerId());
            gpe.setPlayerId(pe.get());
        }
        
        if (gpe.getGameId() == null) {
            Optional<GameEntity> ge = gameRepository.findById(gp.getGameId());
            gpe.setGameId(ge.get());
        }
    }
    
    private GamePlayer buildGamePlayer(GamePlayerEntity gpe) {
        GamePlayer player = new GamePlayer();
        player.setGameId(gpe.getGameId().getGameId());
        player.setId(gpe.getGamePlayerId());
        player.setPlayerId(gpe.getPlayerId().getPlayerId());
        player.setScore(gpe.getScoreCnt());
        player.setSequenceNumber(gpe.getOrderSeq());
        return player;
    }

    @Override
    public List<GamePlayer> getPlayersForGame(int gameId) {
        log.info("getting players for game {}", gameId);

        Optional<GameEntity> ge = gameRepository.findById(gameId);
        if (ge.isPresent()) {
            Collection<GamePlayerEntity> playersE = ge.get().getGamePlayerCollection();
            if (playersE != null) {
                List<GamePlayer> players = playersE.stream()
                        .map(e -> buildGamePlayer(e))
                        .sorted((p1, p2) -> Integer.compare(p1.getSequenceNumber(), p2.getSequenceNumber()))
                        .collect(Collectors.toList());
                return players;
            }
        }
        return new ArrayList<>();
    }

    private Player buildPlayer(Optional<PlayerEntity> peo) {

        if (peo.isPresent()) {
            Player p = new Player();
            PlayerEntity pe = peo.get();

            GameEntity highGame = pe.getHighGameId();
            p.setHighGameId(highGame != null ? highGame.getGameId() : null);
            p.setId(pe.getPlayerId());
            p.setName(pe.getNameTxt());

            return p;
        } else {
            return null;
        }
    }

    @Override
    public Player getPlayer(int playerId) {
        log.info("getting player {}", playerId);

        Optional<PlayerEntity> pe = playerRepository.findById(playerId);
        return buildPlayer(pe);
    }

    @Override 
    public List<Player> getPlayers() {
        log.info("getting all players");
        
        List<PlayerEntity> allPlayerEntities = playerRepository.findAll();
        List<Player> players = allPlayerEntities.stream()
                .map(pe -> buildPlayer(Optional.of(pe)))
                .collect(Collectors.toList());
        return players;
    }
    
    @Override
    public Player addPlayer(Player player) {
        log.info("adding player {}", player);
        
        // sanity check
        if (player.getId() != null) {
            throw new IllegalStateException("player already has an ID");
        }
        
        PlayerEntity pe = new PlayerEntity();
        updatePlayerEntity(player, pe);
        
        playerRepository.save(pe);
        
        player.setId(pe.getPlayerId());
        
        return player;
    }
    
    private void updatePlayerEntity(Player p, PlayerEntity pe) {
        GameEntity highGame = null;
        if (p.getHighGameId() != null) {
            highGame = gameRepository.findById(p.getHighGameId()).get();
        }
        pe.setHighGameId(highGame);
        pe.setNameTxt(p.getName());
    }

    @Override
    public void updatePlayer(Player player) {
        log.info("updating player {}", player);

        Optional<PlayerEntity> peo = playerRepository.findById(player.getId());
        if (peo.isEmpty()) {
            throw new IllegalStateException("updating player " + player.getId() + " who is not found");
        }

        PlayerEntity pe = peo.get();
        updatePlayerEntity(player, pe);

        playerRepository.save(pe);
    }

    @Override
    public GamePlayer getPlayerForGame(int gameId, int playerId) {
        log.info("getting player {} for game {}", playerId, gameId);

        Collection<GamePlayerEntity> gamePlayers = gamePlayerRepository.findByGameAndPlayer(gameId, playerId);
        if (gamePlayers == null || gamePlayers.isEmpty()) {
            return null;
        } else {
            GamePlayerEntity gpe = gamePlayers.iterator().next();
            return buildGamePlayer(gpe);
        }
    }

    private GamePlayerRoundEntity buildGamePlayerRoundEntity(GamePlayerRound round) {
        GamePlayerRoundEntity gpre = new GamePlayerRoundEntity();

        Optional<GamePlayerEntity> gpe = gamePlayerRepository.findById(round.getGamePlayerId());

        gpre.setGamePlayerId(gpe.get());
        gpre.setNoteTxt(round.getNotes());
        gpre.setRowCreateDtm(new Date());
        gpre.setScoreCnt(round.getScore());
        gpre.setSvnLtrInd(round.isSevenLetter() ? 'Y' : 'N');
        
        return gpre;
    }

    @Override
    public GamePlayerRound addGamePlayerRound(GamePlayerRound round) {
        log.info("adding player round {}", round);

        GamePlayerRoundEntity gpre = buildGamePlayerRoundEntity(round);
        gamePlayerRoundRepository.save(gpre);
        
        round.setId(gpre.getGamePlayerRoundId());
        
        return round;
    }
}
