package com.desai.demo.cassandra.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("leaderboards")
public class GameLeaderboard implements Serializable {
    private static final long serialVersionUID = 4853098488569300343L;

    @PrimaryKey
    private LeaderboardKey id;

    private int score;

    private String teamName;

    private GameInfo gameInfo;
}
