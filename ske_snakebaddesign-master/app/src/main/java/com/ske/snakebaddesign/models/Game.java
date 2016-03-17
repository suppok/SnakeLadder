package com.ske.snakebaddesign.models;

import java.util.Observable;

/**
 * Created by pockybossuser on 3/16/2016.
 */
public class Game extends Observable {

    private DieCup dieCup;
    private Player player1;
    private Player player2;
    private int face;
    private int turn = 0;
    private int boardSize;

    public Game(int boardSize) {
        dieCup = new DieCup();
        player1 = new Player(1);
        player2 = new Player(2);
        this.boardSize = boardSize;
    }

    public void play(int value) {
        if (turn%2 == 0) {
            player1.setPosition(adjustPosition(player1.getPosition(), value));
            setChanged();
            notifyObservers(player1);
        } else {
            player2.setPosition(adjustPosition(player2.getPosition(), value));
            setChanged();
            notifyObservers(player2);
        }
        turn++;
    }

    private int adjustPosition(int current, int distance) {
        current = current + distance;
        int maxSquare = boardSize * boardSize - 1;
        if(current > maxSquare) {
            current = maxSquare - (current - maxSquare);
        }
        return current;
    }

    public String sendGameStatus() {
        String msg = "";
        if (player1.getPosition() == boardSize * boardSize - 1) {
            msg = "Player 1 won!";
        } else if (player2.getPosition() == boardSize * boardSize - 1) {
            msg = "Player 2 won!";
        }
        return msg;
    }

    public DieCup getDieCup() {
        return this.dieCup;
    }
    public int getP1Pos() {
        return this.player1.getPosition();
    }
    public int getP2Pos() {
        return this.player2.getPosition();
    }

    public void playSpecial1(int value) {
        player1.setPosition(adjustPosition(player1.getPosition(), value));
        setChanged();
        notifyObservers(player1);
    }


    public void playSpecial2(int value) {
        player2.setPosition(adjustPosition(player2.getPosition(), value));
        setChanged();
        notifyObservers(player2);
    }


}
