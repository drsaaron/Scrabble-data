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
parser.add_argument('-e', nargs = 1, help = 'environment', required = 0, default = ['int'], metavar = 'int|qa|prod')
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
desiredPlayers = [ { "name": "Scott", "key": "first" }, { "name": "Kathleen", "key": "second" } ]
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
    { "round": 1, "first": { "notes": "played 11/20/2024", "sevenLetter": False, "score": 18 }, "second": { "notes": "", "sevenLetter": True, "score": 71 } },
    { "round": 2, "first": { "notes": "", "sevenLetter": False, "score": 18 }, "second": { "notes": "", "sevenLetter": False, "score": 0 } },
    { "round": 3, "first": { "notes": "", "sevenLetter": False, "score": 16 }, "second": { "notes": "", "sevenLetter": False, "score": 3 } },
    { "round": 4, "first": { "notes": "", "sevenLetter": True, "score": 86 }, "second": { "notes": "", "sevenLetter": True, "score": 76 } },
    { "round": 5, "first": { "notes": "", "sevenLetter": False, "score": 10 }, "second": { "notes": "", "sevenLetter": False, "score": 33 } },
    { "round": 6, "first": { "notes": "", "sevenLetter": False, "score": 16 }, "second": { "notes": "", "sevenLetter": False, "score": 52 } },
    { "round": 7, "first": { "notes": "", "sevenLetter": False, "score": 30 }, "second": { "notes": "", "sevenLetter": False, "score": 35 } },
    { "round": 8, "first": { "notes": "", "sevenLetter": False, "score": 15 }, "second": { "notes": "", "sevenLetter": False, "score": 48 } },
    { "round": 9, "first": { "notes": "", "sevenLetter": False, "score": 36 }, "second": { "notes": "", "sevenLetter": True, "score": 78 } },
    { "round": 10, "first": { "notes": "", "sevenLetter": False, "score": 39 }, "second": { "notes": "", "sevenLetter": False, "score": 35 } },
    { "round": 11, "first": { "notes": "", "sevenLetter": False, "score": 16 }, "second": { "notes": "", "sevenLetter": True, "score": 86 } },
    { "round": 12, "first": { "notes": "", "sevenLetter": False, "score": -9 }, "second": { "notes": "", "sevenLetter": False, "score": 9 } }
#    { "round": 13, "first": { "notes": "", "sevenLetter": False, "score": 14 }, "second": { "notes": "", "sevenLetter": False, "score": 13 } },
#    { "round": 14, "first": { "notes": "", "sevenLetter": False, "score": 10 }, "second": { "notes": "", "sevenLetter": False, "score": 0 } },
#    { "round": 15, "first": { "notes": "", "sevenLetter": False, "score": 2 }, "second": { "notes": "", "sevenLetter": False, "score": -2 } }
]
for round in rounds:
    addGamePlayerRound(round, gamePlayer1, "first")
    addGamePlayerRound(round, gamePlayer2, "second")

# mark game complete
patch = [{ "op": "replace", "path": "/gameStatus", "value": "Complete" }]
headers = {
    'Content-Type': 'application/json-patch+json'
}
updatedGame = requests.patch(urlRoot + "/game/" + str(game["id"]), json = patch, headers = headers)
print(updatedGame)
print("final game: " + json.dumps(updatedGame.json()))
      
