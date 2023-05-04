/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  scott
 * Created: May 4, 2023
 */

insert into Game(GameId, StsCde) values(1, 'P');
insert into Game(GameId, StsCde) values(2, 'P');

insert into Player(PlayerId, NameTxt) values(1, 'Scott');
insert into Player(PlayerId, NameTxt) values(2, 'Roberta');
insert into Player(PlayerId, NameTxt) values(3, 'Henrietta');
insert into Player(PlayerId, NameTxt) values(4, 'Pauline');

insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(1, 1, 1, 2);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(2, 1, 2, 1);

