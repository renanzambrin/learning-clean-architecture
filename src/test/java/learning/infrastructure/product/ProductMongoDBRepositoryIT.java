package learning.infrastructure.product;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.util.Optional;
import javax.inject.Inject;
import learning.profile.MongoDBProfile;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;
import learning.resolver.ProductResolver;
import learning.resolver.ProductResolver.FakeProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@QuarkusTest
@TestProfile(MongoDBProfile.class)
@ExtendWith(ProductResolver.class)
class ProductMongoDBRepositoryIT {

    final ProductMongoDBRepository productRepository;

    @Inject
    ProductMongoDBRepositoryIT(ProductRepository productRepository) {
        this.productRepository = (ProductMongoDBRepository) productRepository;
    }

    @BeforeEach
    void setUp() {
        productRepository.getCollection().drop();
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
        Assertions.assertTrue(product.getLastPrice().isPresent());
        Assertions.assertTrue(result.get().getLastPrice().isPresent());
        Assertions.assertEquals(product.getLastPrice().get().getValue(), result.get().getLastPrice().get().getValue());
    }

    @Test
    void givenNoProductExists_WhenFindByNameIsCalled_ThenReturnEmpty(@FakeProduct Product product) {
        Optional<Product> result = productRepository.findProductByName(product.getName());
        Assertions.assertTrue(result.isEmpty());
    }

}