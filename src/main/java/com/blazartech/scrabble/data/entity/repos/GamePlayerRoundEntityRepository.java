/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.scrabble.data.entity.repos;

import com.blazartech.scrabble.data.entity.GamePlayerRoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author scott
 */
@Repository
public interface GamePlayerRoundEntityRepository extends JpaRepository<GamePlayerRoundEntity, Integer>, JpaSpecificationExecutor<GamePlayerRoundEntity> {
    
}
