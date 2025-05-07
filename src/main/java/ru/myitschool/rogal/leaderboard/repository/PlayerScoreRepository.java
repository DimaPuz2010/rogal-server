package ru.myitschool.rogal.leaderboard.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myitschool.rogal.leaderboard.model.PlayerScore;

import java.util.List;
import java.util.Optional;

public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
    
    // Запрос для получения лучших результатов всех игроков
    @Query("SELECT p FROM PlayerScore p ORDER BY p.score DESC")
    List<PlayerScore> findTopScores(Pageable pageable);
    
    // Запрос для получения результатов конкретного игрока, отсортированных по убыванию
    @Query("SELECT p FROM PlayerScore p WHERE p.playerName = :playerName ORDER BY p.score DESC")
    List<PlayerScore> findPlayerScoresSorted(@Param("playerName") String playerName);
    
    // Запрос для получения лучшего результата конкретного игрока
    @Query("SELECT p FROM PlayerScore p WHERE p.playerName = :playerName AND p.bestScore = true")
    Optional<PlayerScore> findPlayerBestScore(@Param("playerName") String playerName);
    
    // Запрос для получения всех лучших результатов игроков
    List<PlayerScore> findByBestScoreTrue(Pageable pageable);
} 