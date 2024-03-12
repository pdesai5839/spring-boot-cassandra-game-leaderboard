package com.desai.demo.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;

public interface GameLeaderboardRepository extends CassandraRepository<GameLeaderboard, LeaderboardKey> {

}
