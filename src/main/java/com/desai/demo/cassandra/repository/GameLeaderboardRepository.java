package com.desai.demo.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.query.Param;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;

public interface GameLeaderboardRepository extends CassandraRepository<GameLeaderboard, LeaderboardKey> {
//    @Query("select * from \"cart\" where \"userid\"= :userId and \"id\"= :id")
    @AllowFiltering
    List<GameLeaderboard> findByTeamName(@Param("teamname")String teamName);
}
