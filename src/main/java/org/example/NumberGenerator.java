package org.example;

import lombok.Getter;

@Getter
public class NumberGenerator implements Runnable{

    private int number;
    @Override
    public void run() {
        number = (int) Math.random();
    }

}
