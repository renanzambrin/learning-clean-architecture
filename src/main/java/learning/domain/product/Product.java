package learning.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import learning.domain.product.exception.InvalidProductException;
import lombok.Getter;

@Getter
public class Product {

    static final String NAME_CAN_NOT_BE_NULL = "Name can't be null";

    private final String name;
    private final List<Price> prices;

    private Product(String name, List<Price> prices) {
        this.name = name;
        this.prices = prices;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public void addPrice(BigDecimal value) {
        Optional<Price> lastPrice = getLastPrice();
        if (lastPrice.isPresent()) {
            Price price = lastPrice.get()
                    .toBuilder()
                    .withValidTo(LocalDateTime.now())
                    .build();
            replaceLastPrice(price);
        }
        prices.add(Price.builder()
                .withValue(value)
                .withValidFrom(LocalDateTime.now())
                .build());
    }

    public Optional<Price> getLastPrice() {
        if (prices.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(prices.get(getLastPriceIndex()));
    }

    void replaceLastPrice(Price price) {
        prices.remove(getLastPriceIndex());
        prices.add(price);
    }

    int getLastPriceIndex() {
        return prices.size() - 1;
    }

    public static class ProductBuilder {
        private String name;
        private List<Price> prices;

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withPrices(List<Price> prices) {
            this.prices = prices;
            return this;
        }

        public Product build() {
            if (Objects.isNull(name)) {
                throw new InvalidProductException(NAME_CAN_NOT_BE_NULL);
            } else if (Objects.isNull(prices)) {
                prices = new ArrayList<>();
            }
            return new Product(name, prices);
        }
    }

}
