package com.unkarjedy.platformer.model;

/**
 * Created by Dima Naumenko on 05.07.2015.
 */
public class LevelScore {

    private int coinsCollected = 0;
    private int maxCoins;

    public LevelScore(GameLevel level) {
        maxCoins = level.getStarsAmount();
    }

    public void collectCoin(){
        collectCoins(1);
    }

    public void collectCoins(int amount) {
        coinsCollected += amount;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public int getMaxCoins() {
        return maxCoins;
    }

    public String buildScoreString() {
        return String.format("Score: %d (%d)", getCoinsCollected(), getMaxCoins());
    }
}
