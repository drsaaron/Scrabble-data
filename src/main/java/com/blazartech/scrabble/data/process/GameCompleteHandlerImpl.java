/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.process;

import com.blazartech.scrabble.data.app.Game;
import com.blazartech.scrabble.data.app.GamePlayer;
import com.blazartech.scrabble.data.app.GamePlayerRound;
import com.blazartech.scrabble.data.app.Player;
import com.blazartech.scrabble.data.app.access.ScrabbleDataAccess;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class GameCompleteHandlerImpl implements GameCompleteHandler {

    @Autowired
    private ScrabbleDataAccess dal;

    public int getTotalScore(Collection<GamePlayerRound> rounds) {
        return rounds.stream()
                .map(r -> r.getScore())
                .collect(Collectors.summingInt(Integer::intValue));
    }

    public boolean isNewHighScore(Player p, int score) {
        if (p.getHighGameId() != null) {
            log.info("previous high game: {}", p.getHighGameId());
            GamePlayer highGamePlayer = dal.getGamePlayerForGame(p.getHighGameId(), p.getId());
            log.info("previous high score: {}", highGamePlayer.getScore());
            return score > highGamePlayer.getScore();
        } else { // no existing high game, so by definition this is the high game
            log.info("no previous high game");
            return true;
        }
    }

    @Override
    @Transactional
    public void handleGameComplete(Game game) {
        log.info("handling game complete for game {}", game);

        // set the final score and then determine if it's their highest.  decouple teh two?
        Collection<GamePlayer> players = dal.getGamePlayersForGame(game.getId());
        for (GamePlayer gamePlayer : players) {
            Player p = dal.getPlayer(gamePlayer.getPlayerId());

            // get the rounds for the game for the player
            Collection<GamePlayerRound> rounds = dal.getGamePlayerRoundsForGamePlayer(gamePlayer.getPlayerId());

            // accumulate the score
            int score = getTotalScore(rounds);
            log.info("got total score {}", score);
            
            // update the score
            gamePlayer.setScore(score);
            dal.updateGamePlayer(gamePlayer);
            log.info("updated game player {}", gamePlayer);

            // is this their new high score?
            if (isNewHighScore(p, score)) {
                // new high score
                log.info("new high score for player {}", p);
                p.setHighGameId(game.getId());
                dal.updatePlayer(p);
            }
        }
    }

}
