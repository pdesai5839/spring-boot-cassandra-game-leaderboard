package com.desai.demo.cassandra.service;

import java.util.List;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;

public interface GameLeaderboardService {
    List<GameLeaderboard> save();

    List<GameLeaderboard> findAll();

    GameLeaderboard findById(LeaderboardKey id);
    
    List<GameLeaderboard> findByTeamName(String teamName);

    GameLeaderboard save(GameLeaderboard gameLeaderboard);

    GameLeaderboard update(GameLeaderboard gameLeaderboard);

    void delete(LeaderboardKey id);
}
