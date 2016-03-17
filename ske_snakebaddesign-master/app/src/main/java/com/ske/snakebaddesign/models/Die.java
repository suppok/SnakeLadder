package com.ske.snakebaddesign.models;

import java.util.Random;

/**
 * Created by pockybossuser on 3/16/2016.
 */
public class Die {

    public int roll() {
        return 1 + new Random().nextInt(6);
    }

}
