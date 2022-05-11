package ru.job4j.kiss;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MaxMinTest {

    MaxMin maxMin = new MaxMin();
    Comparator<Integer> intCompare = Integer::compare;
    List<Integer> integers = List.of(99, 101, 43, 55, 98, 333);

    @Test
    public void thenFindMaxAndThenIs333() {
        int max = maxMin.max(integers, intCompare);
        assertEquals(333, max);
    }

    @Test
    public void whenFindMaxAndThenIs43() {
        int min = maxMin.min(integers, intCompare);
        assertEquals(43, min);
    }
}