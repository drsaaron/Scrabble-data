/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  scott
 * Created: May 2, 2023
 */

insert into Player(PlayerId, NameTxt) values(1, 'Scott');
insert into Player(PlayerId, NameTxt) values(2, 'Roberta');

insert into Game(GameId, StsCde) values(1, 'P');

insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(1, 1, 1, 1);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(2, 1, 2, 2);