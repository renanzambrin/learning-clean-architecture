package learning.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import learning.domain.product.Price;
import learning.domain.product.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ProductResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(FakeProduct.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        return getFakeProduct();
    }

    private Product getFakeProduct() {
        return Product.builder()
                .withName("A Fake Product")
                .withPrices(getFakePriceList())
                .build();
    }

    private List<Price> getFakePriceList() {
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
        return prices;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface FakeProduct {

    }
}
