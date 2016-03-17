package com.ske.snakebaddesign.models;

/**
 * Created by pockybossuser on 3/16/2016.
 */
public class DieCup {

    private Die die;

    public int roll() {
        return die.roll();
    }

    public DieCup() {
        die = new Die();
    }

}
