package ru.myitschool.rogal.leaderboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.myitschool.rogal.leaderboard.model.PlayerScore;
import ru.myitschool.rogal.leaderboard.repository.PlayerScoreRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LeaderboardService {

    private final PlayerScoreRepository playerScoreRepository;

    @Autowired
    public LeaderboardService(PlayerScoreRepository playerScoreRepository) {
        this.playerScoreRepository = playerScoreRepository;
    }

    public PlayerScore saveScore(PlayerScore playerScore) {
        // Устанавливаем текущее время если оно не задано
        if (playerScore.getSubmittedAt() == null) {
            playerScore.setSubmittedAt(LocalDateTime.now());
        }
        
        // Проверяем, является ли текущий результат лучшим для данного игрока
        PlayerScore bestScore = getPlayerBestScore(playerScore.getPlayerName());
        
        // Если это первый результат игрока или новый результат лучше предыдущего, 
        // помечаем его как личный рекорд
        if (bestScore == null || playerScore.getScore() > bestScore.getScore()) {
            playerScore.setBestScore(true);
            
            // Если есть предыдущий лучший результат, снимаем с него пометку
            if (bestScore != null) {
                bestScore.setBestScore(false);
                playerScoreRepository.save(bestScore);
            }
        } else {
            playerScore.setBestScore(false);
        }
        
        return playerScoreRepository.save(playerScore);
    }

    public List<PlayerScore> getTopScores(int limit) {
        return playerScoreRepository.findTopScores(PageRequest.of(0, limit));
    }

    public List<PlayerScore> getPlayerScores(String playerName) {
        return playerScoreRepository.findPlayerScoresSorted(playerName);
    }
    
    public PlayerScore getPlayerBestScore(String playerName) {
        // Используем новый запрос для получения лучшего результата
        Optional<PlayerScore> bestScore = playerScoreRepository.findPlayerBestScore(playerName);
        
        // Если результат существует, возвращаем его
        if (bestScore.isPresent()) {
            return bestScore.get();
        }
        
        // В противном случае пробуем найти лучший результат по сортировке
        List<PlayerScore> scores = getPlayerScores(playerName);
        if (scores != null && !scores.isEmpty()) {
            PlayerScore highestScore = scores.get(0);
            // Помечаем этот результат как лучший
            highestScore.setBestScore(true);
            playerScoreRepository.save(highestScore);
            return highestScore;
        }
        
        return null;
    }

    /**
     * Получает список лучших результатов всех игроков
     * @param page номер страницы
     * @param size размер страницы
     * @return список лучших результатов
     */
    public List<PlayerScore> getBestScores(int page, int size) {
        return playerScoreRepository.findByBestScoreTrue(PageRequest.of(page, size));
    }
} 