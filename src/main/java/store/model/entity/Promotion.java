package store.model.entity;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final Integer buy;
    private final Integer get;
    private final LocalDate startDay;
    private final LocalDate endDay;

    public Promotion(String name, Integer buy, Integer get, LocalDate startDay, LocalDate endDay) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }
}
