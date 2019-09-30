package com.company.mainproject;

import lombok.Getter;

@Getter
public class Game {

    private Player currentPlayer;

    public void createNewPlayer(String playerName) {
        Player player = new Player();
        player.setName(playerName);
        currentPlayer = player;
    }

    public void updatePlayerScore(int scoreValue) {
        currentPlayer.setScore(currentPlayer.getScore() + scoreValue);
    }
}
