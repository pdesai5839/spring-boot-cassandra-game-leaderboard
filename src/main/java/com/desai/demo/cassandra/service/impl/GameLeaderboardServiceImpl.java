package com.desai.demo.cassandra.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;
import com.desai.demo.cassandra.repository.GameLeaderboardRepository;
import com.desai.demo.cassandra.service.GameLeaderboardService;
import com.desai.demo.cassandra.util.DataLoaderUtil;

@Service
public class GameLeaderboardServiceImpl implements GameLeaderboardService {
    @Autowired
    private GameLeaderboardRepository repository;

    @Override
    public List<GameLeaderboard> save() {
        List<GameLeaderboard> leaderboards = repository.findAll();
        if (leaderboards.isEmpty())
            repository.saveAll(DataLoaderUtil.getGameLeaderboardData());

        return repository.findAll();
    }

    @Override
    public List<GameLeaderboard> findAll() {
        return repository.findAll();
    }
    
    @Override
    public GameLeaderboard findById(LeaderboardKey id) {
        return repository.findById(id)
            .orElse(GameLeaderboard.builder().build());
    }

    @Override
    public GameLeaderboard save(GameLeaderboard gameLeaderboard) {
        return repository.save(gameLeaderboard);
    }

    @Override
    public GameLeaderboard update(GameLeaderboard gameLeaderboard) {
        return repository.save(gameLeaderboard);
    }

    @Override
    public void delete(LeaderboardKey id) {
        repository.findById(id).ifPresent(leaderboard -> repository.delete(leaderboard));
    }

}
