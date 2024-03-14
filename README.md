# spring-boot-cassandra-game-leaderboard

An demo application built using Spring Boot and Cassandra. 

We'll model a hypothetical game leaderboard to play around.

## Run Cassandra Locally
Let's go with a stress-free approach! Run the command below to get setup with the latest version of Cassandra:

```shell
docker run -p 9042:9042 cassandra:latest
```

This will launch a single-node Cassandra cluster.

## Open cqlsh terminal
From a terminal, run the following:

```shell
docker ps
```

Copy the value in the `NAMES` column for the cassandra container. Now run the following, substituting the `NAMES` value:

```shell
docker exec -it <NAMES> cqlsh
```

If all goes well, you'll have `cqlsh` available to do your bidding:

```shell
docker exec -it funny_northcutt cqlsh

Connected to Test Cluster at 127.0.0.1:9042
[cqlsh 6.1.0 | Cassandra 4.1.4 | CQL spec 3.4.6 | Native protocol v5]
Use HELP for help.
cqlsh>
```

## Data Model
<img width="468" alt="Screenshot 2024-03-11 at 10 36 41 PM" src="https://github.com/pdesai5839/spring-boot-cassandra-game-leaderboard/assets/143283961/c0644921-00fe-47d9-862a-cb25b7adef5f">

```
Keyspace = games
Table = leaderboards
User Defined Type = gameinfo
Primary Key = gameId + userId
Partition Key = gameId
Cluster Key = userId
```

You don't have to create any of these manually. Code will create and populate Cassandra with sample data if the keyspace/table doesn't exist at start up time.

## Start The App

```shell
mvn spring-boot:run
```

As mentioned above, if the keyspace and table don't exist, they will be created and populated with sample data.

```log
2024-03-12 05:45:09.157 [main] INFO  o.s.d.c.config.CqlSessionFactoryBean - Executing CQL [CREATE KEYSPACE IF NOT EXISTS games WITH durable_writes = true AND replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };]
2024-03-12 05:45:09.395 [main] DEBUG o.s.d.cassandra.core.cql.CqlTemplate - Executing CQL statement [CREATE TYPE IF NOT EXISTS gameinfo (endtime timestamp, gamename text, maxplayersallowed int, starttime timestamp);]
2024-03-12 05:45:09.403 [main] DEBUG o.s.d.cassandra.core.cql.CqlTemplate - Executing CQL statement [CREATE TABLE IF NOT EXISTS leaderboards (gameid text, userid text, gameinfo frozen<gameinfo>, score int, teamname text, PRIMARY KEY (gameid, userid)) WITH CLUSTERING ORDER BY (userid DESC);]
2024-03-12 06:00:32.734 [main] DEBUG o.s.d.cassandra.core.cql.CqlTemplate - Executing prepared statement [INSERT INTO leaderboards (gameid,userid,gameinfo,score,teamname) VALUES (?,?,?,?,?)]
```

## OpenAPI

See all the available endpoints by going to [OpenAPI UI](http://localhost:8080/swagger-ui-custom.html).
<img width="717" alt="Screenshot 2024-03-11 at 11 34 18 PM" src="https://github.com/pdesai5839/spring-boot-cassandra-game-leaderboard/assets/143283961/978f0000-16a8-495e-9d3a-9adf9fb967ec">

As you can see, we have the basic CRUD APIs available to try out. You can do so directly from the OpenAPI UI or use `curl`.

### Insert New Leaderboard
Run the following from a terminal to insert a new record:

```shell
curl --location 'http://localhost:8080/leaderboards/' \
--header 'Content-Type: application/json' \
--data '{
        "id": {
            "gameId": "rocket_league-APAC-2024-02-27",
            "userId": "ChatterBot"
        },
        "score": 66,
        "teamName": "Rapt0rs",
        "gameInfo": {
            "gameName": "Rocket League",
            "startTime": "2024-02-27T15:00:00",
            "endTime": "2024-02-27T15:30:00",
            "maxPlayersAllowed": 50
        }
    }'
```

And verify that it was indeed saved into Cassandra by running:

```shell
curl --location --request GET 'http://localhost:8080/leaderboards/rocket_league-APAC-2024-02-27/ChatterBot' \
--header 'Content-Type: application/json'
```

