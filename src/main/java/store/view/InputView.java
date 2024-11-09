package store.view;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static store.constant.ViewMessageConstant.ORDER_PRODUCT_MESSAGE;

public class InputView {
    public String inputProductOrder() {
        System.out.println(ORDER_PRODUCT_MESSAGE);
        return input();
    }

    private String input() {
        return readLine().trim();
    }
}
