package store.model;

import java.util.List;
import store.controller.dto.ProductOrderInfo;

public interface MembershipService {
    int calculateDisCount(List<ProductOrderInfo> productOrderList);
}
