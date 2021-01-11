package learning.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import learning.product.exception.InvalidPriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Nested
    @DisplayName("Valid PriceVO unit tests")
    class ValidPriceVO {
        @Test
        void givenAValueAndValidFrom_WhenBuildPrice_ThenReturnPriceVo() {
            BigDecimal value = BigDecimal.valueOf(9.99);
            LocalDateTime dateFrom = LocalDateTime.now();
            Price price = Price.builder()
                    .withValue(value)
                    .withValidFrom(dateFrom)
                    .build();
            Assertions.assertNotNull(price);
        }

        @Test
        void givenAValueAndValidFromAndValidTo_WhenBuildPrice_ThenReturnPriceVo() {
            BigDecimal value = BigDecimal.valueOf(9.99);
            LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
            LocalDateTime dateTo = LocalDateTime.now();
            Price price = Price.builder()
                    .withValue(value)
                    .withValidFrom(dateFrom)
                    .withValidTo(dateTo)
                    .build();
            Assertions.assertNotNull(price);
        }
    }

    @Nested
    @DisplayName("Exception throw PriceVO unit tests")
    class ExceptionPriceVO {
        @Test
        void givenNoData_WhenBuildPrice_ThenThrowException() {
            Assertions.assertThrows(InvalidPriceException.class,
                    () -> Price.builder().build(),
                    Price.VALUE_CANT_BE_NULL);
        }

        @Test
        void givenANegativeValue_WhenBuildPrice_ThenThrowException() {
            BigDecimal value = BigDecimal.valueOf(-9.99);
            Assertions.assertThrows(InvalidPriceException.class,
                    () -> Price.builder().withValue(value).build(),
                    Price.VALUE_CANT_BE_NEGATIVE);
        }

        @Test
        void givenAValueAndNoValidFrom_WhenBuildPrice_ThenThrowException() {
            BigDecimal value = BigDecimal.valueOf(9.99);
            Assertions.assertThrows(InvalidPriceException.class,
                    () -> Price.builder().withValue(value).build(),
                    Price.VALID_FROM_CANT_BE_NULL);
        }

        @Test
        void givenAValueAndValidFromAndValidToBeforeValidFrom_WhenBuildPrice_ThenThrowException() {
            BigDecimal value = BigDecimal.valueOf(9.99);
            LocalDateTime dateFrom = LocalDateTime.now();
            LocalDateTime dateTo = LocalDateTime.now().minusDays(1);
            Assertions.assertThrows(InvalidPriceException.class,
                    () -> Price.builder().withValue(value).withValidFrom(dateFrom).withValidTo(dateTo).build(),
                    Price.VALID_TO_CANT_BE_BEFORE_VALID_FROM);
        }
    }

    @Nested
    @DisplayName("PriceBuilder unit tests")
    class BuilderPrice {
        @Test
        void givenAValueAndValidFromAndValidTo_WhenBuildPrice_ThenAllDataShouldMatch() {
            BigDecimal value = BigDecimal.valueOf(9.99);
            LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
            LocalDateTime dateTo = LocalDateTime.now();
            Price price = Price.builder()
                    .withValue(value)
                    .withValidFrom(dateFrom)
                    .withValidTo(dateTo)
                    .build();
            Assertions.assertNotNull(price);
            Assertions.assertEquals(value, price.getValue());
            Assertions.assertEquals(dateFrom, price.getValidFrom());
            Assertions.assertEquals(dateTo, price.getValidTo());
        }
    }

}