package learning.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import learning.product.exception.InvalidProductException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Nested
    class ValidProduct {
        @Test
        void givenAValidName_WhenBuildProduct_ThenReturnProduct() {
            String name = "A test name";
            Product product = Product.builder()
                    .withName(name)
                    .build();
            Assertions.assertNotNull(product);
            Assertions.assertEquals(name, product.getName());
        }

        @Test
        void givenAValidNameAndPrices_WhenBuildProduct_ThenReturnProduct() {
            String name = "A test name";
            List<Price> prices = new ArrayList<>();
            prices.add(Price.builder()
                    .withValue(BigDecimal.valueOf(10.0))
                    .withValidFrom(LocalDateTime.now().minusDays(2))
                    .withValidTo(LocalDateTime.now().minusDays(1))
                    .build());
            prices.add(Price.builder()
                    .withValue(BigDecimal.valueOf(9.99))
                    .withValidFrom(LocalDateTime.now().minusDays(1))
                    .build());
            Product product = Product.builder()
                    .withName(name)
                    .withPrices(prices)
                    .build();
            Assertions.assertNotNull(product);
            Assertions.assertEquals(name, product.getName());
            Assertions.assertEquals(2, product.getPrices().size());
        }
    }

    @Nested
    class InvalidProduct {
        @Test
        void givenNoName_WhenBuildProduct_ThenReturnProduct() {
            Assertions.assertThrows(InvalidProductException.class,
                    () -> Product.builder().withName(null).build(),
                    Product.NAME_CAN_NOT_BE_NULL);
        }
    }

    @Nested
    class PricesManageMethods {
        @Test
        void givenAddingValidPrice_WhenAddPrice_ThenPricesSizeShouldIncrease() {
            String name = "A test name";
            Product product = Product.builder()
                    .withName(name)
                    .build();
            Assertions.assertNotNull(product);
            product.addPrice(BigDecimal.valueOf(10));
            product.addPrice(BigDecimal.valueOf(9.99));
            Assertions.assertEquals(2, product.getPrices().size());
        }

        @Test
        void givenAValidPrice_WhenReplaceLastPrice_ThenLastPriceShouldBeOverride() {
            String name = "A test name";
            Product product = Product.builder()
                    .withName(name)
                    .build();
            Assertions.assertNotNull(product);
            product.addPrice(BigDecimal.valueOf(10));
            BigDecimal newValue = BigDecimal.valueOf(9.99);
            product.replaceLastPrice(Price.builder()
                    .withValue(newValue)
                    .withValidFrom(LocalDateTime.now().minusDays(1))
                    .build());
            Assertions.assertEquals(1, product.getPrices().size());
            Optional<Price> priceOpt = product.getLastPrice();
            Assertions.assertTrue(priceOpt.isPresent());
            Price price = priceOpt.get();
            Assertions.assertEquals(newValue, price.getValue());
        }
    }

}