#! /usr/bin/env python3

import requests
import json
import argparse
import os
import sys

def contains(list, filter):
    for x in list:
        if filter(x):
            return True
    return False

parser = argparse.ArgumentParser(description = 'simulate scrabble game')
parser.add_argument('-e', nargs = 1, help = 'environment', required = 0, default = ['int'])
args = parser.parse_args()
appEnvironment = args.e[0]

match appEnvironment:
  case "int":
    server = "localhost"
    port = 41000

  case "qa":
    server = "localhost"
    port = 4100

  case "prod":
    server = str(os.getenv('LAPTOP_IP'))
    port = 4100

  case _:
    print("invalid environment, only int|qa|prod are recognized", file=sys.stderr)
    sys.exit(1)
    
# get the current list of players
urlRoot = "http://" + server + ":" + str(port)
print("urlroot = " + urlRoot)
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
    playerRoundData = { "gamePlayerId": gamePlayer["id"], "notes": roundData["notes"], "score": roundData["score"], "sevenLetter": roundData["sevenLetter"], "round": round["round"] }
    playerRound  = requests.post(urlRoot + "/gamePlayerRound", json = playerRoundData).json()
    return playerRound
    
rounds = [
    { "round": 1, "first": { "notes": "test round 1", "sevenLetter": False, "score": 25 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 32 } },
    { "round": 2, "first": { "notes": "test round 1", "sevenLetter": False, "score": 20 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 36 } },
    { "round": 3, "first": { "notes": "test round 1", "sevenLetter": False, "score": 21 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 5 } },
    { "round": 4, "first": { "notes": "test round 1", "sevenLetter": False, "score": 15 }, "second": { "notes": "test round 1", "sevenLetter": True, "score": 95 } },
    { "round": 5, "first": { "notes": "test round 1", "sevenLetter": False, "score": 18 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 2 } },
    { "round": 6, "first": { "notes": "test round 1", "sevenLetter": True, "score": 9 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 7 } },
    { "round": 7, "first": { "notes": "test round 1", "sevenLetter": False, "score": 20 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 9 } },
    { "round": 8, "first": { "notes": "test round 1", "sevenLetter": False, "score": 5 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 19 } },
    { "round": 9, "first": { "notes": "test round 1", "sevenLetter": False, "score": 12 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 22 } },
    { "round": 10, "first": { "notes": "test round 1", "sevenLetter": False, "score": 10 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 30 } },
    { "round": 11, "first": { "notes": "test round 1", "sevenLetter": False, "score": 23 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 20 } },
    { "round": 12, "first": { "notes": "test round 1", "sevenLetter": False, "score": 11 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 15 } },
    { "round": 13, "first": { "notes": "test round 1", "sevenLetter": False, "score": 13 }, "second": { "notes": "test round 1", "sevenLetter": False, "score": 0 } }
]
for round in rounds:
    addGamePlayerRound(round, gamePlayer1, "first")
    addGamePlayerRound(round, gamePlayer2, "second")

# mark game complete
updatedGame = requests.put(urlRoot + "/game/" + str(game["id"]), json = game).json()
print("final game: " + json.dumps(updatedGame))
      
