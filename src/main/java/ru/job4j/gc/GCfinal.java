package ru.job4j.gc;

import static ru.job4j.gc.GCDemo.info;

public class GCfinal {
    public static void main(String[] args) {
        info();
        for (int i = 0; i < 3350; i++) {
            new User(i, "Name" + i);
        }
        info();
    }
}
