package learning.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import learning.domain.product.Price;
import learning.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private BigDecimal price;

    public Product toProduct() {
        Price newPrice = Price.builder()
                .withValue(this.price)
                .withValidFrom(LocalDateTime.now())
                .build();
        return Product.builder()
                .withName(this.name)
                .withPrices(Collections.singletonList(newPrice))
                .build();
    }

}
