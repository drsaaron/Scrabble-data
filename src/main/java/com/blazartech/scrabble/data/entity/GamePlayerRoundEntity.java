/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.scrabble.data.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scott
 */
@Entity
@Table(name = "GamePlayerRound")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GamePlayerRoundEntity.findAll", query = "SELECT g FROM GamePlayerRoundEntity g"),
    @NamedQuery(name = "GamePlayerRoundEntity.findByGamePlayerRoundId", query = "SELECT g FROM GamePlayerRoundEntity g WHERE g.gamePlayerRoundId = :gamePlayerRoundId"),
    @NamedQuery(name = "GamePlayerRoundEntity.findByScoreCnt", query = "SELECT g FROM GamePlayerRoundEntity g WHERE g.scoreCnt = :scoreCnt"),
    @NamedQuery(name = "GamePlayerRoundEntity.findBySvnLtrInd", query = "SELECT g FROM GamePlayerRoundEntity g WHERE g.svnLtrInd = :svnLtrInd"),
    @NamedQuery(name = "GamePlayerRoundEntity.findByNoteTxt", query = "SELECT g FROM GamePlayerRoundEntity g WHERE g.noteTxt = :noteTxt"),
    @NamedQuery(name = "GamePlayerRoundEntity.findByRowCreateDtm", query = "SELECT g FROM GamePlayerRoundEntity g WHERE g.rowCreateDtm = :rowCreateDtm"),
    @NamedQuery(name = "GamePlayerRoundEntity.findByGamePlayerId", query = "SELECT g FROM GamePlayerRoundEntity g where g.gamePlayerId.gamePlayerId = :gamePlayerId")
})
public class GamePlayerRoundEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GamePlayerRoundId")
    private Integer gamePlayerRoundId;
    @Basic(optional = false)
    @Column(name = "ScoreCnt")
    private int scoreCnt;
    @Basic(optional = false)
    @Column(name = "SvnLtrInd")
    private Character svnLtrInd;
    @Column(name = "NoteTxt")
    private String noteTxt;
    @Basic(optional = true)
    @Column(name = "RowCreateDtm", columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowCreateDtm;
    @JoinColumn(name = "GamePlayerId", referencedColumnName = "GamePlayerId")
    @ManyToOne(optional = false)
    private GamePlayerEntity gamePlayerId;

    public GamePlayerRoundEntity() {
    }

    public GamePlayerRoundEntity(Integer gamePlayerRoundId) {
        this.gamePlayerRoundId = gamePlayerRoundId;
    }

    public GamePlayerRoundEntity(Integer gamePlayerRoundId, int scoreCnt, Character svnLtrInd, Date rowCreateDtm) {
        this.gamePlayerRoundId = gamePlayerRoundId;
        this.scoreCnt = scoreCnt;
        this.svnLtrInd = svnLtrInd;
        this.rowCreateDtm = rowCreateDtm;
    }

    public Integer getGamePlayerRoundId() {
        return gamePlayerRoundId;
    }

    public void setGamePlayerRoundId(Integer gamePlayerRoundId) {
        this.gamePlayerRoundId = gamePlayerRoundId;
    }

    public int getScoreCnt() {
        return scoreCnt;
    }

    public void setScoreCnt(int scoreCnt) {
        this.scoreCnt = scoreCnt;
    }

    public Character getSvnLtrInd() {
        return svnLtrInd;
    }

    public void setSvnLtrInd(Character svnLtrInd) {
        this.svnLtrInd = svnLtrInd;
    }

    public String getNoteTxt() {
        return noteTxt;
    }

    public void setNoteTxt(String noteTxt) {
        this.noteTxt = noteTxt;
    }

    public Date getRowCreateDtm() {
        return rowCreateDtm;
    }

    public void setRowCreateDtm(Date rowCreateDtm) {
        this.rowCreateDtm = rowCreateDtm;
    }

    public GamePlayerEntity getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(GamePlayerEntity gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamePlayerRoundId != null ? gamePlayerRoundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamePlayerRoundEntity)) {
            return false;
        }
        GamePlayerRoundEntity other = (GamePlayerRoundEntity) object;
        if ((this.gamePlayerRoundId == null && other.gamePlayerRoundId != null) || (this.gamePlayerRoundId != null && !this.gamePlayerRoundId.equals(other.gamePlayerRoundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.scrabble.data.entity.GamePlayerRound[ gamePlayerRoundId=" + gamePlayerRoundId + " ]";
    }
    
}
