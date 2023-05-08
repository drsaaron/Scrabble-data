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
def addGamePlayerRound(round, gamePlayer, label, notes):
    playerRoundData = { "gamePlayerId": gamePlayer["id"], "notes": notes, "score": round[label], "sevenLetter": False }
    playerRound  = requests.post(urlRoot + "/gamePlayerRound", json = playerRoundData).json()
    return playerRound
    
rounds = [
    { "first": 50, "second": 32 },
    { "first": 20, "second": 36 },
    { "first": 21, "second": 5 },
    { "first": 15, "second": 95 },
    { "first": 18, "second": 2 },
    { "first": 90, "second": 7 },
    { "first": 20, "second": 9 },
    { "first": 5, "second": 19 },
    { "first": 12, "second": 22 },
    { "first": 10, "second": 30 },
    { "first": 23, "second": 20 },
    { "first": 11, "second": 15 },
    { "first": 13, "second": 0 }
]
for round in rounds:
    addGamePlayerRound(round, gamePlayer1, "first", "sample first")
    addGamePlayerRound(round, gamePlayer2, "second", "sample second")

# mark game complete
updatedGame = requests.put(urlRoot + "/game/" + str(game["id"]), json = game).json()
print("final game: " + json.dumps(updatedGame))
      
