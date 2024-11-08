package store.model;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDateTime startDay;
    private final LocalDateTime endDay;

    public Promotion(String name, Integer buy, Integer get, LocalDateTime startDay, LocalDateTime endDay) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
