package store.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.entity.Promotion;

public class PromotionRepository {
    private static List<Promotion> promotions = new ArrayList<>();

    public void saveAll(List<Promotion> promotion) {
        promotions.addAll(promotion);
    }

    public Optional<Promotion> findByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.isEqualName(name))
                .findFirst();
    }
}
