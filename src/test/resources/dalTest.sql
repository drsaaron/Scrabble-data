/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  scott
 * Created: May 2, 2023
 */

insert into Game(GameId, StsCde) values(1, 'P');
insert into Game(GameId, StsCde) values(2, 'P');
insert into Game(GameId, StsCde) values(3, 'P');

insert into Player(PlayerId, NameTxt) values(1, 'Scott');
insert into Player(PlayerId, NameTxt) values(2, 'Roberta');
insert into Player(PlayerId, NameTxt) values(3, 'Henrietta');
insert into Player(PlayerId, NameTxt) values(4, 'Pauline');

insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq, ScoreCnt) values(1, 1, 1, 2, 200);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(2, 1, 2, 1);

insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq, ScoreCnt) values(3, 2, 1, 2, 500);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq, ScoreCnt) values(4, 2, 2, 1, 1500);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq, ScoreCnt) values(5, 2, 3, 4, 2500);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq, ScoreCnt) values(6, 2, 4, 3, 250);

insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(7, 3, 1, 2);
insert into GamePlayer(GamePlayerId, GameId, PlayerId, OrderSeq) values(8, 3, 4, 1);

insert into GamePlayerRound(GamePlayerRoundId, GamePlayerId, ScoreCnt, SvnLtrInd, NoteTxt, RoundCnt) values(1001, 7, 25, 'N', 'round 1', 3);
insert into GamePlayerRound(GamePlayerRoundId, GamePlayerId, ScoreCnt, SvnLtrInd, NoteTxt, RoundCnt) values(1002, 7, 15, 'N', 'round 2', 2);
insert into GamePlayerRound(GamePlayerRoundId, GamePlayerId, ScoreCnt, SvnLtrInd, NoteTxt, RoundCnt) values(1003, 7, 85, 'Y', 'round 3', 1);

update Player set HighGameId = 1 where PlayerId = 1;
