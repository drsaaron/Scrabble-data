/*drop procedure if exists SCORE_TRUE_UP;

DELIMITER $$

CREATE PROCEDURE `SCORE_TRUE_UP`()
BEGIN*/
	
	create temporary table TotalScores
	select GamePlayerId, sum(ScoreCnt) as TotalScore
	from Scrabble.GamePlayerRound
	group by GamePlayerId;

	update Scrabble.GamePlayer a, TotalScores b
	set a.ScoreCnt = b.TotalScore
	where a.GamePlayerId = b.GamePlayerId
  	  and a.GamePlayerId > 0;
/*
END$$

DELMIITER ;*/
