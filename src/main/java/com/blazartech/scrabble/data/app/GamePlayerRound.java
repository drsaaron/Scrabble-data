/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.app;

import java.util.Objects;

/**
 *
 * @author scott
 */
public class GamePlayerRound extends ScrabbleData {

    private int gamePlayerId;
    private int score;

    private boolean sevenLetter;
    private String notes;

    private Integer round;

    private Integer rollingScore;

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSevenLetter() {
        return sevenLetter;
    }

    public void setSevenLetter(boolean isSevenLetter) {
        this.sevenLetter = isSevenLetter;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getRollingScore() {
        return rollingScore;
    }

    public void setRollingScore(Integer rollingScore) {
        this.rollingScore = rollingScore;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.gamePlayerId;
        hash = 11 * hash + this.score;
        hash = 11 * hash + (this.sevenLetter ? 1 : 0);
        hash = 11 * hash + Objects.hashCode(this.notes);
        hash = 11 * hash + Objects.hashCode(this.round);
        hash = 11 * hash + Objects.hashCode(this.rollingScore);
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
            final GamePlayerRound other = (GamePlayerRound) obj;
            if (this.gamePlayerId != other.gamePlayerId) {
                return false;
            }
            if (this.score != other.score) {
                return false;
            }
            if (this.sevenLetter != other.sevenLetter) {
                return false;
            }
            if (!Objects.equals(this.notes, other.notes)) {
                return false;
            }
            if (!Objects.equals(this.round, other.round)) {
                return false;
            }
            return Objects.equals(this.rollingScore, other.rollingScore);
        } else {
            return superEquals;
        }
    }

    @Override
    public String toString() {
        return "GamePlayerRound{" + "id=" + getId() + ", gamePlayerId=" + gamePlayerId + ", score=" + score + ", sevenLetter=" + sevenLetter + ", notes=" + notes + ", round=" + round + ", rollingScore=" + rollingScore + '}';
    }

}
