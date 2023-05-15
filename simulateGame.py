#! /usr/bin/env python3

import requests
import json

def contains(list, filter):
    for x in list:
        if filter(x):
            return True
    return False

# get the current list of players
urlRoot = "http://localhost:41000"
currentPlayers = requests.get(urlRoot + "/player").json();
print("current players = " + json.dumps(currentPlayers, indent = 2))
playersDict = { "first": {}, "second": {}}

# are the players we want alread there?
desiredPlayers = [ { "name": "testPlayer 1", "key": "first" }, { "name": "testPlayer 2", "key": "second" } ]
for playerName in desiredPlayers:
    if contains(currentPlayers, lambda x: x["name"] == playerName["name"]):
        print("player " + playerName["name"] + " already exists")
        playersDict[playerName["key"]] = list(filter(lambda x: x["name"] == playerName["name"], currentPlayers))[0]
    else:
        print("player " + playerName["name"] + " does not exist, please create")
        newPlayer = requests.post(urlRoot + "/player", json = { "name": playerName["name"] }).json()
        print("newPlyer = " + json.dumps(newPlayer))
        playersDict[playerName["key"]] = newPlayer
    
print("playersDict = " + json.dumps(playersDict, indent = 2))

# create a new game
game = requests.post(urlRoot + "/game", json = { "gameStatus": "Playing" }).json()
print("created game " + str(game["id"]))

# add the players
def addGamePlayer(label, sequenceNumber):
    newGamePlayer = requests.post(urlRoot + "/gamePlayer", json = { "gameId": game["id"], "playerId": playersDict[label]["id"], "sequenceNumber": sequenceNumber }).json()
    return newGamePlayer
    
gamePlayer1 = addGamePlayer("first", 1)
gamePlayer2 = addGamePlayer("second", 2)
print("player 1 = " + json.dumps(gamePlayer1))
print("player 2 = " + json.dumps(gamePlayer2))

# add some scores
def addGamePlayerRound(round, gamePlayer, label):
    roundData = round[label]
    playerRoundData = { "gamePlayerId": gamePlayer["id"], "notes": roundData["notes"], "score": roundData["score"], "sevenLetter": roundData["sevenLetter"], "round": roundData["round"] }
    playerRound  = requests.post(urlRoot + "/gamePlayerRound", json = playerRoundData).json()
    return playerRound
    
rounds = [
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 25, "round": 1 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 32, "round": 1 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 20, "round": 2 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 36, "round": 2 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 21, "round": 3 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 5, "round": 3 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 15, "round": 4 }, "second": { "notes": "test round 1", "sevenLetter": True, "score": 95, "round": 4 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 18, "round": 5 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 2, "round": 5 } },
    { "first": { "notes": "test round 1", "sevenLetter": True, "score": 90, "round": 6 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 7, "round": 6 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 20, "round": 7 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 9, "round": 7 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 5, "round": 8 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 19, "round": 8 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 12, "round": 9 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 22, "round": 9 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 10, "round": 10 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 30, "round": 10 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 23, "round": 11 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 20, "round": 11 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 11, "round": 12 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 15, "round": 12 } },
    { "first": { "notes": "test round 1", "sevenLetter": False, "score": 13, "round": 13 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 0, "round": 13 } }
]
for round in rounds:
    addGamePlayerRound(round, gamePlayer1, "first")
    addGamePlayerRound(round, gamePlayer2, "second")

# mark game complete
updatedGame = requests.put(urlRoot + "/game/" + str(game["id"]), json = game).json()
print("final game: " + json.dumps(updatedGame))
      
