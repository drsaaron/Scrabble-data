#! /bin/sh

curl -i -H 'Content-Type: application/json-patch+json' -X PATCH http://localhost:41000/game/94 -d '[{ "op": "replace", "path":"/gameStatus", "value":"Complete" }]'
