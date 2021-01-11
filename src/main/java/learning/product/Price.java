package learning.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import learning.product.exception.InvalidPriceException;
import lombok.Getter;

@SuppressWarnings("CdiInjectionPointsInspection")
@Getter
public class Price {

    static final String VALUE_CANT_BE_NULL = "Value can't be null";
    static final String VALUE_CANT_BE_NEGATIVE = "Value can't be negative";
    static final String VALID_FROM_CANT_BE_NULL = "ValidFrom can't be null";
    static final String VALID_TO_CANT_BE_BEFORE_VALID_FROM = "ValidTo can't be before ValidFrom";

    private final BigDecimal value;
    private final LocalDateTime validFrom;
    private final LocalDateTime validTo;

    public static PriceBuilder builder() {
        return new PriceBuilder();
    }

    private Price(BigDecimal value, LocalDateTime validFrom, LocalDateTime validTo) {
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public PriceBuilder toBuilder() {
        return new PriceBuilder()
                .withValue(this.value)
                .withValidFrom(validFrom)
                .withValidTo(validTo);
    }

    public static class PriceBuilder {

        private BigDecimal value;
        private LocalDateTime validFrom;
        private LocalDateTime validTo;

        public PriceBuilder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public PriceBuilder withValidFrom(LocalDateTime date) {
            this.validFrom = date;
            return this;
        }

        public PriceBuilder withValidTo(LocalDateTime date) {
            this.validTo = date;
            return this;
        }

        public Price build() {
            if (Objects.isNull(value)) {
                throw new InvalidPriceException(VALUE_CANT_BE_NULL);
            } else if (value.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvalidPriceException(VALUE_CANT_BE_NEGATIVE);
            } else if (Objects.isNull(validFrom)) {
                throw new InvalidPriceException(VALID_FROM_CANT_BE_NULL);
            } else if (validTo != null && validTo.isBefore(validFrom)) {
                throw new InvalidPriceException(VALID_TO_CANT_BE_BEFORE_VALID_FROM);
            }
            return new Price(value, validFrom, validTo);
        }
    }

}
