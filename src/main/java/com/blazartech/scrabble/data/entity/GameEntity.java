/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author scott
 */
@Entity
@Table(name = "Game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameEntity.findAll", query = "SELECT g FROM GameEntity g"),
    @NamedQuery(name = "GameEntity.findByGameId", query = "SELECT g FROM GameEntity g WHERE g.gameId = :gameId"),
    @NamedQuery(name = "GameEntity.findByStsCde", query = "SELECT g FROM GameEntity g WHERE g.stsCde = :stsCde"),
    @NamedQuery(name = "GameEntity.findByStartDtm", query = "SELECT g FROM GameEntity g WHERE g.startDtm = :startDtm"),
    @NamedQuery(name = "GameEntity.findByEndDtm", query = "SELECT g FROM GameEntity g WHERE g.endDtm = :endDtm")})
public class GameEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GameId")
    private Integer gameId;
    @Column(name = "StsCde")
    private Character stsCde;
    @Basic(optional = true)
    @Column(name = "StartDtm", columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDtm;
    @Column(name = "EndDtm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDtm;
    @OneToMany(mappedBy = "highGameId")
    private Collection<PlayerEntity> playerCollection;
    @JoinColumn(name = "WinnerId", referencedColumnName = "PlayerId")
    @ManyToOne
    private PlayerEntity winnerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private Collection<GamePlayerEntity> gamePlayerCollection;

    public GameEntity() {
    }

    public GameEntity(Integer gameId) {
        this.gameId = gameId;
    }

    public GameEntity(Integer gameId, Date startDtm) {
        this.gameId = gameId;
        this.startDtm = startDtm;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Character getStsCde() {
        return stsCde;
    }

    public void setStsCde(Character stsCde) {
        this.stsCde = stsCde;
    }

    public Date getStartDtm() {
        return startDtm;
    }

    public void setStartDtm(Date startDtm) {
        this.startDtm = startDtm;
    }

    public Date getEndDtm() {
        return endDtm;
    }

    public void setEndDtm(Date endDtm) {
        this.endDtm = endDtm;
    }

    @XmlTransient
    public Collection<PlayerEntity> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<PlayerEntity> playerCollection) {
        this.playerCollection = playerCollection;
    }

    public PlayerEntity getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(PlayerEntity winnerId) {
        this.winnerId = winnerId;
    }

    @XmlTransient
    public Collection<GamePlayerEntity> getGamePlayerCollection() {
        return gamePlayerCollection;
    }

    public void setGamePlayerCollection(Collection<GamePlayerEntity> gamePlayerCollection) {
        this.gamePlayerCollection = gamePlayerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameEntity)) {
            return false;
        }
        GameEntity other = (GameEntity) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameEntity{" + "gameId=" + gameId + ", stsCde=" + stsCde + ", startDtm=" + startDtm + ", endDtm=" + endDtm + ", playerCollection=" + playerCollection + ", winnerId=" + winnerId + ", gamePlayerCollection=" + gamePlayerCollection + '}';
    }
    
}
