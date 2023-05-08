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
gamePlayer1 = requests.post(urlRoot + "/gamePlayer", json = { "gameId": game["id"], "playerId": playersDict["first"]["id"], "sequenceNumber": 1 }).json()
gamePlayer2 = requests.post(urlRoot + "/gamePlayer", json = { "gameId": game["id"], "playerId": playersDict["second"]["id"], "sequenceNumber": 2 }).json()
print("player 1 = " + json.dumps(gamePlayer1))
print("player 2 = " + json.dumps(gamePlayer2))

# add some scores
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
    gamePlayerRound1 = { "gamePlayerId": gamePlayer1["id"], "notes": "sample first", "score": round["first"], "sevenLetter": False }
    requests.post(urlRoot + "/gamePlayerRound", json = gamePlayerRound1)

    gamePlayerRound2 = { "gamePlayerId": gamePlayer2["id"], "notes": "sample second", "score": round["second"], "sevenLetter": False }
    requests.post(urlRoot + "/gamePlayerRound", json = gamePlayerRound2)

# mark game complete
updatedGame = requests.put(urlRoot + "/game/" + str(game["id"]), json = game).json()
print("final game: " + json.dumps(updatedGame))
      
