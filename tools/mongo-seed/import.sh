#! /bin/bash

echo 'Import data to mongo'
mongoimport --host mongo --db showcase --collection items --type json --file /mongo-seed/items.json --jsonArray
mongoimport --host mongo --db showcase --collection users --type json --file /mongo-seed/users.json --jsonArray