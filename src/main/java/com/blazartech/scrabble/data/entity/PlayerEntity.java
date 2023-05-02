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
@Table(name = "Player")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerEntity.findAll", query = "SELECT p FROM PlayerEntity p"),
    @NamedQuery(name = "PlayerEntity.findByPlayerId", query = "SELECT p FROM PlayerEntity p WHERE p.playerId = :playerId"),
    @NamedQuery(name = "PlayerEntity.findByNameTxt", query = "SELECT p FROM PlayerEntity p WHERE p.nameTxt = :nameTxt")})
public class PlayerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PlayerId")
    private Integer playerId;
    @Basic(optional = false)
    @Column(name = "NameTxt")
    private String nameTxt;
    @JoinColumn(name = "HighGameId", referencedColumnName = "GameId")
    @ManyToOne
    private GameEntity highGameId;
    @OneToMany(mappedBy = "winnerId")
    private Collection<GameEntity> gameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerId")
    private Collection<GamePlayerEntity> gamePlayerCollection;

    public PlayerEntity() {
    }

    public PlayerEntity(Integer playerId) {
        this.playerId = playerId;
    }

    public PlayerEntity(Integer playerId, String nameTxt) {
        this.playerId = playerId;
        this.nameTxt = nameTxt;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(String nameTxt) {
        this.nameTxt = nameTxt;
    }

    public GameEntity getHighGameId() {
        return highGameId;
    }

    public void setHighGameId(GameEntity highGameId) {
        this.highGameId = highGameId;
    }

    @XmlTransient
    public Collection<GameEntity> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<GameEntity> gameCollection) {
        this.gameCollection = gameCollection;
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
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlayerEntity)) {
            return false;
        }
        PlayerEntity other = (PlayerEntity) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.scrabble.data.entity.Player[ playerId=" + playerId + " ]";
    }
    
}
