package com.desai.demo.cassandra.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.desai.demo.cassandra.model.GameInfo;
import com.desai.demo.cassandra.model.GameLeaderboard;
import com.desai.demo.cassandra.model.LeaderboardKey;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataLoaderUtil {
    public static List<GameLeaderboard> getGameLeaderboardData() {
        return gameLeaderboardSupplier.get();
    }
    
    private static final Supplier<List<GameLeaderboard>> gameLeaderboardSupplier = () ->
        Arrays.asList(
            GameLeaderboard.builder()
                .id(LeaderboardKey.builder().gameId("fortnite-EU-2024-01-02").userId("Mandrake11").build())
                .score(150)
                .teamName("DaBrats")
                .gameInfo(GameInfo.builder().gameName("fortnite").maxPlayersAllowed(100)
                    .startTime(LocalDateTime.of(2024, 1, 2, 14, 0))
                    .endTime(LocalDateTime.of(2024, 1, 2, 15, 0))
                    .build())
                .build(),
            GameLeaderboard.builder()
                .id(LeaderboardKey.builder().gameId("fortnite-EU-2024-01-02").userId("OtterMan").build())
                .score(90)
                .teamName("Kings")
                .gameInfo(GameInfo.builder().gameName("Fortnite").maxPlayersAllowed(100)
                    .startTime(LocalDateTime.of(2024, 1, 2, 14, 0))
                    .endTime(LocalDateTime.of(2024, 1, 2, 15, 0))
                    .build())
                .build(),
            GameLeaderboard.builder()
                .id(LeaderboardKey.builder().gameId("league_of_legends-US-2023-12-05").userId("OtterMan").build())
                .score(300)
                .teamName("Kings")
                .gameInfo(GameInfo.builder().gameName("League Of Legends").maxPlayersAllowed(200)
                    .startTime(LocalDateTime.of(2023, 12, 5, 11, 0))
                    .endTime(LocalDateTime.of(2023, 12, 5, 12, 0))
                    .build())
                .build(),
            GameLeaderboard.builder()
                .id(LeaderboardKey.builder().gameId("league_of_legends-US-2023-12-05").userId("Eggplant").build())
                .score(210)
                .teamName("TheFarmers")
                .gameInfo(GameInfo.builder().gameName("League Of Legends").maxPlayersAllowed(200)
                    .startTime(LocalDateTime.of(2023, 12, 5, 11, 0))
                    .endTime(LocalDateTime.of(2023, 12, 5, 12, 0))
                    .build())
                .build(),
            GameLeaderboard.builder()
                .id(LeaderboardKey.builder().gameId("league_of_legends-US-2023-11-10").userId("Eggplant").build())
                .score(210)
                .teamName("TheFarmers")
                .gameInfo(GameInfo.builder().gameName("League Of Legends").maxPlayersAllowed(150)
                    .startTime(LocalDateTime.of(2023, 11, 10, 16, 30))
                    .endTime(LocalDateTime.of(2023, 11, 10, 17, 30))
                    .build())
                .build()
        );
}
