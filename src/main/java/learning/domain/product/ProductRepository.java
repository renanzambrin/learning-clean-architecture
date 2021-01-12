package learning.domain.product;

import java.util.Optional;

public interface ProductRepository {

    void createProduct(Product product);

    Optional<Product> findProductByName(String name);

}
