package com.matchingengine.simulator;

import com.matchingengine.engine.OrderBook;
import com.matchingengine.model.Order;
import java.util.Random;

public class StressTestRunner {
    public static void main(String[] args) {
        var book = new OrderBook();
        int cnt = 1_000_000;
        System.out.println("gen data...");

        var prices= new long[cnt];
        var quantities= new long[cnt];
        var sides= new char[cnt];
        var r = new Random(42);
        for (int i = 0; i < cnt; i++) {
            sides[i] = r.nextBoolean() ? 'B' : 'S';
            prices[i] = 99 + r.nextInt(3);
            quantities[i] = (r.nextInt(5) + 1) * 10;
        }
        System.out.println("running loop...");
        System.gc();

        long start = System.nanoTime();
        for (int i = 0; i < cnt; i++) {
            book.process(new Order(i + 1, prices[i], quantities[i], sides[i]));
        }
        long end = System.nanoTime();
        long totalCnts = end - start;

        System.out.println("done.");
        System.out.println("took: " + (totalCnts / 1000000.0) + " ms");
        System.out.println("ops/sec: " + (cnt / (double) totalCnts * 1000000000.0));
        System.out.println("avg: " + ((totalCnts / (double) cnt) / 1000.0) + " us");
    }
}