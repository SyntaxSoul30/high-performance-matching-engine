package com.matchingengine.engine;

import com.matchingengine.model.Order;
import java.util.*;

public class OrderBook {
    // bids high->low, asks low->high
    private final TreeMap<Long, ArrayDeque<Order>> bids = new TreeMap<>(Collections.reverseOrder());
    private final TreeMap<Long, ArrayDeque<Order>> asks = new TreeMap<>();

    public void process(Order o) {
        var counterBook = (o.side == 'B') ? asks : bids;
        var nativeBook  = (o.side == 'B') ? bids : asks;

        while (o.qty > 0 && !counterBook.isEmpty()) {
            long bestPrice = counterBook.firstKey();

            // check if crossing
            if ((o.side == 'B' && o.price < bestPrice) || (o.side == 'S' && o.price > bestPrice)) {
                break;
            }

            var queue = counterBook.get(bestPrice);
            while (!queue.isEmpty() && o.qty > 0) {
                Order resting = queue.peekFirst();
                long matchQty = Math.min(o.qty, resting.qty);

                o.qty -= matchQty;
                resting.qty -= matchQty;

                if (resting.qty == 0) {
                    queue.removeFirst();
                }
            }

            if (queue.isEmpty()) {
                counterBook.remove(bestPrice);
            }
        }

        // track resting liquidity
        if (o.qty > 0) {
            nativeBook.computeIfAbsent(o.price, k -> new ArrayDeque<>()).addLast(o);
        }
    }

    public TreeMap<Long, ArrayDeque<Order>> getBids() { return bids; }
    public TreeMap<Long, ArrayDeque<Order>> getAsks() { return asks; }
}