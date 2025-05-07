package ru.myitschool.rogal.leaderboard.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "player_scores")
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int wave;

    @Column(nullable = false)
    private int kills;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    // Дополнительные поля для расчета бонусов
    private int totalKills;
    private int gamesPlayed;
    private int bestWave;
    
    // Флаг лучшего результата игрока
    @Column(nullable = false)
    private boolean bestScore = false;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getBestWave() {
        return bestWave;
    }

    public void setBestWave(int bestWave) {
        this.bestWave = bestWave;
    }
    
    public boolean isBestScore() {
        return bestScore;
    }
    
    public void setBestScore(boolean bestScore) {
        this.bestScore = bestScore;
    }
} 