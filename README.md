# spring-boot-cassandra-game-leaderboard

An demo application built using Spring Boot and Cassandra. 

We'll model a hypothetical game leaderboard to play around.

## Run Cassandra Locally
Let's go with a stress-free approach! Run the command below to get setup with the latest version of Cassandra:

```shell
docker run -p 9042:9042 cassandra:latest
```

This will launch a single-node Cassandra cluster.

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
