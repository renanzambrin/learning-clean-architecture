package learning.infrastructure.product;

import java.util.Optional;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;
import learning.resolver.ProductResolver;
import learning.resolver.ProductResolver.FakeProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ProductResolver.class)
class ProductInMemoryRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductInMemoryRepository();
    }

    @Test
    void givenProduct_WhenCreateIsCalled_ThenSuccess(@FakeProduct Product product) {
        Assertions.assertDoesNotThrow(() -> productRepository.createProduct(product));
    }

    @Test
    void givenProductExists_WhenFindByNameIsCalled_ThenReturnProduct(@FakeProduct Product product) {
        Assertions.assertDoesNotThrow(() -> productRepository.createProduct(product));
        Optional<Product> result = productRepository.findProductByName(product.getName());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(product.getName(), result.get().getName());
        Assertions.assertEquals(product.getLastPrice(), result.get().getLastPrice());
    }

}