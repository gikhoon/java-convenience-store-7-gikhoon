package store.model.entity;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public boolean isPromote() {
        LocalDate today = DateTimes.now()
                .toLocalDate();
        return today.isAfter(startDay) && today.isBefore(endDay);
    }
    public String getName() {
        return name;
    }
}
