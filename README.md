# 편의점

## 소개
편의점에는 현재 재고와 프로모션 할인 상황을 고려하여 최종 걸제 금액을 계산하고
출력하는 프로그램입니다. 사용자는 상품의 가격과 수량을 입력하여 최종 결제 금액을 산출받으며,
자동으로 프로모션 할인과 멤버십 할인 정책이 적용됩니다.

프로모션 할인 적용: 지정된 상품에는 프로모션 기간 내 N+1 형태의 할인이 적용되며,
조건에 맞는 상품은 자동으로 증정됩니다. 재고가 부족한 경우에는 고객에게 적절한 안내를 제공합니다.

멤버십 할인: 프로모션이 적용되지 않은 제품에 대해 멤버십 할인을 추가로 적용하며,
최대 8,000원 한도 내에서 할인됩니다.

이를 기반으로 구매 상품 내역, 할인 내역, 최종 결제 금액을 포함하는 영수중을
출력합니다.

## 디렉토리 구조
```text
+---main
|   +---java
|   |   \---store
|   |       |   Application.java
|   |       |
|   |       +---constant
|   |       |       FormatConstant.java
|   |       |       OrderProductConstant.java
|   |       |       ProductFileConstant.java
|   |       |       PromotionFileConstant.java
|   |       |       ViewMessageConstant.java
|   |       |
|   |       +---controller
|   |       |   |   InitConvenienceStoreController.java
|   |       |   |   MainController.java
|   |       |   |   MembershipController.java
|   |       |   |   ProductOrderController.java
|   |       |   |   ReceiptController.java
|   |       |   |
|   |       |   \---dto
|   |       |           MembershipDiscount.java
|   |       |           OrderNameInfo.java
|   |       |           OrderProduct.java
|   |       |           ProductInfo.java
|   |       |           ProductInfoDto.java
|   |       |           ProductOrderInfo.java
|   |       |           ProductOrderList.java
|   |       |           PromotionProduct.java
|   |       |           PromotionProductInfo.java
|   |       |           ReceiptDto.java
|   |       |           ReceiptProductInfo.java
|   |       |
|   |       +---exception
|   |       |       ErrorCode.java
|   |       |
|   |       +---model
|   |       |   |   GeneralMembershipService.java
|   |       |   |   MembershipService.java
|   |       |   |   ProductService.java
|   |       |   |   ReceiptService.java
|   |       |   |
|   |       |   +---entity
|   |       |   |       Product.java
|   |       |   |       ProductPrice.java
|   |       |   |       ProductQuantity.java
|   |       |   |       Promotion.java
|   |       |   |
|   |       |   \---repository
|   |       |           ProductRepository.java
|   |       |           PromotionRepository.java
|   |       |
|   |       +---util
|   |       |       ConvenienceStoreInitParser.java
|   |       |       OrderProductParser.java
|   |       |       YesOrNoParser.java
|   |       |
|   |       +---validation
|   |       |       OrderProductValidator.java
|   |       |
|   |       \---view
|   |               InputView.java
|   |               OutputView.java
|   |
|   \---resources
|           products.md
|           promotions.md
|
\---test
    \---java
        \---store
            |   ApplicationTest.java
            |
            +---fixture
            |       ProductFixture.java
            |       ProductOrderInfoFixture.java
            |       PromotionFixture.java
            |
            +---model
            |       GeneralMembershipServiceTest.java
            |       ProductServiceTest.java
            |       ReceiptServiceTest.java
            |
            +---util
            |       OrderProductParserTest.java
            |       YesOrNoParserTest.java
            |
            \---validation
                    OrderProductValidatorTest.java

```

## 기능 명세

### 재고관리
- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 나타낸다
- 재고 목록은 products 파일에 있다.
- 고객이 상품을 구매하면 재고에서 차감된다.

### 프로모션 할인
- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용된다.
- N개를 구매하면 1개를 무료 증정하는 방식이다
- 프로모션 혜택은 프로모션 제고 내에서만 가능하다.
- 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 
  필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우,
  일부 수량에 대해 정가로 결제하게 됨을 안내한다.
  - 이 때 프로모션 재고에서 할인을 받을 수 없는 나머지 프로모션도 포함된다.
    - 예를 들어 2+1 행사를 하는 재고가 7개인 상품이 있을 때 나머지인 1개는
      프로모션을 받을 수 없는 항목에 포함 된다.

### 멤버십 할인
- 상품 구매 후 멤버십 할인 받을 건지 여부를 입력 받는다.
- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- 프로모션 적용된 상품은 멤버십 할인이 안된다.
- 멤버십 할인의 최대 한도는 8,000원이다.

### 영수증 출력
- 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- 항목
  - 구매 상품 내역: 구매한 상품명, 수량, 가격
  - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
  - 금액정보
    - 총구매액: 구매한 상품의 총 수량과 총 금액
    - 행사할인: 프로모션에 의해 할인된 금액
    - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    - 내실돈: 최종 결제 금액

### 에러 발생 경우
- Y/N 입력 받을 때 그 외에 값을 입력하면 예외가 발생한다.
  - [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.


- 제품 구매 시 양식이 맞지 않을 때 예외가 발생한다.
  - [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.


- 존재하는 상품이 아닐 때 예외가 발생한다.
  - [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.


- 주문 갯수가 재고 수량을 초과하면 예외가 발생한다.
  - [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.


## 기능 구현 목록
- ### controller
  - 메인 컨트롤러
  - 창고, 프로모션을 세팅하는 컨트롤러
  - 제품 구매 컨트롤러
  - 주문서 관련 컨트롤러

- ### View
  - input
    - Y/N 입력을 받을 수 있다.
      - Y/N가 아닌 값을 받으면 예외를 발생한다.
    - 상품 주문 리스트를 받는다.
      - 존재하지 않은 상품 명일 때 예외 발생한다.
      - 올바르지 않은 양식이면 예외를 발생한다.
      - 재고 수량을 초과하면 예외를 발생한다.
  - output
    - 입장 안내 메세지를 출력할 수 있다. 
    - 상품 목록을 출력할 수 있다.
    - 구매하실 상품명과 수량을 입력 메세지를 출력할 수 있다.
      - 일부 수량이 프로모션 적용 안되는 경우 안내 메세지를 출력할 수 있다.
      - 추가로 받을 수 있는 상품이 있을 경우 안내 메세지를 출력할 수 있다.
    - 멤버십 할인 안내 메세지를 출력할 수 있다.
    - 영수증을 출력할 수 있다.
  
- ### Model
  - 물품 재고 넣고 빼는 서비스
    - 상품을 창고에 넣을 수 있다.
    - 상품을 창고에서 뺄 수 있다.
      - 상품 이름이 없으면 예외 발생
      - 상품의 재고가 모자르면 예외 발생
  
  - 프로모션 관리 서비스
    - 프로모션을 프로모션 리스트에 넣을 수 있다.
    - 프로모션 기간인지 확인할 수 있다.
    - 이름을 통해 프로모션 정보를 받을 수 있다.
  
  - 제품 구매 서비스
    - 제품을 구매할 수 있는지 확인할 수 있다.
    - 제품을 창고에서 뺄 수 있다.
      - 제품, 갯수, 프로모션 여부로 이루어진 주문표를 반환
      - 프로모션에 해당하는데 공짜로 받을 수 있으면 Exception
  
  - 멤버십 할인 서비스
    - 멤버십 계산 서비스

  - 주문서 만드는 서비스
    - 주문 내역 세팅 할 수 있다.
    - 프로모션 할인 제품을 셋팅할 수 있다.
    - 멤버십 할인 금액을 셋팅할 수 있다.
  
- ### Util
  - products.md에 있는 상품 리스트를 parsing 하는 기능
  - promotions.md에 있는 프로모션 목록을 parsing 하는 기능
  - 입력 받은 구매 제품 목록을 parsing 해 객체로 만드는 기능