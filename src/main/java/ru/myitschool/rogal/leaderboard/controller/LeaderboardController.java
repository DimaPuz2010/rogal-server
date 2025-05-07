package ru.myitschool.rogal.leaderboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myitschool.rogal.leaderboard.model.PlayerScore;
import ru.myitschool.rogal.leaderboard.service.LeaderboardService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private static final Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitScore(@RequestBody PlayerScore playerScore) {
        logger.info("Received score submission: {}", playerScore);
        
        try {
            // Проверка обязательных полей
            if (playerScore.getPlayerName() == null || playerScore.getPlayerName().isEmpty()) {
                logger.warn("Score submission rejected: playerName is missing");
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Player name is required"
                ));
            }
            
            PlayerScore savedScore = leaderboardService.saveScore(playerScore);
            logger.info("Score submitted successfully for player: {}, score: {}", 
                      playerScore.getPlayerName(), playerScore.getScore());
            
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Score submitted successfully",
                    "score", savedScore
            ));
        } catch (Exception e) {
            logger.error("Failed to submit score: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to submit score: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<PlayerScore>> getTopScores(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(leaderboardService.getTopScores(limit));
    }

    @GetMapping("/player/{playerName}")
    public ResponseEntity<List<PlayerScore>> getPlayerScores(
            @PathVariable String playerName) {
        return ResponseEntity.ok(leaderboardService.getPlayerScores(playerName));
    }

    /**
     * Получает лучший результат игрока
     * @param playerName имя игрока
     * @return лучший результат или статус 404, если игрок не найден
     */
    @GetMapping("/player/{playerName}/best")
    public ResponseEntity<?> getPlayerBestScore(@PathVariable String playerName) {
        logger.info("Request to get best score for player: {}", playerName);
        
        try {
            PlayerScore bestScore = leaderboardService.getPlayerBestScore(playerName);
            if (bestScore == null) {
                logger.info("No scores found for player: {}", playerName);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(bestScore);
        } catch (Exception e) {
            logger.error("Error retrieving best score for player {}: {}", playerName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error retrieving best score: " + e.getMessage()));
        }
    }

    /**
     * Получение списка всех лучших результатов игроков
     * @param page номер страницы (с 0)
     * @param size размер страницы
     * @return список лучших результатов
     */
    @GetMapping("/bestscores")
    public ResponseEntity<?> getBestScores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            return ResponseEntity.ok(leaderboardService.getBestScores(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to get best scores: " + e.getMessage()
            ));
        }
    }

    /**
     * Проверка доступности сервера
     * @return статус сервера
     */
    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> pingServer() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("timestamp", System.currentTimeMillis());
        response.put("message", "Server is up and running");
        
        return ResponseEntity.ok(response);
    }
} 