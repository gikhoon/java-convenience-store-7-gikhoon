package store.model.repository;

import java.util.ArrayList;
import java.util.List;
import store.model.Promotion;

public class PromotionRepository {
    private List<Promotion> promotions = new ArrayList<>();

    public void saveAll(List<Promotion> promotion) {
        promotions.addAll(promotion);
    }

    public Promotion findByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.isEqualName(name))
                .findFirst()
                .orElse(null);
    }
}