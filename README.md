# high-performance-matching-engine

A low-latency, in-memory limit order book built as a design prototype.

### The Origin / Why this PoC?
While grinding LeetCode and scaling systems, I started wondering how elite trading firms handle massive data structure constraints under extreme microsecond limits. A standard database or web framework is way too slow for that world.

I built this in-memory Proof of Concept (PoC) to get a hands-on look at algorithmic bottlenecks, pointer chasing, and mechanical sympathy within the JVM. It’s an ongoing scratchpad to see how code directly fights the hardware.

### The Stack
* **Language:** Java 17 (Vanilla / Core)
* **Build System:** Maven 3.x
* **Core Structures:** `TreeMap` (for price tiering), `ArrayDeque` (for FIFO time priority queues)

### System Rules
* **Bids (Buy Book):** Sorted high-to-low using reverse order. The highest bidder always gets priority.
* **Asks (Sell Book):** Sorted low-to-high using natural order. The cheapest seller always gets priority.
* **Time Priority:** When prices match, orders are cleared strictly by arrival time (First-In, First-Out) using fast deque arrays under the hood.

---

### Running the 1M Order Stress Test

To check execution speed and throughput limits, the simulation script bypasses I/O bottlenecks and pumps 1,000,000 crossing orders straight into the engine core.

```bash
# Compile everything
mvn clean compile

# Run the performance test loop
mvn exec:java
