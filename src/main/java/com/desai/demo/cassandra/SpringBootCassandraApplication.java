package com.desai.demo.cassandra;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.repository.GameLeaderboardRepository;
import com.desai.demo.cassandra.util.DataLoaderUtil;

@SpringBootApplication
@EnableCassandraRepositories
public class SpringBootCassandraApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public static void main(String[] args) {
        SpringApplication.run(SpringBootCassandraApplication.class, args);
    }

    @Autowired
    private GameLeaderboardRepository gameLeaderboardRepository;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            List<GameLeaderboard> gameLeaderboardData = gameLeaderboardRepository.findAll();
            if (gameLeaderboardData.isEmpty()) {
                logger.info("******* Inserting Game Leaderboard data in DB *******");
                gameLeaderboardRepository.saveAll(DataLoaderUtil.getGameLeaderboardData());
            }
            else {
                logger.info("******* Game Leaderboard in DB :: {}", gameLeaderboardData);
            }
        };
    }

}
