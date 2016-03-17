package com.ske.snakebaddesign.models;

/**
 * Created by pockybossuser on 3/16/2016.
 */
public class Player {

    private int number;
    private Piece piece;

    public Player(int number) {
        piece = new Piece(0);
        this.number = number;
    }

    public int getPosition() {
        return piece.getPosition();
    }

    public void setPosition(int position) {
        this.piece.setPosition(position);
    }

    public int getNumber() {
        return this.number;
    }

}
