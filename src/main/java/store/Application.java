package store;

import store.controller.MainController;
import store.controller.MembershipController;
import store.model.GeneralMembershipService;

public class Application {
    public static void main(String[] args) {
        MembershipController membershipController = new MembershipController(new GeneralMembershipService());
        MainController controller = new MainController(membershipController);
        controller.run();
    }
}
