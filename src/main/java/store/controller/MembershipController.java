package store.controller;

import java.util.List;
import store.controller.dto.ProductOrderInfo;
import store.model.MembershipService;
import store.util.YesOrNoParser;
import store.view.InputView;
import store.view.OutputView;

public class MembershipController {
    private final MembershipService membershipService;
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public int calculateMembershipDisCount(List<ProductOrderInfo> productOrderList) {
        while (true) {
            try {
                outputView.printMemberShipMessage();
                if (YesOrNoParser.parseYesOrNo(inputView.inputYesOrNo())) {
                    return membershipService.calculateDisCount(productOrderList);
                }
                return 0;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
