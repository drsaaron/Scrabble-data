/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author scott
 */
public class Game extends ScrabbleData {

    private GameStatus gameStatus;
    private Date startTimestamp;
    private Date endTimestamp;
    private Integer winnerPlayerId;

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Integer getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Integer winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.gameStatus);
        hash = 59 * hash + Objects.hashCode(this.startTimestamp);
        hash = 59 * hash + Objects.hashCode(this.endTimestamp);
        hash = 59 * hash + Objects.hashCode(this.winnerPlayerId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        boolean superEquals = super.equals(obj);
        if (superEquals) {
            final Game other = (Game) obj;
            if (this.gameStatus != other.gameStatus) {
                return false;
            }
            if (!Objects.equals(this.startTimestamp, other.startTimestamp)) {
                return false;
            }
            if (!Objects.equals(this.endTimestamp, other.endTimestamp)) {
                return false;
            }
            return Objects.equals(this.winnerPlayerId, other.winnerPlayerId);
        } else {
            return superEquals;
        }
    }

}
