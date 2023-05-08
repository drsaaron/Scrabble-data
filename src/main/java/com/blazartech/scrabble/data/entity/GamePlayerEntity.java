/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author scott
 */
@Entity
@Table(name = "GamePlayer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GamePlayerEntity.findAll", query = "SELECT g FROM GamePlayerEntity g"),
    @NamedQuery(name = "GamePlayerEntity.findByGamePlayerId", query = "SELECT g FROM GamePlayerEntity g WHERE g.gamePlayerId = :gamePlayerId"),
    @NamedQuery(name = "GamePlayerEntity.findByScoreCnt", query = "SELECT g FROM GamePlayerEntity g WHERE g.scoreCnt = :scoreCnt"),
    @NamedQuery(name = "GamePlayerEntity.findByOrderSeq", query = "SELECT g FROM GamePlayerEntity g WHERE g.orderSeq = :orderSeq"),
    @NamedQuery(name = "GamePlayerEntity.findByGameId", query = "SELECT g FROM GamePlayerEntity g where g.gameId.gameId = :gameId"),
    @NamedQuery(name = "GamePlayerEntity.findByGameAndPlayer", query = "SELECT g from GamePlayerEntity g where g.gameId.gameId = :gameId and g.playerId.playerId = :playerId")
})
public class GamePlayerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GamePlayerId")
    private Integer gamePlayerId;
    @Basic(optional = true)
    @Column(name = "ScoreCnt", columnDefinition = "int default 0")
    private int scoreCnt;
    @Basic(optional = false)
    @Column(name = "OrderSeq")
    private int orderSeq;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gamePlayerId")
    private Collection<GamePlayerRoundEntity> gamePlayerRoundCollection;
    @JoinColumn(name = "GameId", referencedColumnName = "GameId")
    @ManyToOne(optional = false)
    private GameEntity gameId;
    @JoinColumn(name = "PlayerId", referencedColumnName = "PlayerId")
    @ManyToOne(optional = false)
    private PlayerEntity playerId;

    public GamePlayerEntity() {
    }

    public GamePlayerEntity(Integer gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public GamePlayerEntity(Integer gamePlayerId, int scoreCnt, int orderSeq) {
        this.gamePlayerId = gamePlayerId;
        this.scoreCnt = scoreCnt;
        this.orderSeq = orderSeq;
    }

    public Integer getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(Integer gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public int getScoreCnt() {
        return scoreCnt;
    }

    public void setScoreCnt(int scoreCnt) {
        this.scoreCnt = scoreCnt;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    @XmlTransient
    public Collection<GamePlayerRoundEntity> getGamePlayerRoundCollection() {
        return gamePlayerRoundCollection;
    }

    public void setGamePlayerRoundCollection(Collection<GamePlayerRoundEntity> gamePlayerRoundCollection) {
        this.gamePlayerRoundCollection = gamePlayerRoundCollection;
    }

    public GameEntity getGameId() {
        return gameId;
    }

    public void setGameId(GameEntity gameId) {
        this.gameId = gameId;
    }

    public PlayerEntity getPlayerId() {
        return playerId;
    }

    public void setPlayerId(PlayerEntity playerId) {
        this.playerId = playerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamePlayerId != null ? gamePlayerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamePlayerEntity)) {
            return false;
        }
        GamePlayerEntity other = (GamePlayerEntity) object;
        if ((this.gamePlayerId == null && other.gamePlayerId != null) || (this.gamePlayerId != null && !this.gamePlayerId.equals(other.gamePlayerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.scrabble.data.entity.GamePlayer[ gamePlayerId=" + gamePlayerId + " ]";
    }
    
}
